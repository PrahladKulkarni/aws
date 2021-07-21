package com.aws.vokunev.prodcatalog.dao;

import java.util.ArrayList;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import com.aws.vokunev.prodcatalog.model.AccessToken;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.springframework.stereotype.Component;

import net.minidev.json.JSONArray;

/**
 * This class implements a DAO patetrn for retrieving the OIDC access token.
 */
@Component
public class AccessTokenDataAccessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenDataAccessor.class);

    /**
     * This method attempts to locate and fetch the user data from an OIDC access
     * token forwarded by an Application Load Balancer as per
     * https://docs.aws.amazon.com/elasticloadbalancing/latest/application/listener-authenticate-users.html
     * 
     * @return an instance of a {@link AccessToken} or null if not available.
     */
    public AccessToken getToken(HttpServletRequest request) {
        return getToken(request.getHeader("x-amzn-oidc-accesstoken"));
    }

    /**
     * This method parses provided Base64 encoded OIDC access token. Please note
     * that this is a simplified token consumption algorithm. For the proper token
     * treatment please refer to:
     * https://docs.aws.amazon.com/cognito/latest/developerguide/amazon-cognito-user-pools-using-tokens-verifying-a-jwt.html
     * 
     * @return an instance of a {@link AccessToken} or null if not available.
     */
    public AccessToken getToken(String access_token_encoded) {

        LOGGER.info("Access Token Encoded: {}", access_token_encoded);

        if (access_token_encoded == null) {
            return null;
        }

        AccessToken user = new AccessToken();

        // Decode the token
        String[] parts = access_token_encoded.split("\\.");
        String b64payload = parts[1];
        byte[] decodedBytes = Base64.getDecoder().decode(b64payload);
        String access_token_decoded = new String(decodedBytes);
        LOGGER.info("Access Token Decoded: {}", access_token_decoded);

        // Process the token
        DocumentContext context = JsonPath.parse(access_token_decoded);

        try {
            // Parse user name
            user.setUsername(context.read("$.username"));
        } catch (com.jayway.jsonpath.PathNotFoundException ex) {
            // If the element is not found, just log the exception and carry on
            LOGGER.error("No username found in the access token", ex);
        }

        try {
            // Parse user groups
            JSONArray groups_array = (JSONArray) context.read("$.cognito:groups");
            if (groups_array != null) {
                ArrayList<String> groups = new ArrayList<String>(groups_array.size());
                for (Object group : groups_array) {
                    groups.add(String.valueOf(group));
                }
                user.setGroups(groups);
            }
        } catch (com.jayway.jsonpath.PathNotFoundException ex) {
            // If the element is not found, just log the exception and carry on
            LOGGER.error("No user groups found in the access token", ex);
        }

        return user;
    }
}

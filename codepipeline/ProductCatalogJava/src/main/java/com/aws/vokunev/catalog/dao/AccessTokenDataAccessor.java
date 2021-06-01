package com.aws.vokunev.catalog.dao;

import com.aws.vokunev.catalog.model.AccessToken;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Base64;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

/**
 * This class implements a DAO patetrn for accessing the user data.
 */
public class AccessTokenDataAccessor {

    /**
     * This method fetches the user data from an OIDC access token forwarded by an
     * Application Load Balancer as per
     * https://docs.aws.amazon.com/elasticloadbalancing/latest/application/x-forwarded-headers.html
     * 
     * @return an instance of a {@link AccessToken} or null if not available.
     */
    public static AccessToken getToken(HttpServletRequest request) {
        return getToken(request.getHeader("x-amzn-oidc-accesstoken"));
    }

    public static AccessToken getToken(String access_token_encoded) {

        System.out.println("access_token_encoded: " + access_token_encoded);

        if (access_token_encoded == null) {
            return null;
        }

        AccessToken user = new AccessToken();

        // Decode the token
        String[] parts = access_token_encoded.split("\\.");
        String b64payload = parts[1];
        byte[] decodedBytes = Base64.getDecoder().decode(b64payload);
        String access_token_decoded = new String(decodedBytes);
        System.out.println("access_token-decoded: " + access_token_decoded);

        // Process the token
        DocumentContext context = JsonPath.parse(access_token_decoded);

        try {
            // Parse user name
            user.setUsername(context.read("$.username"));
        } catch (com.jayway.jsonpath.PathNotFoundException ex) {
            // If the element is not found, just log the exception and carry on
            ex.printStackTrace(System.out);
        }

        try {
            // Parse user groups
            JSONArray groups_array = (JSONArray) context.read("$.cognito:groups");
            if (groups_array != null) {
                ArrayList<String> groups = new ArrayList<String>(groups_array.size());
                for(Object group: groups_array) {
                    groups.add(String.valueOf(group));
                }
                user.setGroups(groups);
            }
        } catch (com.jayway.jsonpath.PathNotFoundException ex) {
            // If the element is not found, just log the exception and carry on
            ex.printStackTrace(System.out);
        }

        System.out.println(user);

        return user;
    }
}

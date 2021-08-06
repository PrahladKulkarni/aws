package com.aws.vokunev.prodcatalog.model;

import java.util.List;
import java.util.ArrayList;

/**
 * An object of this type represents the application permissions rules.
 */
public class ApplicationPermissions {

    private AccessToken token;
    private ApplicationConfiguration config;

    public ApplicationPermissions(AccessToken token, ApplicationConfiguration config) {
        this.token = token;
        this.config = config;
    }

    /**
     * This method makes a decision whether the currently authenticated user is
     * permitted to access the instance metadata. The decision is based on the user
     * group membership from the access token and the application configuration
     * data.
     * 
     * @return true if the current user is allowed to access the instance metadata,
     *         otherwise false.
     */
    public boolean isPermittedToAccessMetadata() {

        if (token == null || config == null) {
            // not enough data to make a desision
            return false;
        }

        List<String> userGroups = token.getGroups();
        List<String> userGroupsAllowedMetadataAccess = config.getInstanceMetadataAccessRoles();

        List<String> common = new ArrayList<String>(userGroups);
        common.retainAll(userGroupsAllowedMetadataAccess);

        return common.size() > 0 ? true : false;
    }

    /**
     * This method makes a decision whether the currently authenticated user is
     * permitted to update the product price. The decision is based on the user
     * group membership from the access token and the application configuration
     * data.
     * 
     * @return true if the current user is allowed to update the product price,
     *         otherwise false.
     */
    public boolean isPermittedToUpdatePrice() {

        if (token == null || config == null) {
            // not enough data to make a desision
            return false;
        }

        List<String> userGroups = token.getGroups();
        List<String> userGroupsAllowedUpdatePrice = config.getPriceUpdateRoles();

        List<String> common = new ArrayList<String>(userGroups);
        common.retainAll(userGroupsAllowedUpdatePrice);

        return common.size() > 0 ? true : false;
    }
}

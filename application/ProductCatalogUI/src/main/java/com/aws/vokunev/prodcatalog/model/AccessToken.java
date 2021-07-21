package com.aws.vokunev.prodcatalog.model;

import java.util.List;

/**
 * An object of this type represents relevant for the application
 * data from an OIDC access token.
 */
public class AccessToken {

    private String username;
    private List<String> groups;

    /**
     * @return String return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return List return the groups
     */
    public List<String> getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "User [groups=" + groups + ", username=" + username + "]";
    }

    /**
     * @return true if this user is part of the managers group, otherwise false.
     */
    public boolean isManagers() {
        if (groups == null) {
            return false;
        } else {
            return groups.contains("managers"); 
        }
    }

    /**
     * @return true if this user is part of the operations group, otherwise false.
     */
    public boolean isOperations() {
        if (groups == null) {
            return false;
        } else {
            return groups.contains("operations"); 
        }
    }

    /**
     * @return true if this user is part of the engineers group, otherwise false.
     */
    public boolean isEngineers() {
        if (groups == null) {
            return false;
        } else {
            return groups.contains("engineers"); 
        }
    }    
}
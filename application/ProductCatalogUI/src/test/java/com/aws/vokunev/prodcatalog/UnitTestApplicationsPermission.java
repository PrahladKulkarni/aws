package com.aws.vokunev.prodcatalog;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import com.aws.vokunev.prodcatalog.model.AccessToken;
import com.aws.vokunev.prodcatalog.model.ApplicationConfiguration;
import com.aws.vokunev.prodcatalog.model.ApplicationPermissions;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UnitTestApplicationsPermission {

    private static AccessToken token1;
    private static AccessToken token2;
    private static ApplicationConfiguration config;

    @BeforeAll
    static void setup() {

        ArrayList<String> tokenUserGroups1 = new ArrayList<String>();
        tokenUserGroups1.add("engineers");
        tokenUserGroups1.add("managers");
        tokenUserGroups1.add("operations");
        token1 = new AccessToken();
        token1.setGroups(tokenUserGroups1);

        ArrayList<String> tokenUserGroups2 = new ArrayList<String>();
        tokenUserGroups2.add("users");
        tokenUserGroups2.add("powerusers");
        tokenUserGroups2.add("dbas");
        token2 = new AccessToken();
        token2.setGroups(tokenUserGroups2);

        ArrayList<String> configUserGroups1 = new ArrayList<String>();
        configUserGroups1.add("engineers");
        configUserGroups1.add("operations");
        config = new ApplicationConfiguration();
        config.setInstanceMetadataAccessRoles(configUserGroups1);
    }

    @AfterAll
    static void tearDown() {
        token1 = null;
        token2 = null;
        config = null;
    }

    @Test
    @DisplayName("Test for permission granted")
    void testPermissionGranted() {
        ApplicationPermissions permission = new ApplicationPermissions(token1, config);
        assertTrue(permission.isPermittedToAccessMetadata());
    }

    @Test
    @DisplayName("Test for permission denied")
    void testPermissionDenied() {
        ApplicationPermissions permission = new ApplicationPermissions(token2, config);
        assertFalse(permission.isPermittedToAccessMetadata());
    }

}

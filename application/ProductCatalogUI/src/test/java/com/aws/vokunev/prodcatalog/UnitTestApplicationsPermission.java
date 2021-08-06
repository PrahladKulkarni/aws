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

        config = new ApplicationConfiguration();

        ArrayList<String> instanceMetadataAccessRoles = new ArrayList<String>();
        instanceMetadataAccessRoles.add("engineers");
        instanceMetadataAccessRoles.add("operations");
        config.setInstanceMetadataAccessRoles(instanceMetadataAccessRoles);

        ArrayList<String> priceUpdateRoles = new ArrayList<String>();
        priceUpdateRoles.add("managers");
        config.setPriceUpdateRoles(priceUpdateRoles);
    }

    @AfterAll
    static void tearDown() {
        token1 = null;
        token2 = null;
        config = null;
    }

    @Test
    @DisplayName("Test for metadata access permission granted")
    void testMetadataAccessPermissionGranted() {
        ApplicationPermissions permission = new ApplicationPermissions(token1, config);
        assertTrue(permission.isPermittedToAccessMetadata());
    }

    @Test
    @DisplayName("Test for metadata access permission denied")
    void testMetadataAccessPermissionDenied() {
        ApplicationPermissions permission = new ApplicationPermissions(token2, config);
        assertFalse(permission.isPermittedToAccessMetadata());
    }

    @Test
    @DisplayName("Test for price update permission granted")
    void testPriceUpdatePermissionGranted() {
        ApplicationPermissions permission = new ApplicationPermissions(token1, config);
        assertTrue(permission.isPermittedToUpdatePrice());
    }

    @Test
    @DisplayName("Test for price update permission denied")
    void testPriceUpdatePermissionDenied() {
        ApplicationPermissions permission = new ApplicationPermissions(token2, config);
        assertFalse(permission.isPermittedToUpdatePrice());
    }
}

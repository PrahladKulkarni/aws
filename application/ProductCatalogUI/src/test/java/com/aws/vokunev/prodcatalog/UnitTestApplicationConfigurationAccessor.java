package com.aws.vokunev.prodcatalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.aws.vokunev.prodcatalog.dao.ApplicationConfigurationAccessor;
import com.aws.vokunev.prodcatalog.model.ApplicationConfiguration;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UnitTestApplicationConfigurationAccessor {

    @Autowired
    private ApplicationConfigurationAccessor configurationAccessor;

    @Test
    @DisplayName("Test for parsing application configuration JSON data")
    void testParseApplicationConfiguration() throws Exception {

        // Load test JSON data from a file
        String json = IOUtils.toString(
            this.getClass().getResourceAsStream("applicationConfigurationTestData.json"),
            "UTF-8"
        );

        ApplicationConfiguration config = configurationAccessor.getConfiguration(json);
        assertNotNull(config);
        assertTrue(config.getInstanceMetadataAccessRoles().contains("operations"));
        assertEquals(config.getApiKeySecret(), "mysecret");
        assertEquals(config.getServiceEndpointProductList(), "productlistapiendpoint");
        assertEquals(config.getServiceEndpointProductDetails(), "productdetailsapiendpoint");
        assertEquals(config.getServiceEndpointLogout(), "logoutendpoint");
        assertEquals(config.getItemColor(), "lightskyblue");
        assertEquals(config.isFeatureFlagPriceUpdate(), true);
    }
}

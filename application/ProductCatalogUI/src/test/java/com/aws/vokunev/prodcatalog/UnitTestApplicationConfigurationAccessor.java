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
        String json = IOUtils.toString(this.getClass().getResourceAsStream("applicationConfigurationTestData.json"),
                "UTF-8");

        // Parse JSON to ApplicationConfiguration object
        ApplicationConfiguration config = configurationAccessor.getConfiguration(json);
        assertNotNull(config);
        assertTrue(config.getInstanceMetadataAccessRoles().contains("operations"));
        assertTrue(config.getPriceUpdateRoles().contains("managers"));        
        assertEquals(config.getApiKeySecret(), "apigateway/apikeys/cloud101.link/production");
        assertEquals(config.getServiceEndpointProductList(),
                "https://hygnft82o0.execute-api.us-west-2.amazonaws.com/production/product/list");
        assertEquals(config.getServiceEndpointProductDetails(),
                "https://hygnft82o0.execute-api.us-west-2.amazonaws.com/production/product/details");
        assertEquals(config.getServiceEndpointProductPriceUpdate(),
                "https://hygnft82o0.execute-api.us-west-2.amazonaws.com/production/product/price");
        assertEquals(config.getServiceEndpointLogout(),
                "https://auth.cloud101.link/logout?client_id=34fak97jrt25f4bqdvad28rsdd&logout_uri=https://www.cloud101.link/prodcatalog/");
        assertEquals(config.getItemColor(), "lightskyblue");
        assertEquals(config.isFeatureFlagPriceUpdate(), true);
    }
}

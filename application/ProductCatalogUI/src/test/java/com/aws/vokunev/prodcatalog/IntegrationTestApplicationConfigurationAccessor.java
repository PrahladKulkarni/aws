package com.aws.vokunev.prodcatalog;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.aws.vokunev.prodcatalog.dao.ApplicationConfigurationAccessor;
import com.aws.vokunev.prodcatalog.model.ApplicationConfiguration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IntegrationTestApplicationConfigurationAccessor {

    @Autowired
    private ApplicationConfigurationAccessor configurationAccessor;

    @Test
    @DisplayName("Test for retrieving application configuration from AWS AppConfig")
    void testRetrieveApplicationConfiguration() {
        ApplicationConfiguration config = configurationAccessor.getConfiguration();
        assertNotNull(config);
        assertNotNull(config.getServiceEndpointProductList());
        assertNotNull(config.getServiceEndpointProductDetails());
        assertNotNull(config.getServiceEndpointLogout());
        assertNotNull(config.getItemColor());
    }
}

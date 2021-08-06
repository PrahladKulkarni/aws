package com.aws.vokunev.prodcatalog.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aws.vokunev.prodcatalog.model.ApplicationConfiguration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.services.appconfig.AppConfigClient;
import software.amazon.awssdk.services.appconfig.model.GetConfigurationRequest;
import software.amazon.awssdk.services.appconfig.model.GetConfigurationResponse;

/**
 * This accessor implements implements a DAO patetrn for accessing the
 * application configuration from AWS AppConfig service. It uses the most basic
 * data retrieval approach to keep this demo application simple. It requests the
 * application configuration directly from the AWS AppConfig service every time.
 * A better practice would be to implement an application configuration caching
 * to reduce the amount of API calls to the AWS AppConfig service. An example of
 * such more advanced implementation can be found at:
 * https://github.com/aws-samples/aws-appconfig-java-sample
 */
@Component
@PropertySource("classpath:release.properties")
public class ApplicationConfigurationAccessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfigurationAccessor.class);

    /*
     * This is a unique, user-specified ID to identify the client for the AWS
     * AppConfig configuration. This ID enables AWS AppConfig to deploy the
     * configuration in intervals, as defined in the deployment strategy. See:
     * https://docs.aws.amazon.com/appconfig/latest/userguide/appconfig-retrieving-
     * the-configuration.html
     */
    private static final String clientId = UUID.randomUUID().toString();

    @Value("${appconfig.application}")
    String application;

    @Value("${appconfig.environment}")
    String environment;

    @Value("${appconfig.profile}")
    String profile;

    private AppConfigClient client = AppConfigClient.create();

    private GetConfigurationResponse getConfigurationFromAPI(String application, String environment, String profile) {

        GetConfigurationResponse result = client.getConfiguration(GetConfigurationRequest.builder()
                .application(application).environment(environment).configuration(profile).clientId(clientId).build());

        return result;
    }

    /**
     * Retrieves an application configuration from AWS AppConfig service based on the values specified in release.properties file.
     * 
     * @return Instance of ApplicationConfiguration object
     */
    public ApplicationConfiguration getConfiguration() {

        LOGGER.info("Requesting AppConfig configuration for application={}, environment={}, configuration profile={}",
                application, environment, profile);

        GetConfigurationResponse result = getConfigurationFromAPI(application, environment, profile);

        if (result.content() == null) {
            LOGGER.error("AppConfig returned empty response");
            return null;
        }
        String appConfigResponse = result.content().asUtf8String();
        LOGGER.info("AppConfig response: {}", appConfigResponse);

        // Process the response
        return getConfiguration(appConfigResponse);
    }

    /**
     * Parses JSON document representing application configuration and creates an
     * instance of ApplicationConfiguration object.
     * 
     * @param applicationConfigurationJson JSON document representing application
     *                                     configuration
     * @return Instance of ApplicationConfiguration object
     */
    public ApplicationConfiguration getConfiguration(String applicationConfigurationJson) {

        DocumentContext context = JsonPath.parse(applicationConfigurationJson);
        ApplicationConfiguration config = new ApplicationConfiguration();
        
        int totalInstanceMetadataAccessRoles = context.read("$.InstanceMetadataAccessRoles.length()");
        List<String> instanceMetadataAccessRoles = new ArrayList<String>(totalInstanceMetadataAccessRoles);
        for (int i = 0; i < totalInstanceMetadataAccessRoles; i++) {
            instanceMetadataAccessRoles.add(context.read(String.format("$.InstanceMetadataAccessRoles[%s]", i)));
        }
        config.setInstanceMetadataAccessRoles(instanceMetadataAccessRoles);

        int totalPriceUpdateRoles = context.read("$.PriceUpdateRoles.length()");
        List<String> priceUpdateRoles = new ArrayList<String>(totalPriceUpdateRoles);
        for (int i = 0; i < totalPriceUpdateRoles; i++) {
            priceUpdateRoles.add(context.read(String.format("$.PriceUpdateRoles[%s]", i)));
        }
        config.setPriceUpdateRoles(priceUpdateRoles);

        config.setApiKeySecret(context.read("$.APIKeySecret"));
        config.setServiceEndpointProductList(context.read("$.ServiceEndpointProductList"));
        config.setServiceEndpointProductDetails(context.read("$.ServiceEndpointProductDetails"));
        config.setServiceEndpointProductPriceUpdate(context.read("$.ServiceEndpointProductPriceUpdate"));
        config.setServiceEndpointLogout(context.read("$.ServiceEndpointLogout"));
        config.setItemColor(context.read("$.ItemColor"));
        config.setFeatureFlagPriceUpdate(context.read("$.FeatureFlagPriceUpdate"));
        LOGGER.info("AppConfig response parsed: {}", config);

        return config;
    }

}

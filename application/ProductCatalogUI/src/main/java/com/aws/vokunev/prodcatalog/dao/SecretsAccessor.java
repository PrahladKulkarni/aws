package com.aws.vokunev.prodcatalog.dao;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

/**
 * This accessor implements a DAO patetrn for accessing a secret value from AWS
 * Secrets Manager. It uses the most basic data retrieval approach to keep this
 * demo application simple. It requests the secret value directly from the AWS
 * Secrets Manager service every time. A better practice would be to implement a
 * secret caching to reduce the amount of API calls to the AWS Secrets Manager
 * service.
 */
@Component
public class SecretsAccessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecretsAccessor.class);

    // Create a Secrets Manager client
    private SecretsManagerClient client = SecretsManagerClient.builder().build();

    /**
     * Retrieves a secret value from AWS Secret Manager service.
     * 
     * @param secretName the name of the secret in AWS Secrets Manager
     * @param secretKey  the key of the secret value
     * @return a value of a secret
     */
    public String getSecret(String secretName, String secretKey) {

        GetSecretValueRequest request = GetSecretValueRequest.builder().secretId(secretName).build();
        LOGGER.info("Requesting secret value for {}", secretName);
        GetSecretValueResponse response = client.getSecretValue(request);

        if (response == null) {
            LOGGER.error("AWS SecretsManager returned null response");
            return null;
        }

        // Retrieve the secret. The result will be in the following format:
        // {"mysecretkey":"mysecretvalue"}
        String result = response.secretString();
        if (result == null) {
            return null;
        }

        // Process the result
        DocumentContext context = JsonPath.parse(result);
        String secretValue = context.read("$." + secretKey);

        return secretValue;
    }
}

package com.aws.vokunev.catalog.data;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

/**
 * This DynamoDB client factory creates an instance of a local client if
 * an environment variable AWS_DYNAMODB_LOCAL is set to "true", otherwise
 * it creates an instance of remote client.
 */
public class DynamoDBClientFactory {

    static public String ENVIRONMENT = "AWS_DYNAMODB_LOCAL";

    public static AmazonDynamoDB createClient() {

        String env = System.getenv(ENVIRONMENT);
        AmazonDynamoDB client = null;
        System.out.println("AWS_DYNAMODB_LOCAL=" + env);
        if (env != null && env.equalsIgnoreCase("true")) {
            // Create local client
            client = AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", Regions.US_WEST_2.getName())
                )
                .build();
        } else {
            // Create remote client
            client = AmazonDynamoDBClientBuilder
                .standard()
                .withRegion(Regions.US_WEST_2)
                .build();
        }

        return client;
    }
}
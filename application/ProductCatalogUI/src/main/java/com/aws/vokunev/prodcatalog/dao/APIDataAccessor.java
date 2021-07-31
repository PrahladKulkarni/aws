package com.aws.vokunev.prodcatalog.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.jsonpath.JsonPath;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * This is a base class for the data accessors retrieving the data from a web API.
 */
public abstract class APIDataAccessor {

    private static final Logger LOGGER=LoggerFactory.getLogger(APIDataAccessor.class);    

    /**
     * Invokes a web API
     * @param url - the url of the API
     * @return the result of an API invocation or null if not available
     */
    protected String invokeGetAPIRequest(String url) {

        int timeoutSeconds = 5;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        int CONNECTION_TIMEOUT_MS = timeoutSeconds * 1000; // Timeout in millis.
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(CONNECTION_TIMEOUT_MS)
            .setConnectTimeout(CONNECTION_TIMEOUT_MS)
            .setSocketTimeout(CONNECTION_TIMEOUT_MS)
            .build();

        request.setConfig(requestConfig);

        try {
            // Send Get request 
            LOGGER.info("Sending HTTP GET request: {}", request);
            CloseableHttpResponse response = httpClient.execute(request);

            // Log the HttpResponse Status
            LOGGER.info("HTTP response status: {}", response.getStatusLine().toString());

            // Process the response
            String result = EntityUtils.toString(response.getEntity());
            LOGGER.info("HTTP response data: {}", result);

            if (result == null) {
                throw new RuntimeException("Unexpected null value for API response entity.");
            } 

            try {
                String error = JsonPath.read(result, "$.errorMessage");
                throw new RuntimeException(error);
            } catch (com.jayway.jsonpath.PathNotFoundException ex) {
                // No error was returned, which is good, we can continue
            }
    
            // Parse the content
            return result;

        } catch (Exception ex) {
            LOGGER.error("API invocation error: {}", ex.getMessage());
            return null;
        }
    }
}
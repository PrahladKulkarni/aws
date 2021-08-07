package com.aws.vokunev.prodcatalog.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import com.jayway.jsonpath.JsonPath;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * This is a base class for the data accessors retrieving the data from a web
 * API.
 */
public abstract class APIDataAccessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(APIDataAccessor.class);

    // HTTP methods supported by this accessor 
    enum HttpMethod { GET, PUT; }

    /**
     * Sends a GET request to the provided URL.
     * 
     * @param url - the url of the API
     * @return the result of an API invocation or null if not available
     */
    protected String invokeGetAPIRequest(String url) throws IOException {
        return invokeGetAPIRequest(url, null);
    }

    /**
     * Sends a GET request to the provided URL with provided API key.
     * 
     * @param url    - the url of the API
     * @param apiKey - the value of the API key
     * @return the result of an API invocation or null if not available
     */
    protected String invokeGetAPIRequest(String url, String apiKey) throws IOException {
        return invokeAPIRequest(HttpMethod.GET, url, apiKey);
    }

    /**
     * Sends a PUT request to the provided URL with provided API key.
     * 
     * @param url    - the url of the API
     * @param apiKey - the value of the API key
     * @return the result of an API invocation or null if not available
     */
    protected String invokePutAPIRequest(String url, String apiKey) throws IOException {
        return invokeAPIRequest(HttpMethod.PUT, url, apiKey);
    }

    /**
     * Sends a specified HTTP request to the provided URL with provided API key.
     * 
     * @param url    - the url of the API
     * @param apiKey - the value of the API key
     * @return the result of an API invocation or null if not available
     */
    protected String invokeAPIRequest(HttpMethod method, String url, String apiKey) throws IOException {

        int timeoutSeconds = 5;

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpRequestBase request = null;
        switch (method) {
            case GET:
                request = new HttpGet(url);
                break;
            case PUT:
                request = new HttpPut(url);
                break;
        }

        // Include an API key if provided
        if (apiKey != null) {
            request.setHeader("x-api-key", apiKey);
        }

        int CONNECTION_TIMEOUT_MS = timeoutSeconds * 1000; // Timeout in millis.
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECTION_TIMEOUT_MS)
                .setConnectTimeout(CONNECTION_TIMEOUT_MS).setSocketTimeout(CONNECTION_TIMEOUT_MS).build();

        request.setConfig(requestConfig);

        // Send Get request
        LOGGER.info("Sending HTTP GET request: {}", request);
        CloseableHttpResponse response = httpClient.execute(request);

        // Log the HttpResponse Status
        LOGGER.info("HTTP response status: {}", response.getStatusLine().toString());

        // Process the response
        String result = EntityUtils.toString(response.getEntity());
        LOGGER.info("HTTP response data: {}", result);

        // Treat any response code other than 200 as an error
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException(response.getStatusLine().toString());
        }

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
    }    
}
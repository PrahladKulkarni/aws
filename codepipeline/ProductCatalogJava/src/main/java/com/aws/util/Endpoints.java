package com.aws.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * This class provides references to the service endpoints used in this application
 */
public class Endpoints {
    
    // Load configuration data for this data accessor
    private static final Properties endpoints = new Properties();

    static {
        try (final InputStream stream = Endpoints.class.getClassLoader()
                .getResourceAsStream("endpoints.properties")) {
            endpoints.load(stream);
            System.out.println("Service endpoints configuration loaded: " + endpoints);
        } catch (Exception ex) {
            System.out.println("Service endpoints configuration not found.");
            throw new RuntimeException(ex);
        }
    }

    /**
     * @return product llst service endpoint URL
     */
    public static String getProductListEndpoint() {
        return endpoints.getProperty("product_list");
    }

    /**
     * @return product details service endpoint URL
     */
    public static String getProductDetailsEndpoint() {
        return endpoints.getProperty("product_details");
    }    

    /**
     * @return product details service endpoint URL
     */
    public static String getLogoutEndpoint() {
        return endpoints.getProperty("logout");
    }    
}

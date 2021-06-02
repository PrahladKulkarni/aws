package com.aws.vokunev.catalog.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.aws.util.Endpoints;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for Endpoints")
public class EndpointsTest {

    /**
     * Initializing variables before we run the tests. Use @BeforeAll for
     * initializing static variables at the start of the test class execution.
     * Use @BeforeEach for initializing variables before each test is run.
     */
    @BeforeAll
    static void setup() {
    }

    /**
     * De-initializing variables after we run the tests. Use @AfterAll for
     * de-initializing static variables at the end of the test class execution.
     * Use @AfterEach for de-initializing variables at the end of each test.
     */
    @AfterAll
    static void tearDown() {
    }

    @Test
    @DisplayName("Test for retrieving product list service endpoint")
    void testProductListEndpoint() {
        String endpoint = Endpoints.getProductListEndpoint();
        assertNotNull(endpoint);
    }

    @Test
    @DisplayName("Test for retrieving product details service endpoint")
    void testProductDetailsEndpoint() {
        String endpoint = Endpoints.getProductDetailsEndpoint();
        assertNotNull(endpoint);
    }

    @Test
    @DisplayName("Test for retrieving logout service endpoint")
    void testLogoutEndpoint() {
        String endpoint = Endpoints.getLogoutEndpoint();
        assertNotNull(endpoint);
    }    
}
package com.aws.vokunev.catalog.data;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for ProductCatalogAccessor")
public class ProductCatalogAccessorTest {

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

    /**
     * Basic test to verify the result obtained when calling
     * {@link HelloWorldController#helloWorld} successfully.
     * 
     * @param <CatalogItem>
     */
    @Test
    @DisplayName("Test for retrieving the data")
    void testDataAccess() {
        ArrayList<CatalogItem> catalog = ProductCatalogAccessor.getProductCatalog();
        assertNotNull(catalog);
        assertTrue(catalog.size() > 0);
    }
}
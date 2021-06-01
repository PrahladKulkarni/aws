package com.aws.vokunev.catalog.dao;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.aws.vokunev.catalog.model.CatalogItem;
import com.aws.vokunev.catalog.model.Product;
import com.aws.vokunev.catalog.model.dao.ProductDataAccessor;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for ProductCatalogAccessor")
public class ProductDataAccessorTest {

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
    @DisplayName("Test for retrieving product catalog data")
    void testDataAccessor() {
        List<CatalogItem> catalog = ProductDataAccessor.getProductCatalog();
        assertNotNull(catalog);
        assertTrue(catalog.size() > 0);
    }

    @Test
    @DisplayName("Test for retrieving product object")
    void testExistingProduct() {
        // Product id -1 is a special test case, the data accessor retrieves the first available product
        Product product = ProductDataAccessor.getProduct(-1);
        assertNotNull(product);
    }    

    @Test
    @DisplayName("Test for retrieving product object")
    void testNonexistingProduct() {
        try {
            Product product = ProductDataAccessor.getProduct(-100);
            assertNull(product);
        } catch (Exception ex) {}
    }    
}
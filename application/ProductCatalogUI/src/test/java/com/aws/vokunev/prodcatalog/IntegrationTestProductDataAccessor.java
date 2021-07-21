package com.aws.vokunev.prodcatalog;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.aws.vokunev.prodcatalog.dao.ApplicationConfigurationAccessor;
import com.aws.vokunev.prodcatalog.dao.ProductDataAccessor;
import com.aws.vokunev.prodcatalog.model.ApplicationConfiguration;
import com.aws.vokunev.prodcatalog.model.CatalogItem;
import com.aws.vokunev.prodcatalog.model.Product;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
// We use @TestInstance(Lifecycle.PER_CLASS) annotation to be able to use the
// non static @BeforeAll and
// @AfterAll methods. More on this:
// https://www.baeldung.com/junit-testinstance-annotation
@TestInstance(Lifecycle.PER_CLASS)
public class IntegrationTestProductDataAccessor {

    @Autowired
    private ApplicationConfigurationAccessor configurationAccessor;

    @Autowired
    private ProductDataAccessor productDataAccessor;

    private static ApplicationConfiguration config;

    @BeforeAll
    void setup() {
        config = configurationAccessor.getConfiguration();
    }

    @AfterAll
    void tearDown() {
        config = null;
    }

    @Test
    @DisplayName("Test for retrieving product list")
    void testRetrieveProductList() {
        List<CatalogItem> catalog = productDataAccessor.getProductCatalog(config.getServiceEndpointProductList());
        assertNotNull(catalog);
        assertTrue(catalog.size() > 0);
    }

    @Test
    @DisplayName("Test for retrieving existing product")
    void testRetrieveExistingProduct() {
        // Product id -1 is a special test case, the data accessor retrieves the first
        // available product
        Product product = productDataAccessor.getProduct(config.getServiceEndpointProductDetails(), -1);
        assertNotNull(product);
    }

    @Test
    @DisplayName("Test for retrieving non existing product")
    void testRetrieveNonExistingProduct() {
        try {
            Product product = productDataAccessor.getProduct(config.getServiceEndpointProductDetails(), -100);
            assertNull(product);
        } catch (Exception ex) {
        }
    }
}
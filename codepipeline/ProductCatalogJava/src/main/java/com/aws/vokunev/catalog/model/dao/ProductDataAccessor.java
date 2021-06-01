package com.aws.vokunev.catalog.model.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.aws.vokunev.catalog.model.CatalogItem;
import com.aws.vokunev.catalog.model.Product;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

/**
 * This class implements a DAO patetrn for accessing product data.
 */
public class ProductDataAccessor extends APIDataAccessor {

    // Load configuration data for this data accessor
    private static final Properties endpoints = new Properties();

    static {
        try (final InputStream stream = ProductDataAccessor.class.getClassLoader()
                .getResourceAsStream("endpoints.properties")) {
            endpoints.load(stream);
            System.out.println("Service endpoints configuration loaded: " + endpoints);
        } catch (Exception ex) {
            System.out.println("Service endpoints configuration not found.");
            throw new RuntimeException(ex);
        }
    }

    /**
     * This method populates a list of {@link CatalogItem} objects from
     * ProductCatalog table.
     * 
     * @return a list of {@link CatalogItem} objects
     */
    public static List<CatalogItem> getProductCatalog() {

        // Prepare the request
        String requestUrl = endpoints.getProperty("product_list");
        // Invoke the API
        String result = invokeGetAPIRequest(requestUrl);
        // Process the results
        DocumentContext context = JsonPath.parse(result);
        int totalProducts = context.read("$.Products.length()");
        // Populate Product Catalog list from the database records
        List<CatalogItem> catalog = new ArrayList<CatalogItem>(totalProducts);
        for (int i = 0; i < totalProducts; i++) {
            CatalogItem item = new CatalogItem();
            item.setId(context.read(String.format("$.Products[%s].Id", i)));
            item.setQty(context.read(String.format("$.Products[%s].Qty", i)));
            item.setYear(context.read(String.format("$.Products[%s].Year", i), Integer.class));
            item.setTitle(context.read(String.format("$.Products[%s].Title", i)));
            item.setProductCategory(context.read(String.format("$.Products[%s].ProductCategory", i)));
            item.setPrice(context.read(String.format("$.Products[%s].Price", i), Double.class));
            try {
                item.setOldPrice(context.read(String.format("$.Products[%s].OldPrice", i), Double.class));
            } catch (com.jayway.jsonpath.PathNotFoundException ex) {
                // The old price attribute is not available for this product, just skip
            }

            catalog.add(item);
        }

        // Order the items since the table scan perform no ordering
        Collections.sort(catalog);

        return catalog;
    }

    /**
     * This method fetches a Product for the provided ID.
     * 
     * @param productId product ID
     * @return an instance of a {@link Product} for the provided ID.
     */
    public static Product getProduct(int productId) {

        // Prepare the request
        String requestUrlTemplate = endpoints.getProperty("product_details").concat("?id=%s");
        String requestUrl = String.format(requestUrlTemplate, productId);
        // Invoke the API
        String result = invokeGetAPIRequest(requestUrl);
        // Process the results
        DocumentContext context = JsonPath.parse(result);
        // Capture the core properties
        Product product = new Product();
        product.setId(context.read("$.Id", Integer.class));
        product.setYear(context.read("$.Year", Integer.class));
        product.setDescription(context.read("$.Description"));
        product.setProductCategory(context.read("$.ProductCategory"));
        product.setTitle(context.read("$.Title"));
        product.setImage(context.read("$.Image"));
        product.setPrice(context.read("$.Price", Double.class));
        product.setId(context.read("$.Id", Integer.class));
        // Capture the additional properties
        LinkedHashMap<String, Object> props = JsonPath.parse(result).read("$");
        for (Map.Entry<String, Object> entry : props.entrySet()) {
            product.addProperty(entry.getKey(), String.valueOf(entry.getValue()));
        }

        return product;
    }
}
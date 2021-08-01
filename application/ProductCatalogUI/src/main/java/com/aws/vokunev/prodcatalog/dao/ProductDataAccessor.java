package com.aws.vokunev.prodcatalog.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.aws.vokunev.prodcatalog.model.CatalogItem;
import com.aws.vokunev.prodcatalog.model.Product;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.springframework.stereotype.Component;

/**
 * This class implements a DAO patetrn for accessing the product data.
 */
@Component
public class ProductDataAccessor extends APIDataAccessor {

    /**
     * This method fetches a list of {@link CatalogItem} objects from from the
     * provided service endpoint.
     * 
     * @return a list of {@link CatalogItem} objects
     */
    public List<CatalogItem> getProductCatalog(String serviceEndpointURL, String apiKey) throws IOException {

        // Invoke the API
        String result = invokeGetAPIRequest(serviceEndpointURL, apiKey);

        if (result == null) {
            // The product list is not available
            return null;
        }

        return getProductCatalog(result);
    }

    /**
     * Parses JSON document representing a list of product catalog items and creates
     * list of {@link CatalogItem} objects.
     * 
     * @param productListData JSON document representing a list of product catalog
     *                        items.
     * @return a list of {@link CatalogItem} objects
     */
    public List<CatalogItem> getProductCatalog(String productListData) {

        // Parse the JSON document
        DocumentContext context = JsonPath.parse(productListData);
        int totalProducts = context.read("$.Products.length()");

        // Populate Product Catalog list from the database records
        List<CatalogItem> catalog = new ArrayList<CatalogItem>(totalProducts);
        for (int i = 0; i < totalProducts; i++) {
            CatalogItem item = new CatalogItem();
            item.setId(context.read(String.format("$.Products[%s].Id", i)));
            item.setQty(context.read(String.format("$.Products[%s].Qty", i)));
            item.setYear(context.read(String.format("$.Products[%s].Year", i), Integer.class));
            item.setTitle(context.read(String.format("$.Products[%s].Title", i)));
            item.setDescription(context.read(String.format("$.Products[%s].Description", i)));
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
     * This method fetches a {@link Product} for the provided ID from the provided
     * service endpoint.
     * 
     * @param productId product ID
     * @return an instance of a {@link Product} for the provided ID or null if not
     *         found.
     */
    public Product getProduct(String serviceEndpointURL, String apiKey, int productId) throws IOException {

        // Prepare the request
        String requestUrlTemplate = serviceEndpointURL.concat("?id=%s");
        String requestUrl = String.format(requestUrlTemplate, productId);

        // Invoke the API
        String result = invokeGetAPIRequest(requestUrl, apiKey);

        if (result == null) {
            // The product is not available
            return null;
        }

        return getProduct(result);
    }

    /**
     * Parses JSON document representing a single product from the product catalog
     * and creates an instance of a {@link Product} object.
     * 
     * @param productData JSON document representing a single product from the
     *                    product catalog
     * @return Instance of {@link Product} object
     */
    public Product getProduct(String productData) {

        // Parse the JSON document
        DocumentContext context = JsonPath.parse(productData);

        // Capture the core properties
        Product product = new Product();
        product.setId(context.read("$.Id", Integer.class));
        product.setYear(context.read("$.Year", Integer.class));
        product.setDescription(context.read("$.Description"));
        product.setProductCategory(context.read("$.ProductCategory"));
        product.setTitle(context.read("$.Title"));
        product.setImage(context.read("$.Image"));
        product.setPrice(context.read("$.Price", Double.class));
        product.setQty(context.read("$.Qty", Integer.class));
        product.setId(context.read("$.Id", Integer.class));

        // Capture the additional properties
        LinkedHashMap<String, Object> props = JsonPath.parse(productData).read("$");
        for (Map.Entry<String, Object> entry : props.entrySet()) {
            product.addProperty(entry.getKey(), String.valueOf(entry.getValue()));
        }

        return product;

    }
}
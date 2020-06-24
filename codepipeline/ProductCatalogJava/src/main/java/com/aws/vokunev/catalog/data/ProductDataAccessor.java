package com.aws.vokunev.catalog.data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.jayway.jsonpath.JsonPath;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * This class implements a DAO patetrn for accessing the data.
 */
public class ProductDataAccessor {

    /**
     * This method populates a list of {@link CatalogItem} objects from
     * ProductCatalog table.
     * 
     * @return a list of {@link CatalogItem} objects
     */
    public static List<CatalogItem> getProductCatalog() {

        // Get DynamoDB table reference
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable("ProductCatalog");

        // Fetch the records from DynamoDB table
        ItemCollection<ScanOutcome> items = table.scan();

        // Populate Product Catalog list from the database records
        List<CatalogItem> catalog = new ArrayList<CatalogItem>();
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {

            CatalogItem item = new CatalogItem();

            Item record = iterator.next();
            item.setId(record.getInt("Id"));
            item.setTitle(record.getString("Title"));
            item.setDescription(record.getString("Description"));
            item.setProductCategory(record.getString("ProductCategory"));
            item.setYear(record.getInt("Year"));
            item.setImage(record.getString("Image"));
            item.setPrice(record.getFloat("Price"));
            item.setOldPrice(getOldPrice(record.getInt("Id")));
            catalog.add(item);

            // Log the created item
            System.out.println(item);
        }

        // Order the items since the table scan perform no ordering
        Collections.sort(catalog);

        return catalog;
    }

    /**
     * This method returns an old price from a recent product price update event.
     * @param productId product ID
     * @return the old product price if the price update took place recently, otherwise -1.
     */
    public static float getOldPrice(int productId) {

        // Get DynamoDB table reference
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable("ProductPriceUpdates");
        // Fetch an item for the provided product ID
        Item item = table.getItem("Id", productId);
        if(item != null) {
            System.out.println("Price Update Item: " + item);
            long now = Instant.now().getEpochSecond();
            long expiration = item.getLong("Expires");
            System.out.println(String.format("The item expires on %d and the current time is %d. Has it expired? %s", expiration, now, now > expiration));
            if(now > expiration) {
                // The item TTL has expired, for the best practice, see:
                // https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/howitworks-ttl.html
                return -1f;
            } else {
                return item.getFloat("OldPrice");
            }
        } else {
            return -1f;
        }
    }

    /**
     * This method fetches a Product for the provided ID.
     * @param productId product ID
     * @return an instance of a {@link Product} for the provided ID.
     */
    public static Product getProduct(int productId) {

        String result = invokeProductAPI("https://hygnft82o0.execute-api.us-west-2.amazonaws.com/dev/product/details", productId);

        try {
            String error = JsonPath.read(result, "$.errorMessage");
            throw new RuntimeException(error);
        } catch (com.jayway.jsonpath.PathNotFoundException ex) {
            // No error was returned, which is good, we can continue
        }

        // Attempt to parse the content
        String year = JsonPath.read(result, "$.Year");
        String description = JsonPath.read(result, "$.Description");
        String productCategory = JsonPath.read(result, "$.ProductCategory");
        String title = JsonPath.read(result, "$.Title");
        String image = JsonPath.read(result, "$.Image");
        double price = JsonPath.read(result, "$.Price");
        double id = JsonPath.read(result, "$.Id");

        Product product = new Product();
        // Capture the core properties
        product.setYear(Integer.parseInt(year));
        product.setDescription(description);
        product.setProductCategory(productCategory);
        product.setTitle(title);
        product.setImage(image);
        product.setPrice((float)price);
        product.setId((int)id);
        // Capture the additional properties
        LinkedHashMap<String, Object> props = JsonPath.parse(result).read("$");
        for (Map.Entry<String, Object> entry : props.entrySet()) {
            product.addProperty(entry.getKey(), String.valueOf(entry.getValue()));
        }
        System.out.println(product);

        return product;
    }

    /**
     * Invokes Product API from given endpoint with given product ID.
     * @return parseable response content.
     */
    private static String invokeProductAPI(String apiEndpoint, int productId) {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        String requestUrlTemplate = apiEndpoint.concat("?id=%s");

        HttpGet request = new HttpGet(String.format(requestUrlTemplate, productId));

        try {
            // Send Get request 
            CloseableHttpResponse response = httpClient.execute(request);

            // Log the HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            // Process the response
            String result = EntityUtils.toString(response.getEntity());
            if (result == null) {
                throw new RuntimeException("Unexpected null value for API response entity.");
            } 
            return result;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
package com.aws.vokunev.catalog.data;

import java.util.ArrayList;
import java.util.Iterator;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

/**
 * This data accessor populates a list of {@link CatalogItem} objects.
 * 
 * If the environment variable AWS_DYNAMODB_LOCAL equals true, a local DynamoDB
 * table will be used as a data source, otherwise - remote.
 */
public class ProductCatalogAccessor {

    static String tableName = "ProductCatalog";

    public static ArrayList<CatalogItem> getProductCatalog() {

        // Get a reference to the DynamoDB table
        AmazonDynamoDB client = DynamoDBClientFactory.createClient();
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable(tableName);

        // Fetch the records from DynamoDB table
        ItemCollection<ScanOutcome> items = table.scan();

        // Populate Product Catalog list from the database records
        ArrayList<CatalogItem> catalog = new ArrayList<CatalogItem>();
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
            catalog.add(item);

            // Log the created item
            System.out.println(item);
        }

        return catalog;
    }
}
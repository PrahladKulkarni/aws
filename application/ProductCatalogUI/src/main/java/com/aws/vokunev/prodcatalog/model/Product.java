package com.aws.vokunev.prodcatalog.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * An object of this type represents a Product. Each product has a set of core properties accessible via
 * setters and getters. The product may also have other arbitrary properties accessible via
 * addProperty/getProperties method. 
 */ 
public class Product extends CatalogItem {

    private HashMap<String, String> properties = new HashMap<String, String>();

    public Product() {
    }

    public Product(int year, String description, String productCategory, String title, String image, float price, int qty, int id) {
        super(year, description, productCategory, title, image, price, qty, id);
    }

    public void addProperty(String key, String value) {
        // perform case-insensitive match against the core properties
        if (key.matches("(?i:year|description|productCategory|title|image|bucket|qty|price|id)")) {
            // skip the property
            return;
        }
        // add a non-core property
        properties.put(key, value);
    }

    /*
     * Returns complementary properties, the core properties are not included.
     */ 
    public Set<Map.Entry<String,String>> getProperties() {
        return properties.entrySet();
    }

    @Override
    public String toString() {
        return super.toString() + ", Product " + properties;
    }
}
package com.aws.vokunev.catalog.data;

/**
 * This class represents a catalog item with the common set of properties.
 */
public class CatalogItem implements Comparable<CatalogItem>{

    private int year;
    private String description;
    private String productCategory;
    private String title;
    private String image;
    private float price;
    private float oldPrice = -1.0f;
    private int id;

    public CatalogItem() {
    }

    public CatalogItem(int year, String description, String productCategory, String title, String image, float price, int id) {
        this.year = year;
        this.description = description;
        this.productCategory = productCategory;
        this.title = title;
        this.image = image;
        this.price = price;
        this.id = id;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductCategory() {
        return this.productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CatalogItem year(int year) {
        this.year = year;
        return this;
    }

    public CatalogItem description(String description) {
        this.description = description;
        return this;
    }

    public CatalogItem productCategory(String productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public CatalogItem title(String title) {
        this.title = title;
        return this;
    }

    public CatalogItem image(String image) {
        this.image = image;
        return this;
    }

    public CatalogItem price(float price) {
        this.price = price;
        return this;
    }

    public CatalogItem id(int id) {
        this.id = id;
        return this;
    }

    public float getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(float oldPrice) {
        this.oldPrice = oldPrice;
    }
    
    @Override
    public String toString() {
        return "CatalogItem {" +
            " year='" + getYear() + "'" +
            ", description='" + getDescription() + "'" +
            ", productCategory='" + getProductCategory() + "'" +
            ", title='" + getTitle() + "'" +
            ", image='" + getImage() + "'" +
            ", price='" + getPrice() + "'" +
            ", oldPrice='" + getOldPrice() + "'" +
            ", id='" + getId() + "'" +
            "}";
    }

    @Override
    public int compareTo(CatalogItem otherItem) {
        return this.id > otherItem.getId() ? 1 : this.id < otherItem.getId() ? -1 : 0;
    }
}
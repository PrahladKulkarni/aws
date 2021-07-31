package com.aws.vokunev.prodcatalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aws.vokunev.prodcatalog.dao.ProductDataAccessor;
import com.aws.vokunev.prodcatalog.model.CatalogItem;
import com.aws.vokunev.prodcatalog.model.Product;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UnitTestProductDataAccessor {

    @Autowired
    private ProductDataAccessor productDataAccessor;

    @Test
    @DisplayName("Test for parsing product list JSON data")
    void testParseProductList() throws Exception {

        // Load test JSON data from a file
        String json = IOUtils.toString(this.getClass().getResourceAsStream("productListTestData.json"), "UTF-8");

        // Parse JSON to a list of CatalogItem objects
        List<CatalogItem> productList = productDataAccessor.getProductCatalog(json);
        assertNotNull(productList);

        // Test the number of items
        assertEquals(productList.size(), 17);
        // Test the first item from the list
        CatalogItem item = productList.get(0);
        assertNotNull(item);
        // Test each property of the item
        assertEquals(item.getYear(), 2017);
        assertEquals(item.getDescription(),
                "If your life unravelled would you sink or swim? Jono Dunnett hit rock bottom. Then he launched into stormy waters to follow his dream - by his own estimation a foolish ambition \u2013 to windsurf round Britain. This powerful account charts the highs and lows of risking all, and the consequences of doing so.");
        assertEquals(item.getProductCategory(), "Book");
        assertEquals(item.getTitle(), "Long Standing Ambition: the first solo round Britain windsurf");
        assertEquals(item.getQty(), 450);
        assertEquals(item.getPrice(), 18.99);
        assertEquals(item.getId(), 200);
    }

    @Test
    @DisplayName("Test for parsing product details JSON data")
    void testParseProductDetails() throws Exception {

        // Load test JSON data from a file
        String json = IOUtils.toString(this.getClass().getResourceAsStream("productDetailsTestData.json"), "UTF-8");

        // Parse JSON to a Product object
        Product product = productDataAccessor.getProduct(json);
        assertNotNull(product);

        // Test core Product properties
        assertEquals(product.getYear(), 2017);
        assertEquals(product.getDescription(),
                "If your life unravelled would you sink or swim? Jono Dunnett hit rock bottom. Then he launched into stormy waters to follow his dream - by his own estimation a foolish ambition \u2013 to windsurf round Britain. This powerful account charts the highs and lows of risking all, and the consequences of doing so.");
        assertEquals(product.getProductCategory(), "Book");
        assertEquals(product.getTitle(), "Long Standing Ambition: the first solo round Britain windsurf");
        assertEquals(product.getImage(),
                "https://s3.us-west-2.amazonaws.com/product-catalog-images/long_standing_ambition.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=ASIA2CHRLHGXJJ2L5GWD%2F20210731%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210731T060957Z&X-Amz-Expires=30&X-Amz-SignedHeaders=host&X-Amz-Security-Token=IQoJb3JpZ2luX2VjEK3%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaCXVzLXdlc3QtMiJHMEUCIQCZiXKHaNlqC%2BS82%2BS%2FS17CaUufEwxVfDma8mXROW%2B44gIgYwM6n%2FALr3IZ5xljeF0yOkzp7nt7mP%2BzOUe7%2FZShrEwqpwIItv%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FARACGgw2OTE5OTU4ODM5NTAiDM7ZpbpmiSOtjnxmeCr7AVH2weiB4QM%2Bbb7Xemj7cAaRAFbEd6zeIRm519XbUcupPj2wtBdSLz8vi2ojlKveIKMMbIDORRQJRMz9SAV0McIG5E6CfGuwpLzLEDKKy5%2BosxKrbX42vobPq0qUf2h06Jrcj%2B97Gqp9qGfK3WChdYdEvVH0yGrY3nSHCQY%2Fsx79OXTI38ApbiGT7JvpgPEkvz%2FCXe7p22aquIsZJC7Hygg9tLq%2Fa6gWSwhdwMNRgAdSNDfnYo7CVPnMp69JZadtiNGe87lb60DA%2FZJTx8%2B9lxIDmyWjioVK4ktKgh2VF4rkfcpD3raBP5ejIIpyJs2oCXDcFqHnX5OGYT7dMOShk4gGOpoBDUmreiRVhE1M%2FXzw%2FG2Ne3oSNbbHAvTjFp6nLyprKS6sPBHVarDsYy66um3kVW4ZMfaq65GWqMmMxY9%2BjzpIwtFor%2FUlii8ETgiytalghm3U2FgRGu2TJZU%2Fbx%2F7JauNPWpoA385nW5MgyNVbWeitQdooISWEhGn9dUOVN6l1aZ4p9IVsbjOFYkJwff%2FrSnNvplsWFp6N5JqiA%3D%3D&X-Amz-Signature=c8c6c8c50e999fe1208e1906e366148e775e65a25505f1c3306206c0cf80347c");
        assertEquals(product.getQty(), 450);
        assertEquals(product.getPrice(), 18.99);
        assertEquals(product.getId(), 200);

        // Test the additional properties
        Set<Map.Entry<String, String>> productProperties = product.getProperties();
        // Test the number of additional properties
        assertEquals(productProperties.size(), 6);
        // Iterate over the additional properties
        for (Map.Entry<String, String> entry : productProperties) {
            switch (entry.getKey()) {
                case "Advanced":
                    assertEquals(entry.getValue(), "true");
                    break;
                case "ISBN-10":
                    assertEquals(entry.getValue(), "0995778205");
                    break;
                case "Dimensions":
                    assertEquals(entry.getValue(), "5.2\" x 0.8\" x 8\"");
                    break;
                case "ISBN-13":
                    assertEquals(entry.getValue(), "978-0995778207");
                    break;
                case "Language":
                    assertEquals(entry.getValue(), "English");
                    break;
                case "Publisher":
                    assertEquals(entry.getValue(), "Jonathan Dunnett (April 1, 2017)");
                    break;
                default:
                    assertEquals(entry.getValue(), null);
                    break;
            }
        }
    }
}

package com.aws.vokunev.prodcatalog.controller;

import java.util.List;

import com.aws.vokunev.prodcatalog.dao.ApplicationConfigurationAccessor;
import com.aws.vokunev.prodcatalog.dao.ProductDataAccessor;
import com.aws.vokunev.prodcatalog.model.AccessToken;
import com.aws.vokunev.prodcatalog.model.ApplicationConfiguration;
import com.aws.vokunev.prodcatalog.model.ApplicationPermissions;
import com.aws.vokunev.prodcatalog.model.CatalogItem;
import com.aws.vokunev.prodcatalog.model.Product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This controller handles requests specific to the Product Catalog business
 * domain.
 */
@Controller
public class ProductCatalogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCatalogController.class);

    @Autowired
    private ProductDataAccessor productDataAccessor;

    @Autowired
    private ApplicationConfigurationAccessor configurationAccessor;

    @GetMapping("/")
    public String productList(@RequestAttribute(name = "token", required = false) AccessToken token, Model model) {

        // Preprocess the request
        ApplicationConfiguration config = preprocess(token, model);

        // Retrieve the list of catalog items
        List<CatalogItem> catalog = productDataAccessor.getProductCatalog(config.getServiceEndpointProductList());
        LOGGER.info("Retrieved {} items", catalog.size());
        // Make the list available to the view
        model.addAttribute("catalog", catalog);

        // return the name of the view file
        return "product_list";
    }

    @GetMapping("/product")
    public String productDetails(@RequestAttribute(name = "token", required = false) AccessToken token,
            @RequestParam(name = "id", required = true) int productId, Model model) {

        // Preprocess the request
        ApplicationConfiguration config = preprocess(token, model);

        // Retrieve a product for the provided id
        Product product = productDataAccessor.getProduct(config.getServiceEndpointProductDetails(), productId);
        LOGGER.info("Retrieved product: {}", product);
        // Make the list available to the view
        model.addAttribute("product", product);

        // return the name of the view file
        return "product_details";
    }

    /**
     * This method performs some common request preprocessing activities.
     */
    private ApplicationConfiguration preprocess(AccessToken token, Model model) {

        // Retrieve current application configuration
        ApplicationConfiguration config = configurationAccessor.getConfiguration();
        if (config == null) {
            throw new RuntimeException("The application configuration is not available.");
        }
        // Make the application configuration available to the view
        model.addAttribute("config", config);

        ApplicationPermissions permissions = new ApplicationPermissions(token, config);
        // Make the application permissions available to the view
        model.addAttribute("permissions", permissions);

        return config;
    }

}

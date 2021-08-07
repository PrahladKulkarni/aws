package com.aws.vokunev.prodcatalog.controller;

import java.util.List;

import com.aws.vokunev.prodcatalog.dao.ApplicationConfigurationAccessor;
import com.aws.vokunev.prodcatalog.dao.ProductDataAccessor;
import com.aws.vokunev.prodcatalog.dao.SecretsAccessor;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

    @Autowired
    private SecretsAccessor secretsAccessor;

    @GetMapping("/")
    public String productList(@RequestAttribute(name = "token", required = false) AccessToken token, Model model)
            throws Exception {

        LOGGER.info("Product list requested");

        // Preprocess the request
        ApplicationConfiguration config = preprocessGetRequest(token, model);

        // Retrieve the list of catalog items
        List<CatalogItem> catalog = productDataAccessor.getProductCatalog(config.getServiceEndpointProductList(),
                config.getApiKey());
        LOGGER.info("Retrieved {} items", catalog.size());

        // Make the list available to the view
        model.addAttribute("catalog", catalog);

        // return the name of the view file
        return "product_list";
    }

    @GetMapping("/product")
    public String productDetails(@RequestAttribute(name = "token", required = false) AccessToken token,
            @RequestParam(name = "id", required = true) int productId,
            @RequestParam(name = "newPrice", required = false) String newPrice, Model model) throws Exception {

        LOGGER.info("Product details requested for Id: {}", productId);

        // Preprocess the request
        ApplicationConfiguration config = preprocessGetRequest(token, model);

        // Retrieve a product for the provided id
        Product product = productDataAccessor.getProduct(config.getServiceEndpointProductDetails(), config.getApiKey(),
                productId);
        LOGGER.info("Retrieved product: {}", product);

        // Make the list available to the view
        model.addAttribute("product", product);

        // Provide indication to the view that the price update has been requested.
        model.addAttribute("newPrice", newPrice);

        // return the name of the view file
        return "product_details";
    }

    @PostMapping("/updatePrice")
    public ModelAndView productPriceUpdate(Product product, ModelMap model) throws Exception {

        LOGGER.info("Price update submitted: {}", product);

        // Preprocess the request
        ApplicationConfiguration config = getApplicationConfiguration();

        // Send product price update request
        productDataAccessor.updatePrice(config.getServiceEndpointProductPriceUpdate(), config.getApiKey(),
                product.getId(), product.getPrice());

        // Redirect to the product details page
        model.addAttribute("id", product.getId());
        model.addAttribute("newPrice", product.getPrice());
        return new ModelAndView("redirect:/product", model);
    }

    /**
     * This method provides current application configuration.
     * 
     * @return
     */
    private ApplicationConfiguration getApplicationConfiguration() {

        // Retrieve current application configuration
        ApplicationConfiguration config = configurationAccessor.getConfiguration();
        if (config == null) {
            throw new RuntimeException("The application configuration is not available.");
        }

        if (config.getApiKeySecret() != null) {
            // The APi invocation requires an API key, which has to be retrieved first
            config.setApiKey(secretsAccessor.getSecret(config.getApiKeySecret(), "apikey"));
        }

        return config;
    }

    /**
     * This method performs some common request preprocessing activities.
     */
    private ApplicationConfiguration preprocessGetRequest(AccessToken token, Model model) {

        // Retrieve current application configuration
        ApplicationConfiguration config = getApplicationConfiguration();

        // Make the application configuration available to the view
        model.addAttribute("config", config);

        // Initialize application permissions based on the combination of the access
        // token and the application configuration data
        ApplicationPermissions permissions = new ApplicationPermissions(token, config);

        // Make the application permissions available to the view
        model.addAttribute("permissions", permissions);

        return config;
    }

}

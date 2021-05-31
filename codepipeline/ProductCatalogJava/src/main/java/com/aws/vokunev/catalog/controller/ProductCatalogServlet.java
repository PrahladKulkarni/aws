package com.aws.vokunev.catalog.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aws.vokunev.catalog.data.CatalogItem;
import com.aws.vokunev.catalog.data.InstanceMetadata;
import com.aws.vokunev.catalog.data.InstanceMetadataAccessor;
import com.aws.vokunev.catalog.data.ProductDataAccessor;

/**
 * A controller for the Product Catalog request
 */
@WebServlet("/")
public class ProductCatalogServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the list of catalog items
        List<CatalogItem> catalog = ProductDataAccessor.getProductCatalog();
        // Retrieve the instance metadata
        InstanceMetadata metadata = InstanceMetadataAccessor.getInstanceMetadata();

        // Make the model available to the view        
        request.setAttribute("catalog", catalog);
        request.setAttribute("metadata", metadata);

        // Forward control to the view
        request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
    }
}
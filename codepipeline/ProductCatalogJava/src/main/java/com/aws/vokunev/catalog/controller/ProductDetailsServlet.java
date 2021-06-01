package com.aws.vokunev.catalog.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aws.vokunev.catalog.dao.ProductDataAccessor;
import com.aws.vokunev.catalog.model.Product;

/**
 * A controller for the Product Details request
 */
@WebServlet("/product")
public class ProductDetailsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the parameter is present
        String param = request.getParameter("id");
        if (param == null ) {
            throw new RuntimeException("Error: missing request parameter \"id\"");
        }
        // Parse the input parameter as int
        int productId = 0;
        try {
            productId = Integer.parseInt(param);
        } catch (java.lang.NumberFormatException ex) {
            throw new RuntimeException("Error: unable to parse value " + param + " as integer.");
        }
        // Retrieve a product for the provided id
        Product product = ProductDataAccessor.getProduct(productId);
        // Make the model available to the view
        request.setAttribute("product", product);
        // Forward control to the view
        request.getRequestDispatcher("/WEB-INF/views/product.jsp").forward(request, response);
    }
}
package com.aws.vokunev.catalog.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aws.vokunev.catalog.data.CatalogItem;
import com.aws.vokunev.catalog.data.ProductCatalogAccessor;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/")
public class ProductCatalogServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the list of catalog items
        ArrayList<CatalogItem> catalog = ProductCatalogAccessor.getProductCatalog();        
        request.setAttribute("catalog", catalog);
        request.setAttribute("myName", "Victor Okunev");
        // Forward control to the view
        request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
    }
}
package com.aws.vokunev.catalog.controller;

import java.io.IOException;
import java.util.List;
import java.util.Base64;

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
        
        // Retrieve authentication claims
        String access_token = request.getHeader("x-amzn-oidc-accesstoken");
        //String access_token = "eyJraWQiOiJsNENVeHFuU2lCYmVrYmJqTG1OSUhpVXdLcCtGODNxV3FqaEJXUnNoRDBJPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI5NzY3Njk1Zi1kY2JmLTQyMzYtYjljNS1lMGY5N2FlNjM5ZWIiLCJjb2duaXRvOmdyb3VwcyI6WyJlbmdpbmVlcnMiLCJtYW5hZ2VycyJdLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6Im9wZW5pZCIsImF1dGhfdGltZSI6MTYyMjUwNTI3NSwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLnVzLXdlc3QtMi5hbWF6b25hd3MuY29tXC91cy13ZXN0LTJfQUZXZ0F5cWlJIiwiZXhwIjoxNjIyNTA4ODc1LCJpYXQiOjE2MjI1MDUyNzUsInZlcnNpb24iOjIsImp0aSI6ImE1MTRjODg5LWU4ZDItNGE5Ni1hYTJiLTY3MjIwNmQ4MjExMyIsImNsaWVudF9pZCI6IjM0ZmFrOTdqcnQyNWY0YnFkdmFkMjhyc2RkIiwidXNlcm5hbWUiOiJpbnN0cnVjdG9yIn0.VMydxjk8gAjPjwS9dzRSX3xhBTe3Glc3EuFU6ZhHG1qn_4VSMzb6u083DwpplqPu_IOzOpLWnWVV4hHrYQOGerjVhxJgpMrrUh8MoAaOzSsUL2OGPFfUKR23VEgXJejZmvmhN-rLOgnAh2f852W1-ZS9MT4xFouG6ChCH3JOG864pfTqIYtChq1D7GoelsY0d1xlO46RxyYVozkBMdYUk6BSsryoazEQztf3slF27FhVO7TIlRzFwGwwZ0JKjYqbShLEKklf1cS3AO0VxddOVBwQ0XA_S82L9d6Ah0wrzugTOjv5MD7hTXaFjr7tfrQ56eg_wvzscTykq5yuxaS9MQ";
        System.out.println("access_token: " + access_token);
        if (access_token != null) {
            byte[] decodedBytes = Base64.getDecoder().decode(access_token);
            String access_token_decoded = new String(decodedBytes);
            System.out.println("access_token-decoded: " + access_token_decoded );
        }

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
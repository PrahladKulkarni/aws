package com.aws.vokunev.catalog.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public class LogOutController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        // Locate the ALB cookie
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie: cookies) {
            if(cookie.getName().startsWith("AWSELBAuthSessionCookie")) {
                System.out.println("Found an ALB cookie: " + cookie.getName() + ", expiring it now!");
                cookie.setMaxAge(-1);
            }
        }

        response.sendRedirect("https://auth.cloud101.link/logout?client_id=34fak97jrt25f4bqdvad28rsdd&logout_uri=https://qa.cloud101.link/prodcatalog/");

    }
}

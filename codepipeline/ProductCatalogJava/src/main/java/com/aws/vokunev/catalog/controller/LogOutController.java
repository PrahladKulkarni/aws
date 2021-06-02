package com.aws.vokunev.catalog.controller;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aws.util.Endpoints;

@WebServlet("/logout")
public class LogOutController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        Cookie cookie1 = new Cookie("AWSELBAuthSessionCookie", "");
        cookie1.setMaxAge(-1);
        cookie1.setPath("/");        
        response.addCookie(cookie1);

        Cookie cookie2 = new Cookie("AWSELBAuthSessionCookie-0", "");
        cookie2.setMaxAge(-1);
        cookie2.setPath("/");
        response.addCookie(cookie2);

        Cookie cookie3 = new Cookie("AWSELBAuthSessionCookie-1", "");
        cookie3.setMaxAge(-1);
        cookie3.setPath("/");
        response.addCookie(cookie3);

        System.out.println("Expiring the ALB cookies...");
        System.out.println(cookie1.getName());
        System.out.println(cookie2.getName());
        System.out.println(cookie3.getName());

        // Redirect to the logout endpoint address
        response.sendRedirect(Endpoints.getLogoutEndpoint());
    }
}

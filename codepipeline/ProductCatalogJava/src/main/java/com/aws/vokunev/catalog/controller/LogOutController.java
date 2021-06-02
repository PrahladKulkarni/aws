package com.aws.vokunev.catalog.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aws.util.Endpoints;

/**
 * This controller handles the logout requests. It performs the logout sequence according to 
 * https://docs.aws.amazon.com/elasticloadbalancing/latest/application/listener-authenticate-users.html
 * and
 * https://docs.aws.amazon.com/cognito/latest/developerguide/logout-endpoint.html
 */
@WebServlet("/logout")
public class LogOutController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Expire the ALB cookies
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

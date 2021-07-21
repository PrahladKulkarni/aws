package com.aws.vokunev.prodcatalog.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.aws.vokunev.prodcatalog.dao.ApplicationConfigurationAccessor;
import com.aws.vokunev.prodcatalog.model.ApplicationConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This controller handles the logout sequence for the Cognito User
 * Pool-authenticated user in accordance with:
 * https://docs.aws.amazon.com/elasticloadbalancing/latest/application/listener-authenticate-users.html#authentication-logout
 */
@Controller
public class LogoutController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutController.class);

    @Autowired
    private ApplicationConfigurationAccessor configurationAccessor;

    @GetMapping("/logout")
    public void logout(HttpServletResponse response) throws ServletException, IOException {

        // Retrieve current application configuration
        ApplicationConfiguration config = configurationAccessor.getConfiguration();
        if (config == null) {
            throw new RuntimeException("The application configuration is not available.");
        }

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

        LOGGER.info("Expiring the ALB cookies...");
        LOGGER.info(cookie1.getName());
        LOGGER.info(cookie2.getName());
        LOGGER.info(cookie3.getName());

        // Redirect to the logout endpoint address
        response.sendRedirect(config.getServiceEndpointLogout());
    }
}

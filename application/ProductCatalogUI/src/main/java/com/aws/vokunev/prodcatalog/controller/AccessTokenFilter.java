package com.aws.vokunev.prodcatalog.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.aws.vokunev.prodcatalog.dao.AccessTokenDataAccessor;
import com.aws.vokunev.prodcatalog.model.AccessToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * This web filter fetches an ALB access token from the request and makes it available
 * from the request attribute "token".
 * 
 * How to Define a Spring Boot Filter: https://www.baeldung.com/spring-boot-add-filter
 */
@Component
@Order(1)
public class AccessTokenFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenFilter.class);

	@Autowired
	private AccessTokenDataAccessor accessTokenDataAccessor;

	/**
	 * Initialize the request scope attributes.
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// retrieve access token
		AccessToken access_token = accessTokenDataAccessor.getToken((HttpServletRequest) request);
		
		LOGGER.info("Access token: {} ", access_token);

		// share the data through the request scope
		request.setAttribute("token", access_token);

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
}

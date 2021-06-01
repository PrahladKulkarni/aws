package com.aws.vokunev.catalog.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.FilterChain;

import com.aws.vokunev.catalog.model.AccessToken;
import com.aws.vokunev.catalog.dao.AccessTokenDataAccessor;

@WebFilter("/AccessTokenFilter")
public class AccessTokenFilter implements Filter {

	private ServletContext context;

	public void init(FilterConfig config) throws ServletException {
		this.context = config.getServletContext();
		this.context.log("AuthenticationFilter initialized");
	}

	/**
	 * Fetches the OIDC access token from the request and makes it available 
	 * as a request attribute "token" 
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// Retrieve access token
		AccessToken access_token = AccessTokenDataAccessor.getToken((HttpServletRequest) request);
		// Make the model available to the view
		((HttpServletRequest) request).setAttribute("token", access_token);
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	public void destroy() {
		// close any resources here
	}
}

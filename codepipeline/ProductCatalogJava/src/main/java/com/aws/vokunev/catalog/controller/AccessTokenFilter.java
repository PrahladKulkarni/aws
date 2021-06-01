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
import com.aws.vokunev.catalog.model.InstanceMetadata;
import com.aws.vokunev.catalog.model.dao.AccessTokenDataAccessor;
import com.aws.vokunev.catalog.model.dao.InstanceMetadataAccessor;

@WebFilter("/AccessTokenFilter")
public class AccessTokenFilter implements Filter {

	private ServletContext context;

	public void init(FilterConfig config) throws ServletException {
		this.context = config.getServletContext();
		this.context.log("AuthenticationFilter initialized");
	}

	/**
	 * Fetches the OIDC access token from the request and makes it available as a
	 * request attribute "token"
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// Retrieve access token
		AccessToken access_token = AccessTokenDataAccessor.getToken((HttpServletRequest) request);
		// Retrieve the instance metadata
		InstanceMetadata metadata = InstanceMetadataAccessor.getInstanceMetadata();

		// Share the data through the request scope
		request.setAttribute("token", access_token);
		request.setAttribute("metadata", metadata);

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	public void destroy() {
		// close any resources here
	}
}

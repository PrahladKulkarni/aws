package com.aws.vokunev.prodcatalog.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.aws.vokunev.prodcatalog.dao.InstanceMetadataAccessor;
import com.aws.vokunev.prodcatalog.model.InstanceMetadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * This web filter fetches an EC2 instance metadata and makes it available
 * from the request attribute "metadata".
 * 
 * How to Define a Spring Boot Filter: https://www.baeldung.com/spring-boot-add-filter
 */
@Component
@Order(2)
public class InstanceMetadataFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(InstanceMetadataFilter.class);

	@Autowired
	private InstanceMetadataAccessor instanceMetadataAccessor;

	/**
	 * Initialize the request scope attributes.
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// fetch the metadata for the authenticated requests only		
		if( request.getAttribute("token") != null ) {
			// retrieve the instance metadata
			InstanceMetadata metadata = instanceMetadataAccessor.getInstanceMetadata();
			LOGGER.info("Instance metadata: {} ", metadata);
			// share the data through the request scope
			request.setAttribute("metadata", metadata);
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
}

package com.aws.vokunev.prodcatalog.controller;

import java.util.UUID;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * This exception handler replaces the default exception handler. It adds a log
 * correlation Id that can be used to locate the detailed exception information
 * in the CloudWatch logs.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception exception) {

        String correlationId = UUID.randomUUID().toString();

        // To make sure that the \n doesn't break the mesage into multiple log messages,
        // set "multi_line_start_pattern": "{datetime_format}" option in the CloudWatch Agent config
        // as per: https://forums.aws.amazon.com/thread.jspa?threadID=158643
        LOGGER.error("\nLog correlation Id: {}\n{}", correlationId, ExceptionUtils.getStackTrace(exception));

        return getModelAndView(exception, correlationId, "error");
    }
    private ModelAndView getModelAndView(Exception ex, String correlationId, String view) {
        ModelAndView mav = new ModelAndView();
        mav.getModel().put("message", ex.getMessage());
        mav.getModel().put("correlationId", correlationId);
        mav.setViewName(view);
        return mav;
    }
}

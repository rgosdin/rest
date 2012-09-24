/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faraox.rest.filter;

import com.faraox.rest.util.CommonUtils;
import java.io.IOException;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author sshami
 */
public class LoggingFilter implements Filter {

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    private static final Logger CLASS_LOGGER = Logger.getLogger(LoggingFilter.class);

    public LoggingFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        CLASS_LOGGER.debug("Pre processing servlet...");
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        CLASS_LOGGER.debug("Post processing servlet...");
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        Date d1 = new Date();
        doBeforeProcessing(request, response);
        try {
            chain.doFilter(request, response);
        } catch (Throwable t) {
            CLASS_LOGGER.error("Exception in servlet. Message: "
                    + t.getMessage(), t);
        }

        doAfterProcessing(request, response);
        Date d2 = new Date();

        CLASS_LOGGER.info("Time taken in getting response: "
                + CommonUtils.getTimeDifference(d1, d2));
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
        CLASS_LOGGER.debug("Destroying filter...");
    }

    /**
     * Init method for this filter
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            CLASS_LOGGER.debug("Initializing filter...");
        }
    }
}

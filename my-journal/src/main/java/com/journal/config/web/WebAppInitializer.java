
package com.journal.config.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.journal.config.app.AppConfiguration;

/**
 * @author Praveen Sivasamy
 *
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(WebAppInitializer.class);

    @Override
    protected Class<?>[] getRootConfigClasses() {
	if (log.isDebugEnabled()) {
	    log.debug("Loading Spring Application Configuration");
	}

	return new Class[] { AppConfiguration.class };
    }

    @Override
    protected String[] getServletMappings() {
	return new String[] { "/" };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
	return null;
    }

}

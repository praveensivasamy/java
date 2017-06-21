package com.journal.config.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;

/**
 * Application Lifecycle Listener implementation class AppListener
 *
 */
@WebListener
public class WebAppListener implements ServletContextListener {

    /**
     * Default constructor.
     */
    public WebAppListener() {

    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent contextDestroyEvent) {
	/**
	 * Removing the logger instance
	 */
	if (LoggerFactory.getILoggerFactory() instanceof LoggerContext) {
	    ((LoggerContext) LoggerFactory.getILoggerFactory()).stop();
	}

    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent contextInitEvent) {
	System.out.println(this.getClass().getSimpleName() + " Initialized ...");
    }

}

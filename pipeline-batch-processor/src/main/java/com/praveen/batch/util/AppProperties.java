package com.praveen.batch.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.praveen.commons.enums.AppExceptionIdentifier;
import com.praveen.commons.exception.ApplicationException;

public class AppProperties {

    private static final Logger log = LoggerFactory.getLogger(AppProperties.class);

    private static AppProperties instance = new AppProperties();
    private String propertyFile = "app.properties";

    private Properties props;

    public static AppProperties instance() {
        if (instance == null) {
            throw ApplicationException.warn("AppProperties not initialised properly.");
        }
        return instance;
    }

    private AppProperties() {
        loadProperties();
        updateProperties();

    }

    /**
     * Load properties from the .properties file
     */
    private void loadProperties() {
        props = new Properties();
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyFile));
        } catch (IOException e) {
            throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION, e);
        }
    }

    /**
     * Override the loaded properties with that specified as system property like -D>property
     */
    private void updateProperties() {
        Enumeration<Object> sysProps = System.getProperties().keys();
        while (sysProps.hasMoreElements()) {
            String key = (String) sysProps.nextElement();
            Object res = props.setProperty(key, System.getProperty(key));
            if (res != null) {
                log.info("Property <{}> overridden by system property", key);
            }
        }
    }

    public String getProperty(String propertyName) {
        return props.getProperty(propertyName);
    }

    public void setProperty(String propertyName, String value) {
        props.setProperty(propertyName, value);
    }

    public int getThreads() {
        return Integer.valueOf(props.getProperty("threads", "1"));
    }

}

package com.praveen.batch.util;

import java.io.IOException;
import java.util.Properties;

import com.praveen.commons.enums.AppExceptionIdentifier;
import com.praveen.commons.exception.ApplicationException;
import com.praveen.commons.exception.ApplicationWarning;

public class AppProperties {

    private static AppProperties instance;
    private String propertyFile = "app.properties";

    private Properties props;

    public static AppProperties getInstance() {
        if (instance == null) {
            throw ApplicationWarning.warn("AppProperties not initialised");
        }
        return instance;
    }

    private AppProperties() {

    }

    private void loadProperties() {
        props = new Properties();
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyFile));
        } catch (IOException e) {
        }

    }

    public static void initialise() {

    }

}

package com.praveen.commons.exception;

import com.praveen.commons.enums.ExceptionIdentifier;

public class ExceptionReport extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected String errorMessage;
    protected String step;
    protected Integer errorCode;
    protected ExceptionIdentifier identifier;

    
    public static ExceptionReporter instance (ExceptionIdentifier identifier){
	return new ExceptionReporter(identifier);
    }
}

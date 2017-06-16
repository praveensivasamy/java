package com.praveen.commons.exception;

import com.praveen.commons.enums.ExceptionIdentifier;
import com.praveen.commons.utils.ToStringUtils;

/**
 * Write the application exception to database
 * 
 * @author Praveen
 *
 */
public class MyCommonsException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected String errorMessage;
    protected String step;
    protected Integer errorCode;
    protected ExceptionIdentifier identifier;// Types of errors

    public static MyAppException instance(ExceptionIdentifier identifier) {
	return new MyAppException(identifier);
    }

    /**
     * Write the exception received as is with {@link ExceptionIdentifier} code
     * 
     * @param identifier
     * @param cause
     * @return
     */
    public static MyAppException instance(ExceptionIdentifier identifier, Exception cause) {
	MyAppException res = new MyAppException(cause);
	res.identifier = identifier;
	res.errorCode = identifier.code();
	res.errorMessage = identifier.message();
	return res;
    }

    /**
     * Write {@link Throwable} received with custom error message
     * 
     * @param errorMessage
     * @param causedException
     */
    protected MyCommonsException(String errorMessage, Throwable causedException) {
	super(causedException);
	this.errorMessage = errorMessage;
	if (causedException != null) {
	    if (this.errorMessage == null) {
		this.errorMessage = causedException.getMessage();
	    }
	    this.setStackTrace(causedException.getStackTrace());
	}
    }

    /**
     * Just log these exceptions
     * 
     * @param causedException
     */
    protected MyCommonsException(Throwable causedException) {
	super(causedException);
    }

    /**
     * Hide the cause and write business understandable message.Usually this should never happen
     * 
     * @param errMsg
     */
    protected MyCommonsException(String errMsg) {
	this.errorMessage = errMsg;
    }

    /**
     * Write the standard business exception with a valid inentifier
     * 
     * @param identifier
     */
    protected MyCommonsException(ExceptionIdentifier identifier) {
	this.identifier = identifier;
	this.errorCode = identifier.code();
	this.errorMessage = identifier.message();
    }

    /**
     * Append step the error occured like login,validation etc.
     * 
     * @param step
     * @return
     */
    public MyAppException step(String step) {
	setStep(step);
	return this;
    }

    /**
     * Add some details to the message place holder in the {@link ExceptionIdentifier}  
     * 
     * @param value
     * @return
     */
    public MyAppException details(Object value) {
	errorMessage = ToStringUtils.replacePlaceholder(errorMessage, "details", value);
	return this;
    }

    public void setErrorCode(Integer errorCode) {
	this.errorCode = errorCode;
    }

    public void setStep(String step) {
	this.step = step;
    }

    public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
	return this.errorMessage;
    }

    @Override
    public String toString() {
	String result = "AppException";

	if (errorCode != null) {
	    result += " - Code: " + errorCode;
	}
	if (errorMessage != null) {
	    result += " - Message: " + errorMessage;
	}
	if (getCause() != null) {
	    result += " - Exception: " + getCause();
	}
	if (step != null) {
	    result += " - Step: " + step;
	}

	return result;
    }

    public static Throwable getRoot(Exception e) {
	Throwable root = e;
	while (root.getCause() != null) {
	    root = root.getCause();
	}
	return root;
    }

    public ExceptionIdentifier getIdentifier() {
	return identifier;
    }

}

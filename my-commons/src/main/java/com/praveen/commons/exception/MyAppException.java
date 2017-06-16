package com.praveen.commons.exception;

import com.praveen.commons.enums.ExceptionIdentifier;
import com.praveen.commons.utils.ToStringUtils;

/**
 * Write the application exception to database
 * 
 * @author Praveen
 *
 */
public class MyAppException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected String errorMessage;
    protected String step;
    protected Integer errorCode;
    protected ExceptionIdentifier identifier;// Types of errors

    public static ApplicationException instance(ExceptionIdentifier identifier) {
	return new ApplicationException(identifier);
    }

    /**
     * Write the exception received as is with {@link ExceptionIdentifier} code
     * 
     * @param identifier
     * @param cause
     * @return
     */
    public static ApplicationException instance(ExceptionIdentifier identifier, Exception cause) {
	ApplicationException res = new ApplicationException(cause);
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
    protected MyAppException(String errorMessage, Throwable causedException) {
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
    protected MyAppException(Throwable causedException) {
	super(causedException);
    }

    /**
     * Hide the cause and write business understandable message.Usually this should never happen
     * 
     * @param errMsg
     */
    protected MyAppException(String errMsg) {
	this.errorMessage = errMsg;
    }

    /**
     * Write the standard business exception with a valid inentifier
     * 
     * @param identifier
     */
    protected MyAppException(ExceptionIdentifier identifier) {
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
    public ApplicationException step(String step) {
	setStep(step);
	return this;
    }

    /**
     * Add some details to the message place holder in the {@link ExceptionIdentifier}  
     * 
     * @param value
     * @return
     */
    public ApplicationException details(Object value) {
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

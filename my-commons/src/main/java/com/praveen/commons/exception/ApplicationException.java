package com.praveen.commons.exception;

import com.praveen.commons.enums.AppExceptionIdentifier;
import com.praveen.commons.utils.ToStringUtils;

/**
 * <pre>
 * This exception handler reports in the format as :
 *
 * IDENTIFIER | MESSAGE | STAGE (optional base exception stacktrace/message)
 *
 * </pre>
 *
 * @author Praveen
 *
 */
public class ApplicationException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected String errorMessage;
	protected String step;
	protected Integer errorCode;
	protected String information;
	protected AppExceptionIdentifier identifier;

	public static ApplicationException instance(AppExceptionIdentifier identifier) {
		return new ApplicationException(identifier);
	}

	/**
	 * Write the exception received as is with {@link ExceptionIdentifier} code
	 *
	 * @param identifier
	 * @param cause
	 * @return
	 */
	public static ApplicationException instance(AppExceptionIdentifier identifier, Exception cause) {
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
	 * @param causedException Actual exception
	 * @return
	 */
	public static ApplicationException instance(String errorMessage, Throwable causedException) {
		ApplicationException res = new ApplicationException(causedException);
		res.errorMessage = errorMessage;
		if (causedException != null) {
			if (res.errorMessage == null) {
				res.errorMessage = causedException.getMessage();
			}
			res.setStackTrace(causedException.getStackTrace());
		}
		return res;
	}

	public static ApplicationException warn(String warning) {
		ApplicationException res = new ApplicationException(AppExceptionIdentifier.TECHNICAL_WARNING);
		res.errorMessage = warning;
		return res;
	}

	private ApplicationException(Throwable causedException) {
		super(causedException);
	}

	/**
	 * Write the standard business exception with a valid indentifier
	 *
	 * @param identifier
	 */
	protected ApplicationException(AppExceptionIdentifier identifier) {
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
	 * Add some details to the message place holder in the
	 * {@link ExceptionIdentifier}
	 *
	 * @param value
	 * @return
	 */
	public ApplicationException details(Object value) {
		errorMessage = ToStringUtils.replacePlaceholder(errorMessage, "details", value);
		return this;
	}

	public void setStep(String step) {
		this.step = step;
	}

	@Override
	public String getMessage() {
		return this.errorMessage;
	}

	@Override
	public String toString() {
		String result = "AppException - ";

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
}

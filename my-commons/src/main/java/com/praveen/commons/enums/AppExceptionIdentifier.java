package com.praveen.commons.enums;

import com.praveen.commons.exception.ExceptionIdentifier;

public enum AppExceptionIdentifier implements ExceptionIdentifier {
	// -------------------------------------------------------------------

	/** Any exception which is totally not relevant to the business */
	TECHNICAL_EXCEPTION(9901, "Unhandled technical exception.{details}");

	// -------------------------------------------------------------------

	String message;
	int code;

	public String message() {
		return message;
	}

	public int code() {
		return code;
	}

	private AppExceptionIdentifier(int code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public int errorcode() {
		return code;
	}

	@Override
	public String errorMessage() {
		return message;

	}

}

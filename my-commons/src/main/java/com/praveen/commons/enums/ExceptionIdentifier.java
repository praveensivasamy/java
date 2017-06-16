package com.praveen.commons.enums;

public enum ExceptionIdentifier {

    /** Any exception which is totally not relevant to the business */
    TECHNICAL_EXCEPTION(9901, "Unhandled technical exception.{details}");

    String message;
    int code;

    public String message() {
	return message;
    }

    public int code() {
	return code;
    }

    private ExceptionIdentifier(int code, String message) {
	this.code = code;
	this.message = message;
    }

}

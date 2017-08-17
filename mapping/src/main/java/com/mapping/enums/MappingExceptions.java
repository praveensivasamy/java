package com.mapping.enums;

import com.praveen.commons.exception.ExceptionIdentifier;

public enum MappingExceptions implements ExceptionIdentifier {

	// @formatter:off
	TECHNICAL_EXCEPTION(9901, "Technical exception.{details}"),
	BUSINESS_EXCEPTION(9902, "Unhandled technical exception.{details}"),
	INPUT_EXCEPTION(1000, "Input exception.{details}"), 
	INVALID_FILE_FORMAT(10001,"File.{details}"), 
	FILE_IO_EXCEPTION(10002,"File opration.{details}")
	;
	// @formatter:on


	String message;
	int code;

	public String message() {
		return message;
	}

	public int code() {
		return code;
	}

	private MappingExceptions(int errCode, String message) {
		this.code = errCode;
		this.message = message;
	}

	@Override
	public String errorMessage() {
		return message;
	}

	@Override
	public int errorcode() {
		return code;
	}
}

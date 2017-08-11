package com.mapping.enums;

public enum MappingExceptions {

	/** */
	TECHNICAL_EXCEPTION(9901, "Unhandled technical exception.{details}"),
	/** */
	BUSINESS_EXCEPTION(9902, "Unhandled technical exception.{details}");

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
}

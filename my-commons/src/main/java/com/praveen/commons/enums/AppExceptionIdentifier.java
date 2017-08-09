package com.praveen.commons.enums;

public enum AppExceptionIdentifier {
	// -------------------------------------------------------------------

	/** Any exception which is totally not relevant to the business */
	TECHNICAL_EXCEPTION(9901, "Unhandled technical exception.{details}"),
	/** */
	MIN_NULL_DAY1(000, "getMin(day1, day2) - day1 is null"),
	/** */
	MIN_NULL_DAY2(000, "getMin(day1, day2) - day2 is null"),
	/** "getMax(day1, day2) - day1 is null" */
	MAX_NULL_DAY1(000, "getMax(day1, day2) - day1 is null"),
	/** "getMax(day1, day2) - day2 is null" */
	MAX_NULL_DAY2(000, "getMax(day1, day2) - day2 is null");

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

}

package com.praveen.commons.enums;

public enum AppExceptionIdentifier {
    // -------------------------------------------------------------------

    /** Any exception which is totally not relevant to the business */
    TECHNICAL_EXCEPTION(9901, "Technical exception. {details}"),
    /** Warn the developer for in correct workflow */
    TECHNICAL_WARNING(9801, "Technical warning");
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

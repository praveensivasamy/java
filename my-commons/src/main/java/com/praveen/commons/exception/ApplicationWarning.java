package com.praveen.commons.exception;

import org.apache.commons.lang.StringUtils;

public class ApplicationWarning extends RuntimeException {

    private static final long serialVersionUID = -351809707274752543L;

    private String warning;

    public static ApplicationWarning warn(String warningMessage) {
        return new ApplicationWarning(warningMessage);
    }

    public ApplicationWarning(String warningMessage) {
        warning = warningMessage;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Application issued Warning : ");
        if (StringUtils.isNotBlank(warning)) {
            result.append("message - ");
            result.append(this.warning);
        }
        return result.toString();
    }

}

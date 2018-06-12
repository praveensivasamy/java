package com.praveen.commons.hibernate;

import com.praveen.commons.utils.ToStringUtils;

public class QueryParameter {

    private String name;
    private Object value;

    public QueryParameter(String name, Object value) {
        super();
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public static QueryParameter newInstance(String name, Object value) {
        return new QueryParameter(name, value);
    }

    @Override
    public String toString() {
        return name + "=" + ToStringUtils.objectToString(value);
    }

}

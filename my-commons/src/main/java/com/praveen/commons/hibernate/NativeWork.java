package com.praveen.commons.hibernate;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.jdbc.Work;

import com.praveen.commons.enums.AppExceptionIdentifier;
import com.praveen.commons.exception.ApplicationException;

/**
 * Default hibernate {@link Work} implementation for calling db functions with variable number of arguments
 * 
 * @author Praveen Sivasamy
 *
 */
class NativeWork implements Work {
    String name;
    /** 0 means no return, e.g. procedure */
    int returnType;
    Object result;
    Object[] arguments;

    public NativeWork(String functionName, int returnType, Object... arguments) {
	this.name = functionName;
	this.returnType = returnType;
	this.arguments = arguments;
    }

    public NativeWork(String procedureName, Object... arguments) {
	this.name = procedureName;
	this.returnType = 0;
	this.arguments = arguments;
    }

    @Override
    public void execute(Connection connection) throws SQLException {
	String argsList = getArgsList(arguments);
	CallableStatement call;
	int paramIndex;
	if (returnType != 0) {
	    call = connection.prepareCall("{ ? = call " + name + argsList + " }");
	    call.registerOutParameter(1, returnType);
	    paramIndex = 2;
	} else {
	    call = connection.prepareCall("{call " + name + argsList + " }");
	    paramIndex = 1;
	}
	for (Object argument : arguments) {
	    if (argument == null) {
		call.setObject(paramIndex, null);
	    } else if (argument instanceof String) {
		call.setString(paramIndex, (String) argument);
	    } else if (argument instanceof Number) {
		call.setBigDecimal(paramIndex, new BigDecimal(String.valueOf(argument)));
	    } else if (argument instanceof Date) {
		call.setDate(paramIndex, new java.sql.Date(((Date) argument).getTime()));
	    } else {
		throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION).details(
			"Unsuported IN argument type for argument: " + argument + " at position " + (paramIndex - 1) + " and function " + name);
	    }
	    paramIndex++;
	}
	call.execute();
	if (returnType != 0) {
	    result = call.getObject(1);
	}
    }

    /**
     * @return (?,? ...?) or empty string if arguments is <code>null</code> or empty
     */
    private String getArgsList(Object[] arguments) {
	if (arguments == null || arguments.length == 0) {
	    return "";
	}
	return "(" + StringUtils.repeat("?", ",", arguments.length) + ")";
    }

    public Object getResult() {
	return result;
    }
}
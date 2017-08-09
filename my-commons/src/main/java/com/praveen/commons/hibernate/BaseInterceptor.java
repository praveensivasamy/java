package com.praveen.commons.hibernate;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class BaseInterceptor extends EmptyInterceptor {

	public BaseInterceptor() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		return super.onSave(entity, id, state, propertyNames, types);
	}

	@Override
	public String onPrepareStatement(String sql) {
		return super.onPrepareStatement(sql);
	}

	@Override
	public String toString() {
		return "BASE INTERCEPTOR";
	}

}

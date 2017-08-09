package com.praveen.commons.hibernate;

import org.hibernate.type.Type;

import com.praveen.commons.utils.ToStringUtils;

/**
 * Map colum alias of query with bean for {@link ResultTransformer}
 * 
 * @author Praveen Sivasamy
 *
 */
public class ColumnAliasMapping {
	private String aliasName;
	private Type columnType;

	public ColumnAliasMapping(String aliasName, Type columnType) {
		super();
		this.aliasName = aliasName;
		this.columnType = columnType;
	}

	public String getAliasName() {
		return aliasName;
	}

	public Type getColumnType() {
		return columnType;
	}

	public static ColumnAliasMapping newInstance(String aliasName, Type columnType) {
		return new ColumnAliasMapping(aliasName, columnType);
	}

	@Override
	public String toString() {
		return aliasName + "=" + ToStringUtils.output(columnType);
	}

}
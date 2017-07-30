/**
 * 
 */
package com.mapping.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Praveen Sivasamy
 *
 */
public enum TrimatrixColumn {

    //
    MAPPED_CUSTOMER(5),
    //
    ROW_TYPE(8),
    //
    CONSOLIDATED_BILLING_NUMBER(10),
    //
    INVOICE_NUMBER(11),
    //
    INVOICE_DATE(12),
    //
    INVOICE_CURRENCY(13),
    //
    OPEN_AMOUNT(14),
    //
    OUTSTANDING_DAYS(17),
    //
    AGE_BUCKET(21),
    //
    WON(23),
    //
    PROJECT_NAME(25),
    //
    INVALID(-1);

    private int columnIndex;

    private TrimatrixColumn(int columnIndex) {
	this.columnIndex = columnIndex;
    }

    private static final Map<Integer, TrimatrixColumn> _map = new HashMap<Integer, TrimatrixColumn>();

    static {
	for (TrimatrixColumn column : TrimatrixColumn.values()) {
	    _map.put(column.columnIndex, column);
	}
    }

    public static TrimatrixColumn from(int cellIndex) {
	TrimatrixColumn res = _map.get(cellIndex);
	return res == null ? INVALID : res;
    }

}

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
public enum CollectionColumn {

	//
	CUSTOMER_NAME(3),
	//
	RECEIPT_NUMBER(5),
	//
	RECEIPT_DATE(6),
	//
	RECEIPT_CURRENCY(7),
	//
	ALLOCATED_AMOUNT(16),
	//
	RECEIVED_AMOUNT(17),
	//
	DATE_APPLED(19),
	//
	INVOICE_NUMBER(20),
	//
	INVOICE_DATE(21),
	//
	WON(22),
	//
	INVOICE_CURRENCY(23),
	//
	APPLIED_INVOICE_AMOUNT(24),
	//
	APPLIED_RECEIPT_AMOUNT(26),
	//
	UNAPPLIED_RECEIPT_AMOUNT(30),
	//
	COMMENTS(33),
	//
	CONTRACT_ID(61),
	//
	INVALID(-1);

	private int columnIndex;

	private CollectionColumn(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	private static final Map<Integer, CollectionColumn> _map = new HashMap<>();

	static {
		for (CollectionColumn column : CollectionColumn.values()) {
			_map.put(column.columnIndex, column);
		}
	}

	public static CollectionColumn from(int cellIndex) {
		CollectionColumn res = _map.get(cellIndex);
		return res == null ? INVALID : res;
	}

}

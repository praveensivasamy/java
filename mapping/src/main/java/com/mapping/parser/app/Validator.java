/**
 *
 */
package com.mapping.parser.app;

import org.apache.poi.ss.usermodel.Row;

/**
 * @author Praveen Sivasamy
 *
 */
public class Validator {

	/**
	 * 
	 * @param row
	 * @return
	 */
	public static boolean validateTrimatrixTemplate(Row row) {

		return row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue().contains("Set of Books Name") &&
				row.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue().contains("Mapped Customer Name") &&
				row.getCell(8, Row.CREATE_NULL_AS_BLANK).getStringCellValue().contains("Class") &&
				row.getCell(10, Row.CREATE_NULL_AS_BLANK).getStringCellValue().contains("Consolidated Billing Number") &&
				row.getCell(11, Row.CREATE_NULL_AS_BLANK).getStringCellValue().contains("Invoice Number");

	}

}

/**
 *
 */
package com.mapping.commons;

import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Praveen Sivasamy
 *
 */
public class Validator {

	private static final Logger log = LoggerFactory.getLogger(Validator.class);

	public static boolean validateTrimatrixTemplate(Row row) {

		if (log.isDebugEnabled()) {
			log.debug("00 - {}", row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
			log.debug("05 - {}", row.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
			log.debug("08 - {}", row.getCell(8, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
			log.debug("10 - {}", row.getCell(10, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
			log.debug("11 - {}", row.getCell(11, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
		}

		return row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue().contains("Set of Books Name") &&
				row.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue().contains("Mapped Customer Name") &&
				row.getCell(8, Row.CREATE_NULL_AS_BLANK).getStringCellValue().contains("Class") &&
				row.getCell(10, Row.CREATE_NULL_AS_BLANK).getStringCellValue().contains("Consolidated Billing Number") &&
				row.getCell(11, Row.CREATE_NULL_AS_BLANK).getStringCellValue().contains("Invoice Number");

	}

	public static boolean validateCollectionTemplate(Row row) {

		if (log.isDebugEnabled()) {
			log.debug("00 - {}", row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
			log.debug("03 - {}", row.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
			log.debug("05 - {}", row.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
			log.debug("07 - {}", row.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
			log.debug("20 - {}", row.getCell(20, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
			log.debug("21 - {}", row.getCell(21, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
			log.debug("22 - {}", row.getCell(22, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
		}

		return row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue().contains("SOB ID") &&
				row.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue().contains("Customer Name") &&
				row.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue().contains("Receipt No") &&
				row.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue().contains("Receipt Currency") &&
				row.getCell(20, Row.CREATE_NULL_AS_BLANK).getStringCellValue().contains("Invoice No.") &&
				row.getCell(21, Row.CREATE_NULL_AS_BLANK).getStringCellValue().contains("Invoice Date") &&
				row.getCell(22, Row.CREATE_NULL_AS_BLANK).getStringCellValue().contains("WON No");
	}

}

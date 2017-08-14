package com.mapping.parser.app;

import java.io.File;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapping.commons.Validator;
import com.mapping.enums.BilledCurrency;
import com.mapping.enums.TrimatrixColumn;
import com.mapping.parser.input.TrimatrixTracker;

/**
 * Persist TrimatrixReport shared by AR
 *
 * @author Praveen Sivasamy
 *
 */
public class TrimatrixTrackerUploader extends AbstractTemplateUploader<TrimatrixTracker> {

	private static final Logger log = LoggerFactory.getLogger(TrimatrixTrackerUploader.class);

	protected TrimatrixTrackerUploader(File template) {
		super(template);
	}

	/**
	 * Validate {@link TrimatrixTracker}
	 */
	@Override
	public boolean isValidTemplate() {
		log.info("Validate template");
		return Validator.validateTrimatrixTemplate(sheet.getRow(HEADER_ROW));
	}

	@Override
	public TrimatrixTracker parse(Row row) {
		TrimatrixTracker record = new TrimatrixTracker();
		if (processNextRow(row)) {
			for (Cell cell : row) {
				TrimatrixColumn column = TrimatrixColumn.from(cell.getColumnIndex());
				switch (column)
					{
						case MAPPED_CUSTOMER:
							record.setMappedCustomer(cell.getStringCellValue());
							break;
						case CONSOLIDATED_BILLING_NUMBER:
							record.setConsolidatedBillingNumber(cell.getStringCellValue());
							break;

						case ROW_TYPE:
							record.setRowType(cell.getStringCellValue());
							break;

						case INVOICE_NUMBER:
							if (record.getRowType().equalsIgnoreCase("Receipt") && (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
								record.setReceiptNumber((long) cell.getNumericCellValue());
								record.setInvoiceNumber("R" + record.getReceiptNumber());
							} else {
								record.setInvoiceNumber(cell.getStringCellValue());
							}
							break;
						case INVOICE_DATE:
							record.setInvoiceDate(cell.getDateCellValue());
							break;
						case INVOICE_CURRENCY:
							record.setCurrency(BilledCurrency.EUR);
							break;
						case OPEN_AMOUNT:
							record.setOpenAmount(cell.getNumericCellValue());
							break;
						case ORIGINAL_AMOUNT:
							record.setOriginalAmount(cell.getNumericCellValue());
							break;
						case OUTSTANDING_DAYS:
							record.setOutstandingDays((int) cell.getNumericCellValue());
							break;
						case AGE_BUCKET:
							record.setAgingBucket(cell.getStringCellValue());
							break;
						case WON:
							if (record.getRowType().equalsIgnoreCase("Receipt")) {
								record.setWon(0);
							} else {
								record.setWon((int) cell.getNumericCellValue());
							}
							break;
						case PROJECT_NAME:
							record.setProjectName(cell.getStringCellValue());
							break;
						default:
							break;
					}
			}
			log.info(record.toString());
		}
		return record;
	}

	/**
	 * Check if {@link TrimatrixTracker}'s {@link Row} is relevant
	 *
	 * @param row
	 * @return
	 */
	private static boolean processNextRow(Row row) {
		return (row.getRowNum() > 1) && row.getCell(TRIMATRIX_CHECK_FILTER_CELL).getStringCellValue().contains(TRIMATRIX_CLIENT_FILTER);
	}

	public static void main(String... args) {
		String in = "C:/_work/_data/part-2/IS-BFS EUC 1.1-Group1--Q4 17_Consolidated Outstanding report as of 23'rd Feb'17_Trimatrix Report.xlsx";
		File inputFile = new File(in);
		log.info("Start upload  {}", inputFile.getName());
		new TrimatrixTrackerUploader(inputFile).run();
		log.info("Finished");
	}
}

package com.mapping.parser.app;

import java.io.File;
import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapping.commons.Validator;
import com.mapping.enums.BilledCurrency;
import com.mapping.enums.CollectionColumn;
import com.mapping.parser.input.CollectionTracker;
import com.praveen.commons.exception.ApplicationException;

public class CollectionReportUploader extends AbstractTemplateUploader<CollectionTracker> {

	private static final Logger log = LoggerFactory.getLogger(CollectionReportUploader.class);

	public CollectionReportUploader(File templateFile) throws ApplicationException {
		super(templateFile);
	}

	@Override
	public boolean isValidTemplate() {
		return Validator.validateCollectionTemplate(sheet.getRow(HEADER_ROW));
	}

	@Override
	public CollectionTracker parse(Row row) {
		CollectionTracker record = new CollectionTracker();
		if (processNextRow(row)) {
			loop: for (Cell cell : row) {
				CollectionColumn column = CollectionColumn.from(cell.getColumnIndex());

				switch (column)
					{
						case CUSTOMER_NAME:
							record.setCustomerName(cell.getStringCellValue());
							break;
						case RECEIPT_NUMBER:
							if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								record.setReceiptNumber((long) cell.getNumericCellValue());
							} else {
								record.setReceiptNumber(new BigInteger(cell.getStringCellValue()).longValue());
							}
							break;
						case RECEIPT_DATE:
							record.setReceiptDate(cell.getDateCellValue());
							break;
						case RECEIPT_CURRENCY:
							record.setCurrency(BilledCurrency.EUR);
							break;
						case ALLOCATED_AMOUNT:
							record.setAllocatedAmount(cell.getNumericCellValue());
							break;
						case RECEIVED_AMOUNT:
							record.setReceivedAmount(cell.getNumericCellValue());
							break;
						case DATE_APPLED:
							record.setDateApplied(cell.getDateCellValue());
							break;
						case INVOICE_NUMBER:
							if (StringUtils.isBlank(cell.getStringCellValue())) {
								record.setInvoiceNumber(null);
								break loop;
							}
							record.setInvoiceNumber(cell.getStringCellValue());
							break;
						case INVOICE_DATE:
							record.setInvoiceDate(cell.getDateCellValue());
							break;
						case WON:
							if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								record.setWon((int) cell.getNumericCellValue());
							} else {
								record.setWon(0);
							}
							break;
						case INVOICE_CURRENCY:
							record.setInvoiceCurrency(BilledCurrency.EUR);
							break;
						case APPLIED_INVOICE_AMOUNT:
							record.setAdjustedInvoiceAmount(cell.getNumericCellValue());
							break;
						case APPLIED_RECEIPT_AMOUNT:
							record.setAllocatedReceiptAmount(cell.getNumericCellValue());
							break;
						case UNAPPLIED_RECEIPT_AMOUNT:
							record.setUnappliedReceiptAMount(cell.getNumericCellValue());
							break;
						case COMMENTS:
							record.setComments(cell.getStringCellValue());
							break;
						case CONTRACT_ID:
							record.setContractId(cell.getStringCellValue());
							break;
						default:
							break;
					}
			}
		}
		return record;
	}

	private static boolean processNextRow(Row row) {
		return (row.getRowNum() > 1)
				&& row.getCell(COLLECTION_CHECK_FILTER_CELL).getStringCellValue().contains(COLLECTION_CLIENT_FILTER);

	}

	public static void main(String[] args) {
		String in = "C:/_work/_data/part-2/IS-BFS EUC 1.1-Group1--Q4 17_Consolidated Outstanding report as of 23'rd Feb'17_Trimatrix Report.xls";
		File inputFile = new File(in);
		log.info("Input file {} ", inputFile.getName());
		new CollectionReportUploader(inputFile).run();
	}

}

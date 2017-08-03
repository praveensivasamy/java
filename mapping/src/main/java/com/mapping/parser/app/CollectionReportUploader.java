package com.mapping.parser.app;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapping.commons.MappingConstants;
import com.mapping.commons.TrackerUploader;
import com.mapping.commons.Validator;
import com.mapping.enums.BilledCurrency;
import com.mapping.enums.CollectionColumn;
import com.mapping.parser.input.CollectionTracker;
import com.praveen.commons.enums.AppExceptionIdentifier;
import com.praveen.commons.exception.ApplicationException;
import com.praveen.commons.hibernate.HibernateProvider;
import com.praveen.commons.hibernate.JpaDao;

public class CollectionReportUploader extends MappingConstants implements TrackerUploader<CollectionTracker> {

	private static final Logger log = LoggerFactory.getLogger(CollectionReportUploader.class);

	private static HibernateProvider provider = null;
	private static JpaDao dao = null;

	private static Workbook workbook = null;
	private static Sheet sheet = null;

	@Override
	public void initialize(String template) {

		try {
			if (workbook == null) {
				workbook = WorkbookFactory.create(new File(template));
			}
			sheet = workbook.getSheet("IS-BFS EUC 1.1-Group1");
		} catch (InvalidFormatException e) {
			throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION, e).details("Invalid input file" + template);
		} catch (IOException e) {
			throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION, e).details("The input file : " + template + " not found or does not exist!");
		}
	}

	private void initializeHibernate() {
		provider = HibernateProvider.instance("mapping.hibernate.cfg.xml", null);
		dao = JpaDao.instance(provider);
	}

	@Override
	public boolean isValidTemplate() {
		return Validator.validateCollectionTemplate(sheet.getRow(HEADER_ROW));
	}

	@Override
	public CollectionTracker parse(Row row) {
		CollectionTracker record = new CollectionTracker();
		if (processNextRow(row)) {
			for (Cell cell : row) {
				CollectionColumn column = CollectionColumn.from(cell.getColumnIndex());
				switch (column) {
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
						return record.reset();
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

			log.info(record.toString());
		}
		return record;
	}

	private static boolean processNextRow(Row row) {
		return (row.getRowNum() > 1)
				&& row.getCell(COLLECTION_CHECK_FILTER_CELL).getStringCellValue().contains(COLLECTION_CLIENT_FILTER);

	}

	@Override
	public void save(CollectionTracker record) {
		dao.saveOrUpdate(record);
	}

	private void run() {
		String inputFile = "IS-BFS EUC 1.1-Group1--Consolidated Collection Report for _Q4 17_Updated  till 31'st Mar 2017_After AR CLOSURE.xlsx";
		//String inputFile = "copy.xlsx";
		CollectionReportUploader uploader = new CollectionReportUploader();
		try {
			uploader.initialize(inputFile);

			if (uploader.isValidTemplate()) {
				initializeHibernate();

				for (Row row : sheet) {
					CollectionTracker record = uploader.parse(row);
					if ((record.getInvoiceNumber() != null) && (record.getReceiptNumber() != 0)) {
						//record.setUploadedFile(inputFile);

						uploader.save(record);
					}
				}

				dao.beginTransaction();
				dao.flushAndClear();
				dao.commit();

			} else {
				throw ApplicationException.instance(AppExceptionIdentifier.TECHNICAL_EXCEPTION).details("Invalid Template for parsing" + inputFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateProvider.tearDownAll();
		}

	}

	public static void main(String[] args) {
		new CollectionReportUploader().run();
	}
}

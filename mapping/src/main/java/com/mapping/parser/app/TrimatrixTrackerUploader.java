package com.mapping.parser.app;

import java.io.File;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapping.commons.MappingConstants;
import com.mapping.enums.BilledCurrency;
import com.mapping.enums.TrimatrixColumn;
import com.mapping.parser.input.TrimatrixTracker;
import com.praveen.commons.enums.AppExceptionIdentifier;
import com.praveen.commons.exception.ApplicationException;
import com.praveen.commons.hibernate.HibernateProvider;
import com.praveen.commons.hibernate.JpaDao;

public class TrimatrixTrackerUploader extends MappingConstants implements TrackerUploader<TrimatrixTracker> {

	private static final Logger log = LoggerFactory.getLogger(TrimatrixTrackerUploader.class);

	private static HibernateProvider provider = null;
	private static JpaDao dao = null;

	private static Workbook workbook = null;
	private static Sheet sheet = null;

	@Override
	public void initialize(String template) throws ApplicationException {
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

	@Override
	public boolean isValidTemplate() {
		return Validator.validateTrimatrixTemplate(sheet.getRow(HEADER_ROW));
	}

	@Override
	public TrimatrixTracker parse(Row row) {
		TrimatrixTracker record = new TrimatrixTracker();
		if (processNextRow(row)) {
			for (Cell cell : row) {

				TrimatrixColumn column = TrimatrixColumn.from(cell.getColumnIndex());
				switch (column) {
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
						System.out.println(cell.getNumericCellValue());
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
		} else {
			return null;
		}
		return record;
	}

	private static boolean processNextRow(Row row) {
		return (row.getRowNum() > 1) && row.getCell(TRIMATRIX_CHECK_FILTER_CELL).getStringCellValue().contains(TRIMATRIX_CLIENT_FILTER);
	}

	@Override
	public void save(TrimatrixTracker record) {
		dao.saveOrUpdate(record);
	}

	private void run() {
		String inputFile = "IS-BFS EUC 1.1-Group1--Q2 18_Consolidated Outstanding report till 27'th Jul 2017_Trimatrix Report.xlsx";
		//String inputFile = "copy.xlsx";
		TrimatrixTrackerUploader uploader = new TrimatrixTrackerUploader();
		try {
			uploader.initialize(inputFile);

			if (uploader.isValidTemplate()) {
				provider = HibernateProvider.instance("mapping.hibernate.cfg.xml", null);
				dao = JpaDao.instance(provider);

				for (Row row : sheet) {
					TrimatrixTracker record = uploader.parse(row);
					if (record != null) {
						record.setUploadedFile(inputFile);
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

	public static void main(String... args) {
		new TrimatrixTrackerUploader().run();
	}
}

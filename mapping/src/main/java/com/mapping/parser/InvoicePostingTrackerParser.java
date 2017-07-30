/**
 * 
 */
package com.mapping.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapping.enums.BilledCurrency;
import com.mapping.enums.TrimatrixColumn;
import com.mapping.parser.bo.TrimatrixTracker;
import com.praveen.commons.hibernate.HibernateProvider;
import com.praveen.commons.hibernate.JpaDao;

public class InvoicePostingTrackerParser implements TrackerParser {

	private static final Logger log = LoggerFactory.getLogger(InvoicePostingTrackerParser.class);

	@Override
	public void validateTemplate() {

	}

	@Override
	public void parse() {

	}

	public static void main(String[] args) {
		try {
			InputStream ExcelFileToRead = new FileInputStream(
					"IS-BFS EUC 1.1-Group1--Q2 18_Consolidated Outstanding report till 27'th Jul 2017_Trimatrix Report.xlsx");
			XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

			XSSFSheet sheet = wb.getSheet("IS-BFS EUC 1.1-Group1");
			XSSFRow row;
			XSSFCell cell = null;
			Iterator rows = sheet.rowIterator();
			int count = 0;

			HibernateProvider provider = HibernateProvider.instance("mapping.hibernate.cfg.xml", null);
			JpaDao srcDao = JpaDao.instance(provider);
			System.out.println("Connection  : " + srcDao.getSession().isConnected());

			srcDao.beginTransaction();
			while (rows.hasNext()) {
				row = (XSSFRow) rows.next();
				Iterator cells = row.cellIterator();

				if (row.getRowNum() > 1 && row.getCell(5).getStringCellValue().equalsIgnoreCase("COMMERZBANK AG")) {
					count++;
					TrimatrixTracker tracker = new TrimatrixTracker();

					while (cells.hasNext()) {
						cell = (XSSFCell) cells.next();
						TrimatrixColumn column = TrimatrixColumn.from(cell.getColumnIndex());
						switch (column)
							{
							case MAPPED_CUSTOMER:
								tracker.setMappedCustomer(cell.getStringCellValue());
								break;
							case CONSOLIDATED_BILLING_NUMBER:
								tracker.setConsolidatedBillingNumber(cell.getStringCellValue());
								break;

							case ROW_TYPE:

								tracker.setRowType(cell.getStringCellValue());
								break;

							case INVOICE_NUMBER:

								if (tracker.getRowType().equalsIgnoreCase("Receipt")) {
									tracker.setReceiptNumber((long) cell.getNumericCellValue());
									tracker.setInvoiceNumber("R" + tracker.getReceiptNumber());
								} else {
									tracker.setInvoiceNumber(cell.getStringCellValue());
								}
								break;
							case INVOICE_DATE:
								tracker.setInvoiceDate(cell.getDateCellValue());
								break;
							case INVOICE_CURRENCY:
								tracker.setCurrency(BilledCurrency.EUR);
								break;
							case OPEN_AMOUNT:
								tracker.setOpenAmount((long) cell.getNumericCellValue());
								break;
							case OUTSTANDING_DAYS:
								tracker.setOutstandingDays((int) cell.getNumericCellValue());
								break;
							case AGE_BUCKET:
								tracker.setAgingBucket(cell.getStringCellValue());
								break;
							case WON:
								if (tracker.getRowType().equalsIgnoreCase("Receipt")) {
									tracker.setWon(0);
								} else {
									tracker.setWon((int) cell.getNumericCellValue());
								}
								break;
							case PROJECT_NAME:
								tracker.setProjectName(cell.getStringCellValue());
								break;
							default:
								break;
							}

					}

					log.info(tracker.toString());

					srcDao.saveOrUpdate(tracker);
				}
			}
			System.out.println(count);
			srcDao.flush();
			srcDao.commit();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			HibernateProvider.tearDownAll();
		}

	}

}

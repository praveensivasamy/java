package com.mapping.parser.app;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapping.commons.MappingConstants;
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

	@Override
	public boolean isValidTemplate() {

		return Validator.validateCollectionTemplate(sheet.getRow(HEADER_ROW));
	}

	@Override
	public CollectionTracker parse(Row row) {

		CollectionTracker record = new CollectionTracker();

		if (processNextRow(row)) {

		} else {
			return null;
		}

		return record;
	}

	private static boolean processNextRow(Row row) {
		return (row.getRowNum() > 1) && row.getCell(TRIMATRIX_CHECK_FILTER_CELL).getStringCellValue().contains(TRIMATRIX_CLIENT_FILTER);
	}

	@Override
	public void save(CollectionTracker record) {
		dao.save(record);
	}

	private void run() {
		String inputFile = "IS-BFS EUC 1.1-Group1--Consolidated Collection Report for _Q4 17_Updated  till 31'st Mar 2017_After AR CLOSURE.xlsx";
		//String inputFile = "copy.xlsx";
		CollectionReportUploader uploader = new CollectionReportUploader();
		try {
			uploader.initialize(inputFile);

			if (uploader.isValidTemplate()) {
				provider = HibernateProvider.instance("mapping.hibernate.cfg.xml", null);
				dao = JpaDao.instance(provider);

				for (Row row : sheet) {
					CollectionTracker record = uploader.parse(row);
					if (record != null) {
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

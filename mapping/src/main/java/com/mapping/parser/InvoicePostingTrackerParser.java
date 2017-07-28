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
					"C:/_Praveen/MyPrograms/github/java/mapping/IS-BFS EUC 1.1-Group1--Q2 18_Consolidated Outstanding report till 27'th Jul 2017_Trimatrix Report.xlsx");
			XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

			XSSFSheet sheet = wb.getSheet("IS-BFS EUC 1.1-Group1");
			XSSFRow row;
			XSSFCell cell = null;
			Iterator rows = sheet.rowIterator();
			int count = 0;
			while (rows.hasNext()) {
				count++;
				row = (XSSFRow) rows.next();
				Iterator cells = row.cellIterator();

				while (cells.hasNext()) {

					cell = (XSSFCell) cells.next();

					switch (cell.getCellType())
						{
						case XSSFCell.CELL_TYPE_NUMERIC:

							break;
						case XSSFCell.CELL_TYPE_STRING:

							break;
						case XSSFCell.CELL_TYPE_BLANK:

							break;
						default:
							break;
						}
				}
				System.out.println("_____________________________");
			}
			System.out.println(count);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

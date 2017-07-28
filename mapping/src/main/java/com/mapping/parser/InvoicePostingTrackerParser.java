/**
 * 
 */
package com.mapping.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class InvoicePostingTrackerParser implements TrackerParser {

    @Override
    public void validateTemplate() {

    }

    @Override
    public void parse() {

    }

    private static void cellType(XSSFCell cell) {
	System.out.println(cell.getNumericCellValue());
	System.out.println(cell.getStringCellValue());
    }

    public static void main(String[] args) {
	try {
	    InputStream ExcelFileToRead = new FileInputStream(
		    "C:/_Praveen/MyPrograms/github/java/mapping/IS-BFS EUC 1.1-Group1--Q2 18_Consolidated Outstanding report till 27'th Jul 2017_Trimatrix Report.xlsx");
	    XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

	    XSSFSheet sheet = wb.getSheet("IS-BFS EUC 1.1-Group1");
	    XSSFRow row;
	    XSSFCell cell;
	    Cell raw;
	    Iterator rows = sheet.rowIterator();
	    int count = 0;
	    while (rows.hasNext()) {
		count++;
		row = (XSSFRow) rows.next();

		Iterator cells = row.cellIterator();

		while (cells.hasNext()) {

		    raw = (Cell) cells.next();

		    System.out.println(raw.getCellType());
		    // cell = (XSSFCell) cells.next();
		    /*
		     * 
		     * 
		     * if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
		     * System.out.print(cell.getStringCellValue() + " "); } else
		     * if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
		     * System.out.print(cell.getNumericCellValue() + " "); }
		     * else { // U Can Handel Boolean, Formula, Errors }
		     */
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

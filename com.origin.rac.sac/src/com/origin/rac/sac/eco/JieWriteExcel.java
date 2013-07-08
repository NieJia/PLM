package com.origin.rac.sac.eco;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class JieWriteExcel {

	public static Workbook createExcel(String filename) throws Exception{   
		Workbook workbook = null;   
		if(null!=filename&&!"".equals(filename)){
			if(!(new File(filename).exists())){
				XSSFWorkbook wb= new XSSFWorkbook();
				FileOutputStream fileOut= new FileOutputStream(filename);
				wb.write(fileOut);
				fileOut.close();
			}
			if(filename.substring(filename.lastIndexOf(".")+1).equals("xls")){   
				workbook = new HSSFWorkbook(new FileInputStream(filename));   
			}else{   
				workbook = new XSSFWorkbook(new FileInputStream(filename));   
			}   
		}   
		return workbook;     
	}   
	public static void wirteExcel( Workbook workbook, String mySheet, int myRow, int myCell, String message) {   
		Sheet sheet = workbook.getSheet(mySheet) == null?workbook.createSheet(mySheet):workbook.getSheet(mySheet); 
		Row row = sheet.getRow(myRow) == null?sheet.createRow(myRow):sheet.getRow(myRow);   
		Cell cell = row.getCell(myCell) == null?row.createCell(myCell):row.getCell(myCell);   
		cell.setCellValue(message);

		CellStyle style = workbook.createCellStyle();
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		cell.setCellStyle(style);
	}

	public static void clearExcel(Workbook wb, String sheet, int rowNum, int colNum)throws Exception {
		Sheet sheet1 = wb.getSheet(sheet);
		Row row1 = sheet1.getRow(rowNum) != null ? sheet1.getRow(rowNum) : sheet1.createRow(rowNum);
		Cell cell = row1.getCell(colNum) != null ? row1.getCell(colNum) : row1.createCell(colNum);
		cell.setCellType(3);
	}

	public static void closeExcel(Workbook workbook, String filename)throws Exception{
		FileOutputStream out = new FileOutputStream(filename);   
		workbook.write(out);   
		out.close(); 
	}
}

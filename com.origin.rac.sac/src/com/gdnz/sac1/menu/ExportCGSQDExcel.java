package com.gdnz.sac1.menu;

import java.awt.Color;
import  java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.Region;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



class ExportCGSQDExcel  {
	
	private Sheet sheet = null;
	private Workbook workBook = null;
//	private  font = null;
	private static CellStyle style =null;
	private int beginCol = 0;
	private int beginRow = 5;
//	private WritableCellFormat cellnumformat = null;
	private Vector<CGSQDInfo> cGSQDInfo = new Vector<CGSQDInfo>();
	private int RowNum = 0;
	private String bianhao;

	public ExportCGSQDExcel(Vector<CGSQDInfo> cgSQDInfo, File file,String bh) {
		try {
			    workBook=new HSSFWorkbook(new FileInputStream(file.getPath()));
				this.cGSQDInfo = cgSQDInfo;
				this.bianhao=bh;
				collectSheet(file.getPath(),cgSQDInfo);
			} catch (Exception e)  {
				System.out.println(e);
		} 
       
	}
			
	private void collectSheet(String path,Vector<CGSQDInfo> cgSQDInfo) {
		try {
			// set Style
			    HSSFFont font=(HSSFFont) workBook.createFont();
			    font.setFontName("宋体");
			    font.setFontHeightInPoints((short) 10);
			    style = workBook.createCellStyle();
			    style.setFont(font);
				style.setBorderBottom(CellStyle.BORDER_MEDIUM);
				style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderLeft(CellStyle.BORDER_MEDIUM);
				style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderRight(CellStyle.BORDER_MEDIUM);
				style.setRightBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderTop(CellStyle.BORDER_MEDIUM);
				style.setTopBorderColor(IndexedColors.BLACK.getIndex());
				style.setAlignment(CellStyle.ALIGN_CENTER);
				style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				style.setWrapText(true);
     		System.out.println("写的第X行+++++++++++++++++++++"+beginRow);
    		// 写cell
   		    addRecord(beginCol, cgSQDInfo);
   		   closeExcel(workBook,path);
		} catch (Exception e)  {
				System.out.println(e);
			} 
			
	}
	
	private void addExtra(int col, int row){
		// TODO Auto-generated method stub
		for(int i=0;i<=col+11;i++)
		   wirteExcel(workBook,"Sheet1",row,i,"");
		workBook.getSheet("Sheet1").addMergedRegion(new CellRangeAddress(row,row,col,col+11));

		wirteExcel(workBook,"Sheet1",row,col+12,"合计:");
		wirteExcel(workBook,"Sheet1",row,col+15,"");
	
		for(int i=col+17;i<=col+21;i++)
		wirteExcel(workBook,"Sheet1",row,i,"");
		workBook.getSheet("Sheet1").addMergedRegion(new CellRangeAddress(row,row,col+17,col+21));
	
		wirteExcel(workBook,"Sheet1",row+1,col,"备注");
		wirteExcel(workBook,"Sheet1",row+1,col+1,"");
		workBook.getSheet("Sheet1").addMergedRegion(new CellRangeAddress(row+1,row+1,col,col+1));

		for(int i=col+2;i<=col+21;i++)
		wirteExcel(workBook,"Sheet1",row+1,i,"");
		workBook.getSheet("Sheet1").addMergedRegion(new CellRangeAddress(row+1,row+1,col+2,col+21));	
	}

	private void addRecord(int col,Vector<CGSQDInfo> cgSQDInfo){
		
		wirteExcel(workBook,"Sheet1",3,19,bianhao);
		RowNum=beginRow-5;
		for(int i=0;i<cgSQDInfo.size();i++){
			RowNum++;			
			wirteExcel(workBook,"Sheet1",RowNum+4,col,String.valueOf(RowNum));
			wirteExcel(workBook,"Sheet1",RowNum+4,col+1,cgSQDInfo.get(i).getContactNum());
			wirteExcel(workBook,"Sheet1",RowNum+4,col+3,cgSQDInfo.get(i).getPrjName());
			wirteExcel(workBook,"Sheet1",RowNum+4,col+5,cgSQDInfo.get(i).getWuLiaoId());
			wirteExcel(workBook,"Sheet1",RowNum+4,col+6,cgSQDInfo.get(i).getWuLiaoName());
			wirteExcel(workBook,"Sheet1",RowNum+4,col+7,cgSQDInfo.get(i).getDanWei());
			
			for(int j=8;j<22;j++){
				wirteExcel(workBook,"Sheet1",RowNum+4,col+j,"");
			}
		}
		addExtra(beginCol, RowNum+5);
	}
	public static void wirteExcel( Workbook workbook, String mySheet, int myRow, int myCell, String message) {   
		Sheet sheet = workbook.getSheet(mySheet) == null?workbook.createSheet(mySheet):workbook.getSheet(mySheet); 
		
		Row row = sheet.getRow(myRow) == null?sheet.createRow(myRow):sheet.getRow(myRow);   
		Cell cell = row.getCell(myCell) == null?row.createCell(myCell):row.getCell(myCell);   
		cell.setCellValue(message);
		cell.setCellStyle(style);
	//	sheet.
	//	sheet.autoSizeColumn(myCell);//单元格自适应大小

		
	}
	/*
	private void writeMergeCell(String content, int colA, int rowA, int colB, int rowB) {
		
		workBook.getSheet("Sheet1").addMergedRegion(new CellRangeAddress(rowA,colA,rowB,colB));
		Cell cell = workBook.getSheet("Sheet1").getRow(rowA).getCell(colA);
		cell.setCellValue(content);
		cell.setCellStyle(style);
	}
	*/

	public static void closeExcel(Workbook workbook, String filename)throws Exception{
		FileOutputStream out = new FileOutputStream(filename);   
		workbook.write(out);   
		out.close(); 
	}
} 
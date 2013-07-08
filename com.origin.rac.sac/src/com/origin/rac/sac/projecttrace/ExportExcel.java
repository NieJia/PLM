package com.origin.rac.sac.projecttrace;


import java.awt.Color;
import  java.io.File;
import java.util.ArrayList;
import java.util.List;

import  jxl.Cell;
import  jxl.Sheet;
import  jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

class ExportExcel  {
	
	private Sheet sheet = null;
	private Sheet sheet2 = null;

	private WritableSheet writeSheet = null;
	private WritableSheet writeSheet2 = null;
	private Workbook workBook = null;
	private WritableWorkbook  writebook = null;
	private WritableFont font = null;
	private WritableFont BoldFont = null;
	private WritableFont redFont = null;
	private WritableFont titleFont = null;
	private WritableFont headFont = null;



	private WritableCellFormat format = null;
	private WritableCellFormat BoldFormat = null;
	private WritableCellFormat redFormat = null;
	private WritableCellFormat darkFormat = null;
	private WritableCellFormat whiteFormat = null;
	private WritableCellFormat whiteLightFormat = null;

	private WritableCellFormat pinkFormat = null;
	private WritableCellFormat blueFormat = null;
	private WritableCellFormat greenFormat = null;
	private WritableCellFormat headFormat = null;
	
	private PrjTraceInfo traceInfo = null;

	public ExportExcel(PrjTraceInfo traceInfo, String path) {
		try {
	
				workBook = Workbook.getWorkbook( new File(path));
				writebook = Workbook.createWorkbook(new File(path), workBook);
       
				sheet = workBook.getSheet(0);
				sheet2 = workBook.getSheet(1);

				
				this.traceInfo = traceInfo;
			
				collectSheet();
			
			} catch (Exception e)  {
				System.out.println(e);
		} 
       
	}
			
	private void collectSheet() {
		try {
				// set Style
				font = new WritableFont(WritableFont.createFont("宋体"), 10);
				headFont = new  WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.BOLD);
				titleFont = new WritableFont(WritableFont.createFont("华文新魏"), 18, WritableFont.BOLD);
				BoldFont = new  WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD);
				redFont = new  WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD);
				redFont.setColour(Colour.RED);
			
				format =  new  WritableCellFormat(font);
				format.setAlignment(jxl.format.Alignment.CENTRE);
				format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
				format.setWrap(true);
				format.setBackground(Colour.GRAY_25);
	
			
				format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); 
				
				BoldFormat = new WritableCellFormat(format);
				BoldFormat.setFont(BoldFont);
			
				redFormat = new WritableCellFormat(format);
				redFormat.setFont(redFont);
			
				darkFormat = new WritableCellFormat(format);
				darkFormat.setBackground(Colour.GRAY_50);
			
				whiteFormat = new WritableCellFormat(format);
				whiteFormat.setFont(BoldFont);
				whiteFormat.setBackground(Colour.WHITE);
				
				whiteLightFormat = new WritableCellFormat(format);
				whiteLightFormat.setFont(font);
				whiteLightFormat.setBackground(Colour.WHITE);
			
				pinkFormat = new WritableCellFormat(format);
				pinkFormat.setFont(font);
				pinkFormat.setBackground(Colour.ICE_BLUE);
			
				blueFormat = new WritableCellFormat(format);
				blueFormat.setFont(BoldFont);
				blueFormat.setBackground(Colour.PALE_BLUE);
			
				headFormat = new WritableCellFormat(format);
				headFormat.setFont(headFont);
				headFormat.setBackground(Colour.PALE_BLUE);
			
				greenFormat = new WritableCellFormat(format);
				greenFormat.setFont(titleFont);
				greenFormat.setBackground(Colour.LIGHT_GREEN);
				//find insert record position
        	
        		int beginCol = 1;
        		int beginRow = 10;
        		Cell cell = sheet.getCell(beginCol, beginRow); 
        		cell = sheet.getCell(beginCol, beginRow);
        	
        		while (!cell.getContents().equals("")) {
        			beginRow += 3;
        			cell = sheet.getCell(beginCol, beginRow);
        		}
        	
        	
        		System.out.println(cell.getRow());
        		System.out.println("a");
    		
        	
        	
        		String result  =  cell.getContents();
        		System.out.println(result);
        
        		// 写cell
        		writeSheet = writebook.getSheet(0);
        		writeSheet2 = writebook.getSheet(1);

        		addRecord(beginCol, beginRow);
        		
        		writeYearCell(traceInfo.getProcess(), 5, 7);
        	
        		writeTitle(traceInfo.getChargeFirm() + "项目" + 
        				traceInfo.getProcess() + "年实施进度总表", 1, 6);
        		
        		writeSheet2(traceInfo.getChargeFirm() + 
        				traceInfo.getProcess() + "年指标完成情况", 1, 1);
        		writeSheet2(traceInfo.getChargeFirm() + 
        				traceInfo.getProcess() + "年费用情况（万元）", 7, 1);
        		writeHead(traceInfo.getChargeFirm(), 1, 2);
        	
        		writebook.write();
        		writebook.close();
        		workBook.close();
				
		} catch (Exception e)  {
				System.out.println(e);
			} 
			
	}
	
	private void addRecord(int col, int row) throws RowsExceededException, WriteException {
			
		int prjNum = 0;
		
		if (row == 10) {
			prjNum = 1;
		}
		else {
			prjNum = row - 9 - 4;
		}
		
		writeMergeCell(Integer.toString(((row - 10) / 3) + 1), col, row, col, row + 2);
		writeMergeCell(traceInfo.getPrjId(), col + 1, row, col + 1, row + 2);
		writeMergeCell(traceInfo.getPrjName(), col + 2, row, col + 2, row + 2);
			
		writeCell("计划", col + 3, row);
		writeCell("变更", col + 3, row + 1);
		writeCell("执行说明", col + 3, row + 2);
		
		writeHeadCell("在研项目：" + 
				Integer.toString(((row - 10) / 3) + 1) + "个", 1, 9);
			
				
		for (int i = 0; i < traceInfo.getPlan().length; i++) {
			writePlanBox(traceInfo.getPlan()[i], col + 4 + i, row);
		}
			
		for (int i = 0; i < traceInfo.getChangePlan().length; i++) {
			writePlanBox(traceInfo.getChangePlan()[i], col + 4 + i, row + 1);
		}
			
		for (int i = 0; i < traceInfo.getExcuteState().length; i++) {
			writeExcuteState(traceInfo.getExcuteState()[i], col + 4 + i, row + 2);
		}
			
		writeRedCell(traceInfo.getChangeTimes(), col + 16, row, col + 16, row + 2);
		writeProgress(traceInfo.getProcessBias(), col + 17, row, col + 17, row + 2);
		writeRedCell(traceInfo.getFinishingRate(), col + 18, row, col + 18, row + 2);
		writeMergeCell(traceInfo.getDeadline(), col + 19, row, col + 19, row + 2);
		writeMergeCell(traceInfo.getTotalCost(), col + 20, row, col + 20, row + 2);
		writeMergeCell(traceInfo.getRealCost(), col + 21, row, col + 21, row + 2);
		writePrjFormat(traceInfo.getEndFormat(), col + 22, row, col + 22, row + 2);
		writeMergeCell(traceInfo.getLeader2() + " " +
				traceInfo.getLeader1() + "\n" +
				traceInfo.getLeader3(), col + 23, row, col + 23, row + 2);

		
	}
		
	private void writeMergeCell(String content, int colA, int rowA, int colB, int rowB) throws RowsExceededException, WriteException {
			
		writeSheet.mergeCells(colA, rowA, colB, rowB);
			
			
		Label label = new Label(colA, rowA, content, format);
		writeSheet.addCell(label);
	}
	
	private void writeHead(String content, int col, int row) throws RowsExceededException, WriteException {
			
		
		Label label = new Label(col, row, content, whiteLightFormat);
		writeSheet.addCell(label);
	}

	private void writeCell(String content, int col, int row) throws RowsExceededException, WriteException {
			
		
		Label label = new Label(col, row, content, BoldFormat);
		writeSheet.addCell(label);
	}
	
	private void writeYearCell(String content, int col, int row) throws RowsExceededException, WriteException {
			
		
		Label label = new Label(col, row, content, blueFormat);
		writeSheet.addCell(label);
	}
	
	private void writeRedCell(String content, int colA, int rowA, int colB, int rowB) throws RowsExceededException, WriteException {
		
		writeSheet.mergeCells(colA, rowA, colB, rowB);
			
		Label label = new Label(colA, rowA, content, redFormat);
		writeSheet.addCell(label);
	}
	
	private void writeHeadCell(String content, int col, int row) throws RowsExceededException, WriteException {
			
		
		Label label = new Label(col, row, content, headFormat);
		writeSheet.addCell(label);
	}
	
	private void writeTitle(String content, int col, int row) throws RowsExceededException, WriteException {
			
		
		Label label = new Label(col, row, content, greenFormat);
		writeSheet.addCell(label);
	}
		
		
	private void writePlanBox(String content, int col, int row) throws RowsExceededException, WriteException {
		Label label = new Label(col, row, content, pinkFormat);
		
		WritableCellFeatures wcf = new WritableCellFeatures();
		List<String> angerlist = new ArrayList<String>();
		angerlist.add("A");
		angerlist.add("B");
		angerlist.add("C");
		angerlist.add("D");
		angerlist.add("E");
		angerlist.add("F");
		wcf.setDataValidationList(angerlist);
		label.setCellFeatures(wcf);
		writeSheet.addCell(label); 
		
	}
		
	private void writeExcuteState(String content, int col, int row) throws RowsExceededException, WriteException {
		Label label = new Label(col, row, content, pinkFormat);
		
		WritableCellFeatures wcf = new WritableCellFeatures();
		List<String> angerlist = new ArrayList<String>();
		angerlist.add("关注");
		angerlist.add("正常");
		wcf.setDataValidationList(angerlist);
		label.setCellFeatures(wcf);
		writeSheet.addCell(label); 
		
	}
		
	private void writeProgress(String content, int colA, int rowA, int colB, int rowB) throws RowsExceededException, WriteException {
		writeSheet.mergeCells(colA, rowA, colB, rowB);
		Label label = new Label(colA, rowA, content, whiteFormat);
		
		WritableCellFeatures wcf = new WritableCellFeatures();
		List<String> angerlist = new ArrayList<String>();
		angerlist.add("正常");
		angerlist.add("延迟>15");
		angerlist.add("警告>30");
		angerlist.add("暂停");
		angerlist.add("正式归档");
		angerlist.add("正式归档");

		wcf.setDataValidationList(angerlist);
		label.setCellFeatures(wcf);
		writeSheet.addCell(label); 
			
	}		
	private void writePrjFormat(String content, int colA, int rowA, int colB, int rowB) throws RowsExceededException, WriteException {
		writeSheet.mergeCells(colA, rowA, colB, rowB);
		Label label = new Label(colA, rowA, content, darkFormat);
		
		writeSheet.addCell(label);
			
	}
	
	private void writeSheet2(String content, int col, int row) throws RowsExceededException, WriteException {
			
		
		Label label = new Label(col, row, content, greenFormat);
		writeSheet2.addCell(label);
	}
} 
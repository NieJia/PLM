package test;



import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.CellStyle;
import java.io.FileOutputStream;


public class CreateXL {

	/** Excel 

	 文件要存放的位置，假定在

	 D

	 盘下

	

	 */

	public static String outputFile = "c:\\test.xls";

	private void cteateCell(HSSFWorkbook wb, HSSFRow row, short col, String val) {
		
		HSSFCell cell = row.createCell(col); 
		cell.setCellValue(val); 
		HSSFCellStyle cellstyle = wb.createCellStyle(); 
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION); 
		cell.setCellStyle(cellstyle);
	}	
	
	
	public static void main(String argv[]) {
		
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook(); 
		// 设置字体

		HSSFFont font = workbook.createFont(); 

		// font.setColor(HSSFFont.COLOR_RED); 
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
		font.setFontHeightInPoints((short) 14); 
		// HSSFFont font2 = workbook.createFont(); 
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
		// font.setFontHeightInPoints((short)14); 
		// 设置样式

		HSSFCellStyle cellStyle = workbook.createCellStyle(); 
		cellStyle.setFont(font); 
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
		// HSSFCellStyle cellStyle2= workbook.createCellStyle(); 
		// cellStyle.setFont(font2); 
		// cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"月报表"的工作表，其语句为：

		HSSFSheet sheet = workbook.createSheet("月报表"); 
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0,11); 

		sheet.addMergedRegion(cellRangeAddress);

		//第一行
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0); 

		// 在索引0的位置创建单元格（左上端）
		HSSFCell cell = row.createCell(0); 
		// 定义单元格为字符串类型

		cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
		cell.setCellStyle(cellStyle); 
		// 在单元格中输入一些内容
		cell.setCellValue(new HSSFRichTextString("北京亿卡联科技发展有限公司小区门禁维修月报表")); 

		
		
	}
}  


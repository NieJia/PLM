package com.gdnz.sac1.form;
import java.awt.Color;  


import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
 
import com.lowagie.text.Cell;  
import com.lowagie.text.Document;  
import com.lowagie.text.DocumentException;  
import com.lowagie.text.Font;  
import com.lowagie.text.PageSize;  
import com.lowagie.text.Paragraph;  
import com.lowagie.text.Table;  
import com.lowagie.text.rtf.RtfWriter2;  
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCException;

class PrjWord {
	
	public PrjWord(String exPath,TCComponentForm target_form) {
		Document document = new Document(PageSize.A4); 
		
		try {  
			
			
			if (target_form.getTCProperty("s4Prj_name").getStringValue().length() > 0) {
			RtfWriter2.getInstance(document,
					new FileOutputStream(exPath));  
			}
			
			else {
				RtfWriter2.getInstance(document,
						new FileOutputStream(exPath));  
			}
			
			
			document.open();  

					// 标题
	     	         
					Paragraph ph = new Paragraph();  
					Font f  = new Font();  

					Paragraph p = new Paragraph(target_form.getTCProperty("s4Prj_name").getStringValue(),
					new Font(Font.NORMAL, 16, Font.BOLD, new Color(0, 0, 0)) );  
					p.setAlignment(1);  
					document.add(p);  
					ph.setFont(f);  
	 
					document.add(new Paragraph(""));  

					//表
					Table table = new Table(8,10);  

					table.setBorderWidth(1);  
					table.setBorderColor(Color.BLACK);  
					table.setPadding(0);  
					table.setSpacing(0);
					table.setWidth(100);
	    
					makeCell("项目名称", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4Prj_name").getStringValue(), true, 1, 3, table);
					makeCell("研制令号", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4prj_id").getStringValue(), true, 1, 3, table);
					
					makeCell("项目类型", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4Projtype").getStringValue(), true, 1, 3, table);
					makeCell("集团项目", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4teamevent").getStringValue(), true, 1, 1, table);
					makeCell("seiban号", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4seiban").getStringValue(), true, 1, 1, table);

					makeCell("项\n目\n基\n本\n属\n性", true, 75, 1, table);

					makeCell("\n项目负责人\n", true, 1, 7, table);
					
					makeCell("姓名", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4name2").getStringValue(), false, 1, 1, table);
					makeCell("职称", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4title2").getStringValue(), false, 1, 1, table);
					makeCell("专业", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4profession").getStringValue(), false, 1, 2, table);
					makeCell("研制单位", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4department").getStringValue(), false, 1, 3, table);
					makeCell("研制部门", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4prj_class").getStringValue(), false, 1, 2, table);
					makeCell("联系方式", true, 3, 1, table);
					makeCell(target_form.getTCProperty("s4contact_way").getStringValue(), false, 3, 3, table);
					makeCell("Email", true, 3, 1, table);
					makeCell(target_form.getTCProperty("s4Email").getStringValue(), false, 3, 2, table);
					
					makeCell("姓名", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4name1").getStringValue(), false, 1, 1, table);
					makeCell("职称", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4title1").getStringValue(), false, 1, 1, table);
					makeCell("专业", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4profession1").getStringValue(), false, 1, 2, table);
					makeCell("研制单位", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4department1").getStringValue(), false, 1, 3, table);
					makeCell("研制部门", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4prj_class2").getStringValue(), false, 1, 2, table);
					makeCell("联系方式", true, 3, 1, table);
					makeCell(target_form.getTCProperty("s4contact_way1").getStringValue(), false, 3, 3, table);
					makeCell("Email", true, 3, 1, table);
					makeCell(target_form.getTCProperty("s4Email1").getStringValue(), false, 3, 2, table);
					
					makeCell("姓名", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4name3").getStringValue(), false, 1, 1, table);
					makeCell("职称", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4title3").getStringValue(), false, 1, 1, table);
					makeCell("专业", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4profession3").getStringValue(), false, 1, 2, table);
					makeCell("研制单位", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4department3").getStringValue(), false, 1, 3, table);
					makeCell("研制部门", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4prj_class3").getStringValue(), false, 1, 2, table);
					makeCell("联系方式", true, 3, 1, table);
					makeCell(target_form.getTCProperty("s4contact_way3").getStringValue(), false, 3, 3, table);
					makeCell("Email", true, 3, 1, table);
					makeCell(target_form.getTCProperty("s4Email3").getStringValue(), false, 3, 2, table);
					
					
					makeCell("\n项目简况\n", true, 1, 7, table);
					
					makeCell("适用范围", true, 3, 1, table);
					makeCell(target_form.getTCProperty("s4scope").getStringValue(), false, 3, 6, table);
					makeCell("起始年月", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4timed").getStringValue(), false, 1, 3, table);
					makeCell("截至年月", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4timedup").getStringValue(), false, 1, 2, table);
					makeCell("承担单位", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4tandd").getStringValue(), false, 1, 3, table);
					makeCell("承担部门", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4tandd1").getStringValue(), false, 1, 2, table);
					makeCell("研试方式", true, 3, 1, table);
					makeCell(target_form.getTCProperty("s4Grind_testw").getStringValue(), false, 3, 6, table);
					makeCell("合作单位", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4Cooperator").getStringValue(), false, 1, 3, table);
					makeCell("合作方式", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4Cooperation").getStringValue(), false, 1, 2, table);
					makeCell("项目总预算(万元)", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4prj_budget").getStringValue(), false, 1, 3, table);
					makeCell("资金来源", true, 1, 1, table);
					makeCell(target_form.getTCProperty("s4cap_source").getStringValue(), false, 1, 2, table);
					
					makeCell("\n预期目标及成果\n", true, 1, 7, table);
					
					makeCell("目标技术\n水平", true, 11, 1, table);
					makeCell(target_form.getTCProperty("s4goal_resu").getStringValue(), false, 11, 6, table);
					makeCell("专利", true, 3, 1, table);
					makeCell(target_form.getTCProperty("s4patent").getStringValue(), false, 3, 6, table);
					makeCell("著作权", true, 3, 1, table);
					makeCell(target_form.getTCProperty("s4copyright").getStringValue(), false, 3, 6, table);
					makeCell("论文", true, 3, 1, table);
					makeCell(target_form.getTCProperty("s4thesis").getStringValue(), false, 3, 6, table);
					makeCell("成果验收\n方式", true, 6, 1, table);
					makeCell(target_form.getTCProperty("s4Results_acceway").getStringValue(), false, 6, 6, table);
					
					makeCell("\n项目内容\n", true, 1, 7, table);
					
					makeCell(target_form.getTCProperty("s4prj_content").getStringValue(), false, 7, 7, table);
					makeCell(target_form.getTCProperty("s4ado_stand").getStringValue(), false, 6, 7, table);
					
					makeCell("\n进度安排\n", true, 1, 7, table);
					
					makeCell("阶段", true, 1, 1, table);
					makeCell("阶段名称", true, 1, 2, table);
					makeCell("起始时间", true, 1, 2, table);
					makeCell("截止时间", true, 1, 2, table);

					makeCell("A", true, 1, 1, table);
					makeCell("立项阶段", true, 1, 2, table);
					if (target_form.getTCProperty("s4uptime").getStringArrayValue().length > 0) {
						
						makeCell(target_form.getTCProperty("s4start_t").getStringArrayValue()[0], true, 1, 2, table);
						makeCell(target_form.getTCProperty("s4uptime").getStringArrayValue()[0], true, 1, 2, table);
					}
					else {
						
						makeCell("", true, 1, 2, table);
						makeCell("", true, 1, 2, table);	
						
					}
					
					makeCell("B", true, 1, 1, table);
					makeCell("设计阶段", true, 1, 2, table);
					
					if (target_form.getTCProperty("s4uptime").getStringArrayValue().length > 0) {
						
						makeCell(target_form.getTCProperty("s4start_t").getStringArrayValue()[1], true, 1, 2, table);
						makeCell(target_form.getTCProperty("s4uptime").getStringArrayValue()[1], true, 1, 2, table);
						
					}
					else {
						
						
						makeCell("", true, 1, 2, table);
						makeCell("", true, 1, 2, table);
					
					}
					
					makeCell("C", true, 1, 1, table);
					makeCell("样机试制阶段", true, 1, 2, table);
					
					if (target_form.getTCProperty("s4uptime").getStringArrayValue().length > 0) {

						makeCell(target_form.getTCProperty("s4start_t").getStringArrayValue()[2], true, 1, 2, table);
						makeCell(target_form.getTCProperty("s4uptime").getStringArrayValue()[2], true, 1, 2, table);
					}
					else {
						
						makeCell("", true, 1, 2, table);
						makeCell("", true, 1, 2, table);	
					}
						
					makeCell("D", true, 1, 1, table);
					makeCell("测试及运行阶段", true, 1, 2, table);
					
					if (target_form.getTCProperty("s4uptime").getStringArrayValue().length > 0) {

						makeCell(target_form.getTCProperty("s4start_t").getStringArrayValue()[3], true, 1, 2, table);
						makeCell(target_form.getTCProperty("s4uptime").getStringArrayValue()[3], true, 1, 2, table);
					}
					else {
						
						makeCell("", true, 1, 2, table);
						makeCell("", true, 1, 2, table);	
					}
					
					makeCell("E", true, 1, 1, table);
					makeCell("项目验收", true, 1, 2, table);
					
					if (target_form.getTCProperty("s4uptime").getStringArrayValue().length > 0) {

						makeCell(target_form.getTCProperty("s4start_t").getStringArrayValue()[4], true, 1, 2, table);
						makeCell(target_form.getTCProperty("s4uptime").getStringArrayValue()[4], true, 1, 2, table);
					}
					else {
						
						makeCell("", true, 1, 2, table);
						makeCell("", true, 1, 2, table);	
					}
					makeCell("\n项目组人员\n", true, 1, 8, table);
					makeCell("姓名", true, 1, 1, table);
					makeCell("性别", true, 1, 1, table);
					makeCell("职称", true, 1, 1, table);
					makeCell("年龄", true, 1, 1, table);
					makeCell("承担本项目主要工作", true, 1, 2, table);
					makeCell("投入月数", true, 1, 1, table);
					makeCell("备注", true, 1, 1, table);
					
					for (int i = 0; i < target_form.getTCProperty("s4name").getStringArrayValue().length; i++) {
						makeHumCell(""+target_form.getTCProperty("s4name").getStringArrayValue()[i], 
								""+target_form.getTCProperty("s4sex").getStringArrayValue()[i],
								""+target_form.getTCProperty("s4title").getStringArrayValue()[i],
								""+target_form.getTCProperty("s4age").getStringArrayValue()[i],
								""+target_form.getTCProperty("s4mainwork").getStringArrayValue()[i],
								""+target_form.getTCProperty("s4inmonth").getStringArrayValue()[i],
								""+target_form.getTCProperty("s4remark").getStringArrayValue()[i],
								table);
				}



					Table cosTable = new Table(10,10);  

					cosTable.setBorderWidth(1);  
					cosTable.setBorderColor(Color.BLACK);  
					cosTable.setPadding(0);  
					cosTable.setSpacing(0);
					cosTable.setWidth(100);
					
					for (int i = 0; i < 5; i++) {
						 if(i==0)
							makeCell("经济来源", true, 1, 2, cosTable);
						else if(i==1)
							makeCell("申请总公司资助", true, 1, 2, cosTable);
						else if(i==2)
							makeCell("申请公司外资助", true, 1, 2, cosTable);
						else if(i==3)
							makeCell("研制部门自筹金额", true, 1, 2, cosTable);
						else if(i==4)
							makeCell("合		计", true, 1, 2, cosTable);
						 
						if (target_form.getTCProperty("s4ina").getStringArrayValue().length > 0) {
							
							makeCell(""+target_form.getTCProperty("s4ina").getStringArrayValue()[i], true, 1, 2, cosTable);
						}
						else {
							
							makeCell("", true, 1, 2, cosTable);	
						}
						
						if (target_form.getTCProperty("s4inb").getStringArrayValue().length > 0) {
							
							makeCell(""+target_form.getTCProperty("s4inb").getStringArrayValue()[i], true, 1, 2, cosTable);
						}
						else {
							
							makeCell("", true, 1, 2, cosTable);	
						}

						if (target_form.getTCProperty("s4inc").getStringArrayValue().length > 0) {
	
							makeCell(""+target_form.getTCProperty("s4inc").getStringArrayValue()[i], true, 1, 2, cosTable);
						}
						else {
	
							makeCell("", true, 1, 2, cosTable);	
						}

						if (target_form.getTCProperty("s4remark1").getStringArrayValue().length > 0) {
	
							makeCell(""+target_form.getTCProperty("s4remark1").getStringArrayValue()[i], true, 1, 2, cosTable);
						}
						else {
	
							makeCell("", true, 1, 2, cosTable);	
						}
											
					}
					
					makeCell("预算支出科目", true, 1, 2, cosTable);
					makeCell("预研阶段(万元)", true, 1, 1, cosTable);
					makeCell("方案设计(万元)", true, 1, 1, cosTable);
					makeCell("样机测试(万元)", true, 1, 1, cosTable);
					makeCell("检测与试运行(万元)", true, 1, 2, cosTable);
					makeCell("设计确认(万元)", true, 1, 1, cosTable);
					makeCell("计算根据及理由", true, 1, 2, cosTable);
					
					String []str={"(一)直接费用","1.人员表","(1)研究人员工资","(2)临时工工资","2.设备及软件费","(1)购置","(2)试制","3.业务费","(1)材料,元器件费用","(2)样机加工费","(3)测试试验费","(4)资料费","(5)会议费","(6)差旅费","4.工程化设计费","5.其他直接费用","(二)间接费用","1.现有仪器设备使用费","2.直接管理费用","3.其他间接费用","(三)合作研究支持","1.合作支出1","2.合作支出2","合 计"};
					
					if (target_form.getTCProperty("s4pre_stage").getStringArrayValue().length > 0) {
						
			
						for (int i = 0; i < 24; i++) {
							makeCostCell(
								str[i],
								""+target_form.getTCProperty("s4pre_stage").getStringArrayValue()[i], 
								""+target_form.getTCProperty("s4prj_design").getStringArrayValue()[i],
								""+target_form.getTCProperty("s4pro_test").getStringArrayValue()[i],
								""+target_form.getTCProperty("s4tandc").getStringArrayValue()[i],
								""+target_form.getTCProperty("s4designcom").getStringArrayValue()[i],
								""+target_form.getTCProperty("s4cbareson").getStringArrayValue()[i],
								cosTable);
						}
					}
					
					else {
						
						for (int i = 0; i < 24; i++) {
							makeCostCell(
								str[i],"","","","","","",cosTable);
						}
					}
					

					document.add(table); 
					document.newPage();
					document.add(new Paragraph(""));
					document.add(new Paragraph("\n项目总费用：      "+
							target_form.getTCProperty("s4prj_cost").getStringValue()+"万元"));  
					document.add(cosTable); 




					document.close();  
					
			} catch (TCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (FileNotFoundException e) {  
				e.printStackTrace();  
			} catch (DocumentException e) {  
				e.printStackTrace();  
			} catch (IOException e) {  
				e.printStackTrace();  
			} 
	}
	
	private void makeCell(String content, boolean inMid,
			int rows, int cols, Table table) {
		
		if (content.equals(""+"null")) {
			content = "";
		}
		Cell cell = new Cell(content);  
		cell.setRowspan(rows);
		cell.setColspan(cols);
		table.addCell(cell);
		
		if (inMid) {
			cell.setUseAscender(true);
			cell.setVerticalAlignment(cell.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(cell.ALIGN_CENTER);
		}
		
	}
	
	private void makeCostCell(String s1, String s2,String s3,
			String s4,String s5,String s6,String s7,Table table) {
		
		
		makeCell(s1, false, 1, 2, table);
		makeCell(s2, true, 1, 1, table);
		makeCell(s3, true, 1, 1, table);
		makeCell(s4, true, 1, 1, table);
		makeCell(s5, true, 1, 2, table);
		makeCell(s6, true, 1, 1, table);
		makeCell(s7, true, 1, 2, table);
	}
	
	private void makeHumCell(String s1, String s2,String s3,
			String s4,String s5,String s6,String s7,Table table) {
		
		
		makeCell(s1, true, 1, 1, table);
		makeCell(s2, true, 1, 1, table);
		makeCell(s3, true, 1, 1, table);
		makeCell(s4, true, 1, 1, table);
		makeCell(s5, true, 1, 2, table);
		makeCell(s6, true, 1, 1, table);
		makeCell(s7, true, 1, 1, table);
	}
	

}

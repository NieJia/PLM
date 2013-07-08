package com.gdnz.sac1.form;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;
import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;

import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;


public class S4ExportWordCommand extends AbstractAIFCommand {

	private TCSession session = null;
	private TCComponentForm target_form;
	private String  exportPath;
	private JTextField jf;
    private String jfText;
	
	public S4ExportWordCommand(AbstractAIFUIApplication app) throws TCException {
		session = (TCSession) app.getSession();
		InterfaceAIFComponent target = app.getTargetComponent();
		if(target instanceof TCComponentForm){
			target_form=(TCComponentForm)target;
			String formType = target_form.getType();
			if(formType.equals("S4HDKJXMJZYBRevisionMaster")){
				 
				/* 
				    这是尤为重要的。因为JFileChooser默认的是选择文件，而需要选目录。 
				    故要将DIRECTORIES_ONLY装入模型 
				另外，若选择文件，则无需此句 
				*/    System.out.println("B");
				      JFileChooser fc = new JFileChooser(); 
				      fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
				      
			             fc.setCurrentDirectory(new File("C:/Users/Administrator/Documents"));
			             jf=getTextField(fc);
			             jf.setText("中国华电集团公司科技项目研究进展月报表");
			             jfText=jf.getText();
			             jf.addFocusListener(new FocusListener(){

							
							@Override
							public void focusGained(FocusEvent e) {
								// TODO Auto-generated method stub
								
							}
							@Override
							public void focusLost(FocusEvent e) {
								// TODO Auto-generated method stub
				            	 jfText=jf.getText();

							}
			            	
			            	 
			             });
			             fc.addPropertyChangeListener(new PropertyChangeListener(){

							@Override
							public void propertyChange(PropertyChangeEvent evt) {
								// TODO Auto-generated method stub
								jf.setText(jfText);	

								
							}
			             });
				      
				      int intRetVal = fc.showSaveDialog(null); 
				      if( intRetVal == JFileChooser.APPROVE_OPTION){ 
				    	  System.out.println("A");
				    	  exportPath = fc.getSelectedFile().getPath();
				    	
				    	  File file = new File(exportPath+".doc");
				    	  if (!file.exists()) {
				    		  S4HDKJXMJZYBRevisionMaster_exportWord(exportPath+".doc");
				    	  }
				    	  
				    	  else {
				    		  int n = JOptionPane.showConfirmDialog(null, "文件已存在，确认覆盖吗？",
				    				  "提示", JOptionPane.YES_NO_OPTION);
				    		  if (n == JOptionPane.YES_OPTION) {
				    			  S4HDKJXMJZYBRevisionMaster_exportWord(exportPath+".doc");
				    		  } 
				    	  }
				      }  
			}
			else if(formType.equals("S4SJGGRevisionMaster")){
				 System.out.println("C");
			      JFileChooser fc = new JFileChooser(); 
			      fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
			      
			      fc.setCurrentDirectory(new File("C:/Users/Administrator/Documents"));
		             jf=getTextField(fc);
		             jf.setText("设计更改申请表");
		             jfText=jf.getText();
		             jf.addFocusListener(new FocusListener(){

						
						@Override
						public void focusGained(FocusEvent e) {
							// TODO Auto-generated method stub
							
						}
						@Override
						public void focusLost(FocusEvent e) {
							// TODO Auto-generated method stub
			            	 jfText=jf.getText();

						}
		            	
		            	 
		             });
		             fc.addPropertyChangeListener(new PropertyChangeListener(){

						@Override
						public void propertyChange(PropertyChangeEvent evt) {
							// TODO Auto-generated method stub
							jf.setText(jfText);	

							
						}
		             });
		             
			      int intRetVal = fc.showSaveDialog(null); 
			      if( intRetVal == JFileChooser.APPROVE_OPTION){ 
			    	  exportPath = fc.getSelectedFile().getPath();
			    	  
			    	  File file = new File(exportPath+".doc");
			    	  if (!file.exists()) {
			    		  S4SJGGRevisionMaster_exportWord(exportPath+".doc");
			    	  }
			    	  else {
			    		  int n = JOptionPane.showConfirmDialog(null, "文件已存在，确认覆盖吗？",
			    				  "提示", JOptionPane.YES_NO_OPTION);
			    		  if (n == JOptionPane.YES_OPTION) {
			    			  S4SJGGRevisionMaster_exportWord(exportPath+".doc");
			    		  } 
			    	  }
			      } 
			
			}

			else if (formType.equals("S4YFprojXJMaster")){

				 System.out.println("D");
			      JFileChooser fc = new JFileChooser(); 
			      fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
			      
			      fc.setCurrentDirectory(new File("C:/Users/Administrator/Documents"));
		             jf=getTextField(fc);
		             jf.setText(target_form.getTCProperty("s4Prj_name").getStringValue());
		             jfText=jf.getText();
		             jf.addFocusListener(new FocusListener(){

						
						@Override
						public void focusGained(FocusEvent e) {
							// TODO Auto-generated method stub
							
						}
						@Override
						public void focusLost(FocusEvent e) {
							// TODO Auto-generated method stub
			            	 jfText=jf.getText();

						}
		            	
		            	 
		             });
		             fc.addPropertyChangeListener(new PropertyChangeListener(){

						@Override
						public void propertyChange(PropertyChangeEvent evt) {
							// TODO Auto-generated method stub
							jf.setText(jfText);	

							
						}
		             });
		             
			      int intRetVal = fc.showSaveDialog(null); 
			      if( intRetVal == JFileChooser.APPROVE_OPTION){
			    	  
			    	  exportPath = fc.getSelectedFile().getPath();
			    	  File file;
					
						file = new File(exportPath+".doc");
					
			    	  if (!file.exists()) {
				    	  new PrjWord(exportPath+".doc",target_form);
			    	  }
			    	  else {
			    		  int n = JOptionPane.showConfirmDialog(null, "文件已存在，确认覆盖吗？",
			    				  "提示", JOptionPane.YES_NO_OPTION);
			    		  if (n == JOptionPane.YES_OPTION) {
					    	  new PrjWord(exportPath+".doc",target_form);
			    		  } 
			    	  }
					
			      }	
			}
			
			MessageBox.post("word文件导出完毕。", "提示", MessageBox.INFORMATION);

		}
	}
	public void S4HDKJXMJZYBRevisionMaster_exportWord(String exPath) { 
		
		System.out.println("AA");
		Document document = new Document(PageSize.A4);

		
		try {  
			RtfWriter2.getInstance(document,
			new FileOutputStream(exPath));  
			document.open();  
 

 
			Paragraph ph = new Paragraph();  
			Font f  = new Font();  
 
			Paragraph p = new Paragraph("中国华电集团公司科技项目研究进展月报表", 
			new Font(Font.NORMAL, 14, Font.BOLD, new Color(0, 0, 0)) );  
			p.setAlignment(1);  
			document.add(p);  
			ph.setFont(f);  


			
			document.add(new Paragraph(""));  

	
			Table table = new Table(8,10);  

			table.setBorderWidth(1);  
			table.setBorderColor(Color.BLACK);  
			table.setPadding(0);  
			table.setSpacing(0);  
			
							
			Cell cName = new Cell("课题名称");
			cName.setRowspan(1);//当前单元格占两行,纵向跨度  
			cName.setColspan(2);
			cName.setUseAscender(true); 
			cName.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cName.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cName); 
			
			Cell cinName;
			cinName = new Cell(target_form.getTCProperty("s4prjName").getStringValue());
			cinName.setRowspan(1);//当前单元格占两行,纵向跨度  
			cinName.setColspan(6);
			table.addCell(cinName); 
			
			Cell cFirm = new Cell("承担单位");
			cFirm.setRowspan(1);//当前单元格占两行,纵向跨度  
			cFirm.setColspan(2);
			cFirm.setUseAscender(true); 
			cFirm.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cFirm.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cFirm); 
			
			Cell cinFirm = new Cell(target_form.getTCProperty("s4chargeFirm").getStringValue());
			cinFirm.setRowspan(1);//当前单元格占两行,纵向跨度  
			cinFirm.setColspan(2);
			table.addCell(cinFirm); 
			
			Cell cNum = new Cell("课题编号");
			cNum.setRowspan(1);//当前单元格占两行,纵向跨度  
			cNum.setColspan(2);
			cNum.setUseAscender(true); 
			cNum.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cNum.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cNum); 
			
			Cell cinNum = new Cell(target_form.getTCProperty("s4prjNumber").getStringValue());
			cinNum.setRowspan(1);//当前单元格占两行,纵向跨度  
			cinNum.setColspan(2);
			table.addCell(cinNum);
			
			Cell cLeader = new Cell("课题组长");
			cLeader.setRowspan(1);//当前单元格占两行,纵向跨度  
			cLeader.setColspan(2);
			cLeader.setUseAscender(true); 
			cLeader.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cLeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cLeader); 
			
			Cell cinLeader = new Cell(target_form.getTCProperty("s4prjLeader").getStringValue());
			cinLeader.setRowspan(1);//当前单元格占两行,纵向跨度  
			cinLeader.setColspan(2);
			table.addCell(cinLeader);
			
			Cell cPhone = new Cell("电话/手机");
			cPhone.setRowspan(1);//当前单元格占两行,纵向跨度  
			cPhone.setColspan(2);
			cPhone.setUseAscender(true); 
			cPhone.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cPhone.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cPhone); 
			
			Cell cinPhone = new Cell(target_form.getTCProperty("s4telephone").getStringValue());
			cinPhone.setRowspan(1);//当前单元格占两行,纵向跨度  
			cinPhone.setColspan(2);
			table.addCell(cinPhone);
			
			Cell cCost = new Cell("经费 \n使用 \n情况");
			cCost.setRowspan(4);//当前单元格占两行,纵向跨度  
			cCost.setColspan(1);
			cCost.setUseAscender(true); 
			cCost.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cCost.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cCost); 
			
			Cell cSum = new Cell("总投资");
			cSum.setRowspan(1);//当前单元格占两行,纵向跨度  
			cSum.setColspan(1);
			cSum.setUseAscender(true); 
			cSum.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cSum.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cSum); 
			
			Cell cinSum = new Cell(target_form.getTCProperty("s4totalInvest").getStringValue());
			cinSum.setRowspan(1);//当前单元格占两行,纵向跨度  
			cinSum.setColspan(2);
			table.addCell(cinSum); 
			 
			Cell cBudget = new Cell("计划金额");
			cBudget.setRowspan(1);//当前单元格占两行,纵向跨度  
			cBudget.setColspan(2);
			cBudget.setUseAscender(true); 
			cBudget.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cBudget.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cBudget); 
			
			Cell cinBudget = new Cell(target_form.getTCProperty("s4budget").getStringValue());
			cinBudget.setRowspan(1);//当前单元格占两行,纵向跨度  
			cinBudget.setColspan(2);
			table.addCell(cinBudget); 
		
			Cell cCount = new Cell("本年发生 \n累计");
			cCount.setRowspan(2);//当前单元格占两行,纵向跨度  
			cCount.setColspan(2);
			cCount.setUseAscender(true); 
			cCount.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cCount.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cCount); 
			
			Cell cPlan = new Cell("计划安排（单位：万元）");
			cPlan.setRowspan(1);//当前单元格占两行,纵向跨度  
			cPlan.setColspan(5);
			cPlan.setUseAscender(true); 
			cPlan.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cPlan.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cPlan); 
			
			
			table.addCell(new Paragraph("  小计")); 
			
			if (target_form.getTCProperty("s4month1").getStringArrayValue().length  > 0) {
			table.addCell(new Paragraph(""+target_form.getTCProperty("s4month1").getStringArrayValue()[0])); 
			}
			else {
				table.addCell(new Paragraph(" （ ）月")); 

			}
			if (target_form.getTCProperty("s4month2").getStringArrayValue().length  > 0) {

				table.addCell(new Paragraph(""+target_form.getTCProperty("s4month2").getStringArrayValue()[0])); 
			}
			else {
				table.addCell(new Paragraph(" （ ）月")); 

			}
			if (target_form.getTCProperty("s4month3").getStringArrayValue().length  > 0) {

				table.addCell(new Paragraph(""+target_form.getTCProperty("s4month3").getStringArrayValue()[0])); 
			}
			else {
				table.addCell(new Paragraph(" （ ）月")); 

			}
			if (target_form.getTCProperty("s4month4").getStringArrayValue().length  > 0) {

				table.addCell(new Paragraph(""+target_form.getTCProperty("s4month4").getStringArrayValue()[0])); 
			}
			else {
				table.addCell(new Paragraph(" （ ）月")); 

			}
			Cell cinCount = new Cell("  "+target_form.getTCProperty("s4countYear").getStringValue());
			cinCount.setRowspan(1);//当前单元格占两行,纵向跨度  
			cinCount.setColspan(2);
			table.addCell(cinCount); 
			
			
			table.addCell(new Paragraph("  "+target_form.getTCProperty("s4monthSum").getStringValue())); 
			if (target_form.getTCProperty("s4month1").getStringArrayValue().length  > 0) {
				table.addCell(new Paragraph(""+target_form.getTCProperty("s4month1").getStringArrayValue()[1])); 
				}
				else {
					table.addCell(new Paragraph("")); 

				}
				if (target_form.getTCProperty("s4month2").getStringArrayValue().length  > 0) {

					table.addCell(new Paragraph(""+target_form.getTCProperty("s4month2").getStringArrayValue()[1])); 
				}
				else {
					table.addCell(new Paragraph("")); 

				}
				if (target_form.getTCProperty("s4month3").getStringArrayValue().length  > 0) {

					table.addCell(new Paragraph(""+target_form.getTCProperty("s4month3").getStringArrayValue()[1])); 
				}
				else {
					table.addCell(new Paragraph("")); 

				}
				if (target_form.getTCProperty("s4month4").getStringArrayValue().length  > 0) {

					table.addCell(new Paragraph(""+target_form.getTCProperty("s4month4").getStringArrayValue()[1])); 
				}
				else {
					table.addCell(new Paragraph("")); 

				}


			Cell cSituation = new Cell("课题\n研究\n情况");
			cSituation.setRowspan(12);//当前单元格占两行,纵向跨度  
			cSituation.setColspan(1);
			cSituation.setUseAscender(true); 
			cSituation.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cSituation.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cSituation); 
			
			
			Cell cGain = new Cell("已取得的成果");
			cGain.setRowspan(3);//当前单元格占两行,纵向跨度  
			cGain.setColspan(2);
			cGain.setUseAscender(true); 
			cGain.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cGain.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cGain); 
			
			Cell cinGain =  new Cell(target_form.getTCProperty("s4currentGain").getStringValue());
			cinGain.setRowspan(3);//当前单元格占两行,纵向跨度  
			cinGain.setColspan(5);
			table.addCell(cinGain); 
			
			Cell cProblem = new Cell("存在的问题");
			cProblem.setRowspan(3);//当前单元格占两行,纵向跨度  
			cProblem.setColspan(2);
			cProblem.setUseAscender(true); 
			cProblem.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cProblem.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cProblem); 
			
			Cell cinProblem = new Cell(target_form.getTCProperty("s4existProblem").getStringValue());
			cinProblem.setRowspan(3);//当前单元格占两行,纵向跨度  
			cinProblem.setColspan(5);
			table.addCell(cinProblem); 
			
			Cell cNext = new Cell("下一步计划及措施");
			cNext.setRowspan(3);//当前单元格占两行,纵向跨度  
			cNext.setColspan(2);
			cNext.setUseAscender(true); 
			cNext.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cNext.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cNext); 
			
			Cell cinNext = new Cell(target_form.getTCProperty("s4nextPlan").getStringValue());
			cinNext.setRowspan(3);//当前单元格占两行,纵向跨度  
			cinNext.setColspan(5);
			table.addCell(cinNext); 
			
	
			Cell cTime = new Cell("预计完成时间");
			cTime.setRowspan(3);//当前单元格占两行,纵向跨度  
			cTime.setColspan(2);
			cTime.setUseAscender(true); 
			cTime.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cTime.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cTime); 
		
			
			Cell cinTime = new Cell(target_form.getTCProperty("s4deadline").getStringValue());
			cinTime.setRowspan(3);//当前单元格占两行,纵向跨度  
			cinTime.setColspan(5);
			table.addCell(cinTime); 
			
		
			document.add(table); 
			document.add(new Paragraph(""));  
			document.add(new Paragraph("           分管领导：             审核：                  课题组长："));  
			document.add(new Paragraph("           填报人：             电话/手机：                填报日期："));  


			document.close();  
			} catch (TCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (FileNotFoundException e) {  
				e.printStackTrace();  
			} catch (DocumentException e) {  
				e.printStackTrace();  
			} catch (IOException e) {  
				e.printStackTrace();  
			} 	
	}  
	public void S4SJGGRevisionMaster_exportWord(String exPath) { 
		System.out.println("AA");
		Document document = new Document(PageSize.A4); 
		
		try {  
			

			RtfWriter2.getInstance(document,
					new FileOutputStream(exPath));  
					document.open();  
	     
	   //设置合同头  
	     
					Paragraph ph = new Paragraph();  
					Font f  = new Font();  
	     
					Paragraph p = new Paragraph("国电南京自动化股份有限公司", 
					new Font(Font.NORMAL, 14, Font.BOLD, new Color(0, 0, 0)) );  
					p.setAlignment(1);  
					document.add(p);  
					ph.setFont(f);  
	    
	    //设置标题
					
					document.add(new Paragraph(""));  

	    
					Paragraph ph2 = new Paragraph();  
	      
					Paragraph p2 = new Paragraph("设计更改申请表", 
					new Font(Font.NORMAL, 16, Font.BOLD, new Color(0, 0, 0)) );  
					p2.setAlignment(1);  
					document.add(p2);  
					ph2.setFont(f);  
	  
					document.add(new Paragraph(""));  

					
					Table table = new Table(4,10);  
					document.add(new Paragraph("           编 号：                                             年  月  日"));  

					table.setBorderWidth(1);  
					table.setBorderColor(Color.BLACK);  
					table.setPadding(0);  
					table.setSpacing(0);  
	    
					table.addCell(new Paragraph("项目名称"));  
					
					Cell cinName = new Cell(target_form.getTCProperty("s4Prjname").getStringValue());  
					cinName.setRowspan(1);
					cinName.setColspan(3);
					table.addCell(cinName); 
					
					table.addCell(new Paragraph("项目令号"));
					table.addCell(new Paragraph(target_form.getTCProperty("s4Prjnumber").getStringValue()));  				
					table.addCell(new Paragraph("项目负责人")); 
					table.addCell(new Paragraph(target_form.getTCProperty("s4changereson").getStringValue()));  
					
					table.addCell(new Paragraph("设计单位")); 
					table.addCell(new Paragraph(target_form.getTCProperty("s4DesignD").getStringValue()));  
					table.addCell(new Paragraph("设计部门")); 
					table.addCell(new Paragraph(target_form.getTCProperty("s4DesignDpart").getStringValue()));  

	    // 表格的主体  
					Cell cell = new Cell("更改原因：\n" +target_form.getTCProperty("s4changereson").getStringValue()+"\n");  
					cell.setRowspan(2);//当前单元格占两行,纵向跨度  
					cell.setColspan(4);
					table.addCell(cell); 
	    
					Cell cWay = new Cell("建议更改方法：\n"+target_form.getTCProperty("s4changeway").getStringValue()+"\n");  
			
					cWay.setRowspan(2);//当前单元格占两行,纵向跨度  
					cWay.setColspan(4);
					table.addCell(cWay); 
	    
					Cell cDoc = new Cell("需要更改的图纸、文件：\n"+target_form.getTCProperty("s4needdocmen").getStringValue()+"\n");  
					cDoc.setRowspan(2);//当前单元格占两行,纵向跨度  
					cDoc.setColspan(4);
					table.addCell(cDoc);
					
					Cell cImpact = new Cell("影响性分析及处理意见\n"+
							"需求范围: \n"+target_form.getTCProperty("s4demand").getStringValue()+"\n\n测试方案与测试用例: \n"
							+target_form.getTCProperty("s4testcase").getStringValue()+"\n\n关联模板与系统设计: \n"+target_form.getTCProperty("s4design").getStringValue()	+"\n"
							);  
					cImpact.setRowspan(2);//当前单元格占两行,纵向跨度  
					cImpact.setColspan(4);
					table.addCell(cImpact);
	    
					Cell cRemark = new Cell("附注：\n"+target_form.getTCProperty("s4remarks").getStringValue()+"\n");  
					cRemark.setRowspan(2);//当前单元格占两行,纵向跨度  
					cRemark.setColspan(4);
					table.addCell(cRemark);
	    
					document.add(table); 
					document.add(new Paragraph(""));   
					document.add(new Paragraph("           申请人：                 审核人：                  批准人："));  


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
	
	public JTextField getTextField(Container c){
		JTextField textField =null;
		for(int i=0;i<c.getComponentCount();i++){
			Component cnt=c.getComponent(i);
				if(cnt instanceof JTextField){
					return (JTextField)cnt;
				}
				if(cnt instanceof Container){
					textField=getTextField((Container)cnt);
					if(textField !=null)
						return textField;
				}
			}			
		return textField;
	}
	
}


package com.origin.rac.sac.exportdefect;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import cn.com.origin.util.ProgressBarThread;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.common.Activator;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCFormProperty;
import com.teamcenter.rac.kernel.TCProperty;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class DefectMemberCommand extends AbstractAIFCommand{
	
	private TCSession session = null;
	public TCComponent target = null;
	private String releation = "IMAN_reference";
	private TCComponentFolder folder ;
	private String releation1 = "IMAN_master_form";
	private String releation2 = "IMAN_master_form_rev";
	private String formType ="S4CSQXBGRevisionMaster";
	private String formType1="S4YFprojXJMaster";
	private String folder_name = "缺陷报告";
	private TCFormProperty ddformProperties[] = null;
	private TCProperty[] formProperties = null;
	//缺陷报告表单的属性
	private String 	 defectid=null;		//缺陷编号
	private String	 reporter=null;		//报告人
	private Date   	 reportdate=null;   //报告日期
	private String   reportdate1=null;  //string型的报告日期
	private String   assets=null;       //所属产品
	private String   testobj=null;      //被测对象
	private String   nowrevision=null;  //当前版本号
	private String   state=null;		//状态
	private String   defectdescrip=null;//缺陷描述
	private String   defectclass=null;  //缺陷分类
	private String   severerank=null;   //严重等级
	private String   dorank=null;       //处理优先级
	private String   mendsuggest=null;  //修复建议
	private String   solverevision=null;//解决后版本号
	private String   solveperson=null;  //解决人员
	private String   changerecord=null;  //更改记录
	private String   explanation=null;  //缺陷修复注释
	private String   mendexplanation=null; //回归测试注释
	private String   addition="";     //附件
	private String   addition1[]=null;   //附件的数组
	private int   count=0;              //附件的个数
	private static String exportexcel=null;
	private String exportexcel1=null;
	private String filename1=null;
	//private static File file=null;
	private boolean flag=false;
	private boolean flag1=false;
	//private WritableSheet ws=null;
	private int h=1;
	private ProgressBarThread progressBarThread = null;
	private String text_content="缺陷汇总表";
	

	public DefectMemberCommand(AbstractAIFUIApplication application) throws TCException {
		// TODO Auto-generated constructor stub
		
		target = (TCComponent) application.getTargetComponent();
		if (target == null || !(target instanceof TCComponentItem)) {
			MessageBox.post("请选择零组件对象后重试!", "提示", MessageBox.INFORMATION);
			return;
		}else{
			
					TCComponentItem item = (TCComponentItem) target;
					TCComponentForm master_form = (TCComponentForm) item.getRelatedComponent(releation1);
					System.out.println("master_form==>"+master_form.getType());
					if(formType1.equals(master_form.getType()))
					{
						session = (TCSession) application.getSession();
						System.out.println("此时的target为"+target);
						TCComponent[] coms = item.getRelatedComponents(releation);
						System.out.println("coms--->:"+coms.length);		
						WritableFont titleFont = new WritableFont(WritableFont
							.createFont("幼圆"), 26, WritableFont.BOLD);
						WritableFont contentFont = new WritableFont(WritableFont
							.createFont("宋体"), 16, WritableFont.BOLD);   
						WritableFont contentFont1=new WritableFont(WritableFont
							.createFont("楷体 _GB2312"),12,WritableFont.NO_BOLD);
        
						WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
						WritableCellFormat contentFormat = new WritableCellFormat(
							contentFont);                 
						WritableCellFormat contentFormat2 = new WritableCellFormat(
							contentFont1);             //表格中文字的书写格式       
						
						try{
							//ExcelHandle excelHandle = new ExcelHandle();	
							//exportexcel();
							//contentFormat2.setAlignment(jxl.format.Alignment.CENTRE);	
							contentFormat2.setBorder(Border.ALL, BorderLineStyle.THIN,
				                    Colour.BLACK);
							if(coms!=null && coms.length>0){
								System.out.println("123");
								for (int i = 0; i < coms.length; i++) {	
									folder = (TCComponentFolder) coms[i];
									TCComponent[] folders=folder.getRelatedComponents("contents");
									for(int j=0;j<folders.length;j++){
										//System.out.println()
										TCComponentItem item1 = (TCComponentItem) folders[j];
										TCComponentItemRevision item_rev = item1.getLatestItemRevision();
										TCComponentForm tccomponentForm =(TCComponentForm)item_rev.getRelatedComponent(releation2);
										if(formType.equals(tccomponentForm.getType())){
											flag1=true;
									}
								}
							}
							if(flag1==true){
								System.out.println("flag1==>"+flag1);
								JFileChooser fc=new JFileChooser();
								fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);			//此句尤为重要，定义了JFileChooser选择文件的类型
								fc.setDialogTitle("导出excel");
								final JTextField text;
								text=getTextField(fc);
								text.setText("缺陷汇总表");
								
								text.addFocusListener(new FocusListener(){

									@Override
									public void focusGained(FocusEvent e) {
										// TODO Auto-generated method stub
										
									}

									@Override
									public void focusLost(FocusEvent e) {
										// TODO Auto-generated method stub
										text_content=text.getText();
									}
									
									
									
								});
								fc.addPropertyChangeListener(new PropertyChangeListener(){
									public void propertyChange(PropertyChangeEvent event)
									{
										
										text.setText(text_content);
									}

									public void actionPerformed(ActionEvent e) {
										// TODO Auto-generated method stub
										System.out.println("456");
									}
								});
								//fc.setSelectedFile(new File("缺陷汇总表"));
								int intRetVal=fc.showSaveDialog(null);
						
								if(intRetVal==JFileChooser.APPROVE_OPTION){
									exportexcel=fc.getSelectedFile().getPath();
									//fc.setName("缺陷huizong");
									//fc.removeActionListener();
									//fc.setFileFilter(new FileNameExtensionFilter("xls",saveType));
									System.out.println("exportexcel==>"+exportexcel);
							//		File file=new File(exportexcel+"/缺陷汇总表.xls");
									
									File file=new File(fc.getSelectedFile().getParent(),fc.getSelectedFile().getName()+".xls");
									//fc.setSelectedFile(new File("缺陷汇总表"));
									WritableWorkbook wwb=null;
									if(!file.exists()){
										wwb = Workbook
										.createWorkbook(file);
										// 创建Excel工作表 指定名称和位置
										System.out.println("file==>"+file);        
										WritableSheet ws = wwb.createSheet("Sheet1", 0);
										writeExcelfirst(file,ws);
										flag=true;
										System.out.println("文件不存在");	
										System.out.println("此时的 coms.length==>"+coms.length);
										progressBarThread = new ProgressBarThread("提示", "文档正在导出，请稍后...");
										progressBarThread.start();
										for (int i = 0; i < coms.length; i++) {				
											folder = (TCComponentFolder) coms[i];	
											TCComponent[] folders=folder.getRelatedComponents("contents");
											for(int j=0;j<folders.length;j++){					
												System.out.println("folders==>"+folders[j].toString());
												TCComponentItem item1 = (TCComponentItem) folders[j];
												TCComponentItemRevision item_rev = item1.getLatestItemRevision();
												System.out.println("TCComponentItemRevision=="+item_rev.toString());
												TCComponentForm tccomponentForm =(TCComponentForm)item_rev.getRelatedComponent(releation2); //在这里估计是表单后面带斜杠的都是用relation2来获得属性值
												System.out.println("tccomponentForm==>"+tccomponentForm.toString());
												if(formType.equals(tccomponentForm.getType())){                           
													Vector<String> v_all=new Vector<String>();
													System.out.println(tccomponentForm.getTCProperty("s4reporter"));
													String str1= tccomponentForm.getTCProperty("s4reporter").getStringValue();
													reporter =  tccomponentForm.getProperty("s4reporter");     //报告日期
													if(tccomponentForm.getTCProperty("s4repdate1").getDateValue()==null)         //对时间的赋值
														//DateFormat df=new SimpleDateFormat("yyyy/MM/dd");
														//strRepDate=df.format(new Date());
														reportdate=new Date();
													else
													{
														
														reportdate = tccomponentForm.getTCProperty("s4repdate1").getDateValue();
														System.out.println("reportdate==>"+reportdate.toString());
													}
													reportdate1=new SimpleDateFormat("yyyy/MM/dd").format(reportdate);
													System.out.println("reportdate1"+reportdate1);				//被测对象
													assets=tccomponentForm.getProperty("s4Assets").toString();
													testobj = tccomponentForm.getProperty("s4measurand");					//被测对象																	//当前版本号
													nowrevision = tccomponentForm.getProperty("s4nowversion");
													System.out.println("nowrevision"+nowrevision);							      
													defectdescrip = tccomponentForm.getProperty("s4Description");                  //缺陷描述
													System.out.println("defectdescrip"+defectdescrip);																									
													defectclass =tccomponentForm.getProperty("s4DefectClass");				//缺陷分类
													System.out.println("defectclass"+defectclass);							       
													severerank = tccomponentForm.getProperty("s4Severlevel");			//严重等级
													System.out.println("severerank"+severerank);						
													dorank =tccomponentForm.getProperty("s4proprior");  			//处理优先级
													System.out.println("dorank"+dorank);
													mendsuggest = tccomponentForm.getProperty("s4suggestrepa"); 					//修复建议
													System.out.println("mendsuggest"+mendsuggest);							            
													state = tccomponentForm.getProperty("s4statuss");           //状态
													System.out.println("state"+state);							        
													solverevision = tccomponentForm.getProperty("s4solvversion");  //解决后的版本号
													System.out.println("solverevision"+solverevision);							         
													solveperson =tccomponentForm.getProperty("s4Resolvingpro");       //解决人员
													System.out.println("solveperson"+solveperson);							      	 
													changerecord = tccomponentForm.getProperty("s4ChangeRe"); //更改记录
													System.out.println("changerecord"+changerecord);							        	
													explanation = tccomponentForm.getProperty("s4exegesis"); //缺陷修复注释
													System.out.println("explanation"+explanation);							            
													mendexplanation = tccomponentForm.getProperty("s4exegesis1"); //回归测试注释
													System.out.println("mendexplanation"+mendexplanation);							        
													count=tccomponentForm.getTCProperty("s4acc").getStringValueArray().length;; //附件
													addition1=tccomponentForm.getTCProperty("s4acc").getStringValueArray();
													for(int m=0;m<count;m++){
														addition=addition+addition1[m]+"; ";
													}
													System.out.println("addition"+addition);
													System.out.println("flag==>"+flag);
													/*if(flag=true){
														exportexcelsecond();
													}*/
													//exportexcel(reporter,reportdate,testobj,nowrevision,defectdescrip,defectclass,severerank,dorank,mendsuggest,state,solverevision,changerecord,explanation,mendexplanation,addition);
													h++;
													ws.addCell(new Label(0,h,tccomponentForm.toString(),contentFormat2));  //缺陷编号
													System.out.println("此时的tccomponentForm.toString"+tccomponentForm.toString());
													ws.addCell(new Label(1,h,reporter,contentFormat2));    //报告人
													System.out.println("此时的reporter==>"+reporter);
													ws.addCell(new Label(2,h,reportdate1,contentFormat2));  //报告日期
													ws.addCell(new Label(3,h,assets,contentFormat2));      //所属产品
													ws.addCell(new Label(4,h,testobj,contentFormat2));     //被测对象
													ws.addCell(new Label(5,h,nowrevision,contentFormat2)); //当前版本号
													ws.addCell(new Label(6,h,state,contentFormat2));       //状态
													ws.addCell(new Label(7,h,defectdescrip,contentFormat2)); //缺陷描述
													ws.addCell(new Label(8,h,defectclass,contentFormat2));   //缺陷分类
													ws.addCell(new Label(9,h,severerank,contentFormat2));    //严重等级
													ws.addCell(new Label(10,h,dorank,contentFormat2));       //处理优先级
													ws.addCell(new Label(11,h,mendsuggest,contentFormat2));   //修复建议
													ws.addCell(new Label(12,h,solverevision,contentFormat2));   //解决后的版本号
													ws.addCell(new Label(13,h,solveperson,contentFormat2));     //解决人员
													ws.addCell(new Label(14,h,changerecord,contentFormat2));    //更改记录
													ws.addCell(new Label(15,h,explanation,contentFormat2));    //缺陷修复注释
													ws.addCell(new Label(16,h,mendexplanation,contentFormat2));    //回归测试注释
													ws.addCell(new Label(17,h,addition,contentFormat2));      //附件
							            	
													System.out.println("此时的h==>"+h);
													addition="";
										
												}
											
											
											
											
										}

									}
								
								
										wwb.write();									
										wwb.close();
										progressBarThread.stopBar();
										MessageBox.post("文档导出成功!", "提示", MessageBox.INFORMATION);
								//	wwb.write();	
								//	MessageBox.post("文档导出成功!", "提示", MessageBox.INFORMATION);
								}
								else{
									int  n=JOptionPane.showConfirmDialog(null, "文件已存在，确认覆盖吗？","提示",JOptionPane.YES_NO_OPTION);
									if(n==JOptionPane.YES_OPTION){
										//JOptionPane.
										flag=true;
										progressBarThread = new ProgressBarThread("提示", "文档正在导出，请稍后...");
										progressBarThread.start();
										//	writeExcelfirst(file,ws);
										//	writeExcelsecond(file,ws);
										System.out.println("文件已存在");
										wwb = Workbook
											.createWorkbook(file);
										// 创建Excel工作表 指定名称和位置
										System.out.println("file==>"+file);        
										WritableSheet ws = wwb.createSheet("Sheet1", 0);
										writeExcelfirst(file,ws);
										flag=true;
										System.out.println("文件不存在");															
										for (int i = 0; i < coms.length; i++) {				
											folder = (TCComponentFolder) coms[i];	
											TCComponent[] folders=folder.getRelatedComponents("contents");
											for(int j=0;j<folders.length;j++){					
												System.out.println("folders==>"+folders[j].toString());
												TCComponentItem item1 = (TCComponentItem) folders[j];
												TCComponentItemRevision item_rev = item1.getLatestItemRevision();
												System.out.println("TCComponentItemRevision=="+item_rev.toString());
												TCComponentForm tccomponentForm =(TCComponentForm)item_rev.getRelatedComponent(releation2); //在这里估计是表单后面带斜杠的都是用relation2来获得属性值
												System.out.println("tccomponentForm==>"+tccomponentForm.toString());
												if(formType.equals(tccomponentForm.getType())){
													Vector<String> v_all=new Vector<String>();
													System.out.println(tccomponentForm.getTCProperty("s4reporter"));
													String str1= tccomponentForm.getTCProperty("s4reporter").getStringValue();
													reporter =  tccomponentForm.getProperty("s4reporter");     //报告日期	
													if(tccomponentForm.getTCProperty("s4repdate1").getDateValue()==null)         //对时间的赋值
														//DateFormat df=new SimpleDateFormat("yyyy/MM/dd");
														//strRepDate=df.format(new Date());
														reportdate=new Date();
													else
													{
														
														reportdate = tccomponentForm.getTCProperty("s4repdate1").getDateValue();
														System.out.println("reportdate==>"+reportdate.toString());
													}												
													//reportdate = tccomponentForm.getTCProperty("s4repdate1").getDateValue();
													reportdate1=new SimpleDateFormat("yyyy/MM/dd").format(reportdate);
													System.out.println("reportdate1"+reportdate1);				//被测对象
													assets=tccomponentForm.getProperty("s4Assets").toString();
													testobj = tccomponentForm.getProperty("s4measurand");					//被测对象																	//当前版本号
													nowrevision = tccomponentForm.getProperty("s4nowversion");
													System.out.println("nowrevision"+nowrevision);							      
													defectdescrip = tccomponentForm.getProperty("s4Description");                  //缺陷描述
													System.out.println("defectdescrip"+defectdescrip);																									
													defectclass =tccomponentForm.getProperty("s4DefectClass");				//缺陷分类
													System.out.println("defectclass"+defectclass);							       
													severerank = tccomponentForm.getProperty("s4Severlevel");			//严重等级
													System.out.println("severerank"+severerank);						
													dorank =tccomponentForm.getProperty("s4proprior");  			//处理优先级
													System.out.println("dorank"+dorank);
													mendsuggest = tccomponentForm.getProperty("s4suggestrepa"); 					//修复建议
													System.out.println("mendsuggest"+mendsuggest);							            
													state = tccomponentForm.getProperty("s4statuss");           //状态
													System.out.println("state"+state);							        
													solverevision = tccomponentForm.getProperty("s4solvversion");  //解决后的版本号
													System.out.println("solverevision"+solverevision);							         
													solveperson =tccomponentForm.getProperty("s4Resolvingpro");       //解决人员
													System.out.println("solveperson"+solveperson);							      	 
													changerecord = tccomponentForm.getProperty("s4ChangeRe"); //更改记录
													System.out.println("changerecord"+changerecord);							        	
													explanation = tccomponentForm.getProperty("s4exegesis"); //缺陷修复注释
													System.out.println("explanation"+explanation);							            
													mendexplanation = tccomponentForm.getProperty("s4exegesis1"); //回归测试注释
													System.out.println("mendexplanation"+mendexplanation);							        
													count=tccomponentForm.getTCProperty("s4acc").getStringValueArray().length;; //附件
													addition1=tccomponentForm.getTCProperty("s4acc").getStringValueArray();
													for(int m=0;m<count;m++){
														addition=addition+addition1[m]+"; ";
													}
													System.out.println("addition"+addition);
													System.out.println("flag==>"+flag);
													/*if(flag=true){
														exportexcelsecond();
													}*/
										//exportexcel(reporter,reportdate,testobj,nowrevision,defectdescrip,defectclass,severerank,dorank,mendsuggest,state,solverevision,changerecord,explanation,mendexplanation,addition);
													h++;
													ws.addCell(new Label(0,h,tccomponentForm.toString(),contentFormat2));  //缺陷编号
													System.out.println("此时的tccomponentForm.toString"+tccomponentForm.toString());
									            	ws.addCell(new Label(1,h,reporter,contentFormat2));    //报告人
									            	System.out.println("此时的reporter==>"+reporter);
									            	ws.addCell(new Label(2,h,reportdate1,contentFormat2));  //报告日期
									            	ws.addCell(new Label(3,h,assets,contentFormat2));      //所属产品
									            	ws.addCell(new Label(4,h,testobj,contentFormat2));     //被测对象
									            	ws.addCell(new Label(5,h,nowrevision,contentFormat2)); //当前版本号
									            	ws.addCell(new Label(6,h,state,contentFormat2));       //状态
									            	ws.addCell(new Label(7,h,defectdescrip,contentFormat2)); //缺陷描述
									            	ws.addCell(new Label(8,h,defectclass,contentFormat2));   //缺陷分类
									            	ws.addCell(new Label(9,h,severerank,contentFormat2));    //严重等级
									            	ws.addCell(new Label(10,h,dorank,contentFormat2));       //处理优先级
									            	ws.addCell(new Label(11,h,mendsuggest,contentFormat2));   //修复建议
									            	ws.addCell(new Label(12,h,solverevision,contentFormat2));   //解决后的版本号
									            	ws.addCell(new Label(13,h,solveperson,contentFormat2));     //解决人员
									            	ws.addCell(new Label(14,h,changerecord,contentFormat2));    //更改记录
									            	ws.addCell(new Label(15,h,explanation,contentFormat2));    //缺陷修复注释
									            	ws.addCell(new Label(16,h,mendexplanation,contentFormat2));    //回归测试注释
									            	ws.addCell(new Label(17,h,addition,contentFormat2));      //附件
									            	
									            	System.out.println("此时的h==>"+h);
									            	addition="";
												
												}
											}

										}																				
										/*wwb.write();
										MessageBox.post("文档导出成功!", "提示", MessageBox.INFORMATION);*/
										wwb.write();									
										wwb.close();
										progressBarThread.stopBar();
										MessageBox.post("文档导出成功!", "提示", MessageBox.INFORMATION);
									
								}
								else if(n==JOptionPane.NO_OPTION)
								{
								     flag=false;
								     System.out.println("你选择了不覆盖！");
								}
						 }
								/*	wwb.write();									
									wwb.close();
									MessageBox.post("文档导出成功!", "提示", MessageBox.INFORMATION);*/
					}		    //这一个括号对应着什么		
				}
					else{
							MessageBox.post("找不到缺陷报告对象!", "提示", MessageBox.INFORMATION);
						}
							
							
							
			}
		else{
			MessageBox.post("找不到相应对象,请检查!", "提示", MessageBox.INFORMATION);
			}						
				
		}catch(Exception e){
			e.printStackTrace();
			MessageBox.post("找不到相应对象,请检查!", "提示", MessageBox.INFORMATION);
		}
		}else{
			MessageBox.post("请选择研发项目表单,请检查!", "提示", MessageBox.INFORMATION);		
			}
		}
		
	}
		
    private JTextField getTextField(Container c) {
		JTextField textfield=null;
		for(int i=0;i<c.getComponentCount();i++){
			Component cnt=c.getComponent(i);
			if(cnt instanceof JTextField){
				return (JTextField)cnt;
			}
			if(cnt instanceof Container){
				textfield=getTextField((Container)cnt);
				if(textfield!=null){
					return textfield;
				}
			}
		}
		return textfield;
	}

	public void writeExcelfirst(File file, WritableSheet ws) {
        try {   
            ws.setColumnView(0, 30);//第一列宽14   缺陷编号
            ws.setColumnView(1, 12);   //报告人
            ws.setColumnView(2, 25);   //报告日期
            ws.setColumnView(3, 20);   //所属产品
            ws.setColumnView(4, 20);   //被测对象
            ws.setColumnView(5, 20);   //当前版本号
            ws.setColumnView(6, 20);   //状态
            ws.setColumnView(7, 20);   //缺陷描述
            ws.setColumnView(8, 26);   //缺陷分类
            ws.setColumnView(9, 20);   //严重等级
            ws.setColumnView(10,20);   //处理优先级
            ws.setColumnView(11,20);   //修复建议
            ws.setColumnView(12, 30);  //解决后版本号
            ws.setColumnView(13, 60);   //解决人
            ws.setColumnView(14, 20);  //更改记录
            ws.setColumnView(15, 30);  //缺陷修复注释
            ws.setColumnView(16, 30); //回归测试注释
            ws.setColumnView(17, 45);  //附件
            

// **************往工作表中添加数据*****************

//定义字体格式：字体为：幼圆，26号，加粗
            WritableFont titleFont = new WritableFont(WritableFont
                    .createFont("幼圆"), 26, WritableFont.BOLD);
            WritableFont contentFont = new WritableFont(WritableFont
                    .createFont("宋体"), 16, WritableFont.BOLD);   
            WritableFont contentFont1=new WritableFont(WritableFont
            		.createFont("楷体 _GB2312"),12,WritableFont.NO_BOLD);
            
            WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
            WritableCellFormat contentFormat = new WritableCellFormat(
                    contentFont);                 
            WritableCellFormat contentFormat2 = new WritableCellFormat(
                    contentFont1);             //表格中文字的书写格式

            contentFormat.setBorder(Border.ALL, BorderLineStyle.THIN,
                    Colour.BLACK);
            //设置格式居中对齐
            titleFormat.setAlignment(jxl.format.Alignment.CENTRE);
            contentFormat.setAlignment(jxl.format.Alignment.CENTRE);
           // contentFormat.setBackground(jxl.format.Colour.BLUE);
            contentFormat2.setAlignment(jxl.format.Alignment.CENTRE);

            // ***************将定义好的单元格添加到工作表中*****************
            ws.mergeCells(0, 0, 18, 0);// 合并单元格A-G共7列
            ws.addCell(new Label(0, 0, "项目缺陷汇总表", titleFormat));
            ws.addCell(new Label(0, 1, "缺陷编号", contentFormat));
            ws.addCell(new Label(1,1,"报告人",contentFormat));
            ws.addCell(new Label(2,1,"报告日期",contentFormat));
            ws.addCell(new Label(3,1,"所属产品",contentFormat));
            ws.addCell(new Label(4,1,"被测对象",contentFormat));
            ws.addCell(new Label(5,1,"当前版本号",contentFormat));
            ws.addCell(new Label(6,1,"状态",contentFormat));
            ws.addCell(new Label(7,1,"缺陷描述",contentFormat));
            ws.addCell(new Label(8,1,"缺陷分类",contentFormat));
            ws.addCell(new Label(9,1,"严重等级",contentFormat));
            ws.addCell(new Label(10,1,"处理优先级",contentFormat));
            ws.addCell(new Label(11,1,"修复建议",contentFormat));
            ws.addCell(new Label(12,1,"解决后版本号",contentFormat));
            ws.addCell(new Label(13,1,"解决人",contentFormat));
            ws.addCell(new Label(14,1,"更改记录",contentFormat));
            ws.addCell(new Label(15,1,"缺陷修复注释",contentFormat));
            ws.addCell(new Label(16,1,"回归测试注释",contentFormat));
            ws.addCell(new Label(17,1,"附件",contentFormat));         
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

package com.gdnz.sac1.menu;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


import cn.com.origin.util.ProgressBarThread;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class ExportWuLiaoExcel {
	private AbstractAIFUIApplication application;
	private TCSession session;
	private TCComponent target =null;
	private TCComponentFolder target_folder =null;
	private String releation0 = "IMAN_reference";
	private String releation1 = "IMAN_master_form";
	private String releation2 = "IMAN_master_form_rev";
    private WritableWorkbook wwb;
    private WritableSheet ws;
    private WritableCellFormat contentFormat ;
    private WritableCellFormat titleFormat;
    private WritableCellFormat titleFormat1;
	private ProgressBarThread progressBarThread = null;
    private JTextField jf;
    private String jfText;
	public ExportWuLiaoExcel(AbstractAIFApplication application2) throws TCException{
		target=(TCComponent)application2.getTargetComponent();
		if(target==null){
			MessageBox.post("未选择对应的对象","提示",MessageBox.INFORMATION);
		}
		else if(target instanceof TCComponentFolder){
			try {
	             JFileChooser fc=new JFileChooser();
	             fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	             fc.setDialogTitle("请选择文件保存路径");
	             fc.setCurrentDirectory(new File("C:/Users/Administrator/Documents"));
	             jf=getTextField(fc);
	             jf.setText("物料汇总");
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
	        //   fc.setApproveButtonText("确定");
	        //   int intRetVal =fc.showSaveDialog(null);
	             int intRetVal =fc.showDialog(null,"保存文件");
	             if(intRetVal==JFileChooser.APPROVE_OPTION){
	             if((new File(fc.getSelectedFile().getParent(),fc.getSelectedFile().getName()+".xls")).exists()){
	          //   if((new File(fc.getSelectedFile().getPath()+"/物料汇总.xls")).exists()){
	            	 int result=JOptionPane.showConfirmDialog(null,"文件已存在，您确定要覆盖原文件","文件提示",JOptionPane.YES_NO_OPTION);
	                 if(result==JOptionPane.NO_OPTION){
	                	 return;}
	             }
	             progressBarThread = new ProgressBarThread("提示", "正在导出Excel文件，请稍后...");
				 progressBarThread.start();
			     //写入到那个Excel文件
				//	wwb = Workbook.createWorkbook(new File(fc.getSelectedFile().getPath()+"/物料汇总.xls"));
				 wwb = Workbook.createWorkbook(new File(fc.getSelectedFile().getParent(),fc.getSelectedFile().getName()+".xls"));

	             // 创建Excel工作表sheet 指定名称和位置
	             ws = wwb.createSheet("02 烛泡 拉尾泡 树型泡", 0);

	             
	            // **************往工作表中添加数据*****************
	            //定义标题字体格式：字体为：微软雅黑，11号子，加粗
	             WritableFont titleFont = new WritableFont(WritableFont.createFont("微软雅黑"), 11, WritableFont.BOLD);
	             WritableFont titleFont1 = new WritableFont(WritableFont.createFont("微软雅黑"), 11, WritableFont.NO_BOLD);
	        //     WritableFont contentFont = new WritableFont(WritableFont.createFont("楷体 _GB2312"), 12, WritableFont.NO_BOLD);
	           //定义正文字体格式：字体为：宋体，10号子，不加粗
	             WritableFont contentFont = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD);

	             titleFormat = new WritableCellFormat(titleFont);
	             titleFormat1 = new WritableCellFormat(titleFont1);
	             contentFormat = new WritableCellFormat(contentFont);
	    //       WritableCellFormat contentFormat2 = new WritableCellFormat(contentFont);

	             //设置格式居左对齐
	             titleFormat.setAlignment(jxl.format.Alignment.LEFT);
	             titleFormat1.setAlignment(jxl.format.Alignment.LEFT);
	             contentFormat.setAlignment(jxl.format.Alignment.LEFT);
				 contentFormat.setBorder(Border.ALL, BorderLineStyle.THIN,Colour.BLACK);
	   //        contentFormat2.setBackground(Colour.GREY_25_PERCENT);
	 
	             String titleheaders[] = { "物料编码", "物料描述", "物料名称", "物料状态", "生产厂商", "订货代码", "电源电压" ,"CT电流","库存物料","主要单位","允许更新说明","是否原型物料","物料模板","工序号",
	            		                   "组织","默认采购员","计划员","提前期_固定","SAC_库存类别集","SAC_财务类别集","SAC_计划类别集","SAC_产品类别集","子库存","货位","固定供应天数","固定批量增加","库存计划方法","价目表价格","重量单位","单位重量","体积单位","单位体积",
	            		                   "组织","默认采购员","计划员","提前期_固定","SAC_库存类别集","SAC_财务类别集","SAC_计划类别集","SAC_产品类别集","子库存","货位","固定供应天数","固定批量增加","库存计划方法","价目表价格","重量单位","单位重量","体积单位","单位体积"
	                                     };
	             for (int i = 0; i < titleheaders.length; i++) {
		             // 设置表格的列宽度
		             ws.setColumnView(i, 25);//每一列宽25
		             if(i==0) 
		            	 ws.addCell(new Label(0, 0, titleheaders[i], titleFormat));
		             else
	                     ws.addCell(new Label(i, 0, titleheaders[i], titleFormat1));
	             }
	             ws.setColumnView(1, 80);//第2列宽80
	             ws.setColumnView(24, 40);
	             ws.setColumnView(25, 40);

			target_folder=(TCComponentFolder)target;
			TCComponent[] items=target_folder.getRelatedComponents("contents");
			if(items!=null && items.length>0){
	             //向Excel中插入数据
				for(int i=0;i<items.length;i++){
					TCComponentItem item=(TCComponentItem)items[i];
					TCComponentItemRevision item_rev=item.getLatestItemRevision();
					TCComponentForm form=(TCComponentForm)item_rev.getRelatedComponent(releation2);
			//		if(form.getType().equals("S4ELECCRevisionMaster")){
					 ws.addCell(new Label(0,1+i,item.getTCProperty("item_id").getStringValue(), contentFormat));//物料编码
	                 ws.addCell(new Label(1,1+i,form.getTCProperty("s4Mdescription").getStringValue() , contentFormat));
	                 ws.addCell(new Label(2,1+i,item.getTCProperty("object_name").getStringValue(), contentFormat));//物料名称
	                 ws.addCell(new Label(3,1+i,form.getTCProperty("s4Item_Status").getStringValue() , contentFormat));
	                 ws.addCell(new Label(4,1+i,form.getTCProperty("s4vendor").getStringValue(), contentFormat));
	                 ws.addCell(new Label(5,1+i,form.getTCProperty("s4contact_maker").getStringValue(), contentFormat));
	                 ws.addCell(new Label(6,1+i,form.getTCProperty("s4Supply_vol").getStringValue(), contentFormat));
	                 ws.addCell(new Label(7,1+i,form.getTCProperty("s4CT_current").getStringValue(), contentFormat));
	                 ws.addCell(new Label(8,1+i,form.getTCProperty("s4Inventory_Item").getStringValue() , contentFormat));
	                 ws.addCell(new Label(9,1+i,form.getTCProperty("s4Primary_Unit_of_M").getStringValue() , contentFormat));
	                 ws.addCell(new Label(10,1+i,form.getTCProperty("s4Allow_Description_U").getStringValue() , contentFormat));
	                 ws.addCell(new Label(11,1+i,form.getTCProperty("s4Wpromaterials").getStringValue() , contentFormat));//是否原型物料
	                 ws.addCell(new Label(12,1+i,form.getTCProperty("s4Materialt").getStringValue(), contentFormat));//物料模板
	                 ws.addCell(new Label(13,1+i,form.getTCProperty("s4opernumber").getStringValue(), contentFormat));
	                 
	                 ws.addCell(new Label(14,1+i,form.getTCProperty("s4tissue14").getStringValue(), contentFormat));
	                 ws.addCell(new Label(15,1+i,form.getTCProperty("s4Default_Buyer").getStringValue(), contentFormat));
	                 ws.addCell(new Label(16,1+i,form.getTCProperty("s4Planner").getStringValue(), contentFormat));
	                 ws.addCell(new Label(17,1+i,form.getTCProperty("s4Fixed_Lead_Time").getStringValue(), contentFormat));
	                 ws.addCell(new Label(18,1+i,form.getTCProperty("s4SAC_Inventory_c").getStringValue(), contentFormat));
	                 ws.addCell(new Label(19,1+i,form.getTCProperty("s4SAC_Financial_c").getStringValue(), contentFormat));
	                 ws.addCell(new Label(20,1+i,form.getTCProperty("s4SAC_Plan_c").getStringValue(), contentFormat));
	                 ws.addCell(new Label(21,1+i,form.getTCProperty("s4SAC_Pro_c").getStringValue(), contentFormat));
	                 ws.addCell(new Label(22,1+i,form.getTCProperty("s4Childstock").getStringValue(), contentFormat));
	                 ws.addCell(new Label(23,1+i,form.getTCProperty("s4cargo_s").getStringValue(), contentFormat));
	                 ws.addCell(new Label(24,1+i,form.getTCProperty("s4Fixed_Days_Supply").getStringValue(), contentFormat));
	                 ws.addCell(new Label(25,1+i,form.getTCProperty("s4Fixed_Lot_Size_M").getStringValue(), contentFormat));
	                 ws.addCell(new Label(26,1+i,form.getTCProperty("s4Inventory_Planning_M").getStringValue(), contentFormat));
	                 ws.addCell(new Label(27,1+i,form.getTCProperty("s4List_Price").getStringValue(), contentFormat));
	                 ws.addCell(new Label(28,1+i,form.getTCProperty("s4Weight_Unit_of_Mea").getStringValue(), contentFormat));
	                 ws.addCell(new Label(29,1+i,form.getTCProperty("s4Unit_Weight").getStringValue(), contentFormat));
	                 ws.addCell(new Label(30,1+i,form.getTCProperty("s4Volume_Unit_of_Mea").getStringValue(), contentFormat));
	                 ws.addCell(new Label(31,1+i,form.getTCProperty("s4Unit_Volume").getStringValue(), contentFormat));
	                 
	                 ws.addCell(new Label(32,1+i,form.getTCProperty("s4tissue15").getStringValue(), contentFormat));
	                 ws.addCell(new Label(33,1+i,form.getTCProperty("s4Default_Buyer1").getStringValue(), contentFormat));
	                 ws.addCell(new Label(34,1+i,form.getTCProperty("s4Planner1").getStringValue(), contentFormat));
	                 ws.addCell(new Label(35,1+i,form.getTCProperty("s4Fixed_Lead_Time1").getStringValue(), contentFormat));
	                 ws.addCell(new Label(36,1+i,form.getTCProperty("s4SAC_Inventory_c1").getStringValue(), contentFormat));
	                 ws.addCell(new Label(37,1+i,form.getTCProperty("s4SAC_Financial_c1").getStringValue(), contentFormat));
	                 ws.addCell(new Label(38,1+i,form.getTCProperty("s4SAC_Plan_c1").getStringValue(), contentFormat));
	                 ws.addCell(new Label(39,1+i,form.getTCProperty("s4SAC_Pro_c1").getStringValue(), contentFormat));
	                 ws.addCell(new Label(40,1+i,form.getTCProperty("s4Childstock1").getStringValue(), contentFormat));
	                 ws.addCell(new Label(41,1+i,form.getTCProperty("s4cargo_s1").getStringValue(), contentFormat));
	                 ws.addCell(new Label(42,1+i,form.getTCProperty("s4Fixed_Days_Supply1").getStringValue(), contentFormat));
	                 ws.addCell(new Label(43,1+i,form.getTCProperty("s4Fixed_Lot_Size_M1").getStringValue(), contentFormat));
	                 ws.addCell(new Label(44,1+i,form.getTCProperty("s4Inventory_Planning_M1").getStringValue(), contentFormat));
	                 ws.addCell(new Label(45,1+i,form.getTCProperty("s4List_Price1").getStringValue(), contentFormat));
	                 ws.addCell(new Label(46,1+i,form.getTCProperty("s4Weight_Unit_of_Mea1").getStringValue(), contentFormat));
	                 ws.addCell(new Label(47,1+i,form.getTCProperty("s4Unit_Weight1").getStringValue(), contentFormat));
	                 ws.addCell(new Label(48,1+i,form.getTCProperty("s4Volume_Unit_of_Mea1").getStringValue(), contentFormat));
	                 ws.addCell(new Label(49,1+i,form.getTCProperty("s4Unit_Volume1").getStringValue(), contentFormat));
			//	}
				}
			}
				
				
	             // 写入工作表完毕，关闭流
					wwb.write();
				    wwb.close();
		         	progressBarThread.stopBar();
		     		MessageBox.post("文件导出成功","提示",MessageBox.INFORMATION);
		     		return;
				}
	        //    progressBarThread.stopBar();
		    // 	MessageBox.post("文件导出失败","提示",MessageBox.INFORMATION);
				} catch (RowsExceededException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (WriteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            
		
		}
		else {
			MessageBox.post("请选择正确的对象（文件夹）","提示",MessageBox.INFORMATION);
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



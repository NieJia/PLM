package com.origin.rac.sac.form;
/**
 * @file GcProjitemMasterForm.java
 *
 * @brief 工程类项目属性Form客制化
 * 
 */
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.common.TCTable;
import com.teamcenter.rac.form.AbstractTCForm;
import com.teamcenter.rac.kernel.*;
import com.teamcenter.rac.util.*;

public class GcProjitemMasterForm extends AbstractTCForm
{
    private TCFormProperty[] formProperties = null;
    
    private Registry registry = Registry.getRegistry(this);
    private TCComponentForm form;
    private TCSession tcsession;
   
    private iTextField hetongiTextField;
    private iTextField zongjiaiTextField;
    private iTextField gongciTextField;
    private iTextField dinghiTextField;
    private DateButton jiaohuoqiDate;
//    private iTextField edjliTextField;
//    private iTextField pgcciTextField;
//    private iTextField pgysiTextField;
//    private JRadioButton radio_v2;
//    private JRadioButton radio_v1;
//    private ButtonGroup radio_group;
    private boolean flagStatu;
	private JTable table = null;
	public DefaultTableModel tableModelTwo = null;
	private String[] LineNum_array,Coding_array,Discriptions_array,Vdc_array,Ddcc_array,Babi_array,Declass_array,Remarkss_array,Maternum_array,Saleo_array;
	private String[] tablePropertyName = {"s4linenum","s4coding1","s4discriptions1","s4vdc1","s4ddcc1","s4babintasize1","s4declass","s4remarkss","s4Maternum","s4Saleo"};
	private TCFormProperty ddformProperties[] = null;
 
    public GcProjitemMasterForm(TCComponentForm tccomponentform) throws Exception {
    	super(tccomponentform);
    	form=tccomponentform;
    	tcsession=(TCSession) form.getSession();
    	System.out.println("tcsession====>:"+tcsession);
    	initializeUI();
    	loadForm();
    }
    /*
     * Form加载数据
     * 
     * */
    public void loadForm() throws TCException {
		try {
			String tmpCheckedOut = form.getProperty("checked_out");
			System.out.println("tmpCheckedOut===>:" + tmpCheckedOut);
			boolean isCheckedOut = false;
			if (tmpCheckedOut != null && tmpCheckedOut.length() > 0
					&& tmpCheckedOut.indexOf("Y") >= 0) {
				isCheckedOut = true;
			}
			flagStatu = isCheckedOut;
			if (flagStatu == false) {
				hetongiTextField.setEnabled(false);
				zongjiaiTextField.setEnabled(false);
				gongciTextField.setEnabled(false);
				dinghiTextField.setEnabled(false);
				jiaohuoqiDate.setEnabled(false);
				// edjliTextField.setEnabled(false);
				// pgcciTextField.setEnabled(false);
				// pgysiTextField.setEnabled(false);

			} else {
				hetongiTextField.setEnabled(true);
				zongjiaiTextField.setEnabled(true);
				gongciTextField.setEnabled(true);
				dinghiTextField.setEnabled(true);
				jiaohuoqiDate.setEnabled(true);
				// edjliTextField.setEnabled(true);
				// pgcciTextField.setEnabled(true);
				// pgysiTextField.setEnabled(true);
			}

			formProperties = form.getAllFormProperties();
			int k = formProperties.length;
			TCProperty id_array_property = form.getFormTCProperty("s4coding1");
			String[] id_array = id_array_property.getStringValueArray();
			int array_size = id_array.length;
			for (int m = 0; m < array_size; m++) {
				String[] newRow = { "", "", "", "", "", "", "", "", "", "" };
				tableModelTwo.addRow(newRow);
			}
			for (int i = 0; i < k; i++) {
				String str = formProperties[i].getPropertyName();
				if (str.equals("s4linenum")) {
					LineNum_array = formProperties[i].getStringValueArray();
					for (int j = 0; j < LineNum_array.length; j++) {
						table.setValueAt(LineNum_array[j], j, 0);
					}
				} else if (str.equals("s4coding1")) {
					Coding_array = formProperties[i].getStringValueArray();
					for (int j = 0; j < Coding_array.length; j++) {
						table.setValueAt(Coding_array[j], j, 1);
					}
				} else if (str.equals("s4discriptions1")) {
					Discriptions_array = formProperties[i]
							.getStringValueArray();
					for (int j = 0; j < Discriptions_array.length; j++) {
						table.setValueAt(Discriptions_array[j], j, 2);
					}
				} else if (str.equals("s4vdc1")) {
					Vdc_array = formProperties[i].getStringValueArray();
					for (int j = 0; j < Vdc_array.length; j++) {
						table.setValueAt(Vdc_array[j], j, 3);
					}
				} else if (str.equals("s4ddcc1")) {
					Ddcc_array = formProperties[i].getStringValueArray();
					for (int j = 0; j < Ddcc_array.length; j++) {
						table.setValueAt(Ddcc_array[j], j, 4);
					}
				} else if (str.equals("s4babintasize1")) {
					Babi_array = formProperties[i].getStringValueArray();
					for (int j = 0; j < Babi_array.length; j++) {
						table.setValueAt(Babi_array[j], j, 5);
					}
				} else if (str.equals("s4declass")) {
					Declass_array = formProperties[i].getStringValueArray();
					for (int j = 0; j < Declass_array.length; j++) {
						table.setValueAt(Declass_array[j], j, 6);
					}
				} else if (str.equals("s4remarkss")) {
					Remarkss_array = formProperties[i].getStringValueArray();
					for (int j = 0; j < Remarkss_array.length; j++) {
						table.setValueAt(Remarkss_array[j], j, 7);
					}
				} else if (str.equals("s4Maternum")) {
					Maternum_array = formProperties[i].getStringValueArray();
					for (int j = 0; j < Maternum_array.length; j++) {
						table.setValueAt(Maternum_array[j], j, 8);
					}
				} else if (str.equals("s4Saleo")) {
					Saleo_array = formProperties[i].getStringValueArray();
					for (int j = 0; j < Saleo_array.length; j++) {
						table.setValueAt(Saleo_array[j], j, 9);
					}
				} else if (str.equals("s4contractno")) {
					hetongiTextField
							.setText(formProperties[i].getStringValue());
				} else if (str.equals("s4totalprice")) {
					zongjiaiTextField.setText(formProperties[i]
							.getStringValue());
				} else if (str.equals("s4projectnam")) {
					gongciTextField.setText(formProperties[i].getStringValue());
				} else if (str.equals("s4orderunit")) {
					dinghiTextField.setText(formProperties[i].getStringValue());
				} else if (str.equals("s4deliverydate")) {
					if (formProperties[i].getStringValue() != null
							&& !"".equals(formProperties[i].getStringValue())) {
						jiaohuoqiDate.setDate(formProperties[i]
								.getStringValue());
					} else {
						jiaohuoqiDate.setDate("");
					}
				}
			}
		} catch (TCException tcexception) {
			throw tcexception;
		}
	}
    /*
     * Form保存数据
     * 
     * */
    public void saveForm() {
    	/*try {
    		int row = table.getRowCount();
			ddformProperties = form.getAllFormProperties();
			int k = ddformProperties.length;
			for (int i = 0; i < k; i++) {
				String str = ddformProperties[i].getPropertyName();
				if(str.equals("s4linenum")){
					String[] array = new String[row];
					for (int j = 0; j < row; j++) {
						array[j] = (String) table.getValueAt(j, 0);
					}
					formProperties[i].setStringValueArray(array);
				} else if(str.equals("s4coding1")){
					String[] array = new String[row];
					for (int j = 0; j < row; j++) {
						array[j] = (String) table.getValueAt(j, 1);
					}
					formProperties[i].setStringValueArray(array);
				} else if(str.equals("s4discriptions1")){
					String[] array = new String[row];
					for (int j = 0; j < row; j++) {
						array[j] = (String) table.getValueAt(j, 2);
					}
					formProperties[i].setStringValueArray(array);
				} else if(str.equals("s4vdc1")){
					String[] array = new String[row];
					for (int j = 0; j < row; j++) {
						array[j] = (String) table.getValueAt(j, 3);
					}
					formProperties[i].setStringValueArray(array);
				} else if(str.equals("s4ddcc1")){
					String[] array = new String[row];
					for (int j = 0; j < row; j++) {
						array[j] = (String) table.getValueAt(j, 4);
					}
					formProperties[i].setStringValueArray(array);
				} else if(str.equals("s4babintasize1")){
					String[] array = new String[row];
					for (int j = 0; j < row; j++) {
						array[j] = (String) table.getValueAt(j, 5);
					}
					formProperties[i].setStringValueArray(array);
				} else if(str.equals("s4declass")){
					String[] array = new String[row];
					for (int j = 0; j < row; j++) {
						array[j] = (String) table.getValueAt(j, 6);
					}
					formProperties[i].setStringValueArray(array);
				} else if(str.equals("s4remarkss")){
					String[] array = new String[row];
					for (int j = 0; j < row; j++) {
						array[j] = (String) table.getValueAt(j, 7);
					}
					formProperties[i].setStringValueArray(array);
				} else if(str.equals("s4Maternum")){
					String[] array = new String[row];
					for (int j = 0; j < row; j++) {
						array[j] = (String) table.getValueAt(j, 8);
					}
					formProperties[i].setStringValueArray(array);
				} else if(str.equals("s4Saleo")){
					String[] array = new String[row];
					for (int j = 0; j < row; j++) {
						array[j] = (String) table.getValueAt(j, 9);
					}
					formProperties[i].setStringValueArray(array);
				} 
			}
			form.setTCProperties(ddformProperties);
		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*		try {	  	
					for(int i=0; i<formProperties.length; i++){
						String propertyName = formProperties[i] == null ? "" : formProperties[i].getPropertyName();
						if(propertyName.equals("s4contractno")){
							 formProperties[i].setStringValueData(hetongiTextField.getText());
						 } else if(propertyName.equals("s4totalprice")){
							 formProperties[i].setStringValueData(zongjiaiTextField.getText());
						 } else if(propertyName.equals("s4projectnam")){
							 formProperties[i].setStringValueData(gongciTextField.getText());
						 } else if(propertyName.equals("s4orderunit")){
							 formProperties[i].setStringValueData(dinghiTextField.getText());
						 } else if(propertyName.equals("s4deliverydate")){
							 formProperties[i].setStringValueData(jiaohuoqiDate.getDateString());
						 }
					}
					form.setTCProperties(formProperties);
  
			} catch (Exception exception) {
			    exception.printStackTrace();
			    MessageBox.post(exception);
			}*/
		  	   	
   }
    private JPanel buildProjectInformation(){
    	
    	JPanel jpanel = new JPanel(new PropertyLayout());
    	JLabel hength = new JLabel("合同号");
    	hetongiTextField = new iTextField(30);
    	JLabel zongjia = new JLabel("总价(万元)");
    	zongjiaiTextField = new iTextField(30);
    	JLabel gongcmc = new JLabel("工程名称");
    	gongciTextField = new iTextField(30);
    	JLabel dinghdw = new JLabel("订货单位");
    	dinghiTextField = new iTextField(30);
    	JLabel jiaohq = new JLabel("交货期");
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	jiaohuoqiDate=new DateButton(sdf);
    	jiaohuoqiDate.setPreferredSize(new Dimension(188,25));
    	//radio button group
//    	JLabel eddy = new JLabel("额定直流电压");
//    	radio_v2 = new JRadioButton("220V");
//    	radio_v1 = new JRadioButton("110V");
//    	radio_v1.setEnabled(false);
//    	radio_group = new ButtonGroup();
//    	radio_group.add(radio_v2);
//    	radio_group.add(radio_v1);
    	
//    	JLabel edjldl = new JLabel("额定交流电流");
//    	edjliTextField = new iTextField(30);
//    	JLabel pgcc = new JLabel("屏柜尺寸");
//    	pgcciTextField = new iTextField(30);
//    	JLabel pgys = new JLabel("屏柜颜色(色卡号)");
//    	pgysiTextField = new iTextField(30);
    	
    	
    	jpanel.add("1.1.right",new JLabel(" "));
    	jpanel.add("2.1.right",hength);
    	jpanel.add("2.2.right",hetongiTextField);
    	jpanel.add("2.3.right",zongjia);
    	jpanel.add("2.4.right",zongjiaiTextField);
    	jpanel.add("3.1.right",new JLabel(" "));
    	jpanel.add("4.1.right",gongcmc);
    	jpanel.add("4.2.right",gongciTextField);
    	jpanel.add("5.1.right",new JLabel(" "));
    	jpanel.add("6.1.right",dinghdw);
    	jpanel.add("6.2.right",dinghiTextField);
    	jpanel.add("7.1.right",new JLabel(" "));
    	jpanel.add("8.1.right",jiaohq);
    	jpanel.add("8.2.right",jiaohuoqiDate);
//    	jpanel.add("9.1.right",new JLabel(" "));
//    	jpanel.add("10.1.right",eddy);
//    	jpanel.add("10.2.right",radio_v2);
//    	jpanel.add("10.3.right",radio_v1);
//    	jpanel.add("10.4.right",new JLabel("             "));
//    	jpanel.add("10.5.right",edjldl);
//    	jpanel.add("10.6.right",edjliTextField);
//    	jpanel.add("11.1.right",new JLabel(" "));
//    	jpanel.add("12.1.right",pgcc);
//    	jpanel.add("12.2.right",pgcciTextField);
//    	jpanel.add("13.1.right",new JLabel(" "));
//    	jpanel.add("14.1.right",pgys);
//    	jpanel.add("14.2.right",pgysiTextField);
    	
    	return jpanel;
    }
    
    private JPanel bulidProjectPersonInformation(){
    	JPanel jpanel = new JPanel();
    	jpanel.setPreferredSize(new Dimension(1280,540));
    	String[] columnNames = {"行号","订单行物料编码", "订单行物料描述","额定直流电压","额定交流电压","颜色-柜型-尺寸","需求分类","备注","物料数量","销售单价"};
    	tableModelTwo = new DefaultTableModel(columnNames,0);
    	table = new JTable(tableModelTwo){
    		public boolean isCellEditable(int row, int column) {
    			 return false;
    		}
    	};
    	setColumnWidth(table, 0, 50);
    	setColumnWidth(table, 1, 120);
    	setColumnWidth(table, 2, 120);
    	setColumnWidth(table, 3, 80);
    	setColumnWidth(table, 4, 80);
    	setColumnWidth(table, 5, 80);
    	setColumnWidth(table, 6, 100);
    	setColumnWidth(table, 7, 100);
    	setColumnWidth(table, 8, 90);
    	setColumnWidth(table, 9, 90);
    	table.getTableHeader().setReorderingAllowed(false) ;
    	JScrollPane selectscrollPane = new JScrollPane(table);
		selectscrollPane.setPreferredSize(new Dimension(1280,540));
		selectscrollPane.setBounds(10, 10, 1280, 540);
		jpanel.add(selectscrollPane);
		return jpanel;
    }
    
    /*
     * Form界面初始化
     * 
     * */
    private void initializeUI() {

		JTabbedPane jtabbedPane = new JTabbedPane();
		jtabbedPane.setTabPlacement(JTabbedPane.TOP);// 设置标签置放位置。
		jtabbedPane.addTab("合同基本属性", buildProjectInformation());
		jtabbedPane.addTab("产品编码清单", bulidProjectPersonInformation());
		jtabbedPane.setSelectedIndex(0);

		Dimension dim = new Dimension(1300, 580);
		jtabbedPane.setPreferredSize(dim);

		add(jtabbedPane);

	}
    
	
	/*
	 * 设置表格初始化列宽
	 */
	public void setColumnWidth(JTable table, int index, int width) {
		TableColumn tc = table.getColumnModel().getColumn(index);
		tc.setPreferredWidth(width);
		tc.setWidth(width);
		tc.setMinWidth(width);
		table.getTableHeader().getColumnModel().getColumn(index).setMinWidth(
				width);
	}
	
	
	
	
}
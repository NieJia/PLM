package com.origin.rac.sac.form;
/**
 * @file CssyForm.java
 *
 * @brief 测试、试验费Form客制化
 * 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import test.MQDocument;

import cn.com.origin.util.SACTextFieldEditor128;
import cn.com.origin.util.SACTextFieldEditor32;
import cn.com.origin.util.SACTextFieldEditor64;

import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.common.TCTable;
import com.teamcenter.rac.form.AbstractTCForm;
import com.teamcenter.rac.kernel.*;
import com.teamcenter.rac.util.*;

public class CssyForm extends AbstractTCForm
{
    private TCFormProperty[] formProperties = null;
    private Registry registry = Registry.getRegistry(this);
    private TCComponentForm form;
    private TCSession tcsession;
    public JTable tableSYFY = null;;
    public Font font;
    
    private TCProperty xuhao = null;//序号
    private TCProperty name = null;//姓名
    private TCProperty price = null;//价格
    private TCProperty beiz = null;//备注
    private String[] strxuhao = null;
	private String[] strname = null;
	private String[] strjg = null;
	private String[] strbz = null;
    
	public CssyForm(TCComponentForm tccomponentform) throws Exception {
		super(tccomponentform);
		form = tccomponentform;
		tcsession = (TCSession) form.getSession();
		initializeUI();
		loadForm();
	}
    /*
     * Form加载数据
     * 
     * */
	public void loadForm() throws TCException {
		try {
			formProperties = form.getAllFormProperties();
			for (int n = 0; n < formProperties.length; n++) {
				String str = formProperties[n].getPropertyName();
				if (str.equals("s4Serial_No")) {
					xuhao = formProperties[n];
				} else if (str.equals("s4name4")) {
					name = formProperties[n];
				} else if (str.equals("s4price")) {
					price = formProperties[n];
				} else if (str.equals("s4remarks2")) {
					beiz = formProperties[n];
				}
				strxuhao = xuhao.getStringArrayValue();
				for (int j = 0; j < strxuhao.length; j++) {
					tableSYFY.setValueAt(strxuhao[j], j, 0);
				}
				if (name != null) {
					strname = name.getStringArrayValue();
					for (int j = 0; j < strname.length; j++) {
						tableSYFY.setValueAt(strname[j], j, 1);
					}
				}
				if (price != null) {
					strjg = price.getStringArrayValue();
					for (int j = 0; j < strjg.length; j++) {
						tableSYFY.setValueAt(strjg[j], j, 2);
					}
				}
				if (beiz != null) {
					strbz = beiz.getStringArrayValue();
					for (int j = 0; j < strbz.length; j++) {
						tableSYFY.setValueAt(strbz[j], j, 3);
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
    	try {
    		if(tableSYFY.getCellEditor()!=null){
    			tableSYFY.getCellEditor().stopCellEditing();
			}
    		strxuhao = new String[tableSYFY.getRowCount()];
    		strname = new String[tableSYFY.getRowCount()];
    		strjg = new String[tableSYFY.getRowCount()];
    		strbz = new String[tableSYFY.getRowCount()];
			for(int i = 0; i < tableSYFY.getRowCount(); i++){
				if((String)tableSYFY.getValueAt(i, 0) == null){
					strxuhao[i] = "";
				}
				else{
					strxuhao[i] = (String)tableSYFY.getValueAt(i, 0);
				}
				if((String)tableSYFY.getValueAt(i, 1) == null){
					strname[i] = "";
				}
				else{
					strname[i] = (String)tableSYFY.getValueAt(i, 1);
				}
				if((String)tableSYFY.getValueAt(i, 2) == null){
					strjg[i] = "";
				}
				else{
					strjg[i] = (String)tableSYFY.getValueAt(i, 2);
				}
				if((String)tableSYFY.getValueAt(i, 3) == null){
					strbz[i] = "";
				}
				else{
					strbz[i] = (String)tableSYFY.getValueAt(i, 3);
				}
			}
			xuhao.setStringValueArray(strxuhao);
			name.setStringValueArray(strname);
			price.setStringValueArray(strjg);
			beiz.setStringValueArray(strbz);
			TCProperty[] tcProperty = new TCProperty[4];
			tcProperty[0] = xuhao;
			tcProperty[1] = name;
			tcProperty[2] = price;
			tcProperty[3] = beiz;
			form.setTCProperties(tcProperty);
    	} catch (Exception exception) {
	    exception.printStackTrace();
	    MessageBox.post(exception);
	}
    	
   }
    
    
    /*
     * Form界面初始化
     * 
     * */
	private void initializeUI() {
		JPanel parentPanel = new JPanel(new BorderLayout());
		this.setLayout(new BorderLayout());
		// 构造关键材料、元器件费table panel
		JPanel yqjfy_Panel = bulidYQJFY_Panel();
		parentPanel.add(BorderLayout.CENTER, yqjfy_Panel);
		add(BorderLayout.CENTER, parentPanel);

	}
    
	
	/**
	 * 关键材料、元器件费的Panel
	 */
	public JPanel bulidYQJFY_Panel(){
		JPanel panelSYFY = new JPanel(new GridLayout(1, 0));
		TitledBorder titleBorderSYFY = BorderFactory.createTitledBorder("测试、试验费");
		titleBorderSYFY.setTitleFont(font);
		titleBorderSYFY.setTitlePosition(2);
		panelSYFY.setBorder(titleBorderSYFY);
		Object[] columnNamesShiYan = {"序号", "名称", "价格(万元)", "备注"};
		Object[][] dataShiYan = {{"1", "", "",""},{"2", "", "",""},
				{"3", "", "",""},{"4", "", "",""},{"5", "", "",""},{"6", "", "",""},
				{"7", "", "",""},{"8", "", "",""},{"9", "", "",""},{"10", "合计", "",""}};

		DefaultTableModel tableModel = new DefaultTableModel(dataShiYan,columnNamesShiYan){   
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row,int column){ 
				if (column < 1 || (row ==2 && column==1)) {
					return false;
				} else {
					return true;
				}
			}
		};
		tableSYFY = new JTable(tableModel);
		tableSYFY.getTableHeader().setReorderingAllowed( false ) ;
		tableSYFY.setRowHeight(23);
		tableSYFY.getColumnModel().getColumn(0).setPreferredWidth(30);
		tableSYFY.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableSYFY.setPreferredScrollableViewportSize(new Dimension(400, 70));
		for (int i = 0; i < 4; i++) {
			if(i==2){
				tableSYFY.getColumnModel().getColumn(i).setCellEditor(new SACTextFieldEditor32(tableSYFY));
			}else{
				tableSYFY.getColumnModel().getColumn(i).setCellEditor(new SACTextFieldEditor128(tableSYFY));
			}
		}
		JScrollPane scrollPaneSYFY = new JScrollPane(tableSYFY);
		panelSYFY.add(scrollPaneSYFY);
		return panelSYFY;
		
	}
    
}
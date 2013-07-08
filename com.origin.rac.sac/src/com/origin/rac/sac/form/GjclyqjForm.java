package com.origin.rac.sac.form;
/**
 * @file GjclyqjForm.java
 *
 * @brief 关键材料、元器件费Form客制化
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

public class GjclyqjForm extends AbstractTCForm
{
    private TCFormProperty[] formProperties = null;
    private Registry registry = Registry.getRegistry(this);
    private TCComponentForm form;
    private TCSession tcsession;
    public JTable tableYQJFY = null;;
    public Font font;
    private TCProperty xuhao = null;//序号
    private TCProperty name = null;//姓名
    private TCProperty xinghao = null;//型号
    private TCProperty price = null;//价格
    private TCProperty beiz = null;//备注
    private String[] strxuhao = null;
	private String[] strname = null;
	private String[] strxingh = null;
	private String[] strjg = null;
	private String[] strbz = null;
    
	public GjclyqjForm(TCComponentForm tccomponentform) throws Exception {
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
				} else if (str.equals("s4model")) {
					xinghao = formProperties[n];
				} else if (str.equals("s4price")) {
					price = formProperties[n];
				} else if (str.equals("s4remarks2")) {
					beiz = formProperties[n];
				}
				strxuhao = xuhao.getStringArrayValue();
				for (int j = 0; j < strxuhao.length; j++) {
					tableYQJFY.setValueAt(strxuhao[j], j, 0);
				}
				if (name != null) {
					strname = name.getStringArrayValue();
					for (int j = 0; j < strname.length; j++) {
						tableYQJFY.setValueAt(strname[j], j, 1);
					}
				}
				if (xinghao != null) {
					strxingh = xinghao.getStringArrayValue();
					for (int j = 0; j < strxingh.length; j++) {
						tableYQJFY.setValueAt(strxingh[j], j, 2);
					}
				}
				if (price != null) {
					strjg = price.getStringArrayValue();
					for (int j = 0; j < strjg.length; j++) {
						tableYQJFY.setValueAt(strjg[j], j, 3);
					}
				}
				if (beiz != null) {
					strbz = beiz.getStringArrayValue();
					for (int j = 0; j < strbz.length; j++) {
						tableYQJFY.setValueAt(strbz[j], j, 4);
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
    		if(tableYQJFY.getCellEditor()!=null){
    			tableYQJFY.getCellEditor().stopCellEditing();
			}
    		strxuhao = new String[tableYQJFY.getRowCount()];
    		strname = new String[tableYQJFY.getRowCount()];
    		strxingh = new String[tableYQJFY.getRowCount()];
    		strjg = new String[tableYQJFY.getRowCount()];
    		strbz = new String[tableYQJFY.getRowCount()];
			for(int i = 0; i < tableYQJFY.getRowCount(); i++){
				if((String)tableYQJFY.getValueAt(i, 0) == null){
					strxuhao[i] = "";
				}
				else{
					strxuhao[i] = (String)tableYQJFY.getValueAt(i, 0);
				}
				if((String)tableYQJFY.getValueAt(i, 1) == null){
					strname[i] = "";
				}
				else{
					strname[i] = (String)tableYQJFY.getValueAt(i, 1);
				}
				if((String)tableYQJFY.getValueAt(i, 2) == null){
					strxingh[i] = "";
				}
				else{
					strxingh[i] = (String)tableYQJFY.getValueAt(i, 2);
				}
				if((String)tableYQJFY.getValueAt(i, 3) == null){
					strjg[i] = "";
				}
				else{
					strjg[i] = (String)tableYQJFY.getValueAt(i, 3);
				}
				if((String)tableYQJFY.getValueAt(i, 4) == null){
					strbz[i] = "";
				}
				else{
					strbz[i] = (String)tableYQJFY.getValueAt(i, 4);
				}
			}
			xuhao.setStringValueArray(strxuhao);
			name.setStringValueArray(strname);
			xinghao.setStringValueArray(strxingh);
			price.setStringValueArray(strjg);
			beiz.setStringValueArray(strbz);
			TCProperty[] tcProperty = new TCProperty[5];
			tcProperty[0] = xuhao;
			tcProperty[1] = name;
			tcProperty[2] = xinghao;
			tcProperty[3] = price;
			tcProperty[4] = beiz;
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
		JPanel panelYQJFY = new JPanel(new GridLayout(1, 0));
		TitledBorder titleBorderYQJFY = BorderFactory.createTitledBorder("关键材料、元器件费");
		titleBorderYQJFY.setTitleFont(font);
		titleBorderYQJFY.setTitlePosition(2);
		panelYQJFY.setBorder(titleBorderYQJFY);
		Object[] columnNamesYuanQiJian = {"序号", "名称", "型号", "价格(万元)", "备注"};
		Object[][] dataYuanQiJian = {{"1", "", "","",""},{"2", "", "","",""},
				{"3", "", "","",""},{"4", "", "","",""},{"5", "", "","",""},{"6", "", "","",""},{"7", "", "","",""},{"8", "", "","",""},{"9", "", "","",""},
				{"10", "", "","",""},{"11", "", "","",""},{"12", "", "","",""},{"13", "", "","",""},{"14", "", "","",""},{"15", "合计", "","",""}};

		DefaultTableModel tableModel = new DefaultTableModel(dataYuanQiJian,columnNamesYuanQiJian){   
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row,int column){ 
				if (column < 1 || (row ==8 && column==1)) {
					return false;
				} else {
					return true;
				}
			}
		};
		tableYQJFY = new JTable(tableModel);
		tableYQJFY.getTableHeader().setReorderingAllowed( false ) ;
		tableYQJFY.setRowHeight(23);
		tableYQJFY.getColumnModel().getColumn(0).setPreferredWidth(30);
		tableYQJFY.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableYQJFY.setPreferredScrollableViewportSize(new Dimension(500, 105));
		for (int i = 0; i < 5; i++) {
			if(i==2){
				tableYQJFY.getColumnModel().getColumn(i).setCellEditor(new SACTextFieldEditor64(tableYQJFY));
			}else if(i==3){
				tableYQJFY.getColumnModel().getColumn(i).setCellEditor(new SACTextFieldEditor32(tableYQJFY));
			}else{
				tableYQJFY.getColumnModel().getColumn(i).setCellEditor(new SACTextFieldEditor128(tableYQJFY));
			}
		}
		JScrollPane scrollPaneYQJFY = new JScrollPane(tableYQJFY);
		panelYQJFY.add(scrollPaneYQJFY);
		return panelYQJFY;
		
	}
    
}
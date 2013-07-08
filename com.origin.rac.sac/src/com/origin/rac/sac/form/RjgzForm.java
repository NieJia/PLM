package com.origin.rac.sac.form;
/**
 * @file RjgzForm.java
 *
 * @brief 设备及软件购置费Form客制化
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
import cn.com.origin.util.SACTextFieldEditor1280;
import cn.com.origin.util.SACTextFieldEditor32;
import cn.com.origin.util.SACTextFieldEditor64;

import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.common.TCTable;
import com.teamcenter.rac.form.AbstractTCForm;
import com.teamcenter.rac.kernel.*;
import com.teamcenter.rac.util.*;

public class RjgzForm extends AbstractTCForm
{
    private TCFormProperty[] formProperties = null;
    private Registry registry = Registry.getRegistry(this);
    private TCComponentForm form;
    private TCSession tcsession;
    public JTable tableRJGZFY = null;;
    public Font font;
    private TCProperty xuhao = null;//序号
    private TCProperty name = null;//姓名
    private TCProperty xinghao = null;//型号
    private TCProperty price = null;//价格
    private TCProperty beiz = null;//备注
    private String[] strrjxuhao = null;
	private String[] strrjname = null;
	private String[] strrjxingh = null;
	private String[] strrjjg = null;
	private String[] strrjbz = null;
	
	
	public RjgzForm(TCComponentForm tccomponentform) throws Exception {
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
				strrjxuhao = xuhao.getStringArrayValue();
				for (int j = 0; j < strrjxuhao.length; j++) {
					tableRJGZFY.setValueAt(strrjxuhao[j], j, 0);
				}
				if (name != null) {
					strrjname = name.getStringArrayValue();
					for (int j = 0; j < strrjname.length; j++) {
						tableRJGZFY.setValueAt(strrjname[j], j, 1);
					}
				}
				if (xinghao != null) {
					strrjxingh = xinghao.getStringArrayValue();
					for (int j = 0; j < strrjxingh.length; j++) {
						tableRJGZFY.setValueAt(strrjxingh[j], j, 2);
					}
				}
				if (price != null) {
					strrjjg = price.getStringArrayValue();
					for (int j = 0; j < strrjjg.length; j++) {
						tableRJGZFY.setValueAt(strrjjg[j], j, 3);
					}
				}
				if (beiz != null) {
					strrjbz = beiz.getStringArrayValue();
					for (int j = 0; j < strrjbz.length; j++) {
						tableRJGZFY.setValueAt(strrjbz[j], j, 4);
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
    		if(tableRJGZFY.getCellEditor()!=null){
    			tableRJGZFY.getCellEditor().stopCellEditing();
			}
    		strrjxuhao = new String[tableRJGZFY.getRowCount()];
    		strrjname = new String[tableRJGZFY.getRowCount()];
    		strrjxingh = new String[tableRJGZFY.getRowCount()];
    		strrjjg = new String[tableRJGZFY.getRowCount()];
    		strrjbz = new String[tableRJGZFY.getRowCount()];
			for(int i = 0; i < tableRJGZFY.getRowCount(); i++){
				if((String)tableRJGZFY.getValueAt(i, 0) == null){
					strrjxuhao[i] = "";
				}
				else{
					strrjxuhao[i] = (String)tableRJGZFY.getValueAt(i, 0);
				}
				if((String)tableRJGZFY.getValueAt(i, 1) == null){
					strrjname[i] = "";
				}
				else{
					strrjname[i] = (String)tableRJGZFY.getValueAt(i, 1);
				}
				if((String)tableRJGZFY.getValueAt(i, 2) == null){
					strrjxingh[i] = "";
				}
				else{
					strrjxingh[i] = (String)tableRJGZFY.getValueAt(i, 2);
				}
				if((String)tableRJGZFY.getValueAt(i, 3) == null){
					strrjjg[i] = "";
				}
				else{
					strrjjg[i] = (String)tableRJGZFY.getValueAt(i, 3);
				}
				if((String)tableRJGZFY.getValueAt(i, 4) == null){
					strrjbz[i] = "";
				}
				else{
					strrjbz[i] = (String)tableRJGZFY.getValueAt(i, 4);
				}
			}
			xuhao.setStringValueArray(strrjxuhao);
			name.setStringValueArray(strrjname);
			xinghao.setStringValueArray(strrjxingh);
			price.setStringValueArray(strrjjg);
			beiz.setStringValueArray(strrjbz);
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
		// 构造设备及软件购置费table panel
		JPanel rjgzfy_Panel = bulidRJGZFY_Panel();
		parentPanel.add(BorderLayout.CENTER, rjgzfy_Panel);
		add(BorderLayout.CENTER, parentPanel);

	}
    
	
	/**
	 * 设备及软件购置费的Panel
	 */
	public JPanel bulidRJGZFY_Panel(){
		JPanel panelRJGZFY = new JPanel(new GridLayout(1, 0));
		TitledBorder titleBorderRJGZFY = BorderFactory.createTitledBorder("设备及软件购置费");
		titleBorderRJGZFY.setTitleFont(font);
		titleBorderRJGZFY.setTitlePosition(2);
		panelRJGZFY.setBorder(titleBorderRJGZFY);
		Object[] columnNamesGoZhi = {"序号", "名称", "型号", "价格(万元)", "备注"};
		Object[][] dataGoZhi = {{"1", "", "","",""},{"2", "", "","",""},
				{"3", "", "","",""},{"4", "", "","",""},{"5", "", "","",""},{"6", "", "","",""},{"7", "", "","",""},
				{"8", "", "","",""},{"9", "", "","",""},{"10", "", "","",""},{"11", "", "","",""},{"12", "", "","",""},
				{"13", "", "","",""},{"14", "", "","",""},{"15", "合计", "","",""}};

		DefaultTableModel tableModel = new DefaultTableModel(dataGoZhi,columnNamesGoZhi){   
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row,int column){ 
				if (column < 1 || (row ==6 && column==1)) {
					return false;
				} else {
					return true;
				}
			}
		};
		tableRJGZFY = new JTable(tableModel);
		tableRJGZFY.getTableHeader().setReorderingAllowed( false ) ;
		tableRJGZFY.setRowHeight(23);
		tableRJGZFY.getColumnModel().getColumn(0).setPreferredWidth(30);
		tableRJGZFY.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableRJGZFY.setPreferredScrollableViewportSize(new Dimension(500, 105));
		for (int i = 0; i < 5; i++) {
			if(i==2){
				tableRJGZFY.getColumnModel().getColumn(i).setCellEditor(new SACTextFieldEditor64(tableRJGZFY));
			}else if(i==3){
				tableRJGZFY.getColumnModel().getColumn(i).setCellEditor(new SACTextFieldEditor32(tableRJGZFY));
			}else{
				tableRJGZFY.getColumnModel().getColumn(i).setCellEditor(new SACTextFieldEditor128(tableRJGZFY));
			}
		}
		
		
		JScrollPane scrollPaneRJGZFY = new JScrollPane(tableRJGZFY);
		panelRJGZFY.add(scrollPaneRJGZFY);
		return panelRJGZFY;
		
	}
	
    
}
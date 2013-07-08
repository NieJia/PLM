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
import com.teamcenter.rac.stylesheet.AbstractRendering;
import com.teamcenter.rac.util.*;

public class CopyOfCssyForm extends AbstractRendering
{
    private TCComponentForm form;
    private TCSession tcsession;
    private JComboBox combox;
    private JCheckBox checkbox;
    private JTextField text1;
    private DateButton dateButton;
    private String str[] = {"aa","bb","cc"};
    
    
	public CopyOfCssyForm(TCComponentForm tccomponentform) throws Exception {
		super(tccomponentform);
		form = tccomponentform;
		tcsession = (TCSession) form.getSession();
		initializeUI();
		loadRendering();
	}
   
    
    
    /*
     * Form界面初始化
     * 
     * */
	private void initializeUI() {
		JPanel parentPanel = new JPanel(new BorderLayout());
		this.setLayout(new BorderLayout());
		JPanel yqjfy_Panel = bulidYQJFY_Panel();
		parentPanel.add(BorderLayout.CENTER, yqjfy_Panel);
		add(BorderLayout.CENTER, parentPanel);

	}
    
	public JPanel bulidYQJFY_Panel(){
		JPanel panelSYFY = new JPanel(new PropertyLayout());
		combox = new JComboBox(str);
		combox.setEnabled(false);
//		combox.getComponent(0).removeMouseListener(combox.getMouseListeners()[0]);
//		combox.removeMouseListener(combox.getMouseListeners()[0]);
		checkbox = new JCheckBox();
		checkbox.setEnabled(false);
//		checkbox.removeMouseListener(checkbox.getMouseListeners()[0]);
		text1 = new JTextField(10);
		text1.setEnabled(false);
		dateButton = new DateButton();
		dateButton.setEnabled(false);
//		dateButton.removeMouseListener(dateButton.getMouseListeners()[0]);
		panelSYFY.add("1.1.left",combox);
		panelSYFY.add("2.1.left",checkbox);
		panelSYFY.add("3.1.left",text1);
		panelSYFY.add("4.1.left",dateButton);
		return panelSYFY;
		
	}
	@Override
	public void loadRendering() throws TCException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void saveRendering() {
		// TODO Auto-generated method stub
		
	}
    
}
package com.gdnz.sac1.form;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;


import java.awt.Component;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;


public class MyTableCellEditor extends AbstractCellEditor implements TableCellEditor{
   private JComboBox cb[]=new JComboBox[2];
   private JComboBox editorComponent=null;
	public MyTableCellEditor(){
		cb[0]=new JComboBox();
		cb[0].addItem("");
		cb[0].addItem("A");
		cb[0].addItem("B");
		cb[0].addItem("C");
		cb[0].addItem("D");
		cb[0].addItem("E");
		cb[0].addItem("F");
		
		cb[1]=new JComboBox();
		cb[1].addItem("");
		cb[1].addItem("Õý³£");
		cb[1].addItem("¹Ø×¢");

	}
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isselected, int row, int column) {
		// TODO Auto-generated method stub
		if(row%3==2){
			
			cb[1].setSelectedItem(table.getValueAt(row, column));
			editorComponent =cb[1];
		}
		else {
			cb[0].setSelectedItem(table.getValueAt(row, column));
			editorComponent =cb[0];
			
		}
		return editorComponent;
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return editorComponent.getSelectedItem();
	}
	public boolean isCellEditable(EventObject anEvent){
		return true;
	}
	public void cancelCellEditing(){
		super.cancelCellEditing();
	}
	public boolean stopCellEditing(){
		super.stopCellEditing();
		return true;
	}

}


	
	

  
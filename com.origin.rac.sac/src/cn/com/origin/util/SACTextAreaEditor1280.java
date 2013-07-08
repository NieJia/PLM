package cn.com.origin.util;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellEditor;


public class SACTextAreaEditor1280 extends AbstractCellEditor implements TableCellEditor{

	protected SACJTextArea1280 textField;
	int row = 0 ;
	int column = 0 ;
	JTable table = null ;
	JPopupMenu deposePopupMenu = null ;
	public SACTextAreaEditor1280(JTable table) {
		super();
		this.table = table ;
		row = table.getSelectedRow() ;
		column = table.getSelectedColumn();
//		System.out.println("row"+row+" column:"+column);
		if(row != -1 || column != -1)
		{
			System.out.println("enter:"+table.getValueAt(row, column));
		}
		textField = new SACJTextArea1280();
		textField.setLineWrap(true);
		
		
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1,
			boolean arg2, int arg3, int arg4) {
		String s = (arg1 == null ? "" : arg1.toString());
		arg0.setValueAt(s, arg3, arg4);
		JScrollPane jsp = new JScrollPane(textField);
		textField.setText(s);
		textField.setLineWrap(true);
		return jsp;
	}

	@Override
	public Object getCellEditorValue() {
		if(textField != null )
		{
			return textField.getText();
		}
		return null;
	}
	
}
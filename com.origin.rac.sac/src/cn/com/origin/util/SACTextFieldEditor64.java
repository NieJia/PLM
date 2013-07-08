package cn.com.origin.util;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;


public class SACTextFieldEditor64 extends AbstractCellEditor implements TableCellEditor{

	protected SACJTextField64 textField;
	int row = 0 ;
	int column = 0 ;
	JTable table = null ;
	JPopupMenu deposePopupMenu = null ;
	public SACTextFieldEditor64(JTable table) {
		super();
		this.table = table ;
		row = table.getSelectedRow() ;
		column = table.getSelectedColumn();
//		System.out.println("row"+row+" column:"+column);
		if(row != -1 || column != -1)
		{
			System.out.println("enter:"+table.getValueAt(row, column));
		}
		textField = new SACJTextField64();
		
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1,
			boolean arg2, int arg3, int arg4) {
		String s = (arg1 == null ? "" : arg1.toString());
		arg0.setValueAt(s, arg3, arg4);
		textField.setText(s);
		return textField;
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
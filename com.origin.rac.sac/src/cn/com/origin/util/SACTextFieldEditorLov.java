package cn.com.origin.util;

import java.awt.Component;
import java.text.SimpleDateFormat;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.teamcenter.rac.util.DateButton;


public class SACTextFieldEditorLov extends AbstractCellEditor implements TableCellEditor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected SACJTextField32 textField;
	int row = 0 ;
	int column = 0 ;
	JTable table = null ;
	JComboBox combox;
	String[] str_Lov = null;
	public SACTextFieldEditorLov(JTable table,String[] str) {
		super();
		this.table = table ;
		this.str_Lov = str;
		row = table.getSelectedRow() ;
		column = table.getSelectedColumn();
		if(row != -1 || column != -1)
		{
			System.out.println("enter:"+table.getValueAt(row, column));
		}
		combox=new JComboBox(str_Lov);
		
		
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1,
			boolean arg2, int arg3, int arg4) {
		String s = (arg1 == null ? "" : arg1.toString());
		combox=new JComboBox(str_Lov);
		if(s!=null&&s.length()>0) {
			combox.setSelectedItem(s);
		}
		return combox;
	}

	@Override
	public Object getCellEditorValue() {
		String s=null;
		try{
			s=combox.getSelectedItem().toString();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return s;
	}
	
}
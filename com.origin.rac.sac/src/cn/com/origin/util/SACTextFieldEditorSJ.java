package cn.com.origin.util;

import java.awt.Component;
import java.text.SimpleDateFormat;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.teamcenter.rac.util.DateButton;


public class SACTextFieldEditorSJ extends AbstractCellEditor implements TableCellEditor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected SACJTextField32 textField;
	int row = 0 ;
	int column = 0 ;
	JTable table = null ;
	DateButton dateButton;
	SimpleDateFormat sdf;
	public SACTextFieldEditorSJ(JTable table) {
		super();
		this.table = table ;
		row = table.getSelectedRow() ;
		column = table.getSelectedColumn();
		if(row != -1 || column != -1)
		{
			System.out.println("enter:"+table.getValueAt(row, column));
		}
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		dateButton=new DateButton(sdf);
		
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1,
			boolean arg2, int arg3, int arg4) {
		String s = (arg1 == null ? "" : arg1.toString());
		dateButton=new DateButton(sdf);
		if(s!=null&&s.length()>0) {
			dateButton.setDate(s);
		}
		return dateButton;
	}
	
	public boolean stopCellEditing(){	
		this.dateButton.postDown();		
		return super.stopCellEditing();
	}
	
	

	@Override
	public Object getCellEditorValue() {
		String s=null;
		try{
			s=dateButton.getDateString();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return s;
	}
	
}
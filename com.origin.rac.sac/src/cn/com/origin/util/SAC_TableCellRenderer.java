package cn.com.origin.util;

import java.awt.Component;
import java.text.SimpleDateFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.teamcenter.rac.util.DateButton;


public class SAC_TableCellRenderer extends  DefaultTableCellRenderer {
	   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");;
	
	public Component getTableCellRendererComponent(JTable table, Object value,
		boolean isSelected, boolean hasFocus, int row, int column) {
		System.out.println("value==>:"+value.getClass().toString());
		if(value instanceof DateButton)
		{
			try {
				DateButton arg1_1 =  (DateButton) value;
				String s_date = arg1_1.getDateString();
				System.out.println("s_date===>:"+s_date);
				arg1_1.setDate(s_date);
				return arg1_1;
//				return new DateButton(sdf);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else{
			return super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
		}
		return table;
	}
}

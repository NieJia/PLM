package cn.com.origin.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.teamcenter.rac.util.MLabel;

public class SACGridBagRenderer extends JTextArea implements TableCellRenderer  {

	public SACGridBagRenderer() { 
		setLineWrap(true);
	    setWrapStyleWord(true);
	    setFont(new Font("宋体", 0, 12));
    } 
	
	
	public Component getTableCellRendererComponent(JTable arg0, Object arg1,
			boolean arg2, boolean arg3, int arg4, int arg5) {
			arg0.setRowHeight(arg4, 50);
			/*int maxPreferredHeight = 0; 
			for (int i = 0; i < arg0.getColumnCount(); i++) { 
	            setText("" + arg0.getValueAt(arg4, i)); 
	            setSize(arg0.getColumnModel().getColumn(arg5).getWidth(), 0); 
	            maxPreferredHeight = Math.max(maxPreferredHeight, getPreferredSize().height); 
	            
			} 
			System.out.println("maxPreferredHeight--->:"+maxPreferredHeight);
			System.out.println("arg0--->:"+arg0.getRowHeight(arg4));
			if (arg0.getRowHeight(arg4) != maxPreferredHeight)  // 少了这行则处理器瞎忙 
				arg0.setRowHeight(arg4, 100); */
			
			JTextArea jt = new JTextArea(6,20);
			jt.setAutoscrolls(true);
			jt.setLineWrap(true);
			jt.setWrapStyleWord(true);
			jt.setText(arg1 == null ? "" : arg1.toString()); 
			JScrollPane jsp = new JScrollPane(jt);
			return jsp; 

	
	}
}
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

public class SACGridBagLaiYRenderer extends JTextArea implements TableCellRenderer  {

	public Color color;
	public JTextField field ;
	public String beizhu = "±¸×¢";
	
	
	public SACGridBagLaiYRenderer(Color c) { 
		this.color = c;
		setLineWrap(true);
	    setWrapStyleWord(true);
	    setFont(new Font("ËÎÌå", 0, 12));
    } 
	
	
	public Component getTableCellRendererComponent(JTable arg0, Object arg1,
			boolean arg2, boolean arg3, int arg4, int arg5) {
			if(arg4!=0){
				JTextArea jt = new JTextArea(6,20);
				jt.setAutoscrolls(true);
				jt.setLineWrap(true);
				jt.setWrapStyleWord(true);
				jt.setText(arg1 == null ? "" : arg1.toString()); 
				JScrollPane jsp = new JScrollPane(jt);
				return jsp;
			}else{
				field = new JTextField();
				field.setBackground(color);
				field.setText(beizhu);
				return field;
			}
			 
	}
}
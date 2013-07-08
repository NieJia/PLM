package com.gdnz.sac1.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class S4CPRevisionMasterUI {

    
    public JTextField textrDCvoltage = null;//额定直流电压
	public JTextField textrACcurrent=null;//额定交流电流
	public JTextField texttinct = null;//颜色-柜型-尺寸
	public JTextField textdeclass=null;//需求分类
	
	public JTextArea textcPdiscri = null;//描述
	public JTextArea textremarkss = null;//备注
	
	public JTabbedPane jTabbedPane = null;
	public JTabbedPane getJTabbedPane(){
		return jTabbedPane;
	}

	public S4CPRevisionMasterUI(){
		//super(new GridLayout(1, 1));
		jTabbedPane = new JTabbedPane(JTabbedPane.NORTH);
		jTabbedPane.setBounds(0, 0, 500, 500);
		jTabbedPane.addTab("订单行物料", Panel());				
		}

	public JPanel Panel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


		JPanel panel0 = new JPanel(new BorderLayout());
		TitledBorder titleBorderMiaoShu = BorderFactory.createTitledBorder("描述");
		titleBorderMiaoShu.setTitlePosition(2);
		panel0.setBorder(titleBorderMiaoShu);
		
		textcPdiscri=new JTextArea(5,20);
		textcPdiscri.setLineWrap(true);

		JScrollPane jp0=new JScrollPane(textcPdiscri);
		jp0.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel0.add(jp0);
		
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(2,1,5,5));
		
		JPanel panel1t = new JPanel();
		panel1t.setLayout(new GridLayout(1, 2));
		JPanel panel1b = new JPanel();
		panel1b.setLayout(new GridLayout(1, 2));
		
		JPanel panel10=new JPanel();
		JPanel panel11=new JPanel();
		
		panel10.setLayout(new BoxLayout(panel10, BoxLayout.X_AXIS));
		panel11.setLayout(new BoxLayout(panel11, BoxLayout.X_AXIS));
		
		JPanel panel20=new JPanel();
		JPanel panel21=new JPanel();
		
		panel20.setLayout(new BoxLayout(panel20, BoxLayout.X_AXIS));
		panel21.setLayout(new BoxLayout(panel21, BoxLayout.X_AXIS));

		
		JLabel lbrDCvoltage = new JLabel("  额定直流电压 :");lbrDCvoltage.setPreferredSize(new Dimension(100,20));
	//	lbrDCvoltage.setHorizontalTextPosition(4);
		textrDCvoltage = new JTextField();
		
		JLabel lbrACcurrent = new JLabel("额定交流电流 :");lbrACcurrent.setPreferredSize(new Dimension(80,20));
	//	lbrACcurrent.setHorizontalTextPosition(4);
		textrACcurrent = new JTextField();


		
		JLabel lbtinct = new JLabel("  颜色-柜型-尺寸:");lbtinct.setPreferredSize(new Dimension(100,20));
	//	lbtinct.setHorizontalTextPosition(4);
		texttinct = new JTextField();

		
		JLabel lbdeclass = new JLabel("需求分类:");lbdeclass.setPreferredSize(new Dimension(80,20));
	//	lbdeclass.setHorizontalTextPosition(0);
		textdeclass = new JTextField();

		
		panel10.add(lbrDCvoltage);
		panel10.add(textrDCvoltage);
		panel1t.add(panel10);
		
		panel20.add(lbrACcurrent);
		panel20.add(textrACcurrent);
		panel1t.add(panel20);
			
		panel11.add(lbtinct);
		panel11.add(texttinct);
		panel1b.add(panel11);
		
		panel21.add(lbdeclass);
		panel21.add(textdeclass);
		panel1b.add(panel21);
		
		panel1.add(panel1t);
		panel1.add(panel1b);
		

		JPanel panel2 = new JPanel(new BorderLayout());
		TitledBorder titleBorderBeiZhu = BorderFactory.createTitledBorder("备注");
		titleBorderBeiZhu.setTitlePosition(2);
		panel2.setBorder(titleBorderBeiZhu);
		
		textremarkss= new JTextArea(5,20);
		textremarkss.setLineWrap(true);

		JScrollPane jp1=new JScrollPane(textremarkss);
		jp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel2.add(jp1);
		
		JPanel p0=new JPanel(new BorderLayout());
		p0.add(panel0,BorderLayout.NORTH);
		p0.add(panel1,BorderLayout.CENTER);
		
		JPanel p1=new JPanel(new BorderLayout());
		p1.add(p0,BorderLayout.NORTH);
		p1.add(panel2,BorderLayout.CENTER);
		
		JPanel p=new JPanel(new BorderLayout());
		p.add(p1,BorderLayout.NORTH);
		
		
		panel.add(p);
	    return panel;
	}

}

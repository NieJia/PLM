package com.gdnz.sac1.form;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import cn.com.origin.util.SACDocument;

public class S4XMSSJDUI {
	
   /*   public static void main(String args[]){
		 
	 S4XMSSJDUI s4XMSSJDUI = new S4XMSSJDUI();
			JTabbedPane jTabbedPane = s4XMSSJDUI.getJTabbedPane();
	    //  JPanel jp=s4XMSSJDUI.getJPanel();
			JFrame f=new JFrame();
			f.add(jTabbedPane);
			jTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		//	jp.setAutoscrolls(true);
			f.setVisible(true);
			f.pack();
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	 }
	 */
	  private static final long serialVersionUID = 1L;	
        public int maxlen=32;

		public JTextField textrepSchedule = null;//进度
		public JTextField textCNumber=null;//变更次数
		public JComboBox textSchControl = null;//进度监控偏差值
		
	//	public JTextField textSchControl = null;//进度监控偏差值
		public JTextField textProCompletion=null;//项目完成率
		public JTextField textActCosts= null;//实际发生

		public JTable tableZongJie = null;//总结表格
		
		public JTabbedPane jTabbedPane = null;
		public JTabbedPane getJTabbedPane(){
			return jTabbedPane;
		}
		public JPanel jpanel = null;
		public JPanel getJPanel(){
			return jpanel;
		}

		public S4XMSSJDUI(){
			//super(new GridLayout(1, 1));
			jTabbedPane = new JTabbedPane(JTabbedPane.NORTH);
			jTabbedPane.setBounds(0, 0, 500, 500);
			jTabbedPane.addTab("水电项目跟踪汇总", Panel());
		//	jpanel=new JPanel();
		//	jpanel.setBounds(0, 0, 500, 500);
			
			
			}

	    public JPanel Panel(){
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			
			JPanel paneltop=new JPanel(new BorderLayout());

			JPanel panel0=new JPanel();
			panel0.setLayout(new BorderLayout());
			JPanel panel0t=new JPanel();
			panel0t.setLayout(new BoxLayout(panel0t, BoxLayout.Y_AXIS));
			JPanel panel0b=new JPanel();
			panel0b.setLayout(new GridLayout(3,1));
			
			JLabel lbrepSchedule=new JLabel();
			lbrepSchedule.setText("  进度");lbrepSchedule.setPreferredSize(new Dimension(60,40));
			//lbrepSchedule.setHorizontalTextPosition(JLabel.CENTER);//文本居中显示
			
			JLabel lbplan=new JLabel();
			lbplan.setText("  计划");lbplan.setPreferredSize(new Dimension(60,20));
			JLabel lbchange=new JLabel();
			lbchange.setText("  变更");lbchange.setPreferredSize(new Dimension(60,20));
			JLabel lbexecution=new JLabel();
			lbexecution.setText("执行说明");lbexecution.setPreferredSize(new Dimension(60,20));
			

			panel0t.add(lbrepSchedule);	
			panel0b.add(lbplan);
			panel0b.add(lbchange);
			panel0b.add(lbexecution);
			
			panel0.add(panel0t,BorderLayout.NORTH);
			panel0.add(panel0b,BorderLayout.SOUTH);
		
			
			JPanel panel1=new JPanel();
			panel1.setLayout(new BorderLayout());
			
			JPanel panel1t=new JPanel();//上
			panel1t.setLayout(new BoxLayout(panel1t, BoxLayout.X_AXIS));
			JPanel panel1b=new JPanel();//下
			panel1b.setLayout(new BoxLayout(panel1b, BoxLayout.X_AXIS));
			
			textrepSchedule=new JTextField();
			textrepSchedule.setHorizontalAlignment(JTextField.CENTER);
			SACDocument doctextrepSchedule=new SACDocument();
			doctextrepSchedule.setMaxLength(maxlen);
			textrepSchedule.setDocument(doctextrepSchedule);
			
			panel1t.add(textrepSchedule);
			
			String[] columnNames = {"1", "2","3","4","5", "6","7","8","9","10","11","12"};
			Object[][] cellData = {{"","", "","","","","", "","","","",""},{"","", "","","","","", "","","","",""},{"","", "","","","","", "","","","",""}};
			DefaultTableModel tableModel = new DefaultTableModel(cellData,columnNames);
			tableZongJie = new JTable(tableModel);
			tableZongJie.setRowHeight(20);
			tableZongJie.getTableHeader().setReorderingAllowed( false ) ;
			tableZongJie.setPreferredScrollableViewportSize(new Dimension(500, 60));
			JScrollPane scrollPaneZongJie= new JScrollPane(tableZongJie);

            tableZongJie.setDefaultEditor(Object.class, new MyTableCellEditor());
        //   TableColumn d=tableZongJie.getColumn("1");
        //   d.setCellEditor(new DefaultCellEditor(boxplan));
           
         //  TableColumn d1=tableZongJie.getColumn("2");
        //   d1.setCellEditor(new DefaultCellEditor(boxplan));
           
         //  TableColumn d2=tableZongJie.getColumn("3");
        //   d2.setCellEditor(new DefaultCellEditor(boxplan));
			
		
		//	tableZongJie.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			panel1b.add(scrollPaneZongJie);
			
			panel1.add(panel1t,BorderLayout.NORTH);
			panel1.add(panel1b,BorderLayout.CENTER);

			 paneltop.add(panel0,BorderLayout.WEST);
             paneltop.add(panel1);
             
             JPanel panelbottom=new JPanel();
             panelbottom.setLayout(new GridLayout(2,2));
             
             JPanel panelbottom0=new JPanel();
             panelbottom0.setLayout(new BoxLayout(panelbottom0, BoxLayout.X_AXIS));
             JPanel panelbottom1=new JPanel();
             panelbottom1.setLayout(new BoxLayout(panelbottom1, BoxLayout.X_AXIS));
             JPanel panelbottom2=new JPanel();
             panelbottom2.setLayout(new BoxLayout(panelbottom2, BoxLayout.X_AXIS));
             JPanel panelbottom3=new JPanel();
             panelbottom3.setLayout(new BoxLayout(panelbottom3, BoxLayout.X_AXIS));
             
             JLabel lbcNumber=new JLabel();
             lbcNumber.setText("变更次数");lbcNumber.setPreferredSize(new Dimension(60,20));
             textCNumber=new JTextField();
             SACDocument doctextCNumber=new SACDocument();
             doctextCNumber.setMaxLength(maxlen);
             textCNumber.setDocument(doctextCNumber);
             
 			 JLabel lbschControl=new JLabel();
 			 lbschControl.setText("进度监控偏差");lbschControl.setPreferredSize(new Dimension(80,20));
 			 textSchControl=new JComboBox(new String[]{"","正常","延迟>15","警告>30","暂停","正式归档"});
 		//	 textSchControl=new JTextField();
 			 
 			 JLabel lbactCosts=new JLabel();
 			 lbactCosts.setText("实际发生");lbactCosts.setPreferredSize(new Dimension(60,20));
 			 textActCosts=new JTextField();
 			 SACDocument doctextActCosts=new SACDocument();
 			 doctextActCosts.setMaxLength(maxlen);
 			 textActCosts.setDocument(doctextActCosts);
 			 
 			 JLabel lbproCompletion=new JLabel();
 			 lbproCompletion.setText("项目完成率");lbproCompletion.setPreferredSize(new Dimension(80,20));
 			 textProCompletion=new JTextField();;
 			 SACDocument doctextProCompletion=new SACDocument();
 			 doctextProCompletion.setMaxLength(maxlen);
 			 textProCompletion.setDocument(doctextProCompletion);

 			
 			panelbottom0.add(lbcNumber);
 			panelbottom0.add(textCNumber);
 			
 			panelbottom1.add(lbschControl);
 			panelbottom1.add(textSchControl);
 			
 			
 			panelbottom2.add(lbactCosts);
 			panelbottom2.add(textActCosts);
 			
 			panelbottom3.add(lbproCompletion);
 			panelbottom3.add(textProCompletion);
             
             
             panelbottom.add(panelbottom0);
             panelbottom.add(panelbottom1);
             panelbottom.add(panelbottom2);
             panelbottom.add(panelbottom3);

             
             JPanel p0=new JPanel(new BorderLayout());
             
             p0.add(paneltop,BorderLayout.NORTH);
             p0.add(panelbottom,BorderLayout.CENTER);
             
             JPanel p=new JPanel(new BorderLayout());
             p.add(p0,BorderLayout.NORTH);
             
             
             panel.add(p);
			 return panel;
		}

}

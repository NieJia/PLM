package com.gdnz.sac1.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagLayout;
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
import javax.swing.border.TitledBorder;

import cn.com.origin.util.SACDocument;

import com.teamcenter.rac.common.ActionAdapter;
import com.teamcenter.rac.form.AbstractTCForm;
import com.teamcenter.rac.kernel.TCException;



public class S4CSQXBGRevisionUI {
	/*public static void main(String args[]){
		 
		 S4CSQXBGRevisionUI myUI = new S4CSQXBGRevisionUI();
		 
			JTabbedPane jTabbedPane = myUI.getJTabbedPane();
			JFrame f=new JFrame();
			f.add(jTabbedPane);
			jTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			f.setVisible(true);
			f.pack();
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
			//for(int i=0;i< myUI.accessoryPanel.getComponentCount();i++)
		         
	 }*/
	    private static final long serialVersionUID = 1L;	
	    public String DEFAULT="最多只能填写256个字,";
	    public int maxlen=512;
	    public int textmaxlen=32;

		public JTextField textrepPeo = null;//报告人
		public DateChooserJButton buttonrepDate=null;//报告日期
		//public DateButton buttonrepDate=null;//报告日期
		public JTextField textassets=null;//所属产品
		public JTextField texttestedObj = null;//被测对象
		public JTextField textcurrversion=null;//当前版本号
		public JComboBox textstatus = null;//状态
		//public JTextField textkaiFaRenYuan = null;//开发人员
		public JTextArea textdefectDescribtion = null;//缺陷描述
		public JComboBox textdefectClass = null;//缺陷分类
		public JComboBox textservletlevel = null;//严重等级
		public JComboBox textproprior = null;//处理优先级
		public JTextArea textsuggestRepa = null;//修复建议
		public JTextField textresoversion = null;//解决后版本号
		public JTextField textresolvingPro = null;//解决人员
		public JTextArea textchangeRe = null;//更改记录
		public JTextArea textexegesisl = null;//缺陷修复注释	
		public JTextArea textexegesisr = null;//回归测试注释	
		

		
		public String path=null;//附件路径
		public int fujiancount=0;//附件个数
		public LinkLabel filelabel[]=new LinkLabel[50];
		public JPanel accessoryPanel=new JPanel(new GridLayout(1,1));
		
		public JPanel p[]=new JPanel[50];
		JPanel panel3=new JPanel(new BorderLayout());
		
		
		public String[] str={"","新建","打开","拒绝","处理中","再次打开","关闭"};
		
		public JTabbedPane jTabbedPane = null;
		public JTabbedPane getJTabbedPane(){
			return jTabbedPane;
		}

		public S4CSQXBGRevisionUI(){
			//super(new GridLayout(1, 1));
			jTabbedPane = new JTabbedPane(JTabbedPane.NORTH);
			jTabbedPane.setBounds(0, 0, 500, 500);
			jTabbedPane.addTab("缺陷报告", Panel());				
			}
		
		public void TextPrompts( JTextArea td0,final String str_tip){
			final JTextArea td=td0;
			//td.setText(DEFAULT);
			//td.setForeground(Color.GRAY);
			td.addFocusListener(new FocusAdapter(){
				
				 public void focusGained(FocusEvent e) { 
					     if(td.getText().equals(str_tip) ) {
					    	 
					    	 td.setText("");
					    	 td.setForeground(Color.BLACK);
					     }   
					 }          
				 @Override 
				 public void focusLost(FocusEvent e) {  
					 if(td.getText().equals(""))  {
				    	 
						 td.setText(str_tip);
						 td.setForeground(Color.GRAY);
				    	 
				     }  
					 }
			});
			
			}

	    public JPanel Panel(){
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		//	panel.setLayout(new BorderLayout());

			JPanel panel0 = new JPanel(new BorderLayout());
			TitledBorder titleBorderQueXian = BorderFactory.createTitledBorder("缺陷报告");
			titleBorderQueXian.setTitlePosition(2);
			panel0.setBorder(titleBorderQueXian);

		    JPanel panelA = new JPanel(new BorderLayout());
		    JPanel paneltop = new JPanel(new BorderLayout());
			JPanel panel1=new JPanel(new GridLayout(3,2));
			JPanel panel10=new JPanel();
			JPanel panel11=new JPanel();
			JPanel panel12=new JPanel();
			JPanel panel13=new JPanel();
			JPanel panel14=new JPanel();
			JPanel panel15=new JPanel();
			panel10.setLayout(new BoxLayout(panel10, BoxLayout.X_AXIS));
			panel11.setLayout(new BorderLayout());
			panel12.setLayout(new BoxLayout(panel12, BoxLayout.X_AXIS));
			panel13.setLayout(new BoxLayout(panel13, BoxLayout.X_AXIS));
			panel14.setLayout(new BoxLayout(panel14, BoxLayout.X_AXIS));
			panel15.setLayout(new BoxLayout(panel15, BoxLayout.X_AXIS));
			
			JLabel lbrepPeo = new JLabel(" 报告人 :");lbrepPeo.setPreferredSize(new Dimension(80,20));
			textrepPeo = new JTextField();
			SACDocument doctextrepPeo=new SACDocument();
			doctextrepPeo.setMaxLength(textmaxlen);
			textrepPeo.setDocument(doctextrepPeo);
			
			JLabel lbrepDate = new JLabel(" 报告日期 :");lbrepDate.setPreferredSize(new Dimension(80,20));
			buttonrepDate=new DateChooserJButton();
			
			JLabel lbassets = new JLabel(" 所属产品 :");lbassets.setPreferredSize(new Dimension(80,20));
			textassets = new JTextField();
			SACDocument doctextassets=new SACDocument();
			doctextassets.setMaxLength(textmaxlen);
			textassets.setDocument(doctextassets);
			
			JLabel lbtestedObj = new JLabel(" 被测对象 :");lbtestedObj.setPreferredSize(new Dimension(80,20));
			texttestedObj = new JTextField();
			SACDocument doctexttestedObj=new SACDocument();
			doctexttestedObj.setMaxLength(textmaxlen);
			texttestedObj.setDocument(doctexttestedObj);
			
			
			JLabel lbcurrversion = new JLabel(" 当前版本号 :");lbcurrversion.setPreferredSize(new Dimension(80,20));
			textcurrversion=new JTextField();
			SACDocument doctextcurrversion=new SACDocument();
			doctextcurrversion.setMaxLength(textmaxlen);
			textcurrversion.setDocument(doctextcurrversion);
			
			
			JLabel lbstatus = new JLabel(" 状     态  :");lbstatus.setPreferredSize(new Dimension(80,20));
			textstatus = new JComboBox(new String[]{"","新建","打开","拒绝","处理中","再次打开","关闭"});
			
			
			panel10.add(lbrepPeo);
			panel10.add(textrepPeo);
			panel1.add(panel10);
			
			panel11.add(lbrepDate,BorderLayout.WEST);
			panel11.add(buttonrepDate,BorderLayout.CENTER);
			panel1.add(panel11);
			
			panel12.add(lbassets);
			panel12.add(textassets);
			panel1.add(panel12);
			
  		    panel13.add(lbtestedObj);
			panel13.add(texttestedObj);
			panel1.add(panel13);
			
			
			panel14.add(lbcurrversion);
			panel14.add(textcurrversion);
			panel1.add(panel14);
			
			
			panel15.add(lbstatus);
			panel15.add(textstatus);
			panel1.add(panel15);
			
			
			
			JPanel panel2 = new JPanel(new BorderLayout(5,20));
			TitledBorder titleBorderQueXianMiaoShu = BorderFactory.createTitledBorder("缺陷描述");
			titleBorderQueXian.setTitlePosition(2);
			panel2.setBorder(titleBorderQueXianMiaoShu);
			textdefectDescribtion = new JTextArea(5,20);
			textdefectDescribtion.setLineWrap(true);
			SACDocument doctextdefectDescribtion=new SACDocument();
			doctextdefectDescribtion.setMaxLength(maxlen);
			textdefectDescribtion.setDocument(doctextdefectDescribtion);
			TextPrompts(textdefectDescribtion,DEFAULT+"该部分由测试人员填写");
			
			//JScrollPane jp0=new JScrollPane(textdefectDescribtion,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			JScrollPane jp0=new JScrollPane(textdefectDescribtion);
			jp0.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			panel2.add(jp0);
			
			paneltop.add(panel1,BorderLayout.NORTH);
			paneltop.add(panel2,BorderLayout.SOUTH);
			
			JPanel panellow = new JPanel(new BorderLayout());
			
			//JPanel panel3=new JPanel(new BorderLayout());
			JPanel panel3_explore=new JPanel(new FlowLayout(FlowLayout.LEFT));
           JButton explore_button=new JButton("浏览");
			explore_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fDialog=new JFileChooser();
				fDialog.setDialogTitle("请选择所添加的附件");
				fDialog.setApproveButtonText("打开");
				fDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(JFileChooser.APPROVE_OPTION==fDialog.showOpenDialog(new Frame())){
					for(int i=0;i<fujiancount;i++){
						System.out.println(filelabel[i].getText());
						if(filelabel[i].getText().equals(fDialog.getSelectedFile().getName()))
						{
							JOptionPane.showMessageDialog(null, "温馨提示：您所添加的文件已存在，请重命名后再添加","提示",JOptionPane.ERROR_MESSAGE);
							return;

						}
					}
					path=fDialog.getSelectedFile().getPath();
					filelabel[fujiancount]=new LinkLabel(fDialog.getSelectedFile().getName(),new File(path));
					filelabel[fujiancount].setForeground(Color.BLUE);
					p[fujiancount]=new JPanel(new FlowLayout(FlowLayout.LEFT));
					p[fujiancount].add(filelabel[fujiancount]);
					final JLabel label_delete=new JLabel("删除");
					p[fujiancount].add(label_delete);
					accessoryPanel.setLayout(new GridLayout(accessoryPanel.getComponentCount()+1,1));//调整附件Panel的布局
					accessoryPanel.add(p[fujiancount]);
					label_delete.addMouseListener(new MouseAdapter(){
					public void mouseExited(MouseEvent e) {  
						label_delete.setForeground(Color.BLACK);
								    	  }                     
					public void mouseEntered(MouseEvent e) { 
						label_delete.setForeground(Color.RED);
						label_delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					}
						
						public void mouseClicked(MouseEvent e) {
						//	JOptionPane.showConfirmDialog(null, "您确定要删除该附件吗？","删除附件",JOptionPane.YES_NO_OPTION);
							int result=JOptionPane.showConfirmDialog(null, "您确定要删除该附件吗？","删除附件",JOptionPane.YES_NO_OPTION);
							if(result==JOptionPane.YES_OPTION){
							accessoryPanel.setLayout(new GridLayout(accessoryPanel.getComponentCount()-1,1));//调整附件Panel的布局
							int j=0;
							for(int i=0;i<fujiancount;i++)
								if(filelabel[i].getParent()==e.getComponent().getParent())
									{
									   j=i;//选择删除的那个附件  
									   break;
									}
							for(;j<fujiancount-1;j++){
								filelabel[j].setText(filelabel[j+1].getText());
								filelabel[j].setPath(filelabel[j+1].getPath());
								//filelabel[j]=new LinkLabel(filelabel[j+1].getText(),filelabel[j+1].getPath());
								//filelabel[j].setSize(j-1);
								System.out.println("删除的下一个附件名称为："+filelabel[j].getText());
								
							}
						
							//accessoryPanel.remove(p);
							fujiancount--;
							accessoryPanel.remove(p[fujiancount]);
							accessoryPanel.setLayout(new GridLayout(fujiancount,1));
							jTabbedPane.repaint();//实时刷新标签上的内容
							jTabbedPane.setBounds(0, 0, 500, 500);
						//	jTabbedPane.setBounds(0, 0, jTabbedPane.getWidth(),jTabbedPane.getHeight());

							}
					}
						}
					);
					fujiancount++;
					jTabbedPane.repaint();//实时刷新标签上的内容

				}
				
			}
			});
			panel3_explore.add(explore_button);
			panel3.add(panel3_explore,BorderLayout.NORTH);
			panel3.add(accessoryPanel,BorderLayout.CENTER);
			
			
			JPanel panel4=new JPanel(new GridLayout(1,3));
			JPanel panel40=new JPanel();
			JPanel panel41=new JPanel();
			JPanel panel42=new JPanel();
		//	panel40=new JPanel();
		//	panel41=new JPanel();
		//    panel42=new JPanel();
			panel40.setLayout(new BoxLayout(panel40, BoxLayout.X_AXIS));
			panel41.setLayout(new BoxLayout(panel41, BoxLayout.X_AXIS));
			panel42.setLayout(new BoxLayout(panel42, BoxLayout.X_AXIS));
			//JPanel panel3=new JPanel(new FlowLayout());
			JLabel lbdefectClass = new JLabel(" 缺陷分类");
			textdefectClass = new JComboBox(new String[]{"","软件","硬件"});
		//	textdefectClass = new JComboBox(new String[]{"","software","hardware"});
			JLabel lbservletlevel = new JLabel(" 严重等级");
			textservletlevel = new JComboBox(new String[]{"","灾难","严重","一般","建议"});
			JLabel lbproprior = new JLabel(" 处理优先级");
			textproprior= new JComboBox(new String[]{"","最高优先级","次高优先级","中等优先级","最低优先级"});
			
			
			
		   panel40.add(lbdefectClass);
           panel40.add(textdefectClass);
           panel4.add(panel40);
           
           panel41.add(lbservletlevel);
           panel41.add(textservletlevel);
           panel4.add(panel41);
           
           
           panel42.add(lbproprior);
           panel42.add(textproprior);
           panel4.add(panel42);
           
           panellow.add(panel3,BorderLayout.NORTH);
           panellow.add(panel4,BorderLayout.SOUTH);
           
           panelA.add(paneltop,BorderLayout.NORTH);
           panelA.add(panellow,BorderLayout.SOUTH);
           
           JPanel panelB = new JPanel(new BorderLayout());
           
           JPanel panelcenter = new JPanel(new BorderLayout());
           
           JPanel panel5 = new JPanel(new BorderLayout());
			TitledBorder titleBorderXiuFuJianYi = BorderFactory.createTitledBorder("修复建议");
			titleBorderXiuFuJianYi.setTitlePosition(2);
			panel5.setBorder(titleBorderXiuFuJianYi);
			textsuggestRepa = new JTextArea(5,20);
			textsuggestRepa.setLineWrap(true);
			SACDocument doctextsuggestRepa=new SACDocument();
			doctextsuggestRepa.setMaxLength(maxlen);
			textsuggestRepa.setDocument(doctextsuggestRepa);
			TextPrompts(textsuggestRepa,DEFAULT+"该部分由项目经理填写");
			//JScrollPane jp1=new JScrollPane(textsuggestRepa,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			JScrollPane jp1=new JScrollPane(textsuggestRepa);
			jp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			panel5.add(jp1);
			
			
			JPanel panel6=new JPanel(new GridLayout(1,2));
			JPanel panel60=new JPanel();
			JPanel panel61=new JPanel();
			panel60.setLayout(new BoxLayout(panel60, BoxLayout.X_AXIS));
			panel61.setLayout(new BoxLayout(panel61, BoxLayout.X_AXIS));
			JLabel lbresolversion = new JLabel("解决后版本号 :");
			textresoversion = new JTextField();
			SACDocument doctextresoversion=new SACDocument();
			doctextresoversion.setMaxLength(textmaxlen);
			textresoversion.setDocument(doctextresoversion);
			TextPrompts(textsuggestRepa,"该部分由项目经理填写");
			
			JLabel lbresolvingPro = new JLabel("  解决人员 :");
			textresolvingPro = new JTextField();
			
			panel60.add(lbresolversion);
			panel60.add(textresoversion);
			panel6.add(panel60);
			
			panel61.add(lbresolvingPro);
			panel61.add(textresolvingPro);
			panel6.add(panel61);
			
			panelcenter.add(panel5,BorderLayout.NORTH);
			panelcenter.add(panel6,BorderLayout.SOUTH);
			
			JPanel panelsouth = new JPanel(new BorderLayout());
			 JPanel panel7 = new JPanel(new BorderLayout());
				TitledBorder titleBorderGengGaiJiLu = BorderFactory.createTitledBorder("更改记录");
				titleBorderGengGaiJiLu.setTitlePosition(2);
				panel7.setBorder(titleBorderGengGaiJiLu);
				textchangeRe = new JTextArea(5,20);
				textchangeRe.setLineWrap(true);
				SACDocument doctextchangeRe=new SACDocument();
				doctextchangeRe.setMaxLength(maxlen);
				textchangeRe.setDocument(doctextchangeRe);
				TextPrompts(textchangeRe,DEFAULT+"该部分由研发人员填写");
				//JScrollPane jp2=new JScrollPane(textchangeRe,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				JScrollPane jp2=new JScrollPane(textchangeRe);
				jp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				panel7.add(jp2);
			
			
			 JPanel panel8 = new JPanel(new GridLayout(1,2));
			 JPanel panel8l = new JPanel(new BorderLayout());
			 JPanel panel8r = new JPanel(new BorderLayout());
				TitledBorder titleBorderZhuShileft = BorderFactory.createTitledBorder("缺陷修复注释");
				titleBorderZhuShileft.setTitlePosition(2);
				panel8l.setBorder(titleBorderZhuShileft);
				textexegesisl = new JTextArea(10,20);
				textexegesisl.setLineWrap(true);
				SACDocument doctextexegesisl=new SACDocument();
				doctextexegesisl.setMaxLength(maxlen);
				textexegesisl.setDocument(doctextexegesisl);
				TextPrompts(textexegesisl,DEFAULT+"该部分由研发人员填写");
				//JScrollPane jp3=new JScrollPane(textexegesis,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				JScrollPane jp3=new JScrollPane(textexegesisl);
				jp3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				panel8l.add(jp3);
				TitledBorder titleBorderZhuShiright = BorderFactory.createTitledBorder("回归测试注释");
				titleBorderZhuShiright.setTitlePosition(2);
				panel8r.setBorder(titleBorderZhuShiright);
				textexegesisr = new JTextArea(10,20);
				textexegesisr.setLineWrap(true);
				SACDocument doctextexegesisr=new SACDocument();
				doctextexegesisr.setMaxLength(maxlen);
				textexegesisr.setDocument(doctextexegesisr);
				TextPrompts(textexegesisr,DEFAULT+"该部分由测试人员填写");
				//JScrollPane jp3=new JScrollPane(textexegesis,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				JScrollPane jp4=new JScrollPane(textexegesisr);
				jp3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				panel8r.add(jp4);
				
				panel8.add(panel8l);
				panel8.add(panel8r);
				
				
				
			
			panelsouth.add(panel7,BorderLayout.NORTH);
			panelsouth.add(panel8,BorderLayout.CENTER);
			
			panelB.add(panelcenter,BorderLayout.NORTH);
			panelB.add(panelsouth,BorderLayout.CENTER);

		
			panel0.add(panelA,BorderLayout.NORTH);	
			panel0.add(panelB,BorderLayout.CENTER);
			
			JScrollPane p=new JScrollPane(panel0);
			
			panel.add(p);
		//	panel.add(panel0);

			 return panel;
		}

}
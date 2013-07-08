package com.gdnz.sac1.form;


import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;

import cn.com.origin.util.SACDocument;
 

class S4HDKJXMJZYB_UI {
	
	private static final long serialVersionUID = 1L;
	public static final String wordsTips = "（建议此文本中内容应不多于1024个字）";

	public String exportPath = null;

	
	public JTextField textPrjName = null;//课题名称
	public JTextField textChargeFirm = null;//承担单位
	public JTextField textPrjNumber = null;//课题编号
	public JTextField textPrjLeader = null;//课题组长
	public JTextField textTelephone = null;//电话、手机
	public JTextField textTotalInvest = null;//总投资
	public JTextField textBudget = null;//计划金额
	public JTextArea textCurrentGain = null; //已取得的成果
	public JTextArea textExistProblem = null; //存在的问题
	public JTextArea textNextPlan = null; //下一步计划及措施
	public JTextArea textDeadline = null; //预计完成时间
	
	public JTable tableBudget = null;//经费来源
	public JLabel lbPrjName = null;
	
	public JButton btnSelect = null;

	
	public SACDocument gainDoc = null;
	public SACDocument problemDoc = null;
	public SACDocument planDoc = null;
	public SACDocument deadlineDoc = null;
	
	public SACDocument numDoc = null;
	public SACDocument leaderDoc = null;
	public SACDocument phoneDoc = null;
	public SACDocument budgetDoc = null;
	
	public SACDocument nameDoc = null;
	public SACDocument investDoc = null;
	public SACDocument firmDoc = null;




	
	public JPanel panel = null;
	
	public JPanel getJPanel(){
		return panel;
	}
	
	public S4HDKJXMJZYB_UI(){
		panel = new JPanel(new GridLayout(1,1));
		panel.setBounds(0, 0, 800, 800);
		panel.add(applyPanel());
	
	}
	
	
	public JPanel applyPanel(){
	
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		TitledBorder nameTitleBorder = BorderFactory.createTitledBorder("");
		nameTitleBorder.setTitlePosition(2);
		namePanel.setBorder(nameTitleBorder);
		
		textPrjName = new JTextField();
		lbPrjName = new JLabel("  课题名称");
		lbPrjName.setPreferredSize(new Dimension(80, 20));
		nameDoc = new SACDocument();
		nameDoc.setMaxLength(64);
		textPrjName.setDocument(nameDoc);


		namePanel.add(lbPrjName);
		namePanel.add(textPrjName);
		
		JPanel PrjInfoPanel = new JPanel(new GridLayout(2, 2, 2, 2));
		TitledBorder infoTitleBorder = BorderFactory.createTitledBorder("");
		infoTitleBorder.setTitlePosition(2);
		PrjInfoPanel.setBorder(infoTitleBorder);

		
		JPanel PrjInfo1Panel = new JPanel();
		PrjInfo1Panel.setLayout(new BoxLayout(PrjInfo1Panel, BoxLayout.X_AXIS));
		
		
		JPanel PrjInfo2Panel = new JPanel();
		PrjInfo2Panel.setLayout(new BoxLayout(PrjInfo2Panel, BoxLayout.X_AXIS));
		
		JPanel PrjInfo3Panel = new JPanel();
		PrjInfo3Panel.setLayout(new BoxLayout(PrjInfo3Panel, BoxLayout.X_AXIS));
		
		JPanel PrjInfo4Panel = new JPanel();
		PrjInfo4Panel.setLayout(new BoxLayout(PrjInfo4Panel, BoxLayout.X_AXIS));
		
		JLabel lbPrjFirm = new JLabel("  承担单位");
		lbPrjFirm.setPreferredSize(new Dimension(80, 20));
		textChargeFirm = new JTextField();
		firmDoc = new SACDocument();
		firmDoc.setMaxLength(32);
		textChargeFirm.setDocument(firmDoc);
		
		JLabel lbPrjId = new JLabel("  课题编号");
		lbPrjId.setPreferredSize(new Dimension(80, 20));
		textPrjNumber = new JTextField();
		numDoc = new SACDocument();
		numDoc.setMaxLength(32);
		textPrjNumber.setDocument(numDoc);
		
		JLabel lbPrjLeader = new JLabel("  课题组长");
		lbPrjLeader.setPreferredSize(new Dimension(80, 20));
		textPrjLeader = new JTextField();
		leaderDoc = new SACDocument();
		leaderDoc.setMaxLength(32);
		textPrjLeader.setDocument(leaderDoc);
		
		JLabel lbPhone = new JLabel("  电话/手机 ");
		lbPhone.setPreferredSize(new Dimension(80, 20));
		textTelephone = new JTextField();
		phoneDoc = new SACDocument();
		phoneDoc.setMaxLength(32);
		textTelephone.setDocument(phoneDoc);
		
		PrjInfo1Panel.add(lbPrjFirm);
		PrjInfo1Panel.add(textChargeFirm);
		PrjInfo2Panel.add(lbPrjId);
		PrjInfo2Panel.add(textPrjNumber);
		PrjInfo3Panel.add(lbPrjLeader);
		PrjInfo3Panel.add(textPrjLeader);
		PrjInfo4Panel.add(lbPhone);
		PrjInfo4Panel.add(textTelephone);
		
		PrjInfoPanel.add(PrjInfo1Panel);
		PrjInfoPanel.add(PrjInfo2Panel);
		PrjInfoPanel.add(PrjInfo3Panel);
		PrjInfoPanel.add(PrjInfo4Panel);

		
		JPanel costPanel = new JPanel();
		costPanel.setLayout(new BoxLayout(costPanel, BoxLayout.Y_AXIS));	

		
		TitledBorder costTitlePanel = BorderFactory.createTitledBorder("经费使用情况: ");
		costTitlePanel.setTitlePosition(2);
		costPanel.setBorder(costTitlePanel);
		
		JPanel investPanel = new JPanel(new GridLayout(1,2));
		
		JPanel invest1Panel = new JPanel();
		invest1Panel.setLayout(new BoxLayout(invest1Panel, BoxLayout.X_AXIS));	
		JPanel invest2Panel = new JPanel();
		invest2Panel.setLayout(new BoxLayout(invest2Panel, BoxLayout.X_AXIS));	
		JLabel lbTotalInvest = new JLabel("  总投资");
		lbTotalInvest.setPreferredSize(new Dimension(80, 20));
		textTotalInvest = new JTextField();
		investDoc = new SACDocument();
		investDoc.setMaxLength(32);
		textTotalInvest.setDocument(investDoc);
		
		
		JLabel lbBudget = new JLabel("   计划金额");
		lbBudget.setPreferredSize(new Dimension(80, 20));
		textBudget = new JTextField();
		budgetDoc = new SACDocument();
		budgetDoc.setMaxLength(32);
		textBudget.setDocument(budgetDoc);
		
		invest1Panel.add(lbTotalInvest);
		invest1Panel.add(textTotalInvest);
		invest2Panel.add(lbBudget);
		invest2Panel.add(textBudget);
		
		investPanel.add(invest1Panel);
		investPanel.add(invest2Panel);

		
		JPanel monthPanel = new JPanel(new GridLayout(1, 0));
		TitledBorder monthTitleBorder = BorderFactory.createTitledBorder("计划安排 （单位：万元）");
		monthTitleBorder.setTitlePosition(2);
		monthPanel.setBorder(monthTitleBorder);
		
		Object[] columnBudget = {"", "", "", "", "",""};
		Object[][] dataBudget = {{"本年发生累计", "小计", "  （）月", "  （）月", "  （）月", "  （）月"}
		, {"", "", "", "", "", ""}};


		DefaultTableCellRenderer dtc = new DefaultTableCellRenderer(){  

			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table,  
					Object value, boolean isSelected, boolean hasFocus,  
					int row, int column) {  
				if(row == 0)  {
					//setForeground(Color.GRAY);
					setBackground(lbPrjName.getBackground());
				}
				else{
					setBackground(Color.WHITE);
				}
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);  

			}  
		};
		
		DefaultTableModel tableModel = new DefaultTableModel(dataBudget,columnBudget){   
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row,int column){ 
				if ((column < 1 && row == 0)||(row == 0 && column == 1)) {
					return false;
				} else {
					return true;
				}
			}
		};

		tableBudget = new JTable(tableModel);
		tableBudget.getTableHeader().setReorderingAllowed( false ) ;
		tableBudget.setPreferredScrollableViewportSize(new Dimension(100, 40));
		tableBudget.getColumnModel().getColumn(0).setCellRenderer(dtc);
		tableBudget.getColumnModel().getColumn(1).setCellRenderer(dtc);
		tableBudget.getColumnModel().getColumn(2).setCellRenderer(dtc);
		tableBudget.getColumnModel().getColumn(3).setCellRenderer(dtc);
		tableBudget.getColumnModel().getColumn(4).setCellRenderer(dtc);
		tableBudget.getColumnModel().getColumn(5).setCellRenderer(dtc);
	
		JScrollPane scrollBudget = new JScrollPane(tableBudget);
		monthPanel.add(scrollBudget);

		investPanel.setSize(new Dimension(100, 40));
		costPanel.add(investPanel);
		costPanel.add(monthPanel);
		
		

		
		JPanel statusPanel = new JPanel(new GridLayout(4, 1, 2, 2));
		TitledBorder statusTitlePanel = BorderFactory.createTitledBorder("课题研究情况");
		statusTitlePanel.setTitlePosition(2);
		statusPanel.setBorder(statusTitlePanel);
		
		JPanel gainPanel = new JPanel(new BorderLayout());
		
		TitledBorder gainTitlePanel = BorderFactory.createTitledBorder("已取得的成果：");
		gainTitlePanel.setTitlePosition(2);
		gainPanel.setBorder(gainTitlePanel);
	
		textCurrentGain = new JTextArea(5, 10);
		textCurrentGain.setText("");
		gainDoc = new SACDocument();
		gainDoc.setMaxLength(2048);
		textCurrentGain.setDocument(gainDoc);
		textCurrentGain.setLineWrap(true);
		JScrollPane scrolGain=new JScrollPane(textCurrentGain);
		gainPanel.add(scrolGain);
		
		JPanel problemPanel = new JPanel(new GridLayout(1, 1, 2, 2));
		
		TitledBorder problemTitlePanel = BorderFactory.createTitledBorder("存在的问题：");
		problemTitlePanel.setTitlePosition(2);
		problemPanel.setBorder(problemTitlePanel);
		
		textExistProblem = new JTextArea(5, 10);
		textExistProblem.setText("");
		problemDoc = new SACDocument();
		problemDoc.setMaxLength(2048);
		textExistProblem.setDocument(problemDoc);
		textExistProblem.setLineWrap(true);
		JScrollPane scrolProblem = new JScrollPane(textExistProblem);	
		problemPanel.add(scrolProblem);
	
		JPanel planPanel = new JPanel(new GridLayout(1, 1, 2, 2));
		
		TitledBorder planTitlePanel = BorderFactory.createTitledBorder("下一步计划及措施：");
		planTitlePanel.setTitlePosition(2);
		planPanel.setBorder(planTitlePanel);
		
		textNextPlan = new JTextArea(5, 10);
		textNextPlan.setText("");
		planDoc = new SACDocument();
		planDoc.setMaxLength(2048);
		textNextPlan.setDocument(planDoc);
		textNextPlan.setLineWrap(true);
		JScrollPane scrolPlan = new JScrollPane(textNextPlan);	
		planPanel.add(scrolPlan);
		
		JPanel timePanel = new JPanel(new GridLayout(1, 1, 2, 2));
		
		TitledBorder timeTitlePanel = BorderFactory.createTitledBorder("预计完成时间：");
		timeTitlePanel.setTitlePosition(2);
		timePanel.setBorder(timeTitlePanel);
		
		textDeadline = new JTextArea(5, 10);
		textDeadline.setText("");
		deadlineDoc = new SACDocument();
		deadlineDoc.setMaxLength(512);
		textDeadline.setDocument(deadlineDoc);
		textDeadline.setLineWrap(true);
		JScrollPane scrolTime = new JScrollPane(textDeadline);	
		timePanel.add(scrolTime);
		
		
		statusPanel.add(gainPanel);
		statusPanel.add(problemPanel);
		statusPanel.add(planPanel);
		statusPanel.add(timePanel);
		
		JPanel exportPanel = new JPanel();
		btnSelect = new JButton("导出word到");
		btnSelect.addActionListener(new ActionListener() {

		
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fc = new JFileChooser(); 
				if(e.getSource()==btnSelect){ 
					/* 
					    这是尤为重要的。因为JFileChooser默认的是选择文件，而需要选目录。 
					    故要将DIRECTORIES_ONLY装入模型 
					另外，若选择文件，则无需此句 
					*/  System.out.println("B");
					      fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
					      int intRetVal = fc.showOpenDialog(null); 
					      if( intRetVal == JFileChooser.APPROVE_OPTION){ 
					    	  System.out.println("A");
					    	  exportPath = fc.getSelectedFile().getPath();
					    	
					    	  File file = new File(exportPath+"/中国华电集团公司科技项目研究进展月报表.doc");
					    	  if (!file.exists()) {
						    	  exportWord(exportPath);

					    	  }
					    	  
					    	  else {
					    		  int n = JOptionPane.showConfirmDialog(null, "文件已存在，确认覆盖吗？",
					    				  "提示", JOptionPane.YES_NO_OPTION);
					    		  
					    		  if (n == JOptionPane.YES_OPTION) {
					    			  exportWord(exportPath);
					    		  } 
					    		  
					    	  }
					      } 
				}
			}
		});
		exportPanel.add(btnSelect);
		
		
		panel.add(namePanel);
		panel.add(PrjInfoPanel);
		panel.add(costPanel);
		panel.add(statusPanel);

	
		textCurrentGain.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (textCurrentGain.getText().equals(wordsTips)) {
					textCurrentGain.setText("");
				}	
					
			}  

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}		
		});
		
		
		textExistProblem.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (textExistProblem.getText().equals(wordsTips)) {
					textExistProblem.setText("");
				}	
					
			}  

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}		
		});
		
			
		textNextPlan.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (textNextPlan.getText().equals(wordsTips)) {
					textNextPlan.setText("");
				}	
					
			}  

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}		
		});
		
			
		return panel;
	}
	
	public void exportWord(String exPath) { 
		
		System.out.println("AA");
		Document document = new Document(PageSize.A4); 
		
		try {  
			RtfWriter2.getInstance(document,
			new FileOutputStream(exPath+"/中国华电集团公司科技项目研究进展月报表.doc"));  
			document.open();  
 

 
			Paragraph ph = new Paragraph();  
			Font f  = new Font();  
 
			Paragraph p = new Paragraph("中国华电集团公司科技项目研究进展月报表", 
			new Font(Font.NORMAL, 14, Font.BOLD, new Color(0, 0, 0)) );  
			p.setAlignment(1);  
			document.add(p);  
			ph.setFont(f);  


			
			document.add(new Paragraph(""));  

	
			Table table = new Table(8,10);  

			table.setBorderWidth(1);  
			table.setBorderColor(Color.BLACK);  
			table.setPadding(0);  
			table.setSpacing(0);  
			
							
			Cell cName = new Cell("课题名称");
			cName.setRowspan(1);//当前单元格占两行,纵向跨度  
			cName.setColspan(2);
			cName.setUseAscender(true); 
			cName.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cName.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cName); 
			
			Cell cinName = new Cell(textPrjName.getText());
			cinName.setRowspan(1);//当前单元格占两行,纵向跨度  
			cinName.setColspan(6);
			table.addCell(cinName); 
			
			Cell cFirm = new Cell("承担单位");
			cFirm.setRowspan(1);//当前单元格占两行,纵向跨度  
			cFirm.setColspan(2);
			cFirm.setUseAscender(true); 
			cFirm.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cFirm.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cFirm); 
			
			Cell cinFirm = new Cell(textChargeFirm.getText());
			cinFirm.setRowspan(1);//当前单元格占两行,纵向跨度  
			cinFirm.setColspan(2);
			table.addCell(cinFirm); 
			
			Cell cNum = new Cell("课题编号");
			cNum.setRowspan(1);//当前单元格占两行,纵向跨度  
			cNum.setColspan(2);
			cNum.setUseAscender(true); 
			cNum.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cNum.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cNum); 
			
			Cell cinNum = new Cell(textPrjNumber.getText());
			cinNum.setRowspan(1);//当前单元格占两行,纵向跨度  
			cinNum.setColspan(2);
			table.addCell(cinNum);
			
			Cell cLeader = new Cell("课题组长");
			cLeader.setRowspan(1);//当前单元格占两行,纵向跨度  
			cLeader.setColspan(2);
			cLeader.setUseAscender(true); 
			cLeader.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cLeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cLeader); 
			
			Cell cinLeader = new Cell(textPrjLeader.getText());
			cinLeader.setRowspan(1);//当前单元格占两行,纵向跨度  
			cinLeader.setColspan(2);
			table.addCell(cinLeader);
			
			Cell cPhone = new Cell("电话/手机");
			cPhone.setRowspan(1);//当前单元格占两行,纵向跨度  
			cPhone.setColspan(2);
			cPhone.setUseAscender(true); 
			cPhone.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cPhone.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cPhone); 
			
			Cell cinPhone = new Cell(textTelephone.getText());
			cinPhone.setRowspan(1);//当前单元格占两行,纵向跨度  
			cinPhone.setColspan(2);
			table.addCell(cinPhone);
			
			Cell cCost = new Cell("经费 \n使用 \n情况");
			cCost.setRowspan(4);//当前单元格占两行,纵向跨度  
			cCost.setColspan(1);
			cCost.setUseAscender(true); 
			cCost.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cCost.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cCost); 
			
			Cell cSum = new Cell("总投资");
			cSum.setRowspan(1);//当前单元格占两行,纵向跨度  
			cSum.setColspan(1);
			cSum.setUseAscender(true); 
			cSum.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cSum.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cSum); 
			
			Cell cinSum = new Cell(textTotalInvest.getText());
			cinSum.setRowspan(1);//当前单元格占两行,纵向跨度  
			cinSum.setColspan(2);
			table.addCell(cinSum); 
			 
			Cell cBudget = new Cell("计划金额");
			cBudget.setRowspan(1);//当前单元格占两行,纵向跨度  
			cBudget.setColspan(2);
			cBudget.setUseAscender(true); 
			cBudget.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cBudget.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cBudget); 
			
			Cell cinBudget = new Cell(textBudget.getText());
			cinBudget.setRowspan(1);//当前单元格占两行,纵向跨度  
			cinBudget.setColspan(2);
			table.addCell(cinBudget); 
		
			Cell cCount = new Cell("本年发生 \n累计");
			cCount.setRowspan(2);//当前单元格占两行,纵向跨度  
			cCount.setColspan(2);
			cCount.setUseAscender(true); 
			cCount.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cCount.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cCount); 
			
			Cell cPlan = new Cell("计划安排（单位：万元）");
			cPlan.setRowspan(1);//当前单元格占两行,纵向跨度  
			cPlan.setColspan(5);
			cPlan.setUseAscender(true); 
			cPlan.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cPlan.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cPlan); 
			
		
			table.addCell(new Paragraph("  小计")); 
			table.addCell(new Paragraph(""+tableBudget.getValueAt(0, 2))); 
			table.addCell(new Paragraph(""+tableBudget.getValueAt(0, 3))); 
			table.addCell(new Paragraph(""+tableBudget.getValueAt(0, 4))); 
			table.addCell(new Paragraph(""+tableBudget.getValueAt(0, 5))); 
			
			Cell cinCount = new Cell("  "+tableBudget.getValueAt(1, 0));
			cinCount.setRowspan(1);//当前单元格占两行,纵向跨度  
			cinCount.setColspan(2);
			table.addCell(cinCount); 
			
			
			table.addCell(new Paragraph("  "+tableBudget.getValueAt(1, 1))); 
			table.addCell(new Paragraph("  "+tableBudget.getValueAt(1, 2))); 
			table.addCell(new Paragraph("  "+tableBudget.getValueAt(1, 3))); 
			table.addCell(new Paragraph("  "+tableBudget.getValueAt(1, 4))); 
			table.addCell(new Paragraph("  "+tableBudget.getValueAt(1, 5))); 


			Cell cSituation = new Cell("课题\n研究\n情况");
			cSituation.setRowspan(12);//当前单元格占两行,纵向跨度  
			cSituation.setColspan(1);
			cSituation.setUseAscender(true); 
			cSituation.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cSituation.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cSituation); 
			
			
			Cell cGain = new Cell("已取得的成果");
			cGain.setRowspan(3);//当前单元格占两行,纵向跨度  
			cGain.setColspan(2);
			cGain.setUseAscender(true); 
			cGain.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cGain.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cGain); 
			
			Cell cinGain = new Cell(textCurrentGain.getText());
			cinGain.setRowspan(3);//当前单元格占两行,纵向跨度  
			cinGain.setColspan(5);
			table.addCell(cinGain); 
			
			Cell cProblem = new Cell("存在的问题");
			cProblem.setRowspan(3);//当前单元格占两行,纵向跨度  
			cProblem.setColspan(2);
			cProblem.setUseAscender(true); 
			cProblem.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cProblem.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cProblem); 
			
			Cell cinProblem = new Cell(textExistProblem.getText());
			cinProblem.setRowspan(3);//当前单元格占两行,纵向跨度  
			cinProblem.setColspan(5);
			table.addCell(cinProblem); 
			
			Cell cNext = new Cell("下一步计划及措施");
			cNext.setRowspan(3);//当前单元格占两行,纵向跨度  
			cNext.setColspan(2);
			cNext.setUseAscender(true); 
			cNext.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cNext.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cNext); 
			
			Cell cinNext = new Cell(textNextPlan.getText());
			cinNext.setRowspan(3);//当前单元格占两行,纵向跨度  
			cinNext.setColspan(5);
			table.addCell(cinNext); 
			
	
			Cell cTime = new Cell("预计完成时间");
			cTime.setRowspan(3);//当前单元格占两行,纵向跨度  
			cTime.setColspan(2);
			cTime.setUseAscender(true); 
			cTime.setVerticalAlignment(Cell.ALIGN_MIDDLE); 
			cTime.setHorizontalAlignment(Cell.ALIGN_CENTER);
			table.addCell(cTime); 
		
			
			Cell cinTime = new Cell(textDeadline.getText());
			cinTime.setRowspan(3);//当前单元格占两行,纵向跨度  
			cinTime.setColspan(5);
			table.addCell(cinTime); 
			
		
			document.add(table); 
			document.add(new Paragraph(""));  
			document.add(new Paragraph("           分管领导：             审核：                  课题组长："));  
			document.add(new Paragraph("           填报人：             电话/手机：                填报日期："));  


			document.close();  
			} catch (FileNotFoundException e) {  
				e.printStackTrace();  
			} catch (DocumentException e) {  
				e.printStackTrace();  
			} catch (IOException e) {  
				e.printStackTrace();  
			} 
		
	}  
	

}

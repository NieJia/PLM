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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import com.teamcenter.rac.util.MessageBox;






import cn.com.origin.util.SACDocument;

 

class S4SJGGUI {
	
	private static final long serialVersionUID = 1L;
	
	public static final String changeResonTips = "（必须详细说明此次更改的原因，如：用户要求、特殊工程、元器件或通用件变化、发现bug等）";
	public static final String wordsTips = "（建议此文本内容应不多于256个字）";
	public String exportPath = null;
	

	public JTextField textPrjName = null;//项目名称
	public JTextField textPrjNumber = null;//项目令号
	public JComboBox textDesignDpartextra = null;//设计danwei
	public JTextField textDesignDpart= null;//设计部门
	public JTextField textPrjOfficer = null;//项目负责人
	public JTextArea textChangeReason = null;//更改原因
	public JTextArea textChangeWay = null;//建议更改方法
	public JTextArea textNeedDocmen = null;//需要更改的图纸文件
	public JTextArea textDemand = null; //需求范围
	public JTextArea textTestCase = null; //测试方案与测试用例
	public JTextArea textDesign = null; //关联模板与系统设计
	public JTextArea textRemarks = null; //附注
	
	public JButton btnSelect = null;

	
	public SACDocument remarkDoc = null;
	public SACDocument reasonDoc = null;
	public SACDocument wayDoc = null;
	public SACDocument needDoc = null;
	public SACDocument caseDoc = null;
	public SACDocument designDoc = null;
	public SACDocument demandDoc = null;
	public SACDocument nameDoc = null;
	public SACDocument numDoc = null;
	public SACDocument dpartDoc = null;
	public SACDocument officerDoc = null;



	
	public JPanel panel = null;
	
	public JPanel getJPanel(){
		return panel;
	}
	
	public S4SJGGUI(){
		panel = new JPanel(new GridLayout(1,1));
		panel.setBounds(0, 0, 800, 800);
		
		panel.add(applyPanel());
	}
	
	
	public JPanel applyPanel(){
	
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel PrjInfoPanel = new JPanel(new GridLayout(2, 2));
	//	TitledBorder infoTitleBorder = BorderFactory.createTitledBorder("");
	//	infoTitleBorder.setTitlePosition(2);
	//	PrjInfoPanel .setBorder(infoTitleBorder);
		
		JPanel PrjInfo1Panel = new JPanel();
		PrjInfo1Panel.setLayout(new BoxLayout(PrjInfo1Panel, BoxLayout.X_AXIS));

		JPanel PrjInfo2Panel = new JPanel();
		PrjInfo2Panel.setLayout(new BoxLayout(PrjInfo2Panel, BoxLayout.X_AXIS));
		
		JPanel PrjInfo3Panel = new JPanel();
		PrjInfo3Panel.setLayout(new GridLayout(1,2));
		
		JPanel PrjInfo3Panell = new JPanel();
		PrjInfo3Panell.setLayout(new BoxLayout(PrjInfo3Panell, BoxLayout.X_AXIS));
		JPanel PrjInfo3Panelr = new JPanel();
		PrjInfo3Panelr.setLayout(new BoxLayout(PrjInfo3Panelr, BoxLayout.X_AXIS));
		
		JPanel PrjInfo4Panel = new JPanel();
		PrjInfo4Panel.setLayout(new BoxLayout(PrjInfo4Panel, BoxLayout.X_AXIS));
		
		JLabel lbPrjName = new JLabel("  项目名称    ");
		lbPrjName.setPreferredSize(new Dimension(80, 20));
		textPrjName = new JTextField();
		nameDoc = new SACDocument();
		nameDoc.setMaxLength(128);
		textPrjName.setDocument(nameDoc);
		
		JLabel lbPrjId = new JLabel("  项目令号    ");
		lbPrjId.setPreferredSize(new Dimension(80, 20));
		
		textPrjNumber = new JTextField();
		numDoc = new SACDocument();
		numDoc.setMaxLength(32);
		textPrjNumber.setDocument(numDoc);
		
		JLabel lbPrjClass = new JLabel("  设计部门    ");
		lbPrjClass.setPreferredSize(new Dimension(80, 20));
		
		JLabel lbPrjClassextra = new JLabel("  设计单位   ");
		lbPrjClassextra.setPreferredSize(new Dimension(80, 20));
		
		textDesignDpartextra = new JComboBox();
		textDesignDpart = new JTextField();
		
		dpartDoc = new SACDocument();
		dpartDoc.setMaxLength(32);
		textDesignDpart.setDocument(dpartDoc);
		
		JLabel lbPrjMan = new JLabel("  项目负责人");
		lbPrjMan.setPreferredSize(new Dimension(80, 20));
		textPrjOfficer = new JTextField();
		officerDoc = new SACDocument();
		officerDoc.setMaxLength(32);
		textPrjOfficer.setDocument(officerDoc);
		
		PrjInfo1Panel.add(lbPrjName);
		PrjInfo1Panel.add(textPrjName);
		PrjInfo2Panel.add(lbPrjId);
		PrjInfo2Panel.add(textPrjNumber);
		
		PrjInfo3Panell.add(lbPrjClassextra);
		PrjInfo3Panell.add(textDesignDpartextra);
		PrjInfo3Panelr.add(lbPrjClass);
		PrjInfo3Panelr.add(textDesignDpart);
				
		PrjInfo3Panel.add(PrjInfo3Panell);
		PrjInfo3Panel.add(PrjInfo3Panelr);
		
		PrjInfo4Panel.add(lbPrjMan);
		PrjInfo4Panel.add(textPrjOfficer);
		

		PrjInfoPanel.add(PrjInfo1Panel);
		PrjInfoPanel.add(PrjInfo2Panel);
		PrjInfoPanel.add(PrjInfo3Panel);
		PrjInfoPanel.add(PrjInfo4Panel);
	

		
		JPanel reasonPanel = new JPanel(new GridLayout(1, 1, 2, 2));
		
		TitledBorder titlereasonPanel = BorderFactory.createTitledBorder("更改原因: ");
		titlereasonPanel.setTitlePosition(2);
		reasonPanel.setBorder(titlereasonPanel);
	
		textChangeReason = new JTextArea(5, 20);
		textChangeReason.setText("");
		reasonDoc = new SACDocument();
		reasonDoc.setMaxLength(512);
		textChangeReason.setDocument(reasonDoc);
		textChangeReason.setLineWrap(true);
		JScrollPane scrolReason = new JScrollPane(textChangeReason);
		reasonPanel.add(scrolReason);
		
		JPanel wayPanel = new JPanel(new GridLayout(1, 1, 2, 2));
		
		TitledBorder titleWayPanel = BorderFactory.createTitledBorder("建议更改方法：");
		titleWayPanel.setTitlePosition(2);
		wayPanel.setBorder(titleWayPanel);
		
		textChangeWay = new JTextArea(5, 10);
		textChangeWay.setText("");
		wayDoc = new SACDocument();
		wayDoc.setMaxLength(512);
		textChangeWay.setDocument(wayDoc);
		textChangeWay.setLineWrap(true);
		JScrollPane scrolWay = new JScrollPane(textChangeWay);
		wayPanel.add(scrolWay);
		
		JPanel docPanel = new JPanel(new GridLayout(1, 1, 2, 2));
		
		TitledBorder titleDocPanel = BorderFactory.createTitledBorder("需要更改的图纸、文件：");
		titleDocPanel.setTitlePosition(2);
		docPanel.setBorder(titleDocPanel);
		
		textNeedDocmen = new JTextArea(5, 10);
		textNeedDocmen.setText("");
		needDoc = new SACDocument();
		needDoc.setMaxLength(512);
		textNeedDocmen.setDocument(needDoc);
		textNeedDocmen.setLineWrap(true);
		JScrollPane scrolDoc = new JScrollPane(textNeedDocmen);
		docPanel.add(scrolDoc);
		
		JPanel impactPanel = new JPanel(new GridLayout(3, 1, 2, 2));
		TitledBorder titleImpactPanel = BorderFactory.createTitledBorder("影响性分析及处理意见");
		titleImpactPanel.setTitlePosition(2);
		impactPanel.setBorder(titleImpactPanel);
		
		JPanel demandPanel = new JPanel(new GridLayout(1, 1, 2, 2));
		
		TitledBorder titleDemandPanel = BorderFactory.createTitledBorder("需求范围：");
		titleDemandPanel.setTitlePosition(2);
		demandPanel.setBorder(titleDemandPanel);
	
		textDemand = new JTextArea(5, 10);
		textDemand.setText("");
		demandDoc = new SACDocument();
		demandDoc.setMaxLength(512);
		textDemand.setDocument(demandDoc);
		textDemand.setLineWrap(true);
		JScrollPane scrolDem = new JScrollPane(textDemand);
		demandPanel.add(scrolDem);
		
		JPanel testPanel = new JPanel(new GridLayout(1, 1, 2, 2));
		
		TitledBorder titleTestPanel = BorderFactory.createTitledBorder("测试方案与测试用例：");
		titleTestPanel.setTitlePosition(2);
		testPanel.setBorder(titleTestPanel);
		
		textTestCase = new JTextArea(5, 10);
		textTestCase.setText("");
		caseDoc = new SACDocument();
		caseDoc.setMaxLength(512);
		textTestCase.setDocument(caseDoc);
		textTestCase.setLineWrap(true);
		JScrollPane scrolCase = new JScrollPane(textTestCase);
		testPanel.add(scrolCase);
	
		JPanel designPanel = new JPanel(new GridLayout(1, 1, 2, 2));
		
		TitledBorder titleDesignPanel = BorderFactory.createTitledBorder("关联模板与系统设计：");
		titleDesignPanel.setTitlePosition(2);
		designPanel.setBorder(titleDesignPanel);
		
		textDesign = new JTextArea(5, 10);
		textDesign.setText("");
		designDoc = new SACDocument();
		designDoc.setMaxLength(512);
		textDesign.setDocument(designDoc);
		textDesign.setLineWrap(true);
		JScrollPane scrolDesign = new JScrollPane(textDesign);
		designPanel.add(scrolDesign);
		
		impactPanel.add(demandPanel);
		impactPanel.add(testPanel);
		impactPanel.add(designPanel);
		
		JPanel PSPanel = new JPanel(new GridLayout(1, 1, 2, 2));
		
		TitledBorder titlePSPanel = BorderFactory.createTitledBorder("附注：");
		titlePSPanel.setTitlePosition(2);
		PSPanel.setBorder(titlePSPanel);
		
		textRemarks = new JTextArea(5, 10);
		remarkDoc = new SACDocument();
		remarkDoc.setMaxLength(512);
		textRemarks.setDocument(remarkDoc);
		textRemarks.setText("");
		textRemarks.setLineWrap(true);
		JScrollPane scrolRemarks = new JScrollPane(textRemarks);
		PSPanel.add(scrolRemarks);
		
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
					    	  
					    	  File file = new File(exportPath+"/设计更改申请表.doc");
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
					    	 
					    	  
					    	  System.out.println(fc.getSelectedFile().getName());

					      } 
				}
			}
		});
		exportPanel.add(btnSelect);
		
		
		panel.add(PrjInfoPanel);
		panel.add(reasonPanel);
		panel.add(wayPanel);
		panel.add(docPanel);
		panel.add(impactPanel);
		panel.add(PSPanel);
		
		textChangeReason.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (textChangeReason.getText().equals(changeResonTips)) {
					textChangeReason.setText("");
				}	
					
			}  
			
		

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}		
		});
		
		textChangeWay.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (textChangeWay.getText().equals(wordsTips)) {
					textChangeWay.setText("");
				}	
					
			}  

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}		
		});
		
			
		textNeedDocmen.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (textNeedDocmen.getText().equals(wordsTips)) {
					textNeedDocmen.setText("");
				}	
					
			}  

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}		
		});
		
		
		textDemand.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (textDemand.getText().equals(wordsTips)) {
					textDemand.setText("");
				}	
					
			}  

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}		
		});
		
		
		
		
		textTestCase.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (textTestCase.getText().equals(wordsTips)) {
					textTestCase.setText("");
				}	
					
			}  

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}		
		});
		
		textDesign.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (textDesign.getText().equals(wordsTips)) {
					textDesign.setText("");
				}	
					
			}  

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}		
		});
	
		textRemarks.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (textRemarks.getText().equals(wordsTips)) {
					textRemarks.setText("");
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
					new FileOutputStream(exPath+"/设计更改申请表.doc"));  
					document.open();  
	     
	   //设置合同头  
	     
					Paragraph ph = new Paragraph();  
					Font f  = new Font();  
	     
					Paragraph p = new Paragraph("国电南京自动化股份有限公司", 
					new Font(Font.NORMAL, 14, Font.BOLD, new Color(0, 0, 0)) );  
					p.setAlignment(1);  
					document.add(p);  
					ph.setFont(f);  
	    
	    //设置标题
					
					document.add(new Paragraph(""));  

	    
					Paragraph ph2 = new Paragraph();  
	      
					Paragraph p2 = new Paragraph("设计更改申请表", 
					new Font(Font.NORMAL, 16, Font.BOLD, new Color(0, 0, 0)) );  
					p2.setAlignment(1);  
					document.add(p2);  
					ph2.setFont(f);  
	  
					document.add(new Paragraph(""));  

					
					Table table = new Table(4,10);  
					document.add(new Paragraph("           编 号：                                             年  月  日"));  

					table.setBorderWidth(1);  
					table.setBorderColor(Color.BLACK);  
					table.setPadding(0);  
					table.setSpacing(0);  
	    
					table.addCell(new Paragraph("项目名称"));  
					
					Cell cinName = new Cell(textPrjName.getText());  
					cinName.setRowspan(1);
					cinName.setColspan(3);
					table.addCell(cinName); 
					
					table.addCell(new Paragraph("项目令号"));
					table.addCell(new Paragraph(textPrjNumber.getText()));  				
					table.addCell(new Paragraph("项目负责人")); 
					table.addCell(new Paragraph(textPrjOfficer.getText()));  
					
					table.addCell(new Paragraph("设计单位")); 
					table.addCell(new Paragraph(textDesignDpartextra.getSelectedItem().toString()));  
					table.addCell(new Paragraph("设计部门")); 
					table.addCell(new Paragraph(textDesignDpart.getText()));  

	    // 表格的主体  
					Cell cell = new Cell("更改原因：\n" +textChangeReason.getText()+"\n");  
					cell.setRowspan(2);//当前单元格占两行,纵向跨度  
					cell.setColspan(4);
					table.addCell(cell); 
	    
					Cell cWay = new Cell("建议更改方法：\n"+textChangeWay.getText()+"\n");  
			
					cWay.setRowspan(2);//当前单元格占两行,纵向跨度  
					cWay.setColspan(4);
					table.addCell(cWay); 
	    
					Cell cDoc = new Cell("需要更改的图纸、文件：\n"+textNeedDocmen.getText()+"\n");  
					cDoc.setRowspan(2);//当前单元格占两行,纵向跨度  
					cDoc.setColspan(4);
					table.addCell(cDoc);
					
					Cell cImpact = new Cell("影响性分析及处理意见\n"+
							"需求范围: \n"+textDemand.getText()+"\n\n测试方案与测试用例: \n"
							+textTestCase.getText()+"\n\n关联模板与系统设计: \n"+textDesign.getText()	+"\n"
							);  
					cImpact.setRowspan(2);//当前单元格占两行,纵向跨度  
					cImpact.setColspan(4);
					table.addCell(cImpact);
	    
					Cell cRemark = new Cell("附注：\n"+textRemarks.getText()+"\n");  
					cRemark.setRowspan(2);//当前单元格占两行,纵向跨度  
					cRemark.setColspan(4);
					table.addCell(cRemark);
	    
					document.add(table); 
					document.add(new Paragraph(""));   
					document.add(new Paragraph("           申请人：                 审核人：                  批准人："));  


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
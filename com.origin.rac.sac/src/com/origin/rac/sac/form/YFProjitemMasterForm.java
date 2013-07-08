package com.origin.rac.sac.form;
/**
 * @file YFProjitemMasterForm.java
 *
 * @brief 研发项目（鉴定类）Form客制化
 * 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import test.MQDocument;

import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.common.TCTable;
import com.teamcenter.rac.form.AbstractTCForm;
import com.teamcenter.rac.kernel.*;
import com.teamcenter.rac.util.*;

public class YFProjitemMasterForm extends AbstractTCForm
{
    private TCFormProperty[] formProperties = null;
    private Registry registry = Registry.getRegistry(this);
    private TCComponentForm form;
    private TCSession tcsession;
    private JTextField xmmciTextField;//项目名称
    private JTextField yzlhiTextField;//研制令号
    private JTextField syfwiTextField;//适用范围
    private JTextField qznyiTextField;//起止年月
    private JTextField cddwybmiTextField;//承担单位与部门
    private iTextArea ysfsiTextField; //研式方式
    private JTextField hzdwiTextField;//合作单位
    private JTextField hzfsiTextField;//合作方式
    private JTextField xmzysiTextField;//项目总预算
    private JTextField zjlyiTextField;//资金来源
    private iTextArea mbjsspiTextField;//目标技术水平
    private iTextArea zliTextField;//专利
    private iTextArea zzqiTextField;//著作权
    private iTextArea lwiTextField;//论文
    private iTextArea cgysfsiTextField;//成果验收方式
    private JTextField xmiTextField1;//姓名1
    private JTextField zciTextField1;//职称1
    private JTextField zyiTextField1;//专业1
    private JTextField dwbmiTextField1;//单位部门1
    private iTextArea lxfsiTextField1;//联系方式1
    private iTextArea emailiTextField1;//EMail1
    private JTextField xmiTextField2;//姓名2
    private JTextField zciTextField2;//职称2
    private JTextField zyiTextField2;//专业2
    private JTextField dwbmiTextField2;//单位部门2
    private iTextArea lxfsiTextField2;//联系方式2
    private iTextArea emailiTextField2;//EMail2
    private String yafs_str = "[注明是自主研发、合作研试、引进接产等，如有合作和外包，说明合作方或包方技术能力。]";
    private String cgysfs_str = "[国家级或省部级鉴定、企业级鉴定、"+"\n"+"集团或用户组织验收]";
    private String tel_str = "Tel:"+"\n"+"Mobile:";
    private MQDocument doc = null;
    private boolean flagStatu;
    private Font font;
    
    public YFProjitemMasterForm(TCComponentForm tccomponentform) throws Exception {
	super(tccomponentform);
	form = tccomponentform;
	tcsession = (TCSession) form.getSession();	
	System.out.println("tcsession====>:"+tcsession);
	initializeUI();
	loadForm();
    }
    /*
     * Form加载数据
     * 
     * */
    public void loadForm() throws TCException {
	try {
		String tmpCheckedOut = form.getProperty("checked_out");
		boolean isCheckedOut = false;
		if (tmpCheckedOut != null && tmpCheckedOut.length() > 0 && tmpCheckedOut.indexOf("Y") >= 0) {
			isCheckedOut = true;
		}
		flagStatu = isCheckedOut;
			if (flagStatu == false) {
				xmmciTextField.setEnabled(false);
				yzlhiTextField.setEnabled(false);
				syfwiTextField.setEnabled(false);
				qznyiTextField.setEnabled(false);
				cddwybmiTextField.setEnabled(false);
				ysfsiTextField.setEnabled(false);
				hzdwiTextField.setEnabled(false);
				hzfsiTextField.setEnabled(false);
				xmzysiTextField.setEnabled(false);
				zjlyiTextField.setEnabled(false);
				mbjsspiTextField.setEnabled(false);
				zliTextField.setEnabled(false);
				zzqiTextField.setEnabled(false);
				lwiTextField.setEnabled(false);
				cgysfsiTextField.setEnabled(false);
				xmiTextField1.setEnabled(false);
				zciTextField1.setEnabled(false);
				zyiTextField1.setEnabled(false);
				dwbmiTextField1.setEnabled(false);
				lxfsiTextField1.setEnabled(false);
				emailiTextField1.setEnabled(false);
				xmiTextField2.setEnabled(false);
				zciTextField2.setEnabled(false);
				zyiTextField2.setEnabled(false);
				dwbmiTextField2.setEnabled(false);
				lxfsiTextField2.setEnabled(false);
				emailiTextField2.setEnabled(false);
			} else {
				xmmciTextField.setEnabled(true);
				yzlhiTextField.setEnabled(true);
				syfwiTextField.setEnabled(true);
				qznyiTextField.setEnabled(true);
				cddwybmiTextField.setEnabled(true);
				ysfsiTextField.setEnabled(true);
				hzdwiTextField.setEnabled(true);
				hzfsiTextField.setEnabled(true);
				xmzysiTextField.setEnabled(true);
				zjlyiTextField.setEnabled(true);
				mbjsspiTextField.setEnabled(true);
				zliTextField.setEnabled(true);
				zzqiTextField.setEnabled(true);
				lwiTextField.setEnabled(true);
				cgysfsiTextField.setEnabled(true);
				xmiTextField1.setEnabled(true);
				zciTextField1.setEnabled(true);
				zyiTextField1.setEnabled(true);
				dwbmiTextField1.setEnabled(true);
				lxfsiTextField1.setEnabled(true);
				emailiTextField1.setEnabled(true);
				xmiTextField2.setEnabled(true);
				zciTextField2.setEnabled(true);
				zyiTextField2.setEnabled(true);
				dwbmiTextField2.setEnabled(true);
				lxfsiTextField2.setEnabled(true);
				emailiTextField2.setEnabled(true);
			}
		
	    formProperties = form.getAllFormProperties();
	    int k = formProperties.length;
	    for (int i = 0; i < k; i++) {
	    	String str = formProperties[i].getPropertyName();
	    	if(str.equals("s4prj_name1")){
	    		xmmciTextField.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4prj_id")){
	    		yzlhiTextField.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4scope")){
	    		syfwiTextField.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4timed")){
	    		qznyiTextField.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4tandd")){
	    		cddwybmiTextField.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4Grind_testw")){
	    		if(formProperties[i].getStringValue() != null && !"".equals(formProperties[i].getStringValue().toString())){
	    			ysfsiTextField.setText(formProperties[i].getStringValue());	
	    		}
	    	} else if(str.equals("s4Cooperator")){
	    		hzdwiTextField.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4Cooperation")){
	    		hzfsiTextField.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4prj_budget")){
	    		xmzysiTextField.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4cap_source")){
	    		zjlyiTextField.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4goal_resu")){
	    		mbjsspiTextField.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4patent")){
	    		zliTextField.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4copyright")){
	    		zzqiTextField.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4thesis")){
	    		lwiTextField.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4Results_acceway")){
	    		if(formProperties[i].getStringValue() != null && !"".equals(formProperties[i].getStringValue().toString())){
	    			cgysfsiTextField.setText(formProperties[i].getStringValue());	
	    		}
	    	} else if(str.equals("s4name")){
	    		xmiTextField1.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4title")){
	    		zciTextField1.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4profession")){
	    		zyiTextField1.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4department")){
	    		dwbmiTextField1.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4contact_way")){
	    		if(formProperties[i].getStringValue() != null && !"".equals(formProperties[i].getStringValue().toString())){
	    			lxfsiTextField1.setText(formProperties[i].getStringValue());	
	    		}
	    	} else if(str.equals("s4Email")){
	    		emailiTextField1.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4name1")){
	    		xmiTextField2.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4title1")){
	    		zciTextField2.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4profession1")){
	    		zyiTextField2.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4department1")){
	    		dwbmiTextField2.setText(formProperties[i].getStringValue());	
	    	} else if(str.equals("s4contact_way1")){
	    		if(formProperties[i].getStringValue() != null && !"".equals(formProperties[i].getStringValue().toString())){
	    			lxfsiTextField2.setText(formProperties[i].getStringValue());	
	    		}
	    	} else if(str.equals("s4Email1")){
	    		emailiTextField2.setText(formProperties[i].getStringValue());	
	    	} 
		}
	} catch (TCException tcexception) {
	    throw tcexception;
	}
		if (formProperties == null) {
		    Registry registry = Registry.getRegistry(this);
		    MessageBox.post("failedLoadingFormProperties","提示",MessageBox.INFORMATION);
		}
    }
    /*
     * Form保存数据
     * 
     * */
    public void saveForm() {
    	try {	  		

			for(int j=0; j<formProperties.length; j++){
				String string = formProperties[j].getPropertyName();
				 if(string.equals("s4prj_name1")){
					 formProperties[j].setStringValueData(xmmciTextField.getText());
				 } else if(string.equals("s4prj_id")){
					 formProperties[j].setStringValueData(yzlhiTextField.getText());
				 } else if(string.equals("s4scope")){
					 formProperties[j].setStringValueData(syfwiTextField.getText());
				 } else if(string.equals("s4timed")){
					 formProperties[j].setStringValueData(qznyiTextField.getText());
				 } else if(string.equals("s4tandd")){
					 formProperties[j].setStringValueData(cddwybmiTextField.getText());
				 } else if(string.equals("s4Grind_testw")){
					 formProperties[j].setStringValueData(ysfsiTextField.getText());
				 } else if(string.equals("s4Cooperator")){
					 formProperties[j].setStringValueData(hzdwiTextField.getText());
				 } else if(string.equals("s4Cooperation")){
					 formProperties[j].setStringValueData(hzfsiTextField.getText());
				 } else if(string.equals("s4prj_budget")){
					 formProperties[j].setStringValueData(xmzysiTextField.getText());
				 } else if(string.equals("s4cap_source")){
					 formProperties[j].setStringValueData(zjlyiTextField.getText());
				 } else if(string.equals("s4goal_resu")){
					 formProperties[j].setStringValueData(mbjsspiTextField.getText());
				 } else if(string.equals("s4patent")){
					 formProperties[j].setStringValueData(zliTextField.getText());
				 } else if(string.equals("s4copyright")){
					 formProperties[j].setStringValueData(zzqiTextField.getText());
				 } else if(string.equals("s4thesis")){
					 formProperties[j].setStringValueData(lwiTextField.getText());
				 } else if(string.equals("s4Results_acceway")){
					 formProperties[j].setStringValueData(cgysfsiTextField.getText());
				 } else if(string.equals("s4name")){
					 formProperties[j].setStringValueData(xmiTextField1.getText());
				 } else if(string.equals("s4title")){
					 formProperties[j].setStringValueData(zciTextField1.getText());
				 } else if(string.equals("s4profession")){
					 formProperties[j].setStringValueData(zyiTextField1.getText());
				 } else if(string.equals("s4department")){
					 formProperties[j].setStringValueData(dwbmiTextField1.getText());
				 } else if(string.equals("s4contact_way")){
					 formProperties[j].setStringValueData(lxfsiTextField1.getText());
				 } else if(string.equals("s4Email")){
					 formProperties[j].setStringValueData(emailiTextField1.getText());
				 } else if(string.equals("s4name1")){
					 formProperties[j].setStringValueData(xmiTextField2.getText());
				 } else if(string.equals("s4title1")){
					 formProperties[j].setStringValueData(zciTextField2.getText());
				 } else if(string.equals("s4profession1")){
					 formProperties[j].setStringValueData(zyiTextField2.getText());
				 } else if(string.equals("s4department1")){
					 formProperties[j].setStringValueData(dwbmiTextField2.getText());
				 } else if(string.equals("s4contact_way1")){
					 formProperties[j].setStringValueData(lxfsiTextField2.getText());
				 } else if(string.equals("s4Email1")){
					 formProperties[j].setStringValueData(emailiTextField2.getText());
				 } 
			}
			form.setTCProperties(formProperties);

	} catch (Exception exception) {
	    exception.printStackTrace();
	    MessageBox.post(exception);
	}
    	
   }
    
    
    /*
     * Form界面初始化
     * 
     * */
	private void initializeUI() {
		JPanel parentPanel = new JPanel(new BorderLayout());
		this.setLayout(new BorderLayout());
		font=new Font("宋体",Font.BOLD,12);
		// 项目简况面板
		JPanel xmjk_Panel = bulidxmjk_Panel();
		// 构造项目简况标签
		TitledBorder xmjkBorder = BorderFactory.createTitledBorder("项目简况");
//		TitledBorder xmjkBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,1), "项目简况", TitledBorder.LEFT, TitledBorder.TOP, font);
		xmjkBorder.setTitlePosition(TitledBorder.TOP);
		xmjkBorder.setTitleFont(font);
		xmjk_Panel.setBorder(xmjkBorder);
		// 项目负责人面板
		JPanel xmfzr_Panel = bulidxmfzr_Panel();
		// 构造项目负责人标签
		TitledBorder xmfzrBorder = BorderFactory.createTitledBorder("项目负责人");
		xmfzrBorder.setTitlePosition(TitledBorder.TOP);
		xmfzrBorder.setTitleFont(font);
		xmfzr_Panel.setBorder(xmfzrBorder);
		
		parentPanel.add(BorderLayout.CENTER, xmjk_Panel);
		add(BorderLayout.CENTER, parentPanel);
		add(BorderLayout.SOUTH, xmfzr_Panel);

	}
    
	
	/**
	 * 构造项目简况的Panel
	 */
	public JPanel bulidxmjk_Panel(){
		JPanel panel_xmjk = new JPanel(new PropertyLayout());
		JPanel panel_1 = new JPanel(new PropertyLayout());
		JLabel xmmc = new JLabel("项目名称");
		xmmciTextField = new JTextField(100);
		/*doc = new MQDocument();
		xmmciTextField.setDocument(doc);
		doc.setMaxLength(9); */
		JLabel yzlh = new JLabel("研制令号");
		yzlhiTextField = new JTextField(100);
		JLabel syfw = new JLabel("适用范围");
		syfwiTextField = new JTextField(100);
		JLabel qzny = new JLabel("起止年月");
		qznyiTextField = new JTextField(100);
		JLabel cddwybm = new JLabel("承担单位与部门");
		cddwybmiTextField = new JTextField(100);
		JLabel yzfs = new JLabel("研试方式");
		ysfsiTextField = new iTextArea(3,100);
		ysfsiTextField.setText(yafs_str);
		ysfsiTextField.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(yafs_str.equals(ysfsiTextField.getText().toString())){
					ysfsiTextField.setText("");
				}
				
			}
		});
		JScrollPane ysfs_jspane = new JScrollPane(ysfsiTextField);
		JLabel hzdw = new JLabel("合作单位");
		hzdwiTextField = new JTextField(45);
		JLabel hzfs = new JLabel("合作方式");
		hzfsiTextField = new JTextField(44);
		JLabel xmzys = new JLabel("项目总预算(万元)");
		xmzysiTextField = new JTextField(45);
		JLabel zjly = new JLabel("资金来源");
		zjlyiTextField = new JTextField(44);
		//预期目标Panel
		JPanel panel_yqmb = new JPanel(new PropertyLayout());
		// 构造预期目标及成果标签
		TitledBorder yqmbBorder = BorderFactory.createTitledBorder("预期目标及成果");
		yqmbBorder.setTitlePosition(TitledBorder.TOP);
		yqmbBorder.setTitleFont(font);
		panel_yqmb.setBorder(yqmbBorder);
		
		JPanel panel_mb = new JPanel(new PropertyLayout());
		JLabel mbjssp = new JLabel("目标技术水平");
		mbjsspiTextField = new iTextArea(10,34);
		JScrollPane mbjssp_jspane = new JScrollPane(mbjsspiTextField);
		
		JPanel panel_zl = new JPanel(new PropertyLayout());
		JLabel zlzzlw = new JLabel("专利/著作权/论文");
		//专利与论文Panel
		JPanel panel_zlylw = new JPanel(new PropertyLayout());
		JLabel zl = new JLabel("专利");
		zliTextField = new iTextArea(3,22);
		JScrollPane zl_jspane = new JScrollPane(zliTextField);
		JLabel zzq = new JLabel("著作权");
		zzqiTextField = new iTextArea(3,22);
		JScrollPane zzq_jspane = new JScrollPane(zzqiTextField);
		JLabel lw = new JLabel("论文");
		lwiTextField = new iTextArea(3,22);
		JScrollPane lw_jspane = new JScrollPane(lwiTextField);
	
		panel_mb.add("1.1.left",mbjssp);
		panel_mb.add("2.1.left",mbjssp_jspane);
		
		panel_zlylw.add("1.1.left",zl);
		panel_zlylw.add("1.2.left",zl_jspane);
		panel_zlylw.add("2.1.left",zzq);
		panel_zlylw.add("2.2.left",zzq_jspane);
		panel_zlylw.add("3.1.left",lw);
		panel_zlylw.add("3.2.left",lw_jspane);
		
		panel_zl.add("1.1.left",zlzzlw);
		panel_zl.add("2.1.left",panel_zlylw);
		
		JPanel panel_cg = new JPanel(new PropertyLayout());
		JLabel cgysfs = new JLabel("成果验收方式");
		cgysfsiTextField = new iTextArea(10,34);
		cgysfsiTextField.setText(cgysfs_str);
		cgysfsiTextField.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(cgysfs_str.equals(cgysfsiTextField.getText().toString())){
					cgysfsiTextField.setText("");
				}
				
			}
		});
		JScrollPane cgysfs_jspane = new JScrollPane(cgysfsiTextField);
		panel_cg.add("1.1.left",cgysfs);
		panel_cg.add("2.1.left",cgysfs_jspane);
		
		
		
		panel_yqmb.add("1.1.left",new JLabel("               "));
		panel_yqmb.add("1.2.left",panel_mb);
		panel_yqmb.add("1.3.left",panel_zl);
		panel_yqmb.add("1.4.left",panel_cg);
		
		
		
		panel_1.add("1.1.left",xmmc);
		panel_1.add("1.2.left",xmmciTextField);
		panel_1.add("2.1.left",yzlh);
		panel_1.add("2.2.left",yzlhiTextField);
		panel_1.add("3.1.left",syfw);
		panel_1.add("3.2.left",syfwiTextField);
		panel_1.add("4.1.left",qzny);
		panel_1.add("4.2.left",qznyiTextField);
		panel_1.add("5.1.left",cddwybm);
		panel_1.add("5.2.left",cddwybmiTextField);
		panel_1.add("6.1.left",yzfs);
		panel_1.add("6.2.left",ysfs_jspane);
		panel_1.add("7.1.left",hzdw);
		panel_1.add("7.2.left",hzdwiTextField);
		panel_1.add("7.3.left",hzfs);
		panel_1.add("7.4.left",hzfsiTextField);
		panel_1.add("8.1.left",xmzys);
		panel_1.add("8.2.left",xmzysiTextField);
		panel_1.add("8.3.left",zjly);
		panel_1.add("8.4.left",zjlyiTextField);
		panel_xmjk.add("1.1.left",panel_1);
		panel_xmjk.add("2.1.left",panel_yqmb);
		
		
		return panel_xmjk;
		
	}
	
	/**
	 * 构造项目负责人的Panel
	 * */
	public JPanel bulidxmfzr_Panel(){
		JPanel panel_fzr = new JPanel(new PropertyLayout());
		JLabel xm1 = new JLabel("姓名");
		xmiTextField1 = new JTextField(29);
		xmiTextField1.setText("aaaa");
		JLabel zc1 = new JLabel("职称");
		zciTextField1 = new JTextField(29);
		JLabel zy1 = new JLabel("专业");
		zyiTextField1 = new JTextField(29);
		JLabel dwbm1 = new JLabel("单位/部门");
		dwbmiTextField1 = new JTextField(100);
		JLabel lxfs1 = new JLabel("联系方式");
		lxfsiTextField1 = new iTextArea(3,47);
		lxfsiTextField1.setText(tel_str);
		lxfsiTextField1.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(tel_str.equals(lxfsiTextField1.getText().toString())){
					lxfsiTextField1.setText("");
				}
				
			}
		});
		JLabel email1 = new JLabel("Email");
		emailiTextField1 = new iTextArea(3,47);
		
		JLabel xm2 = new JLabel("姓名");
		xmiTextField2 = new JTextField(29);
		JLabel zc2 = new JLabel("职称");
		zciTextField2 = new JTextField(29);
		JLabel zy2 = new JLabel("专业");
		zyiTextField2 = new JTextField(29);
		JLabel dwbm2 = new JLabel("单位/部门");
		dwbmiTextField2 = new JTextField(100);
		JLabel lxfs2 = new JLabel("联系方式");
		lxfsiTextField2 = new iTextArea(3,47);
		lxfsiTextField2.setText(tel_str);
		lxfsiTextField2.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(tel_str.equals(lxfsiTextField2.getText().toString())){
					lxfsiTextField2.setText("");
				}
				
			}
		});
		JLabel email2 = new JLabel("Email");
		emailiTextField2 = new iTextArea(3,47);
		
		panel_fzr.add("1.1.left",xm1);
		panel_fzr.add("1.2.left",new JLabel("      "));
		panel_fzr.add("1.3.left",xmiTextField1);
		panel_fzr.add("1.4.left",zc1);
		panel_fzr.add("1.5.left",zciTextField1);
		panel_fzr.add("1.6.left",zy1);
		panel_fzr.add("1.7.left",zyiTextField1);
		panel_fzr.add("2.1.left",dwbm1);
		panel_fzr.add("2.2.left",new JLabel("      "));
		panel_fzr.add("2.3.left",dwbmiTextField1);
		panel_fzr.add("3.1.left",lxfs1);
		panel_fzr.add("3.2.left",new JLabel("      "));
		panel_fzr.add("3.3.left",lxfsiTextField1);
		panel_fzr.add("3.4.left",email1);
		panel_fzr.add("3.5.left",emailiTextField1);
		panel_fzr.add("4.1.left",xm2);
		panel_fzr.add("4.2.left",new JLabel("      "));
		panel_fzr.add("4.3.left",xmiTextField2);
		panel_fzr.add("4.4.left",zc2);
		panel_fzr.add("4.5.left",zciTextField2);
		panel_fzr.add("4.6.left",zy2);
		panel_fzr.add("4.7.left",zyiTextField2);
		panel_fzr.add("5.1.left",dwbm2);
		panel_fzr.add("5.2.left",new JLabel("      "));
		panel_fzr.add("5.3.left",dwbmiTextField2);
		panel_fzr.add("6.1.left",lxfs2);
		panel_fzr.add("6.2.left",new JLabel("      "));
		panel_fzr.add("6.3.left",lxfsiTextField2);
		panel_fzr.add("6.4.left",email2);
		panel_fzr.add("6.5.left",emailiTextField2);
		
		return panel_fzr;
		
	}
    
}
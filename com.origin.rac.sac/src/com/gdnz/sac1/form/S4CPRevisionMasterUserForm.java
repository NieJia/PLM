package com.gdnz.sac1.form;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.teamcenter.rac.form.AbstractTCForm;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCProperty;

public class S4CPRevisionMasterUserForm extends AbstractTCForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TCComponentForm form = null;
	private S4CPRevisionMasterUI s4CPRevisionMasterUI = null;
	
	private TCProperty rDCvoltage = null;//额定直流电压
	private TCProperty rACcurrent=null;//额定交流电流
	private TCProperty tinct = null;//颜色-柜型-尺寸
	private TCProperty declass=null;//需求分类
	private TCProperty cPdiscri = null;//描述
	private TCProperty remarkss = null;//备注
	
	public S4CPRevisionMasterUserForm(TCComponentForm arg0) throws Exception {
		super(arg0);
		// TODO Auto-generated constructor stub
		form=arg0;
		initUI();
		loadForm();
	}
	private void initUI() {
		// TODO Auto-generated method stub
		setLayout(new GridLayout(1, 1));
		s4CPRevisionMasterUI = new S4CPRevisionMasterUI();
		JTabbedPane jTabbedPane = s4CPRevisionMasterUI.getJTabbedPane();
		add(jTabbedPane);
		jTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
	}


	@Override
	public void loadForm() throws TCException {
		// TODO Auto-generated method stub
		System.out.println("********loading begin*********");
		rDCvoltage=form.getTCProperty("s4RDCvoltage");
		rACcurrent=form.getTCProperty("s4raccurrent");
		tinct=form.getTCProperty("s4tinct");
		declass=form.getTCProperty("s4declass");
		cPdiscri=form.getTCProperty("s4Cpdiscri");
		remarkss=form.getTCProperty("s4remarkss");
		
		s4CPRevisionMasterUI.textcPdiscri.setEnabled(false);
		s4CPRevisionMasterUI.textremarkss.setEnabled(false);
		s4CPRevisionMasterUI.textrDCvoltage.setEnabled(false);
		s4CPRevisionMasterUI.textrACcurrent.setEnabled(false);
		s4CPRevisionMasterUI.texttinct.setEnabled(false);
		s4CPRevisionMasterUI.textdeclass.setEnabled(false);
		

		s4CPRevisionMasterUI.textcPdiscri.setText(cPdiscri.getStringValue());
		s4CPRevisionMasterUI.textremarkss.setText(remarkss.getStringValue());
		s4CPRevisionMasterUI.textrDCvoltage.setText(rDCvoltage.getStringValue());
		s4CPRevisionMasterUI.textrACcurrent.setText(rACcurrent.getStringValue());
		s4CPRevisionMasterUI.texttinct.setText(tinct.getStringValue());
		s4CPRevisionMasterUI.textdeclass.setText(declass.getStringValue());
		
		System.out.println("********loading end*********");
	}

	@Override
	public void saveForm() {
		// TODO Auto-generated method stub
		System.out.println("********saving begin*********");
	
		cPdiscri.setStringValueData(s4CPRevisionMasterUI.textcPdiscri.getText());
		remarkss.setStringValueData(s4CPRevisionMasterUI.textremarkss.getText());
		rDCvoltage.setStringValueData(s4CPRevisionMasterUI.textrDCvoltage.getText());
		rACcurrent.setStringValueData(s4CPRevisionMasterUI.textrACcurrent.getText());
		tinct.setStringValueData(s4CPRevisionMasterUI.texttinct.getText());
		declass.setStringValueData(s4CPRevisionMasterUI.textdeclass.getText());
		
		TCProperty[] tcProperty = new TCProperty[6];
		tcProperty[0] = cPdiscri;
		tcProperty[1] = remarkss;
		tcProperty[2] = rDCvoltage;
		tcProperty[3] = rACcurrent;
		tcProperty[4] = tinct;
		tcProperty[5] = declass;
		try {
			form.setTCProperties(tcProperty);
		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("********saving end*********");
		

	}

}

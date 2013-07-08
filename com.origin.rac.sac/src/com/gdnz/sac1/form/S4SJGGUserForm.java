package com.gdnz.sac1.form;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.form.AbstractTCForm;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCPreferenceService;
import com.teamcenter.rac.kernel.TCProperty;
import com.teamcenter.rac.kernel.TCSession;

public class S4SJGGUserForm extends AbstractTCForm {
	
	private static final long serialVersionUID = 1L;
	private TCComponentForm form = null;
	private TCComponentItem item = null;
	private TCComponentItemRevision ir = null;
	private TCComponentFolder folder1 = null;
	private TCComponentFolder folder2 = null;
	private TCComponentItem fatherItem = null;

	private S4SJGGUI s4SJGGUI = null;
	
	public TCProperty prjName = null;//项目名称
	public TCProperty prjNumber = null;//项目令号
	public TCProperty designDpart = null;//设计部门
	public TCProperty designDpartextra = null;//设计部门Extra
	public TCProperty prjOfficer = null;//项目负责人
	public TCProperty changeReson = null;//更改原因
	public TCProperty changeWay = null;//建议更改方法
	public TCProperty needDocmen = null;//需要更改的图纸文件
	public TCProperty demand = null;// 需求范围
	public TCProperty testcase = null;//测试方案与测试用例
	public TCProperty design = null;//关联模板与系统设计
	public TCProperty remarks = null;//附注
	
	private String preferenceName = "SAC_Department_Lov";
	private String[] optionKeys = null;
	



	public S4SJGGUserForm(TCComponentForm arg0) throws Exception {
		super(arg0);
		// TODO Auto-generated constructor stub
		form = arg0;
		initUI();
		loadForm();	
	}

	@Override
	public void loadForm() throws TCException {
		// TODO Auto-generated method stub
		 TCSession session = form.getSession();
			optionKeys = getTCPreferenceArray(session, preferenceName);
			for(int i=0;i<optionKeys.length;i++)
			{
				s4SJGGUI.textDesignDpartextra.addItem(optionKeys[i].trim());
			}
			s4SJGGUI.textDesignDpartextra.setSelectedIndex(0);
		
		prjName = form.getTCProperty("s4Prjname");
		prjNumber = form.getTCProperty("s4Prjnumber");
		designDpart = form.getTCProperty("s4DesignDpart");
		designDpartextra = form.getTCProperty("s4DesignD");
		prjOfficer = form.getTCProperty("s4Prjofficer");
		changeReson = form.getTCProperty("s4changereson");
		changeWay = form.getTCProperty("s4changeway");
		needDocmen = form.getTCProperty("s4needdocmen");
		demand = form.getTCProperty("s4demand");
		testcase = form.getTCProperty("s4testcase");
		design = form.getTCProperty("s4design");
		remarks = form.getTCProperty("s4remarks");
		

		s4SJGGUI.textPrjName.setText(prjName.getStringValue());
		s4SJGGUI.textPrjNumber.setText(prjNumber.getStringValue());
	//	s4SJGGUI.textDesignDpart.setText(designDpart.getStringValue());
		s4SJGGUI.textDesignDpartextra.setSelectedItem(designDpartextra.getStringValue());
		s4SJGGUI.textDesignDpart.setText(designDpart.getStringValue());
		s4SJGGUI.textPrjOfficer.setText(prjOfficer.getStringValue());

		if(changeReson.getStringValue() == null || changeReson.getStringValue().equals("")){
			s4SJGGUI.textChangeReason.setText(S4SJGGUI.changeResonTips);
		}
		else{
			s4SJGGUI.textChangeReason.setText(changeReson.getStringValue());
		}
		
		if(changeWay.getStringValue() == null || changeWay.getStringValue().equals("")){
			s4SJGGUI.textChangeWay.setText(S4SJGGUI.wordsTips);
		}
		else{
			s4SJGGUI.textChangeWay.setText(changeWay.getStringValue());
		}
		
		if(needDocmen.getStringValue() == null || needDocmen.getStringValue().equals("")){
			s4SJGGUI.textNeedDocmen.setText(S4SJGGUI.wordsTips);
		}
		else{
			s4SJGGUI.textNeedDocmen.setText(needDocmen.getStringValue());
		}
		
		if(demand.getStringValue() == null || demand.getStringValue().equals("")){
			s4SJGGUI.textDemand.setText(S4SJGGUI.wordsTips);
		}
		else{
			s4SJGGUI.textDemand.setText(demand.getStringValue());
		}
		
		if(testcase.getStringValue() == null || testcase.getStringValue().equals("")){
			s4SJGGUI.textTestCase.setText(S4SJGGUI.wordsTips);
		}
		else{
			s4SJGGUI.textTestCase.setText(testcase.getStringValue());
		}
		
		if(design.getStringValue() == null || design.getStringValue().equals("")){
			s4SJGGUI.textDesign.setText(S4SJGGUI.wordsTips);
		}
		else{
			s4SJGGUI.textDesign.setText(design.getStringValue());
		}
		
		if(remarks.getStringValue() == null || remarks.getStringValue().equals("")){
			s4SJGGUI.textRemarks.setText(S4SJGGUI.wordsTips);
		}
		else{
			s4SJGGUI.textRemarks.setText(remarks.getStringValue());
		}
		
		this.setPrjInfo();

	}
	
	@Override
	public void saveForm() {
		// TODO Auto-generated method stub
		
		try {	
			prjName.setStringValueData(s4SJGGUI.textPrjName.getText());
			prjNumber.setStringValueData(s4SJGGUI.textPrjNumber.getText());
		//	designDpart.setStringValueData(s4SJGGUI.textDesignDpart.getText());
			designDpartextra.setStringValueData(s4SJGGUI.textDesignDpartextra.getSelectedItem().toString());
			designDpart.setStringValueData(s4SJGGUI.textDesignDpart.getText());
			prjOfficer.setStringValueData(s4SJGGUI.textPrjOfficer.getText());
			
			changeReson.setStringValueData(s4SJGGUI.textChangeReason.getText());
			changeWay.setStringValueData(s4SJGGUI.textChangeWay.getText());
			needDocmen.setStringValueData(s4SJGGUI.textNeedDocmen.getText());
			demand.setStringValueData(s4SJGGUI.textDemand.getText());
			testcase.setStringValueData(s4SJGGUI.textTestCase.getText());
			design.setStringValueData(s4SJGGUI.textDesign.getText());
			remarks.setStringValueData(s4SJGGUI.textRemarks.getText());
		
			
			
			TCProperty[] tcProperty = new TCProperty[12];
			tcProperty[0] = prjName;
			tcProperty[1] = prjNumber;
			tcProperty[2] = designDpart;
			tcProperty[3] = prjOfficer;
			tcProperty[4] = changeReson;
			tcProperty[5] = changeWay;
			tcProperty[6] = needDocmen;
			tcProperty[7] = demand;
			tcProperty[8] = testcase;
			tcProperty[9] = design;
			tcProperty[10] = remarks;
			
			tcProperty[11] = designDpartextra;
			
			form.setTCProperties(tcProperty);
			
		} catch (TCException e) {
			e.printStackTrace();
		}	
	}
	
	public void setPrjInfo() throws TCException {
		
		AIFComponentContext[] aif = form.getPrimary();
		AIFComponentContext[] aif1 = null;
		AIFComponentContext[] aif2 = null;
		AIFComponentContext[] aif3 = null;
		AIFComponentContext[] aif4= null;

		if (aif != null && aif.length > 0) {
					ir = (TCComponentItemRevision) aif[0].getComponent();
					aif1 = ir.whereReferenced();

					if (aif1 != null && aif1.length > 0) {

						item = (TCComponentItem) aif1[0].getComponent();
						
						
						System.out.println(item.getType().toString());
						System.out.println(item.getType());


						aif2 = item.whereReferenced();
						
						System.out.println("item " + aif1[0]);
						
						if (aif2 != null && aif2.length > 0
								&& aif2[0].getComponent().getType().toString().equals("S4Prj_Folder")) {

							folder1 = (TCComponentFolder) aif2[0].getComponent();

							aif3 = folder1.whereReferenced();
							
							System.out.println("folder1 " + aif2[0]);

							
							if (aif3 != null && aif3.length > 0
									&& aif3[0].getComponent().getType().toString().equals("S4Prj_Folder")) {
								folder2 = (TCComponentFolder) aif3[0].getComponent();
								aif4 = folder2.whereReferenced();
								
								System.out.println("folder2 " + aif3[0]);
								System.out.println("why can't do it");

								
								if (aif4 != null && aif4.length > 0
										&& aif4[0].getComponent().getType().toString().equals("S4YFprojXJ")) {
									fatherItem = (TCComponentItem) aif4[0].getComponent();
									System.out.println("fatherItem " + aif4[0]);
																
																	
									String str_proj_name = fatherItem.getProperty("object_name");
									s4SJGGUI.textPrjName.setText(str_proj_name);
									s4SJGGUI.textPrjName.setEnabled(false);	
									
									String str_proj_id = fatherItem.getProperty("item_id");
									s4SJGGUI.textPrjNumber.setText(str_proj_id);
									s4SJGGUI.textPrjNumber.setEnabled(false);	
								}
							}
						}
					}
		}
	}

	public void initUI(){
		setLayout(new GridLayout(1, 1));
		s4SJGGUI = new S4SJGGUI();
		
		JPanel panel = s4SJGGUI.getJPanel();
		add(panel);		
	}
	  private String[] getTCPreferenceArray(TCSession tcSession, String preferenceName) {
			
			String[] preString = null;
		
			TCPreferenceService tcPreservice = tcSession.getPreferenceService();
			
			preString = tcPreservice.getStringArray(TCPreferenceService.TC_preference_site, preferenceName);

			return preString;
		}
}
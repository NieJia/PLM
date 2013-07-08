package com.gdnz.sac1.form;


import java.awt.Dimension;
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

public class S4HDKJXMJZYB_Form extends AbstractTCForm {
	
	private static final long serialVersionUID = 1L;
	private TCComponentForm form = null;
	private S4HDKJXMJZYB_UI s4HDKJXMJZYB_UI = null;
	
	private TCComponentItem item = null;
	private TCComponentItemRevision ir = null;
	private TCComponentFolder folder1 = null;
	private TCComponentFolder folder2 = null;
	private TCComponentItem fatherItem = null;
	private TCComponentForm master_form = null;
	
	public TCProperty tcPrjName = null;
	public TCProperty tcChargeFirm = null;
	public TCProperty tcPrjNumber = null;
	public TCProperty tcPrjLeader = null;
	public TCProperty tcTelephone = null;
	public TCProperty tcTotalInvest = null;
	public TCProperty tcBudget = null;
	
	public TCProperty tcCurrentGain = null;
	public TCProperty tcExistProblem = null;
	public TCProperty tcNextPlan = null;
	public TCProperty tcDeadline = null;
	
	private TCProperty tcMonthSum = null;
	private TCProperty tcCountYear = null;

	
	public TCProperty tcMonth1 = null;
	private TCProperty tcMonth2 = null;
	private TCProperty tcMonth3 = null;
	private TCProperty tcMonth4 = null;
	

	
	private String[] StrMonthA = null;
	private String[] StrMonthB = null;
	private String[] StrMonthC = null;
	private String[] StrMonthD = null;
	
	private String relation = "IMAN_master_form";


	

	public S4HDKJXMJZYB_Form(TCComponentForm arg0) throws Exception {
		super(arg0);
		// TODO Auto-generated constructor stub
		form = arg0;
		initUI();
		loadForm();	
		
		
	}

	@Override
	public void loadForm() throws TCException {
		// TODO Auto-generated method stub
		
		tcPrjName = form.getTCProperty("s4prjName");
		
		tcPrjNumber = form.getTCProperty("s4prjNumber");
		tcChargeFirm = form.getTCProperty("s4chargeFirm");
		tcPrjLeader = form.getTCProperty("s4prjLeader");
		tcTelephone = form.getTCProperty("s4telephone");
		tcTotalInvest = form.getTCProperty("s4totalInvest");
		tcBudget = form.getTCProperty("s4budget");
		
		tcCurrentGain = form.getTCProperty("s4currentGain");
		tcExistProblem = form.getTCProperty("s4existProblem");
		tcNextPlan = form.getTCProperty("s4nextPlan");
		tcDeadline = form.getTCProperty("s4deadline");
		
		tcMonthSum = form.getTCProperty("s4monthSum");
		tcCountYear = form.getTCProperty("s4countYear");

		tcMonth1 = form.getTCProperty("s4month1");
		tcMonth2  = form.getTCProperty("s4month2");
		tcMonth3  = form.getTCProperty("s4month3");
		tcMonth4  = form.getTCProperty("s4month4");
		
		
		s4HDKJXMJZYB_UI.textPrjName.setText(tcPrjName.getStringValue());
		
	
		s4HDKJXMJZYB_UI.textPrjNumber.setText(tcPrjNumber.getStringValue());
		s4HDKJXMJZYB_UI.textChargeFirm.setText(tcChargeFirm.getStringValue());
		s4HDKJXMJZYB_UI.textPrjLeader.setText(tcPrjLeader.getStringValue());	
		s4HDKJXMJZYB_UI.textTelephone.setText(tcTelephone.getStringValue());
		s4HDKJXMJZYB_UI.textTotalInvest.setText(tcTotalInvest.getStringValue());
		s4HDKJXMJZYB_UI.textBudget.setText(tcBudget.getStringValue());
		
		
		s4HDKJXMJZYB_UI.tableBudget.setValueAt(tcCountYear.getStringValue(), 1, 0);
		s4HDKJXMJZYB_UI.tableBudget.setValueAt(tcMonthSum.getStringValue(), 1, 1);

		
		if(tcCurrentGain.getStringValue() == null || tcCurrentGain.getStringValue().equals("")){
			s4HDKJXMJZYB_UI.textCurrentGain.setText(s4HDKJXMJZYB_UI.wordsTips);
		}
		else{
			s4HDKJXMJZYB_UI.textCurrentGain.setText(tcCurrentGain.getStringValue());
		}
		
		if(tcExistProblem.getStringValue() == null || tcExistProblem.getStringValue().equals("")){
			s4HDKJXMJZYB_UI.textExistProblem.setText(s4HDKJXMJZYB_UI.wordsTips);
		}
		else{
			s4HDKJXMJZYB_UI.textExistProblem.setText(tcExistProblem.getStringValue());
		}
		
		if(tcNextPlan.getStringValue() == null || tcNextPlan.getStringValue().equals("")){
			s4HDKJXMJZYB_UI.textNextPlan.setText(s4HDKJXMJZYB_UI.wordsTips);
		}
		else{
			s4HDKJXMJZYB_UI.textNextPlan.setText(tcNextPlan.getStringValue());
		}
		
		if(tcDeadline.getStringValue() == null || tcDeadline.getStringValue().equals("")){
			s4HDKJXMJZYB_UI.textDeadline.setText("");
		}
		else{
			s4HDKJXMJZYB_UI.textDeadline.setText(tcDeadline.getStringValue());
		}
		
		StrMonthA = tcMonth1.getStringArrayValue();
		for(int i = 0; i < StrMonthA.length; i++){
			s4HDKJXMJZYB_UI.tableBudget.setValueAt(StrMonthA[i], i, 2);
		}
		
		StrMonthB = tcMonth2.getStringArrayValue();
		for(int i = 0; i < StrMonthB.length; i++){
			s4HDKJXMJZYB_UI.tableBudget.setValueAt(StrMonthB[i], i, 3);
		}
		StrMonthC = tcMonth3.getStringArrayValue();
		for(int i = 0; i < StrMonthC.length; i++){
			s4HDKJXMJZYB_UI.tableBudget.setValueAt(StrMonthC[i], i, 4);
		}
		
		StrMonthD = tcMonth4.getStringArrayValue();
		for(int i = 0; i < StrMonthD.length; i++){
			s4HDKJXMJZYB_UI.tableBudget.setValueAt(StrMonthD[i], i, 5);
		}
		
		this.setPrjInfo();

	}
	
	@Override
	public void saveForm() {
		
		try {	
			tcPrjName.setStringValueData(s4HDKJXMJZYB_UI.textPrjName.getText());
			
			tcPrjNumber.setStringValueData(s4HDKJXMJZYB_UI.textPrjNumber.getText());
			tcChargeFirm.setStringValueData(s4HDKJXMJZYB_UI.textChargeFirm.getText());
			tcPrjLeader.setStringValueData(s4HDKJXMJZYB_UI.textPrjLeader.getText());
			tcTelephone.setStringValueData(s4HDKJXMJZYB_UI.textTelephone.getText());
			tcTotalInvest.setStringValueData(s4HDKJXMJZYB_UI.textTotalInvest.getText());
			tcBudget.setStringValueData(s4HDKJXMJZYB_UI.textBudget.getText());
	
			tcCurrentGain.setStringValueData(s4HDKJXMJZYB_UI.textCurrentGain.getText());
			tcExistProblem.setStringValueData(s4HDKJXMJZYB_UI.textExistProblem.getText());
			tcNextPlan.setStringValueData(s4HDKJXMJZYB_UI.textNextPlan.getText());
			tcDeadline.setStringValueData(s4HDKJXMJZYB_UI.textDeadline.getText());
			

			if(s4HDKJXMJZYB_UI.tableBudget.getCellEditor()!=null){
				s4HDKJXMJZYB_UI.tableBudget.getCellEditor().stopCellEditing();
			}
			
			StrMonthA = new String[s4HDKJXMJZYB_UI.tableBudget.getRowCount()];
			StrMonthB = new String[s4HDKJXMJZYB_UI.tableBudget.getRowCount()];
			StrMonthC = new String[s4HDKJXMJZYB_UI.tableBudget.getRowCount()];
			StrMonthD = new String[s4HDKJXMJZYB_UI.tableBudget.getRowCount()];

			
			for(int i = 0; i < s4HDKJXMJZYB_UI.tableBudget.getRowCount(); i++){
				if((String) s4HDKJXMJZYB_UI.tableBudget.getValueAt(i, 2) == null){
					if(i == 0){
						StrMonthA[i] = "  （）月";
					}
					else{
						StrMonthA[i] = "";
					}
				}
				else{
					StrMonthA[i] = (String) s4HDKJXMJZYB_UI.tableBudget.getValueAt(i, 2);
				}
				if((String) s4HDKJXMJZYB_UI.tableBudget.getValueAt(i, 3) == null){
					if(i == 0){
						StrMonthB[i] = "  （）月";
					}
					else{
						StrMonthB[i] = "";
					}
				}
				else{
					StrMonthB[i] = (String) s4HDKJXMJZYB_UI.tableBudget.getValueAt(i, 3);
				}
				if((String) s4HDKJXMJZYB_UI.tableBudget.getValueAt(i, 4) == null){
					if(i == 0){
						StrMonthC[i] = "  （）月";
					}
					else{
						StrMonthC[i] = "";
					}
				}
				else{
					StrMonthC[i] = (String) s4HDKJXMJZYB_UI.tableBudget.getValueAt(i, 4);
				}
				if((String) s4HDKJXMJZYB_UI.tableBudget.getValueAt(i, 5) == null){
					if(i == 0){
						StrMonthD[i] = "  （）月";
					}
					else{
						StrMonthD[i] = "";
					}
				}
				else{
					StrMonthD[i] = (String) s4HDKJXMJZYB_UI.tableBudget.getValueAt(i, 5);
				}
			}
			
			tcCountYear.setStringValue((String) s4HDKJXMJZYB_UI.tableBudget.getValueAt(1, 0));
			tcMonthSum.setStringValue((String) s4HDKJXMJZYB_UI.tableBudget.getValueAt(1, 1));
			
			tcMonth1.setStringValueArray(StrMonthA);
			tcMonth2.setStringValueArray(StrMonthB);
			tcMonth3.setStringValueArray(StrMonthC);
			tcMonth4.setStringValueArray(StrMonthD);


				
			TCProperty[] tcProperty = new TCProperty[17];
			tcProperty[0] = tcPrjName;
		
			tcProperty[1] = tcChargeFirm;
			tcProperty[2] = tcPrjNumber;
			tcProperty[3] = tcPrjLeader;
			tcProperty[4] = tcTelephone;
			tcProperty[5] = tcTotalInvest;
			tcProperty[6] = tcBudget;
			tcProperty[7] = tcMonthSum;
			tcProperty[8] = tcCountYear;
			
			tcProperty[9] = tcCurrentGain;
			tcProperty[10] = tcExistProblem;
			tcProperty[11] = tcNextPlan;
			tcProperty[12] = tcDeadline;
			
			tcProperty[13] = tcMonth1;
			tcProperty[14] = tcMonth2;
			tcProperty[15] = tcMonth3;
			tcProperty[16] = tcMonth4;
			
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
						


						
						if (aif2 != null && aif2.length > 0 &&
								aif2[0].getComponent().getType().toString().equals("S4Prj_Folder")) {

							folder1 = (TCComponentFolder) aif2[0].getComponent();

							aif3 = folder1.whereReferenced();
							
							System.out.println("folder1 " + aif2[0]);

							
							if (aif3 != null && aif3.length > 0
									&& aif3[0].getComponent().getType().toString().equals("S4Prj_Folder")) {
								folder2 = (TCComponentFolder) aif3[0].getComponent();
								aif4 = folder2.whereReferenced();
								
								System.out.println("folder2 " + aif3[0]);
								
								if (aif4 != null && aif4.length > 0
										&& aif4[0].getComponent().getType().toString().equals("S4YFprojXJ")) {
									fatherItem = (TCComponentItem) aif4[0].getComponent();
									System.out.println("fatherItem " + aif4[0]);
									
									
									
								
											

									master_form = 
										(TCComponentForm) fatherItem.getRelatedComponent(relation);
									
									
									System.out.println("why can't do it");
									
									String str_proj_firm = master_form.getProperty("s4tandd");
									s4HDKJXMJZYB_UI.textChargeFirm.setText(str_proj_firm);
									String str_proj_invest = master_form.getProperty("s4prj_cost");
									s4HDKJXMJZYB_UI.textTotalInvest.setText(str_proj_invest);
									s4HDKJXMJZYB_UI.textTotalInvest.setEnabled(false);
									s4HDKJXMJZYB_UI.textChargeFirm.setEnabled(false);
									
									String str_proj_name = fatherItem.getProperty("object_name");
									s4HDKJXMJZYB_UI.textPrjName.setText(str_proj_name);
									s4HDKJXMJZYB_UI.textPrjName.setEnabled(false);	
									
								}
							}
						}
					}
		}
	}
	
	public void initUI(){
		setLayout(new GridLayout(1, 1));
		s4HDKJXMJZYB_UI = new S4HDKJXMJZYB_UI();
		
		JPanel panel = s4HDKJXMJZYB_UI.getJPanel();
		add(panel);
	}
}
package com.gdnz.sac1.form;

import java.awt.GridLayout;

import javax.swing.JTabbedPane;

import com.teamcenter.rac.form.AbstractTCForm;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCProperty;

public class S4XMSSJDUserForm extends AbstractTCForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TCComponentForm form = null;
	private S4XMSSJDUI s4XMSSJDUI = null;
	
	private TCProperty schedule = null;//进度
	private TCProperty plan = null;//计划
	private TCProperty execution = null;//执行说明
	private TCProperty change = null;//变更
	private TCProperty schcontrol = null;//进度监控偏差值
	private TCProperty proCompletion = null;//项目完成率
	private TCProperty cNumber = null;//变更次数
	private TCProperty actCosts = null;//实际发生
	
	private String[] strPlan = null;
	private String[] strExecution = null;
	private String[] strChange = null;

	
	
	public S4XMSSJDUserForm(TCComponentForm arg0) throws Exception {
		super(arg0);
		//System.out.println("jiejiejiejiejie");
		System.out.println("***********begin0***********");
		form = arg0;
		initUI();
		System.out.println("***********begin1***********");
		loadForm();
	}

	@Override
	public void loadForm() throws TCException {
		// TODO Auto-generated method stub
		schedule=form.getTCProperty("s4schedule");
		plan=form.getTCProperty("s4plan");
		execution=form.getTCProperty("s4execution");
		change=form.getTCProperty("s4change");
		schcontrol=form.getTCProperty("s4schcontrol");
		proCompletion=form.getTCProperty("s4Procompletion");
		cNumber=form.getTCProperty("s4Cnumber");
		actCosts=form.getTCProperty("s4Actcosts");
		
		
		s4XMSSJDUI.textrepSchedule.setText(schedule.toString());
		s4XMSSJDUI.textSchControl.setSelectedItem(schcontrol.toString());
	//	s4XMSSJDUI.textSchControl.setText(schcontrol.toString());
		s4XMSSJDUI.textProCompletion.setText(proCompletion.toString());
		s4XMSSJDUI.textActCosts.setText(actCosts.toString());
		s4XMSSJDUI.textCNumber.setText(cNumber.toString());
		
		strPlan=plan.getStringArrayValue();
	//	System.out.println("********1**********"+strPlan.length);
		for(int i=0;i<strPlan.length;i++){
			s4XMSSJDUI.tableZongJie.setValueAt(strPlan[i], 0, i);
		}
		strExecution=execution.getStringArrayValue();
	//	System.out.println("********2**********"+strExecution.length);
		for(int i=0;i<strExecution.length;i++){
			s4XMSSJDUI.tableZongJie.setValueAt(strExecution[i], 1, i);
		}
		strChange=change.getStringArrayValue();
	//	System.out.println("********3**********"+strChange.length);
		for(int i=0;i<strChange.length;i++){
			s4XMSSJDUI.tableZongJie.setValueAt(strChange[i], 2, i);
		}
		
		

	}

	@Override
	public void saveForm() {
		// TODO Auto-generated method stub
		try {
		schedule.setStringValueData(s4XMSSJDUI.textrepSchedule.getText());
		schcontrol.setStringValueData(s4XMSSJDUI.textSchControl.getSelectedItem().toString());
	//	schcontrol.setStringValue(s4XMSSJDUI.textSchControl.getText());
		proCompletion.setStringValueData(s4XMSSJDUI.textProCompletion.getText());
		cNumber.setStringValueData(s4XMSSJDUI.textCNumber.getText());
		actCosts.setStringValueData(s4XMSSJDUI.textActCosts.getText());
		
		if(s4XMSSJDUI.tableZongJie.getCellEditor()!=null){
			s4XMSSJDUI.tableZongJie.getCellEditor().stopCellEditing();
		}
		strPlan = new String[s4XMSSJDUI.tableZongJie.getColumnCount()];
		for(int i = 0; i < s4XMSSJDUI.tableZongJie.getColumnCount(); i++){
			if((String) s4XMSSJDUI.tableZongJie.getValueAt(0, i) == null){
				strPlan[i] = "";
			}
			else{
				strPlan[i] = (String) s4XMSSJDUI.tableZongJie.getValueAt(0, i);
			}

		}
		plan.setStringValueArray(strPlan);
		
		
		strExecution = new String[s4XMSSJDUI.tableZongJie.getColumnCount()];
		for(int i = 0; i < s4XMSSJDUI.tableZongJie.getColumnCount(); i++){
			if((String) s4XMSSJDUI.tableZongJie.getValueAt(1, i) == null){
				strExecution[i] = "";
			}
			else{
				strExecution[i] = (String) s4XMSSJDUI.tableZongJie.getValueAt(1, i);
			}

		}
		execution.setStringValueArray(strExecution);
		
		
		strChange = new String[s4XMSSJDUI.tableZongJie.getColumnCount()];
		for(int i = 0; i < s4XMSSJDUI.tableZongJie.getColumnCount(); i++){
			if((String) s4XMSSJDUI.tableZongJie.getValueAt(2, i) == null){
				strChange[i] = "";
			}
			else{
				strChange[i] = (String) s4XMSSJDUI.tableZongJie.getValueAt(2, i);
			}

		}
		change.setStringValueArray(strChange);
	
		
		
		TCProperty[] tcProperty = new TCProperty[8];
		tcProperty[0] = schedule;
		tcProperty[1] = plan;
		tcProperty[2] = execution;
		tcProperty[3] = change;
		tcProperty[4] = schcontrol;
		tcProperty[5] = proCompletion;
		tcProperty[6] = cNumber;
		tcProperty[7] = actCosts;
		
		form.setTCProperties(tcProperty);
		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public void initUI(){
		setLayout(new GridLayout(1, 1));
		s4XMSSJDUI = new S4XMSSJDUI();
		JTabbedPane jTabbedPane = s4XMSSJDUI.getJTabbedPane();
		add(jTabbedPane);
		jTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

}

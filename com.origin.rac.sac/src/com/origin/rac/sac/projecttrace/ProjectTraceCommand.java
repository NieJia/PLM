package com.origin.rac.sac.projecttrace;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cn.com.origin.util.ProgressBarThread;

import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.common.Activator;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentDatasetType;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentTcFile;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCProperty;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCTextService;
import com.teamcenter.rac.util.MessageBox;

public class ProjectTraceCommand extends AbstractAIFCommand {

	private TCSession session = null;

	private String prj_type="S4YFprojXJ";
	private TCComponentForm target_form;
	private String fileName = "";
	
	private String[] StrPlan = null;
	private String[] StrChangePlan = null;
	private String[] StrExcuteState = null;
	private TCProperty tcChangePlan = null;
	private TCProperty tcExcuteState = null;
	private TCProperty tcPlan = null;
	private TCComponentItem fatherItem = null;
	private ProgressBarThread progressBarThread = null;
	private String  exportPath = null;
	private TCComponent target = null;
	private InterfaceAIFComponent[] targets = null;
	private PrjTraceInfo traceInfo = null;
	private JTextField jf;
    private String jfText;
	private String relation = "IMAN_master_form";
	private TCComponentForm master_form = null;


    
    


	private File fmsFile = null;


	public ProjectTraceCommand(AbstractAIFUIApplication app) throws TCException, IOException {
		
		
		targets = app.getTargetComponents();
		session = (TCSession) app.getSession();
		

		
		for (InterfaceAIFComponent AIFtarget : targets) {
			
			target = (TCComponent) AIFtarget;


			
			if (target == null) {
				MessageBox.post("该操作仅限于 研发项目 组件", "提示", MessageBox.INFORMATION);
				return;
			}
			
				
			if (!((target instanceof TCComponentItem) && 
					((TCComponentItem) target).getType().equals("S4YFprojXJ"))) {
				MessageBox.post("该操作仅限于 研发项目 组件", "提示", MessageBox.INFORMATION);
				return;
			}
		}
		
		fatherItem = (TCComponentItem) target;
		master_form = 
			(TCComponentForm) fatherItem.getRelatedComponent(relation);
		
		InterfaceAIFComponent[] dataSet = queryDateset("项目跟踪汇总表");
		
		if (dataSet.length > 0) {
			System.out.println("find the temple");
			
			TCComponentDataset ds = (TCComponentDataset) dataSet[0];

			if (dataSet != null) {
				TCComponentTcFile[] files = ds.getTcFiles();
				if (files != null) {
					if (files.length == 1) {
						fmsFile = files[0].getFmsFile();
					} else if (files.length == 0) {
						MessageBox.post("数据集模板没有引用文件，请检查！", "提示", 1);
						return;
					} else {
						MessageBox.post("数据集模板的引用文件过多，请检查！", "提示", 1);
						return;
					}
				} else {
					MessageBox.post("数据集模板没有引用文件，请检查！", "提示", 1);
					return;
				}
			}

		}
		
		else {
			System.out.println("can't find the temple");
			return;

		}
		
		JFileChooser fc = new JFileChooser(); 
	    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    
	    fc.setCurrentDirectory(new File("C:/Users/Administrator/Documents"));
        jf=getTextField(fc);
        jf.setText(master_form.getProperty("s4tandd")+"项目跟踪汇总表");
        jfText=jf.getText();
        jf.addFocusListener(new FocusListener(){

			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
           	 jfText=jf.getText();

			}
       	
       	 
        });
        fc.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				jf.setText(jfText);	

				
			}
        });
        
	    int intRetVal = fc.showSaveDialog(null); 
	    if( intRetVal == JFileChooser.APPROVE_OPTION){ 
	    	  System.out.println("A");
	    	  exportPath = fc.getSelectedFile().getPath();
	    	
	    	  File file = new File(exportPath+".xls");
	    	  if (!file.exists()) {
	    		  traceExport(exportPath+".xls");
	    	  }
	    	  
	    	  else {
	    		  int n = JOptionPane.showConfirmDialog(null, "文件已存在，确认覆盖吗？",
	    				  "提示", JOptionPane.YES_NO_OPTION);
	    		  if (n == JOptionPane.YES_OPTION) {
		    		  traceExport(exportPath+".xls");
	    		  } 
	    	 }
	    } 
		
	
		
	}
	
	private void traceExport(String path) throws IOException, TCException {
		
	File xlsFile = new File(path);
		
		System.out.println(fmsFile.getPath().toString());
		
		FileInputStream in = new FileInputStream(fmsFile);
		FileOutputStream out = new FileOutputStream(xlsFile);

		byte[] buffer = new byte[1024];
		int len = -1;
		
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		
		in.close();
		out.close();
		
		
		
		progressBarThread = new ProgressBarThread("提示", "正在汇总项目进度，请稍后...");
		progressBarThread.start();
		
		for (InterfaceAIFComponent AIFtarget : targets) {
			
			TCComponent target = (TCComponent) AIFtarget;
			
			
			//System.out.println("trace handle");
			
			TCComponentItem fatherItem = (TCComponentItem) target;

			
			TCComponentForm trace_form = 
				(TCComponentForm) fatherItem.getRelatedComponent("IMAN_reference");
			
			TCComponentForm master_form = 
				(TCComponentForm) fatherItem.getRelatedComponent("IMAN_master_form");
			
			System.out.println(trace_form.getType());
			System.out.println(master_form.getType());
			
			traceInfo = new PrjTraceInfo();
			
			traceInfo.setPrjId(fatherItem.getProperty("item_id"));
			traceInfo.setPrjName(fatherItem.getProperty("object_name"));
			traceInfo.setProcess(trace_form.getProperty("s4schedule"));
			traceInfo.setChangeTimes(trace_form.getProperty("s4Cnumber"));
			traceInfo.setProcessBias(trace_form.getProperty("s4schcontrol"));
			traceInfo.setFinishingRate(trace_form.getProperty("s4Procompletion"));
			traceInfo.setDeadline(master_form.getProperty("s4timedup"));
			traceInfo.setTotalCost(master_form.getProperty("s4prj_cost"));
			traceInfo.setRealCost(trace_form.getProperty("s4Actcosts"));
			traceInfo.setEndFormat(master_form.getProperty("s4Projtype"));
			traceInfo.setLeader1(master_form.getProperty("s4name1"));
			traceInfo.setLeader2(master_form.getProperty("s4name2"));
			traceInfo.setLeader3(master_form.getProperty("s4name3"));
			traceInfo.setChargeFirm(master_form.getProperty("s4tandd"));


			
			tcPlan = trace_form.getTCProperty("s4plan");
			StrPlan = tcPlan.getStringArrayValue();
			traceInfo.setPlan(StrPlan);
			
			tcChangePlan = trace_form.getTCProperty("s4execution");
			StrChangePlan = tcChangePlan.getStringArrayValue();
			traceInfo.setChangePlan(StrChangePlan);
			
			tcExcuteState = trace_form.getTCProperty("s4change");
			StrExcuteState = tcExcuteState.getStringArrayValue();
			traceInfo.setExcuteState(StrExcuteState);
			
					
			ExportExcel excel = new ExportExcel(traceInfo, path);
			
		
					
		}
		
		progressBarThread.stopBar();
		MessageBox.post("项目进度汇总完毕", "提示", MessageBox.INFORMATION);
		
	}
	
	
	
	public InterfaceAIFComponent[] queryDateset(String name){
		InterfaceAIFComponent[] items = null;
		try {
			TCTextService tcService =session.getTextService();
			String askKey[]={tcService.getTextValue("Name"),tcService.getTextValue("OwningUser")};
			String askValue[]={name,"infodba"};
			items =  session.search("数据集...", askKey, askValue);
		} catch (TCException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
	
	public JTextField getTextField(Container c){
		JTextField textField =null;
		for(int i=0;i<c.getComponentCount();i++){
			Component cnt=c.getComponent(i);
				if(cnt instanceof JTextField){
					return (JTextField)cnt;
				}
				if(cnt instanceof Container){
					textField=getTextField((Container)cnt);
					if(textField !=null)
						return textField;
				}
			}			
		return textField;
	}
	
	
}

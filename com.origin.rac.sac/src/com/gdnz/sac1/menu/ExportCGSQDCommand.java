package com.gdnz.sac1.menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import cn.com.origin.util.ProgressBarThread;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.commands.namedreferences.ImportFilesOperation;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentItemType;
import com.teamcenter.rac.kernel.TCComponentTcFile;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCTextService;
import com.teamcenter.rac.util.MessageBox;

public class ExportCGSQDCommand extends AbstractAIFCommand {
	
	private TCSession session = null;

	private String fileName = "";
	
	private TCComponentItem fatherItem = null;
	private ProgressBarThread progressBarThread = null;
	private TCComponent target = null;
	private InterfaceAIFComponent[] targets = null;
	private CGSQDInfo cgSQDInfo_row = null;

	private File fmsFile = null;
	private File fmsFileMuBan = null;
	private Vector<CGSQDInfo> cGSQDInfo = new Vector<CGSQDInfo>();
	private String bianhao;
	
 public ExportCGSQDCommand(AbstractAIFApplication application) throws TCException, IOException {
		System.out.println("********ExportCGSQDCommand***************");
		targets = application.getTargetComponents();
	//	session = (TCSession) application.getSession();

		
		for (InterfaceAIFComponent AIFtarget : targets) {
			
			target = (TCComponent) AIFtarget;
			if (target == null) {
				MessageBox.post("该操作仅限于 工程项目 组件", "提示", MessageBox.INFORMATION);
				return;
			}
				
			if (!((target instanceof TCComponentItem) && checkType(target))) {
				MessageBox.post("该操作仅限于 工程项目组件", "提示", MessageBox.INFORMATION);
				return;
			}
		}
	  new CGSQDBianHaoDialog(application);
 };

 public boolean  checkType(TCComponent target){
 	String type=((TCComponentItem) target).getType();
 	if(type.equals("S4TW")||
 			type.equals("S4ELECC")||
 			type.equals("S4DEC")||
 			type.equals("S4ROXE")||
 			type.equals("S4ECOMP")||
 			type.equals("S4NECOMP")||
 			type.equals("S4SSCMM")||
 			type.equals("S4SSCOM")||
 			type.equals("S4PLG")||
 			type.equals("S4MP")||
 			type.equals("S4COST")||
 			type.equals("S4OCOST")||
 			type.equals("S4DQDM")||
 			type.equals("S4RKDM")||
 			type.equals("S4OPC"))
 		return true;
 	else return false;
 }
}

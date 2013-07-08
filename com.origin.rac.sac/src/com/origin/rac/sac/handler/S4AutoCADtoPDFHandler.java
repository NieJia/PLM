package com.origin.rac.sac.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.origin.rac.sac.autocadtopdf.S4AutoCADtoPDFAction;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.util.MessageBox;

public class S4AutoCADtoPDFHandler extends AbstractHandler {

	private boolean flage = false;

	
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		
		flage = false;
		AbstractAIFUIApplication app = AIFUtility.getCurrentApplication();	
		InterfaceAIFComponent[] targets = app.getTargetComponents();

		if(targets.length > 0){
			for (int i = 0; i < targets.length; i++) {
				if(!(targets[i] instanceof TCComponentItem) && !(targets[i] instanceof TCComponentFolder)){
					flage = true;
				}
			}
			if(flage){
				MessageBox.post("请选择文件夹或者item类型对象后重试！", "提示", MessageBox.WARNING);
				return null;
			}
			S4AutoCADtoPDFAction s4AutoCADtoPDFAction = new S4AutoCADtoPDFAction(app, null);
			new Thread(s4AutoCADtoPDFAction).start();
		}
		else{
			MessageBox.post("请选择文件夹或者item类型对象后重试！", "提示", MessageBox.WARNING);
			return null;
		}
		return null;
	}
}

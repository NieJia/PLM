package com.origin.rac.sac.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.origin.rac.sac.gxhselect.S4GXHSelectAction;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.util.MessageBox;

public class S4GXHSelectHandler  extends AbstractHandler{

	private AbstractAIFUIApplication app = null;
	
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		app = AIFUtility.getCurrentApplication();	

		InterfaceAIFComponent[] targets = app.getTargetComponents();
		if(targets.length > 0){
			S4GXHSelectAction s4GXHSelectAction = new S4GXHSelectAction(app, targets, null);
			new Thread(s4GXHSelectAction).start();
		}
		else{
			MessageBox.post("请右键点击要添加工序号的bomline！","提示",MessageBox.WARNING);
			return null;
		}
		
		return null;
	}

}

package com.origin.rac.sac.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.origin.rac.sac.gylx.S4GYLXCheckingAction;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.util.MessageBox;

public class S4GYLXCheckingHandler  extends AbstractHandler{

	private AbstractAIFUIApplication app = null;
	
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		app = AIFUtility.getCurrentApplication();	
		/*InterfaceAIFComponent[] targets = app.getTargetComponents();
		if(targets.length == 1){
			InterfaceAIFComponent target = targets[0];
			S4GYLXCheckingAction s4GYLXCheckingAction = new S4GYLXCheckingAction(app, target, null);
			new Thread(s4GYLXCheckingAction).start();
		}
		else{
			MessageBox.post("bomline对象！","提示",MessageBox.WARNING);
			return null;
		}*/
		
		S4GYLXCheckingAction s4GYLXCheckingAction = new S4GYLXCheckingAction(app, null);
		new Thread(s4GYLXCheckingAction).start();
		return null;
	}

}

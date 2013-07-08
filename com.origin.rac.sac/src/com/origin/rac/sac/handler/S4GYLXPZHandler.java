package com.origin.rac.sac.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.origin.rac.sac.gylx.S4GYLXPZAction;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.util.MessageBox;

public class S4GYLXPZHandler extends AbstractHandler{

	private AbstractAIFUIApplication app = null;
	private String item_type = "S4CP";

	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		app = AIFUtility.getCurrentApplication();	
		InterfaceAIFComponent[] targets = app.getTargetComponents();
		if(targets.length == 1 && targets[0] instanceof TCComponentItem){
			TCComponentItem target = (TCComponentItem) targets[0];
			if(!item_type.equals(target.getType())){
				MessageBox.post("请选择订单行物料对象!","提示",MessageBox.WARNING);
				return null;
			}else{
				S4GYLXPZAction s4GYLXPZAction = new S4GYLXPZAction(app, target, null);
				new Thread(s4GYLXPZAction).start();
			}
		}
		else{
			MessageBox.post("请选择订单行物料对象!","提示",MessageBox.WARNING);
			return null;
		}
		return null;
	}
}

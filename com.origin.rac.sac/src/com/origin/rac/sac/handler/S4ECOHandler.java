package com.origin.rac.sac.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.origin.rac.sac.eco.S4ECOAction;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.util.MessageBox;

public class S4ECOHandler extends AbstractHandler{

	private AbstractAIFUIApplication app = null;

	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		app = AIFUtility.getCurrentApplication();	
		InterfaceAIFComponent[] targets = app.getTargetComponents();

		if(targets.length == 1){
			InterfaceAIFComponent target = targets[0];
			if(target instanceof TCComponentItemRevision){
				TCComponentItemRevision tccomponentItemRevision =(TCComponentItemRevision)target;
				String itemRevType = tccomponentItemRevision.getType();
				//.getProperty("object_type");
				if(itemRevType.equals("S4ECOrderRevision") 
						|| itemRevType.equals("S4PGTZDRevision")){
					S4ECOAction s4ECOAction = new S4ECOAction(app, tccomponentItemRevision, null);
					new Thread(s4ECOAction).start();
				}
				else{
					MessageBox.post("请选中更改单的版本！","提示",MessageBox.WARNING);
					return null;
				}
			}
			else{
				MessageBox.post("请选中更改单的版本！","提示",MessageBox.WARNING);
				return null;
			}
		}
		else{
			MessageBox.post("请选中更改单的版本！","提示",MessageBox.WARNING);
			return null;
		}
		return null;
	}
}

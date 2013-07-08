package com.origin.rac.sac.handler;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.gdnz.sac1.form.S4ExportWordAction;
import com.origin.rac.sac.eco.S4ECOToErpAction;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.util.MessageBox;

public class S4ExportWordHandler extends AbstractHandler{

	private AbstractAIFUIApplication app = null;
	
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		
		app = AIFUtility.getCurrentApplication();	
		InterfaceAIFComponent target = app.getTargetComponent();

			if(target instanceof TCComponentForm){
				TCComponentForm tccomponentForm =(TCComponentForm)target;
				String formType = tccomponentForm.getType();
				//getProperty("object_type");
				if(formType.equals("S4HDKJXMJZYBRevisionMaster")||formType.equals("S4SJGGRevisionMaster")||formType.equals("S4YFprojXJMaster")){
					S4ExportWordAction s4ExportWordAction = new S4ExportWordAction(app,null);
					new Thread(s4ExportWordAction).start();
				}
				else{
					MessageBox.post("请选中要导出Word的表单！","提示",MessageBox.WARNING);
					return null;
				}
			}
			else{
				MessageBox.post("请选中要导出Word的表单！","提示",MessageBox.WARNING);
				return null;
			}

		
		return null;
	}
	
}

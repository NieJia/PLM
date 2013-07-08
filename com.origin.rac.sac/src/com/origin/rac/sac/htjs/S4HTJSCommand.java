package com.origin.rac.sac.htjs;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.kernel.TCComponentFolder;

public class S4HTJSCommand extends AbstractAIFCommand {

	private AbstractAIFApplication app = null;
	private TCComponentFolder targetfolder = null;
	
	public S4HTJSCommand(AbstractAIFApplication app,TCComponentFolder targetfolder)
	{
		this.app = app;
		this.targetfolder = targetfolder;
	}
	
	@Override
	public void executeModal() throws Exception {
		S4HTJSOperation operation = new S4HTJSOperation(app,targetfolder);
		operation.executeOperation();
		/*S4HTJSDialog dlg = new S4HTJSDialog(app,targetfolder);
		if(dlg!=null)
			setRunnable(dlg);*/
	}
	
}

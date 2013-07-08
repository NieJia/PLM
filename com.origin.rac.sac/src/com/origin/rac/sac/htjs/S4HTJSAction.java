package com.origin.rac.sac.htjs;

import java.awt.Frame;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;
import com.teamcenter.rac.kernel.TCComponentFolder;

public class S4HTJSAction extends AbstractAIFAction {

	private AbstractAIFApplication app = null;
	private TCComponentFolder targetfolder = null;
	
	public S4HTJSAction(AbstractAIFApplication app, Frame arg1, String arg2,TCComponentFolder targetfolder) {
		super(app, arg1, arg2);
		this.app = app;
		this.targetfolder = targetfolder;
	}
	
	
	@Override
	public void run() {
		S4HTJSCommand command = new S4HTJSCommand(app,targetfolder);
		try {
			command.executeModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

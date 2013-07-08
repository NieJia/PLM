package com.origin.rac.sac.eco;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;
import com.teamcenter.rac.kernel.TCComponentItemRevision;

public class S4ECOCheckingAction extends AbstractAIFAction{

	private AbstractAIFUIApplication app = null;
	private TCComponentItemRevision target = null;
	
	public S4ECOCheckingAction(AbstractAIFUIApplication arg0, TCComponentItemRevision target, String arg1) {
		super(arg0, arg1);
		this.app = arg0;
		this.target = target;
	}
	
	public void run() {
		S4ECOCheckingCommand s4ECOCheckingCommand = new S4ECOCheckingCommand(app, target);
		try {
			s4ECOCheckingCommand.executeModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

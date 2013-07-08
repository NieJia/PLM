package com.origin.rac.sac.eco;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;
import com.teamcenter.rac.kernel.TCComponentItemRevision;

public class S4ECOToErpAction extends AbstractAIFAction{

	private AbstractAIFUIApplication app = null;
	private TCComponentItemRevision target = null;

	public S4ECOToErpAction(AbstractAIFUIApplication arg0, TCComponentItemRevision target, String arg1) {
		super(arg0, arg1);
		this.app = arg0;
		this.target = target;
	}

	public void run() {
		S4ECOToErpCommand s4ECOToErpCommand = new S4ECOToErpCommand(app, target);
		try {
			s4ECOToErpCommand.executeModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

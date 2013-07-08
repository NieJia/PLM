package com.origin.rac.sac.sendbominfo;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;

public class CheckYXBomInfoAction extends AbstractAIFAction {

	private AbstractAIFUIApplication application = null;

	public CheckYXBomInfoAction(AbstractAIFUIApplication arg0, String arg1) {
		super(arg0, arg1);
		application = arg0;
	}

	public void run() {
		new CheckYXBomInfoCommand(application);
	}

}

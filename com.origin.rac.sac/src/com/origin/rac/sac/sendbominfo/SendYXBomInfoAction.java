package com.origin.rac.sac.sendbominfo;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;

public class SendYXBomInfoAction extends AbstractAIFAction {

	private AbstractAIFUIApplication application = null;

	public SendYXBomInfoAction(AbstractAIFUIApplication arg0, String arg1) {
		super(arg0, arg1);
		application = arg0;
	}

	public void run() {
		new SendYXBomInfoCommand(application);
		
	}

}

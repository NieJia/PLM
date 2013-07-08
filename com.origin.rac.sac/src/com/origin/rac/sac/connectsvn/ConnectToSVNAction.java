package com.origin.rac.sac.connectsvn;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;

public class ConnectToSVNAction extends AbstractAIFAction {

	private AbstractAIFUIApplication application = null;

	public ConnectToSVNAction(AbstractAIFUIApplication arg0, String arg1) {
		super(arg0, arg1);
		application = arg0;
	}

	public void run() {
		new ConnectToSVNCommand(application);
		
	}

}

package com.origin.rac.sac.dditemcreate;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;

public class DDItemCreateAction extends AbstractAIFAction {

	private AbstractAIFUIApplication application = null;

	public DDItemCreateAction(AbstractAIFUIApplication arg0, String arg1) {
		super(arg0, arg1);
		application = arg0;
	}

	public void run() {
		new DDItemCreateCommand(application);
		
	}

}

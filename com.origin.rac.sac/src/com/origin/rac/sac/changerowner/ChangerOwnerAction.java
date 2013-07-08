package com.origin.rac.sac.changerowner;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;

public class ChangerOwnerAction extends AbstractAIFAction {

	private AbstractAIFUIApplication application = null;

	public ChangerOwnerAction(AbstractAIFUIApplication arg0, String arg1) {
		super(arg0, arg1);
		application = arg0;
	}

	public void run() {
		new ChangerOwnerCommand(application);
	}

}

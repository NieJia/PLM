package com.origin.rac.sac.setprojectid;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;

public class SetProjectIdAction extends AbstractAIFAction {

	private AbstractAIFUIApplication application = null;

	public SetProjectIdAction(AbstractAIFUIApplication arg0, String arg1) {
		super(arg0, arg1);
		application = arg0;
	}

	public void run() {
		new SetProjectIdCommand(application);
		
	}

}

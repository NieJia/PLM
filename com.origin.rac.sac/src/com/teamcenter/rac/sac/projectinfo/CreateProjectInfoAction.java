package com.teamcenter.rac.sac.projectinfo;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;

public class CreateProjectInfoAction extends AbstractAIFAction {

	private AbstractAIFUIApplication application = null;

	public CreateProjectInfoAction(AbstractAIFUIApplication arg0, String arg1) {
		super(arg0, arg1);
		application = arg0;
	}

	public void run() {
		new CreateProjectInfoCommand(application);
	}

}

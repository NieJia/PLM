package com.origin.rac.sac.projectmember;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;

public class ProjectMemberAction extends AbstractAIFAction {

	private AbstractAIFUIApplication application = null;

	public ProjectMemberAction(AbstractAIFUIApplication arg0, String arg1) {
		super(arg0, arg1);
		application = arg0;
	}

	public void run() {
		new ProjectMemberCommand(application);
		
	}

}

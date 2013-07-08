package com.origin.rac.sac.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.origin.rac.sac.projectmember.ProjectMemberAction;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;

public class ProjectMemberHandler extends AbstractHandler {

	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
		ProjectMemberAction action = new ProjectMemberAction(application, null);
		new Thread(action).start();
		return null;
	}

}

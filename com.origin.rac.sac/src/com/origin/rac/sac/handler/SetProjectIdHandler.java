package com.origin.rac.sac.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.origin.rac.sac.setprojectid.SetProjectIdAction;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;

public class SetProjectIdHandler extends AbstractHandler {

	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
		SetProjectIdAction action = new SetProjectIdAction(application, null);
		new Thread(action).start();
		return null;
	}

}

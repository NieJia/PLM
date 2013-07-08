package com.origin.rac.sac.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.origin.rac.sac.dditemcreate.DDItemCreateAction;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;

public class DDItemCreateHandler extends AbstractHandler {

	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
		DDItemCreateAction action = new DDItemCreateAction(application, null);
		new Thread(action).start();
		return null;
	}

}

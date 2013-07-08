package com.origin.rac.sac.projecttrace;

import java.io.IOException;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;
import com.teamcenter.rac.kernel.TCException;

public class ProjectTraceAction extends AbstractAIFAction {

	private AbstractAIFUIApplication application = null;

	public ProjectTraceAction(AbstractAIFUIApplication arg0, String arg1) {
		super(arg0, arg1);
		application = arg0;
	}

	public void run() {
		try {
			new ProjectTraceCommand(application);
		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

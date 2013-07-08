package com.origin.rac.sac.createdateset;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;

public class SJGGDatesetCreateAction extends AbstractAIFAction {

	private AbstractAIFUIApplication application = null;

	public SJGGDatesetCreateAction(AbstractAIFUIApplication arg0, String arg1) {
		super(arg0, arg1);
		application = arg0;
	}

	public void run() {
		new SJGGDatesetCreateCommand(application);
		
	}

}

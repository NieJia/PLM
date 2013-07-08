package com.origin.rac.sac.itemchangesendinfo;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;

public class ItemChangeCheckInfoAction extends AbstractAIFAction {

	private AbstractAIFUIApplication application = null;

	public ItemChangeCheckInfoAction(AbstractAIFUIApplication arg0, String arg1) {
		super(arg0, arg1);
		application = arg0;
	}

	public void run() {
		new ItemChangeCheckInfoCommand(application);
		
	}

}

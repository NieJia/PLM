package com.origin.rac.sac.itemsendinfo;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;

public class ItemSendInfoAction extends AbstractAIFAction {

	private AbstractAIFUIApplication application = null;

	public ItemSendInfoAction(AbstractAIFUIApplication arg0, String arg1) {
		super(arg0, arg1);
		application = arg0;
	}

	public void run() {
		
		new ItemSendInfoCommand(application);
		
	}

}

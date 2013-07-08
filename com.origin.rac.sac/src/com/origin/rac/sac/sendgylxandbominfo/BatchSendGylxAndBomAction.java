package com.origin.rac.sac.sendgylxandbominfo;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;

public class BatchSendGylxAndBomAction extends AbstractAIFAction {

	private AbstractAIFUIApplication application = null;
	
	public BatchSendGylxAndBomAction(AbstractAIFUIApplication arg0, String arg1) {
		super(arg0, arg1);
		application = arg0;
	}

	public void run() {
		
		new BatchSendGylxAndBomCommand(application);
		
	}

}

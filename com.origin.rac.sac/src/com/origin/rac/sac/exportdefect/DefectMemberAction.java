package com.origin.rac.sac.exportdefect;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;
import com.teamcenter.rac.kernel.TCException;

public class DefectMemberAction extends AbstractAIFAction {

	private AbstractAIFUIApplication application = null;

	public DefectMemberAction(AbstractAIFUIApplication arg0, String arg1) {
		super(arg0, arg1);
		application = arg0;
	}

	public void run() {
		try {
			new DefectMemberCommand(application);
		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

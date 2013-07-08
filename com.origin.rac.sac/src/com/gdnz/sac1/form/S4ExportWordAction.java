package com.gdnz.sac1.form;


import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;
import com.teamcenter.rac.kernel.TCException;

public class S4ExportWordAction extends AbstractAIFAction {

	private AbstractAIFUIApplication application = null;

	public S4ExportWordAction(AbstractAIFUIApplication arg0, String arg1) {
		super(arg0, arg1);
		application = arg0;
	}

	public void run() {
		try {
			new S4ExportWordCommand(application);
		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
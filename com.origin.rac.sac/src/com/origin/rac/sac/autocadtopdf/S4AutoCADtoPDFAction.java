package com.origin.rac.sac.autocadtopdf;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;

public class S4AutoCADtoPDFAction extends AbstractAIFAction {

	private AbstractAIFUIApplication app = null;
	
	public S4AutoCADtoPDFAction(AbstractAIFUIApplication app, String arg1) {
		super(app, arg1);
		this.app = app;
	}

	public void run() {
		S4AutoCADtoPDFCommand s4AutoCADtoPDFCommand = new S4AutoCADtoPDFCommand(app);
		try {
			s4AutoCADtoPDFCommand.executeModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

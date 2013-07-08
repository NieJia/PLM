package com.origin.rac.sac.gylx;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;

public class S4GYLXPZAction extends AbstractAIFAction{

	private AbstractAIFUIApplication app = null;
	private InterfaceAIFComponent target = null;
	
	public S4GYLXPZAction(AbstractAIFUIApplication app, InterfaceAIFComponent target, String arg1) {
		super(app, arg1);
		this.app = app;
		this.target = target;
	}
	
	public void run() {
		S4GYLXPZCommand s4GYLXPZCommand = new S4GYLXPZCommand(app, target);
		try {
			s4GYLXPZCommand.executeModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

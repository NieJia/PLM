package com.origin.rac.sac.gylx;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;

public class S4GYLXToErpAction extends AbstractAIFAction{

	private AbstractAIFUIApplication app = null;
	private InterfaceAIFComponent target = null;
	
	public S4GYLXToErpAction(AbstractAIFUIApplication app, InterfaceAIFComponent target, String arg1) {
		super(app, arg1);
		this.app = app;
		this.target = target;
	}
	
	public void run() {
		S4GYLXToErpCommand s4GYLXToErpCommand = new S4GYLXToErpCommand(app, target);
		try {
			s4GYLXToErpCommand.executeModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

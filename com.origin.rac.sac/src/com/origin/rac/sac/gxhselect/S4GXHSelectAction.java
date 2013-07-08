package com.origin.rac.sac.gxhselect;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;

public class S4GXHSelectAction extends AbstractAIFAction{

	private AbstractAIFUIApplication app = null;
	private InterfaceAIFComponent[] targets = null;
	
	public S4GXHSelectAction(AbstractAIFUIApplication arg0, InterfaceAIFComponent[] targets, String arg1) {
		super(arg0, arg1);
		this.app = arg0;
		this.targets = targets;
	}

	public void run() {
		
		S4GXHSelectCommand s4GXHSelectCommand = new S4GXHSelectCommand(app, targets);
		try {
			s4GXHSelectCommand.executeModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

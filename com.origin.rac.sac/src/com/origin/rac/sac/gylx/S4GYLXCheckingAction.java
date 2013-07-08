package com.origin.rac.sac.gylx;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;

public class S4GYLXCheckingAction extends AbstractAIFAction{

	private AbstractAIFUIApplication app = null;
	//private InterfaceAIFComponent target = null;
	
	public S4GYLXCheckingAction(AbstractAIFUIApplication arg0, String arg1) {
		super(arg0, arg1);
		this.app = arg0;
		//this.target = target;
	}

	public void run() {
		
		S4GYLXCheckingCommand s4GYLXCheckingCommand = new S4GYLXCheckingCommand(app);
		try {
			s4GYLXCheckingCommand.executeModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

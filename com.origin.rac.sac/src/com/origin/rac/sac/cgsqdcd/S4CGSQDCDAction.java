package com.origin.rac.sac.cgsqdcd;

import java.awt.Frame;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;
import com.teamcenter.rac.kernel.TCComponentItem;

public class S4CGSQDCDAction extends AbstractAIFAction {

	private AbstractAIFUIApplication app = null;
	private TCComponentItem targetitem = null;
	
	public S4CGSQDCDAction(AbstractAIFUIApplication app, Frame arg1, String arg2,TCComponentItem targetitem) {
		super(app, arg1, arg2);
		// TODO Auto-generated constructor stub
		this.app = app;
		this.targetitem = targetitem;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		S4CGSQDCDCommand command = new S4CGSQDCDCommand(app, targetitem);
		try {
			command.executeModal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

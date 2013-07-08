package com.origin.rac.sac.cgsqdcd;

import java.awt.Frame;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;
import com.teamcenter.rac.kernel.TCComponentItem;

public class S4CGSQDCDCJAction extends AbstractAIFAction{

	private AbstractAIFApplication app = null;
	private TCComponentItem targetitem = null;
	
	public S4CGSQDCDCJAction(AbstractAIFApplication arg0, Frame arg1,
			String arg2,TCComponentItem targetitem) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
		this.app = arg0;
		this.targetitem = targetitem;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		S4CGSQDCDCJCommand command = new S4CGSQDCDCJCommand(app, targetitem);
		try {
			command.executeModal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

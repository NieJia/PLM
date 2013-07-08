package com.gdnz.sac1.menu;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;
import com.teamcenter.rac.kernel.TCException;

public class ExportWuLiaoAction extends AbstractAIFAction {
	
	public ExportWuLiaoAction(AbstractAIFUIApplication arg0, String arg1) {
		super(arg0, arg1);
		application = arg0;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
     	try {
			new ExportWuLiaoExcel(application);
		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

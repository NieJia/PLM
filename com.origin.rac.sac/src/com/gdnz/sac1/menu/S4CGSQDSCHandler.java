package com.gdnz.sac1.menu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.common.NotDefinedException;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;


public class S4CGSQDSCHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		// TODO Auto-generated method stub
		AbstractAIFUIApplication app = AIFUtility.getCurrentApplication();
		System.out.println("**************SCCGSQD****************");
		new CreateCGSQDAction(app, null);
		try {
			final String menu_name = arg0.getCommand().getName();
			System.out.println("menu_name--->:"+menu_name);
			if ("生成采购申请单".equals(menu_name)) {
				CreateCGSQDAction action = new CreateCGSQDAction(app, null);
				new Thread(action).start();
			} 
		} catch (NotDefinedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

package com.origin.rac.sac.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.common.NotDefinedException;

import com.origin.rac.sac.sendbominfo.CheckGCBomInfoAction;
import com.origin.rac.sac.sendbominfo.CheckYXBomInfoAction;
import com.origin.rac.sac.sendbominfo.SendGCBomInfoAction;
import com.origin.rac.sac.sendbominfo.SendYXBomInfoAction;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;

public class SendBomInfoHandler extends AbstractHandler {

	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
		try {
			final String menu_name = arg0.getCommand().getName();
			System.out.println("menu_name--->:"+menu_name);
			if ("原型BOM传递".equals(menu_name)) {
				SendYXBomInfoAction action = new SendYXBomInfoAction(application, null);
				new Thread(action).start();
			} else if ("原型BOM传递检查".equals(menu_name)) {
				CheckYXBomInfoAction action = new CheckYXBomInfoAction(application, null);
				new Thread(action).start();
			} else if ("生产BOM传递".equals(menu_name)) {
				SendGCBomInfoAction action = new SendGCBomInfoAction(application, null);
				new Thread(action).start();
			} else if ("生产BOM传递检查".equals(menu_name)) {
				CheckGCBomInfoAction action = new CheckGCBomInfoAction(application, null);
				new Thread(action).start();
			}
			
		} catch (NotDefinedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

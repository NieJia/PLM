package com.gdnz.sac1.menu;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.origin.rac.sac.autocadtopdf.S4AutoCADtoPDFAction;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;

public class ExportWuLiaoHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		// TODO Auto-generated method stub
		AbstractAIFUIApplication app = AIFUtility.getCurrentApplication();	
		
		ExportWuLiaoAction exportWuLiaoAction = new ExportWuLiaoAction(app, null);
		new Thread(exportWuLiaoAction).start();
		
		return null;
	}

}

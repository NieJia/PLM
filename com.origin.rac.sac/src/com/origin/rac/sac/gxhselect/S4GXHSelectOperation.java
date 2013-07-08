package com.origin.rac.sac.gxhselect;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;

public class S4GXHSelectOperation extends AbstractAIFOperation{

	//private AbstractAIFApplication app = null;
	//private TCSession session = null;
	private S4GXHSelectCommand command = null;
	
	public S4GXHSelectOperation(S4GXHSelectCommand command, 
			AbstractAIFApplication app, TCSession session){
		//this.app = app;
		//this.session = session;
		this.command = command;
	}
	
	public void executeOperation() throws Exception {
		for(int i = 0; i < command.targets.length; i++){
			InterfaceAIFComponent target = command.targets[i];
			TCComponentBOMLine itemLine = (TCComponentBOMLine)target;
			try {
				TCComponentBOMLine parentLine = itemLine.parent();
				if(parentLine != null){
					itemLine.setProperty("S4masteroper", command.gongxuhao);
				}
			} catch (TCException e) {
				e.printStackTrace();
			}
		}
	}

}

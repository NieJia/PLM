package com.origin.rac.sac.gxhselect;

import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class S4GXHSelectCommand extends AbstractAIFCommand{

	private AbstractAIFUIApplication app = null;
	private TCSession session = null;

	public InterfaceAIFComponent[] targets = null;
	public TCComponentBOMLine parentLine = null;
	public String gongxuhao = null;

	public S4GXHSelectCommand(AbstractAIFUIApplication app, InterfaceAIFComponent[] targets){
		this.app = app;
		this.targets = targets;
		session = (TCSession) app.getSession();
		getParentLine();
	}
	public void executeModal() throws Exception {
		if(parentLine == null){
			MessageBox.post("请右键点击要添加工序号的底层bomline！","提示",MessageBox.WARNING);
			return;
		}
		else{
			//System.out.println("parentLine 不是空");
			S4GXHSelectDialog dialog = new S4GXHSelectDialog(this, app, session);
			setRunnable(dialog);
		}
	}
	public void getParentLine(){
		for(int i = 0; i < targets.length; i++){
			InterfaceAIFComponent target = targets[i];
			TCComponentBOMLine itemLine = (TCComponentBOMLine)target;
			try {
				parentLine = itemLine.parent();
				if(parentLine != null){
					break;
				}
			} catch (TCException e) {
				e.printStackTrace();
			}
		}
	}
}

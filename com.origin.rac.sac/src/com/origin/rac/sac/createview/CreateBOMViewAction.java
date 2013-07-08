package com.origin.rac.sac.createview;

import java.util.Vector;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class CreateBOMViewAction extends AbstractAIFAction {

	private AbstractAIFUIApplication application = null;
	private TCSession session =null;
	private TCComponentItem imancomponentitem = null;
	private InterfaceAIFComponent[] targets = null;
	private String[] types = {"S4CP","S4DEC","S4PLG"};
	private Vector<String> vec = new Vector<String>();
	private TCComponentItemRevision rev = null;

	public CreateBOMViewAction(AbstractAIFUIApplication arg0, String arg1) {
		super(arg0, arg1);
		application = arg0;
		targets = (InterfaceAIFComponent[])application.getTargetComponents();
		session = (TCSession) application.getSession();
	}

	public void run() {
		for (int i = 0; i < types.length; i++) {
			vec.add(types[i]);
		}
		if(targets.length==1){
			if(targets[0] instanceof TCComponentItem || targets[0] instanceof TCComponentItemRevision){
				if(targets[0] instanceof TCComponentItem){
					imancomponentitem = (TCComponentItem) targets[0];
				}else if(targets[0] instanceof TCComponentItemRevision){
					try {
						rev =  (TCComponentItemRevision) targets[0];
						imancomponentitem = rev.getItem();
					} catch (TCException e) {
						e.printStackTrace();
					}
				}
				String target_type = imancomponentitem.getType().toString();
				if(!vec.contains(target_type)){
					MessageBox.post("请选择单个指定对象操作!", "提示", MessageBox.INFORMATION);
					return;
				}else{
					new CreateBOMViewCommand(session,imancomponentitem,target_type);
				}
			}else{
				MessageBox.post("请选择单个指定对象操作!", "提示", MessageBox.INFORMATION);
				return;
			}
		}else{
			MessageBox.post("请选择单个指定对象操作!", "提示", MessageBox.INFORMATION);
			return;
		}
	}

}

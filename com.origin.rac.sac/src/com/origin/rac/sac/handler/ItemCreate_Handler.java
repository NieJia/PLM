package com.origin.rac.sac.handler;

import java.util.Vector;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;


import cn.com.origin.autocode.newitem.generatcode.NewCodeItemDialog;
import cn.com.origin.autocode.newitem.system.ORNewItemCommand;

import com.teamcenter.rac.aif.AIFDesktop;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.commands.newitem.NewItemCommand;
import com.teamcenter.rac.common.actions.NewItemAction;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.rac.util.Registry;

public class ItemCreate_Handler extends AbstractHandler {

	private AbstractAIFUIApplication app = null ;
	private String origin_newItemDialogType = "origin_newItemDialogType";
	private String str_type="";
	private String str_name="";
	private Vector<String> vec_name = new Vector<String>();
	
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		try {
			vec_name.removeAllElements();
			final String str_type1 = arg0.getCommand().getName();
			System.out.println("选择的菜单名称--->:"+str_type1);
			TCSession session = (TCSession) AIFUtility.getCurrentApplication()
					.getSession();
			app = AIFUtility.getCurrentApplication();
			String str[] = session.getPreferenceService().getStringArray(4,
					"origin_ItemType");
			if (str.length == 0) {
				MessageBox.post("请在OPTION中配置origin_ItemType选项！", "WARNING",
						MessageBox.WARNING);
				return null;
			}
			/*String str_auto[] = session.getPreferenceService().getStringArray(4,
				"origin_ItemType_Auto");
			if (str_auto.length == 0) {
				MessageBox.post("请在OPTION中配置origin_ItemType_Auto选项！", "WARNING",
						MessageBox.WARNING);
				return null;
			}*/
			for (int i = 0; i < str.length; i++) {
				if (str_type1.equals(str[i].split("=")[0])) {
					str_type = str[i].split("=")[1];
				}
			}
			/*for (int i = 0; i < str_auto.length; i++) {
				vec_name.add(str_auto[i]);
			}*/
			if ("".equals(str_type)) {
				MessageBox.post("请在OPTION中配置" + str_type1 + "所对应的类型！",
						"WARNING", MessageBox.WARNING);
				return null;
			}
			System.out.println("str_type====>:"+str_type);
			String value = session.getPreferenceService().getString(4,origin_newItemDialogType);
			if (value.equals("2")) {
				new Thread() {
					public void run() {
						ORNewItemCommand command = new ORNewItemCommand(AIFDesktop.getActiveDesktop().getFrame(), AIFUtility.getCurrentApplication(),str_type);
						try {
							command.executeModal();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}.start();
			} else {
				new NewCodeItemDialog(AIFDesktop.getActiveDesktop().getShell()).open();
			}
			
			/*NewCodeItemDialog dialog=new NewCodeItemDialog(AIFDesktop.getActiveDesktop().getShell(),str_type);
		
			dialog.open();*/
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}

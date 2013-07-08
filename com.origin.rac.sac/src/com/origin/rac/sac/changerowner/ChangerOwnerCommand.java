package com.origin.rac.sac.changerowner;

import java.util.Vector;

import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCAccessControlService;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentGroup;
import com.teamcenter.rac.kernel.TCComponentGroupMember;
import com.teamcenter.rac.kernel.TCComponentGroupMemberType;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCProperty;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCTextService;
import com.teamcenter.rac.util.MessageBox;

public class ChangerOwnerCommand extends AbstractAIFCommand {

	private AbstractAIFUIApplication app;
	private TCSession session;
	private InterfaceAIFComponent[] targets;
	private boolean flag=false;
	private boolean flag1=false;
	private boolean fg=false;
	private boolean fg1=false;
	private TCComponentGroupMember groupMember;
	private static TCAccessControlService accessService = null;
	private String CHANGENAME="CHANGE_OWNER";
	private String object_string;
	private Vector vNum = new Vector();
	private Vector vuser = new Vector();
	
	public ChangerOwnerCommand(AbstractAIFUIApplication application) {
		this.app=application;
		session = (TCSession) app.getSession();
		targets = app.getTargetComponents();
		if(targets==null || targets.length==0){
			MessageBox.post("请选择item对象或者文件夹!", "提示", MessageBox.INFORMATION);
			return;
		}
		//首先判断选择对象的类型是不是满足要求
		for (int i = 0; i < targets.length; i++) {
			if(!(targets[i] instanceof TCComponentItem) && !(targets[i] instanceof TCComponentFolder)){
				flag = true;
			}
		}
		if(flag){
			MessageBox.post("请选择item对象或者文件夹!", "提示", MessageBox.INFORMATION);
			return;
		}else{
			try {
				for (int i = 0; i < targets.length; i++) {
					TCComponent com = (TCComponent) targets[i];
					object_string = com.getProperty("object_string");
					System.out.println("object_string----->:"+object_string);
					fg = checkAccessPrivilige(com,CHANGENAME);
//					System.out.println("fg-----===>:"+fg);
					if(fg==false){
						fg1=true;
						vNum.add(object_string);
					}
					TCProperty tcproperty = com.getTCProperty("owning_user");
					TCComponentUser tccomponentuser = (TCComponentUser)tcproperty.getReferenceValue();
					TCProperty tcproperty1 = com.getTCProperty("owning_group");
					TCComponentGroup tccomponentgroup = (TCComponentGroup)tcproperty1.getReferenceValue();
					TCComponentGroupMemberType tccomponentgroupmembertype = (TCComponentGroupMemberType)session.getTypeComponent("GroupMember");
		            TCComponentGroupMember atccomponentgroupmember[] = tccomponentgroupmembertype.find(tccomponentuser, tccomponentgroup);
		            if(atccomponentgroupmember != null && atccomponentgroupmember.length > 0)
		            {
		                groupMember = atccomponentgroupmember[0];
		            }
		            vuser.add(groupMember);
				}
//				System.out.println("fg1===>:"+fg1);
//				System.out.println("vNum==11=>:"+vNum);
//				System.out.println("vuser==22222=>:"+vuser);
				if(fg1==true){
					MessageBox.post("您选择的对象中对"+vNum+"没有更改权限，请重新选择！", "提示", MessageBox.INFORMATION);
					return;
				}
				for (int i = 0; i < vuser.size()-1; i++) {
					if(!vuser.elementAt(i).equals(vuser.elementAt(i+1))){
						flag1 = true;
					}
				}
//				System.out.println("flag1====000---->:"+flag1);
				if(flag1 == true){
					MessageBox.post("您多选对象的所有者不一样，请重新选择!", "提示", MessageBox.INFORMATION);
					return;
				}
//				System.out.println("groupMember----1-->:"+groupMember);
				new ChangerOwnerDialog(session, app,targets,groupMember);
				
				
			} catch (TCException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public boolean checkAccessPrivilige(TCComponent com, String changeownername)
			throws TCException {
		return checkAccessPrivilige(session.getUser(), com, changeownername);
	}

	private boolean checkAccessPrivilige(TCComponentUser user,
			TCComponent comp, String accessName) throws TCException {
		if (accessService == null)
			accessService = session.getTCAccessControlService();
		return accessService.checkUsersPrivilege(user, comp, accessName);
	}
}

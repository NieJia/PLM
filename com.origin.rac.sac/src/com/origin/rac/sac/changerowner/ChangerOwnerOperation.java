package com.origin.rac.sac.changerowner;

import java.util.Vector;

import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCAccessControlService;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentGroup;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;

public class ChangerOwnerOperation extends AbstractAIFOperation {

	private static TCAccessControlService accessService = null;
	private AbstractAIFUIApplication app;
	private InterfaceAIFComponent[] targets;
	private TCSession session;
	private TCComponentUser user;
	private TCComponentGroup group;
	private String CHANGENAME="CHANGE_OWNER";
	private boolean flag1=false;
	
	public ChangerOwnerOperation(AbstractAIFUIApplication application,
			TCComponentUser selectUser, TCComponentGroup grp,InterfaceAIFComponent[] tgetcomponents){
		app = application;
		session = (TCSession) app.getSession();
		targets = tgetcomponents;
		user = selectUser;
		group = grp;
	}
	
	
	
	@Override
	public void executeOperation() throws Exception {
		// TODO Auto-generated method stub

		for (int i = 0; i < targets.length; i++) {
			if(targets[i] instanceof TCComponentItem){
				TCComponentItem item = (TCComponentItem) targets[i];
				flag1 = checkAccessPrivilige(item,CHANGENAME);
				item.changeOwner(user, group);
				TCComponent[] revisions = item.getRelatedComponents("revision_list");
				//对item下面的版本进行更改所有权
				for (int k = 0; k < revisions.length; k++) {
					if(revisions[k] instanceof TCComponentItemRevision){
						revisions[k].changeOwner(user, group);
						TCComponent[] com = revisions[k].getRelatedComponents("IMAN_specification");
						//对数据集进行修改所有权
						if(com!=null && com.length>0){
							for (int j = 0; j < com.length; j++) {
								if(com[j] instanceof TCComponentDataset){
									com[j].changeOwner(user, group);
								}
							}
						}
					}
				}
			}else if(targets[i] instanceof TCComponentFolder){
				TCComponentFolder folder = (TCComponentFolder) targets[i];
				getAllFolders(folder);
			}
		}
	}
	
	/*
	 * 递归获取所有子Folder
	 * */
	public void getAllFolders(TCComponentFolder folder){
		try {
			folder.changeOwner(user, group);
			TCComponent[] coms = folder.getRelatedComponents("contents");
			if(coms!=null && coms.length>0){
				for (int j = 0; j < coms.length; j++) {
					if(coms[j] instanceof TCComponentItem){
						TCComponentItem item = (TCComponentItem) coms[j];
						item.changeOwner(user, group);
						TCComponent[] revisions = item.getRelatedComponents("revision_list");
						//对item下面的版本进行更改所有权
						for (int k = 0; k < revisions.length; k++) {
							if(revisions[k] instanceof TCComponentItemRevision){
								revisions[k].changeOwner(user, group);
								TCComponent[] com = revisions[k].getRelatedComponents("IMAN_specification");
								//对数据集进行修改所有权
								if(com!=null && com.length>0){
									for (int m = 0; m < com.length; m++) {
										if(com[m] instanceof TCComponentDataset){
											com[m].changeOwner(user, group);
										}
									}
								}
							}
						}
					}else if(coms[j] instanceof TCComponentFolder){
						TCComponentFolder subFolder = (TCComponentFolder) coms[j];
						getAllFolders(subFolder);
					}
				}
			}
		} catch (TCException e) {
			e.printStackTrace();
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

package com.origin.rac.sac.createview;

import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentBOMViewRevision;
import com.teamcenter.rac.kernel.TCComponentBOMViewRevisionType;
import com.teamcenter.rac.kernel.TCComponentBOMWindow;
import com.teamcenter.rac.kernel.TCComponentBOMWindowType;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentRevisionRule;
import com.teamcenter.rac.kernel.TCComponentRevisionRuleType;
import com.teamcenter.rac.kernel.TCComponentViewType;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class CreateBOMViewCommand extends AbstractAIFCommand {
	
	private TCSession session = null;
	private TCComponentItem imancomponentitem = null;
	private String item_type = "";
	private String dd_type = "S4CP";
	private String dd_view_type = "S4MZ0";
	private boolean flag = false;
	
	public CreateBOMViewCommand(TCSession sess,TCComponentItem item,String type){
		session = sess;
		imancomponentitem = item;
		item_type = type;
		if(dd_type.equals(item_type)){
			//创建BOM视图
			createView(dd_view_type);
			System.out.println("flag---------->:"+flag);
			if(!flag){
				MessageBox.post("BOM视图创建成功!","提示",MessageBox.INFORMATION);
			}
		}else{
			//如果选择的不是订单行物料,则在对话框里面选择
			new CreateBOMViewDialog(session,imancomponentitem);
		}
		
	}

	
	//创建BOM视图版本
	public void createView(String type){
		try {
			TCComponentRevisionRuleType imancomponentrevisionruletype = (TCComponentRevisionRuleType) session
					.getTypeComponent("RevisionRule");
			TCComponentRevisionRule imancomponentrevisionrule = imancomponentrevisionruletype
					.getDefaultRule();
			TCComponentBOMWindowType imancomponentbomwindowtype = (TCComponentBOMWindowType) session
					.getTypeComponent("BOMWindow");
			TCComponentBOMWindow imancomponentbomwindow = imancomponentbomwindowtype
					.create(imancomponentrevisionrule);
			TCComponentBOMViewRevisionType localTCComponentBOMViewRevisionType = (TCComponentBOMViewRevisionType) session
					.getTypeComponent("PSBOMViewRevision");
			TCComponentViewType[] arrayOfTCComponentViewType = localTCComponentBOMViewRevisionType
					.getAvailableViewTypes(imancomponentitem
							.getProperty("item_id"), imancomponentitem
							.getLatestItemRevision().getProperty(
									"item_revision_id"));

			TCComponentViewType tccomponentViewType = null;
			for (int i = 0; i < arrayOfTCComponentViewType.length; i++) {
				System.out.println(arrayOfTCComponentViewType[i]);
				if (type.equals(arrayOfTCComponentViewType[i].toString())) {
					tccomponentViewType = arrayOfTCComponentViewType[i];
					break;
				}
			}
			System.out.println("tccomponentViewType=========>:"
					+ tccomponentViewType);
			TCComponentBOMLine componentbomline1 = null;
			if (tccomponentViewType != null) {
				TCComponentBOMViewRevision localTCComponentBOMViewRevision = localTCComponentBOMViewRevisionType
						.create(imancomponentitem.getProperty("item_id"),
								imancomponentitem.getLatestItemRevision()
										.getProperty("item_revision_id"),
								tccomponentViewType, false);
				System.out.println("localTCComponentBOMViewRevision---->:"+localTCComponentBOMViewRevision);
				TCComponentItemRevision imancomponentitemrevision1 = null;
				TCComponent imancomponent = null;
				componentbomline1 = imancomponentbomwindow.setWindowTopLine(
						imancomponentitem, imancomponentitemrevision1,
						imancomponent, localTCComponentBOMViewRevision);
				imancomponentbomwindow.save();
			}else{
				flag = true;
				MessageBox.post("您选择的对象下已经存在" + type
						+ "类型", "提示", MessageBox.INFORMATION);
				return;
			}
		} catch (TCException e) {
			e.printStackTrace();
		}
	}
	
	
}

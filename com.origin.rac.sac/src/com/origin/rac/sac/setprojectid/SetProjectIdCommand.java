package com.origin.rac.sac.setprojectid;

import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCTextService;
import com.teamcenter.rac.util.MessageBox;

public class SetProjectIdCommand extends AbstractAIFCommand {

	private String relation ="IMAN_master_form";
	private String itemType1 ="S4YFprojXJ";
//	private String itemType2 ="S4YFprojJD";
	private TCSession session = null;
	
	public SetProjectIdCommand(AbstractAIFUIApplication app) {
		session = (TCSession) app.getSession();
		TCComponent target = (TCComponent) app.getTargetComponent();
		if (target == null || !(target instanceof TCComponentItem)) {
			MessageBox.post("您选中的对象不是项目对象,请重新选择!", "提示", MessageBox.INFORMATION);
			return;
		}else{
			try {
				TCComponentItem item = (TCComponentItem) target;
				if(itemType1.equals(item.getType().toString())){
					TCComponentForm master_form = (TCComponentForm) item.getRelatedComponent(relation);
					String proj_id = master_form.getProperty("s4prj_id");
					System.out.println("22===>:"+proj_id);
					if(proj_id==null || "".equals(proj_id)){
						MessageBox.post("项目还未通过评审!", "提示", MessageBox.INFORMATION);
						return;
					}else{
						InterfaceAIFComponent[] items = query(proj_id);
						if(items == null || items.length==0){
							item.setProperty("item_id", proj_id);
							master_form.setProperty("object_name", proj_id);
						}else{
							MessageBox.post("这个项目令号已经存在,不符合唯一性,请检查!", "提示", MessageBox.INFORMATION);
							return;
						}
					}
				}else{
					MessageBox.post("您选中的对象不是项目对象,请重新选择!", "提示", MessageBox.INFORMATION);
					return;
				}
			} catch (TCException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	//调用系统零组件 ID查询
	public InterfaceAIFComponent[] query(String id){
		InterfaceAIFComponent[] items = null;
		try {
			TCTextService tcService =session.getTextService();
			String askKey[]={tcService.getTextValue("ItemID")};
			String askValue[]={id};
			items =  session.search("零组件 ID", askKey, askValue);
		} catch (TCException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
	
}

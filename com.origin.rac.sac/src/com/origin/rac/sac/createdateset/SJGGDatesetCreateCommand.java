package com.origin.rac.sac.createdateset;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.com.origin.util.PublicMethod;
import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentTcFile;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCTextService;
import com.teamcenter.rac.util.MessageBox;

public class SJGGDatesetCreateCommand extends AbstractAIFCommand {

	private String itemType ="S4SJGG";
	private TCSession session = null;
	private String releation = "S4Affix_C";
//	private String[] str_names = {"模板1","模板2","模板3"};
	private String[] str_names = null;
	private List all_dateset = new ArrayList();
	
	
	public SJGGDatesetCreateCommand(AbstractAIFUIApplication app) {
		session = (TCSession) app.getSession();
		TCComponent target = (TCComponent) app.getTargetComponent();
		if (target == null || !(target instanceof TCComponentItem)) {
			MessageBox.post("请选择设计更改单对象后重试!", "提示", MessageBox.INFORMATION);
			return;
		}else{
			try {
				str_names = session.getPreferenceService().getStringArray(4, "SAC_MSWord_Template_Name");
				if(str_names == null || str_names.length == 0){
					MessageBox.post("未配置首选项：SAC_MSWord_Template_Name的值", "提示", MessageBox.INFORMATION);
					return;
				}
				for (int i = 0; i < str_names.length; i++) {
					
					System.out.println("str_names--->:"+str_names[i]);
				}
				TCComponentItem item = (TCComponentItem) target;
				//判断选择的类型是不是设计更改申请表
				if(itemType.equals(item.getType().toString())){
					TCComponentItemRevision latest_rev = item.getLatestItemRevision();
					String str = latest_rev.getProperty("release_status_list");
					if("".equals(str)){
						TCComponent[] dasets = latest_rev.getRelatedComponents(releation);
						if(dasets!=null && dasets.length>0){
							MessageBox.post("更改附件已经存在数据集,不能创建,请检查!", "提示", MessageBox.INFORMATION);
							return;
						}else{
							// 查找数据集模版
							boolean flag = false;
							for (int i = 0; i < str_names.length; i++) {
								InterfaceAIFComponent[] datesets = queryDateset(str_names[i]);
								if(datesets==null || datesets.length==0){
									flag = true;
									break;
								}
							}
							if(flag){
								MessageBox.post("请在infodba下创建数据集模板!", "提示", MessageBox.INFORMATION);
								return;
							}else{
								//创建数据集在指定关系下
								for (int i = 0; i < str_names.length; i++) {
									InterfaceAIFComponent[] datesets = queryDateset(str_names[i]);
									for (int j = 0; j < datesets.length; j++) {
										TCComponentDataset daset = (TCComponentDataset) datesets[j];
										String name = daset.getProperty("object_name");
										TCComponentDataset daset1 = daset.saveAs(name);
										all_dateset.add(daset1);
									}
								}
								System.out.println("all_dateset--->:"+all_dateset);
								latest_rev.add(releation, all_dateset);
								MessageBox.post("设计更改单附件创建成功!", "提示", MessageBox.INFORMATION);
							}
						}
					}else{
						MessageBox.post("最新版本已经发布，不能生成更改附件!", "提示", MessageBox.INFORMATION);
						return;
					}
				}else{
					MessageBox.post("请选择设计更改单对象后重试!", "提示", MessageBox.INFORMATION);
					return;
				}
			} catch (TCException e) {
				e.printStackTrace();
			}
			
			
			
		}
	}
	
	//调用系统零组件 ID查询
	public InterfaceAIFComponent[] queryDateset(String name){
		InterfaceAIFComponent[] items = null;
		try {
			TCTextService tcService =session.getTextService();
			String askKey[]={tcService.getTextValue("Name"),tcService.getTextValue("OwningUser")};
			String askValue[]={name,"infodba"};
			items =  session.search("数据集...", askKey, askValue);
		} catch (TCException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
	
}

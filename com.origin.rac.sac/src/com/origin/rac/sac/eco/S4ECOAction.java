package com.origin.rac.sac.eco;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentBOMViewRevision;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.util.MessageBox;

public class S4ECOAction extends AbstractAIFAction{

	private AbstractAIFUIApplication app = null;
	private TCComponentItemRevision target = null;

	private String relationForm = "IMAN_master_form_rev";
	private static final String relation = "structure_revisions";
	public String description = null;
	private String relation1 = "structure_revisions";
	private String before_zzdm = "";
	private String after_zzdm = "";
	
	
	public S4ECOAction(AbstractAIFUIApplication arg0, TCComponentItemRevision target, String arg1) {
		super(arg0, arg1);
		this.app = arg0;
		this.target = target;
	}

	public void run() {
		try {
			TCComponent[] before = target.getReferenceListProperty("S4Before_C");
			TCComponent[] after = target.getReferenceListProperty("S4After_C");
			if(before.length ==1 && after.length ==1){
				TCComponentItemRevision beforeItemRev = (TCComponentItemRevision)before[0];
				TCComponent[] before_views = beforeItemRev.getRelatedComponents(relation1);
				if(before_views.length>1){
					MessageBox.post("更改前对象不能存在两个视图版本!","提示",MessageBox.WARNING);
					return;
				}
				TCComponentBOMViewRevision before_bomview = (TCComponentBOMViewRevision) beforeItemRev.getRelatedComponent(relation1);
				if(before_bomview!=null){
					String str_view = before_bomview.toString();
					before_zzdm = str_view.substring(str_view.length()-3,str_view.length());
				}
				System.out.println("before_zzdm---->:"+before_zzdm);
				TCComponentItemRevision afterItemRev = (TCComponentItemRevision)after[0];
				TCComponent[] after_views = afterItemRev.getRelatedComponents(relation1);
				if(after_views.length>1){
					MessageBox.post("更改后对象不能存在两个视图版本!","提示",MessageBox.WARNING);
					return;
				}
				TCComponentBOMViewRevision after_bomview = (TCComponentBOMViewRevision) afterItemRev.getRelatedComponent(relation1);
				if(after_bomview!=null){
					String str_view = after_bomview.toString();
					after_zzdm = str_view.substring(str_view.length()-3,str_view.length());
				}
				System.out.println("更改后组织代码----------->:"+after_zzdm);
				if(!before_zzdm.equals(after_zzdm)){
					MessageBox.post("更改前对象和更改后对象视图版本不一致!","提示",MessageBox.WARNING);
					return;
				}
				
				String beforeItemId = beforeItemRev.getProperty("item_id");
				String afterItemId = afterItemRev.getProperty("item_id");
				if(beforeItemId.equals(afterItemId)){
					String beforeItemRevId = beforeItemRev.getProperty("item_revision_id");
					String afterItemRevId = afterItemRev.getProperty("item_revision_id");
					if(!beforeItemRevId.equals(afterItemRevId)){

						TCComponent beforeComponent = beforeItemRev.getRelatedComponent(relation);
						TCComponent afterComponent = afterItemRev.getRelatedComponent(relation);
						if(beforeComponent != null && afterComponent != null){
							TCComponentBOMViewRevision beforeBOMViewRevision = 
								(TCComponentBOMViewRevision)beforeComponent;
							TCComponentBOMViewRevision afterBOMViewRevision = 
								(TCComponentBOMViewRevision)afterComponent;
							//得到说明
							TCComponent[] tccomponent = beforeItemRev.getRelatedComponents(relationForm);
							for (int i = 0; i < tccomponent.length; i++) {
								if(tccomponent[i] instanceof TCComponentForm){
									TCComponentForm tccomponentForm = (TCComponentForm)tccomponent[i];
									description = tccomponentForm.getProperty("s4Mdescription");
								}
							}
							//如果有数据集就不要在进行了
							TCComponent[] specstand = target.getReferenceListProperty("S4ECO");
							if(specstand.length != 0){
/*								for (int n = 0; n < specstand.length; n++) {
									if(specstand[n] instanceof TCComponentDataset){
										TCComponentDataset temp = (TCComponentDataset)specstand[n];
										target.remove("S4ECO", temp);
									}
								}*/
								MessageBox.post("ECO已经生成过！","提示",MessageBox.WARNING);
								return;
							}
							
							S4ECOCommand s4ECOCommand = new S4ECOCommand(app,
									beforeBOMViewRevision, afterBOMViewRevision, description, target,before_zzdm);
							try {
								s4ECOCommand.executeModal();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						else{
							if(beforeComponent == null){
								MessageBox.post("更改前的物料没有bomview！","提示",MessageBox.WARNING);
								return;
							}
							else{
								MessageBox.post("更改后的物料没有bomview！","提示",MessageBox.WARNING);
								return;
							}
						}
					}
					else{
						MessageBox.post("请保证更改前后的物料是同一物料的不同版本！","提示",MessageBox.WARNING);
						return;
					}
				}
				else{
					MessageBox.post("请保证更改前后的物料是同一物料的不同版本！","提示",MessageBox.WARNING);
					return;
				}
			}
			else{
				MessageBox.post("请保证更改前和更改后不为空，切只有一个bom！","提示",MessageBox.WARNING);
				return;
			}
		} catch (TCException e1) {
			e1.printStackTrace();
		}
	}
}

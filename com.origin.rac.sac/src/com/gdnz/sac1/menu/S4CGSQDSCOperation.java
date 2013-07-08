package com.gdnz.sac1.menu;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentItemType;
import com.teamcenter.rac.kernel.TCComponentTcFile;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCTextService;
import com.teamcenter.rac.util.MessageBox;

import cn.com.origin.util.ProgressBarThread;

public class S4CGSQDSCOperation extends AbstractAIFOperation{
	private ProgressBarThread progressBarThread = null ;
	private TCSession session = null;
	private String fileName = "";
	private TCComponentItem fatherItem = null;
	private TCComponent target = null;
	private InterfaceAIFComponent[] targets = null;
	private CGSQDInfo cgSQDInfo_row = null;
	private File fmsFile = null;
	private File fmsFileMuBan = null;
	private Vector<CGSQDInfo> cGSQDInfo = new Vector<CGSQDInfo>();
	private String bianhao;
	private String contactNum;
	
	public S4CGSQDSCOperation(AbstractAIFApplication app,String bh){
		
	targets = app.getTargetComponents();
	session = (TCSession) app.getSession();
	this.bianhao=bh;
	}
	@Override
	public void executeOperation() throws TCException, IOException ,Exception{
		
		System.out.println("******b*******"+bianhao);
		init();
		
	}

	public void init(){
		session.queueOperation(new AbstractAIFOperation("工作中...") {
			public void executeOperation() throws Exception {
				progressBarThread = new ProgressBarThread("生成采购申请单", "正在生成采购申请单，请稍后...");
				progressBarThread.start();	
				try {
					TCComponentItem target_item = (TCComponentItem) targets[0];
					TCComponentFolder folder1 = null;
					TCComponentFolder father_folder = null;
					TCComponentFolder target_folder = null;
					TCComponentItem qgd_item=null;
					AIFComponentContext[] aif = target_item.whereReferenced();

					if (aif != null && aif.length > 0) {
						for (int i = 0; i < aif.length; i++) {
							if(aif[i].getComponent() instanceof TCComponentFolder){
								folder1 = (TCComponentFolder) aif[i].getComponent();
							}
						}
						System.out.println("----folder1>:"+folder1);
						if(folder1!=null){
							AIFComponentContext[] aif1 = folder1.whereReferenced();
							if (aif1 != null && aif1.length > 0) {
								for (int i = 0; i < aif1.length; i++) {
									if(aif1[i].getComponent() instanceof TCComponentFolder){
										father_folder = (TCComponentFolder) aif1[i].getComponent();
									}
								}
								if(father_folder!=null){
									for (TCComponent component : father_folder.getRelatedComponents("contents")) {
										if (component.getProperty("object_name").equals("归档资料")) {
											target_folder = (TCComponentFolder) component;
										//	contactNum=target_folder.getProperty("gov_classification");
										}
									}
									if(target_folder!=null){
										qgd_item=createItem("S4QGD", "请购单", target_folder);	
									}else{
										progressBarThread.stopBar();
										MessageBox.post("您选择的对象引用的文件夹没有归档资料，请检查！", "提示", 1);
										return;
									}
								}
							}
						}
					}	
					if(qgd_item!=null){
						TCComponentItemRevision qgditem_rev=qgd_item.getLatestItemRevision();
						TCComponentDataset dataSet=(TCComponentDataset)qgditem_rev.getRelatedComponent("IMAN_specification");
						if (dataSet != null) {
							TCComponentTcFile[] files = dataSet.getTcFiles();
							if (files != null) {
								if (files.length == 1) {
									fmsFile = files[0].getFmsFile();
									fmsFile.setWritable(true);
									fmsFile.setExecutable(true);
									fmsFile.setReadable(true);
									
								} else if (files.length == 0) {
									progressBarThread.stopBar();
									MessageBox.post("数据集模板没有引用文件，请检查！", "提示", 1);
									return;
								} else {
									progressBarThread.stopBar();
									MessageBox.post("数据集模板的引用文件过多，请检查！", "提示", 1);
									return;
								}
							} else {
								progressBarThread.stopBar();
								MessageBox.post("数据集模板没有引用文件，请检查！", "提示", 1);
								return;
							}
//					dataSet.removeFiles("excel");//NamedReference为引用的文件类型
//					dataSet.clearCache();
						}
						qgdExport(fmsFile,dataSet);
						progressBarThread.stopBar();
						MessageBox.post("生成采购申请单完毕", "提示", MessageBox.INFORMATION);
					}else{
						progressBarThread.stopBar();
						MessageBox.post("您选择的对象不在规定的文件夹结构目录下，请检查！", "提示", 1);
						return;
					}
				} catch (TCException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	

}	
private void qgdExport(File file,TCComponentDataset ds) throws IOException, TCException {
/*	
    File xlsFile = new File(path);	
	FileInputStream in = new FileInputStream(fmsFileMuBan);
	FileOutputStream out = new FileOutputStream(xlsFile);
	byte[] buffer = new byte[1024];
	int len = -1;
	
	while ((len = in.read(buffer)) != -1) {
		out.write(buffer, 0, len);
	}
	
	in.close();
	out.close();
*/	
	TCComponentItem fatherItem = getFatherItem((TCComponentItem)targets[0]);
	for (InterfaceAIFComponent AIFtarget : targets) {
		
		TCComponent target = (TCComponent) AIFtarget;
//		TCComponentItem fatherItem = getFatherItem((TCComponentItem)target);
		TCComponentItemRevision item_rev=((TCComponentItem)target).getLatestItemRevision();
		TCComponentForm form=(TCComponentForm)item_rev.getRelatedComponent("IMAN_master_form_rev");
		
		cgSQDInfo_row = new CGSQDInfo();
		cgSQDInfo_row.setContactNum(fatherItem.getProperty("item_id"));
		cgSQDInfo_row.setPrjName(fatherItem.getProperty("object_name"));
		cgSQDInfo_row.setWuLiaoId(((TCComponentItem)target).getProperty("item_id"));
		cgSQDInfo_row.setWuLiaoName(form.getProperty("s4Mdescription"));
		cgSQDInfo_row.setDanWei(form.getProperty("s4Primary_Unit_of_M"));
		
		cGSQDInfo.add(cgSQDInfo_row);	
	}
	new ExportCGSQDExcel(cGSQDInfo, file,bianhao);
/*				
	ImportFilesOperation fileOp=null;
	fileOp=new ImportFilesOperation(ds,file,"MSExcel","BINARY","excel",null); 
	try {
		fileOp.executeOperation();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
*/
}



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

public TCComponentItem getFatherItem(TCComponentItem target) throws TCException {

	TCComponentFolder folder1 = null;
	TCComponentFolder folder2 = null;
	AIFComponentContext[] aif = null;
	AIFComponentContext[] aif1 = null;
	AIFComponentContext[] aif2 = null;
	
	TCComponentItem	item = target;
    aif = item.whereReferenced();
	if (aif != null && aif.length > 0) {
		for (int i = 0; i < aif.length; i++) {
			if(aif[i].getComponent() instanceof TCComponentFolder){
				folder1 = (TCComponentFolder) aif[i].getComponent();
			}
		}
        System.out.println("--Father*--folder1>:"+folder1);
			if(folder1!=null){
				aif1 = folder1.whereReferenced();
				if (aif1 != null && aif1.length > 0) {
					for (int i = 0; i < aif1.length; i++) {
						if(aif1[i].getComponent() instanceof TCComponentFolder){
							folder2 = (TCComponentFolder) aif1[i].getComponent();
					}
				}
		System.out.println("--Father*--folder2>:"+folder2);
		if(folder2!=null){
			aif2 = folder2.whereReferenced();
			if (aif2 != null && aif2.length > 0) {
				for (int i = 0; i < aif2.length; i++) {
					if(aif2[i].getComponent() instanceof TCComponentItem){
						fatherItem = (TCComponentItem) aif2[i].getComponent();
				         }
			       }
				System.out.println("--Father*--fatherItem>:"+fatherItem);
				if(fatherItem!=null)
					return fatherItem;
				}						
		  }
		}
	}
  }
	return null;
}
private TCComponentItem createItem(String type, String name, TCComponentFolder parent) {
	try {
		String itemId="" ;// 获取编码
		TCComponentItemType itemType = (TCComponentItemType) session.getTypeComponent(type);
		        itemId= bianhao; // 获取编码
		//   itemType.find(itemId)
				String itemRev = itemType.getNewRev(null); // 获取版本编码
				TCComponentItem item = itemType.create(itemId, itemRev, type, name, "", null);
				item.setProperty("gov_classification", parent.getProperty("gov_classification"));
				item.getLatestItemRevision().setProperty("gov_classification", parent.getProperty("gov_classification"));
				if (item != null)
				{	parent.add("contents", item);
				    return item;
				}
				
		
	} catch (TCException e) {
		e.printStackTrace();
	}
	return null;
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

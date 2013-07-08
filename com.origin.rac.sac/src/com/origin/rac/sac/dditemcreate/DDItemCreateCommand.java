package com.origin.rac.sac.dditemcreate;

import java.util.Vector;

import cn.com.origin.util.ProgressBarThread;

import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentItemType;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCFormProperty;
import com.teamcenter.rac.kernel.TCProperty;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCTextService;
import com.teamcenter.rac.util.MessageBox;

public class DDItemCreateCommand extends AbstractAIFCommand {

	private String itemType ="S4Gcproj";
	private TCSession session = null;
	private String releation = "IMAN_reference";
	private String releation1 = "IMAN_master_form";
	private String releation2 = "IMAN_master_form_rev";
	private TCComponentFolder folder ;
	private TCComponentFolder target_folder ;
	private String type = "S4MZPrj_Folder";
	private String folder_name = "订单行物料及图纸";
	private String[] tablePropertyName = {"s4coding1","s4discriptions1","s4vdc1","s4ddcc1","s4babintasize1","s4declass","s4remarkss","s4Maternum","s4Saleo"};
	private String[] Coding_array,Discriptions_array,Vdc_array,Ddcc_array,Babintasize_array,Declass_array,Remarkss_array,Maternum_array,Saleo_array;
	private TCProperty[] formProperties = null;
	private TCFormProperty ddformProperties[] = null;
	private String str_type = "S4CP";
	private boolean flag3 = false;
	private boolean flag4 = false;
	private ProgressBarThread progressbar = null ;
	private String zffl_str = "";
	private String attri = "gov_classification";
	
	
	public DDItemCreateCommand(AbstractAIFUIApplication app) {
		session = (TCSession) app.getSession();
		TCComponent target = (TCComponent) app.getTargetComponent();
		if (target == null || !(target instanceof TCComponentItem)) {
			MessageBox.post("请选择合同对象后重试!", "提示", MessageBox.INFORMATION);
			return;
		}else{
			try {
				progressbar = new ProgressBarThread("订单行物料创建" ,"订单行物料创建中,请稍等...");
				progressbar.start();
				TCComponentItem item = (TCComponentItem) target;
				String owner_name = item.getProperty("owning_user");
				System.out.println("owner_name--->:"+owner_name);
				zffl_str = item.getProperty("item_id").toString();
				System.out.println("zffl_str--->:"+zffl_str);
				TCComponentUser user = session.getUser();
				System.out.println("user-------++===>:"+user.toString());
				/*System.out.println("k===>:"+k);
				for (int i = 0; i < k; i++) {
					String[] str1 = new String[v_all.size()];
					String[] str = new String[k];
					for (int j = 0; j < v_all.size(); j++) {
						str[i] = v_all.get(j)[i];
						str1[j] = str[i];
					}
					vec_dd.add(str1);
				}
				System.out.println("vec_dd1==>:"+vec_dd.size());*/
				
				//判断选择的合同对象owner不是当前执行人
				if(owner_name.equals(user.toString())){
					//判断选择对象的类型是不是合同对象
					if(itemType.equals(item.getType().toString())){
						//判断选择的合同下面有没有工程物料的文件夹
						TCComponent[] coms = item.getRelatedComponents(releation);
						System.out.println("coms--->:"+coms.length);
						if(coms!=null && coms.length>0){
							for (int i = 0; i < coms.length; i++) {
								if(type.equals(coms[i].getType().toString())){
									flag3 = true;
									folder = (TCComponentFolder) coms[i];
									break;
								}
							}
						}
						if(!flag3){
							progressbar.stopBar();
							MessageBox.post("您选择的对象下面没有订单行物料及图纸文件夹对象,请检查!", "提示", MessageBox.INFORMATION);
							return;
						}
						System.out.println("folder===>"+folder);
						//判断工程项目项目文档文件夹下面是不是有工程物料文件夹对象
						TCComponent[] folders = folder.getRelatedComponents("contents");
						if(folders!=null && folders.length>0){
							for (int i = 0; i < folders.length; i++) {
								//获取到工程物料的文件夹对象
								if(type.equals(folders[i].getType().toString()) && folders[i].getProperty("object_name").toString().contains(folder_name)){
									flag4 = true;
									target_folder = (TCComponentFolder) folders[i];
									break;
								}
							}
						}
						if(!flag4){
							progressbar.stopBar();
							MessageBox.post("您选择的对象下面没有订单行物料及图纸文件夹对象,请检查!", "提示", MessageBox.INFORMATION);
							return;
						}
						TCComponentForm master_form = (TCComponentForm) item.getRelatedComponent(releation1);
						formProperties = master_form.getFormTCProperties(tablePropertyName);
						int propertySize = formProperties.length;
						//取form里面的有关订单属性值
//						Vector<String[]> v_all = new Vector<String[]>();
						for (int i = 0; i < propertySize; i++) {
							String propertyName = formProperties[i] == null ? "" : formProperties[i].getPropertyName();
							if (propertyName.equals("s4coding1")) {
								Coding_array = formProperties[i].getStringValueArray();
							} else if (propertyName.equals("s4discriptions1")) {
								Discriptions_array = formProperties[i].getStringValueArray();
							} else if (propertyName.equals("s4vdc1")) {
								Vdc_array = formProperties[i].getStringValueArray();
							} else if (propertyName.equals("s4ddcc1")) {
								Ddcc_array = formProperties[i].getStringValueArray();
							} else if (propertyName.equals("s4babintasize1")) {
								Babintasize_array = formProperties[i].getStringValueArray();
							} else if (propertyName.equals("s4declass")) {
								Declass_array = formProperties[i].getStringValueArray();
							} else if (propertyName.equals("s4remarkss")) {
								Remarkss_array = formProperties[i].getStringValueArray();
							} else if (propertyName.equals("s4Maternum")) {
								Maternum_array = formProperties[i].getStringValueArray();
							} else if (propertyName.equals("s4Saleo")) {
								Saleo_array = formProperties[i].getStringValueArray();
							}
						}
						int k = Coding_array.length;
						if(k>0){
							boolean flag = false;
							boolean flag1 = false;
							boolean flag2 = false;
							Vector<String> v_1 = new Vector<String>();
							Vector<String> v_2 = new Vector<String>();
							//先判断订单行物料有没有全部创建
							for (int i = 0; i < k; i++) {
								String item_id = Coding_array[i];
								InterfaceAIFComponent[] items = query(item_id);
								if(items == null || items.length==0){
									flag1 = true;
								}else{
									v_2.add(item_id);
									flag2 = true;
								}
							}
							//如果flag1为false并且flag2为true,表示合同下订单行物料在系统中全部存在,则不需要创建,也不要创建文件夹了
							if(flag2 && !flag1){
								progressbar.stopBar();
								MessageBox.post(v_2+"产品编码清单上的订单物料都已经存在,请检查!", "提示", MessageBox.INFORMATION);
								return;
							}else{
								/*if(flag3){
									folder = createFolder(type, folder_name, item);
								}*/
								//创建ITEM并赋值
								for (int i = 0; i < k; i++) {
									String item_id = Coding_array[i];
									String item_ms = Discriptions_array[i];
									String form_eddy = Vdc_array[i];
									String form_eddl = Ddcc_array[i];
									String form_yscc = Babintasize_array[i];
									String form_xqfl = Declass_array[i];
									String form_bz = Remarkss_array[i];
									String form_wlsl = Maternum_array[i];
									String form_xsdj = Saleo_array[i];
									InterfaceAIFComponent[] items = query(item_id);
									if(items == null || items.length==0){
										TCComponentItem dditem = createItem(item_id, item_ms,item_ms);
										//给form赋值
										setFormProperties(dditem,item_ms,form_eddy,form_eddl,form_yscc,form_xqfl,form_bz,form_wlsl,form_xsdj);
										target_folder.add("contents", dditem);
									}else{
										flag = true;
										v_1.add(item_id);
										continue;
									}
								}
								/*for (int i = 0; i < vec_dd.size(); i++) {
									String []str = vec_dd.get(i);
									String item_id = str[0];
									InterfaceAIFComponent[] items = query(item_id);
									if(items == null || items.length==0){
										TCComponentItem dditem = createItem(item_id, str[1],str[1]);
										//给form赋值
										setFormProperties(dditem,str[1],str[2],str[3],str[4]);
										folder.add("contents", dditem);
									}else{
										flag = true;
										v_1.add(item_id);
										continue;
									}
								}*/
								if(flag){
									progressbar.stopBar();
									MessageBox.post(v_1+"这些订单物料已经存在请检查,其他订单行物料成功创建!", "提示", MessageBox.INFORMATION);
									return;
								}else{
									progressbar.stopBar();
									MessageBox.post("订单行物料成功创建!", "提示", MessageBox.INFORMATION);
								}
							}
						}else{
							progressbar.stopBar();
							MessageBox.post("您选择合同对象里没有订单行物料数据,请检查!", "提示", MessageBox.INFORMATION);
							return;
						}
					}else{
						progressbar.stopBar();
						MessageBox.post("请选择合同对象后重试!", "提示", MessageBox.INFORMATION);
						return;
					}
				}else{
					progressbar.stopBar();
					MessageBox.post("您对该对象没有操作权限，请重新选择!", "提示", MessageBox.INFORMATION);
					return;
				}
			} catch (TCException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/*
	 * 给创建出来的订单下面版本表单上面赋值
	 * */
	private void setFormProperties(TCComponentItem item,String ms,String eddy,String eddl,String yscc,String xqfl,String bz,String wlsl,String xsdj) {
		try {
			item.setProperty(attri, zffl_str);
			TCComponentItemRevision item_rev = item.getLatestItemRevision();
			item_rev.setProperty(attri, zffl_str);
			TCComponentForm form = (TCComponentForm) item_rev.getRelatedComponent(releation2);
			if(form!=null){
				ddformProperties = form.getAllFormProperties();
				int k = ddformProperties.length;
				for (int i = 0; i < k; i++) {
					String str = ddformProperties[i].getPropertyName();
					if(str.equals("s4Cpdiscri")){
						ddformProperties[i].setStringValueData(ms);
	    			} else if(str.equals("s4RDCvoltage")){
	    				ddformProperties[i].setStringValueData(eddy);
	    			} else if(str.equals("s4raccurrent")){
	    				ddformProperties[i].setStringValueData(eddl);
	    			} else if(str.equals("s4tinct")){
	    				ddformProperties[i].setStringValueData(yscc);
	    			} else if(str.equals("s4declass")){
	    				ddformProperties[i].setStringValueData(xqfl);
	    			} else if(str.equals("s4remarkss")){
	    				ddformProperties[i].setStringValueData(bz);
	    			} else if(str.equals("s4Maternum")){
	    				ddformProperties[i].setStringValueData(wlsl);
	    			} else if(str.equals("s4Saleo")){
	    				ddformProperties[i].setStringValueData(xsdj);
	    			} 
				}
				form.setTCProperties(ddformProperties);
			}else{
				System.out.println("创建出来的订单版本下面没有form");
			}
		} catch (TCException e) {
			e.printStackTrace();
			progressbar.stopBar();
			MessageBox.post("设置Form属性出错,错误信息为:"+e.getMessage(), "提示", MessageBox.INFORMATION);
			return;
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
	
	
	/**
	 * 创建Item
	 * 
	 * */
	public TCComponentItem createItem(String id,String name,String ds) {
		try {
			TCComponentItemType itemtype = (TCComponentItemType) session.getTypeComponent(str_type);
			TCComponentItem item = itemtype.create(id, "",
					str_type, name, ds, null);
			return item;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}

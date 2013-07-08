package com.origin.rac.sac.sendgylxandbominfo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import cn.com.origin.util.OracleConnect;
import cn.com.origin.util.ProgressBarThread;
import cn.com.origin.util.ReadProperties;

import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentBOMViewRevision;
import com.teamcenter.rac.kernel.TCComponentBOMWindow;
import com.teamcenter.rac.kernel.TCComponentBOMWindowType;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCProperty;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class BatchSendGylxAndBomCommand extends AbstractAIFCommand {

	private InterfaceAIFComponent[] targets = null;
	private AbstractAIFUIApplication application = null;
	private String relation = "IMAN_master_form_rev";
	private String relation1 = "structure_revisions";
	private String relation2 = "IMAN_specification";
	private String relation3 = "contents";
	private String relation4 = "S4MZ0";
	private TCSession session = null;
	private String attri = "s4Passing_State";
	private String attri1 = "s4Tranlogo";
	private String gylxform_type = "S4GYLUPZ";
	private boolean flag_lx = false;//用来标识选择的对象是不是物料对象
	private boolean flag_2 = false;//用来标识选择的对象是不是ITEM
	private boolean flag_3 = false;//用来标识选择的对象是不是文件夹
	private boolean flag_sfdd = false;//用来标识选择的对象是不是订单行物料
	private boolean flag_sffb = false;//用来选择的订单物料是不是有BOMVIEW
	private boolean flag_view = false;//用来选择的订单物料是不是有BOMVIEW
	private boolean flag_haveview = false;//用来选择的订单物料的BOMVIEW是不是有BOM
	private boolean flag_f_hydd = false;//用来标识文件夹下是否有订单物料
	private boolean flag_f_sfdd = false;//用来标识文件夹下是否订单物料
	private boolean flag_f_sfdd1 = false;//用来标识文件夹下是否订单物料
	private boolean flag_f_sffb = false;//用来标识文件夹下订单物料是否发布
	private boolean flag_f_view = false;//用来标识文件夹下订单物料是不是有BOMVIEW
	private boolean flag_f_haveview = false;//用来标识文件夹下订单物料的BOMVIEW是不是有BOM
	private boolean flag_desc_cd = false;//用来标识选择的订单物料是不是已经传递过ERP
	private boolean flag_vtp_cd = false;//用来标识选择的订单物料的BOMVIEW类型是不是S4MZ0
	private boolean flag_f_desc_cd = false;//用来标识选择文件夹下的订单物料是不是已经传递过ERP
	private boolean flag_f_vtp_cd = false;//用来标识选择文件夹下的订单物料的BOMVIEW类型是不是S4MZ0
	private boolean flag1 = false;//用来标识选择的物料的子BOM（有bomview）是不是已经传递过ERP
	private boolean flag_f_1 = false;//用来标识选择文件夹下的物料的子BOM（有bomview）是不是已经传递过ERP
	private boolean flag2 = false;//用来标识选择的物料是不是传递过ERP
	private boolean flag_f_2 = false;//用来标识选择文件夹下择的物料是不是传递过ERP
	private boolean flag_bt = false;//用来标识选择物料的子BOM上的必填属性
	private boolean flag_f_bt = false;//用来标识选择文件夹下物料的子BOM上的必填属性
	private boolean flag3 = false;//用来标识选择的物料在中间表中是不是已经存在
	private boolean flag4 = false;//用来标识选择物料的工艺路线在中间表中是不是已经存在
	private boolean flag6 = false;//用来标识选择的BOM及子结构ID所在组织在ERP中有没有
	private boolean flag_gylx = false;//用来标识选择的订单物料是不是配置了工艺路线
	private boolean flag_gylx1= false;//用来标识选择的订单物料是不是配置了工艺路线
	private boolean flag_gylx_cd= false;//用来标识选择的订单物料是不是已经传递
	private boolean flag_f_gylx_cd= false;//用来标识选择的订单物料是不是已经传递
	private boolean flag_f_gylx = false;//用来标识选择文件夹下的订单物料是不是配置了工艺路线
	private boolean flag_f_gylx1 = false;//用来标识选择文件夹下的订单物料是不是配置了工艺路线
	private boolean flag_cdgycg = false;//用来标识是否成功传递了工艺路线
	private boolean flag_cdbomcg = false;//用来标识是否成功传递了BOM
	private String yxbom = "N";
	public Connection conn = null;
	public ReadProperties read = null;
	public Statement stmt = null;
	public ResultSet reset = null;
	private Vector<String> vec_1 = new Vector<String>();
	private Vector<String> vec_f_1 = new Vector<String>();
	private Vector<String> vec_2 = new Vector<String>();
	private Vector<String> vec_f_2 = new Vector<String>();
	private Vector<String> vec_sfdd = new Vector<String>();
	private Vector<String> vec_f_sfdd = new Vector<String>();
	private Vector<String> vec_desc_cd = new Vector<String>();
	private Vector<String> vec_vtp_cd = new Vector<String>();
	private Vector<String> vec_f_desc_cd = new Vector<String>();
	private Vector<String> vec_f_vtp_cd = new Vector<String>();
	private Vector<String> vec_sffb = new Vector<String>();
	private Vector<String> vec_f_sffb = new Vector<String>();
	private Vector<String> vec_view = new Vector<String>();
	private Vector<String> vec_haveview = new Vector<String>();
	private Vector<String> vec_f_view = new Vector<String>();
	private Vector<String> vec_f_haveview = new Vector<String>();
	private Vector<String> vec_bt = new Vector<String>();
	private Vector<String> vec_f_bt = new Vector<String>();
	private Vector<String> vec_3 = new Vector<String>();
	private Vector<String> vec_4 = new Vector<String>();
	private Vector<String> vec_ID = new Vector<String>();//用来检查选择BOM及单层子结构ID所在的组织在ERP中是否存在
	private Vector<String> vec_error_ID = new Vector<String>();//用来提示选择的BOM及单层子结构ID在搜索到的组织里面不存在的ID
	private Vector<String> vec_gylx = new Vector<String>();//用来存储没有配置工艺路线的ID
	private Vector<String> vec_gylx1 = new Vector<String>();//用来存储没有配置工艺路线的ID
	private Vector<String> vec_f_gylx = new Vector<String>();//用来存储没有配置工艺路线的ID
	private Vector<String> vec_f_gylx1 = new Vector<String>();//用来存储没有配置工艺路线的ID
	private Vector<String> vec_form_type = new Vector<String>();//用来存储选择订单行物料版本下面的类型
	private Vector<String> vec_f_form_type = new Vector<String>();//用来存储选择订单行物料版本下面的类型
	private Vector<String> vec_gylx_cd = new Vector<String>();//用来存储选择订单行物料工艺路线和BOM是不是都已经传递
	private Vector<String> vec_f_gylx_cd = new Vector<String>();//用来存储选择文件夹下订单行物料工艺路线和BOM是不是都已经传递
	private String[] properties = {"bl_sequence_no","S4masteroper","bl_quantity","S4ATTRIBUTE7","S4ATTRIBUTE8",
								"S4ATTRIBUTE9","S4ATTRIBUTE11","S4ATTRIBUTE10","S4ATTRIBUTE12","S4MEANING","S4SUPPLY_SUBIN","S4component_rem"};
	private ProgressBarThread progressbar = null ;
	private String userName = "";//申请人
	private String error_cd = "N";
	private String zzdm = "MZ0";
	private String check_item_type = "S4CP";
	private TCProperty[] formProperties = null;
	private String[] propertyNames = {"object_name","s4opernumber","s4operationcode","s4department","s4texture","s4Childstock","s4cargo_s"};
	private String[] Opernumber_array,Operationcode_array,Department_array,Texture_array;
	
	public BatchSendGylxAndBomCommand(AbstractAIFUIApplication app) {
		application =app;
		session = (TCSession) application.getSession();
		targets = (InterfaceAIFComponent[]) application.getTargetComponents();
		System.out.println("targets==2222=>:"+targets.length);
		//执行菜单的人员
		userName = session.getUser().toString();
//		int m = user.indexOf("(");
//		userName = user.substring(0, m);
		if(targets.length>=1){
			try {
				progressbar = new ProgressBarThread("工程BOM及工艺路线批量传递" ,"工程BOM及工艺路线批量传递中,请稍等...");
				progressbar.start();
				
				for (int i = 0; i < targets.length; i++) {
					if(!(targets[i] instanceof TCComponentItem) && !(targets[i] instanceof TCComponentFolder) && !(targets[i] instanceof TCComponentItemRevision)){
						flag_lx = true;
					}
					if(targets[i] instanceof TCComponentItem || targets[i] instanceof TCComponentItemRevision){
						flag_2 = true;
						TCComponentItem item = null;
						TCComponentItemRevision item_rev =null;
						String viewtype = "";
						if(targets[i] instanceof TCComponentItem){
							item = (TCComponentItem) targets[i];
							item_rev = item.getLatestItemRevision();
							System.out.println("item_rev-888888888---->:"+item_rev);
						}else if(targets[i] instanceof TCComponentItemRevision){
							item_rev = (TCComponentItemRevision) targets[i];
							item = item_rev.getItem();
							System.out.println("item_rev---9999---->:"+item_rev);
						}
						String erro_id = item.getProperty("item_id");
						if(!check_item_type.equals(item.getType().toString())){
							flag_sfdd = true;
							if(!vec_sfdd.contains(erro_id)){
								vec_sfdd.add(erro_id);
							}
						}else{
							//如果选择的对象是订单行物料对象,则先判断有没有BOMVIEW
							TCComponentBOMViewRevision sel_bomview = (TCComponentBOMViewRevision) item_rev.getRelatedComponent(relation1);
							if(sel_bomview==null){
								flag_view = true;
								if(!vec_view.contains(erro_id)){
									vec_view.add(erro_id);
								}
							}else{
								TCComponent[] components = item_rev.getRelatedComponents(relation4);
								if(components==null || components.length ==0){
									flag_haveview = true;
									if(!vec_haveview.contains(erro_id)){
										vec_haveview.add(erro_id);
									}
								}
								String sel_viewtype = sel_bomview.toString();
								viewtype =  sel_viewtype.substring(sel_viewtype.length()-5, sel_viewtype.length());
								System.out.println("viewtype====>:"+viewtype);
								if(!"S4MZ0".equals(viewtype)){
									flag_vtp_cd = true;
									if(!vec_vtp_cd.contains(erro_id)){
										vec_vtp_cd.add(erro_id);
									}
								}
							}
							//如果选择的对象是订单行物料对象,则取判断是不是发布
							String str_status = item_rev.getProperty("release_status_list");
							//判断物料是否发布
							if("".equals(str_status) || str_status==null){
								flag_sffb = true;
								if(!vec_sffb.contains(erro_id)){
									vec_sffb.add(erro_id);
								}
							}
							//判断选择BOM下面是不是配置了工艺路线
							TCComponent[] forms = item_rev.getRelatedComponents(relation2);
							if(forms==null || forms.length==0){
								flag_gylx = true;
								if(!vec_gylx.contains(erro_id)){
									vec_gylx.add(erro_id);
								}
							}else{
								for (int j = 0; j < forms.length; j++) {
									String type = forms[j].getType().toString();
									if(!vec_form_type.contains(type)){
										vec_form_type.add(type);
									}
								}
								if(!vec_form_type.contains(gylxform_type)){
									flag_gylx1 = true;
									if(!vec_gylx1.contains(erro_id)){
										vec_gylx1.add(erro_id);
									}
								}
							}
						}
					}else if(targets[i] instanceof TCComponentFolder){
						//标识是不是选择的文件夹对象
						flag_3 = true;
						TCComponentFolder folder = (TCComponentFolder) targets[i];
						TCComponent[] coms = folder.getRelatedComponents(relation3);
						//判断选择的文件夹下是不是有对象
						if (coms != null && coms.length > 0) {
							for (int j = 0; j < coms.length; j++) {
								//判断选择的文件夹下是不是订单行物料对象
								if(coms[j] instanceof TCComponentItem){
									TCComponentItem item = (TCComponentItem) coms[j];
									String erro_f_id = item.getProperty("item_id");
									if(!check_item_type.equals(item.getType().toString())){
										flag_f_sfdd = true;
										if(!vec_f_sfdd.contains(erro_f_id)){
											vec_f_sfdd.add(erro_f_id);
										}
									}else{
										TCComponentItemRevision item_rev = item.getLatestItemRevision();
										//如果选择的对象是订单行物料对象,则先判断有没有BOMVIEW
										TCComponentBOMViewRevision sel_bomview = (TCComponentBOMViewRevision) item_rev.getRelatedComponent(relation1);
										if(sel_bomview==null){
											flag_f_view = true;
											if(!vec_f_view.contains(erro_f_id)){
												vec_f_view.add(erro_f_id);
											}
										}else{
											TCComponent[] components = item_rev.getRelatedComponents(relation4);
											if(components==null || components.length ==0){
												flag_f_haveview = true;
												if(!vec_f_haveview.contains(erro_f_id)){
													vec_f_haveview.add(erro_f_id);
												}
											}
											String sel_viewtype = sel_bomview.toString();
											String viewtype =  sel_viewtype.substring(sel_viewtype.length()-5, sel_viewtype.length());
											System.out.println("viewtype====>:"+viewtype);
											if(!"S4MZ0".equals(viewtype)){
												flag_f_vtp_cd = true;
												if(!vec_f_vtp_cd.contains(erro_f_id)){
													vec_f_vtp_cd.add(erro_f_id);
												}
											}
										}
										//如果选择的对象是订单行物料对象,则取判断是不是发布
										String str_status = item_rev.getProperty("release_status_list");
										//判断物料是否发布
										if("".equals(str_status) || str_status==null){
											flag_f_sffb = true;
											if(!vec_f_sffb.contains(erro_f_id)){
												vec_f_sffb.add(erro_f_id);
											}
										}
										//判断选择BOM下面是不是配置了工艺路线
										TCComponent[] forms = item_rev.getRelatedComponents(relation2);
										if(forms==null || forms.length==0){
											flag_f_gylx = true;
											if(!vec_f_gylx.contains(erro_f_id)){
												vec_f_gylx.add(erro_f_id);
											}
										}else{
											for (int k = 0; k < forms.length; k++) {
												String type = forms[k].getType().toString();
												if(!vec_f_form_type.contains(type)){
													vec_f_form_type.add(type);
												}
											}
											if(!vec_f_form_type.contains(gylxform_type)){
												flag_f_gylx1 = true;
												if(!vec_f_gylx1.contains(erro_f_id)){
													vec_f_gylx1.add(erro_f_id);
												}
											}
										}
									}
								}else{
									//标识选择的文件夹下是不是订单行物料对象
									flag_f_sfdd1 = true;
								}
							}
						}else{
							//标识选择的文件夹下是不是有订单行物料
							flag_f_hydd = true;
						}
					}
				}
				if(flag_lx){
					progressbar.stopBar();
					MessageBox.post("请选择订单行物料对象或者文件夹对象!", "提示", MessageBox.INFORMATION);
					return;
				}
				//判断选择的对象不能既是文件夹对象又是item对象
				if(flag_2 && flag_3){
					progressbar.stopBar();
					MessageBox.post("请选择订单行物料对象或者文件夹对象!", "提示", MessageBox.INFORMATION);
					return;
				}
				if(flag_f_hydd){
					progressbar.stopBar();
					MessageBox.post("您选择的文件夹下面没有订单行物料对象!", "提示", MessageBox.INFORMATION);
					return;
				}
				//判断选择的对象是不是订单行物料对象
				if(flag_sfdd){
					progressbar.stopBar();
					MessageBox.post("您选择对象"+ vec_sfdd +"不是订单行物料对象,请重新选择!", "提示", MessageBox.INFORMATION);
					return;
				}
				//判断选择文件夹下面是不是订单行物料
				if(flag_f_sfdd){
					progressbar.stopBar();
					MessageBox.post("您选择文件夹对象下"+ vec_f_sfdd +"不是订单行物料对象,请重新选择!", "提示", MessageBox.INFORMATION);
					return;
				}
				//判断选择文件夹下面是不是订单行物料
				if(flag_f_sfdd1){
					progressbar.stopBar();
					MessageBox.post("您选择文件夹对象下不是订单行物料对象,请重新选择!", "提示", MessageBox.INFORMATION);
					return;
				}
				
				//判断选择的订单行物料下面有没有BOMVIEW
				if(flag_view){
					progressbar.stopBar();
					MessageBox.post("您选择对象"+ vec_view +"没有BOM结构,请重新选择!", "提示", MessageBox.INFORMATION);
					return;
				}
				//判断选择的订单行物料下面的BOMVIEW是不是空的BOMVIEW
				if(flag_haveview){
					progressbar.stopBar();
					MessageBox.post("您选择对象"+ vec_haveview +"BOM结构为空,请重新选择!", "提示", MessageBox.INFORMATION);
					return;
				}
				
				//判断选择的BOM的视图类型是不是S4MZ0
				if(flag_vtp_cd){
					progressbar.stopBar();
					MessageBox.post("您选择的"+vec_vtp_cd+"不是MZ0组织,请选择MZ0组织的BOM进行传递!", "提示", MessageBox.INFORMATION);
					return;
				}
				
				//判断选择的订单行物料是不是发布
				if(flag_sffb){
					progressbar.stopBar();
					MessageBox.post("您选择对象"+ vec_sffb +"没有发布,请重新选择!", "提示", MessageBox.INFORMATION);
					return;
				}
				//判断选择文件夹下的订单行物料下面有没有BOMVIEW
				if(flag_f_view){
					progressbar.stopBar();
					MessageBox.post("您选择文件夹对象下"+ vec_f_view +"没有BOM结构,请重新选择!", "提示", MessageBox.INFORMATION);
					return;
				}
				//判断选择文件夹下的订单行物料下面的BOMVIEW是不是空的BOMVIEW
				if(flag_f_haveview){
					progressbar.stopBar();
					MessageBox.post("您选择文件夹对象下"+ vec_f_haveview +"BOM结构为空,请重新选择!", "提示", MessageBox.INFORMATION);
					return;
				}
				
				//判断选择文件夹下的BOM的视图类型是不是S4MZ0
				if(flag_f_vtp_cd){
					progressbar.stopBar();
					MessageBox.post("您选择的"+vec_f_vtp_cd+"不是MZ0组织,请选择MZ0组织的BOM进行传递!", "提示", MessageBox.INFORMATION);
					return;
				}
				
				//判断选择文件夹下的订单行物料是不是发布
				if(flag_f_sffb){
					progressbar.stopBar();
					MessageBox.post("您选择文件夹对象下"+ vec_f_sffb +"没有发布,请重新选择!", "提示", MessageBox.INFORMATION);
					return;
				}
				
				//判断选择的BOM是不是已经配置过工艺路线
				if(flag_gylx){
					progressbar.stopBar();
					MessageBox.post("您选择的"+vec_gylx+"没有配置工艺路线,请检查!", "提示", MessageBox.INFORMATION);
					return;
				}
				//判断选择的BOM是不是已经配置过工艺路线
				if(flag_gylx1){
					progressbar.stopBar();
					MessageBox.post("您选择的"+vec_gylx1+"没有配置工艺路线,请检查!", "提示", MessageBox.INFORMATION);
					return;
				}
				
				//判断选择文件夹下的BOM是不是已经配置过工艺路线
				if(flag_f_gylx){
					progressbar.stopBar();
					MessageBox.post("您选择的"+vec_f_gylx+"没有配置工艺路线,请检查!", "提示", MessageBox.INFORMATION);
					return;
				}
				//判断选择文件夹下的BOM是不是已经配置过工艺路线
				if(flag_f_gylx1){
					progressbar.stopBar();
					MessageBox.post("您选择的"+vec_f_gylx1+"没有配置工艺路线,请检查!", "提示", MessageBox.INFORMATION);
					return;
				}
				
				//如果以上条件都符合,则进行BOM传递前的检查工作,和BOM传递检查逻辑一样
				checkSendBOMInfo();
				
				//判断工艺路线和BOM是不是都已经传递
				if(flag_gylx_cd && flag_desc_cd){
					progressbar.stopBar();
					MessageBox.post("您选择的"+vec_gylx_cd+"BOM已经成功传递,请检查!", "提示", MessageBox.INFORMATION);
					return;
				}
				
				//判断选择文件夹下的工艺路线和BOM是不是都已经传递
				if(flag_f_gylx_cd && flag_f_desc_cd){
					progressbar.stopBar();
					MessageBox.post("您选择的"+vec_f_gylx_cd+"BOM已经成功传递,请检查!", "提示", MessageBox.INFORMATION);
					return;
				}
				
				//判断选择的BOM是不是已经传递过ERP
				if(flag_desc_cd){
					progressbar.stopBar();
					MessageBox.post("您选择的BOM"+vec_desc_cd+"已经传递过ERP,不允许再次传递,请检查!", "提示", MessageBox.INFORMATION);
					return;
				}
				//判断选择的BOM及下面的子是不是物料已经传递过ERP
				if(flag2){
					progressbar.stopBar();
					MessageBox.post(vec_2+"这些物料在ERP中没有创建,请先传递该物料后再传递BOM!", "提示", MessageBox.INFORMATION);
					return;
				}
				//判断选择的BOM下面子BOM(有BOM结构)的是不是已经传递过ERP
				if(flag1){
					progressbar.stopBar();
					MessageBox.post("请先将"+vec_1+"的BOM传递至ERP后再尝试传递该BOM!", "提示", MessageBox.INFORMATION);
					return;
				}
				
				//判断选择文件夹下的BOM是不是已经传递过ERP
				if(flag_f_desc_cd){
					progressbar.stopBar();
					MessageBox.post("您选择的BOM"+vec_f_desc_cd+"已经传递过ERP,不允许再次传递,请检查!", "提示", MessageBox.INFORMATION);
					return;
				}
				//判断选择文件夹下的BOM及下面的子是不是物料已经传递过ERP
				if(flag_f_2){
					progressbar.stopBar();
					MessageBox.post(vec_f_2+"这些物料在ERP中没有创建，请先传递该物料后再传递BOM!", "提示", MessageBox.INFORMATION);
					return;
				}
				//判断选择文件夹下的BOM下面子BOM(有BOM结构)的是不是已经传递过ERP
				if(flag_f_1){
					progressbar.stopBar();
					MessageBox.post("请先将"+vec_f_1+"的BOM传递至ERP后再尝试传递该BOM!", "提示", MessageBox.INFORMATION);
					return;
				}
				
				flag6 = isInOrganization(vec_ID,"MZ0");
				if(flag6){
					progressbar.stopBar();
					MessageBox.post(vec_error_ID+"所在的组织在ERP中没有分配,请检查!", "提示", MessageBox.INFORMATION);
					return;
				}
				
				
				//判断选择的BOM下面的子结构上的必填属性
				if(flag_bt){
					progressbar.stopBar();
					MessageBox.post("选择的"+vec_bt+"数量属性需要维护后方能传递,请检查!", "提示", MessageBox.INFORMATION);
					return;
				}
				//判断选择的BOM下面的子结构上的必填属性
				if(flag_f_bt){
					progressbar.stopBar();
					MessageBox.post("选择文件夹下的"+vec_f_bt+"数量属性需要维护后方能传递,请检查!", "提示", MessageBox.INFORMATION);
					return;
				}
				//判断选择订单行物料的工艺路线在中间表中是不是已经存在
				if(flag4){
					progressbar.stopBar();
					MessageBox.post(vec_4+"BOM已经存在中间表,等待ERP接受!", "提示", MessageBox.INFORMATION);
					return;
				}
				
				//判断选择的订单行物料在中间表中是不是已经存在
				if(flag3){
					progressbar.stopBar();
					MessageBox.post(vec_3+"BOM已经存在中间表,等待ERP接受!", "提示", MessageBox.INFORMATION);
					return;
				}
				
				//如果以上条件都符合,则将工艺路线传递到ERP
				for (int i = 0; i < targets.length; i++) {
					if(targets[i] instanceof TCComponentItem || targets[i] instanceof TCComponentItemRevision){
						TCComponentItem item = null;
						TCComponentItemRevision item_rev =null;
						if(targets[i] instanceof TCComponentItem){
							item = (TCComponentItem) targets[i];
							item_rev = item.getLatestItemRevision();
						}else if(targets[i] instanceof TCComponentItemRevision){
							item_rev = (TCComponentItemRevision) targets[i];
							item = item_rev.getItem();
						}
						System.out.println("==传递工艺路线==选择物料==");
						String sel_id = item.getProperty("item_id").toString();
						System.out.println("sel_id----------选择物料----------->:"+sel_id);
						TCComponentForm gylx_form = null;
						//得到工艺路线配置的FORM
						TCComponent[] forms = item_rev.getRelatedComponents(relation2);
						for (int j = 0; j < forms.length; j++) {
							if(gylxform_type.equals(forms[j].getType().toString())){
								gylx_form = (TCComponentForm) forms[j];
							}
						}
						if(gylx_form!=null){
							String tranlogo = gylx_form.getProperty(attri1).toString();
							if(tranlogo.equals("Y")){
								continue;
							}
						}
						//将工艺路线数据插入到中间表
						insertGYLXDataToTable(sel_id,gylx_form);
					}else if(targets[i] instanceof TCComponentFolder){
						TCComponentFolder folder = (TCComponentFolder) targets[i];
						System.out.println("==================传递工艺路线==选择文件夹=================");
						TCComponent[] coms = folder.getRelatedComponents(relation3);
						for (int j = 0; j < coms.length; j++) {
							TCComponentItem item = (TCComponentItem) coms[j];
							String sel_id = item.getProperty("item_id").toString();
							System.out.println("sel_id===========选择文件夹============>:"+sel_id);
							TCComponentItemRevision item_rev = item.getLatestItemRevision();
							TCComponentForm gylx_form = null;
							//得到工艺路线配置的FORM
							TCComponent[] forms = item_rev.getRelatedComponents(relation2);
							for (int k = 0; k < forms.length; k++) {
								if(gylxform_type.equals(forms[k].getType().toString())){
									gylx_form = (TCComponentForm) forms[k];
								}
							}
							System.out.println("gylx_form------工艺路线form-------------->:"+gylx_form);
							if(gylx_form!=null){
								String tranlogo = gylx_form.getProperty(attri1).toString();
								if(tranlogo.equals("Y")){
									continue;
								}
							}
							//将工艺路线数据插入到中间表
							insertGYLXDataToTable(sel_id,gylx_form);
						}
					}
				}
				
				//如果以上条件都符合,则将BOM传递到ERP
				for (int i = 0; i < targets.length; i++) {
					if(targets[i] instanceof TCComponentItem || targets[i] instanceof TCComponentItemRevision){
						TCComponentItem item = null;
						TCComponentItemRevision item_rev =null;
						if(targets[i] instanceof TCComponentItem){
							item = (TCComponentItem) targets[i];
							item_rev = item.getLatestItemRevision();
						}else if(targets[i] instanceof TCComponentItemRevision){
							item_rev = (TCComponentItemRevision) targets[i];
							item = item_rev.getItem();
						}
						String sel_id = item.getProperty("item_id").toString();
						//转换BOMLINE
						TCComponentBOMLine sel_bomline = getProBomLine(item_rev);
						AIFComponentContext[] boms=sel_bomline.getChildren();
						//将BOM数据插入到中间表
						insertDataToTable(sel_id,boms);
					}else if(targets[i] instanceof TCComponentFolder){
						TCComponentFolder folder = (TCComponentFolder) targets[i];
						TCComponent[] coms = folder.getRelatedComponents(relation3);
						for (int j = 0; j < coms.length; j++) {
							TCComponentItem item = (TCComponentItem) coms[j];
							String sel_id = item.getProperty("item_id").toString();
							TCComponentItemRevision item_rev = item.getLatestItemRevision();
							//转换BOMLINE
							TCComponentBOMLine sel_bomline = getProBomLine(item_rev);
							AIFComponentContext[] boms=sel_bomline.getChildren();
							//将BOM数据插入到中间表
							insertDataToTable(sel_id,boms);
						}
					}
				}
				progressbar.stopBar();
				if(!flag_cdgycg && !flag_cdbomcg){
					MessageBox.post("传递成功至ERP中间表,等待ERP接收!", "提示", MessageBox.INFORMATION);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			MessageBox.post("请选择正确对象类型操作!", "提示", MessageBox.INFORMATION);
			return;
		}
	}
	
	//如果以上条件都符合,则进行BOM传递前的检查工作,和BOM传递检查逻辑一样
	public void checkSendBOMInfo(){
		try {
			for (int i = 0; i < targets.length; i++) {
				if(targets[i] instanceof TCComponentItem || targets[i] instanceof TCComponentItemRevision){
					TCComponentItem item = null;
					TCComponentItemRevision item_rev =null;
					if(targets[i] instanceof TCComponentItem){
						System.out.println("DDDDDDDDDDDDDDDDD");
						item = (TCComponentItem) targets[i];
						item_rev = item.getLatestItemRevision();
						System.out.println("item_rev----item---->:"+item_rev);
						//BOM传递前的检查工作并检查工艺路线是不是传递过
						checkSelectBOMData(item_rev);
					}else if(targets[i] instanceof TCComponentItemRevision){
						System.out.println("FFFFFFFFFFFFFFFFFFFFFF");
						item_rev = (TCComponentItemRevision) targets[i];
						System.out.println("item_rev======================>:"+item_rev);
						item = item_rev.getItem();
						//BOM传递前的检查工作并检查工艺路线是不是传递过
						checkSelectBOMData(item_rev);
					}
					
					String sel_id = item.getProperty("item_id").toString();
					//判断选择物料的在原表中是不是已经存在
					flag4 = checkSelGYLXTableData(sel_id);
					//判断选择的物料在原表中是不是已经存在
					flag3 = checkSelTableData(sel_id);
				}else if(targets[i] instanceof TCComponentFolder){
					TCComponentFolder folder = (TCComponentFolder) targets[i];
					TCComponent[] coms = folder.getRelatedComponents(relation3);
					for (int j = 0; j < coms.length; j++) {
						TCComponentItem item = (TCComponentItem) coms[j];
						String sel_id = item.getProperty("item_id").toString();
						//BOM传递前的检查工作
						checkFolderBOMData(item);
						//判断选择物料的在原表中是不是已经存在
						flag4 = checkSelGYLXTableData(sel_id);
						//判断选择的物料在原表中是不是已经存在
						flag3 = checkSelTableData(sel_id);
					}
				}
			}
		} catch (TCException e) {
			e.printStackTrace();
		}
	}
	
	
	//进行BOM传递前的检查工作,和BOM传递检查逻辑一样(针对选择的订单行物料 )
	public void checkSelectBOMData(TCComponentItemRevision item_rev){
		try {
			TCComponentForm gylx_form = null;
			String sel_bom_id = item_rev.getItem().getProperty("item_id").toString();
			if(!vec_ID.contains(sel_bom_id)){
				vec_ID.add(sel_bom_id);
			}
//			String sel_bom_name = item.getProperty("object_name").toString();
//			String error_info = sel_bom_id +"-" + sel_bom_name;
			System.out.println("item_rev[=======>:"+item_rev);
			//判断选择BOM下面是不是配置了工艺路线
			TCComponent[] forms = item_rev.getRelatedComponents(relation2);
			for (int i = 0; i < forms.length; i++) {
				String type = forms[i].getType().toString();
				if(gylxform_type.equals(type)){
					gylx_form = (TCComponentForm) forms[i];
				}
			}
			if(gylx_form!=null){
				String tranlogo = gylx_form.getProperty(attri1).toString();
				if(tranlogo.equals("Y")){
					flag_gylx_cd = true;
				}
			}
			TCComponentBOMViewRevision sel_bomview = (TCComponentBOMViewRevision) item_rev.getRelatedComponent(relation1);
			if(sel_bomview!=null){
				String sel_desc = sel_bomview.getProperty("object_desc").toString();
				System.out.println("rev====----*sel_desc====>:"+sel_desc);
				if("Y".equals(sel_desc)){
					flag_desc_cd = true;
					if(!vec_desc_cd.contains(sel_bom_id)){
						vec_desc_cd.add(sel_bom_id);
					}
				}
			}
			if(flag_gylx_cd && flag_desc_cd){
				if(!vec_gylx_cd.contains(sel_bom_id)){
					vec_gylx_cd.add(sel_bom_id);
				}
			}
			/*TCComponentForm form = (TCComponentForm) item_rev.getRelatedComponent(relation);
			String passing_state = form.getProperty(attri).toString();
			//判断选择的BOM它的物料有没有传递至ERP过
			if(!"Y".equals(passing_state)){
				flag2 = true;
				if(!vec_2.contains(error_info)){
					vec_2.add(error_info);
				}
			}*/
			//转换BOMLINE
			TCComponentBOMLine sel_bomline = getProBomLine(item_rev);
			AIFComponentContext[] childs = sel_bomline.getChildren();
			for (int j = 0; j < childs.length; j++) {
				TCComponentBOMLine sub_bomline = (TCComponentBOMLine) childs[j].getComponent();
				String[] values = new String[12];
				values = sub_bomline.getProperties(properties);
				System.out.println("values===0===>:"+values[0]);
				System.out.println("values===1===>:"+values[1]);
				System.out.println("values===2===>:"+values[2]);
				//判断选择的BOM的子BOM上的必填属性
				if(values[2]==null || "".equals(values[2])){
					flag_bt = true;
					if(!vec_bt.contains(sel_bom_id)){
						vec_bt.add(sel_bom_id);
					}
				}
				//得到子BOM
				TCComponentItem sub_bom = sub_bomline.getItem();
				System.out.println("sub_bom---->:"+sub_bom);
				String sub_bom_id = sub_bom.getProperty("item_id").toString();
				if(!vec_ID.contains(sub_bom_id)){
					vec_ID.add(sub_bom_id);
				}
				String sub_bom_name = sub_bom.getProperty("object_name").toString();
				String sub_error_info = sub_bom_id +"-" + sub_bom_name;
				//得到BOM最新版本
				TCComponentItemRevision sub_rev = sub_bom.getLatestItemRevision();
				System.out.println("sub_rev===>;"+sub_rev);
				TCComponentForm sub_form = (TCComponentForm) sub_rev.getRelatedComponent(relation);
				System.out.println("sub_bom=---===>:"+sub_bom);
				String sub_passing_state = sub_form.getProperty(attri).toString();
				//判断子BOM有没有传递至ERP过
				if(!"Y".equals(sub_passing_state)){
					flag2 = true;
					if(!vec_2.contains(sub_error_info)){
						vec_2.add(sub_error_info);
					}
				}
				//得到BOM最新版本的BOMVIEW
				TCComponentBOMViewRevision sub_bomview = (TCComponentBOMViewRevision) sub_rev.getRelatedComponent(relation1);
				System.out.println("rev====----*****===sub_bomview====>:"+sub_bomview);
				//如果BOMVIEW不为空,判断描述的值
				if(sub_bomview!=null){
					String desc = sub_bomview.getProperty("object_desc").toString();
					System.out.println("rev====----*desc====>:"+desc);
					if("".equals(desc) || error_cd.equals(desc)){
						flag1 = true;
						if(!vec_1.contains(sub_bom_id)){
							vec_1.add(sub_bom_id);
						}
					}
				}
			}
		} catch (TCException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//进行BOM传递前的检查工作,和BOM传递检查逻辑一样(针对选择文件夹下的订单行物料)
	public void checkFolderBOMData(TCComponentItem item){
		try {
			TCComponentForm gylx_form = null;
			String sel_bom_id = item.getProperty("item_id").toString();
			if(!vec_ID.contains(sel_bom_id)){
				vec_ID.add(sel_bom_id);
			}
//			String sel_bom_name = item.getProperty("object_name").toString();
//			String error_info = sel_bom_id +"-" + sel_bom_name;
			System.out.println("sel_bom===>:"+item);
			TCComponentItemRevision item_rev = item.getLatestItemRevision();
			//判断选择BOM下面是不是配置了工艺路线
			TCComponent[] forms = item_rev.getRelatedComponents(relation2);
			for (int i = 0; i < forms.length; i++) {
				String type = forms[i].getType().toString();
				if(gylxform_type.equals(type)){
					gylx_form = (TCComponentForm) forms[i];
				}
			}
			if(gylx_form!=null){
				String tranlogo = gylx_form.getProperty(attri1).toString();
				if(tranlogo.equals("Y")){
					flag_f_gylx_cd = true;
				}
			}
			
			
			TCComponentBOMViewRevision sel_bomview = (TCComponentBOMViewRevision) item_rev.getRelatedComponent(relation1);
			System.out.println("====sel_bomview====>:"+sel_bomview);
			if(sel_bomview!=null){
				String sel_desc = sel_bomview.getProperty("object_desc").toString();
				System.out.println("rev====----*sel_desc====>:"+sel_desc);
				if("Y".equals(sel_desc)){
					flag_f_desc_cd = true;
					if(!vec_f_desc_cd.contains(sel_bom_id)){
						vec_f_desc_cd.add(sel_bom_id);
					}
				}
			}
			if(flag_f_gylx_cd && flag_f_desc_cd){
				if(!vec_f_gylx_cd.contains(sel_bom_id)){
					vec_f_gylx_cd.add(sel_bom_id);
				}
			}
			
			/*TCComponentForm form = (TCComponentForm) item_rev.getRelatedComponent(relation);
			System.out.println("form===>:"+form);
			String passing_state = form.getProperty(attri).toString();
			//判断选择的BOM它的物料有没有传递至ERP过
			if(!"Y".equals(passing_state)){
				flag_f_2 = true;
				if(!vec_f_2.contains(error_info)){
					vec_f_2.add(error_info);
				}
			}*/
			//转换BOMLINE
			TCComponentBOMLine sel_bomline = getProBomLine(item_rev);
			AIFComponentContext[] childs = sel_bomline.getChildren();
			for (int j = 0; j < childs.length; j++) {
				TCComponentBOMLine sub_bomline = (TCComponentBOMLine) childs[j].getComponent();
				System.out.println("sub_bomline---->:"+sub_bomline);
				String[] values = new String[12];
				values = sub_bomline.getProperties(properties);
				System.out.println("values===0===>:"+values[0]);
				System.out.println("values===1===>:"+values[1]);
				System.out.println("values===2===>:"+values[2]);
				//判断选择的BOM的子BOM上的必填属性
				if(values[2]==null || "".equals(values[2])){
					flag_f_bt = true;
					if(!vec_f_bt.contains(sel_bom_id)){
						vec_f_bt.add(sel_bom_id);
					}
				}
				//得到子BOM
				TCComponentItem sub_bom = sub_bomline.getItem();
				System.out.println("sub_bom---->:"+sub_bom);
				String sub_bom_id = sub_bom.getProperty("item_id").toString();
				if(!vec_ID.contains(sub_bom_id)){
					vec_ID.add(sub_bom_id);
				}
				String sub_bom_name = sub_bom.getProperty("object_name").toString();
				String sub_error_info = sub_bom_id +"-" + sub_bom_name;
				//得到BOM最新版本
				TCComponentItemRevision sub_rev = sub_bom.getLatestItemRevision();
				System.out.println("sub_rev===>;"+sub_rev);
				TCComponentForm sub_form = (TCComponentForm) sub_rev.getRelatedComponent(relation);
				System.out.println("sub_bom=---===>:"+sub_bom);
				String sub_passing_state = sub_form.getProperty(attri).toString();
				//判断子BOM有没有传递至ERP过
				if(!"Y".equals(sub_passing_state)){
					flag_f_2 = true;
					if(!vec_f_2.contains(sub_error_info)){
						vec_f_2.add(sub_error_info);
					}
				}
				//得到BOM最新版本的BOMVIEW
				TCComponentBOMViewRevision sub_bomview = (TCComponentBOMViewRevision) sub_rev.getRelatedComponent(relation1);
				System.out.println("rev====----*****===sub_bomview====>:"+sub_bomview);
				//如果BOMVIEW不为空,判断描述的值
				if(sub_bomview!=null){
					String desc = sub_bomview.getProperty("object_desc").toString();
					System.out.println("rev====----*desc====>:"+desc);
					if("".equals(desc) || error_cd.equals(desc)){
						flag_f_1 = true;
						if(!vec_f_1.contains(sub_bom_id)){
							vec_f_1.add(sub_bom_id);
						}
					}
				}
			}
		} catch (TCException e) {
			e.printStackTrace();
		}
	}
	
	
	//通过选择BOM及子结构ID去检查选择BOM所在的组织在ERP中这个组织下的编码是不是存在
	public boolean isInOrganization(Vector<String> vec_bomid,String zzdm){
		boolean flag_org = false;
		try {
			// 实例化数据库连接
			OracleConnect oraconn = new OracleConnect();
			conn = oraconn.getConnection();
			for (int i = 0; i < vec_bomid.size(); i++) {
				String bomid = vec_bomid.get(i);
				String sql = "select * from CUX_PLM_ORG_ITEM_V where Org_Code = '"+ zzdm + "'" + " and Item_Num = '"+ bomid +"'";
				stmt = conn.createStatement();
				reset = stmt.executeQuery(sql);
				if(reset!=null && reset.next())
				{
					
				}else{
					flag_org = true;
					if(!vec_error_ID.contains(bomid)){
						vec_error_ID.add(bomid);
					}
				}
				stmt.close();
			}
			oraconn.closeConn(conn);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag_org;
	}
	
	//检查选择BOM的工艺路线在中间表中是不是已经存在
	public boolean checkSelGYLXTableData(String sel_id){
		boolean flag_check = false;
		try {
			// 实例化数据库连接
			OracleConnect oraconn = new OracleConnect();
			conn = oraconn.getConnection();
			String sql_gylx_String = "select * from CUX.CUX_PLM_ROUTING_IFACE where ORGANIZATION_CODE='"
				+ "MZ0" +"' and ITEM_NUMBER='" + sel_id +"'";
			System.out.println("【查询语句 】" + sql_gylx_String);
			stmt = conn.createStatement();
			reset = stmt.executeQuery(sql_gylx_String);
			if(reset!=null && reset.next()){
				flag_check = true;
				if(!vec_4.contains(sel_id)){
					vec_4.add(sel_id);
				}
			}
			stmt.close();
			oraconn.closeConn(conn);
			oraconn.closeConn(conn);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag_check;
		
	}
	
	//检查选择的BOM在中间表中是不是已经存在
	public boolean checkSelTableData(String sel_id){
		boolean flag_check = false;
		try {
			// 实例化数据库连接
			OracleConnect oraconn = new OracleConnect();
			//首先判断选择的物料在原表中是不是已经存在
			conn = oraconn.getConnection();
			//sql查询语句
			String sqlString = "select COMPONENT_ITEM from CUX.CUX_PLM_BOM_IFACE where ITEM_NUM='"
				+ sel_id + "'";
			System.out.println("【查询语句】---->" + sqlString);
			stmt = conn.createStatement();
			reset = stmt.executeQuery(sqlString);
			String zijian_id = "";
			if (reset != null && reset.next()) {
				zijian_id = reset.getString("COMPONENT_ITEM");
			}
			stmt.close();
			if(!"".equals(zijian_id) && zijian_id!=null){
				flag_check = true;
				if(!vec_3.contains(sel_id)){
					vec_3.add(sel_id);
				}
			}
			oraconn.closeConn(conn);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag_check;
		
	}
	
	//将工艺路线数据插入到中间表
	public void insertGYLXDataToTable(String sel_id,TCComponentForm form){
		try {
			String formName = "";
			String zhiKuCun = "";
			String huoWei = "";
			String cdhuowei = "";
			formProperties = form.getFormTCProperties(propertyNames);
			int propertySize = formProperties.length;
			for (int i = 0; i < propertySize; i++) {
				String propertyName = formProperties[i] == null ? "" : formProperties[i].getPropertyName();
				if (propertyName.equals("s4opernumber")) {
					Opernumber_array = formProperties[i].getStringValueArray();
				} else if (propertyName.equals("s4operationcode")) {
					Operationcode_array = formProperties[i].getStringValueArray();
				} else if (propertyName.equals("s4department")) {
					Department_array = formProperties[i].getStringValueArray();
				} else if (propertyName.equals("s4texture")) {
					Texture_array = formProperties[i].getStringValueArray();
				} else if (propertyName.equals("object_name")) {
					formName = formProperties[i].getStringValue();
				} else if (propertyName.equals("s4Childstock")) {
					zhiKuCun = formProperties[i].getStringValue();
				} else if (propertyName.equals("s4cargo_s")) {
					huoWei = formProperties[i].getStringValue();
				} 
			}
			if(huoWei==null || "".equals(huoWei)){
				cdhuowei = huoWei;
			}else{
				cdhuowei = huoWei+ ".";
			}
			System.out.println("cdhuowei===>:"+cdhuowei);
			OracleConnect oraconn = new OracleConnect();
			// 实例化数据库连接
			conn = oraconn.getConnection();
			for (int i = 0; i < Opernumber_array.length; i++) {
				String sql = "insert into CUX.CUX_PLM_ROUTING_IFACE(IFACE_ID,OPERATION_SEQ_NUM,STANDARD_OPERATION_CODE,DEPARTMENT_CODE," +
				"ORGANIZATION_CODE,COMPLETION_SUBINVENTORY,COMPLETION_LOCATOR,ITEM_NUMBER,FORM_NAME," +
				"Creation_Date,Created_By,Last_Updated_By,Last_Update_Date,Last_Update_Login,Batch_Id) values ("
				+"CUX.CUX_PLM_ROUTING_IFACE_s.Nextval" + ",'" + Opernumber_array[i] + "','" + Operationcode_array[i] + "','" + Department_array[i] + "','" + Texture_array[i] + 
				"','" + zhiKuCun + "','" + cdhuowei + 
				"','" + sel_id + "','" + formName +"',SYSDATE,'" + userName+ "','-1',SYSDATE,'-1','-1' )";
				System.out.println("sql ===工艺路线====> "+sql);
				stmt = conn.createStatement();
				int j = 0;
				try {
					j = stmt.executeUpdate(sql);
				} catch (Exception e) {
					flag_cdgycg = true;
					e.printStackTrace();
					progressbar.stopBar();
					MessageBox.post(sel_id+"传递错误,错误信息为:"+e.getMessage(),"错误",MessageBox.ERROR);
					return;
				}
				if (j > 0) {
					System.out.println("信息插入成功");
				} else {
					System.out.println("插入失败");
				}
				stmt.close();
			}
			oraconn.closeConn(conn);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TCException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//将BOM数据插入到中间表
	public void insertDataToTable(String sel_id,AIFComponentContext[] boms){
		try {
			// 实例化数据库连接
			OracleConnect oraconn = new OracleConnect();
			conn = oraconn.getConnection();
			int k = boms.length;
			for (int i = 0; i < k; i++) {
				System.out.println("sel_id-------bom->:"+sel_id);
				TCComponentBOMLine sub_bom = (TCComponentBOMLine) boms[i].getComponent();
				//得到BOM的孩子最新版本
				TCComponentItemRevision sub_rev = sub_bom.getItem().getLatestItemRevision();
				//得到BOM最新版本的BOMVIEW
				TCComponentBOMViewRevision bomview = (TCComponentBOMViewRevision) sub_rev.getRelatedComponent(relation1);
				//如果BOMVIEW不为空,判断描述的值,如果为是,则此BOM不传递
				if(bomview!=null){
					String desc = bomview.getProperty("object_desc").toString();
					System.out.println("rev====----*desc==bom==>:"+desc);
					if("Y".equals(desc)){
						continue;
					}
				}
				String sub_bom_id = sub_bom.getProperty("bl_item_item_id");
				System.out.println("sub_bom_id-------bom->:"+sub_bom_id);
				String[] values = new String[12];
				values = sub_bom.getProperties(properties);
				System.out.println("values====00==>:"+values[0]);
				//插入到ERP中物料传递的表
				String sql = "insert into CUX.CUX_PLM_BOM_IFACE(IFACE_ID,ENG_ITEM_FLAG,ORGANIZATION_CODE,ITEM_NUM,ITEM_SEQUENCE,OPERATION_SEQ_NUM,COMPONENT_ITEM,COMPONENT_QUANTITY" +
						",COMPONENT_NUM1,COMPONENT_NUM2,COMPONENT_NUM3,VENDOR_NAME,SALES_DESCRIPTION,ABB_COMPONENT_NUM,SUPPLY_TYPE,SUPPLY_SUBINVENTORY,COMPONENT_REMARKS,CREATE_BY" +
						",CREATION_DATE,BATCH_ID) values ("
					+"CUX.CUX_PLM_BOM_IFACE_s.nextval" + ",'" + yxbom + "','" + zzdm + "','" + sel_id + "','" + values[0] +  "','10','" + sub_bom_id + "','" + values[2] + "','" + values[3] + "','" 
					+ values[4] + "','" + values[5] + "','" + values[6] + "','" + values[7] + "','" + values[8] + "','" + values[9] + "','" + values[10] + "','" + values[11] +"','"+userName+"',"+"SYSDATE" + ",'-1'" + ")";
				System.out.println("sql----bomgc->"+sql);
				stmt = conn.createStatement();
				int j = 0;
				try {
					j = stmt.executeUpdate(sql);
				} catch (Exception e) {
					flag_cdbomcg = true;
					e.printStackTrace();
					progressbar.stopBar();
					MessageBox.post(sel_id+"传递错误,错误信息为:"+e.getMessage(),"错误",MessageBox.ERROR);
					return;
				}
				if (j > 0) {
					System.out.println("信息插入成功");
				} else {
					System.out.println("插入失败");
				}
				stmt.close();
			}
			oraconn.closeConn(conn);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TCException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//通过传递一个对象版本,得到它的BOMLINE
	public TCComponentBOMLine getProBomLine(TCComponentItemRevision itemRev) {
		TCComponentBOMLine bomLine = null;
		try {
			TCComponentBOMWindowType bomWinType = (TCComponentBOMWindowType) session
					.getTypeComponent("BOMWindow");
			TCComponentBOMWindow bomWin = bomWinType.create(null);
			bomWin.lock();
			bomLine = bomWin.setWindowTopLine(itemRev.getItem(), itemRev, null,
					null);
			bomWin.unlock();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bomLine;
	}
	
}

package com.origin.rac.sac.sendbominfo;

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
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentBOMViewRevision;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class SendGCBomInfoCommand extends AbstractAIFCommand {

	private InterfaceAIFComponent[] targets = null;
	private AbstractAIFUIApplication application = null;
	private TCComponentBOMLine select_bom;
	private String relation = "IMAN_master_form_rev";
	private String relation1 = "structure_revisions";
	private TCSession session = null;
	private String[] attris = {"s4Passing_State","s4Wpromaterials"};
	private boolean flag1 = false;
	private boolean flag2 = false;
	private boolean flag3 = false;
	private boolean flag4 = false;
	private boolean flag6 = false;
	private boolean fg2 = false;
	private boolean flag_cd = false;
	private String yxbom = "N";
	public Connection conn = null;
	public ReadProperties read = null;
	public Statement stmt = null;
	public ResultSet reset = null;
	private Vector<String> vec_1 = new Vector<String>();
	private Vector<String> vec_2 = new Vector<String>();
	private Vector<String> vec_ID = new Vector<String>();//用来检查选择BOM及单层子结构ID所在的组织在ERP中是否存在
	private Vector<String> vec_error_ID = new Vector<String>();//用来提示选择的BOM及单层子结构ID在搜索到的组织里面不存在的ID
	private String[] properties = {"bl_sequence_no","S4masteroper","bl_quantity","S4ATTRIBUTE7","S4ATTRIBUTE8",
								"S4ATTRIBUTE9","S4ATTRIBUTE11","S4ATTRIBUTE10","S4ATTRIBUTE12","S4MEANING","S4SUPPLY_SUBIN","S4component_rem"};
	private ProgressBarThread progressbar = null ;
	private String userName = "";//申请人
	private String error_cd = "N";
	private String zzdm = "MZ0";

	
	public SendGCBomInfoCommand(AbstractAIFUIApplication app) {
		application =app;
		session = (TCSession) application.getSession();
		targets = (InterfaceAIFComponent[]) application.getTargetComponents();
		//执行菜单的人员
		userName = session.getUser().toString();
//		int m = user.indexOf("(");
//		userName = user.substring(0, m);
		if(targets.length==1){
			try {
				progressbar = new ProgressBarThread("工程BOM传递" ,"工程BOM传递中，请稍等...");
				progressbar.start();
				//首先检查选择物料单层结构所有物料在ERP中是不是已经存在
				select_bom = (TCComponentBOMLine) targets[0];
				String sel_bom_id = select_bom.getProperty("bl_item_item_id").toString();
				if(!vec_ID.contains(sel_bom_id)){
					vec_ID.add(sel_bom_id);
				}
//				String sel_bom_name = select_bom.getProperty("bl_item_object_name").toString();
//				String error_info = sel_bom_id +"," + sel_bom_name;
				TCComponentItemRevision sel_rev = select_bom.getItem().getLatestItemRevision();
				String str_fb = sel_rev.getProperty("release_status_list");
				if("".equals(str_fb) || str_fb==null){
					progressbar.stopBar();
					MessageBox.post("您选择的BOM没有发布,请检查!", "提示", MessageBox.INFORMATION);
					return;
				}
				TCComponentBOMViewRevision sel_bomview = (TCComponentBOMViewRevision) sel_rev.getRelatedComponent(relation1);
				if(sel_bomview!=null){
					String sel_desc = sel_bomview.getProperty("object_desc").toString();
					System.out.println("rev====----*sel_desc====>:"+sel_desc);
					if("Y".equals(sel_desc)){
						progressbar.stopBar();
						MessageBox.post("您选择的BOM已经传递过ERP,不允许再次传递,请检查!", "提示", MessageBox.INFORMATION);
						return;
					}
				}
				TCComponentForm form = (TCComponentForm) sel_rev.getRelatedComponent(relation);
				String[] yz_values = form.getProperties(attris);
				/*//判断选择的BOM它的物料有没有传递至ERP过
				if(!"Y".equals(yz_values[0])){
					flag2 = true;
					if(!vec_2.contains(error_info)){
						vec_2.add(error_info);
					}
				}*/
				//判断选择的物料是不是原型物料
				if("Y".equals(yz_values[1])){
					flag4 = true;
				}
				if(flag4){
					progressbar.stopBar();
					MessageBox.post("您选择的BOM行是原型物料,请检查!","错误",MessageBox.ERROR);
					return;
				}
				//判断选择的BOMLINE的bom视图是不是S4MZ0
				String select_zzdm = select_bom.getProperty("bl_view_type").toString();
				if(!"S4MZ0".equals(select_zzdm)){
					progressbar.stopBar();
					MessageBox.post("请选择MZ0组织的BOM进行传递!","错误",MessageBox.ERROR);
					return;
				}
				
				//得到选择BOM的孩子
				AIFComponentContext[] coms = select_bom.getChildren();
				if(coms.length>0 && coms!=null){
					for (int j = 0; j < coms.length; j++) {
						TCComponentBOMLine sub_bom = (TCComponentBOMLine) coms[j].getComponent();
						String sub_bom_id = sub_bom.getProperty("bl_item_item_id").toString();
						if(!vec_ID.contains(sub_bom_id)){
							vec_ID.add(sub_bom_id);
						}
						String sub_bom_name = sub_bom.getProperty("bl_item_object_name").toString();
						String sub_error_info = sub_bom_id +"," + sub_bom_name;
						TCComponentItemRevision sub_rev = sub_bom.getItem().getLatestItemRevision();
						TCComponentForm sub_form = (TCComponentForm) sub_rev.getRelatedComponent(relation);
						String sub_passing_state = sub_form.getProperty(attris[0]).toString();
						//判断有没有传递至ERP过
						if(!"Y".equals(sub_passing_state)){
							flag2 = true;
							if(!vec_2.contains(sub_error_info)){
								vec_2.add(sub_error_info);
							}
						}
						//得到BOM最新版本
						TCComponentItemRevision rev = sub_bom.getItem().getLatestItemRevision();
						//得到BOM最新版本的BOMVIEW
						TCComponentBOMViewRevision bomview = (TCComponentBOMViewRevision) rev.getRelatedComponent(relation1);
						//如果BOMVIEW不为空,判断描述的值
						if(bomview!=null){
							String desc = bomview.getProperty("object_desc").toString();
							System.out.println("rev====----*desc====>:"+desc);
							if("".equals(desc) || error_cd.equals(desc)){
								flag1 = true;
								vec_1.add(sub_bom_id);
							}
						}
					}
				}
				if(flag2){
					progressbar.stopBar();
					MessageBox.post(vec_2+"这些物料在ERP中没有创建，请先传递该物料后再传递BOM!", "提示", MessageBox.INFORMATION);
					return;
				}
				if(flag1){
					progressbar.stopBar();
					MessageBox.post("请先将"+vec_1+"的BOM传递至ERP后再尝试传递该BOM!", "提示", MessageBox.INFORMATION);
					return;
				}
				flag6 = isInOrganization(vec_ID,"MZ0");
				if(flag6){
					progressbar.stopBar();
					MessageBox.post(vec_error_ID+"所在的组织在ERP中没有分配,请检查!", "提示", MessageBox.INFORMATION);
					return;
				}
				
				//如果以上条件符合,则把符合条件的BOM信息传递至中间表
				AIFComponentContext[] boms = select_bom.getChildren();
				String parent_name = select_bom.getProperty("bl_formatted_parent_name").toString();
				if(parent_name!=null && !"".equals(parent_name)){
					String[] p_values = new String[12];
					p_values = select_bom.getProperties(properties);
					if(p_values[2]==null || "".equals(p_values[2])){
						fg2 = true;
					}
				}
				int k =boms.length;
				if(k>0){
					//先检查BOM上必填的有没有填
					for (int i = 0; i < k; i++) {
						TCComponentBOMLine sub_bom = (TCComponentBOMLine) boms[i].getComponent();
						String[] values = new String[12];
						values = sub_bom.getProperties(properties);
						if(values[2]==null || "".equals(values[2])){
							fg2 = true;
							break;
						}
					}
					if(fg2){
						progressbar.stopBar();
						MessageBox.post("数量属性需要维护后方能传递,请检查!", "提示", MessageBox.INFORMATION);
						return;
					}
					//首先判断选择的物料在原表中是不是已经存在
					String sel_id = select_bom.getProperty("bl_item_item_id");
					flag3 = checkTableData(sel_id);
					if(flag3){
						progressbar.stopBar();
						MessageBox.post("该BOM已经存在中间表,等待ERP接受!", "提示", MessageBox.INFORMATION);
						return;
					}else{
						//将数据插入到中间表
						insertDataToTable(sel_id,boms);
						progressbar.stopBar();
						if(!flag_cd){
							MessageBox.post("传递成功至ERP中间表，等待ERP接收!", "提示", MessageBox.INFORMATION);
						}
					}
				}else{
					//如果选择的是没有子层结构的bom,则报错
					progressbar.stopBar();
					MessageBox.post("请选择有子层结构的BOM发送!","错误",MessageBox.ERROR);
					return;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			MessageBox.post("请选择单个BOM操作!", "提示", MessageBox.INFORMATION);
			return;
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
	
	
	//检查选择的BOM在中间表中是不是已经存在
	public boolean checkTableData(String sel_id){
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
			}
			oraconn.closeConn(conn);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag_check;
		
	}
	
	
	//将数据插入到中间表
	public void insertDataToTable(String sel_id,AIFComponentContext[] boms){
		
		try {
			// 实例化数据库连接
			OracleConnect oraconn = new OracleConnect();
			conn = oraconn.getConnection();
			int k = boms.length;
			for (int i = 0; i < k; i++) {
				TCComponentBOMLine sub_bom = (TCComponentBOMLine) boms[i].getComponent();
				//得到BOM的孩子最新版本
				TCComponentItemRevision sub_rev = sub_bom.getItem().getLatestItemRevision();
				//得到BOM最新版本的BOMVIEW
				TCComponentBOMViewRevision bomview = (TCComponentBOMViewRevision) sub_rev.getRelatedComponent(relation1);
				//如果BOMVIEW不为空,判断描述的值,如果为是,则此BOM不传递
				if(bomview!=null){
					String desc = bomview.getProperty("object_desc").toString();
					System.out.println("rev====----*desc====>:"+desc);
					if("Y".equals(desc)){
						continue;
					}
				}
				String sub_bom_id = sub_bom.getProperty("bl_item_item_id");
				String[] values = new String[12];
				values = sub_bom.getProperties(properties);
				System.out.println("values====00==>:"+values[0]);
				//插入到ERP中物料传递的表
				String sql = "insert into CUX.CUX_PLM_BOM_IFACE(IFACE_ID,ENG_ITEM_FLAG,ORGANIZATION_CODE,ITEM_NUM,ITEM_SEQUENCE,OPERATION_SEQ_NUM,COMPONENT_ITEM,COMPONENT_QUANTITY" +
						",COMPONENT_NUM1,COMPONENT_NUM2,COMPONENT_NUM3,VENDOR_NAME,SALES_DESCRIPTION,ABB_COMPONENT_NUM,SUPPLY_TYPE,SUPPLY_SUBINVENTORY,COMPONENT_REMARKS,CREATE_BY" +
						",CREATION_DATE,BATCH_ID) values ("
					+"CUX.CUX_PLM_BOM_IFACE_s.nextval" + ",'" + yxbom + "','" + zzdm + "','" + sel_id + "','" + values[0] + "','10','" + sub_bom_id + "','" + values[2] + "','" + values[3] + "','" 
					+ values[4] + "','" + values[5] + "','" + values[6] + "','" + values[7] + "','" + values[8] + "','" + values[9] + "','" + values[10] + "','" + values[11] +"','"+userName+"',"+"SYSDATE" + ",'-1'" + ")";
				System.out.println("sql----bomgc->"+sql);
				stmt = conn.createStatement();
				int j = 0;
				try {
					j = stmt.executeUpdate(sql);
				} catch (Exception e) {
					flag_cd = true;
					e.printStackTrace();
					progressbar.stopBar();
					MessageBox.post("传递错误,错误信息为:"+e.getMessage(),"错误",MessageBox.ERROR);
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
	
	
}

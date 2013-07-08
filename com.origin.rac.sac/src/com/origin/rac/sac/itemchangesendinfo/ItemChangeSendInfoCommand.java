package com.origin.rac.sac.itemchangesendinfo;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import cn.com.origin.util.OracleConnect;
import cn.com.origin.util.ProgressBarThread;
import cn.com.origin.util.ReadProperties;
import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class ItemChangeSendInfoCommand extends AbstractAIFCommand {

	private InterfaceAIFComponent[] targets;
	private TCSession session = null;
	public Connection conn = null;
	public ReadProperties read = null;
	private Statement stmt = null;
	private ResultSet reset = null;
	private boolean flag=false;
	private boolean flag1=false;
	private boolean flag2=false;
	private boolean flag3=false;
	private boolean flag4=false;
	private boolean flag5=false;
	private boolean flag6=false;
	private boolean flag7=false;
	private boolean flag8=false;
	private boolean flag9=false;
	private boolean flag10=false;
	private boolean flag11=false;
	private boolean flag12=false;//用来标识选择物料时候表单有没有签入
	private boolean flag13=false;//用来标识选择物料时候表单有没有签入
	private boolean flag14=false;//用来标识选择物料时候表单上的物料描述字段长度有没有大于160字节
	private boolean flag15=false;//用来标识选择物料时候表单上的物料描述字段长度有没有大于160字节
	private String[] item_types = {"S4TW","S4ELECC","S4DEC","S4ROXE","S4ECOMP","S4NECOMP","S4SSCMM","S4SSCOM","S4PLG","S4MP","S4COST","S4OCOST","S4DQDM","S4RKDM","S4OPC"};
	private Vector<TCComponentItem> vec_item = new Vector<TCComponentItem>();
	private Vector<String> v_type = new Vector<String>();
	private String attri = "s4Passing_State";//是否传递成功属性
	private String attri1 = "checked_out";//签出属性
	private String attri2 = "s4Mdescription";//物料描述属性
	private String relation = "IMAN_master_form_rev";
	private String[] properties = {"s4Materialt","s4Mdescription","s4Item_Status","s4vendor","s4contact_maker","s4Supply_vol","s4CT_current",
									"s4Primary_Unit_of_M","s4Allow_Description_U","s4Wpromaterials","s4SAC_Inventory_c"};
	private String userName = "";//申请人
	private Vector<String> v_f7 = new Vector<String>();
	private Vector<String> v_f8 = new Vector<String>();
	private Vector<String> v_f9 = new Vector<String>();
	private Vector<String> v_f10 = new Vector<String>();
	private Vector<String> v_f11 = new Vector<String>();
	private Vector<String> v_f12 = new Vector<String>();
	private Vector<String> v_f13 = new Vector<String>();
	private Vector<String> v_f14 = new Vector<String>();
	private Vector<String> v_f15 = new Vector<String>();
	private ProgressBarThread progressbar = null ;
	
	
	public ItemChangeSendInfoCommand(AbstractAIFUIApplication app) {
		session = (TCSession) app.getSession();
		targets = app.getTargetComponents();
		v_type.removeAllElements();
		userName = session.getUser().toString();
//		int m = user.indexOf("(");
//		userName = user.substring(0, m);
		for (int i = 0; i < item_types.length; i++) {
			v_type.add(item_types[i]);
		}
		if(targets==null || targets.length==0){
			MessageBox.post("请选择物料对象!", "提示", MessageBox.INFORMATION);
			return;
		}
		
		//首先判断选择对象的类型是不是满足要求
		try {
			progressbar = new ProgressBarThread("物料修改传递" ,"物料修改传递中,请稍等...");
			progressbar.start();
			for (int i = 0; i < targets.length; i++) {
				if(!(targets[i] instanceof TCComponentItem) && !(targets[i] instanceof TCComponentFolder)){
					flag = true;
				}
				if(targets[i] instanceof TCComponentItem){
					flag3= true;
					TCComponentItem item = (TCComponentItem) targets[i];
					String erro7_id = item.getProperty("item_id");
					if(!v_type.contains(item.getType().toString())){
						flag5= true;
					}else{
						TCComponentItemRevision item_rev = item.getLatestItemRevision();
						String str = item_rev.getProperty("release_status_list");
						if("".equals(str) || str==null){
							flag9 = true;
							v_f9.add(erro7_id);
						}
						TCComponentForm com = (TCComponentForm) item_rev.getRelatedComponent(relation);
						if("N".equals(com.getProperty(attri).toString()) || "".equals(com.getProperty(attri).toString())){
							flag7= true;
							v_f7.add(erro7_id);
						}
						String tmpCheckedOut = com.getProperty(attri1).toString();
						System.out.println("tmpCheckedOut===>:"+tmpCheckedOut);
						if("Y".equals(tmpCheckedOut)){
							flag12= true;
							v_f12.add(erro7_id);
						}
						String desc = com.getProperty(attri2).toString();
						System.out.println("desc===>:"+desc.getBytes().length);
						int length = desc.getBytes().length;
						if(length>=160){
							flag14 = true;
							v_f14.add(erro7_id);
						}
					}
				}else if(targets[i] instanceof TCComponentFolder){
					flag4= true;
					TCComponentFolder folder = (TCComponentFolder) targets[i];
					TCComponent[] coms = folder.getRelatedComponents("contents");
					if (coms != null && coms.length > 0) {
						for (int j = 0; j < coms.length; j++) {
							if(coms[j] instanceof TCComponentItem){
								TCComponentItem item =(TCComponentItem) coms[j];
								String erro8_id = item.getProperty("item_id");
								if(!v_type.contains(item.getType().toString())){
									flag6= true;
								}else{
									TCComponentItemRevision item_rev = item.getLatestItemRevision();
									String str = item_rev.getProperty("release_status_list");
									if("".equals(str) || str==null){
										flag10 = true;
										v_f10.add(erro8_id);
									}
									TCComponentForm com = (TCComponentForm) item_rev.getRelatedComponent(relation);
									if("N".equals(com.getProperty(attri).toString()) || "".equals(com.getProperty(attri).toString())){
										flag8= true;
										v_f8.add(erro8_id);
									}
									String tmpCheckedOut = com.getProperty(attri1).toString();
									System.out.println("tmpCheckedOut=13==>:"+tmpCheckedOut);
									if("Y".equals(tmpCheckedOut)){
										flag13= true;
										v_f13.add(erro8_id);
									}
									String desc = com.getProperty(attri2).toString();
									System.out.println("desc===>:"+desc.getBytes().length);
									int length = desc.getBytes().length;
									if(length>=160){
										flag15 = true;
										v_f15.add(erro8_id);
									}
								}
							}else{
								flag1 = true;
							}
						}
					} else {
						flag2 = true;
					}
				}
			}
			if(flag || flag5 || flag6){
				progressbar.stopBar();
				MessageBox.post("请选择物料对象!", "提示", MessageBox.INFORMATION);
				return;
			}
			if(flag3 && flag4){
				progressbar.stopBar();
				MessageBox.post("请选择物料对象或者文件夹对象!", "提示", MessageBox.INFORMATION);
				return;
			}
			if(flag1){
				progressbar.stopBar();
				MessageBox.post("您选择的文件夹下面有不是物料的对象!", "提示", MessageBox.INFORMATION);
				return;
			}
			if(flag2){
				progressbar.stopBar();
				MessageBox.post("您选择的文件夹下面没有物料对象!", "提示", MessageBox.INFORMATION);
				return;
			}
			if(flag9){
				progressbar.stopBar();
				MessageBox.post("选择物料"+v_f9+"没有发布,请重新选择!", "提示", MessageBox.INFORMATION);
				return;
			}
			if(flag10){
				progressbar.stopBar();
				MessageBox.post("该文件夹下"+v_f10+"没有发布,请重新选择!", "提示", MessageBox.INFORMATION);
				return;
			}
			if(flag7){
				progressbar.stopBar();
				MessageBox.post("ERP尚未成功接收"+v_f7+"这些物料，不能修改,请重新选择!", "提示", MessageBox.INFORMATION);
				return;
			}
			if(flag8){
				progressbar.stopBar();
				MessageBox.post("ERP尚未成功接收"+v_f8+"这些物料，不能修改,请重新选择!", "提示", MessageBox.INFORMATION);
				return;
			}
			if(flag12){
				progressbar.stopBar();
				MessageBox.post("选择物料"+v_f12+"下面的版本表单没有签入,请检查!", "提示", MessageBox.INFORMATION);
				return;
			}
			if(flag13){
				progressbar.stopBar();
				MessageBox.post("该文件夹下有物料"+v_f13+"下面的版本表单没有签入,请检查!", "提示", MessageBox.INFORMATION);
				return;
			}
			if(flag14){
				progressbar.stopBar();
				MessageBox.post("选择物料"+v_f14+"下面的版本表单上物料描述值大于160字节,请检查!", "提示", MessageBox.INFORMATION);
				return;
			}
			if(flag15){
				progressbar.stopBar();
				MessageBox.post("该文件夹下有物料"+v_f15+"下面的版本表单上物料描述值大于160字节,请检查!", "提示", MessageBox.INFORMATION);
				return;
			}
			//对于符合条件的物料进行操作,传递状态都是Y的物料
			for (int i = 0; i < targets.length; i++) {
				if(targets[i] instanceof TCComponentItem){
					TCComponentItem item = (TCComponentItem) targets[i];
					vec_item.add(item);
				} else if (targets[i] instanceof TCComponentFolder) {
					TCComponentFolder folder = (TCComponentFolder) targets[i];
					TCComponent[] coms = folder.getRelatedComponents("contents");
					for (int j = 0; j < coms.length; j++) {
						TCComponentItem item =(TCComponentItem) coms[j];
						vec_item.add(item);
					}
					
				}
			}
			System.out.println("vec_item=xiugai===>:"+vec_item);
			try {
				// 实例化数据库连接
				OracleConnect oraconn = new OracleConnect();
				//首先判断选择的物料在原表中是不是已经存在
				conn = oraconn.getConnection();
				for (int i = 0; i < vec_item.size(); i++) {
					TCComponentItem item = vec_item.get(i);
					String item_id = item.getProperty("item_id").toString();
					String sqlString = "select ITEM_STATUS from CUX.CUX_PLM_INV_ITEM_ATT_IFACE where ITEM_NUMBER='"
						+ item_id + "'";
					System.out.println("【查询语句】---检查->" + sqlString);
					stmt = conn.createStatement();
					reset = stmt.executeQuery(sqlString);
					String item_status = "";
					if (reset != null && reset.next()) {
						item_status = reset.getString("ITEM_STATUS");
					}
					stmt.close();
					if(!"".equals(item_status) && item_status!=null){
						flag11 = true;
						v_f11.add(item_id);
					}
				}
				oraconn.closeConn(conn);
				if(flag11){
					progressbar.stopBar();
					MessageBox.post("有物料"+v_f11+"已经做过传递，只有未传递过的物料方可传递，请重新选择!", "提示", MessageBox.INFORMATION);
					return;
				}else{
					//如果选择的物料在原表中都没有存在,则实行插入数据
					conn = oraconn.getConnection();
					System.out.println("conn==xiugai=>:"+conn);
					System.out.println("vec_item==xiugai=>:"+vec_item.size());
					for (int i = 0; i < vec_item.size(); i++) {
						String[] values = new String[11];
						TCComponentItem item = vec_item.get(i);
						String item_id = item.getProperty("item_id").toString();
						TCComponentItemRevision item_rev = item.getLatestItemRevision();
						TCComponentForm form = (TCComponentForm) item_rev.getRelatedComponent(relation);
						values = form.getProperties(properties);
						System.out.println("values======xiugai=>:"+values[0]);
						System.out.println("values====1==xiugai=>:"+values[1]);
						
						//插入到ERP中物料传递的表
						String sql = "insert into CUX.CUX_PLM_INV_ITEM_ATT_IFACE(IFACE_ID,ITEM_NUMBER,ITEM_TEMP,ITEM_DESCRIPTION,ITEM_STATUS,VENDOR,ORDERING_CODE,SUPPLY_VOLTAGE,CT_CURRENT" +
								",PRIMARY_UNIT_OF_MEASURE,ALLOW_ITEM_DESC_UPDATE_FLAG,Y_N_ITEM,SAC_INV_CATEGORY,CREATE_BY,CREATION_DATE,BATCH_ID) values ("
							+ "CUX.CUX_PLM_INV_ITEM_IFACE_s.nextval"+",'" + item_id + "','" + values[0] + "','" + values[1] + "','" + values[2] + "','" + values[3] + "','" + values[4] + "','" 
							+ values[5] + "','" + values[6] + "','" + values[7] + "','" + values[8] + "','" + values[9] + "','" + values[10].split("\\*")[0] + "','" + userName + "',"+ "SYSDATE" + ",'-1'" + ")";
						System.out.println("sql===>:"+sql);
						stmt = conn.createStatement();
						int k = 0;
						try {
							k = stmt.executeUpdate(sql);
						} catch (Exception e) {
							e.printStackTrace();
							progressbar.stopBar();
							MessageBox.post("传递错误,错误信息为:"+e.getMessage(),"错误",MessageBox.ERROR);
							return;
						}
						if (k > 0) {
							System.out.println("信息插入成功");
						} else {
							System.out.println("插入失败");
						}
						stmt.close();
					}
					oraconn.closeConn(conn);
					progressbar.stopBar();
					MessageBox.post("传递成功至ERP中间表，等待ERP接收!", "提示", MessageBox.INFORMATION);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (TCException e) {
			e.printStackTrace();
		}
	}
	
}

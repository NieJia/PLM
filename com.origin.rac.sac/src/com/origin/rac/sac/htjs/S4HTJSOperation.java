package com.origin.rac.sac.htjs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.com.origin.util.OracleConnect;
import cn.com.origin.util.ProgressBarThread;
import cn.com.origin.util.S4Bypass;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentFolderType;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemType;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCProperty;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCTextService;
import com.teamcenter.rac.util.MessageBox;

public class S4HTJSOperation extends AbstractAIFOperation {

	private AbstractAIFApplication app = null;
	private TCComponentFolder targetfolder = null;
	private TCSession session = null;
	private String str_type = "S4Gcproj";
	private String relation ="IMAN_master_form";
	private ArrayList<String[]> hetonglist = new ArrayList<String[]>();
	private ProgressBarThread progressbar = null ;
	private S4Bypass bypass = null;
	private String xmmuban = "项目文档";
	private String projectName;
	private String templateType = "S4MZPrj_Folder";
	private String owner = "";
	private String tempalte_name = "工程项目模板";
	private TCComponentFolder templateObject;
	private String target_item_id=null;
	private String releation = "IMAN_reference";
	private String attri = "gov_classification";
	
	
	
	public S4HTJSOperation(AbstractAIFApplication app,TCComponentFolder targetfolder){
		this.app = app;
		this.session = (TCSession) this.app.getSession();
		this.targetfolder = targetfolder;
		bypass = new S4Bypass(session);
		owner = session.getPreferenceService().getString(4, "SAC_Folder_Template_Owner");
		if (owner == null || "".equals(owner.trim())) {
			MessageBox.post("未配置首选项：SAC_Folder_Template_Owner的值", "提示", MessageBox.INFORMATION);
			return;
		}
	}
	
	
	@Override
	public void executeOperation() throws Exception {

		progressbar = new ProgressBarThread("合同接收" ,"合同接收中,请稍等...");
		progressbar.start();
		//检查逻辑
		checkHTData();
		
	}

	/*
	 * 1.首先从中间表中检查合同号在系统中是不是存在
	 * 2.如果不存在,则创建这个合同,并设置表单属性和更新数据库信息
	 * 3.如果存在,则先判断PROCESS_FLAG值,若为Y或者N,不做任何处理
	 * 4.如果存在,则先判断PROCESS_FLAG值,若PROCESS_FLAG值为空,则做更新操作
	 */
	public void checkHTData() {
		hetonglist.clear();
		OracleConnect oraconn = new OracleConnect();
		try {
			Connection conn = oraconn.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select * from CUX.CUX_PLM_OM_CONT_IFACE where rowid in(select min(rowid) from " +
					"CUX.CUX_PLM_OM_CONT_IFACE group by CONTRACT_NUMBER)";
			ResultSet result = stmt.executeQuery(sql);
			if(result!=null )
			{
				while(result.next())
				{
					String[] tempvalue = new String[3];
//					tempvalue[0] = String.valueOf(result.getInt("CONTRACT_NUMBER"));
					tempvalue[0] = String.valueOf(result.getBigDecimal("CONTRACT_NUMBER"));
					System.out.println("tempvalue[0]====>:"+tempvalue[0]);
					tempvalue[1] = result.getString("PROJECT_NAME");
					tempvalue[2] = result.getString("PROCESS_FLAG");
					System.out.println("tempvalue[2]----->:"+tempvalue[2]);
					hetonglist.add(tempvalue);
				}
			}
			stmt.close();
			oraconn.closeConn(conn);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(hetonglist!=null && hetonglist.size()>0)
		{
			for (int i = 0; i < hetonglist.size(); i++) {
				String[] tempvalue = hetonglist.get(i);
				//得到合同号
				String item_id = tempvalue[0];
				System.out.println("item_id---->:"+item_id);
				//得到合同名称
				String item_name = tempvalue[1];
				//得到传递标记的值
				String process_flag = tempvalue[2];
				System.out.println("process_flag--->:"+process_flag);
				InterfaceAIFComponent[] items = query(item_id);
				//如果不存在,则创建这个合同,并设置表单属性和更新数据库信息
				if(items==null || items.length==0)
				{
					System.out.println(item_id+"这个ID的合同号在系统中不存在!");
					TCComponentItem item = createItem(item_id, item_name);
					try {
						if(item!=null){
							//获得创建ITEM的ID
							target_item_id=item.getProperty("item_id");
							//获得顶层模板文件夹的名称
							projectName = item.getProperty("object_name")+xmmuban;
							//创建顶层文件夹
							TCComponentFolder rootFolder = createFolder(templateType, projectName, item);
							//获得模板文件夹
							templateObject = getTemplateFolder(tempalte_name);
							if(templateObject == null){
								progressbar.stopBar();
								MessageBox.post("未找到工程项目模板对象", "提示", MessageBox.WARNING);
								return;
							}
							//将模板文件夹下面的结构新建挂接在新建的ITEM对象上
							addObjectToFolder(templateObject,rootFolder);
							targetfolder.add("contents", item);
						}
						else
						{
							progressbar.stopBar();
							MessageBox.post(item_id+"合同无法接收,创建失败!","提示",MessageBox.WARNING);
							return;
						}
							
						//得到需要设置属性的表单对象
						TCComponentForm master_form = (TCComponentForm) item.getRelatedComponent(relation);
						//设置表单属性
						boolean set_result = setFormProperties(master_form, item_id,item_name);
						//更新中间表传送标记
						updateTable(item_id,set_result,"接收失败");
						if(set_result)
						{
							continue;
						}
						else
						{
							progressbar.stopBar();
							MessageBox.post(item_id+"合同接收失败!","提示",MessageBox.WARNING);
							return;
						}
					} catch (TCException e1) {
						e1.printStackTrace();
					}
				}
				else
				{
					System.out.println(item_id+"这个ID在系统中已经存在!");
					
					//如果存在,则先判断PROCESS_FLAG值,若为Y或者N,不做任何处理
					if("Y".equals(process_flag) || "N".equals(process_flag)){
						continue;
					}else{
						//若PROCESS_FLAG值为空,则做更新操作
						try {
							TCComponentItem gx_item = (TCComponentItem) items[0];
							TCComponentForm gx_master_form = (TCComponentForm) gx_item.getRelatedComponent(relation);
							boolean gx_result = updateForm(gx_master_form, item_id);
							updateTable(item_id,gx_result,"接收失败");
							if(gx_result)
							{
								continue;
							}
							else
							{
								progressbar.stopBar();
								MessageBox.post(item_id+"合同属性更新失败!","提示",MessageBox.WARNING);
								return;
							}
							
						} catch (TCException e) {
							e.printStackTrace();
						}
					}
				}
			}
			progressbar.stopBar();
			MessageBox.post("合同接受及更新完成!","提示",MessageBox.INFORMATION);
		}else{
			progressbar.stopBar();
			MessageBox.post("合同中间表里面没有数据!","提示",MessageBox.INFORMATION);
			return;
		}
	}
	
	
	//设置表单属性
	private boolean setFormProperties(TCComponentForm master_form,String itemid,String object_name)
	{
		boolean isOK = false;
		//设置主表单两个基本属性
		try {
			master_form.setProperty("s4contractno", itemid);
			master_form.setProperty("s4projectnam", object_name);
		} catch (TCException e1) {
			e1.printStackTrace();
		}
		ArrayList<S4HeToneInfo> hetonginfolist = new ArrayList<S4HeToneInfo>();
	
		OracleConnect oraconn = new OracleConnect();
		try {
			Connection conn = oraconn.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select * from CUX.CUX_PLM_OM_CONT_IFACE where CONTRACT_NUMBER="+itemid+"";
			ResultSet result = stmt.executeQuery(sql);
			if(result!=null )
			{
				while(result.next())
				{
					S4HeToneInfo oneinfo = new S4HeToneInfo();
					
					String total_price = String.valueOf(result.getBigDecimal("TOTAL_PRICE"))==null?"":String.valueOf(result.getBigDecimal("TOTAL_PRICE"));
					if(total_price==null || "null".equals(total_price)){
						total_price = "";
					}
					oneinfo.setS4totalprice(total_price);
					oneinfo.setS4orderunit(result.getString("CUSTOMER_NAME")==null?"":result.getString("CUSTOMER_NAME"));
					Date date = result.getDate("DELIVERY_DATE");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(date == null){
						oneinfo.setS4deliverydate("");
					}else{
						oneinfo.setS4deliverydate(sdf.format(result.getDate("DELIVERY_DATE"))==null?"":sdf.format(result.getDate("DELIVERY_DATE")));
					}
					oneinfo.setS4vdc(result.getString("VDC")==null?"":result.getString("VDC"));
					oneinfo.setS4ddcc(result.getString("DDCC")==null?"":result.getString("DDCC"));
					oneinfo.setS4babintasize(result.getString("BABINTASIZE")==null?"":result.getString("BABINTASIZE"));
					
					//后增加编码和描述
					oneinfo.setS4Gcode(result.getString("ORDERED_ITEM_DSP")==null?"":result.getString("ORDERED_ITEM_DSP"));
					oneinfo.setS4Gdescription(result.getString("ITEM_DESCRIPTION")==null?"":result.getString("ITEM_DESCRIPTION"));
					//再后增加需求分类和备注
					oneinfo.setS4declass(result.getString("DEMAND_CLASS")==null?"":result.getString("DEMAND_CLASS"));
					oneinfo.setS4remarkss(result.getString("ATTRIBUTE16")==null?"":result.getString("ATTRIBUTE16"));
					//再后增加行号、物料数量和销售单价
					String line_num = String.valueOf(result.getBigDecimal("LINE_NUMBER"))==null?"":String.valueOf(result.getBigDecimal("LINE_NUMBER"));
					if(line_num==null || "null".equals(line_num)){
						line_num = "";
					}
					oneinfo.setS4linenum(line_num);
					String ordered_quantity = String.valueOf(result.getBigDecimal("ORDERED_QUANTITY"))==null?"":String.valueOf(result.getBigDecimal("ORDERED_QUANTITY"));
					if(ordered_quantity==null || "null".equals(ordered_quantity)){
						ordered_quantity = "";
					}
					oneinfo.setS4Maternum(ordered_quantity);
					String unit_selling_price = String.valueOf(result.getBigDecimal("UNIT_SELLING_PRICE"))==null?"":String.valueOf(result.getBigDecimal("UNIT_SELLING_PRICE"));
					if(unit_selling_price==null || "null".equals(unit_selling_price)){
						unit_selling_price = "";
					}
					oneinfo.setS4Saleo(unit_selling_price);

					
					hetonginfolist.add(oneinfo);
				}
			}
			stmt.close();
			oraconn.closeConn(conn);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//更新表单相应属性
		if(hetonginfolist!=null && hetonginfolist.size()>0)
		{
			try {
				master_form.setProperty("s4totalprice", hetonginfolist.get(0).getS4totalprice());
				master_form.setProperty("s4orderunit", hetonginfolist.get(0).getS4orderunit());
				master_form.setProperty("s4deliverydate", hetonginfolist.get(0).getS4deliverydate());
			} catch (TCException e) {
				e.printStackTrace();
			}
			
			try {
				String[] attris = new String[]{"s4linenum","s4coding1","s4discriptions1","s4vdc1","s4ddcc1","s4babintasize1","s4declass","s4remarkss","s4Maternum","s4Saleo"};
				String[][] tempvalues = new String[hetonginfolist.size()][10];
				TCProperty[] pros = master_form.getTCProperties(attris);
				for(int i=0;i<hetonginfolist.size();i++)
				{
					S4HeToneInfo oneinfo = hetonginfolist.get(i);
					tempvalues[i][0] = oneinfo.getS4linenum();
					tempvalues[i][1] = oneinfo.getS4Gcode();
					tempvalues[i][2] = oneinfo.getS4Gdescription();
					tempvalues[i][3] = oneinfo.getS4vdc();
					tempvalues[i][4] = oneinfo.getS4ddcc();
					tempvalues[i][5] = oneinfo.getS4babintasize();
					tempvalues[i][6] = oneinfo.getS4declass();
					tempvalues[i][7] = oneinfo.getS4remarkss();
					tempvalues[i][8] = oneinfo.getS4Maternum();
					tempvalues[i][9] = oneinfo.getS4Saleo();
					System.out.println("tempvalues["+i+"][0]"+tempvalues[i][0]);
					System.out.println("tempvalues["+i+"][1]"+tempvalues[i][1]);
					System.out.println("tempvalues["+i+"][2]"+tempvalues[i][2]);
					System.out.println("tempvalues["+i+"][3]"+tempvalues[i][3]);
					System.out.println("tempvalues["+i+"][4]"+tempvalues[i][4]);
					System.out.println("tempvalues["+i+"][5]"+tempvalues[i][5]);
					System.out.println("tempvalues["+i+"][6]"+tempvalues[i][6]);
					System.out.println("tempvalues["+i+"][7]"+tempvalues[i][7]);
					System.out.println("tempvalues["+i+"][8]"+tempvalues[i][8]);
					System.out.println("tempvalues["+i+"][9]"+tempvalues[i][9]);
				}
				
				for(int i=0;i<pros.length;i++)
				{
					TCProperty onepor = pros[i];
					String[] values = new String[tempvalues.length];
					for(int j=0;j<tempvalues.length;j++)
					{
						values[j] = tempvalues[j][i];
					}
					onepor.setStringValueArray(values);
					
				}
				master_form.setTCProperties(pros);
				isOK = true;
			} catch (TCException e) {
				e.printStackTrace();
			}
			
		}
		return isOK;
	}
	
	//更新数据库PROCESS_FLAG值
	private void updateTable(String itemid,boolean result,String errmessage)
	{
		String state = "N";
		if(result)
		{
			state = "Y";
			errmessage = "";
		}
		
		// 实例化数据库连接
		OracleConnect oraconn = new OracleConnect();
		try {
			Connection conn = oraconn.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "update CUX.CUX_PLM_OM_CONT_IFACE set PROCESS_FLAG='"+state+"',ERROR_MSG='"+errmessage+"' " +
					"where CONTRACT_NUMBER="+itemid+"";
			int k = stmt.executeUpdate(sql);
			if(k>0)
				System.out.println("更新合同表成功");
			
			stmt.close();
			oraconn.closeConn(conn);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//更新表单属性
	private boolean updateForm(TCComponentForm master_form,String itemid)
	{
		bypass.setpass();
		boolean isOK = false;
		
		ArrayList<S4HeToneInfo> hetonginfolist = new ArrayList<S4HeToneInfo>();
	
		OracleConnect oraconn = new OracleConnect();
		try {
			Connection conn = oraconn.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select * from CUX.CUX_PLM_OM_CONT_IFACE where CONTRACT_NUMBER="+itemid+"";
			ResultSet result = stmt.executeQuery(sql);
			if(result!=null )
			{
				while(result.next())
				{
					S4HeToneInfo oneinfo = new S4HeToneInfo();
					oneinfo.setS4projectnam(result.getString("PROJECT_NAME")==null?"":result.getString("PROJECT_NAME"));
					
					String total_price = String.valueOf(result.getBigDecimal("TOTAL_PRICE"))==null?"":String.valueOf(result.getBigDecimal("TOTAL_PRICE"));
					if(total_price==null || "null".equals(total_price)){
						total_price = "";
					}
					oneinfo.setS4totalprice(total_price);
					oneinfo.setS4orderunit(result.getString("CUSTOMER_NAME")==null?"":result.getString("CUSTOMER_NAME"));
					Date date = result.getDate("DELIVERY_DATE");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(date == null){
						oneinfo.setS4deliverydate("");
					}else{
						oneinfo.setS4deliverydate(sdf.format(result.getDate("DELIVERY_DATE"))==null?"":sdf.format(result.getDate("DELIVERY_DATE")));
					}
					oneinfo.setS4vdc(result.getString("VDC")==null?"":result.getString("VDC"));
					oneinfo.setS4ddcc(result.getString("DDCC")==null?"":result.getString("DDCC"));
					oneinfo.setS4babintasize(result.getString("BABINTASIZE")==null?"":result.getString("BABINTASIZE"));
					
					//后增加编码和描述
					oneinfo.setS4Gcode(result.getString("ORDERED_ITEM_DSP")==null?"":result.getString("ORDERED_ITEM_DSP"));
					oneinfo.setS4Gdescription(result.getString("ITEM_DESCRIPTION")==null?"":result.getString("ITEM_DESCRIPTION"));
					//再后增加需求分类和备注
					oneinfo.setS4declass(result.getString("DEMAND_CLASS")==null?"":result.getString("DEMAND_CLASS"));
					oneinfo.setS4remarkss(result.getString("ATTRIBUTE16")==null?"":result.getString("ATTRIBUTE16"));
					//再后增加行号、物料数量和销售单价
					String line_num = String.valueOf(result.getBigDecimal("LINE_NUMBER"))==null?"":String.valueOf(result.getBigDecimal("LINE_NUMBER"));
					if(line_num==null || "null".equals(line_num)){
						line_num = "";
					}
					oneinfo.setS4linenum(line_num);
					String ordered_quantity = String.valueOf(result.getBigDecimal("ORDERED_QUANTITY"))==null?"":String.valueOf(result.getBigDecimal("ORDERED_QUANTITY"));
					if(ordered_quantity==null || "null".equals(ordered_quantity)){
						ordered_quantity = "";
					}
					oneinfo.setS4Maternum(ordered_quantity);
					String unit_selling_price = String.valueOf(result.getBigDecimal("UNIT_SELLING_PRICE"))==null?"":String.valueOf(result.getBigDecimal("UNIT_SELLING_PRICE"));
					if(unit_selling_price==null || "null".equals(unit_selling_price)){
						unit_selling_price = "";
					}
					oneinfo.setS4Saleo(unit_selling_price);
					
					hetonginfolist.add(oneinfo);
				}
			}
			stmt.close();
			oraconn.closeConn(conn);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(hetonginfolist!=null && hetonginfolist.size()>0)
		{
			try {
				bypass.setpass();
				master_form.setProperty("s4projectnam", hetonginfolist.get(0).getS4projectnam());
				master_form.setProperty("s4totalprice", hetonginfolist.get(0).getS4totalprice());
				master_form.setProperty("s4orderunit", hetonginfolist.get(0).getS4orderunit());
				master_form.setProperty("s4deliverydate", hetonginfolist.get(0).getS4deliverydate());
			} catch (TCException e) {
				e.printStackTrace();
			}
			
			try {
				String[] attris = new String[]{"s4linenum","s4coding1","s4discriptions1","s4vdc1","s4ddcc1","s4babintasize1","s4declass","s4remarkss","s4Maternum","s4Saleo"};
				String[][] tempvalues = new String[hetonginfolist.size()][10];
				TCProperty[] pros = master_form.getTCProperties(attris);
				for(int i=0;i<hetonginfolist.size();i++)
				{
					S4HeToneInfo oneinfo = hetonginfolist.get(i);
					tempvalues[i][0] = oneinfo.getS4linenum();
					tempvalues[i][1] = oneinfo.getS4Gcode();
					tempvalues[i][2] = oneinfo.getS4Gdescription();
					tempvalues[i][3] = oneinfo.getS4vdc();
					tempvalues[i][4] = oneinfo.getS4ddcc();
					tempvalues[i][5] = oneinfo.getS4babintasize();
					tempvalues[i][6] = oneinfo.getS4declass();
					tempvalues[i][7] = oneinfo.getS4remarkss();
					tempvalues[i][8] = oneinfo.getS4Maternum();
					tempvalues[i][9] = oneinfo.getS4Saleo();
					System.out.println("tempvalues["+i+"][0]"+tempvalues[i][0]);
					System.out.println("tempvalues["+i+"][1]"+tempvalues[i][1]);
					System.out.println("tempvalues["+i+"][2]"+tempvalues[i][2]);
					System.out.println("tempvalues["+i+"][3]"+tempvalues[i][3]);
					System.out.println("tempvalues["+i+"][4]"+tempvalues[i][4]);
					System.out.println("tempvalues["+i+"][5]"+tempvalues[i][5]);
					System.out.println("tempvalues["+i+"][6]"+tempvalues[i][6]);
					System.out.println("tempvalues["+i+"][7]"+tempvalues[i][7]);
					System.out.println("tempvalues["+i+"][8]"+tempvalues[i][8]);
					System.out.println("tempvalues["+i+"][9]"+tempvalues[i][9]);
				}
				
				for(int i=0;i<pros.length;i++)
				{
					TCProperty onepor = pros[i];
					String[] values = new String[tempvalues.length];
					for(int j=0;j<tempvalues.length;j++)
					{
						values[j] = tempvalues[j][i];
					}
					onepor.setStringValueArray(values);
					
				}
				bypass.setpass();
				master_form.setTCProperties(pros);
				isOK = true;
				bypass.closepass();
			} catch (TCException e) {
				e.printStackTrace();
			}
			
		}
		return isOK;
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
	
	//创建ITEM
	public TCComponentItem createItem(String id,String name) {
		try {
			TCComponentItemType itemtype = (TCComponentItemType) session.getTypeComponent(str_type);
			TCComponentItem item = itemtype.create(id, "",
					str_type, name, str_type, null);
			return item;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 获取对应的模板对象
	private TCComponentFolder getTemplateFolder(String templateName) {
		TCComponentFolder folder = null;
		try {
			TCTextService tcTextService = session.getTextService();
			String askKey[] = { tcTextService.getTextValue("Name"), tcTextService.getTextValue("Type"), tcTextService.getTextValue("OwningUser") };
			String askValue[] = { templateName, templateType, owner };
			InterfaceAIFComponent objects[] = session.search("常规...", askKey, askValue);
			if (objects != null && objects.length > 0) {
				folder = (TCComponentFolder) objects[0];
			}
		} catch (TCException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return folder;
	}
	
	// 根据类型和名称创建文件夹对象
	private TCComponentFolder createFolder(String type, String name, TCComponentItem parent) {
		TCComponentFolder folder = null;
		if(name.equals("订单行物料及图纸"))
			name="订单行物料及图纸"+"("+target_item_id+")";
		try {
			TCComponentFolderType folderType = (TCComponentFolderType) session.getTypeComponent(type);
			folder = folderType.create(name, "", type);
			folder.setProperty(attri, target_item_id);
			if (folder != null)
				parent.add(releation, folder);
		} catch (TCException e) {
			e.printStackTrace();
		}
		return folder;
	}
	
	// 根据类型和名称创建文件夹对象
	private TCComponentFolder createFolder(String type, String name, TCComponentFolder parent) {
		TCComponentFolder folder = null;
		if(name.equals("订单行物料及图纸"))
			name="订单行物料及图纸"+"("+target_item_id+")";
		try {
			TCComponentFolderType folderType = (TCComponentFolderType) session.getTypeComponent(type);
			folder = folderType.create(name, "", type);
			folder.setProperty(attri, target_item_id);
			if (folder != null)
				parent.add("contents", folder);
		} catch (TCException e) {
			e.printStackTrace();
		}
		return folder;
	}
	
	// 根据类型和名称创建零组件对象
	private void createItem(String type, String name, TCComponentFolder parent) {
		try {
			TCComponentItemType itemType = (TCComponentItemType) session.getTypeComponent(type);
			String itemId = itemType.getNewID(); // 获取编码
			String itemRev = itemType.getNewRev(null); // 获取版本编码
			TCComponentItem item = itemType.create(itemId, itemRev, type, name, "", null);
			item.setProperty(attri,target_item_id);
			item.getLatestItemRevision().setProperty(attri,target_item_id);
			item.getLatestItemRevision().getRelatedComponent("IMAN_master_form_rev").setProperty(attri,target_item_id);
			if (item != null)
			{
				parent.add("contents", item);	
			}
		} catch (TCException e) {
			e.printStackTrace();
		}
	}
	
	private void addObjectToFolder(TCComponentFolder folder,TCComponentFolder rootfolder) {//folder为模板根节点文件夹对象
		try {
			TCComponent[] children = folder.getRelatedComponents("contents");
			for (int i = 0; i < children.length; i++) {
				TCComponent child = children[i];
				String name = child.getProperty("object_name");
				String type = child.getType();
				if (child instanceof TCComponentFolder) {
					addObjectToFolder((TCComponentFolder) child, createFolder(
							type, name, rootfolder));
				} else if (child instanceof TCComponentItem) {
					createItem(type, name, rootfolder);
				}
			}
		} catch (TCException e) {
			e.printStackTrace();
		}
	}
	
}

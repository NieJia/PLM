package com.origin.rac.sac.itemsendinfo;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import cn.com.origin.util.OracleConnect;
import cn.com.origin.util.ProgressBarThread;
import cn.com.origin.util.ReadProperties;
import cn.com.origin.util.S4Bypass;

import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCTextService;
import com.teamcenter.rac.util.MessageBox;

public class ItemCheckInfoCommand extends AbstractAIFCommand {

	private TCSession session = null;
	public Connection conn = null;
	public ReadProperties read = null;
	private Statement stmt = null;
	private ResultSet reset = null;
	private boolean flag = false;
	private boolean flag1 = false;
	private boolean flag2 = false;
	private HashMap<String,String> map = new HashMap<String,String>();
	private String relation = "IMAN_master_form_rev";
	private String attri = "s4Passing_State";
	private S4Bypass bypass = null;
	private ProgressBarThread progressbar = null ;
	private String userName = "";
	private String rzname= "物料创建传递检查日志";
	private String dname= "";
	
	
	public ItemCheckInfoCommand(AbstractAIFUIApplication app) {
		session = (TCSession) app.getSession();
		bypass = new S4Bypass(session);
		//执行菜单的人员
		userName = session.getUser().toString();
		System.out.println("userName====>:"+userName);
		dname = rzname+getDate()+".txt";
		Vector<String> vect_error_info = new Vector<String>();
		Vector<String> vect_error_ID = new Vector<String>();
		Vector<String> vect_sucess_ID = new Vector<String>();
		Vector<String> vect_kong_ID = new Vector<String>();
		try {
			progressbar = new ProgressBarThread("物料创建传递检查" ,"物料创建传递检查中,请稍等...");
			progressbar.start();
			// 实例化数据库连接
			OracleConnect oraconn = new OracleConnect();
			conn = oraconn.getConnection();
			//查询中间表是传递状态是N的物料
			String sql_N_String = "select * from CUX.CUX_PLM_INV_ITEM_IFACE where PROCESS_FLAG='"
					+ "N" + "'" + "and CREATE_BY = '" + userName +"'";
			System.out.println("【查询语句】" + sql_N_String);
			stmt = conn.createStatement();
			reset = stmt.executeQuery(sql_N_String);
			System.out.println("reset==>:"+reset);
			while(reset.next()){
				flag = true;
				String errorId = reset.getString("ITEM_NUMBER");
				System.out.println("buyId------>:"+errorId);
				String log = reset.getString("ERROR_MSG");
				String error_info = errorId + ",错误信息为:" + log;
//				String error_info = "错误信息为:" + log;
				map.put(errorId, "N");
				if(!vect_error_info.contains(error_info)){
					vect_error_info.add(error_info);
				}
				if(!vect_error_ID.contains(errorId)){
					vect_error_ID.add(errorId);
				}
			}
			stmt.close();
			oraconn.closeConn(conn);
			//查询中间表是传递状态是Y的物料
			conn = oraconn.getConnection();
			System.out.println("conn====^&&&>>>:"+conn);
			String sql_Y_String = "select * from CUX.CUX_PLM_INV_ITEM_IFACE where PROCESS_FLAG='"
				+ "Y" + "'" + "and CREATE_BY = '" + userName +"'";
			stmt = conn.createStatement();
			reset = stmt.executeQuery(sql_Y_String);
			while(reset.next()){
				flag1 = true;
				String sucess_Id = reset.getString("ITEM_NUMBER");
				System.out.println("sucess_Id------>:"+sucess_Id);
				if(!vect_sucess_ID.contains(sucess_Id)){
					vect_sucess_ID.add(sucess_Id);
				}
				map.put(sucess_Id, "Y");
			}
			stmt.close();
			oraconn.closeConn(conn);
			//查询中间表是传递状态是空的物料
			conn = oraconn.getConnection();
			System.out.println("conn====^&&&>>>:"+conn);
			String sqlString12 = "select * from CUX.CUX_PLM_INV_ITEM_IFACE where (PROCESS_FLAG is null or PROCESS_FLAG = '')" 
				+ "and CREATE_BY = '" + userName +"'";
			stmt = conn.createStatement();
			reset = stmt.executeQuery(sqlString12);
			while(reset.next()){
				flag2 = true;
				String kong_Id = reset.getString("ITEM_NUMBER");
				System.out.println("kong_Id------>:"+kong_Id);
				if(!vect_kong_ID.contains(kong_Id)){
					vect_kong_ID.add(kong_Id);
				}
			}
			stmt.close();
			oraconn.closeConn(conn);
			//如果中间表中PROCESS_FLAG是N的则提示错误信息
			if(flag){
				writeTxtMessage(vect_error_info,dname);
			}
			//如果中间表中PROCESS_FLAG是Y的则提示成功信息
			if(flag1 || flag){
				//设置传递状态属性值
				setStateProperty();
			}
			
			//将原表中的数据复制到备份表CUX.CUX_PLM_INV_ITEM_HIST里面
			copydataTotable();
			
			//将原表中PROCESS_FLAG是Y或者是N的数据删除
			deletedataFromtable();
			
			progressbar.stopBar();
			//如果中间表中PROCESS_FLAG既有Y又有N又有空的则提示信息
			if(flag1 && flag && flag2){
				MessageBox.post(vect_error_ID+"传递失败!"
						+"\n"+ vect_sucess_ID+"已经成功传递ERP!"
						+"\n" + vect_kong_ID+"已经传递至中间表等待ERP接收确认!", "提示", MessageBox.INFORMATION);
			}
			//如果中间表中PROCESS_FLAG只是Y的则提示信息
			if(flag1 && !flag && !flag2){
				MessageBox.post(vect_sucess_ID+"已经成功传递ERP!", "提示", MessageBox.INFORMATION);
			}
			//如果中间表中PROCESS_FLAG有Y和N的则提示信息
			if(flag1 && flag && !flag2){
				MessageBox.post(vect_error_ID+"传递失败!"
						+"\n"+ vect_sucess_ID+"已经成功传递ERP!", "提示", MessageBox.INFORMATION);
			}
			//如果中间表中PROCESS_FLAG有空和Y的则提示信息
			if(flag1 && !flag && flag2){
				MessageBox.post(vect_sucess_ID+"已经成功传递ERP!!"
						+"\n"+ vect_kong_ID+"已经传递至中间表等待ERP接收确认!", "提示", MessageBox.INFORMATION);
			}
			//如果中间表中PROCESS_FLAG只是空的则提示信息
			if(!flag1 && !flag && flag2){
				MessageBox.post(vect_kong_ID+"已经传递至中间表等待ERP接收确认!", "提示", MessageBox.INFORMATION);
			}
			//如果中间表中PROCESS_FLAG有为空和N的则提示信息
			if(!flag1 && flag && flag2){
				MessageBox.post(vect_error_ID+"传递失败!"
						+"\n"+ vect_kong_ID+"已经传递至中间表等待ERP接收确认!", "提示", MessageBox.INFORMATION);
			}
			//如果中间表中PROCESS_FLAG只是N的则提示信息
			if(!flag1 && flag && !flag2){
				MessageBox.post(vect_error_ID+"传递失败!", "提示", MessageBox.INFORMATION);
			}
			if(!flag && !flag1 && !flag2){
				MessageBox.post("目前没有你需要检查的物料,检查完毕!", "提示", MessageBox.INFORMATION);
			}
			//自动打开日志文件
			if(flag){
				Runtime.getRuntime().exec("cmd /c "+System.getenv("TEMP")+"/"+dname);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	//设置传递状态属性值
	public void setStateProperty(){
		System.out.println("map====>:"+map.size());
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String item_id = (String) iterator.next();
			String value = map.get(item_id);
			InterfaceAIFComponent[] items = query(item_id);
			if(items == null || items.length==0){
				System.out.println("ERP接受后没有搜索到对应的Item");
			}else{
				TCComponentItem sucess_item = (TCComponentItem) items[0];
				try {
					TCComponentItemRevision rev = sucess_item.getLatestItemRevision();
					TCComponentForm form = (TCComponentForm) rev.getRelatedComponent(relation);
					bypass.setpass();
					form.setProperty(attri, value);
				} catch (TCException e) {
					e.printStackTrace();
				}
			}
		}
		bypass.closepass();
	}
	
	
	//将原表中的数据复制到备份表CUX.CUX_PLM_INV_ITEM_HIST里面
	public void copydataTotable(){
		try {
			// 实例化数据库连接
			OracleConnect oraconn = new OracleConnect();
			conn = oraconn.getConnection();
			String sql_copy = "insert into CUX.CUX_PLM_INV_ITEM_HIST select * from CUX.CUX_PLM_INV_ITEM_IFACE where (PROCESS_FLAG='"
				+ "Y" + "' or PROCESS_FLAG='" + "N" +"')" + "and CREATE_BY = '" + userName +"'";
			System.out.println("【拷贝语句】" + sql_copy);
			stmt = conn.createStatement();
			int rs_copy = stmt.executeUpdate(sql_copy);
			if (rs_copy > 0) {
				System.out.println("信息拷贝成功");
			} else {
				System.out.println("信息拷贝失败");
			}
			stmt.close();
			oraconn.closeConn(conn);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//将原表中PROCESS_FLAG是Y或者是N的数据删除
	public void deletedataFromtable(){
		try {
			// 实例化数据库连接
			OracleConnect oraconn = new OracleConnect();
			conn = oraconn.getConnection();
			System.out.println("conn---2====>:"+conn);
			String sql_delete = "delete from CUX.CUX_PLM_INV_ITEM_IFACE where (PROCESS_FLAG='"
				+ "Y" + "' or PROCESS_FLAG='" + "N" +"')" + "and CREATE_BY = '" + userName +"'";
			System.out.println("【删除语句】" + sql_delete);
			stmt = conn.createStatement();
			int rs12 = stmt.executeUpdate(sql_delete);
			if (rs12 > 0) {
				System.out.println("信息删除成功");
			} else {
				System.out.println("信息删除失败");
			}
			stmt.close();
			oraconn.closeConn(conn);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
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
	
	//写入日志
	public void writeTxtMessage(Vector<String> vec_error,String name) {
		try {
			PrintWriter txt = new PrintWriter(new FileWriter(System.getenv("TEMP")+"/"+name,true),true);
			txt.print("                    日志信息：                 "+"\n");
			for (int i = 0; i < vec_error.size(); i++) {
				String values = vec_error.get(i);
				txt.print(values+"\n");
			}
			txt.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getDate() {
		Calendar date = Calendar.getInstance();
		java.text.SimpleDateFormat sim = new java.text.SimpleDateFormat("ddMMyyyyHHmmss");
		String str = sim.format(date.getTime());
		return str;

	}
	
}

package com.origin.rac.sac.sendgylxandbominfo;

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
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentBOMViewRevision;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCTextService;
import com.teamcenter.rac.util.MessageBox;

public class BatchCheckGylxAndBomCommand extends AbstractAIFCommand {

	private TCSession session = null;
	public Connection conn = null;
	public ReadProperties read = null;
	private Statement stmt = null;
	private ResultSet reset = null;
	private boolean bom_flag = false;
	private boolean bom_flag1 = false;
	private boolean bom_flag2 = false;
	private boolean gylx_flag = false;
	private boolean gylx_flag1 = false;
	private boolean gylx_flag2 = false;
	private HashMap<String,String> map = new HashMap<String,String>();
	private String relation1 = "structure_revisions";
	private String attri = "object_desc";
	private String relation = "IMAN_specification";
	private String error = "s4error";
	private String tranlogo = "s4Tranlogo";
	private S4Bypass bypass = null;
	private ProgressBarThread progressbar = null ;
	private String userName = "";
	private String rzname= "工程BOM及工艺路线批量传递检查日志";
	private String dname= "";
	
	
	public BatchCheckGylxAndBomCommand(AbstractAIFUIApplication app) {
		session = (TCSession) app.getSession();
		bypass = new S4Bypass(session);
		//执行菜单的人员
		userName = session.getUser().toString();
		System.out.println("userName====>:"+userName);
		dname = rzname+getDate()+".txt";
		Vector<String> vect_gylxerror_FormName = new Vector<String>();
		Vector<String> vect_gylxerror_info = new Vector<String>();
		Vector<String> vect_gylxerror_ID = new Vector<String>();
		Vector<String> vect_gylxsucess_ID = new Vector<String>();
		Vector<String> vect_gylxkong_ID = new Vector<String>();
		Vector<String> vect_bomerror_info = new Vector<String>();
		Vector<String> vect_bomerror_ID = new Vector<String>();
		Vector<String> vect_bomsucess_ID = new Vector<String>();
		Vector<String> vect_bomkong_ID = new Vector<String>();
		try {
			progressbar = new ProgressBarThread("工程BOM及工艺路线批量传递检查" ,"工程BOM及工艺路线批量传递检查中,请稍等...");
			progressbar.start();
			// 实例化数据库连接
			OracleConnect oraconn = new OracleConnect();
			conn = oraconn.getConnection();
			//----------查询中间表是传递状态是N的工艺路线------------
			String sql_gylxN_String = "select * from CUX.CUX_PLM_ROUTING_IFACE where PROCESS_FLAG='"
				+ "N" + "' and Created_By='"+ userName +"'" ;
			System.out.println("【查询语句 N】" + sql_gylxN_String);
			stmt = conn.createStatement();
			reset = stmt.executeQuery(sql_gylxN_String);
			System.out.println("reset==>:"+reset);
			while(reset.next()){
				gylx_flag = true;
				String errorId = reset.getString("ITEM_NUMBER");
				String errorFormName = reset.getString("FORM_NAME");
				String log = reset.getString("ERROR_MSG");
				String error_info = errorId + ",错误信息为:" + log;
				if(!vect_gylxerror_info.contains(error_info)){
					vect_gylxerror_info.add(error_info);
				}
				if(!vect_gylxerror_ID.contains(errorId)){
					vect_gylxerror_ID.add(errorId);
				}
				if(!vect_gylxerror_FormName.contains(errorFormName)){
					vect_gylxerror_FormName.add(errorFormName);
					setFormProperty(errorId, errorFormName, "N", log);
				}
			}
			stmt.close();
			oraconn.closeConn(conn);

			//--------------查询中间表是传递状态是Y的工艺路线--------------------------
			conn = oraconn.getConnection();
			String sql_gylxY_String = "select * from CUX.CUX_PLM_ROUTING_IFACE where PROCESS_FLAG='"
				+ "Y" + "' and Created_By='"+ userName + "'";
			System.out.println("【查询语句 Y】" + sql_gylxY_String);
			stmt = conn.createStatement();
			reset = stmt.executeQuery(sql_gylxY_String);
			while(reset.next()){
				gylx_flag1 = true;
				String sucessId = reset.getString("ITEM_NUMBER");
				String sucessFormName = reset.getString("FORM_NAME");
				if(!vect_gylxsucess_ID.contains(sucessFormName)){
					vect_gylxsucess_ID.add(sucessFormName);
					setFormProperty(sucessId, sucessFormName, "Y", null);
				}
			}
			stmt.close();
			oraconn.closeConn(conn);
			
			//-----------------------查询中间表是传递状态是空的工艺路线---------------------
			conn = oraconn.getConnection();
			String sql_gylxK_String = "select * from CUX.CUX_PLM_ROUTING_IFACE where (PROCESS_FLAG='"
				+ "" + "' or PROCESS_FLAG is null" + ") and Created_By='"+ userName +"'";
			System.out.println("【查询语句 NULL】" + sql_gylxK_String);
			stmt = conn.createStatement();
			reset = stmt.executeQuery(sql_gylxK_String);
			while(reset.next()){
				gylx_flag2 = true;
				String kongFormName = reset.getString("ITEM_NUMBER");
				System.out.println("kong_Id----gylx-->:"+kongFormName);
				if(!vect_gylxkong_ID.contains(kongFormName)){
					vect_gylxkong_ID.add(kongFormName);
				}
			}
			stmt.close();
			oraconn.closeConn(conn);
			
			
			conn = oraconn.getConnection();
			//查询中间表是传递状态是N的BOM
			String sql_bomN_String = "select * from CUX.CUX_PLM_BOM_IFACE where PROCESS_FLAG='"
					+ "N" + "'" + " and ENG_ITEM_FLAG = 'N'" + "and CREATE_BY = '" + userName +"'";
			System.out.println("【查询语句】==gc=>" + sql_bomN_String);
			stmt = conn.createStatement();
			reset = stmt.executeQuery(sql_bomN_String);
			while(reset.next()){
				bom_flag = true;
				String errorId = reset.getString("COMPONENT_ITEM");
				System.out.println("buyId---gc--->:"+errorId);
				String parent_Id = reset.getString("ITEM_NUM");
				String log = reset.getString("ERROR_MSG");
				String error_info = "父ID："+parent_Id+",子ID："+errorId + ",错误信息为:" + log;
//				String error_info = "错误信息为:" + log;
				map.put(parent_Id, "N");
				if(!vect_bomerror_info.contains(error_info)){
					vect_bomerror_info.add(error_info);
				}
				if(!vect_bomerror_ID.contains(parent_Id)){
					vect_bomerror_ID.add(parent_Id);
				}
			}
			stmt.close();
			oraconn.closeConn(conn);
			//查询中间表是传递状态是Y的BOM
			conn = oraconn.getConnection();
			System.out.println("conn====gc>>>:"+conn);
			String sql_Y_String = "select * from CUX.CUX_PLM_BOM_IFACE where PROCESS_FLAG='"
				+ "Y" + "'" + " and ENG_ITEM_FLAG = 'N'" + "and CREATE_BY = '" + userName +"'";
			stmt = conn.createStatement();
			reset = stmt.executeQuery(sql_Y_String);
			while(reset.next()){
				bom_flag1 = true;
				String parent_Id = reset.getString("ITEM_NUM");
				System.out.println("parent_Id---gc--->:"+parent_Id);
				if(!vect_bomsucess_ID.contains(parent_Id)){
					vect_bomsucess_ID.add(parent_Id);
				}
				map.put(parent_Id, "Y");
			}
			stmt.close();
			oraconn.closeConn(conn);
			//查询中间表是传递状态是空的BOM
			conn = oraconn.getConnection();
			System.out.println("conn=====22gc=====>:"+conn);
			String sqlString12 = "select * from CUX.CUX_PLM_BOM_IFACE where (PROCESS_FLAG is null or PROCESS_FLAG = '')　and ENG_ITEM_FLAG = 'N'"
				+ "and CREATE_BY = '" + userName +"'";
			stmt = conn.createStatement();
			reset = stmt.executeQuery(sqlString12);
			while(reset.next()){
				bom_flag2 = true;
				String kong_Id = reset.getString("ITEM_NUM");
				System.out.println("kong_Id---gc--->:"+kong_Id);
				if(!vect_bomkong_ID.contains(kong_Id)){
					vect_bomkong_ID.add(kong_Id);
				}
			}
			stmt.close();
			oraconn.closeConn(conn);
			//如果中间表中PROCESS_FLAG是N的则提示错误信息
			if(gylx_flag || bom_flag){
				writeTxtMessage(vect_gylxerror_info,vect_bomerror_info, dname);
			}
			//如果中间表中PROCESS_FLAG是Y的则提示成功信息
			if(bom_flag1 || bom_flag){
				//设置bomview的描述属性值
				setDescProperty();
			}
			
			//将原表中的数据复制到备份表CUX.CUX_PLM_ROUTING_HIST里面
			copygylxdataTotable();

			//将原表中PROCESS_FLAG是Y或者是N的数据删除
			deletegylxdataFromtable();
			
			//将原表中的数据复制到备份表CUX.CUX_PLM_BOM_HIST里面
			copybomdataTotable();
			
			//将原表中PROCESS_FLAG是Y或者是N的数据删除
			deletebomdataFromtable();
			
			progressbar.stopBar();
			//列出所有可能性
			if(gylx_flag && !gylx_flag1 && !gylx_flag2 && !bom_flag && !bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && gylx_flag1 && !gylx_flag2 && !bom_flag && !bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && !gylx_flag1 && gylx_flag2 && !bom_flag && !bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && !gylx_flag1 && !gylx_flag2 && bom_flag && !bom_flag1 && !bom_flag2){
				MessageBox.post(vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && !gylx_flag1 && !gylx_flag2 && !bom_flag && bom_flag1 && !bom_flag2){
				MessageBox.post(vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && !gylx_flag1 && !gylx_flag2 && !bom_flag && !bom_flag1 && bom_flag2){
				MessageBox.post(vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && gylx_flag1 && !gylx_flag2 && !bom_flag && !bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && !gylx_flag1 && gylx_flag2 && bom_flag && bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n" 
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && !gylx_flag1 && gylx_flag2 && !bom_flag && !bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && gylx_flag1 && !gylx_flag2 && bom_flag && bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n" 
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && !gylx_flag1 && !gylx_flag2 && bom_flag && !bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && gylx_flag1 && gylx_flag2 && !bom_flag && bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n" 
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && !gylx_flag1 && !gylx_flag2 && !bom_flag && bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && gylx_flag1 && gylx_flag2 && bom_flag && !bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n" 
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && !gylx_flag1 && !gylx_flag2 && !bom_flag && !bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && gylx_flag1 && gylx_flag2 && bom_flag && bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n" 
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && gylx_flag1 && gylx_flag2 && !bom_flag && !bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n" 
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && !gylx_flag1 && !gylx_flag2 && bom_flag && bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && gylx_flag1 && !gylx_flag2 && bom_flag && !bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n" 
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && !gylx_flag1 && gylx_flag2 && !bom_flag && bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && gylx_flag1 && !gylx_flag2 && !bom_flag && bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n" 
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && !gylx_flag1 && gylx_flag2 && bom_flag && !bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && gylx_flag1 && !gylx_flag2 && !bom_flag && !bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n" 
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && !gylx_flag1 && gylx_flag2 && bom_flag && bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && !gylx_flag1 && gylx_flag2 && bom_flag && !bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n" 
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && gylx_flag1 && !gylx_flag2 && !bom_flag && bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && !gylx_flag1 && gylx_flag2 && !bom_flag && bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n" 
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && gylx_flag1 && !gylx_flag2 && bom_flag && !bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n"
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && !gylx_flag1 && gylx_flag2 && !bom_flag && !bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n" 
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && gylx_flag1 && !gylx_flag2 && bom_flag && bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n"
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && !gylx_flag1 && !gylx_flag2 && bom_flag && bom_flag1 && !bom_flag2){
				MessageBox.post(vect_bomerror_ID+"BOM传递失败!" + "\n" 
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && gylx_flag1 && gylx_flag2 && !bom_flag && !bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n"
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && !gylx_flag1 && !gylx_flag2 && bom_flag && !bom_flag1 && bom_flag2){
				MessageBox.post(vect_bomerror_ID+"BOM传递失败!" + "\n" 
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && gylx_flag1 && gylx_flag2 && !bom_flag && bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n"
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && !gylx_flag1 && !gylx_flag2 && !bom_flag && bom_flag1 && bom_flag2){
				MessageBox.post(vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n" 
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && gylx_flag1 && gylx_flag2 && bom_flag && !bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n"
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && gylx_flag1 && gylx_flag2 && bom_flag && bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n" 
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && !gylx_flag1 && gylx_flag2 && bom_flag && bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && gylx_flag1 && !gylx_flag2 && bom_flag && bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n"
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && gylx_flag1 && gylx_flag2 && !bom_flag && bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n"
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && gylx_flag1 && gylx_flag2 && bom_flag && !bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n"
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && gylx_flag1 && gylx_flag2 && bom_flag && bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n"
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && gylx_flag1 && gylx_flag2 && !bom_flag && !bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n"
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && !gylx_flag1 && !gylx_flag2 && bom_flag && bom_flag1 && bom_flag2){
				MessageBox.post(vect_bomerror_ID+"BOM传递失败!!" + "\n" 
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && gylx_flag1 && !gylx_flag2 && bom_flag && !bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n"
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && !gylx_flag1 && gylx_flag2 && !bom_flag && bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!!" + "\n" 
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && gylx_flag1 && !gylx_flag2 && !bom_flag && bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && !gylx_flag1 && gylx_flag2 && bom_flag && !bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!!" + "\n" 
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && gylx_flag1 && !gylx_flag2 && !bom_flag && !bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && !gylx_flag1 && gylx_flag2 && bom_flag && bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!!" + "\n" 
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && !gylx_flag1 && gylx_flag2 && bom_flag && !bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && gylx_flag1 && !gylx_flag2 && !bom_flag && bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxsucess_ID+"工艺路线已经成功传递ERP!!" + "\n" 
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && !gylx_flag1 && gylx_flag2 && !bom_flag && bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && gylx_flag1 && !gylx_flag2 && bom_flag && !bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxsucess_ID+"工艺路线已经成功传递ERP!!" + "\n" 
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && !gylx_flag1 && gylx_flag2 && !bom_flag && !bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && gylx_flag1 && !gylx_flag2 && bom_flag && bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxsucess_ID+"工艺路线已经成功传递ERP!!" + "\n" 
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && !gylx_flag1 && !gylx_flag2 && bom_flag && bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && gylx_flag1 && gylx_flag2 && !bom_flag && !bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxsucess_ID+"工艺路线已经成功传递ERP!!" + "\n" 
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && !gylx_flag1 && !gylx_flag2 && bom_flag && !bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && gylx_flag1 && gylx_flag2 && !bom_flag && bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxsucess_ID+"工艺路线已经成功传递ERP!!" + "\n" 
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && !gylx_flag1 && !gylx_flag2 && !bom_flag && bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && gylx_flag1 && gylx_flag2 && bom_flag && !bom_flag1 && !bom_flag2){
				MessageBox.post(vect_gylxsucess_ID+"工艺路线已经成功传递ERP!!" + "\n" 
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(gylx_flag && gylx_flag1 && gylx_flag2 && bom_flag && bom_flag1 && bom_flag2){
				MessageBox.post(vect_gylxerror_ID+"工艺路线传递失败!" + "\n" 
						+ vect_gylxsucess_ID+"工艺路线已经成功传递ERP!" + "\n"
						+ vect_gylxkong_ID+"工艺路线已经传递至中间表等待ERP接收确认!" + "\n"
						+ vect_bomerror_ID+"BOM传递失败!" + "\n"
						+ vect_bomsucess_ID+"BOM已经成功传递ERP!" + "\n"
						+ vect_bomkong_ID+"BOM已经传递至中间表等待ERP接收确认!" + "\n"
						+ "工程BOM及工艺路线批量传递检查完毕!", "提示", MessageBox.INFORMATION);
			}
			if(!gylx_flag && !gylx_flag1 && !gylx_flag2 && !bom_flag && !bom_flag && !bom_flag2){
				MessageBox.post("目前没有您需要检查的工艺路线和BOM,检查完毕!", "提示", MessageBox.INFORMATION);
			}
			//自动打开日志文件
			if(gylx_flag || bom_flag){
				Runtime.getRuntime().exec("cmd /c "+System.getenv("TEMP")+"/"+dname);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	//设置bomview的描述属性值
	public void setDescProperty(){
		System.out.println("map=gc===>:"+map.size());
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
					//设置bomview的描述属性值
					TCComponentItemRevision rev = sucess_item.getLatestItemRevision();
					TCComponentBOMViewRevision bomview = (TCComponentBOMViewRevision) rev.getRelatedComponent(relation1);
					if(bomview!=null){
						bypass.setpass();
						bomview.setProperty(attri, value);
					}
				} catch (TCException e) {
					e.printStackTrace();
				}
			}
		}
		bypass.closepass();
	}
	
	
	//将原表中的数据复制到备份表CUX.CUX_PLM_BOM_HIST里面
	public void copybomdataTotable(){
		try {
			// 实例化数据库连接
			OracleConnect oraconn = new OracleConnect();
			conn = oraconn.getConnection();
			String sql_copy = "insert into CUX.CUX_PLM_BOM_HIST select * from CUX.CUX_PLM_BOM_IFACE where (PROCESS_FLAG='"
				+ "Y" + "' or PROCESS_FLAG='" + "N" +"')" + " and ENG_ITEM_FLAG = 'N'" + "and CREATE_BY = '" + userName +"'";
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
	
	//将原表中的数据复制到备份表CUX.CUX_PLM_ROUTING_HIST里面
	public void copygylxdataTotable(){
		try {
			// 实例化数据库连接
			OracleConnect oraconn = new OracleConnect();
			conn = oraconn.getConnection();
			String sql_copy = "insert into CUX.CUX_PLM_ROUTING_HIST select * from CUX.CUX_PLM_ROUTING_IFACE where (PROCESS_FLAG='"
				+ "Y" + "' or PROCESS_FLAG='" + "N" +"')" + "and Created_By = '" + userName +"'";
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
	public void deletebomdataFromtable(){
		try {
			// 实例化数据库连接
			OracleConnect oraconn = new OracleConnect();
			conn = oraconn.getConnection();
			System.out.println("conn---gc====>:"+conn);
			String sql_delete = "delete from CUX.CUX_PLM_BOM_IFACE where (PROCESS_FLAG='"
				+ "Y" + "' or PROCESS_FLAG='" + "N" +"')" + " and ENG_ITEM_FLAG = 'N'" + "and CREATE_BY = '" + userName +"'";
			System.out.println("【删除语句】===gc==>:" + sql_delete);
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
	
	
	//将原表中PROCESS_FLAG是Y或者是N的数据删除
	public void deletegylxdataFromtable(){
		try {
			// 实例化数据库连接
			OracleConnect oraconn = new OracleConnect();
			conn = oraconn.getConnection();
			System.out.println("conn---2====>:"+conn);
			String sql_delete = "delete from CUX.CUX_PLM_ROUTING_IFACE where (PROCESS_FLAG='"
				+ "Y" + "' or PROCESS_FLAG='" + "N" +"')" + "and Created_By = '" + userName +"'";
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
	
	//设置form值
	public void setFormProperty(String item_id, String formName, String tranlogo, String error){
		InterfaceAIFComponent[] items = query(item_id);
		if(items == null || items.length==0){
			System.out.println("ERP接受后没有搜索到对应的Item");
		}else{
			try {
				TCComponentItem sucess_item = (TCComponentItem)items[0];
				TCComponentItemRevision sucess_itemrev = sucess_item.getLatestItemRevision();
				TCComponent[] tccomponent = sucess_itemrev.getRelatedComponents(relation);
				for(int i = 0; i < tccomponent.length; i++){
					if(tccomponent[i] instanceof TCComponentForm){
						TCComponentForm tccomponentForm = (TCComponentForm)tccomponent[i];
						String tmpFormName = tccomponentForm.getProperty("object_name");
						if(tmpFormName.equals(formName)){
							if(tranlogo != null){
								bypass.setpass();
								tccomponentForm.getFormTCProperty(this.tranlogo).setStringValue(tranlogo);
							}
							if(error != null){
								bypass.setpass();
								tccomponentForm.getFormTCProperty(this.error).setStringValue(tranlogo);
							}
						}
					}
				}
			} catch (TCException e) {
				e.printStackTrace();
			}
		}
		bypass.closepass();
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
	public void writeTxtMessage(Vector<String> vec_gylxerror,Vector<String> vec_bomerror,String name) {
		try {
			PrintWriter txt = new PrintWriter(new FileWriter(System.getenv("TEMP")+"/"+name,true),true);
			if(gylx_flag && bom_flag){
				txt.print("                    工艺路线日志信息：                 "+"\n");
				for (int i = 0; i < vec_gylxerror.size(); i++) {
					String gylx_values = vec_gylxerror.get(i);
					txt.print(gylx_values+"\n");
				}
				txt.print("                    BOM日志信息：                 "+"\n");
				for (int i = 0; i < vec_bomerror.size(); i++) {
					String bom_values = vec_bomerror.get(i);
					txt.print(bom_values+"\n");
				}
			}
			if(gylx_flag && !bom_flag){
				txt.print("                    工艺路线日志信息：             "+"\n");
				for (int i = 0; i < vec_gylxerror.size(); i++) {
					String gylx_values = vec_gylxerror.get(i);
					txt.print(gylx_values+"\n");
				}
				txt.print("                    BOM日志信息：                 "+"\n");
				txt.print("BOM没有错误！"+"\n");
			}
			if(!gylx_flag && bom_flag){
				txt.print("                    工艺路线日志信息：             "+"\n");
				txt.print("工艺路线没有错误！"+"\n");
				txt.print("                    BOM日志信息：                 "+"\n");
				for (int i = 0; i < vec_bomerror.size(); i++) {
					String values = vec_bomerror.get(i);
					txt.print(values+"\n");
				}
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

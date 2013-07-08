package com.origin.rac.sac.sendbominfo;

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
import com.teamcenter.rac.kernel.TCComponentBOMViewRevision;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCTextService;
import com.teamcenter.rac.util.MessageBox;

public class CheckYXBomInfoCommand extends AbstractAIFCommand {

	private TCSession session = null;
	public Connection conn = null;
	public ReadProperties read = null;
	private Statement stmt = null;
	private ResultSet reset = null;
	private boolean flag = false;
	private boolean flag1 = false;
	private boolean flag2 = false;
	private HashMap<String,String> map = new HashMap<String,String>();
	private String relation1 = "structure_revisions";
	private String attri = "object_desc";
	private S4Bypass bypass = null;
	private ProgressBarThread progressbar = null ;
	private String userName = "";
	private String rzname= "ԭ��BOM���ݼ����־";
	private String dname= "";
	
	public CheckYXBomInfoCommand(AbstractAIFUIApplication app) {
		session = (TCSession) app.getSession();
		bypass = new S4Bypass(session);
		//ִ�в˵�����Ա
		userName = session.getUser().toString();
		System.out.println("userName====>:"+userName);
		dname = rzname+getDate()+".txt";
		Vector<String> vect_error_info = new Vector<String>();
		Vector<String> vect_error_ID = new Vector<String>();
		Vector<String> vect_sucess_ID = new Vector<String>();
		Vector<String> vect_kong_ID = new Vector<String>();
		try {
			progressbar = new ProgressBarThread("ԭ��BOM���ݼ��" ,"ԭ��BOM���ݼ����,���Ե�...");
			progressbar.start();
			// ʵ�������ݿ�����
			OracleConnect oraconn = new OracleConnect();
			conn = oraconn.getConnection();
			//��ѯ�м���Ǵ���״̬��N��BOM
			String sql_N_String = "select * from CUX.CUX_PLM_BOM_IFACE where PROCESS_FLAG='"
					+ "N" + "'" + " and ENG_ITEM_FLAG = 'Y'" + "and CREATE_BY = '" + userName +"'";
			System.out.println("����ѯ��䡿" + sql_N_String);
			stmt = conn.createStatement();
			reset = stmt.executeQuery(sql_N_String);
			while(reset.next()){
				flag = true;
				String errorId = reset.getString("COMPONENT_ITEM");
				System.out.println("buyId------>:"+errorId);
				String parent_Id = reset.getString("ITEM_NUM");
				String log = reset.getString("ERROR_MSG");
				String error_info = "��ID��"+parent_Id+",��ID��"+errorId + ",������ϢΪ:" + log;
//				String error_info = "������ϢΪ:" + log;
				map.put(parent_Id, "N");
				if(!vect_error_ID.contains(parent_Id)){
					vect_error_ID.add(parent_Id);
				}
				if(!vect_error_info.contains(error_info)){
					vect_error_info.add(error_info);
				}
			}
			stmt.close();
			oraconn.closeConn(conn);
			//��ѯ�м���Ǵ���״̬��Y��BOM
			conn = oraconn.getConnection();
			String sql_Y_String = "select * from CUX.CUX_PLM_BOM_IFACE where PROCESS_FLAG='"
				+ "Y" + "'" + " and ENG_ITEM_FLAG = 'Y'" + "and CREATE_BY = '" + userName +"'";
			stmt = conn.createStatement();
			reset = stmt.executeQuery(sql_Y_String);
			while(reset.next()){
				flag1 = true;
				String sucess_Id = reset.getString("COMPONENT_ITEM");
				String parent_Id = reset.getString("ITEM_NUM");
				System.out.println("sucess_Id------>:"+sucess_Id);
				if(!vect_sucess_ID.contains(parent_Id)){
					vect_sucess_ID.add(parent_Id);
				}
				map.put(parent_Id, "Y");
			}
			stmt.close();
			oraconn.closeConn(conn);
			//��ѯ�м���Ǵ���״̬�ǿյ�BOM
			conn = oraconn.getConnection();
			String sqlString12 = "select * from CUX.CUX_PLM_BOM_IFACE where (PROCESS_FLAG is null or PROCESS_FLAG = '')��and ENG_ITEM_FLAG = 'Y'"
				+ "and CREATE_BY = '" + userName +"'";
			stmt = conn.createStatement();
			reset = stmt.executeQuery(sqlString12);
			while(reset.next()){
				flag2 = true;
				String kong_Id = reset.getString("ITEM_NUM");
				System.out.println("kong_Id------>:"+kong_Id);
				if(!vect_kong_ID.contains(kong_Id)){
					vect_kong_ID.add(kong_Id);
				}
			}
			stmt.close();
			oraconn.closeConn(conn);
			//����м����PROCESS_FLAG��N������ʾ������Ϣ
			if(flag){
				writeTxtMessage(vect_error_info,dname);
			}
			//����м����PROCESS_FLAG��Y������ʾ�ɹ���Ϣ
			if(flag1 || flag){
				//����bomview����������ֵ
				setDescProperty();
			}
			
			//��ԭ���е����ݸ��Ƶ����ݱ�CUX.CUX_PLM_BOM_HIST����
			copydataTotable();
			
			//��ԭ����PROCESS_FLAG��Y������N������ɾ��
			deletedataFromtable();
			
			progressbar.stopBar();
			//����м����PROCESS_FLAG����Y����N���пյ�����ʾ��Ϣ
			if(flag1 && flag && flag2){
				MessageBox.post(vect_error_ID+"����ʧ��!"
						+"\n"+ vect_sucess_ID+"�Ѿ��ɹ�����ERP!"
						+"\n" + vect_kong_ID+"�Ѿ��������м���ȴ�ERP����ȷ��!"
						+ "\n" + "ԭ��BOM���ݼ�����!", "��ʾ", MessageBox.INFORMATION);
			}
			
			//����м����PROCESS_FLAG��Y��N������ʾ��Ϣ
			if(flag1 && flag && !flag2){
				MessageBox.post(vect_error_ID+"����ʧ��!"
						+"\n"+ vect_sucess_ID+"�Ѿ��ɹ�����ERP!"
						+ "\n" +"ԭ��BOM���ݼ�����!", "��ʾ", MessageBox.INFORMATION);
			}
			//����м����PROCESS_FLAG�пպ�Y������ʾ��Ϣ
			if(flag1 && !flag && flag2){
				MessageBox.post(vect_sucess_ID+"�Ѿ��ɹ�����ERP!!"
						+"\n"+ vect_kong_ID+"�Ѿ��������м���ȴ�ERP����ȷ��!"
						+ "\n" +"ԭ��BOM���ݼ�����!", "��ʾ", MessageBox.INFORMATION);
			}
			//����м����PROCESS_FLAGֻ�ǿյ�����ʾ��Ϣ
			if(!flag1 && !flag && flag2){
				MessageBox.post(vect_kong_ID+"�Ѿ��������м���ȴ�ERP����ȷ��!"
						+ "\n" +"ԭ��BOM���ݼ�����!", "��ʾ", MessageBox.INFORMATION);
			}
			//����м����PROCESS_FLAG��Ϊ�պ�N������ʾ��Ϣ
			if(!flag1 && flag && flag2){
				MessageBox.post(vect_error_ID+"����ʧ��!"
						+"\n"+ vect_kong_ID+"�Ѿ��������м���ȴ�ERP����ȷ��!"
						+ "\n" +"ԭ��BOM���ݼ�����!", "��ʾ", MessageBox.INFORMATION);
			}
			//����м����PROCESS_FLAGֻ��N������ʾ��Ϣ
			if(!flag1 && flag && !flag2){
				MessageBox.post(vect_error_ID+"����ʧ��!"
						+ "\n" +"ԭ��BOM���ݼ�����!", "��ʾ", MessageBox.INFORMATION);
			}
			//����м����PROCESS_FLAGֻ��Y������ʾ��Ϣ
			if(flag1 && !flag && !flag2){
				MessageBox.post(vect_sucess_ID+"�Ѿ��ɹ�����ERP!"
						+ "\n" +"ԭ��BOM���ݼ�����!", "��ʾ", MessageBox.INFORMATION);
			}
			if(!flag && !flag1 && !flag2){
				MessageBox.post("Ŀǰû������Ҫ����ԭ��BOM,������!", "��ʾ", MessageBox.INFORMATION);
			}
			//�Զ�����־�ļ�
			if(flag){
				Runtime.getRuntime().exec("cmd /c "+System.getenv("TEMP")+"/"+dname);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	//����bomview����������ֵ
	public void setDescProperty(){
		System.out.println("map====>:"+map.size());
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String item_id = (String) iterator.next();
			String value = map.get(item_id);
			InterfaceAIFComponent[] items = query(item_id);
			if(items == null || items.length==0){
				System.out.println("ERP���ܺ�û����������Ӧ��Item");
			}else{
				TCComponentItem sucess_item = (TCComponentItem) items[0];
				try {
					//����bomview����������ֵ
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
	
	
	//��ԭ���е����ݸ��Ƶ����ݱ�CUX.CUX_PLM_BOM_HIST����
	public void copydataTotable(){
		try {
			// ʵ�������ݿ�����
			OracleConnect oraconn = new OracleConnect();
			conn = oraconn.getConnection();
			String sql_copy = "insert into CUX.CUX_PLM_BOM_HIST select * from CUX.CUX_PLM_BOM_IFACE where (PROCESS_FLAG='"
				+ "Y" + "' or PROCESS_FLAG='" + "N" +"')" + " and ENG_ITEM_FLAG = 'Y'" + "and CREATE_BY = '" + userName +"'";
			System.out.println("��������䡿" + sql_copy);
			stmt = conn.createStatement();
			int rs_copy = stmt.executeUpdate(sql_copy);
			if (rs_copy > 0) {
				System.out.println("��Ϣ�����ɹ�");
			} else {
				System.out.println("��Ϣ����ʧ��");
			}
			stmt.close();
			oraconn.closeConn(conn);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//��ԭ����PROCESS_FLAG��Y������N������ɾ��
	public void deletedataFromtable(){
		try {
			// ʵ�������ݿ�����
			OracleConnect oraconn = new OracleConnect();
			conn = oraconn.getConnection();
			String sql_delete = "delete from CUX.CUX_PLM_BOM_IFACE where (PROCESS_FLAG='"
				+ "Y" + "' or PROCESS_FLAG='" + "N" +"')" + " and ENG_ITEM_FLAG = 'Y'" + "and CREATE_BY = '" + userName +"'";
			System.out.println("��ɾ����䡿" + sql_delete);
			stmt = conn.createStatement();
			int rs12 = stmt.executeUpdate(sql_delete);
			if (rs12 > 0) {
				System.out.println("��Ϣɾ���ɹ�");
			} else {
				System.out.println("��Ϣɾ��ʧ��");
			}
			stmt.close();
			oraconn.closeConn(conn);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//����ϵͳ����� ID��ѯ
	public InterfaceAIFComponent[] query(String id){
		InterfaceAIFComponent[] items = null;
		try {
			TCTextService tcService =session.getTextService();
			String askKey[]={tcService.getTextValue("ItemID")};
			String askValue[]={id};
			items =  session.search("����� ID", askKey, askValue);
		} catch (TCException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
	
	//д����־
	public void writeTxtMessage(Vector<String> vec_error,String name) {
		try {
			PrintWriter txt = new PrintWriter(new FileWriter(System.getenv("TEMP")+"/"+name,true),true);
			txt.print("                    ��־��Ϣ��                 "+"\n");
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
package cn.com.origin.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;




public class Test {

	/**
	 * @param args
	 */
	
	public static Connection		conn	= null;
	public static ReadProperties	read	= null;
	private static Statement stmt = null;
	private static ResultSet reset = null;
	private static String[] str = {"0","WRK000001389","3"};
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Vector<String> vec = new Vector<String>();
	private static Vector<String> vec1 = new Vector<String>();
	private static String[] str1 = {"000012","WRK000001389","WRK000000506"};
	private static boolean flag =false;
	private static String eco_code = "";
	private static int intLiushui = 0;
	
	
	public static void main(String[] args) throws IOException {
		try {
			
			
			// 实例化数据库连接
			PLMOracleConnect oraconn = new PLMOracleConnect();
			conn = oraconn.getConnection();
			System.out.println("conn===>:"+conn);
			String sql = "select * from ECOWATERCODE where ORGANIZATION_CODE = 'MZ0'";
			
			stmt = conn.createStatement();
			reset = stmt.executeQuery(sql);
			if(reset!=null && reset.next()){
				eco_code = reset.getString("CHANGE_NOTICE");
				intLiushui = Integer.parseInt(eco_code);
				
			}
			intLiushui += 1;
			stmt.close();
			oraconn.closeConn(conn);
			System.out.println("intLiushui---:"+intLiushui);
			
			/*conn = oraconn.getConnection();
			String upsql = "update ECOWATERCODE set CHANGE_NOTICE = '" + intLiushui + "' where ORGANIZATION_CODE = 'MZ0'";
			stmt = conn.createStatement();
			int k = stmt.executeUpdate(upsql);
			if (k > 0) {
				System.out.println("信息更新成功");
			} else {
				System.out.println("更新失败");
			}
			oraconn.closeConn(conn);*/
			/*// 实例化数据库连接
			OracleConnect oraconn = new OracleConnect();
			conn = oraconn.getConnection();
			String sql_delete = "delete from CUX.CUX_PLM_ECO_IFACE where CHANGE_NOTICE='MZ0_2032'";
			stmt = conn.createStatement();
			int rs12 = stmt.executeUpdate(sql_delete);
			if (rs12 > 0) {
				System.out.println("信息删除成功");
			} else {
				System.out.println("信息删除失败");
			}
			stmt.close();
			long time1 = System.currentTimeMillis();*/
			/*for (int i = 0; i < str1.length; i++) {
				String id = str1[i];
				vec.add(id);
			}*/
			/*String process_flag = "null";
			System.out.println("conn===>:"+conn);
			for (int i = 0; i < str1.length; i++) {
				String id = str1[i];
				String sql = "select * from CUX_PLM_ORG_ITEM_V where Org_Code = 'MZ0'" + " and Item_Num = '"+ id +"'";
				stmt = conn.createStatement();
				reset = stmt.executeQuery(sql);
				System.out.println("reset===>:"+reset);
				if(reset!=null && reset.next())
				{
//					process_flag = reset.getString("PROCESS_FLAG");
//					System.out.println("process_flag==*****************==>:"+process_flag);
					while(reset.next())
					{
						flag = true;
//						System.out.println(reset.getObject("LINE_NUMBER").getClass().getName());
						String id = reset.getString("DEMAND_CLASS");
						System.out.println("id====>:"+id);
						if(id ==null || "null".equals(id)){
							id = "0";
						}
						System.out.println("id-->:"+id);
						vec.add(id);
					}
				}else{
					flag = true;
					vec.add(id);
				}
				stmt.close();
			}
			System.out.println("flag--->:"+flag);*/
			
//			String sql = "select * from CUX_PLM_PR_IMP_INFACE where ITEM_NUMBER = 'WRK000001389'";
			
			/*if(reset!=null )
			{
				while(reset.next())
				{
					String[] tempvalue = new String[3];
					tempvalue[0] = String.valueOf(reset.getInt("CONTRACT_NUMBER"));
					System.out.println("tempvalue[0]====>:"+tempvalue[0]);
					tempvalue[1] = reset.getString("PROJECT_NAME");
					tempvalue[2] = reset.getString("PROCESS_FLAG");
					System.out.println("tempvalue[2]----->:"+tempvalue[2]);
					Date date = reset.getDate("DELIVERY_DATE");
					System.out.println("22---->:"+date);
					String ms = "";
					if(date == null){
						ms = "";
						System.out.println("ms--->:"+ms);
					}else{
						ms = sdf.format(date);
						System.out.println("ms===>:"+ms);
					}
				}
			}*/
			/*System.out.println(System.currentTimeMillis()-time1);
			System.out.println("vec---size--->:"+vec.size());
			
			for (int i = 0; i < str1.length; i++) {
				if(!vec.contains(str1[i])){
					flag = true;
					vec1.add(str1[i]);
				}
			}
			if(flag){
				System.out.println("vec1===>:"+vec1);
			}*/
			
			
			/*for (int i= 0; i< 2; i++) {
				if(str[1].equals("0")){
					System.out.println("SSSSSS");
					String sql = "insert into CUX.CUX_PLM_INV_ITEM_IFACE(ORGANIZATION_NAME,ITEM_NUMBER,ITEM_DESCRIPTION,SAC_INV_CATEGORY,SAC_FIN_CATEGORY" +
							",SAC_PLAN_CATEGORY,SAC_PRO_CATEGORY) values ('"
						+ "SAC_NZMZ_INV1" + "','" + "2001"+ "','" + "AA" + "','" + "11" + "','" + "22"+ "','" + "33"+ "','" + "44" +"')";
					stmt = conn.createStatement();
					int k = stmt.executeUpdate(sql);
					if (k > 0) {
						System.out.println("信息插入成功");
					} else {
						System.out.println("插入失败");
					}
					stmt.close();
				}else{
					System.out.println("MMMMMMM");
					String sql = "insert into CUX.CUX_PLM_INV_ITEM_IFACE(ORGANIZATION_NAME,ITEM_NUMBER,ITEM_DESCRIPTION,SAC_INV_CATEGORY,SAC_FIN_CATEGORY" +
							",SAC_PLAN_CATEGORY,SAC_PRO_CATEGORY) values ('"
						+ "SAC_NZMZ_INV2" + "','" + "2007" + "','" + "BB" + "','" + "11" + "','" + "22"+ "','" + "33"+ "','" + "44" +"')";
					stmt = conn.createStatement();
					int k = stmt.executeUpdate(sql);
					if (k > 0) {
						System.out.println("信息插入成功");
					} else {
						System.out.println("插入失败");
					}
					stmt.close();
					String sql1 = "insert into CUX.CUX_PLM_INV_ITEM_IFACE(ORGANIZATION_NAME,ITEM_NUMBER,ITEM_DESCRIPTION,SAC_INV_CATEGORY,SAC_FIN_CATEGORY" +
							",SAC_PLAN_CATEGORY,SAC_PRO_CATEGORY) values ('"
						+ "SAC_物料组织" + "','" + "2003"+ "','" + "CCs" + "','" + "11" + "','" + "22"+ "','" + "33"+ "','" + "44" + "')";
					stmt = conn.createStatement();
					int j = stmt.executeUpdate(sql1);
					if (j > 0) {
						System.out.println("111信息插入成功");
					} else {
						System.out.println("1111插入失败");
					}
					stmt.close();
				}
			}*/
			
			/*String sql_N_String = "select * from CUX.CUX_PLM_BOM_IFACE where PROCESS_FLAG='"
				+ "N" + "'" + "and ENG_ITEM_FLAG = 'Y'";
			System.out.println("sql_N_String===>:"+sql_N_String);*/
//			String sql_delete = "delete from CUX.CUX_PLM_INV_ITEM_IFACE where PROCESS_FLAG='"
//				+ "Y" + "' or PROCESS_FLAG='" + "N" +"'";
//			System.out.println("【删除语句】" + sql_delete);
			/*Date date = new Date();
			System.out.println("date--->:"+date);
			String plmCreate_date = PublicMethod.getYearMonthDateHoursMinutesSeconds();
			System.out.println("plmCreate_date===>:"+plmCreate_date);
			
			
			String sql = "insert into CUX.CUX_PLM_INV_ITEM_IFACE(ORGANIZATION_CODE,CREATION_DATE) values ('"
				+ "SAC_NZMZ_INV1"
				+ "',"
				+ "to_date('"+plmCreate_date+"','yyyyMMddHH24miss')"
				+ ")";
			System.out.println("【删除语句】" + sql);
			stmt = conn.createStatement();
			int i = stmt.executeUpdate(sql);
			if (i > 0) {
				System.out.println("信息插入成功");
			} else {
				System.out.println("插入失败");
			}
			stmt.close();*/
//			oraconn.closeConn(conn);
			/*System.out.println("process_flag===>:"+process_flag);
			if(!"".equals(process_flag)){
				System.out.println("FFF");
				if("".equals(process_flag) || process_flag==null){
					System.out.println("MMMMMMMMMMM");
				}
				if(process_flag!=null){
					
					System.out.println("MMMMMMMMMMM");
				}else{
					System.out.println("PPPPPPPPPPPPPPP");
				}
			}else{
				System.out.println("BBBB");
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

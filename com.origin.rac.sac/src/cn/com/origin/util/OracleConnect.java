package cn.com.origin.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cn.com.origin.util.ReadProperties;

	
	public class OracleConnect {
		
		// 定义ORACLE的数据库驱动程序
		public static final String DBDRIVER = "oracle.jdbc.driver.OracleDriver";
		// 定义ORACLE数据库的连接地址
		public static String DBURL = null;

		public Connection getConnection() throws IOException {
			ReadProperties read=new ReadProperties();
			String ip_oracle=read.readProperties("ip_oracle");
			String port_oracle=read.readProperties("port_oracle");
			String SID=read.readProperties("SID");
			
			DBURL="jdbc:oracle:thin:@"+ip_oracle+":"+port_oracle+":"+SID;
			// ORACLE数据库的连接用户名
			String DBUSER=read.readProperties("DBUSER");
			// ORACLE数据库的连接密码
			String DBPASS=read.readProperties("DBPASS");
			
			Connection conn = null; // 数据库连接
			try {
				Class.forName(DBDRIVER);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 加载驱动程序
			System.out.println("加载驱动成功！");
			try {
				conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("连接成功！");
			return conn;
		}

		//关闭连接
		public void closeConn(Connection conn)
		{
			 try{   
		            if(conn != null){   
		                conn.close();   
		            }   
		        }   
		        catch(Exception e){   
		            System.out.println("数据库关闭失败");   
		            e.printStackTrace();   
		        }   
		}

}

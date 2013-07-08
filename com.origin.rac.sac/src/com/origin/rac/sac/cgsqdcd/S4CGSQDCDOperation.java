package com.origin.rac.sac.cgsqdcd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cn.com.origin.util.OracleConnect;
import cn.com.origin.util.ProgressBarThread;

import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class S4CGSQDCDOperation extends AbstractAIFOperation {

	
	private AbstractAIFUIApplication app = null;
	private TCSession session;
	private String ywst_str = "";//业务实体
	private String kczz_str = "";//库存组织
	private String mddlx_str = "";//目的地类型
	private String ly_str = "";//来源
	private String cgsq_str = "";//采购申请类型
	private ProgressBarThread progressbar = null ;
	private ArrayList<S4WLInfo> wlinfolist = null;
	//传递相关变量
	private String applyNumber = "";
	private boolean flag_cgsq = false;
	private String error_msg = "";
	
	
	
	public S4CGSQDCDOperation(AbstractAIFUIApplication application,ArrayList<S4WLInfo> list,String s1,String s2,String s3,
			String s4,String s5,String s6){
		this.app = application;
		session= (TCSession) app.getSession();
		this.wlinfolist =list;
		this.ywst_str = s1;
		this.kczz_str = s2;
		this.applyNumber = s3;
		this.cgsq_str = s4;
		this.mddlx_str = s5;
		this.ly_str = s6;
		
		
	}
	
	@Override
	public void executeOperation() throws Exception {
		
		progressbar = new ProgressBarThread("采购申请单传递" ,"采购申请单传递中,请稍等...");
		progressbar.start();
		System.out.println("ywst_str--->:"+ywst_str);
		System.out.println("kczz_str===--->:"+kczz_str);
		System.out.println("cgsq_str====--->:"+cgsq_str);
		boolean isOK = insertintotable();
		System.out.println("flag_cgsq---->:"+flag_cgsq);
		if(flag_cgsq){
			MessageBox.post("传递错误,错误信息为:"+error_msg,"错误",MessageBox.ERROR);
			return;
		}else{
			if(isOK)
			{
				progressbar.stopBar();
				
				MessageBox.post("采购申请单已经传递到中间表,等待ERP接收!","提示",MessageBox.WARNING);
				return;
			}else{
				progressbar.stopBar();
				MessageBox.post("采购申请传递失败","提示",MessageBox.WARNING);
				return;
			}
		}
	}
	
	
	//插入中间表
	private boolean insertintotable()
	{
		boolean isOK = false;
		if(wlinfolist!=null && wlinfolist.size()>0)
		{			
			// 实例化数据库连接
			OracleConnect oraconn = new OracleConnect();
			try {
				Connection conn = oraconn.getConnection();
				
				//数据准备
				
//				//PLM创建时间
//				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				Date date=new Date();   
//				String dtime=sdf.format(date);  
				//当前用户
				String user = session.getUser().toString();
				int m = user.indexOf(" (");
				String userName = user.substring(0, m);
				System.out.println("userName--->:"+userName);
				String userName1 = userName + ",";
				System.out.println("userName1--======->:"+userName1);

				for(int i=0;i<wlinfolist.size();i++)
				{
					S4WLInfo oneinfo = wlinfolist.get(i);
					String sql = "insert into CUX_PLM_PR_IMP_INFACE(" +
					"OPERATING_UNIT," +
					"DEST_ORGANIZATION," +
					"DELIVER_TO_LOCATION," +
					"SEGMENT1," +
					"DESCRIPTION," +
					
					"DOCUMENT_TYPE_DISPLAY," +
					"PROJECT_NUMBER," +
					"PROJECT_NAME," +
					"ITEM_NUMBER," +
					"QUANTITY," +
					"NEED_BY_DATE," +
					"REQUESTOR," +
					"DESTINATION_TYPE_DISP," +
					"SOURCE_TYPE_DISP," +

					
					"IFACE_ID," +
					
					"CREATED_BY," +
					"CREATION_DATE," +
					"LAST_UPDATE_DATE," +
					"BATCH_ID" +")" +
					"values(" +
					"'"+ywst_str+"'," +
					"'"+kczz_str+"'," +
					"'"+oneinfo.getAddress()+"'," +
					"'"+applyNumber+"'," +	
					"'"+oneinfo.getRemark()+"'," +	
					"'"+cgsq_str+"'," +	
					"'"+oneinfo.getHetonghao()+"'," +
					"'"+oneinfo.getGcmingcheng()+"'," +
					"'"+oneinfo.getWlbianma()+"'," +
					""+oneinfo.getShuliang()+"," +
					"fnd_conc_date.STRING_TO_DATE('"+oneinfo.getNeedtime()+"')," +
					"'"+userName1+"'," +
					"'"+mddlx_str+"'," +
					"'"+ly_str+"'," +
					"CUX_PLM_PR_IMP_INFACE_s.Nextval," +
					"'"+userName1+"'," +
					"SYSDATE," +
					"SYSDATE," +
					"-1)";
					System.out.println(sql);
					try {
						Statement stmt = conn.createStatement();
						int k = 0;
						try {
							k = stmt.executeUpdate(sql);
						} catch (Exception e) {
							flag_cgsq = true;
							e.printStackTrace();
							error_msg = e.getMessage();
							progressbar.stopBar();
							break;
						}
						if (k > 0) {
							System.out.println("信息插入成功");
						} else {
							System.out.println("插入失败");
						}
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				oraconn.closeConn(conn);
			} catch (IOException e) {
				e.printStackTrace();
			}
			isOK = true;
		}
		return isOK;
	}

}

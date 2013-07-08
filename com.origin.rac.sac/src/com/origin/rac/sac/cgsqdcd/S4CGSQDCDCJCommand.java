package com.origin.rac.sac.cgsqdcd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import cn.com.origin.util.OracleConnect;
import cn.com.origin.util.ProgressBarThread;
import cn.com.origin.util.S4Bypass;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentItemType;
import com.teamcenter.rac.kernel.TCComponentTcFile;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class S4CGSQDCDCJCommand extends AbstractAIFCommand{

	private AbstractAIFApplication app = null;
	private TCSession session;
	private TCComponentItem targetitem = null;
	
	//传递相关变量
	private String applyNumber;
	//private ArrayList<S4WLInfo> wlinfolist = null;
	
	//旁路
	private S4Bypass bypass = null;
	private ProgressBarThread progressbar = null ;
	private boolean flag_read = false;
	private String rzname= "采购申请单传递检查日志";
	private String dname= "";
	
	
	public S4CGSQDCDCJCommand(AbstractAIFApplication app,TCComponentItem targetitem)
	{
		this.app = app;
		this.targetitem = targetitem;
	}

	@Override
	public void executeModal() throws Exception {
		// TODO Auto-generated method stub
		progressbar = new ProgressBarThread("采购申请单传递检查" ,"采购申请单传递检查中,请稍等...");
		progressbar.start();
		this.session = (TCSession) this.app.getSession();
		this.bypass = new S4Bypass(this.session);
		TCComponentItemRevision itemrev = targetitem.getLatestItemRevision();
		TCComponentForm nowform = (TCComponentForm) itemrev.getRelatedComponent("IMAN_master_form_rev");
		
		String s4Passing_State = nowform.getProperty("s4Passing_State");
		if(s4Passing_State.equals("Y"))
		{
			progressbar.stopBar();
			MessageBox.post("采购申请已成功传递","提示",MessageBox.WARNING);
			return;
		}
		
		
		
		//读取excel文件
		TCComponent[] tempdataset = itemrev.getRelatedComponents("IMAN_specification");
		if(tempdataset!=null && tempdataset.length==1)
		{
			String filepath = "";
			TCComponentDataset xlsdataset = (TCComponentDataset) tempdataset[0];
			TCComponentTcFile[] tcfiles = ((TCComponentDataset) xlsdataset).getTcFiles();
			if(tcfiles!=null && tcfiles.length>0)
			{
				TCComponentTcFile tcfile = tcfiles[0];
				File onefile = tcfile.getFmsFile();
				onefile.setReadable(true);
				filepath = onefile.getPath();
			}
			if(!filepath.equals(""))
			{
				File readfile = new File(filepath);
				if(readfile.exists())
				{
					//读取excel数据
					System.out.println(filepath);
					InputStream is = new FileInputStream(readfile);
					Workbook workbook = null;
					if(filepath.endsWith(".xls")) {
						workbook = new HSSFWorkbook(is);//Excel 2003
					}
					if(workbook !=null)
					{
						int sheetNum = workbook.getNumberOfSheets();//得到Sheet的个数
						HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
						readExcel(sheet);
						if(flag_read){
							progressbar.stopBar();
							MessageBox.post("读取模板申请编号有错!","提示",MessageBox.WARNING);
							return;
						}
						if(!applyNumber.equals(""))
						{
							String qryresult = selectFromTable();
							System.out.println("qryresult=====>:"+qryresult);
							if(!"".equals(qryresult))
							{
								if(qryresult!=null){
									if(qryresult.equals("Y"))
									{
										progressbar.stopBar();
										MessageBox.post("采购申请在ERP中已创建成功!","提示",MessageBox.WARNING);
										
										bypass.setpass();
										nowform.setProperty("s4Passing_State", "Y");
										nowform.refresh();
										bypass.closepass();
										//转移至备份表
										updateTable();
											
									}
									else if(qryresult.equals("N"))
									{
										progressbar.stopBar();
										MessageBox.post("采购申请未成功，错误信息如下："+this.errmessage,"提示",MessageBox.WARNING);
										writeTxtMessage(this.errmessage, dname);
										bypass.setpass();
										nowform.setProperty("s4Passing_State", "N");
										nowform.refresh();
										bypass.closepass();
										//转移至备份表
										updateTable();
										//自动打开日志文件
										Runtime.getRuntime().exec("cmd /c "+System.getenv("TEMP")+"/"+dname);
									}else if("null".equals(qryresult)){
										progressbar.stopBar();
										MessageBox.post("采购申请已传递，等待处理","提示",MessageBox.WARNING);
										return;
									}
								}else{
									progressbar.stopBar();
									MessageBox.post("采购申请已传递，等待处理","提示",MessageBox.WARNING);
									return;
								}
							}
							else
							{
								progressbar.stopBar();
								MessageBox.post("请先传递后再检查!","提示",MessageBox.WARNING);
								return;
							}
						}else{
							progressbar.stopBar();
							MessageBox.post("申请编号为空,请检查!","提示",MessageBox.WARNING);
							return;
						}
					}else{
						progressbar.stopBar();
						MessageBox.post("数据集命名引用文件格式不对","提示",MessageBox.WARNING);
						return;
					}
					
				}
			}else{
				progressbar.stopBar();
				MessageBox.post("数据集没有正确命名引用文件","提示",MessageBox.WARNING);
				return;
			}
		}
		else
		{
			progressbar.stopBar();
			MessageBox.post("数据集不符合条件","提示",MessageBox.WARNING);
			return;
		}
	}
	
	//将原表中的数据删除，并将数据保存至新表
	private boolean updateTable()
	{
		boolean isOK = false;
		// 实例化数据库连接
		OracleConnect oraconn = new OracleConnect();
		try {
			Connection conn = oraconn.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "insert into CUX_PLM_PR_IMP_HIST select * from CUX_PLM_PR_IMP_INFACE where SEGMENT1='"+applyNumber+"'";
			int k = stmt.executeUpdate(sql);
			if(k>0)
				System.out.println("转入备份表成功");
			stmt.close();
			
			stmt = conn.createStatement();
			String delsql = "delete from CUX_PLM_PR_IMP_INFACE where SEGMENT1='"+applyNumber+"'";
			k = stmt.executeUpdate(delsql);
			if(k>0)
				System.out.println("删除原数据成功");
			
			oraconn.closeConn(conn);
			isOK = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isOK;
	}
	
	
	//保存错误信息
	private String errmessage = "";
	private String selectFromTable()
	{
		String process_flag = "";
		// 实例化数据库连接
		OracleConnect oraconn = new OracleConnect();
		try {
			Connection conn = oraconn.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select * from CUX_PLM_PR_IMP_INFACE where SEGMENT1='"+applyNumber+"'";
			ResultSet result = stmt.executeQuery(sql);
			if(result!=null && result.next())
			{
				process_flag = result.getString("PROCESS_FLAG");
				errmessage = result.getString("ERROR_MSG");
			}
			stmt.close();
			oraconn.closeConn(conn);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return process_flag;
	}
	
	private void readExcel(HSSFSheet sheet)
	{
		int rowcount = sheet.getPhysicalNumberOfRows();
		//wlinfolist = new ArrayList<S4WLInfo>();
		System.out.println("总行数"+rowcount);
		HSSFRow row = sheet.getRow(3);
		HSSFCell cell = row.getCell(19);
		if(cell!=null){
			System.out.println("cell==>:"+cell.getCellType());
			applyNumber = readCell(cell);
			System.out.println("申请编号:	"+applyNumber);
		}else{
			flag_read = true;
		}
		
		
//		//获得整个信息
//		for(int i=5;i<rowcount-2;i++)
//		{
//			HSSFRow eachrow = sheet.getRow(i);
//			if(eachrow!=null)
//				readRow(eachrow);
//		}
		
	}
	
	private void readRow(HSSFRow eachrow)
	{
		S4WLInfo oneinfo = new S4WLInfo();
		oneinfo.setHanghao(readCell(eachrow.getCell(0)));
		oneinfo.setHetonghao(readCell(eachrow.getCell(1)));
		oneinfo.setGcmingcheng(readCell(eachrow.getCell(3)));
		oneinfo.setWlbianma(readCell(eachrow.getCell(5)));
		oneinfo.setDanwei(readCell(eachrow.getCell(6)));
		oneinfo.setHsdanjia(readCell(eachrow.getCell(7)));
		oneinfo.setBhsdanjia(readCell(eachrow.getCell(8)));
		oneinfo.setShuliang(readCell(eachrow.getCell(10)));
		oneinfo.setHsheji(readCell(eachrow.getCell(12)));
		oneinfo.setShuliang(readCell(eachrow.getCell(15)));
		oneinfo.setNeedtime(readCell(eachrow.getCell(17)));
		oneinfo.setAddress(readCell(eachrow.getCell(19)));
		oneinfo.setRemark(readCell(eachrow.getCell(21)));
		
		//wlinfolist.add(oneinfo);
		
	}
	
	private String readCell(HSSFCell cell)
	{
		System.out.println("cell--type--->:"+cell.getCellType());
		String value = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_BLANK:
			value = "";
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			if(HSSFDateUtil.isCellDateFormatted(cell))
				value = HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).toString();
			else
			{
				double tempvalue = cell.getNumericCellValue();
				value = String.valueOf(tempvalue).split("\\.")[0];
			}
			break;
		case HSSFCell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		default:
			value = cell.getStringCellValue();
			break;
		}
		System.out.println("value------>:"+value);
		return value;
		 
	}
	
	//写入日志
	public void writeTxtMessage(String value,String name) {
		try {
			PrintWriter txt = new PrintWriter(new FileWriter(System.getenv("TEMP")+"/"+name,true),true);
			txt.print("                    采购申请单传递检查日志信息：                 "+"\n");
			txt.print(value+"\n");
			txt.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

package com.origin.rac.sac.eco;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import cn.com.origin.util.OracleConnect;
import cn.com.origin.util.ProgressBarThread;

import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentTcFile;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

/**
 * ORGANIZATION_CODE 组织
 * CHANGE_NOTICE ECO号
 * CHANGE_ORDER_TYPE 类型
 * INITIATION_DATE 创建日期
 * ECO_STATUS 状态
 * REQUESTOR_FULL_NAME 申请人
 * ECO_DEPARTMENT ECO部门
 * REASON_CODE 原因
 * APPROVAL_STATUS 审批状态
 * 
 * NEW_ITEM_REVISION_DESC 说明
 * REVISED_ITEM 编码
 * 
 * COMP_ACTION 活动
 * COMPONENT_ITEM 物料编码
 * DESCRIPTION 描述
 * ITEM_NUM 物料序号
 * COMPONENT_BIT_NUM_ONE 组件位号1
 * COMPONENT_BIT_NUM_TWO 组件位号2
 * COMPONENT_BIT_NUM_THREE 组件位号3
 * COMPONENT_REMARKS 备注
 * SUPPLY_TYPE 供应类型
 * 
 * OLD_OPERATION_SEQ_NUM 工序旧的
 * OPERATION_DISPLAY_SEQ_NUM 新的
 * OLD_COMPONENT_QUANTITY 数量旧的
 * DISPLAY_COMPONENT_QUANTITY 新的
 * 
 * BATCH_ID 批次
 * CREATE_BY 申请人
 * CREATION_DATE PLM创建时间
 * LAST_UPDATE_DATE ERP最后修改时间
 */
//ECO读取传递
public class S4ECOToErpCommand extends AbstractAIFCommand{

	//private AbstractAIFUIApplication app = null;
	private TCSession session = null;
	public TCComponentItemRevision target = null;

	private String relationForm = "IMAN_master_form_rev";

	public Connection conn = null;
	private Statement stmt = null;
	private ResultSet reset = null;
	private String path = null;
	private boolean flag = false;

	public S4ECOToErpCommand(AbstractAIFUIApplication app, TCComponentItemRevision target){
		//this.app = app;
		this.target = target;
		session = (TCSession) app.getSession();
	}

	public void executeModal() throws Exception {
		
		String user = session.getUser().toString();
		
		//--------------ECO是否传递成功了-----------------
		TCComponent[] tccomponent = target.getRelatedComponents(relationForm);
		for (int i = 0; i < tccomponent.length; i++) {
			if(tccomponent[i] instanceof TCComponentForm){
				TCComponentForm tccomponentForm = (TCComponentForm)tccomponent[i];
				String passing_state = tccomponentForm.getProperty("s4Passing_State");
				if(passing_state.equals("Y") || passing_state.equals("y")){
					MessageBox.post("该ECO已经成功传递过！","提示",MessageBox.WARNING);
					return;
				}
			}
		}
		//---------------ECO的流水和数据库的流水是否已经存在了--------------

		TCComponent[] specstand = target.getReferenceListProperty("S4ECO");
		if(specstand.length == 1){
			if(specstand[0] instanceof TCComponentDataset){
				TCComponentDataset temp = (TCComponentDataset)specstand[0];
				if(temp.isCheckedOut()){
					MessageBox.post("数据集已经签出，请签入！","提示",MessageBox.WARNING);
					return;
				}
				File sourceFile = getDataSetPathFile(temp);
				path = System.getenv("TEMP") + "\\" + "变更通知单-" + getSystemTime() + ".xls";
				File destDir = new File(path);
				new JieCopyFile().copyFile(sourceFile, destDir);
			}
			else{
				MessageBox.post("ECO文件夹中数据集错误！","提示",MessageBox.WARNING);
				return;
			}
		}
		else{
			MessageBox.post("ECO文件夹中数据集错误！","提示",MessageBox.WARNING);
			return;
		}

		String ECOcode = getDateExcel(path, 6, 2);

		OracleConnect oraconn = new OracleConnect();
		conn = oraconn.getConnection();
		String sql_ECOcode_String = "select * from CUX.CUX_PLM_ECO_IFACE where CHANGE_NOTICE='"
			+ ECOcode +"'" ;
		System.out.println("【查询语句 】" + sql_ECOcode_String);
		stmt = conn.createStatement();
		reset = stmt.executeQuery(sql_ECOcode_String);
		while(reset.next()){
			flag = true;
		}
		stmt.close();
		oraconn.closeConn(conn);
		if(flag){
			MessageBox.post("该ECO已经传递到中间表，等待ERP接收确认！","提示",MessageBox.WARNING);
			return;
		}

		ProgressBarThread progressBar = new ProgressBarThread("ECO发送中...", "发送中，请稍等...");
		progressBar.start();
		
		conn = oraconn.getConnection();
		ArrayList<ArrayList<String>> list = getExcel(path, 6, 25);
		for (int i = 0; i < list.size(); i++) {
			String sqr_cd = "";
			String sqr_bumen = "";
			String yy_cd = "";
			ArrayList<String> listString = list.get(i);
			String sqr_str = listString.get(5);
			System.out.println("sqr_str--->:"+sqr_str);
			String bumen_str = listString.get(6);
			String yy_str = listString.get(7);
			if(sqr_str ==null || "".equals(sqr_str) || " ".equals(sqr_str)){
				sqr_cd = "";
			}else{
				sqr_cd = sqr_str + ",";
			}
			System.out.println("sqr_cd=====>:"+sqr_cd);
			if(bumen_str ==null || "".equals(bumen_str) || " ".equals(bumen_str)){
				sqr_bumen = "";
			}
			System.out.println("sqr_bumen===>:"+sqr_bumen);
			if(yy_str ==null || "".equals(yy_str) || " ".equals(yy_str)){
				yy_cd = "";
			}
			System.out.println("yy_cd===>:"+yy_cd);
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//Date nowDate = sdf.parse(listString.get(3));
			String sql = "insert into CUX.CUX_PLM_ECO_IFACE(" +
			"IFACE_ID,ORGANIZATION_CODE,CHANGE_NOTICE,CHANGE_ORDER_TYPE," +//4
			"INITIATION_DATE,ECO_STATUS,REQUESTOR_FULL_NAME,ECO_DEPARTMENT," +//4
			"REASON_CODE,APPROVAL_STATUS,NEW_ITEM_REVISION_DESC,REVISED_ITEM," +//4
			"COMP_ACTION,COMPONENT_ITEM,DESCRIPTION,ITEM_NUM,COMPONENT_BIT_NUM_ONE," +//5
			"COMPONENT_BIT_NUM_TWO,COMPONENT_BIT_NUM_THREE,COMPONENT_REMARKS,SUPPLY_TYPE," +//4
			"OLD_OPERATION_SEQ_NUM,OPERATION_DISPLAY_SEQ_NUM,OLD_COMPONENT_QUANTITY,DISPLAY_COMPONENT_QUANTITY," +//4
			"BATCH_ID,CREATE_BY,CREATION_DATE,LAST_UPDATE_DATE) " +//4
			"values (CUX.CUX_PLM_ECO_IFACE_s.Nextval,'"+listString.get(0)+"','"+listString.get(1)+
			"','"+listString.get(2)+"',"+"to_date('"+listString.get(3)+"','YYYY-MM-DD HH24:MI:SS')"+",'"+ listString.get(4) + "','" + sqr_cd +"','"+
			sqr_bumen+"','"+yy_cd+"','"+listString.get(8)+"','"+listString.get(10)+"','"+
			listString.get(11)+"','"+listString.get(12)+"','"+listString.get(13)+"','"+listString.get(14)+"','"+
			listString.get(15)+"','"+listString.get(16)+"','"+listString.get(17)+"','"+listString.get(18)+"','"+
			listString.get(19)+"','"+listString.get(20)+"','"+listString.get(21)+"','"+
			listString.get(22)+"','"+listString.get(23)+"','"+listString.get(24)+"','-1','" +user+
					"',SYSDATE,SYSDATE)";

			System.out.println("sql ==> "+sql);

			stmt = conn.createStatement();
			int k = stmt.executeUpdate(sql);
			if (k > 0) {
				System.out.println("信息插入成功");
			} else {
				System.out.println("插入失败");
			}
			stmt.close();
		}
		oraconn.closeConn(conn);
		
		File file = new File(path);
		if(file.exists())
		{
			file.delete();
		}
		
		MessageBox.post("ECO已经传递到中间表，等待ERP接收确认!", "提示", MessageBox.INFORMATION);
		progressBar.stopBar();
	}

	private File getDataSetPathFile(TCComponentDataset tccomponentDataset) {
		File file = null;
		try {
			TCComponentTcFile tcFiles[] = tccomponentDataset.getTcFiles();
			file = tcFiles[0].getFmsFile();

		} catch (TCException e) {
			e.printStackTrace();
		}
		return file;
	}

	private String getSystemTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm");
		Date currentTime = new Date();
		String dateString = format.format(currentTime);
		return dateString;
	}

	public String getDateExcel(String path, int Row,int Cloume){
		Workbook wb = null;
		String value = null;
		try {
			wb = new HSSFWorkbook(new FileInputStream(path));
			Sheet sheet = wb.getSheetAt(0);
			Row row = sheet.getRow(Row);
			Cell cell = row.getCell(Cloume);
			value = cell.getStringCellValue();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	public ArrayList<ArrayList<String>> getExcel(String path, int StartRow,int AllCloume){

		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		Workbook wb = null;
		String value = null;
		try {
			wb = new HSSFWorkbook(new FileInputStream(path));
			HSSFSheet sheet = (HSSFSheet) wb.getSheetAt(0);
			int rowCount = sheet.getPhysicalNumberOfRows();
			//int rowCount = sheet.getLastRowNum();
			//System.out.println(rowCount+"");
			for (int i = StartRow; i < rowCount; i++) {
				ArrayList<String> listString = new ArrayList<String>();
				Row row = sheet.getRow(i);
				for (int j = 1; j <= AllCloume; j++) {
					Cell cell = row.getCell(j);
					int k  = cell.getCellType();
					System.out.println("cell_type===============>:"+k);
					if(k==0){
						double tempvalue = cell.getNumericCellValue();
						value = String.valueOf(tempvalue).split("\\.")[0];
					} else if(k==1){
						
						value = cell.getStringCellValue();
					}
					System.out.println("value===>:"+value);
					//System.out.println(value);
					listString.add(value);
				}
				list.add(listString);
			}
			//System.out.println("---------------------------------");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}

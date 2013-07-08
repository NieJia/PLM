package com.origin.rac.sac.eco;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import cn.com.origin.util.OracleConnect;
import cn.com.origin.util.ProgressBarThread;
import cn.com.origin.util.S4Bypass;

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

public class S4ECOCheckingCommand extends AbstractAIFCommand{

	public Connection conn = null;
	private Statement stmt = null;
	private ResultSet reset = null;

	//private AbstractAIFUIApplication app = null;
	private TCSession session = null;
	public TCComponentItemRevision target = null;

	private String relationForm = "IMAN_master_form_rev";

	private String path = null;
	private String log = null;
	private boolean flag = false;
	private boolean flagY = false;
	private boolean flagN = false;
	private boolean flagNull = false;
	private S4Bypass bypass = null;

	public S4ECOCheckingCommand(AbstractAIFUIApplication app, TCComponentItemRevision target){
		//this.app = app;
		this.target = target;
		session = (TCSession) app.getSession();
		bypass = new S4Bypass(session);
	}
	public void executeModal() throws Exception {

		ProgressBarThread progressBar = new ProgressBarThread("ECO检查中...", "检查中，请稍等...");
		progressBar.start();

		TCComponent[] specstand = target.getReferenceListProperty("S4ECO");
		if(specstand.length == 1){
			if(specstand[0] instanceof TCComponentDataset){
				TCComponentDataset temp = (TCComponentDataset)specstand[0];
				File sourceFile = getDataSetPathFile(temp);
				path = System.getenv("TEMP") + "\\" + "变更通知单-" + getSystemTime() + ".xls";
				File destDir = new File(path);
				new JieCopyFile().copyFile(sourceFile, destDir);
			}
			else{
				MessageBox.post("ECO文件夹中数据集错误！","提示",MessageBox.WARNING);
				progressBar.stopBar();
				return;
			}
		}
		else{
			MessageBox.post("ECO文件夹中数据集错误！","提示",MessageBox.WARNING);
			progressBar.stopBar();
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
			String processFlag = reset.getString("PROCESS_FLAG");
			System.out.println("processFlag------>:"+processFlag);
			if(processFlag != null){
				if(processFlag.equals("Y")){
					flagY = true;
				}
				else if(processFlag.equals("N")){
					flagN = true;
					log = reset.getString("ERROR_MSG");
					if(log == null)
					{
						log = "";
					}
				}
				else if(processFlag.equals("")){
					flagNull = true;
				}
			}
			else{
				flagNull = true;
			}
		}
		stmt.close();
		oraconn.closeConn(conn);
		if(!flag){
			MessageBox.post("该ECO没有传递到中间表，请发送！","提示",MessageBox.WARNING);
			progressBar.stopBar();
			return;
		}
		if(flagY){
			setFormValue("Y");
			MessageBox.post("该ECO已经成功传递ERP!!", "提示", MessageBox.INFORMATION);
		}
		if(flagN){
			setFormValue("N");
			MessageBox.post("该ECO传递失败! 错误信息为：" + log, "提示", MessageBox.INFORMATION);
		}
		if(flagNull){
			MessageBox.post("该ECO已经传递至中间表等待ERP接收确认!", "提示", MessageBox.INFORMATION);
		}

		//将原表中的数据复制到备份表CUX.CUX_PLM_ROUTING_HIST里面
		conn = oraconn.getConnection();
		String sql_copy = "insert into CUX.CUX_PLM_ECO_HIST select * from CUX.CUX_PLM_ECO_IFACE where (PROCESS_FLAG='"
			+ "Y" + "' or PROCESS_FLAG='" + "N" + "') and CHANGE_NOTICE='"+ ECOcode +"'";
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

		//将原表中PROCESS_FLAG是Y或者是N的数据删除
		conn = oraconn.getConnection();
		String sql_delete = "delete from CUX.CUX_PLM_ECO_IFACE where (PROCESS_FLAG='"
			+ "Y" + "' or PROCESS_FLAG='" + "N" + "') and CHANGE_NOTICE='"+ ECOcode +"'";
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

		File file = new File(path);
		if(file.exists())
		{
			file.delete();
		}
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

	public void setFormValue(String value){
		bypass.setpass();
		try {
			TCComponent[] tccomponent = target.getRelatedComponents(relationForm);
			for (int i = 0; i < tccomponent.length; i++) {
				if(tccomponent[i] instanceof TCComponentForm){
					TCComponentForm tccomponentForm = (TCComponentForm)tccomponent[i];
					tccomponentForm.getTCProperty("s4Passing_State").setStringValue(value);
				}
			}
		} catch (TCException e) {
			e.printStackTrace();
		}
		bypass.closepass();
	}
}

package com.origin.rac.sac.eco;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Workbook;

import cn.com.origin.util.PLMOracleConnect;
import cn.com.origin.util.ProgressBarThread;
import cn.com.origin.util.ReadProperties;
import cn.com.origin.util.S4Bypass;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentBOMViewRevision;
import com.teamcenter.rac.kernel.TCComponentBOMWindow;
import com.teamcenter.rac.kernel.TCComponentBOMWindowType;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentRevisionRuleType;
import com.teamcenter.rac.kernel.TCComponentTcFile;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCPreferenceService;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCTextService;
import com.teamcenter.rac.util.MessageBox;

public class S4ECOOperation extends AbstractAIFOperation{

	private AbstractAIFApplication app = null;
	private TCSession session = null;
	private S4ECOCommand command = null;
	public Connection		conn	= null;
	public ReadProperties	read	= null;
	private Statement stmt = null;
	private ResultSet reset = null;
	
	private String zuzhi = null;
	private String liushui = null;
	private static final String preferenceName_ECO_ls = "SAC_ECO_LS";
	private static final String status = "打开";
	private static final String approvalStatus = "批准";

	//private static final String entriesSJJ = "数据集名称";
	//private static final String keyDN = "DatasetName";
	private static final String DatasetName = "ECO模板";
	//private static final String myItemId = "bl_item_item_id";
	private static final String sheet = "ECO表单";

	private HashMap<TCComponentBOMLine, String> mapGenggai = null;
	private HashMap<TCComponentBOMLine, String> mapTianjia = null;

	private String[] arr_ECO_ls = null;
	private TCComponentDataset tmpDataset = null;

	private String liushuiNumber = null;
	private String createDate = null;
	private String bomId = null;
	private TCComponentItem yz_item = null;
	private String item_type = "";
	private String yz_type = "S4CP";
	private S4Bypass bypass = null;
	private int intLiushui = 0;
	//private ArrayList<String> list = null;

	public S4ECOOperation(S4ECOCommand command, 
			AbstractAIFApplication app, TCSession session){
		this.app = app;
		this.session = session;
		this.command = command;
		zuzhi = command.name_zz;
		bypass = new S4Bypass(session);
		/*try {
			item_revision = (TCComponentItemRevision) app.getTargetComponent();
			yz_item = item_revision.getItem();
			
		} catch (TCException e) {
			e.printStackTrace();
		}*/
		getECO_ls();
	}

	public void getECO_ls(){
		try {
			String eco_code = "";
			// 实例化数据库连接
			PLMOracleConnect oraconn = new PLMOracleConnect();
			conn = oraconn.getConnection();
			System.out.println("conn===>:"+conn);
			String sql = "select * from ECOWATERCODE where ORGANIZATION_CODE = '" + zuzhi +"'";
			stmt = conn.createStatement();
			reset = stmt.executeQuery(sql);
			if(reset!=null && reset.next()){
				eco_code = reset.getString("CHANGE_NOTICE");
				intLiushui = Integer.parseInt(eco_code);
			}
			stmt.close();
			oraconn.closeConn(conn);
			intLiushui += 1;
			System.out.println("intLiushui---:"+intLiushui);
			liushui = String.valueOf(intLiushui);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*arr_ECO_ls = getTCPreferenceArray(session, preferenceName_ECO_ls);
		if(arr_ECO_ls.length == 0){
			MessageBox.post("未配置 SAC_ECO_LS 首选项","首选项错误", MessageBox.WARNING);
			return;
		}
		for (int i = 0; i < arr_ECO_ls.length; i++) {
			String[] tmp_ECO_ls  = arr_ECO_ls[i].split("=");
			if(tmp_ECO_ls.length == 2){
				if(tmp_ECO_ls[0].equals(zuzhi)){
					int intLiushui = Integer.parseInt(tmp_ECO_ls[1]);
					intLiushui += 1;
					liushui = intLiushui+"";
					arr_ECO_ls[i] = zuzhi + "=" + liushui;
					System.out.println(arr_ECO_ls[i]);
				}
			}
			else{
				MessageBox.post(" SAC_ECO_LS 首选项配置错误","首选项错误", MessageBox.WARNING);
				return;
			}
		}
		return;*/
	}

	public void executeOperation() throws Exception {

		ProgressBarThread progressBar = new ProgressBarThread("ECO数据提取中.....", "数据提取中，请稍等.....");
		progressBar.start();

//		setTCPreferenceArray(session, preferenceName_ECO_ls, arr_ECO_ls);

//		itemId = command.target.getProperty("item_id");
//		userName = session.getUser().getProperty("user_name");
//		groupName = session.getGroup().toString();
		liushuiNumber = zuzhi+"_"+liushui;
		System.out.println("liushuiNumber--->:"+liushuiNumber);
		createDate = getSystemTimeForERP();

//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm");
//		Date nowDate = sdf.parse(createDate);
		//System.out.println("userName ====> " + userName);
		//System.out.println("groupName ====> " + groupName);
		TCComponentBOMLine beforeBomline = getBomline(command.beforeBOMViewRevision);
		yz_item = beforeBomline.getItem();
		item_type = yz_item.getType().toString();
		TCComponentBOMLine afterBomline = getBomline(command.afterBOMViewRevision);
		bomId = beforeBomline.getProperty("bl_item_item_id");
		System.out.println("beforeBomline---id->:"+bomId);
		//得到比较信息
		mapGenggai = new HashMap<TCComponentBOMLine, String>();
		mapTianjia = new HashMap<TCComponentBOMLine, String>();
		ArrayList info = getBomCompareInfo(beforeBomline, afterBomline);

		if (info.size() == 0){
			MessageBox.post(" 两个bomveiw中的数据相同！无导出数据。", "提示完成", 4);
			return;
		}

		//得到模板
		//String[] values = {DatasetName};
		//JieSearch jieSearch = new JieSearch(session);
		//InterfaceAIFComponent[] iac = jieSearch.jieSearch(keyDN, values, entriesSJJ);
		InterfaceAIFComponent[] iac = queryDateset(DatasetName);
		if(iac == null){
			MessageBox.post("模板没有设置","首选项或模板错误", MessageBox.WARNING);
			return;
		}
		TCComponentDataset dateset = (TCComponentDataset)iac[0];
		File sourceFile = getDataSetPathFile(dateset);
		String path = System.getenv("TEMP") + "\\" + "变更通知单-" + getSystemTime() + ".xls";
		File destDir = new File(path);
		new JieCopyFile().copyFile(sourceFile, destDir);
		//写EXCLE
		writeExcel(path, info);
		//创建挂接数据集
		JieCreateDataset jcd = new JieCreateDataset(session, path);
		try {
			tmpDataset = jcd.jieCreateDataset("ECO变更通知单", "excel", "MSExcel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		command.target.add("S4ECO", tmpDataset);

		File file = new File(path);
		if(file.exists())
		{
			file.delete();
		}
		//将写到ECO表里面的ECO号更新到数据库表里面
		updateECOCode();
		progressBar.stopBar();
		MessageBox.post(" 已经导出到数据集中！请单击确定。", "提示完成", 4);

	}

	private void updateECOCode(){
		try {
			// 实例化数据库连接
			PLMOracleConnect oraconn = new PLMOracleConnect();
			conn = oraconn.getConnection();
			String upsql = "update ECOWATERCODE set CHANGE_NOTICE = '" + liushui + "' where ORGANIZATION_CODE = '" + zuzhi +"'";
			stmt = conn.createStatement();
			int k = stmt.executeUpdate(upsql);
			if (k > 0) {
				System.out.println("信息更新成功");
			} else {
				System.out.println("更新失败");
			}
			oraconn.closeConn(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String[] getTCPreferenceArray(TCSession session, String preferenceName)
	{
		String[] preString = null;
		preString = session.getPreferenceService().getStringArray(TCPreferenceService.TC_preference_site, preferenceName);
		return preString;
	}
	private void setTCPreferenceArray(TCSession session, String preferenceName, String[] preString){
		try {
			bypass.setpass();
			session.getPreferenceService().setStringArray(TCPreferenceService.TC_preference_site, preferenceName, preString);
			session.refresh();
			bypass.closepass();
		} catch (TCException e) {
			e.printStackTrace();
		}
		return;
	}
	private String getSystemTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm");
		Date currentTime = new Date();
		String dateString = format.format(currentTime);
		return dateString;
	}

	private String getSystemTimeForERP() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = new Date();
		String dateString = format.format(currentTime);
		return dateString;
	}

	private TCComponentBOMLine getBomline(TCComponentBOMViewRevision target) {
		TCComponentBOMLine bomLine = null;
		try {
			TCComponentBOMWindowType windowtype = (TCComponentBOMWindowType) session.getTypeComponent("BOMWindow");
			TCComponentRevisionRuleType ruletype = (TCComponentRevisionRuleType) session.getTypeComponent("RevisionRule");
			com.teamcenter.rac.kernel.TCComponentRevisionRule revisionrule = ruletype.getDefaultRule();
			TCComponentBOMViewRevision bomviewRevisino = target;
			TCComponentBOMWindow window = windowtype.create(revisionrule);
			AIFComponentContext com[] = bomviewRevisino.whereReferenced();
			TCComponentItemRevision ir = null;
			for (int j = 0; j < com.length; j++) {
				System.out.println((new StringBuilder("====>")).append(
						com[j].getComponent()).toString());
				if (com[j].getComponent() instanceof TCComponentItemRevision) {
					//TCComponentItemRevision revision = (TCComponentItemRevision) com[j].getComponent();
					ir = (TCComponentItemRevision) com[j].getComponent();
				}
			}
			bomLine = window.setWindowTopLine(ir.getItem(), ir, null, bomviewRevisino);
		} catch (TCException e) {
			e.printStackTrace();
		}
		return bomLine;
	}

	private ArrayList getBomCompareInfo(TCComponentBOMLine lineA,
			TCComponentBOMLine lineB) throws TCException {
		ArrayList list = new ArrayList();
		TCComponentBOMLine bomlinesA[] = getAllComponents(lineA);
		TCComponentBOMLine bomlinesB[] = getAllComponents(lineB);
		System.out.println("!--------开始比较----------！");
		System.out.println((new StringBuilder(String.valueOf(bomlinesA.length)))
				.append("--------- ").append(bomlinesB.length).toString());
		for (int i = 0; i < bomlinesA.length; i++) {
			TCComponentBOMLine line = bomlinesA[i];
			if (!compareBOMlineExist(line, bomlinesB, "bl_item_item_id").booleanValue()) {
				System.out.println((new StringBuilder("==========禁用了 "))
						.append(getValue(line, "bl_item_item_id")).toString());
				HashMap map = new HashMap();
				map.put("禁用", line);
				list.add(map);
			} 
			else{
				TCComponentBOMLine componentBOMLine = findBomline(line, bomlinesB, "bl_item_item_id");
				System.out.println("componentBOMLine==修改===============>:"+componentBOMLine);
				if (compareBOMlineisChanged(line, componentBOMLine, "bl_item_object_desc").booleanValue()
						|| compareBOMlineisChanged(line, componentBOMLine, "S4DESCRIPTION").booleanValue()
						|| compareBOMlineisChanged(line, componentBOMLine, "bl_sequence_no").booleanValue()
						|| compareBOMlineisChanged(line, componentBOMLine, "S4ATTRIBUTE7").booleanValue()
						|| compareBOMlineisChanged(line, componentBOMLine, "S4ATTRIBUTE8").booleanValue()
						|| compareBOMlineisChanged(line, componentBOMLine, "S4ATTRIBUTE9").booleanValue()
						|| compareBOMlineisChanged(line, componentBOMLine, "S4component_rem").booleanValue()
						|| compareBOMlineisChanged(line, componentBOMLine, "S4MEANING").booleanValue()
						|| compareBOMlineisChanged(line, componentBOMLine, "S4masteroper").booleanValue()
						|| compareBOMlineisChanged(line, componentBOMLine, "bl_quantity").booleanValue()){
					System.out.println((new StringBuilder("==========修改了 "))
							.append(getValue(componentBOMLine, "bl_item_item_id")).toString());
					HashMap map = new HashMap();
					map.put("更改", componentBOMLine);
					String before_seq_num = getValue(line, "S4masteroper");
					System.out.println("before_seq_num===>:"+before_seq_num);
					if(before_seq_num == null || before_seq_num.equals("")){
						before_seq_num = " ";
					}
					String before_quantity = getValue(line, "bl_quantity");
					System.out.println("before_quantity------>:"+before_quantity);
					if(before_quantity == null || before_quantity.equals("")){
						before_quantity = " ";
					}
					String after_seq_num = getValue(componentBOMLine, "S4masteroper");
					System.out.println("after_seq_num=====>:"+after_seq_num);
					if(after_seq_num == null || after_seq_num.equals("")){
						after_seq_num = " ";
					}
					String after_quantity = getValue(componentBOMLine, "bl_quantity");
					System.out.println("after_quantity================>:"+after_quantity);
					if(after_quantity == null || after_quantity.equals("")){
						after_quantity = " ";
					}
					String value =  before_seq_num + "," + before_quantity + ";" + after_seq_num
					+ "," + after_quantity;
					System.out.println("[" +value+"]");
					mapGenggai.put(componentBOMLine, value);
					list.add(map);
				}
			}
		}

		for (int j = 0; j < bomlinesB.length; j++) {
			TCComponentBOMLine bomline = bomlinesB[j];
			if (!compareBOMlineExist(bomline, bomlinesA, "bl_item_item_id").booleanValue()) {
				System.out.println((new StringBuilder("==============增加了 "))
						.append(getValue(bomline, "bl_item_item_id")).toString());
				HashMap map = new HashMap();
				System.out.println("bomline-----------------添加"+bomline);
				map.put("添加", bomline);
				String add_seq_num = getValue(bomline, "S4masteroper");
				System.out.println("add_seq_num===>:"+add_seq_num);
				if(add_seq_num == null || add_seq_num.equals("")){
					add_seq_num = " ";
				}
				String add_quantity = getValue(bomline, "bl_quantity");
				System.out.println("add_quantity------>:"+add_quantity);
				if(add_quantity == null || add_quantity.equals("")){
					add_quantity = " ";
				}
				String value =  add_seq_num + ";" + add_quantity;
				mapTianjia.put(bomline,value);
				list.add(map);
			}
		}
		System.out.println("!=====================结束======================!");
		return list;
	}

	private Boolean compareBOMlineExist(TCComponentBOMLine bomline,
			TCComponentBOMLine lines[], String attr) {
		String value = getValue(bomline, attr);
		for (int i = 0; i < lines.length; i++) {
			String str = getValue(lines[i], attr);
			if (str != null && str.equals(value))
				return Boolean.valueOf(true);
		}
		return Boolean.valueOf(false);
	}

	private TCComponentBOMLine findBomline(TCComponentBOMLine bomline, 
			TCComponentBOMLine lines[], String index) {
		for (int i = 0; i < lines.length; i++) {
			TCComponentBOMLine line = lines[i];
			if (getValue(line, index).equals(getValue(bomline, index)))
				return line;
		}
		return null;
	}

	private TCComponentBOMLine[] getAllComponents(TCComponentBOMLine line) {
		TCComponentBOMLine array[] = (TCComponentBOMLine[]) null;
		try {
			ArrayList list = new ArrayList();
			AIFComponentContext children[] = line.getChildren();
			for (int i = 0; i < children.length; i++)
				if (children[i].getComponent() instanceof TCComponentBOMLine)
					list.add((TCComponentBOMLine) children[i].getComponent());

			array = new TCComponentBOMLine[list.size()];
			for (int i = 0; i < list.size(); i++)
				array[i] = (TCComponentBOMLine) list.get(i);

		} catch (TCException e) {
			e.printStackTrace();
		}
		return array;
	}

	private void writeExcel(String path, ArrayList arrayList) {
		try {
			Workbook workbook = JieWriteExcel.createExcel(path);

			/*			for (int i = 5; i < 500; i++) {
				for (int j = 0; j < 20; j++){
					JieWriteExcel.clearExcel(workbook, sheet, i, j);
				}
			}*/
			/**
			 * private String userName = null;
			 * private String groupName = null;
			 * private String liushuiNumber = null;
			 * private String createDate = null;
			 */
			for (int i = 0; i < arrayList.size(); i++) {
				HashMap valueMap = (HashMap) arrayList.get(i);
				if(valueMap.get("添加") != null) {
					TCComponentBOMLine line = (TCComponentBOMLine) valueMap.get("添加");
					String valueStrings[] = getMapValue(valueMap, "添加");
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 1, command.name_zz);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 2, liushuiNumber);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 3, command.name_lx);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 4, createDate);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 5, status);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 6, command.name_sqr);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 7, command.name_bm);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 8, command.name_yy);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 9, approvalStatus);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 10, "");
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 11, command.description);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 12, bomId);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 13, "添加");
					for (int j = 0; j < valueStrings.length; j++) {
						JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 14+j, valueStrings[j]);
					}
					String[] arr = mapTianjia.get(line).split(";");
					System.out.println("arr---------------->:"+arr.length);
					if(arr.length>0){
						if(arr.length==1){
							JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 22, "");
							if(item_type.equals(yz_type)){
								if(arr[0]==null || "".equals(arr[0]) || " ".equals(arr[0])){
									JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 23, "10");
								}else{
									JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 23, arr[0]);
								}
							}else{
								JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 23, arr[0]);
							}
							JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 24, "");
							JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 25, "");
						}else{
							JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 22, "");
							if(item_type.equals(yz_type)){
								if(arr[0]==null || "".equals(arr[0]) || " ".equals(arr[0])){
									JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 23, "10");
								}else{
									JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 23, arr[0]);
								}
							}else{
								JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 23, arr[0]);
							}
							JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 24, "");
							JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 25, arr[1]);
						}
					}else{
						JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 22, "");
						if(item_type.equals(yz_type)){
							JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 23, "10");
						}else{
							JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 23, "");
						}
						JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 24, "");
						JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 25, "");
					}
					
				}
				else if(valueMap.get("更改") != null) {
					TCComponentBOMLine line = (TCComponentBOMLine) valueMap.get("更改");
					String valueStrings[] = getMapValue(valueMap, "更改");
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 1, command.name_zz);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 2, liushuiNumber);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 3, command.name_lx);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 4, createDate);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 5, status);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 6, command.name_sqr);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 7, command.name_bm);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 8, command.name_yy);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 9, approvalStatus);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 10, "");
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 11, command.description);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 12, bomId);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 13, "更改");
					for (int j = 0; j < valueStrings.length; j++) {
						JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 14+j, valueStrings[j]);
					}
					String[] arr = mapGenggai.get(line).split(";");
					System.out.println("arr---------------->:"+arr.length);
					if(arr.length > 1){
						String[] beforearr = arr[0].split(",");
						System.out.println("beforearr====00000===>:"+beforearr[0]);
						System.out.println("beforearr====11111===>:"+beforearr[1]);
						if(beforearr.length > 1){
							if(beforearr[0].equals(" ")){
								JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 22, "");
							}
							else{
								JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 22, beforearr[0]);
							}
							if(beforearr[1].equals(" ")){
								JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 24, "");
							}
							else{
								JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 24, beforearr[1]);
							}
						}
						String[] afterarr = arr[1].split(",");
						System.out.println("afterarr====00000===>:"+afterarr[0]);
						System.out.println("afterarr====11111===>:"+afterarr[1]);
						if(afterarr.length > 1){
							if(afterarr[0].equals(" ")){
								JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 23, "");
							}
							else{
								JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 23, afterarr[0]);
							}
							if(afterarr[1].equals(" ")){
								JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 25, "");
							}
							else{
								JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 25, afterarr[1]);
							}
						}
					}
				}
				else if(valueMap.get("禁用") != null) {
					String valueStrings[] = getMapValue(valueMap, "禁用");
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 1, command.name_zz);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 2, liushuiNumber);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 3, command.name_lx);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 4, createDate);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 5, status);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 6, command.name_sqr);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 7, command.name_bm);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 8, command.name_yy);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 9, approvalStatus);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 10, "");
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 11, command.description);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 12, bomId);
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 13, "禁用");
					for (int j = 0; j < valueStrings.length; j++) {
						JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 14+j, valueStrings[j]);
					}
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 22, "");
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 23, "");
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 24, "");
					JieWriteExcel.wirteExcel(workbook, sheet, i + 6, 25, "");
				}
			}
			JieWriteExcel.closeExcel(workbook, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String[] getMapValue(HashMap map, String type) {
		TCComponentBOMLine line = (TCComponentBOMLine) map.get(type);
		String str[] = { getValue(line, "bl_item_item_id"), getValue(line, "S4DESCRIPTION"),
				getValue(line, "bl_sequence_no"), getValue(line, "S4ATTRIBUTE7"),
				getValue(line, "S4ATTRIBUTE8"), getValue(line, "S4ATTRIBUTE9"),
				getValue(line, "S4component_rem"), getValue(line, "S4MEANING")};
		return str;
	}

	private Boolean compareBOMlineisChanged(TCComponentBOMLine lineA,
			TCComponentBOMLine lineB, String attr) {
		if (!getValue(lineA, attr).equals(getValue(lineB, attr)))
			return Boolean.valueOf(true);
		else
			return Boolean.valueOf(false);
	}

	private String getValue(TCComponentBOMLine line, String valueString) {
		String str = null;
		try {
			str = line.getProperty(valueString);
		} catch (TCException e) {
			e.printStackTrace();
		}
		return str;
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

	//调用系统零组件 ID查询
	public InterfaceAIFComponent[] queryDateset(String name){
		InterfaceAIFComponent[] items = null;
		try {
			TCTextService tcService =session.getTextService();
			String askKey[]={tcService.getTextValue("Name"),tcService.getTextValue("OwningUser")};
			String askValue[]={name,"infodba"};
			items =  session.search("数据集...", askKey, askValue);
		} catch (TCException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
}

package com.origin.rac.sac.cgsqdcd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentItemType;
import com.teamcenter.rac.kernel.TCComponentTcFile;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class S4CGSQDCDCommand extends AbstractAIFCommand {

	private AbstractAIFUIApplication app = null;
	private TCSession session;
	private TCComponentItem targetitem = null;
	
	public Connection conn = null;
	public Statement stmt = null;
	public ResultSet reset = null;
	
	//传递相关变量
	private String applyNumber;
	private ArrayList<S4WLInfo> wlinfolist = null;
	private ProgressBarThread progressbar = null ;
	private String itemId = "";
	private boolean flag_fp= false;//用来判断选择请购单的组织在ERP中有没有分配
	private boolean flag_read= false;//用来判断读取excel是不是有错
	private Vector<String> vec_error_ID = new Vector<String>();//用来提示选择的BOM及单层子结构ID在搜索到的组织里面不存在的ID
	private String str_ywst = "SAC_NZMZ_OU";
	private String str_kczz = "SAC_NZMZ_INV";
	private String str_cgsqlx = "采购申请";
	private String str_mddlx = "库存";
	private String str_ly = "供应商";
	
	
	
	
	
	public S4CGSQDCDCommand(AbstractAIFUIApplication app,TCComponentItem targetitem)
	{
		this.app = app;
		this.targetitem = targetitem;
	}

	@Override
	public void executeModal() throws Exception {
		this.session = (TCSession) this.app.getSession();
		progressbar = new ProgressBarThread("采购申请单传递" ,"采购申请单传递中,请稍等...");
		progressbar.start();
		itemId = targetitem.getProperty("item_id").toString();
		TCComponentItemRevision itemrev = targetitem.getLatestItemRevision();
		TCComponentForm nowform = (TCComponentForm) itemrev.getRelatedComponent("IMAN_master_form_rev");
		//判断是否已发布
		TCComponent[] releaselist = itemrev.getRelatedComponents("release_status_list");
		System.out.println("发布状态个数:"+releaselist.length);
		if(releaselist.length <= 0)
		{
			progressbar.stopBar();
			MessageBox.post("只有已发布的采购申请单才允许发送到ERP","提示",MessageBox.WARNING);
			return;
		}
		
		String s4Passing_State = nowform.getProperty("s4Passing_State");
		if(s4Passing_State.equals("Y"))
		{
			progressbar.stopBar();
			MessageBox.post("该采购申请单已经存在ERP中，请重新选择!","提示",MessageBox.WARNING);
			return;
		}
		else 
		{
			
			
			//做检查，如果存在则提示
			
			//读取excel文件
			TCComponent[] tempdataset = itemrev.getRelatedComponents("IMAN_specification");
			if(tempdataset!=null && tempdataset.length==1)
			{
				String filepath = "";
				TCComponentDataset xlsdataset = (TCComponentDataset) tempdataset[0];
				if(xlsdataset.isCheckedOut())
				{
					progressbar.stopBar();
					MessageBox.post("请将数据集签入后再操作","提示",MessageBox.WARNING);
					return;
				}
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
//						else if(filepath.endsWith(".xlsx")) 
//						{
//							workbook = new XSSFWorkbook(is);//Excel 2007
//						}
						if(workbook !=null)
						{
							int sheetNum = workbook.getNumberOfSheets();//得到Sheet的个数
							HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
//							for(int i = 0; i < sheetNum; i++) //遍历sheet
//							{
//								sheet = (Sheet) workbook.getSheetAt(i);
//								//获取数据
//								
//							}
							readExcel(sheet);
							if(flag_read){
								progressbar.stopBar();
								MessageBox.post("读取模板有错!","提示",MessageBox.WARNING);
								return;
							}
							System.out.println("数据长度:"+wlinfolist.size());
							//检查系统中是否含有指定的合同号对象
							for(int i=0;i<wlinfolist.size();i++)
							{
								S4WLInfo oneinfo = wlinfolist.get(i);
								String hetonghao = oneinfo.getHetonghao();
								TCComponentItemType itemtype = (TCComponentItemType) this.session.getTypeComponent("Item");
								TCComponentItem[] items = itemtype.findItems(hetonghao);
								if(items==null || items.length<=0)
								{
									progressbar.stopBar();
									MessageBox.post("系统中不存在合同号为"+hetonghao+"的对象","提示",MessageBox.WARNING);
									return;
								}
							}
							
							//检查是否已发送到ERP
							for(int i=0;i<wlinfolist.size();i++)
							{
								S4WLInfo oneinfo = wlinfolist.get(i);
								String wlbianma = oneinfo.getWlbianma();
								TCComponentItemType itemtype = (TCComponentItemType) this.session.getTypeComponent("Item");
								TCComponentItem[] items = itemtype.findItems(wlbianma);
								if(items!=null && items.length>0)
								{
									TCComponentItem tempitem = items[0];
									TCComponentItemRevision temprev = tempitem.getLatestItemRevision();
									TCComponentForm tempnowform = (TCComponentForm) temprev.getRelatedComponent("IMAN_master_form_rev");
									
									String tempPassing_State = tempnowform.getProperty("s4Passing_State");
									if(!tempPassing_State.equals("Y"))
									{
										progressbar.stopBar();
										MessageBox.post(wlbianma+"物料还没有传递到ERP，请先传递到ERP!","提示",MessageBox.WARNING);
										return;
									}
									flag_fp = isInOrganization(wlbianma,"MZ0");
									
								}
								else
								{
									progressbar.stopBar();
									MessageBox.post("存在不满足条件的物料,编码为"+wlbianma,"提示",MessageBox.WARNING);
									return;
								}
							}
							if(flag_fp){
								progressbar.stopBar();
								MessageBox.post("您选择采购申请单下"+vec_error_ID+"的物料所在的组织在ERP中没有分配,请检查!", "提示", MessageBox.INFORMATION);
								return;
							}
							//检查s4Passing_State.equals("")
							if(s4Passing_State.equals("") || s4Passing_State.equals("N"))
							{
								// 实例化数据库连接
								OracleConnect oraconn = new OracleConnect();
								Connection conn = oraconn.getConnection();
								Statement stmt = conn.createStatement();
								String sql = "select * from CUX_PLM_PR_IMP_INFACE where SEGMENT1='"+applyNumber+"'";
								ResultSet result = stmt.executeQuery(sql);
								if(result!=null && result.next())
								{
									progressbar.stopBar();
									MessageBox.post("采购申请已经做过传递，请重新选择","提示",MessageBox.WARNING);
									return;
								}
								stmt.close();
								oraconn.closeConn(conn);
							}
							progressbar.stopBar();
							//调用执行类
							S4CGSQDCDOperation operation=new S4CGSQDCDOperation(app,wlinfolist,str_ywst,str_kczz,applyNumber,str_cgsqlx,str_mddlx,str_ly);
							operation.executeOperation();
//							//调用对话框
//							new S4CGSQDCDDialog(app,wlinfolist,applyNumber);
						}
						
					}
				}
			}
			else
			{
				progressbar.stopBar();
				MessageBox.post("数据集不符合条件","提示",MessageBox.WARNING);
				return;
			}
			
		}
		
		
	}
	
	//通过选择请购单去检查所在的组织在ERP中是不是存在
	public boolean isInOrganization(String id,String zzdm){
		boolean flag_org = false;
		try {
			// 实例化数据库连接
			OracleConnect oraconn = new OracleConnect();
			conn = oraconn.getConnection();
			String sql = "select * from CUX_PLM_ORG_ITEM_V where Org_Code = '"+ zzdm + "'" + " and Item_Num = '"+ id +"'";
			stmt = conn.createStatement();
			reset = stmt.executeQuery(sql);
			if(reset!=null && reset.next())
			{
				
			}else{
				flag_org = true;
				if(!vec_error_ID.contains(id)){
					vec_error_ID.add(id);
				}
			}
			stmt.close();
			
			oraconn.closeConn(conn);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag_org;
	}
	
	
	private void readExcel(HSSFSheet sheet)
	{
		int rowcount = sheet.getPhysicalNumberOfRows();
		wlinfolist = new ArrayList<S4WLInfo>();
		System.out.println("总行数"+rowcount);
		HSSFRow row = sheet.getRow(3);
		HSSFCell cell = row.getCell(19);
		System.out.println("cell==>:"+cell.getCellType());
		applyNumber = readCell(cell);
		System.out.println("申请编号:	"+applyNumber);
		
		//获得整个信息
		for(int i=5;i<rowcount-2;i++)
		{
			HSSFRow eachrow = sheet.getRow(i);
			if(eachrow!=null)
				readRow(eachrow);
		}
		
	}
	
	private void readRow(HSSFRow eachrow)
	{
		S4WLInfo oneinfo = new S4WLInfo();
		if(eachrow.getCell(0)!=null){
			oneinfo.setHanghao(readCell(eachrow.getCell(0)));
		}else{
			flag_read = true;
		}
		if(eachrow.getCell(1)!=null){
			oneinfo.setHetonghao(readCell(eachrow.getCell(1)));
		}else{
			flag_read = true;
		}
		if(eachrow.getCell(3)!=null){
			oneinfo.setGcmingcheng(readCell(eachrow.getCell(3)));
		}else{
			flag_read = true;
		}
		if(eachrow.getCell(5)!=null){
			oneinfo.setWlbianma(readCell(eachrow.getCell(5)));
		}else{
			flag_read = true;
		}
		if(eachrow.getCell(6)!=null){
			oneinfo.setWlmingcheng(readCell(eachrow.getCell(6)));
		}else{
			flag_read = true;
		}
		if(eachrow.getCell(7)!=null){
			oneinfo.setDanwei(readCell(eachrow.getCell(7)));
		}else{
			flag_read = true;
		}
		if(eachrow.getCell(8)!=null){
			oneinfo.setHsdanjia(readCell(eachrow.getCell(8)));
		}else{
			flag_read = true;
		}
		if(eachrow.getCell(10)!=null){
			oneinfo.setBhsdanjia(readCell(eachrow.getCell(10)));
		}else{
			flag_read = true;
		}
		if(eachrow.getCell(12)!=null){
			oneinfo.setShuliang(readCell(eachrow.getCell(12)));
		}else{
			flag_read = true;
		}
		if(eachrow.getCell(15)!=null){
			oneinfo.setHsheji(readCell(eachrow.getCell(15)));
		}else{
			flag_read = true;
		}
		//特别处理日期型字符串
//		String needtime = readCell(eachrow.getCell(17));
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		try {
//			Date tempdate = sdf.parse(needtime);
//			oneinfo.setNeedtime(sdf.format(tempdate));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		if(eachrow.getCell(17)!=null){
			oneinfo.setNeedtime(readCell(eachrow.getCell(17)));
		}else{
			flag_read = true;
		}
		if(eachrow.getCell(19)!=null){
			oneinfo.setAddress(readCell(eachrow.getCell(19)));
		}else{
			flag_read = true;
		}
		if(eachrow.getCell(21)!=null){
			oneinfo.setRemark(readCell(eachrow.getCell(21)));
		}else{
			flag_read = true;
		}
		
		wlinfolist.add(oneinfo);
		
	}
	
	
	private String readCell1(HSSFCell cell){
		String value = "";
		switch (cell.getCellType()) {   
        case HSSFCell.CELL_TYPE_NUMERIC: // 数字   
            System.out.print(cell.getNumericCellValue()   
                    + "   ");   
            break;   
        case HSSFCell.CELL_TYPE_STRING: // 字符串   
            System.out.print(cell.getStringCellValue()   
                    + "   ");   
            break;   
        case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean   
            System.out.println(cell.getBooleanCellValue()   
                    + "   ");   
            break;   
        case HSSFCell.CELL_TYPE_FORMULA: // 公式   
            System.out.print(cell.getCellFormula() + "   ");   
            break;   
        case HSSFCell.CELL_TYPE_BLANK: // 空值   
        	value = ""; 
            break;   
        case HSSFCell.CELL_TYPE_ERROR: // 故障   
        	value = ""; 
            break;   
        default:   
            System.out.print("未知类型   ");   
            break;   
        }
		return value;
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
}

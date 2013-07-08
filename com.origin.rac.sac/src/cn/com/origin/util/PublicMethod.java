package cn.com.origin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentContextList;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentDatasetType;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentQuery;
import com.teamcenter.rac.kernel.TCComponentQueryType;
import com.teamcenter.rac.kernel.TCComponentTcFile;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCTextService;
import com.teamcenter.rac.kernel.TCTypeService;
import com.teamcenter.rac.util.MessageBox;

public class PublicMethod {
	
	
    public static String getYearMonthDateHoursMinutesSeconds()
    {
        String str_result = "";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        str_result = sdf.format(date);
        return str_result;
    }
	
	public static Vector<String> readFileTxt(String fileName) {
		File file = new File(fileName);
		Vector<String> vec = new Vector<String>();
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读一行，直到读取为null
			while ((tempString = reader.readLine()) != null) {
				line++;
				vec.add(tempString);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return vec;
	}

	public static void writeFileTxt(String toFileName, Vector<String> vec) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(toFileName,
					true), true);
			if (vec.size() > 0) {
				for (int i = 0; i < vec.size(); i++) {
					writer.print(vec.get(i));
					writer.println();
					writer.flush();
				}
			}
		} catch (Exception e) {
		}
	}

	public static void writeFileTxt(String toFileName, String str) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(toFileName,
					true), true);
			writer.print(str + "\n");
			writer.println();
			writer.flush();
		} catch (Exception e) {
		}
	}

	public static TCComponentDataset findDataSet(TCSession session,
			String dataSetName) {
		TCComponentDataset dataSet = null;
		try {
			TCComponentDatasetType dsType = (TCComponentDatasetType) session
					.getTypeComponent("Dataset");
			dataSet = dsType.find(dataSetName);
			if(dataSet == null)
			{
				MessageBox.post("在系统中没有找到" + dataSetName + " 数据集", "INFO", 1);
			}
			return dataSet;
		} catch (TCException e) {
			MessageBox.post("在系统中没有找到" + dataSetName + " 数据集", "INFO", 1);
			e.printStackTrace();
			return null;
		}
	}

	public static File getSearchDataset(TCSession session, String dsName) throws TCException {
		File fmsFile = null;
		TCComponentDataset dataSet = findDataSet(session, dsName);
		if (dataSet != null) {
			TCComponentTcFile[] files = dataSet.getTcFiles();
			if (files != null) {
				if (files.length == 1) {
					fmsFile = files[0].getFmsFile();
				} else if (files.length == 0) {
					MessageBox.post("数据集模板没有引用文件，请检查！", "提示", 1);
				} else {
					MessageBox.post("数据集模板的引用文件过多，请检查！", "提示", 1);
				}
			} else {
				MessageBox.post("数据集模板没有引用文件，请检查！", "提示", 1);
			}
		} /*else {
			MessageBox.post("未找到数据集模板：" + dsName, "提示", 1);
		}*/
		return fmsFile;
	}
	
	// 创建数据集
	public static TCComponentDataset createDataSet(TCSession session, String fromPath, String dsType, String dsName, String ref_type) {
		TCComponentDataset dataset = null;
		try {
			TCTypeService type = session.getTypeService();
			TCComponentDatasetType datasettype = (TCComponentDatasetType) type.getTypeComponent("Dataset");
			// 创建数据集
			dataset = datasettype.create(dsName, "", dsType);
			String[] p = new String[1];
			String[] n = new String[1];
			p[0] = fromPath;
			n[0] = ref_type;
			// 设置引用文件
			dataset.setFiles(p, n);
		} catch (TCException e) {
			MessageBox.post("数据集创建错！", "提示", 2);
			e.printStackTrace();
		}
		return dataset;
	}
	
	// 判断对应数据集是否存在
	public static boolean ifExists(String name, TCComponentItemRevision rev) {
		try {
			TCComponent[] coms = rev.getRelatedComponents("S4Affix_C");
			for (int i = 0; i < coms.length; i++) {
				if ("MSWord".equals(coms[i].getProperty("object_type")) && name.equals(coms[i].getProperty("object_name"))) {
					MessageBox.post(name + "已存在!", "提示", MessageBox.INFORMATION);
					return true; // 已经存在
				}
			}
		} catch (TCException e) {
			e.printStackTrace();
		}
		return false; // 不存在
	}
	
	  public static TCComponent[] query(TCSession session ,String query_name, String[] arg1, String[] arg2)
	  {
	    TCComponentContextList imancomponentcontextlist = null;
	    TCComponent[] component = (TCComponent[])null;
	    try
	    {
	      TCComponentQueryType imancomponentquerytype = (TCComponentQueryType)session.getTypeComponent("ImanQuery");
	      TCComponentQuery imancomponentquery = (TCComponentQuery)imancomponentquerytype.find(query_name);
	      TCTextService imantextservice = session.getTextService();
	      String[] queryAttribute = new String[arg1.length];
	      for (int i = 0; i < arg1.length; ++i)
	        queryAttribute[i] = imantextservice.getTextValue(arg1[i]);

	      String[] queryValues = new String[arg2.length];
	      for (int i = 0; i < arg2.length; ++i)
	        queryValues[i] = arg2[i];

	      imancomponentcontextlist = imancomponentquery.getExecuteResultsList(queryAttribute, queryValues);
	      component = imancomponentcontextlist.toTCComponentArray();
	    }
	    catch (Exception ex)
	    {
	      ex.printStackTrace();
	    }
	    return component;
	  }
	
	

	public static void copyFile(String oldfile, String newfile) {
		try {
			FileWriter fw = new FileWriter(oldfile);
			FileReader fr = new FileReader(newfile);
			int b;
			while ((b = fr.read()) != -1) {
				fw.write((char) b);
			}
			fr.close();
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

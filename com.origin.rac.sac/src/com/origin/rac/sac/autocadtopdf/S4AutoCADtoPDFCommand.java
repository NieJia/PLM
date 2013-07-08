package com.origin.rac.sac.autocadtopdf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import cn.com.origin.util.ProgressBarThread;
import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentTcFile;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class S4AutoCADtoPDFCommand extends AbstractAIFCommand{

	private AbstractAIFUIApplication app = null;
	private static final String relation = "IMAN_specification";
	private static final String relationchild = "IMAN_reference";
	private String path = "";

	private boolean flage = true;

	public S4AutoCADtoPDFCommand(AbstractAIFUIApplication app){
		this.app = app;
	}

	public void executeModal() throws Exception {

		TCSession session = (TCSession) app.getSession();
		String UserName = session.getUser().toString();

		//jieArrayList = new ArrayList<String>();

		flage = true;

		path = new JieFindFileDialog().init();
		if(path == null || path == ""){
			MessageBox.post("没有选择下载路径，请重新选择！", "提示", MessageBox.WARNING);
			return;
		}

		ProgressBarThread s4ProgressBarThread = new ProgressBarThread("文档下载进度", "正在下载文档，请稍后。");
		s4ProgressBarThread.start();

		InterfaceAIFComponent[] targets = app.getTargetComponents();
		for (int i = 0; i < targets.length; i++) {
			if(targets[i] instanceof TCComponentFolder){
				TCComponentFolder tmpFolder = (TCComponentFolder)targets[i];
				TCComponent[] tmpTCComponent =  tmpFolder.getRelatedComponents("contents");
				for (int j = 0; j < tmpTCComponent.length; j++) {
					if(tmpTCComponent[j] instanceof TCComponentFolder){
						TCComponentFolder tmpFolderChild = (TCComponentFolder)tmpTCComponent[j];
						TCComponent[] tmpTCComponentChild =  tmpFolderChild.getRelatedComponents("contents");
						for (int n = 0; n < tmpTCComponentChild.length; n++) {
							if(tmpTCComponentChild[n] instanceof TCComponentItem){
								TCComponentItem tmpTCComponentItem = (TCComponentItem)tmpTCComponentChild[n];
								if(tmpTCComponentItem.getProperty("owning_user").equals(UserName)){
									TCComponentItemRevision tmpTCComponentItemRevision = tmpTCComponentItem.getLatestItemRevision();
									TCComponent[] tmpDataset = tmpTCComponentItemRevision.getRelatedComponents(relation);
									for (int k = 0; k < tmpDataset.length; k++) {
										if(tmpDataset[k] instanceof TCComponentDataset){
											TCComponentDataset tmpTCComponentDataset = (TCComponentDataset)tmpDataset[k];
											//操作
											String tmpFolderNameChild = tmpFolderChild.getProperty("object_name");
											String tmpFolderName = tmpFolder.getProperty("object_name");
											String sourceFileName = getSourceFileName(tmpTCComponentDataset);

											AIFComponentContext[] context = tmpFolder.whereReferenced();
											if(sourceFileName != null){
												flage = false;
												if(context.length > 0 && context[0].getComponent() instanceof TCComponentItem){
													TCComponentItem myTarget = (TCComponentItem)(context[0].getComponent());
													String tmpItemMessage = myTarget.getProperty("object_string");
													String folerPath = path + "/"  + tmpItemMessage + "-" + tmpFolderName + "/" + tmpFolderNameChild;
													String targetPath = path + "/"  + tmpItemMessage + "-" + tmpFolderName + "/" + tmpFolderNameChild + "/" + sourceFileName;
													downloadDwg(tmpTCComponentDataset, folerPath, targetPath);
												}
												else{
													String folerPath = path + "/"  + tmpFolderName + "/" + tmpFolderNameChild;
													String targetPath = path + "/"  + tmpFolderName + "/" + tmpFolderNameChild + "/" + sourceFileName;
													downloadDwg(tmpTCComponentDataset, folerPath, targetPath);
												}
											}
										}
									}
								}
							}
						}
					}
					if(tmpTCComponent[j] instanceof TCComponentItem){
						TCComponentItem tmpTCComponentItem = (TCComponentItem)tmpTCComponent[j];
						if(tmpTCComponentItem.getProperty("owning_user").equals(UserName)){
							TCComponentItemRevision tmpTCComponentItemRevision = tmpTCComponentItem.getLatestItemRevision();
							TCComponent[] tmpDataset = tmpTCComponentItemRevision.getRelatedComponents(relation);
							for (int k = 0; k < tmpDataset.length; k++) {
								if(tmpDataset[k] instanceof TCComponentDataset){
									TCComponentDataset tmpTCComponentDataset = (TCComponentDataset)tmpDataset[k];
									//操作
									String tmpFolderName = tmpFolder.getProperty("object_name");
									String sourceFileName = getSourceFileName(tmpTCComponentDataset);

									AIFComponentContext[] context = tmpFolder.whereReferenced();
									if(sourceFileName != null){
										flage = false;
										if(context.length > 0 && context[0].getComponent() instanceof TCComponentItem){
											TCComponentItem myTarget = (TCComponentItem)(context[0].getComponent());
											String tmpItemMessage = myTarget.getProperty("object_string");
											String folerPath = path + "/"  + tmpItemMessage + "-" + tmpFolderName;
											String targetPath = path + "/"  + tmpItemMessage + "-" + tmpFolderName + "/" + sourceFileName;
											downloadDwg(tmpTCComponentDataset, folerPath, targetPath);
										}
										else if(context.length > 0 && context[0].getComponent() instanceof TCComponentFolder){
											TCComponentFolder jieFolder = (TCComponentFolder)context[0].getComponent();
											AIFComponentContext[] jieContext = jieFolder.whereReferenced();
											if(jieContext.length > 0 && jieContext[0].getComponent() instanceof TCComponentItem){
												TCComponentItem jieTarget = (TCComponentItem)(jieContext[0].getComponent());
												String jieItemMessage = jieTarget.getProperty("object_string");
												String folerPath = path + "/"  + jieItemMessage + "-"+ tmpFolderName;
												String targetPath = folerPath + "/" + sourceFileName;
												downloadDwg(tmpTCComponentDataset, folerPath, targetPath);
											}
											else{
												String folerPath = path + "/"  + tmpFolderName;
												String targetPath = path + "/"  + tmpFolderName + "/" + sourceFileName;
												downloadDwg(tmpTCComponentDataset, folerPath, targetPath);
											}
										}
										else{
											String folerPath = path + "/"  + tmpFolderName;
											String targetPath = path + "/"  + tmpFolderName + "/" + sourceFileName;
											downloadDwg(tmpTCComponentDataset, folerPath, targetPath);
										}
									}
								}
							}
						}
					}
				}
			}
			else if(targets[i] instanceof TCComponentItem){
				TCComponentItem tmpTCComponentItem = (TCComponentItem)targets[i];
				if(tmpTCComponentItem.getProperty("owning_user").equals(UserName)){
					//flagefirst = false;
					TCComponent[] targetFolder = tmpTCComponentItem.getRelatedComponents(relationchild);
					for (int j = 0; j < targetFolder.length; j++) {
						//flage = false;
						//***********************
						TCComponent[] tmpTCComponent =  targetFolder[j].getRelatedComponents("contents");
						for (int j1 = 0; j1 < tmpTCComponent.length; j1++) {
							if(tmpTCComponent[j1] instanceof TCComponentFolder){
								TCComponentFolder tmpFolderChild = (TCComponentFolder)tmpTCComponent[j1];
								TCComponent[] tmpTCComponentChild =  tmpFolderChild.getRelatedComponents("contents");
								for (int n = 0; n < tmpTCComponentChild.length; n++) {
									if(tmpTCComponentChild[n] instanceof TCComponentItem){
										TCComponentItem tmpTCComponentItem1 = (TCComponentItem)tmpTCComponentChild[n];
										if(tmpTCComponentItem1.getProperty("owning_user").equals(UserName)){
											TCComponentItemRevision tmpTCComponentItemRevision = tmpTCComponentItem1.getLatestItemRevision();
											TCComponent[] tmpDataset = tmpTCComponentItemRevision.getRelatedComponents(relation);
											for (int k = 0; k < tmpDataset.length; k++) {
												if(tmpDataset[k] instanceof TCComponentDataset){
													TCComponentDataset tmpTCComponentDataset = (TCComponentDataset)tmpDataset[k];
													//操作
													String tmpItemMessage = tmpTCComponentItem.getProperty("object_string");
													String tmpFolderNameChild = tmpFolderChild.getProperty("object_name");
													String tmpFolderName = targetFolder[j].getProperty("object_name");
													String sourceFileName = getSourceFileName(tmpTCComponentDataset);
													if(sourceFileName != null){
														flage = false;
														String folerPath = path + "/"  + tmpItemMessage + "-" + tmpFolderName + "/" + tmpFolderNameChild;
														String targetPath = path + "/"  + tmpItemMessage + "-" + tmpFolderName + "/" + tmpFolderNameChild + "/" + sourceFileName;
														downloadDwg(tmpTCComponentDataset, folerPath, targetPath);
													}
												}
											}
										}
									}
								}
							}
							if(tmpTCComponent[j1] instanceof TCComponentItem){
								TCComponentItem tmpTCComponentItem1 = (TCComponentItem)tmpTCComponent[j1];
								if(tmpTCComponentItem1.getProperty("owning_user").equals(UserName)){
									TCComponentItemRevision tmpTCComponentItemRevision = tmpTCComponentItem1.getLatestItemRevision();
									TCComponent[] tmpDataset = tmpTCComponentItemRevision.getRelatedComponents(relation);
									for (int k = 0; k < tmpDataset.length; k++) {
										if(tmpDataset[k] instanceof TCComponentDataset){
											TCComponentDataset tmpTCComponentDataset = (TCComponentDataset)tmpDataset[k];
											//操作
											String tmpItemMessage = tmpTCComponentItem.getProperty("object_string");
											String tmpFolderName = targetFolder[j].getProperty("object_name");
											String sourceFileName = getSourceFileName(tmpTCComponentDataset);
											if(sourceFileName != null){
												flage = false;
												String folerPath = path + "/"  + tmpItemMessage + "-" + tmpFolderName;
												String targetPath = path + "/"  + tmpItemMessage + "-" + tmpFolderName + "/" + sourceFileName;
												downloadDwg(tmpTCComponentDataset, folerPath, targetPath);
											}
										}
									}
								}
							}
						}

					}
//					*****************************


					TCComponentItemRevision tmpTCComponentItemRevision = tmpTCComponentItem.getLatestItemRevision();
					TCComponent[] tmpDataset = tmpTCComponentItemRevision.getRelatedComponents(relation);
					for (int k = 0; k < tmpDataset.length; k++) {
						if(tmpDataset[k] instanceof TCComponentDataset){
							TCComponentDataset tmpTCComponentDataset = (TCComponentDataset)tmpDataset[k];
							//操作
							String sourceFileName = getSourceFileName(tmpTCComponentDataset);

							AIFComponentContext[] context = tmpTCComponentItem.whereReferenced();
							if(sourceFileName != null){
								flage = false;
								if(context.length > 0 && context[0].getComponent() instanceof TCComponentFolder){
									TCComponentFolder jieFolder = (TCComponentFolder)context[0].getComponent();
									AIFComponentContext[] jieContext = jieFolder.whereReferenced();
									if(jieContext.length > 0 && jieContext[0].getComponent() instanceof TCComponentFolder){
										TCComponentFolder jieTempFolder = (TCComponentFolder)jieContext[0].getComponent();
										AIFComponentContext[] jieTempContext = jieTempFolder.whereReferenced();
										if(jieTempContext.length > 0 && jieTempContext[0].getComponent() instanceof TCComponentItem){
											TCComponentItem jieTarget = (TCComponentItem)(jieTempContext[0].getComponent());
											String jieItemMessage = jieTarget.getProperty("object_string");
											String folerPath = path + "/"  + jieItemMessage;
											String targetPath = folerPath + "/" + sourceFileName;
											downloadDwg(tmpTCComponentDataset, folerPath, targetPath);
										}
										else{
											String folerPath = path + "/" ;
											String targetPath = path + "/"  + sourceFileName;
											downloadDwg(tmpTCComponentDataset, folerPath, targetPath);
										}
									}
									else{
										String folerPath = path + "/" ;
										String targetPath = path + "/"  + sourceFileName;
										downloadDwg(tmpTCComponentDataset, folerPath, targetPath);
									}
								}
								else{
									String folerPath = path + "/" ;
									String targetPath = path + "/"  + sourceFileName;
									downloadDwg(tmpTCComponentDataset, folerPath, targetPath);
								}
							}
						}
					}
				}
				/*else{
					s4ProgressBarThread.stopBar();
					MessageBox.post("没有有所有权的文档需要下载！", "提示", MessageBox.WARNING);
					return;
				}*/
			}
		}
		if(flage){
			s4ProgressBarThread.stopBar();
			MessageBox.post("没有文档需要下载！", "提示", MessageBox.WARNING);
			return;
		}

/*		//写log文件，提示。
		if(jieArrayList.size() >0){
			jieStringBuilder = new StringBuilder();

			String logPath = getTempPath("下载文件","txt");
			File logFile = new File(logPath);
			BufferedWriter logBufferedWriter = new BufferedWriter(new FileWriter(logFile));
			for (int j = 0; j < jieArrayList.size(); j++) {
				String tmpLog = jieArrayList.get(j);
				logBufferedWriter.write(tmpLog);
				logBufferedWriter.newLine();
				jieStringBuilder.append(tmpLog);
			}
			logBufferedWriter.close();
		}*/
		MessageBox.post("文档批量下载成功，存放在\""+ path +"\"下！", "提示", MessageBox.WARNING);
		s4ProgressBarThread.stopBar();
	}
//	将文档下载到本地
	public void downloadDwg(TCComponentDataset tmpTCComponentDataset, String folerPath, String targetPath){
		File folderfile = null;
		File tagetfile = null;
		try {
			TCComponentTcFile[] jieTcFile = tmpTCComponentDataset.getTcFiles();
			File sourcefile = jieTcFile[0].getFmsFile();
			if(folerPath != null){
				folderfile = new File(folerPath);
				if(new File(folerPath).exists() == false){
					folderfile.mkdirs();
				}
			}
			tagetfile = new File(targetPath);
			if(tagetfile != null){
				System.out.println("开始下载...");
				copyFile(sourcefile, tagetfile);
			}
		} catch (TCException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//	下载文件
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}
//	得到命名引用的文件的名字
	public String getSourceFileName(TCComponentDataset tmpTCComponentDataset){
		String sourceFileName = null;
		try {
			TCComponent[] tmpRef = tmpTCComponentDataset.getReferenceListProperty("ref_list");
			if(tmpRef.length > 0){
				TCComponentTcFile sourceFile = (TCComponentTcFile)tmpRef[0];
				sourceFileName = sourceFile.getProperty("original_file_name");
				//int endIndex = sourceFileName.indexOf('.');
				//sourceFileName = sourceFileName.substring(0, endIndex);
			}
		} catch (TCException e) {
			e.printStackTrace();
		}
		return sourceFileName;
	}
//	提示日志的路径
	public String getTempPath(String name, String type) {
		String path = System.getenv("TEMP") + File.separator + name + getNowTime()
		+ "." + type;
		return path;
	}
//	获得当前时间
	public String getNowTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(new Date().getTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		return dateFormat.format(calendar.getTime());
	}
}


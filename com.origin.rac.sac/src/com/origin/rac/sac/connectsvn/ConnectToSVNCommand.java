package com.origin.rac.sac.connectsvn;

import java.io.IOException;
import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class ConnectToSVNCommand extends AbstractAIFCommand {

	private TCSession session = null;
	private String[] str_types = null;
	private boolean flag = false;
	private String relation = "IMAN_master_form_rev";
	private String attri = "s4SVN_URL";
	
	
	
	public ConnectToSVNCommand(AbstractAIFUIApplication app) {
		session = (TCSession) app.getSession();
		TCComponent target = (TCComponent) app.getTargetComponent();
		if (target == null || !(target instanceof TCComponentItem)) {
			MessageBox.post("请选择指定对象后重试!", "提示", MessageBox.INFORMATION);
			return;
		}else{
			try {
				str_types = session.getPreferenceService().getStringArray(4, "SAC_SVN_ItemType");
				if(str_types == null || str_types.length == 0){
					MessageBox.post("未配置首选项：SAC_SVN_ItemType的值", "提示", MessageBox.INFORMATION);
					return;
				}else{
					TCComponentItem item = (TCComponentItem) target;
					//判断选择的类型是不是符合要求
					for (int i = 0; i < str_types.length; i++) {
						if(!str_types[i].equals(item.getType().toString())){
							flag=true;
							break;
						}
					}
					if(flag){
						MessageBox.post("请选择指定对象后重试!", "提示", MessageBox.INFORMATION);
						return;
					}else{
						TCComponentItemRevision latest_rev = item.getLatestItemRevision();
						TCComponent com = latest_rev.getRelatedComponent(relation);
						String host = com.getProperty(attri);
						System.out.println("host---->:"+host);
						if("".equals(host)){
							MessageBox.post("选择的对象没有对于的源码请重新选择!", "提示", MessageBox.INFORMATION);
							return;
						} else {
							//windows用默认浏览器打开
							String cmd = "rundll32 url.dll,FileProtocolHandler "+host;
							try {
								Runtime.getRuntime().exec(cmd);
							} catch (IOException e) {
								System.out.println("打开浏览器错误");
								e.printStackTrace();
							}
						}
					}
				}
				
			} catch (TCException e) {
				e.printStackTrace();
			}
		}
	}
	
}

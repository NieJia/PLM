package com.origin.rac.sac.gylx;

import java.util.HashMap;

import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCSession;

public class S4GYLXPZCommand extends AbstractAIFCommand{

	private AbstractAIFUIApplication app = null;
	private TCSession session = null;
	public InterfaceAIFComponent target = null;
	
	public String zhiKuCun = null;
	public String huoWei = null;
	
	public String[] gongxuHao = null;
	public String[] gongxuDaima = null;
	public String[] bumenDaima = null;
	public String[] zuzhiDaima = null;
	//public String[] ziyuanHao = null;
	//public String[] ziyuangDaima = null;
	
	public HashMap<String, String> mapDianxing = null;
	public HashMap<String, String> map_gylx_zz_gxdm = null;
	public HashMap<String, String> map_gylx_gxdm_bm = null;
	public HashMap<String, String> map_gylx_gxdm_zy = null;
	public HashMap<String, String> map_gylx_zz_zkc = null;
	
	public S4GYLXPZCommand(AbstractAIFUIApplication app, InterfaceAIFComponent target){
		this.app = app;
		this.target = target;
		session = (TCSession) app.getSession();
	}
	
	public void executeModal() throws Exception {
		S4GYLXPZDialog dialog = new S4GYLXPZDialog(this, app);
		setRunnable(dialog);
	}
}

package com.origin.rac.sac.eco;

import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.kernel.TCComponentBOMViewRevision;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCSession;

public class S4ECOCommand extends AbstractAIFCommand{

	private AbstractAIFUIApplication app = null;
	private TCSession session = null;
	
	public TCComponentBOMViewRevision beforeBOMViewRevision = null;
	public TCComponentBOMViewRevision afterBOMViewRevision = null;
	
	public String name_zz = null;
	public String name_lx = null;
	public String name_yy = null;
	public String name_sqr = null;
	public String name_bm = null;
	public TCComponentItemRevision target = null;
	private String zzdm_str = "";
	
	public String description = null;
	
	public S4ECOCommand(AbstractAIFUIApplication app,
			TCComponentBOMViewRevision beforeBOMViewRevision, 
			TCComponentBOMViewRevision afterBOMViewRevision,
			String description,
			TCComponentItemRevision target,
			String zzdm){
		
		this.app = app;
		this.beforeBOMViewRevision = beforeBOMViewRevision;
		this.afterBOMViewRevision = afterBOMViewRevision;
		this.description = description;
		this.target = target;
		this.zzdm_str = zzdm;
		session = (TCSession) app.getSession();
	}
	
	public void executeModal() throws Exception {
		S4ECODialog dialog = new S4ECODialog(this, app, session,zzdm_str);
		setRunnable(dialog);
	}
}

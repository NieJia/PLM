package com.origin.rac.sac.eco;

import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentDatasetType;
import com.teamcenter.rac.kernel.TCSession;

public class JieCreateDataset {
	private TCSession session = null;
	private String path = null;
	public JieCreateDataset(TCSession session, String path){
		this.session = session;
		this.path = path;
	}
	public TCComponentDataset jieCreateDataset(String DatasetName, String quote, String type) throws Exception{
		String MyPath[] = {path};
		String Myquote[] = {quote};//引用"excel"
		String Mytype[] = {type};//类型"MSExcelX"
		String string[] = {"Plain"};
		TCComponentDatasetType tccomponentDatasetType = (
				TCComponentDatasetType) session.getTypeComponent(type);//"MSExcelX"
		TCComponentDataset tccomponentDataset = 
			tccomponentDatasetType.create(DatasetName, type, type);
		tccomponentDataset.setFiles(MyPath, Mytype, string, Myquote);
		return tccomponentDataset;
		//session.getUser().getNewStuffFolder().add("contents", tccomponentDataset);
	}
	public void jieAddDataset(TCComponent component, String relationship,
			TCComponentDataset tccomponentDataset) throws Exception{
		component.add(relationship, tccomponentDataset);
	}
	public void jieOpenDataset(TCComponentDataset tccomponentDataset) throws Exception{
		tccomponentDataset.open();
	}
}

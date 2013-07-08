package test;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponentBOMViewRevisionType;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;

public class CreateBomview {
	
	private TCComponentBOMViewRevisionType type = null;
	private TCSession session;
	
	public CreateBomview(){
		try {
			AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
			
			session = (TCSession) application.getSession();
//			TCComponentBOMViewRevisionType type1 =  (TCComponentBOMViewRevisionType)session.getTypeComponent(type);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}

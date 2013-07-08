package cn.com.origin.util;

import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCUserService;

public class S4Bypass {

	private TCSession session = null;
	private TCUserService userServ = null;
	public S4Bypass(TCSession session)
	{
		this.session = session;
		this.userServ= this.session.getUserService(); 
	}
	
	public void setpass()
	{
		Object[] objs= new Object[1]; 
		String temp= new String("1"); 
		objs[0]=temp; 
		try {
			String returnStr= (String) userServ.call("CASIC_set_bypass", objs);
			System.out.println(returnStr);
		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  void closepass()
	{
		Object[] objs= new Object[1]; 
		String temp= new String("0"); 
		objs[0]=temp; 
		try {
			String returnStr= (String) userServ.call("CASIC_close_bypass", objs);
			System.out.println(returnStr);
		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

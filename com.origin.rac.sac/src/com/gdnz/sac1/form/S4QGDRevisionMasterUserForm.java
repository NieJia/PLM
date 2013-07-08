package com.gdnz.sac1.form;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTabbedPane;

import com.teamcenter.rac.form.AbstractTCForm;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCPreferenceService;
import com.teamcenter.rac.kernel.TCProperty;
import com.teamcenter.rac.kernel.TCSession;

public class S4QGDRevisionMasterUserForm extends AbstractTCForm {
	
	private TCComponentForm form = null;
	private S4QGDRevisionMasterUI s4QGDRevisionMasterUI = null;
	
	private TCProperty serentity = null;
	private TCProperty invenORG = null;
	private TCProperty proFile = null;
	private TCProperty Destination = null;
	private TCProperty source = null;
	private TCProperty purapptype = null;
	
	private String preferenceName = "SAC_QGD";
	private String[] optionKeys = null;



	private static final long serialVersionUID = 1L;

	
	public S4QGDRevisionMasterUserForm(TCComponentForm arg0) throws Exception {
		super(arg0);
		// TODO Auto-generated constructor stub
		System.out.println("***********begin0***********");
		form = arg0;
		initUI();
		System.out.println("***********begin1***********");
		loadForm();
	}

	/**
	 * 
	 */
	@Override
	public void loadForm() throws TCException {
		// TODO Auto-generated method stub
		
		serentity=form.getTCProperty("s4serentity");
		invenORG=form.getTCProperty("s4InvenORG");
		proFile=form.getTCProperty("s4Profile1");
		Destination=form.getTCProperty("s4Destination");
		source=form.getTCProperty("s4source");
		purapptype=form.getTCProperty("s4Purapptype");
		

        TCSession session = form.getSession();
		optionKeys = getTCPreferenceArray(session, preferenceName);
		s4QGDRevisionMasterUI.textserentity.addItem("");//
		for(int i=0;i<optionKeys.length;i++)
		{
			s4QGDRevisionMasterUI.textserentity.addItem(optionKeys[i].split("=")[0].trim());
		}
		for(int i=0;i<optionKeys.length;i++)
		{
			
			if(s4QGDRevisionMasterUI.textserentity.getSelectedItem().equals(optionKeys[i].split("=")[0].trim()))
			{
			    s4QGDRevisionMasterUI.textinvenORG.removeAllItems();
				String[] kczz = optionKeys[i].split("=")[1].split(",");
				for (int j=0; j<kczz.length; j++) {
					System.out.println(kczz[j]);
				s4QGDRevisionMasterUI.textinvenORG.addItem( kczz[j].trim());
				}
				s4QGDRevisionMasterUI.textinvenORG.setSelectedIndex(0);
				}
			}
		s4QGDRevisionMasterUI.textserentity.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        	
		for(int i=0;i<optionKeys.length;i++)
		{
			
			if(s4QGDRevisionMasterUI.textserentity.getSelectedItem().equals("")||s4QGDRevisionMasterUI.textserentity.getSelectedItem()==null){
			 s4QGDRevisionMasterUI.textinvenORG.removeAllItems();
			// s4QGDRevisionMasterUI.textinvenORG.addItem("");
			}
			else if(s4QGDRevisionMasterUI.textserentity.getSelectedItem().equals(optionKeys[i].split("=")[0].trim()))
			{
			    s4QGDRevisionMasterUI.textinvenORG.removeAllItems();
				System.out.println("**********1******");
				String[] kczz = optionKeys[i].split("=")[1].split(",");
				for (int j=0; j<kczz.length; j++) {
					System.out.println(kczz[j]);
				s4QGDRevisionMasterUI.textinvenORG.addItem( kczz[j].trim());
				}
				s4QGDRevisionMasterUI.textinvenORG.setSelectedIndex(0);
				}
			
			else{}
				}
			
		}
		}
		);
		
	/*	s4QGDRevisionMasterUI.textinvenORG.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	        	for(int i=0;i<optionKeys.length;i++)
	    		{
	    			if(s4QGDRevisionMasterUI.textserentity.getSelectedItem().equals(optionKeys[i].split("=")[0].trim()))
	    			{
	    				String[] kczz = optionKeys[i].split("=")[1].split(",");
	    				for (int j=0; j<kczz.length; j++) {
	    				if(s4QGDRevisionMasterUI.textinvenORG.getSelectedItem().equals(kczz[j].trim())){
	    				s4QGDRevisionMasterUI.textproFile.removeAllItems();
	    				String[] shdz = optionKeys[i].split("=")[2].split(",");
	    				for (int k=0; k<shdz.length; k++) {
	    					System.out.println(shdz[k]);
	    				s4QGDRevisionMasterUI.textproFile.addItem( shdz[k].trim());
	    				}
	    					}
	    				else{}
	    			}
	    		}

	    		}
	    		}
		});
		*/
		if(serentity.toString()==null||serentity.toString().equals("")){}
		else{
		s4QGDRevisionMasterUI.textserentity.setSelectedItem(serentity.toString());
		s4QGDRevisionMasterUI.textinvenORG.setSelectedItem(invenORG.toString());
	//	s4QGDRevisionMasterUI.textproFile.setSelectedItem(proFile.toString());
		s4QGDRevisionMasterUI.textDestination.setSelectedItem(Destination.toString());
		s4QGDRevisionMasterUI.textsource.setSelectedItem(source.toString());
		s4QGDRevisionMasterUI.textpurapptype.setSelectedItem(purapptype.toString());
		}
		
	}

	@Override
	public void saveForm() {
		// TODO Auto-generated method stub
		try {
			serentity.setStringValueData(s4QGDRevisionMasterUI.textserentity.getSelectedItem().toString());
			invenORG.setStringValueData(s4QGDRevisionMasterUI.textinvenORG.getSelectedItem().toString());
			System.out.println(s4QGDRevisionMasterUI.textinvenORG.getSelectedItem().toString());
		//	proFile.setStringValueData(s4QGDRevisionMasterUI.textproFile.getSelectedItem().toString());
			Destination.setStringValueData(s4QGDRevisionMasterUI.textDestination.getSelectedItem().toString());
			source.setStringValueData(s4QGDRevisionMasterUI.textsource.getSelectedItem().toString());
			purapptype.setStringValueData(s4QGDRevisionMasterUI.textpurapptype.getSelectedItem().toString());
			
		TCProperty[] tcProperty = new TCProperty[5];
		tcProperty[0] = serentity;
		tcProperty[1] = invenORG;
	//	tcProperty[2] = proFile;
		tcProperty[2] = Destination;
		tcProperty[3] = source;
		tcProperty[4] = purapptype;

		

			form.setTCProperties(tcProperty);
		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void initUI(){
		setLayout(new GridLayout(1, 1));
		s4QGDRevisionMasterUI = new S4QGDRevisionMasterUI();
		JTabbedPane jTabbedPane = s4QGDRevisionMasterUI.getJTabbedPane();
		add(jTabbedPane);
		jTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
   private String[] getTCPreferenceArray(TCSession tcSession, String preferenceName) {
		
		String[] preString = null;
	
		TCPreferenceService tcPreservice = tcSession.getPreferenceService();
		
		preString = tcPreservice.getStringArray(TCPreferenceService.TC_preference_site, preferenceName);

		return preString;
	}
	

}

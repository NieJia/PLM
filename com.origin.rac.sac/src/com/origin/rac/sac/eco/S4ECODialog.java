package com.origin.rac.sac.eco;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.kernel.TCPreferenceService;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class S4ECODialog extends AbstractAIFDialog{

	private static final long serialVersionUID = 1L;

	private AbstractAIFApplication app = null;
	private TCSession session = null;
	private S4ECOCommand command = null;

	public JPanel main_Panel;

//	public JComboBox jcbZuzhi;
	public JComboBox jcbLeixing;
	public JComboBox jcbYuanyin;
	public JComboBox jcbShenqr;
	public JComboBox jcbBumen;

	public JButton yesButton;
	public JButton noButton;

	private static final String preferenceName_ECO_zz_lx_yy = "SAC_ECO_ZZ_LX_YY";
	private String[] name_zz = null;
	private String[] name_lx = null;
	private String[] name_yy = null;
	private String[] name_sqr = null;
	private String[] name_bm = null;
	private String zzdm_str = "";

	public S4ECODialog(S4ECOCommand command, AbstractAIFApplication app,
			TCSession session,String zzdm) {
		super(true);
		this.app = app;
		this.session = session;
		this.command = command;
		this.zzdm_str = zzdm;
		getECO();
		initUI();
	}
	//分解典型工艺路线的首选项
	public void getECO(){
		String[] arr_ECO_zz_lx_yy = getTCPreferenceArray(session, preferenceName_ECO_zz_lx_yy);
		if(arr_ECO_zz_lx_yy.length == 0){
			MessageBox.post("未配置 SAC_ECO_ZZ_LX_YY 首选项","首选项错误", MessageBox.WARNING);
			return;
		}
		if(arr_ECO_zz_lx_yy.length == 5){

			for (int i = 0; i < arr_ECO_zz_lx_yy.length; i++) {
				String[] tmp_ECO_zz_lx_yy  = arr_ECO_zz_lx_yy[i].split("=");
				if(tmp_ECO_zz_lx_yy[0].equals("组织")){
					if(tmp_ECO_zz_lx_yy.length ==2){
						name_zz = tmp_ECO_zz_lx_yy[1].split(",");
					}else if(tmp_ECO_zz_lx_yy.length ==1){
						name_zz = null;
					}
				}
				else if(tmp_ECO_zz_lx_yy[0].equals("类型")){
					if(tmp_ECO_zz_lx_yy.length ==2){
						name_lx = tmp_ECO_zz_lx_yy[1].split(",");
					}else if(tmp_ECO_zz_lx_yy.length ==1){
						name_lx = null;
					}
				}
				else if(tmp_ECO_zz_lx_yy[0].equals("原因")){
					if(tmp_ECO_zz_lx_yy.length ==2){
						name_yy = tmp_ECO_zz_lx_yy[1].split(",");
					}else if(tmp_ECO_zz_lx_yy.length ==1){
						name_yy = null;
					}
				} 
				else if(tmp_ECO_zz_lx_yy[0].equals("申请人")){
					if(tmp_ECO_zz_lx_yy.length ==2){
						name_sqr = tmp_ECO_zz_lx_yy[1].split(";");
					}else if(tmp_ECO_zz_lx_yy.length ==1){
						name_sqr = null;
					}
				} 
				else if(tmp_ECO_zz_lx_yy[0].equals("部门")){
					if(tmp_ECO_zz_lx_yy.length ==2){
						name_bm = tmp_ECO_zz_lx_yy[1].split(",");
					}else if(tmp_ECO_zz_lx_yy.length ==1){
						name_bm = null;
					}
				} 
			}
		}
		else{
			MessageBox.post(" SAC_ECO_ZZ_LX_YY 首选项配置错误","首选项错误", MessageBox.WARNING);
			return;
		}
	}
	public void initUI(){
		this.setTitle("ECO生成");
		main_Panel = new JPanel(new GridLayout(5, 2));
		TitledBorder Border = BorderFactory.createTitledBorder("");
		Border.setTitlePosition(TitledBorder.TOP);
		main_Panel.setBorder(Border);

		/*JLabel lbZuzhi = new JLabel("  组织");
		jcbZuzhi = new JComboBox();
		jcbZuzhi.addItem(" ");
		if(name_zz!=null && name_zz.length>0){
			for (int i = 0; i < name_zz.length; i++) {
				jcbZuzhi.addItem(name_zz[i]);
			}
		}*/
		
		JLabel lbLeixing = new JLabel("  类型");
		//lbLeixing.set
		jcbLeixing = new JComboBox();
		jcbLeixing.addItem(" ");
		if(name_lx!=null && name_lx.length>0){
			for (int i = 0; i < name_lx.length; i++) {
				jcbLeixing.addItem(name_lx[i]);
			}
		}
		
		JLabel lbYuanyin = new JLabel("  原因");
		jcbYuanyin = new JComboBox();
		jcbYuanyin.addItem(" ");
		if(name_yy!=null && name_yy.length>0){
			for (int i = 0; i < name_yy.length; i++) {
				jcbYuanyin.addItem(name_yy[i]);
			}
		}

		JLabel lbShenqr = new JLabel("  申请人");
		jcbShenqr = new JComboBox();
		jcbShenqr.addItem(" ");
		if(name_sqr!=null && name_sqr.length>0){
			for (int i = 0; i < name_sqr.length; i++) {
				jcbShenqr.addItem(name_sqr[i]);
			}
		}
		
		
		JLabel lbBumen = new JLabel("  部门");
		jcbBumen = new JComboBox();
		jcbBumen.addItem(" ");
		if(name_bm!=null && name_bm.length>0){
			for (int i = 0; i < name_bm.length; i++) {
				jcbBumen.addItem(name_bm[i]);
			}
		}
		
		
		yesButton = new JButton("确定");
		yesButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				enter();
			}
		});
		noButton = new JButton("取消");
		noButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				imenter();
			}
		});

		main_Panel.add(lbLeixing);
		main_Panel.add(jcbLeixing);
		main_Panel.add(lbYuanyin);
		main_Panel.add(jcbYuanyin);
		main_Panel.add(lbShenqr);
		main_Panel.add(jcbShenqr);
		main_Panel.add(lbBumen);
		main_Panel.add(jcbBumen);
		main_Panel.add(yesButton);
		main_Panel.add(noButton);

		main_Panel.setSize(430, 250);
		main_Panel.setPreferredSize(new Dimension(430, 250));

		getContentPane().add(main_Panel);
		//this.setAlwaysOnTop(true);
		centerToScreen();
		//pack();
		this.setSize(430, 250);
		this.setPreferredSize(new Dimension(430, 250));
		setVisible(true);
	}
	public void enter(){
		/**
		 * public JComboBox jcbZuzhi;
		 * public JComboBox jcbLeixing;
		 * public JComboBox jcbYuanyin;
		 */

		if(jcbLeixing.getSelectedItem().toString() != null
					&& jcbLeixing.getSelectedItem().toString() != " "
						/*&& jcbYuanyin.getSelectedItem().toString() != null
						&& jcbYuanyin.getSelectedItem().toString() != " "*/){
			
			command.name_zz = zzdm_str;
			command.name_lx = jcbLeixing.getSelectedItem().toString();
			command.name_yy = jcbYuanyin.getSelectedItem().toString();
			command.name_sqr = jcbShenqr.getSelectedItem().toString();
			command.name_bm = jcbBumen.getSelectedItem().toString();
			
			
			S4ECOOperation myOperation = new S4ECOOperation(command, app, session);
			this.session.queueOperation(myOperation);
			this.disposeDialog();//关闭对话框
		}
	}

	public void imenter(){
		this.disposeDialog();
	}

	//得到首选项
	private String[] getTCPreferenceArray(TCSession session, String preferenceName)
	{
		String[] preString = null;
		preString = session.getPreferenceService().getStringArray(TCPreferenceService.TC_preference_site, preferenceName);
		return preString;
	}
}

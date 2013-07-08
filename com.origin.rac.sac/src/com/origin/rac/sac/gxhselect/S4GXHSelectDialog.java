package com.origin.rac.sac.gxhselect;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class S4GXHSelectDialog extends AbstractAIFDialog{

	private static final long serialVersionUID = 1L;
	
	private AbstractAIFApplication app = null;
	private TCSession session = null;
	private S4GXHSelectCommand command = null;
	
	private String relation = "IMAN_specification";
	private ArrayList<String> list = null;
	
	public JPanel main_Panel;
	public JPanel first_Panel;
	public JPanel second_Panel;
	
	public JButton yesButton;
	public JButton noButton;
	
	public JComboBox jcbGongxuhao;
	
	public S4GXHSelectDialog(S4GXHSelectCommand command,AbstractAIFApplication app,
			TCSession session) {
		super(true);
		this.app = app;
		this.session = session;
		this.command = command;
		getGongxuhao();
		initUI();
	}
	public void getGongxuhao(){
		list = new ArrayList<String>();
		TCComponentBOMLine parentLine = command.parentLine;
		try {
			String viewType = parentLine.getProperty("bl_view_type");
			TCComponentItemRevision itemrev = parentLine.getItemRevision();
			TCComponent[] tccomponent = itemrev.getRelatedComponents(relation);
			if(tccomponent.length > 0){
				for(int i = 0; i < tccomponent.length; i++){
					TCComponentForm tccomponentForm = (TCComponentForm)tccomponent[i];
					String tranlogo = tccomponentForm.getFormTCProperty("s4Tranlogo").getStringValue();
					String[] tempTexture = tccomponentForm.getFormTCProperty("s4texture").getStringArrayValue();
					if(viewType.equals("S4MZ0")){
						if(!tranlogo.equals("Y")){
							continue;
						}
						if(!tempTexture[0].equals("MZ0")){
							continue;
						}
					}
					if(viewType.equals("S4P31")){
						if(!tranlogo.equals("Y")){
							continue;
						}
						if(!tempTexture[0].equals("P31")){
							continue;
						}
					}
					String[] tempOpernumber = tccomponentForm.getFormTCProperty("s4opernumber").getStringArrayValue();
					for (int j = 0; j < tempOpernumber.length; j++) {
						if(list.indexOf(tempOpernumber[j]) == -1){
							list.add(tempOpernumber[j]);
						}
					}
				}
			}
			else{
				MessageBox.post("该顶册Bomline未执行过工艺路线！","提示",MessageBox.WARNING);
				return;
			}
		} catch (TCException e) {
			e.printStackTrace();
		}
	}
	public void initUI(){
		this.setTitle("工序号选择");
		main_Panel = new JPanel();
		main_Panel.setLayout(new BoxLayout(main_Panel, BoxLayout.Y_AXIS));
		
		first_Panel = new JPanel(new GridLayout(1, 2));
		JLabel lbIsDianxing = new JLabel("   可选择的工序号");
		jcbGongxuhao = new JComboBox();
		for (int i = 0; i < list.size(); i++) {
			jcbGongxuhao.addItem(list.get(i));
		}
		TitledBorder BorderFirst = BorderFactory.createTitledBorder("");
		BorderFirst.setTitlePosition(TitledBorder.TOP);
		first_Panel.setBorder(BorderFirst);
		first_Panel.add(lbIsDianxing);
		first_Panel.add(jcbGongxuhao);
		
		second_Panel = new JPanel(new GridLayout(1, 2));
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
		TitledBorder BorderSecond = BorderFactory.createTitledBorder("");
		BorderSecond.setTitlePosition(TitledBorder.TOP);
		second_Panel.setBorder(BorderSecond);
		second_Panel.add(yesButton);
		second_Panel.add(noButton);
		
		main_Panel.add(first_Panel);
		main_Panel.add(second_Panel);
		
		main_Panel.setSize(450, 100);
		main_Panel.setPreferredSize(new Dimension(450, 100));

		getContentPane().add(main_Panel);
		centerToScreen();
		this.setSize(450, 100);
		this.setPreferredSize(new Dimension(450, 100));
		setVisible(true);
	}
	public void enter(){
		command.gongxuhao = jcbGongxuhao.getSelectedItem().toString();

		S4GXHSelectOperation myOperation = new S4GXHSelectOperation(command, app, session);
		this.session.queueOperation(myOperation);
		this.disposeDialog();//关闭对话框
	}
	public void imenter(){
		this.disposeDialog();
	}
}

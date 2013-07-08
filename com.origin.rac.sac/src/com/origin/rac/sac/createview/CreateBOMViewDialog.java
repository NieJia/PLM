package com.origin.rac.sac.createview;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentBOMViewRevision;
import com.teamcenter.rac.kernel.TCComponentBOMViewRevisionType;
import com.teamcenter.rac.kernel.TCComponentBOMWindow;
import com.teamcenter.rac.kernel.TCComponentBOMWindowType;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentRevisionRule;
import com.teamcenter.rac.kernel.TCComponentRevisionRuleType;
import com.teamcenter.rac.kernel.TCComponentViewType;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class CreateBOMViewDialog extends AbstractAIFDialog{

	private static final long serialVersionUID = 1L;
	
	private TCSession session = null;
	
	public JPanel main_Panel;
	public JPanel first_Panel;
	public JPanel second_Panel;
	
	public JButton yesButton;
	public JButton noButton;
	
	public JComboBox jcbGongxuhao;
	private String[] view_types = {"S4MZ0","S4P31"};
	private String bomview_type = "";
	private TCComponentItem imancomponentitem = null;
	private boolean flag = false;
	
	
	public CreateBOMViewDialog(TCSession session,TCComponentItem item) {
		super(true);
		this.session = session;
		this.imancomponentitem = item;
		initUI();
	}
	
	
	public void initUI(){
		this.setTitle("创建BOM视图");
		main_Panel = new JPanel();
		main_Panel.setLayout(new BoxLayout(main_Panel, BoxLayout.Y_AXIS));
		
		first_Panel = new JPanel(new GridLayout(1, 2));
		JLabel lbIsDianxing = new JLabel("   BOM视图类型");
		jcbGongxuhao = new JComboBox(view_types);
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
	
	
	//确定按钮事件
	public void enter(){
		bomview_type = jcbGongxuhao.getSelectedItem().toString();
		this.disposeDialog();//关闭对话框
		createView(bomview_type);
		if(!flag){
			MessageBox.post("BOM视图创建成功!","提示",MessageBox.INFORMATION);
		}
	}
	
	
	
	public void imenter(){
		this.dispose();
		this.disposeDialog();
	}
	
	//创建BOM视图版本
	public void createView(String type){
		try {
			TCComponentRevisionRuleType imancomponentrevisionruletype = (TCComponentRevisionRuleType) session
					.getTypeComponent("RevisionRule");
			TCComponentRevisionRule imancomponentrevisionrule = imancomponentrevisionruletype
					.getDefaultRule();
			TCComponentBOMWindowType imancomponentbomwindowtype = (TCComponentBOMWindowType) session
					.getTypeComponent("BOMWindow");
			TCComponentBOMWindow imancomponentbomwindow = imancomponentbomwindowtype
					.create(imancomponentrevisionrule);
			TCComponentBOMViewRevisionType localTCComponentBOMViewRevisionType = (TCComponentBOMViewRevisionType) session
					.getTypeComponent("PSBOMViewRevision");
			TCComponentViewType[] arrayOfTCComponentViewType = localTCComponentBOMViewRevisionType
					.getAvailableViewTypes(imancomponentitem
							.getProperty("item_id"), imancomponentitem
							.getLatestItemRevision().getProperty(
									"item_revision_id"));

			TCComponentViewType tccomponentViewType = null;
			for (int i = 0; i < arrayOfTCComponentViewType.length; i++) {
				System.out.println(arrayOfTCComponentViewType[i]);
				if (type.equals(arrayOfTCComponentViewType[i].toString())) {
					tccomponentViewType = arrayOfTCComponentViewType[i];
					break;
				}
			}
			System.out.println("tccomponentViewType=========>:"
					+ tccomponentViewType);
			TCComponentBOMLine componentbomline1 = null;
			if (tccomponentViewType != null) {
				TCComponentBOMViewRevision localTCComponentBOMViewRevision = localTCComponentBOMViewRevisionType
						.create(imancomponentitem.getProperty("item_id"),
								imancomponentitem.getLatestItemRevision()
										.getProperty("item_revision_id"),
								tccomponentViewType, false);
				System.out.println("localTCComponentBOMViewRevision---->:"+localTCComponentBOMViewRevision);
				TCComponentItemRevision imancomponentitemrevision1 = null;
				TCComponent imancomponent = null;
				componentbomline1 = imancomponentbomwindow.setWindowTopLine(
						imancomponentitem, imancomponentitemrevision1,
						imancomponent, localTCComponentBOMViewRevision);
				imancomponentbomwindow.save();
			}else{
				flag = true;
				MessageBox.post("您选择的对象下已经存在" + type
						+ "类型", "提示", MessageBox.INFORMATION);
				return;
			}
		} catch (TCException e) {
			e.printStackTrace();
		}
	}
}

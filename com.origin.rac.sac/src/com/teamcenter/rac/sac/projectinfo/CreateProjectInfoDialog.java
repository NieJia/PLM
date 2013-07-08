package com.teamcenter.rac.sac.projectinfo;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCTextService;
import com.teamcenter.rac.util.MessageBox;

public class CreateProjectInfoDialog extends AbstractAIFDialog {

	private static final long serialVersionUID = 1L;
	private AbstractAIFUIApplication application;
	private TCSession session;
//	private iTextField tfProjectName = null;
	private JComboBox jcbTemplate = null;
	private JButton btnNext = null;
	private JButton btnExit = null;
	private String[] templateNames;
	private String templateType;
	private String owner;
	private TCComponentFolder templateObject;
	private String item_name;

	public CreateProjectInfoDialog(AbstractAIFUIApplication app, String[] templates, String on,String t,String name) {
		super(true);
		application = app;
		session = (TCSession) app.getSession();
		templateNames = templates;
		owner = on;
		templateType = t;
		item_name  = name;
		initUI();
	}

	// 构建主界面
	private void initUI() {
		setTitle("选择项目模板");
		setLayout(null);
		setResizable(false);
		setPreferredSize(new Dimension(400, 150));

		jcbTemplate = new JComboBox(templateNames);
		jcbTemplate.setBounds(20, 20, 200, 30);

//		tfProjectName = new iTextField();
//		tfProjectName.setText(item_name);
//		tfProjectName.setBounds(20, 20, 200, 25);
//		tfProjectName.setVisible(false); // 初始化隐藏

		btnNext = new JButton("下一步");
		btnNext.setBounds(150, 75, 100, 25);
		btnExit = new JButton("关闭");
		btnExit.setBounds(260, 75, 100, 25);

		// 给按钮和文本框添加监听事件
		btnNext.addActionListener(nextListener());
		btnExit.addActionListener(exitListener());
		// tfProjectName.addTextListener(textListener());
//		tfProjectName.addMouseMotionListener(proNameListener());

		add(jcbTemplate);
//		add(tfProjectName);
		add(btnNext);
		add(btnExit);

		centerToScreen();
		pack();
	}

	// 下一步按钮的监听事件
	private ActionListener nextListener() {
		ActionListener listener = new ActionListener() {

			public void actionPerformed(ActionEvent e1) {
				
				String tempName = jcbTemplate.getSelectedItem().toString();
				System.out.println("tempName===>:"+tempName);
				templateObject = getTemplateFolder(tempName);
				if (templateObject != null) {
					// 关闭当前对话框
					setVisible(false);
					dispose();
					String proName = item_name;
					System.out.println("proName====>:"+proName);
					// 打开树结构对话框
					ShowTemplateObjectsDialog dialog = new ShowTemplateObjectsDialog(application, templateObject, proName,templateType);
					dialog.setVisible(true);
				} else {
					MessageBox.post("未找到选择的模板名称所对应的模板对象", "提示", MessageBox.WARNING);
					return;
				}
				
				
				/*if (jcbTemplate.isVisible()) {
					String tempName = jcbTemplate.getSelectedItem().toString();
					templateObject = getTemplateFolder(tempName);
					if (templateObject != null) {
						jcbTemplate.setVisible(false);
						tfProjectName.setVisible(true);
//						btnNext.setEnabled(false);
					} else {
						MessageBox.post("未找到选择的模板名称所对应的模板对象", "提示", MessageBox.WARNING);
						return;
					}
				} else {
					// 关闭当前对话框
					setVisible(false);
					dispose();
					String proName = tfProjectName.getText().trim();
					// 打开树结构对话框
					ShowTemplateObjectsDialog dialog = new ShowTemplateObjectsDialog(application, templateObject, proName,templateType);
					dialog.setVisible(true);
				}*/
			}

		};
		return listener;
	}

	// 退出按钮的监听事件
	private ActionListener exitListener() {
		ActionListener listener = new ActionListener() {

			public void actionPerformed(ActionEvent e2) {
				// 关闭对话框
				setVisible(false);
				dispose();
			}

		};
		return listener;
	}

	// 项目名称文本框的监听事件
	/*private MouseMotionListener proNameListener() {
		MouseMotionListener listener = new MouseMotionListener() {

			public void mouseMoved(MouseEvent e) {
				if ("".equals(tfProjectName.getText().trim()))
					btnNext.setEnabled(false);
				else
					btnNext.setEnabled(true);
			}

			public void mouseDragged(MouseEvent e) {

			}

		};
		return listener;
	}*/

	// 获取对应的模板对象
	private TCComponentFolder getTemplateFolder(String templateName) {
		TCComponentFolder folder = null;
		try {
			TCTextService tcTextService = session.getTextService();
			String askKey[] = { tcTextService.getTextValue("Name"), tcTextService.getTextValue("Type"), tcTextService.getTextValue("OwningUser") };
			String askValue[] = { templateName, templateType, owner };
			InterfaceAIFComponent objects[] = session.search("常规...", askKey, askValue);
			if (objects != null && objects.length > 0) {
				folder = (TCComponentFolder) objects[0];
			}
		} catch (TCException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return folder;
	}
}

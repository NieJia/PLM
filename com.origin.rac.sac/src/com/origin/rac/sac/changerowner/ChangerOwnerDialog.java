package com.origin.rac.sac.changerowner;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.common.AIFTreeNode;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.common.organization.OrgTreePanel;
import com.teamcenter.rac.kernel.TCComponentGroup;
import com.teamcenter.rac.kernel.TCComponentGroupMember;
import com.teamcenter.rac.kernel.TCComponentRole;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCTextService;
import com.teamcenter.rac.kernel.UserList;
import com.teamcenter.rac.util.DialogIconPanel;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.rac.util.Registry;
import com.teamcenter.rac.util.Separator;

public class ChangerOwnerDialog extends AbstractAIFDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractAIFUIApplication app;
	private TCSession session;
	private InterfaceAIFComponent[] tgetcomponent;
	private TCComponentUser user = null; // 用户
	private TCComponentRole role = null; // 角色
	private TCComponentGroup group = null; // 组织
	private OrgTreePanel treePanel = null; // 用户选择面板
	private JButton button = null;
	private JPanel panel;
	private Separator sp1;
	private JButton btnOk;
	private JButton btnCancel;
	private static String buttonString;
	private String selectUserName;
	private String user_name;
	private TCComponentGroupMember groupMember;
	private static Frame frame;
	private String[] names;
	
	public ChangerOwnerDialog(TCSession session1,AbstractAIFUIApplication app,InterfaceAIFComponent[] targetcomponent,TCComponentGroupMember member){
//		super(true);
		super(frame, buttonString);
		this.app=app;
		this.tgetcomponent=targetcomponent;
		this.session=session1;
		this.groupMember = member;
		this.setAlwaysOnTop(true);
		initUI();// 初始化界面
		setVisible(true);
	}
	
	// 初始化对话框
	public void initUI(){
		setTitle("更改所有权");
		setPreferredSize(new Dimension(400, 200));
		setResizable(false);
		panel = new JPanel(null);
		JLabel label1 = new JLabel("新所有权用户");
		label1.setBounds(30, 50, 100, 25);
		Registry registry = Registry.getRegistry("com.teamcenter.rac.commands.changeownership.changeownership");
		DialogIconPanel iconPanel=new DialogIconPanel(registry.getImageIcon("changeownership.ICON"));
		iconPanel.setBounds(10, 8, 390, 25);
		button = new JButton(registry.getImageIcon("user.ICON"));
		button.setText(groupMember.toString());
		button.setToolTipText(registry.getString("selectUser.TIP"));
		button.setBounds(140, 50, 200, 25);
		JLabel label2 = new JLabel("确认更改所有权？");
		label2.setBounds(30, 90, 180, 25);
		sp1=new Separator();
		sp1.setBounds(10, 120, 390, 5);
		JButton btnY = new JButton("是(Y)");
		btnY.setBounds(80, 135, 80, 25);
		JButton btnN = new JButton("否(N)");
		btnN.setBounds(180, 135, 80, 25);
		panel.add(iconPanel);
		panel.add(label1);
		panel.add(button);
		panel.add(label2);
		panel.add(sp1);
		panel.add(btnY);
		panel.add(btnN);
		
		// 按钮“是”的监听事件
		btnY.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
					if(groupMember.toString().equals(button.getText().toString())){
						MessageBox.post("不能选择自己更改所有权,请重新选择!","提示",MessageBox.INFORMATION);
						return;
					}else{
						closePanel();
						// 调用operation
						ChangerOwnerOperation operation = new ChangerOwnerOperation(app,
									user, group,tgetcomponent);
						operation.executeOperation();
					}
				} catch (Exception e) {
					MessageBox.post(e.toString(), "提示", MessageBox.INFORMATION);
					e.printStackTrace();
				}
			}
		});

		// 按钮“否”的监听事件
		btnN.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				closePanel();
			}

		});

		// 按钮“选择新用户”按钮的监听事件
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// 
				SelectUserDialog dialog = new SelectUserDialog();
				dialog.setVisible(true);
			}
					});
		getContentPane().add(panel);
		centerToScreen();
		
	
	}

	// 用户选择对话框：内部类
	class SelectUserDialog extends AbstractAIFDialog {
		private static final long serialVersionUID = 1L;

		public SelectUserDialog() {
			super(true);
//			setVisible(true);
			this.setAlwaysOnTop(true);
			setResizable(false);
			setTitle("组织选择对话框...");
			setPreferredSize(new Dimension(400, 300));
			JPanel pl = new JPanel(null);
			Separator sp3 = new Separator();
			sp3.setBounds(5, 25, 388, 5);
			JLabel label4 =new JLabel("选择新的所有权用户");
			label4.setBounds(50, 3, 150, 25);
			Registry registry = Registry.getRegistry("com.teamcenter.rac.commands.changeownership.changeownership");
			DialogIconPanel iconPanel=new DialogIconPanel(registry.getImageIcon("changeownership.ICON"));
			iconPanel.setBounds(5, 3, 40, 25);
//			iconPanel.add(label4);
			btnOk = new JButton("确定(O)");
			btnOk.setBounds(70, 240, 80, 25);
			btnOk.setEnabled(false);
			btnCancel = new JButton("取消(C)");
			btnCancel.setBounds(180, 240, 80, 25);
			Separator sp4 = new Separator();
			sp4.setBounds(5, 232, 388, 5);
			

			// 实例化树面板
			treePanel = new OrgTreePanel(session);
			treePanel.setBounds(5, 30, 390, 200);
			pl.add(iconPanel);
			pl.add(sp3);
			pl.add(label4);
			pl.add(treePanel);
			pl.add(btnOk);
			pl.add(btnCancel);
			pl.add(sp4);
			centerToScreen();
			// 树结构的监听事件
			treePanel.getOrgTree().addMouseListener(new MouseListener() {

				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 1) {
						btnOk.setEnabled(true);
						try {
							// 获取用户选择的节点
							AIFTreeNode node = treePanel.getOrgTree().getSelectedNode();
							if(node==null){
								return;
							}
							selectUserName = node.getUserObject().toString();
							System.out.println("selectUserName---->:"+selectUserName);
							names = selectUserName.split("/");
							if(names.length>1){
								user_name = names[names.length-1];
								System.out.println("user_name--->:"+user_name);
								InterfaceAIFComponent[] com = queryUser(user_name);
								for (int i = 0; i < com.length; i++) {
									if(com[i] instanceof TCComponentUser){
										user =(TCComponentUser) com[i];
										break;
									}
								}
							}else{
								user = UserList.getUser(session, selectUserName);
							}
							System.out.println("2==22====user---->:"+user);
							if (user != null) {
								//得到选择用户的组和角色
								TCComponentGroupMember cm = (TCComponentGroupMember) treePanel
										.getOrgTree().getSelectedNode()
										.getComponent();
								if(cm ==null){
									// 得到选择的用户的父节点的父节点，即用户所在的组
									String grp = treePanel.getOrgTree()
											.getSelectedNode().getParent()
											.getParent().toString();
									// 获取选择的用户所在的所有组织
									TCComponentGroup[] groups = user.getGroups();

									// 遍历选择的用户所在的所有组织，获取组织对象
									for (int i = 0; i < groups.length; i++) {
										String a=groups[i].toString();
										if(!a.contains(".")){
											if (grp.equals(a)){
												group = groups[i];
												break;
											}
										}else{
											String aa = groups[i].toString().substring(0,groups[i].toString().indexOf("."));
											if (grp.equals(aa)) {
												group = groups[i];
												break;
											}
										}
									}
									
									// 得到选择的用户的父节点，即用户所在的角色
									String rl = treePanel.getOrgTree()
											.getSelectedNode().getParent()
											.toString();
									// 获取组织下所有的角色
									TCComponentRole[] roles = group.getRoles();

									// 遍历选择的用户所在的所有角色，获取角色对象
									for (int i = 0; i < roles.length; i++) {
										if (rl.equals(roles[i].toString())){
											role = roles[i];
										break;
										}
									}	
								}else{
									group = cm.getGroup();
									role = cm.getRole();
								}
							}
						} catch (TCException e1) {
							e1.printStackTrace();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
				
				public void mouseEntered(MouseEvent arg0) {
					
				}

				public void mouseExited(MouseEvent arg0) {

				}

				public void mousePressed(MouseEvent arg0) {
					
				}

				public void mouseReleased(MouseEvent arg0) {

				}
			});
			getContentPane().add(pl);
			// 按钮“确定”的监听事件
			btnOk.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					
					// 设置按钮的值为选择的用户
					try {
						button.setText(selectUserName);
						buttonString = button.getText();
						
						
						// 关闭对话框
						closeDialog();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});
			// 按钮“取消”的监听事件
			btnCancel.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					closeDialog();
				}

			});

		}

		// 关闭对话框
		private void closeDialog() {
			setVisible(false);
			dispose();
		}
	}
	
	// 关闭对话框
	private void closePanel() {
		setVisible(false);
		dispose();
	}
	
	
	//调用系统管理 - 员工信息查询
	public InterfaceAIFComponent[] queryUser(String id){
		InterfaceAIFComponent[] items = null;
		try {
			TCTextService tcService =session.getTextService();
			String askKey[]={tcService.getTextValue("UserId")};
			String askValue[]={id};
			items =  session.search("管理 - 员工信息", askKey, askValue);
		} catch (TCException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
}

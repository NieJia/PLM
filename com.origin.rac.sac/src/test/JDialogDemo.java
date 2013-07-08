package test;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class JDialogDemo extends JDialog implements ActionListener{
	
	public JDialogDemo() {

		Container contentPane = this.getContentPane();
		JButton jButton1 = new JButton("显示对话框");
		jButton1.addActionListener(this);
		contentPane.add(jButton1);
		this.setTitle("JDialogDemo");
//		this.setSize(300, 300);
//		this.setLocation(400, 400);
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
		int screenHeight = (int) screenSize.getHeight();
		int screenWidth = (int) screenSize.getWidth();
		System.out.println("screenHeight-->:"+screenHeight);
		System.out.println("screenWidth--====>:"+screenWidth);
		this.setSize(screenWidth, screenHeight - 30);
		getWindowStateListeners();
//		setLocation(0, 0);//
		this.setVisible(true);
	}
 /* 响应窗体的按钮事件 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("显示对话框")) {
			HelloDialog hw = new HelloDialog(this);
		}
	}

	class HelloDialog implements ActionListener {
		JDialog jDialog1 = null; // 创建一个空的对话框对象

		HelloDialog(JDialog jFrame) {
			/*
			 * 初始化jDialog1 指定对话框的拥有者为jFrame,标题为"Dialog",当对话框为可视时,其他构件不能
			 * 接受用户的输入(静态对话框)
			 */
			jDialog1 = new JDialog(jFrame, "Dialog", true);
			// 创建一个按钮对象,该对象被添加到对话框中
			JButton jButton1 = new JButton("关闭");
			jButton1.addActionListener(this);
			// 将"关闭"按钮对象添加至对话框容器中
			jDialog1.getContentPane().add(jButton1);
			/* 设置对话框的初始大小 */
			jDialog1.setSize(80, 80);
			/* 设置对话框初始显示在屏幕当中的位置 */
			jDialog1.setLocation(450, 450);
			/* 设置对话框为可见(前提是生成了HelloDialog对象) */
			jDialog1.setVisible(true);
		}

		// 响应对话框中的按钮事件
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("关闭")) {
				// 以下语句等价于jDialog1.setVisible(false);
				/*
				 * public void dispose() 释放由此 Window、其子组件及其拥有的所有子组件所使用的所有本机屏幕资源。
				 * 即这些 Component 的资源将被破坏，它们使用的所有内存都将返回到操作系统， 并将它们标记为不可显示。
				 */
				jDialog1.dispose();
			}
		}
}

	public static void main(String[] args) {
		JDialogDemo test = new JDialogDemo();
	}
}

package com.teamcenter.rac.sac.projectinfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import com.teamcenter.rac.common.TCTypeRenderer;
import com.teamcenter.rac.util.Registry;

public class CheckTreeCellRenderer extends JPanel implements TreeCellRenderer {
	private static final long serialVersionUID = 1L;
	private CheckTreeSelectionModel selectionModel;
	private JCheckBox checkBox = new JCheckBox();
	// private TreeCellRenderer delegate;
	public static boolean flag = true;
	protected TreeLabel label;
	private int count = 0;

	class TreeLabel extends JLabel {

		private static final long serialVersionUID = 1L;

		public void setBackground(Color color) {
			if (color instanceof ColorUIResource)
				color = null;
			super.setBackground(color);
		}

		public void paint(Graphics g) {
			String s;
			if ((s = getText()) != null && 0 < s.length()) {
				setBackground(getBackground());
				setForeground(getForeground());
				Dimension dimension = getPreferredSize();
				int i = 0;
				Icon icon1 = getIcon();
				if (icon1 != null)
					i = icon1.getIconWidth() + Math.max(0, getIconTextGap() - 1);
				g.fillRect(i, 0, dimension.width - 1 - i, dimension.height + 2);
				if (hasFocus) {
					g.setColor(UIManager.getColor("Tree.selectionBorderColor"));
					g.drawRect(i, 0, dimension.width - 1 - i, dimension.height - 1);
				}
			}
			super.paint(g);
		}

		public Dimension getPreferredSize() {
			Dimension dimension = super.getPreferredSize();
			if (dimension != null)
				dimension = new Dimension(dimension.width + 3, dimension.height + 2);
			return dimension;
		}

		void setSelected(boolean flag) {
			isSelected = flag;
		}

		void setFocus(boolean flag) {
			hasFocus = flag;
		}

		boolean isSelected;
		boolean hasFocus;

		TreeLabel() {
			super();
			setOpaque(true);
			setVerticalAlignment(1);
		}
	}

	public CheckTreeCellRenderer(TreeCellRenderer delegate, CheckTreeSelectionModel selectionModel) {
		// this.delegate = delegate;
		this.selectionModel = selectionModel;
		setLayout(new BorderLayout());
		setOpaque(false);
		checkBox.setOpaque(false);
		label = new TreeLabel();
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) value;
		TreeNodeObject newobj = (TreeNodeObject) treenode.getUserObject();
		Object obj = newobj.getNodeObject();
		ImageIcon icon = null;

		TreePath path = tree.getPathForRow(row);
		if (path != null) {
			if (selectionModel.isPathSelected(path, true)) {
				checkBox.setSelected(true);
			} else {
				checkBox.setSelected(selectionModel.isPartiallySelected(path) ? true : false);
			}
		}
		newobj.setIfSelected(checkBox.isSelected());

		// 添加的自定义图标
		if (obj != null) {
			icon = TCTypeRenderer.getIcon(obj);
		} else {
			Registry registry = Registry.getRegistry("com.teamcenter.rac.common.common");
			icon = registry.getImageIcon("Folder.ICON");
		}

		removeAll();
		String s = tree.convertValueToText(newobj, selected, expanded, leaf, row, hasFocus);
		Font font = new Font("宋体", Font.PLAIN, 13);
		label.setFont(font);
		Color color = new Color(10, 36, 106);
		if (!selected) {
			label.setBackground(Color.WHITE);
			label.setForeground(Color.BLACK);
		} else {
			label.setBackground(color);
			label.setForeground(Color.WHITE);
		}
		label.setText(s);
		label.setIcon(icon);
		label.setSelected(selected);
		label.setFocus(hasFocus);
		add(checkBox, BorderLayout.WEST);
		add(label, BorderLayout.CENTER);
		count++;

		return this;
	}

}
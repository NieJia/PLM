package com.teamcenter.rac.sac.projectinfo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import javax.swing.tree.TreePath;

public class CheckTreeManager extends MouseAdapter implements TreeSelectionListener {

	private CheckTreeSelectionModel selectionModel = null;
	private JTree tree = null;
	int hotspot = new JCheckBox().getPreferredSize().width;
	public Vector<String> v_info = new Vector<String>();
	public Vector<String> selected_moren = new Vector<String>();

	public CheckTreeManager(JTree tree,Vector<String> vec) {
		this.tree = tree;
		this.selected_moren = vec;
		selectionModel = new CheckTreeSelectionModel(tree.getModel());
		tree.setCellRenderer(new CheckTreeCellRenderer(tree.getCellRenderer(), selectionModel));
		tree.addMouseListener(this); // Êó±ê¼àÌý
		// selectionModel.addTreeSelectionListener(this); // Ê÷Ñ¡Ôñ¼àÌý
	}

	public void mouseClicked(MouseEvent me) {
		TreePath path = tree.getPathForLocation(me.getX(), me.getY());
		if (path == null)
			return;
		if (me.getX() > tree.getPathBounds(path).x + hotspot)
			return;

		boolean selected = selectionModel.isPathSelected(path, true);
		selectionModel.removeTreeSelectionListener(this);

		try {
			if (selected){
				Object obj = path.getLastPathComponent();
				System.out.println("obj----->:"+obj.toString());
				String node_name = obj.toString();
				/*String lujing = path.toString();
				String[] str = lujing.split(",");
				String s = str[str.length-1];
				String node_name = s.substring(1, s.length()-1);*/
				if(selected_moren.contains(node_name)){
					if(!v_info.contains(node_name)){
						v_info.add(node_name);
					}
				}
//				System.out.println("v_info====***===>:"+v_info);
				selectionModel.removeSelectionPath(path);
			} else{
				selectionModel.addSelectionPath(path);
			}
				
		} finally {
			selectionModel.addTreeSelectionListener(this);
			tree.treeDidChange();
		}
	}

	public CheckTreeSelectionModel getSelectionModel() {
		return selectionModel;
	}

	public void valueChanged(TreeSelectionEvent e) {
		tree.treeDidChange();
	}

	public Vector<String> getVecinfo(){
		return v_info;
	}
	
}
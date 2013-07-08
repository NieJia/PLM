package com.origin.rac.sac.gylx;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.kernel.TCPreferenceService;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class S4GYLXToErpDialog extends AbstractAIFDialog{

	private static final long serialVersionUID = 1L;

	private AbstractAIFApplication app = null;
	private TCSession session = null;
	private S4GYLXToErpCommand command = null;

	public JPanel main_Panel;
	public JPanel first_Panel;
	public JPanel second_Panel;
	public JPanel third_Panel;
	public JPanel forth_Panel;
	public JPanel dianxing_Panel;
	public JPanel notdianxing_Panel;
	public JPanel not_Panel1;

	public JButton yesButton;
	public JButton noButton;
	public JButton addButton;
	public JButton deleteButton;

	public JComboBox jcbDianxing;
	public JComboBox jcbZuzhi;
	public JComboBox jcbZiKuChun;
	public JComboBox jcbHuoWei;
	public JComboBox jcbGongxu;

	public JRadioButton jrbYes;
	public JRadioButton jrbNo;

	public DefaultTableModel tableModel = null;
	public JTable table = null;//项目组人员

	private static final String preferenceName_gylx = "SAC_GYLX_Option";
	private static final String preferenceName_gylx_zz_gxdm_zkc = "SAC_GYLX_ZZ_GXDM_ZKC";
	private static final String preferenceName_gylx_gxdm_bm_zy = "SAC_GYLX_GXDM_BM_ZY";
	private String[] nameDianxing = null;
	private String[] name_gx = null;
	private String[] name_zz = null;

	private int gxNum = 10;
	//private int zyNum = 10;

	public S4GYLXToErpDialog(S4GYLXToErpCommand command,AbstractAIFApplication app,
			TCSession session) {
		super(true);
		this.app = app;
		this.session = session;
		this.command = command;
		command.mapDianxing = getDianxing();
		get_gylx_zz_gxdm_zkc();
		get_gylx_gxdm_bm_zy();
		initUI();
	}
	//分解典型工艺路线的首选项
	public HashMap<String, String> getDianxing(){
		HashMap<String, String> mapDianxing = new HashMap<String, String>();
		String[] arrDianxing = getTCPreferenceArray(session, preferenceName_gylx);
		if(arrDianxing.length == 0){
			MessageBox.post("未配置 SAC_GYLX_Option 首选项","首选项错误", MessageBox.WARNING);
			return null;
		}
		nameDianxing = new String[arrDianxing.length];
		for (int i = 0; i < arrDianxing.length; i++) {
			String[] tmpDianxing = arrDianxing[i].split("=");
			nameDianxing[i] = tmpDianxing[0];
			mapDianxing.put(tmpDianxing[0], tmpDianxing[1]);
		}
		return mapDianxing;
	}
	//分解组织工序代码,子库存首选项
	public void get_gylx_zz_gxdm_zkc(){
		command.map_gylx_zz_gxdm = new HashMap<String, String>();
		command.map_gylx_zz_zkc = new HashMap<String, String>();
		String[] arr_gylx_zz_gxdm_zkc = getTCPreferenceArray(session, preferenceName_gylx_zz_gxdm_zkc);
		if(arr_gylx_zz_gxdm_zkc.length == 0){
			MessageBox.post("未配置 SAC_GYLX_ZZ_GXDM_ZKC 首选项","首选项错误", MessageBox.WARNING);
			return;
		}
		name_zz = new String[arr_gylx_zz_gxdm_zkc.length];
		for (int i = 0; i < arr_gylx_zz_gxdm_zkc.length; i++) {
			String[] tmp_gylx_zz_gxdm_zkc = arr_gylx_zz_gxdm_zkc[i].split("=");
			name_zz[i] = tmp_gylx_zz_gxdm_zkc[0];
			command.map_gylx_zz_gxdm.put(tmp_gylx_zz_gxdm_zkc[0], tmp_gylx_zz_gxdm_zkc[1]);
			command.map_gylx_zz_zkc.put(tmp_gylx_zz_gxdm_zkc[0], tmp_gylx_zz_gxdm_zkc[2]);
		}
		return;
	}
	//分解工序代码，部门首选项
	public void get_gylx_gxdm_bm_zy(){
		command.map_gylx_gxdm_bm = new HashMap<String, String>();
		String[] arr_gylx_gxdm_bm_zy = getTCPreferenceArray(session, preferenceName_gylx_gxdm_bm_zy);
		if(arr_gylx_gxdm_bm_zy.length == 0){
			MessageBox.post("未配置 SAC_GYLX_GXDM_BM_ZY 首选项","首选项错误", MessageBox.WARNING);
			return;
		}
		for (int i = 0; i < arr_gylx_gxdm_bm_zy.length; i++) {
			String[] tmp_gylx_gxdm_bm_zy = arr_gylx_gxdm_bm_zy[i].split("=");
			command.map_gylx_gxdm_bm.put(tmp_gylx_gxdm_bm_zy[0], tmp_gylx_gxdm_bm_zy[1]);
		}
		return;
	}
	public void initUI(){

		this.setTitle("工艺路线配置");
		main_Panel = new JPanel();
		main_Panel.setLayout(new BoxLayout(main_Panel, BoxLayout.Y_AXIS));

		first_Panel = new JPanel(new GridLayout(1, 4));
		//first_Panel.setLayout(new BoxLayout(first_Panel, BoxLayout.X_AXIS));
		JLabel lbIsDianxing = new JLabel(" 是否典型工艺路线");
		JLabel lbNull = new JLabel("");
		jrbYes = new JRadioButton("是");
		jrbYes.setSelected(true);
		jrbNo = new JRadioButton("否");
		ButtonGroup group = new ButtonGroup();
		group.add(jrbYes);
		group.add(jrbNo);
		TitledBorder BorderFirst = BorderFactory.createTitledBorder("");
		BorderFirst.setTitlePosition(TitledBorder.TOP);
		first_Panel.setBorder(BorderFirst);
		first_Panel.add(lbIsDianxing);
		first_Panel.add(lbNull);
		first_Panel.add(jrbYes);
		first_Panel.add(jrbNo);

		second_Panel = new JPanel();
		second_Panel.setLayout(new BoxLayout(second_Panel, BoxLayout.Y_AXIS));
		dianxing_Panel = new JPanel(new GridLayout(1, 2));
		JLabel lbDianxing = new JLabel(" 请选择典型工艺类型模板");
		jcbDianxing = new JComboBox();
		jcbDianxing.addItem(" ");
		if(nameDianxing != null){
			for (int i = 0; i < nameDianxing.length; i++) {
				jcbDianxing.addItem(nameDianxing[i]);
			}
		}
		dianxing_Panel.add(lbDianxing);
		dianxing_Panel.add(jcbDianxing);

		notdianxing_Panel = new JPanel();
		notdianxing_Panel.setLayout(new BoxLayout(notdianxing_Panel, BoxLayout.Y_AXIS));
		not_Panel1 =	new JPanel(new GridLayout(1, 4));
		JLabel lbZuzhi = new JLabel(" 组织名称");
		jcbZuzhi = new JComboBox();
		jcbZuzhi.addItem(" ");
		if(name_zz != null){
			for (int i = 0; i < name_zz.length; i++) {
				jcbZuzhi.addItem(name_zz[i]);
			}
		}
		JLabel lbGongxu = new JLabel(" 工序代码");
		jcbGongxu = new JComboBox();
		jcbGongxu.addItem(" ");
		if(name_gx != null){
			for (int i = 0; i < name_gx.length; i++) {
				jcbGongxu.addItem(name_gx[i]);
			}
		}
		not_Panel1.add(lbZuzhi);
		not_Panel1.add(jcbZuzhi);
		not_Panel1.add(lbGongxu);
		not_Panel1.add(jcbGongxu);

		//----------------------------------------
		Object[] columnNames = {"", "", "", ""};
		Object[][] data1 = new Object[0][4];
		tableModel = new DefaultTableModel(data1, columnNames){   
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row,int column){ 
				return false;
			}
		};
		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(20);
		JScrollPane scrollPane = new JScrollPane(table);

		JPanel not_Panel3 =	new JPanel(new GridLayout(1, 2));
		//====================================================
		addButton = new JButton("添加");
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(jcbGongxu.getSelectedItem() != null
						&& jcbGongxu.getSelectedItem() != " "
							&& jcbZuzhi.getSelectedItem() != null
							&& jcbZuzhi.getSelectedItem() != " "){
					String tmpgongxu = jcbGongxu.getSelectedItem().toString();
					System.out.println("tmpgongxu ==> "+tmpgongxu);
					String[] gongxu = tmpgongxu.split(":");
					String bumen = command.map_gylx_gxdm_bm.get(gongxu[0]);
					System.out.println("+bumen===>:"+bumen);
					if(bumen==null || "".equals(bumen)){
						MessageBox.post("部门代码为空,请检查首选项SAC_GYLX_GXDM_BM_ZY!", "提示", MessageBox.INFORMATION);
					}else{
						int rowCount = table.getRowCount();
						if(rowCount == 0){
							gxNum = 10;

							String[] newRow = {gxNum+"", 
									gongxu[0],
									bumen,
									jcbZuzhi.getSelectedItem().toString()};
							tableModel.addRow(newRow);
						}
						if(rowCount > 0){

							if(table.getValueAt(table.getRowCount()-1, 3).toString()
									.equals(jcbZuzhi.getSelectedItem().toString())){

								if(table.getValueAt(table.getRowCount()-1, 1).toString()
										.equals(jcbGongxu.getSelectedItem().toString())){
									String num = table.getValueAt(table.getRowCount()-1, 4).toString();
								}
								else{
									String num = table.getValueAt(table.getRowCount()-1, 0).toString();
									gxNum = Integer.parseInt(num) + 10; 
								}
								
								String[] newRow = {gxNum+"", 
										gongxu[0],
										bumen,
										jcbZuzhi.getSelectedItem().toString()};
								tableModel.addRow(newRow);
							}
							else{
								MessageBox.post("配置路线需要在同一组织下!", "提示", MessageBox.INFORMATION);
							}
						}
					}
				}
			}
		});


		deleteButton = new JButton("删除");
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int[] selectedRow = table.getSelectedRows();
				for(int i = 0; i < selectedRow.length; i++){
					if(selectedRow[i]!= -1)  //存在选中行
					{
						tableModel.removeRow(table.getSelectedRow());  //删除行
					}
				}
			}
		});
		not_Panel3.add(addButton);
		not_Panel3.add(deleteButton);

		notdianxing_Panel.add(not_Panel1);
		notdianxing_Panel.add(scrollPane);
		notdianxing_Panel.add(not_Panel3);
		notdianxing_Panel.setVisible(false);

		TitledBorder BorderSecond = BorderFactory.createTitledBorder("");
		BorderSecond.setTitlePosition(TitledBorder.TOP);
		second_Panel.setBorder(BorderSecond);
		second_Panel.add(dianxing_Panel);
		second_Panel.add(notdianxing_Panel);

		third_Panel = new JPanel(new GridLayout(1, 4));
		JLabel lbZiKuChun = new JLabel(" 子库存");
		jcbZiKuChun = new JComboBox();
		JLabel lbHuoWei = new JLabel(" 货位");
		jcbHuoWei = new JComboBox();
		TitledBorder BorderThird = BorderFactory.createTitledBorder("");
		BorderFirst.setTitlePosition(TitledBorder.TOP);
		third_Panel.setBorder(BorderThird);
		third_Panel.add(lbZiKuChun);
		third_Panel.add(jcbZiKuChun);
		third_Panel.add(lbHuoWei);
		third_Panel.add(jcbHuoWei);

		forth_Panel = new JPanel(new GridLayout(1, 2));
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
		TitledBorder BorderForth = BorderFactory.createTitledBorder("");
		BorderForth.setTitlePosition(TitledBorder.TOP);
		forth_Panel.setBorder(BorderForth);
		forth_Panel.add(yesButton);
		forth_Panel.add(noButton);

		jcbDianxing.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String dianxing = jcbDianxing.getSelectedItem().toString();
				if(dianxing != null &&dianxing != " "){
					String[] temp = command.mapDianxing.get(dianxing).split(";");
					String[] tmp = temp[0].split(",");
					String[] zkc_hw = command.map_gylx_zz_zkc.get(tmp[3]).split(",");
					//String[] zkc_hw = jietmp[1].split(",");
					jcbZiKuChun.removeAllItems();
					jcbHuoWei.removeAllItems();
					jcbZiKuChun.addItem(" ");
					jcbHuoWei.addItem(" ");
					for (int i = 0; i < zkc_hw.length; i++) {
						jcbZiKuChun.addItem(zkc_hw[i]);
						jcbHuoWei.addItem(zkc_hw[i]);
					}
				}   
			}
		});
		jcbZuzhi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String zuzhi = jcbZuzhi.getSelectedItem().toString();
				if(zuzhi != null &&zuzhi != " "){
					String[] gongxu = command.map_gylx_zz_gxdm.get(zuzhi).split(",");
					jcbGongxu.removeAllItems();
					jcbGongxu.addItem(" ");
					for (int i = 0; i < gongxu.length; i++) {
						jcbGongxu.addItem(gongxu[i]);
					}
					String[] zkc_hw = command.map_gylx_zz_zkc.get(zuzhi).split(",");
					jcbZiKuChun.removeAllItems();
					jcbHuoWei.removeAllItems();
					jcbZiKuChun.addItem(" ");
					jcbHuoWei.addItem(" ");
					for (int i = 0; i < zkc_hw.length; i++) {
						jcbZiKuChun.addItem(zkc_hw[i]);
						jcbHuoWei.addItem(zkc_hw[i]);
					} 
				}
			}
		});
		jcbZiKuChun.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				jcbHuoWei.setSelectedItem(jcbZiKuChun.getSelectedItem());
			}
		});
		jrbYes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				second_Panel.validate();
				second_Panel.updateUI();
				dianxing_Panel.setVisible(true);
				notdianxing_Panel.setVisible(false);
			}
		});
		jrbNo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				second_Panel.validate();
				second_Panel.updateUI();
				dianxing_Panel.setVisible(false);
				notdianxing_Panel.setVisible(true);
			}
		});
		main_Panel.add(first_Panel);
		main_Panel.add(second_Panel);
		main_Panel.add(third_Panel);
		main_Panel.add(forth_Panel);

		main_Panel.setSize(750, 200);
		main_Panel.setPreferredSize(new Dimension(750, 200));

		getContentPane().add(main_Panel);
		centerToScreen();
		//pack();
		//this.setAlwaysOnTop(true);
		this.setSize(750, 200);
		this.setPreferredSize(new Dimension(750, 200));
		setVisible(true);
	}
//	得到首选项
	private String[] getTCPreferenceArray(TCSession session, String preferenceName)
	{
		String[] preString = null;
		preString = session.getPreferenceService().getStringArray(TCPreferenceService.TC_preference_site, preferenceName);
		return preString;
	}
	public void enter(){
		if(jrbYes.isSelected() && !jrbNo.isSelected()){
			if(jcbDianxing.getSelectedItem().toString() != null
					&& jcbDianxing.getSelectedItem().toString() != " "
/*						&& jcbZiKuChun.getSelectedItem().toString() != null
						&& jcbZiKuChun.getSelectedItem().toString() != " " 
							&& jcbHuoWei.getSelectedItem().toString() != null 
							&& jcbHuoWei.getSelectedItem().toString() != " "*/
								){

				command.zhiKuCun = jcbZiKuChun.getSelectedItem().toString();
				System.out.println("zhiKuCun--->:"+command.zhiKuCun);
				command.huoWei = jcbHuoWei.getSelectedItem().toString();

				String luxian = command.mapDianxing.get(jcbDianxing.getSelectedItem().toString());
				String[] listLuxian = luxian.split(";");
				command.gongxuHao = new String[listLuxian.length];
				command.gongxuDaima = new String[listLuxian.length];
				command.bumenDaima = new String[listLuxian.length];
				command.zuzhiDaima = new String[listLuxian.length];
				//command.ziyuanHao = new String[listLuxian.length];
				//command.ziyuangDaima = new String[listLuxian.length];
				for (int i = 0; i < listLuxian.length; i++) {
					String[] tmp = listLuxian[i].split(",");
					command.gongxuHao[i] = tmp[0];
					command.gongxuDaima[i] = tmp[1];
					command.bumenDaima[i] = tmp[2];
					command.zuzhiDaima[i] = tmp[3];
					//command.ziyuanHao[i] = tmp[4];
					//command.ziyuangDaima[i] = tmp[5];
				}
				S4GYLXToErpOperation myOperation = new S4GYLXToErpOperation(command, app, session);
				this.session.queueOperation(myOperation);
				this.disposeDialog();//关闭对话框
			}
		}
		else{
			if(table.getRowCount() > 0 
/*					&& jcbZiKuChun.getSelectedItem().toString() != null
					&& jcbZiKuChun.getSelectedItem().toString() != " " 
						&& jcbHuoWei.getSelectedItem().toString() != null 
						&& jcbHuoWei.getSelectedItem().toString() != " " */
							){

				command.zhiKuCun = jcbZiKuChun.getSelectedItem().toString();
				System.out.println("NNNNNNNNN====zhiKuCun==>:"+command.zhiKuCun);
				command.huoWei = jcbHuoWei.getSelectedItem().toString();

				command.gongxuHao = new String[table.getRowCount()];
				command.gongxuDaima = new String[table.getRowCount()];
				command.bumenDaima = new String[table.getRowCount()];
				command.zuzhiDaima = new String[table.getRowCount()];
				//command.ziyuanHao = new String[table.getRowCount()];
				//command.ziyuangDaima = new String[table.getRowCount()];
				for (int i = 0; i < table.getRowCount(); i++) {
					command.gongxuHao[i] = table.getValueAt(i, 0).toString();
					command.gongxuDaima[i] = table.getValueAt(i, 1).toString();
					System.out.println("");
					command.bumenDaima[i] = table.getValueAt(i, 2).toString();
					command.zuzhiDaima[i] = table.getValueAt(i, 3).toString();
					//command.ziyuanHao[i] = table.getValueAt(i, 4).toString();
					//command.ziyuangDaima[i] = table.getValueAt(i, 5).toString();
				}
				S4GYLXToErpOperation myOperation = new S4GYLXToErpOperation(command, app, session);
				this.session.queueOperation(myOperation);
				this.disposeDialog();//关闭对话框
			}
		}
	}
	public void imenter(){
		this.disposeDialog();
	}
}

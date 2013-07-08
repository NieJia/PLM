package com.origin.rac.sac.htjs;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import cn.com.origin.util.OracleConnect;
import cn.com.origin.util.ProgressBarThread;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemType;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCProperty;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCTextService;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.rac.util.PropertyLayout;

public class S4HTJSDialog extends AbstractAIFDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractAIFApplication app = null;
	private TCComponentFolder targetfolder = null;
	private TCSession session = null;
	
	private JTable SelectTable;
	public DefaultTableModel tableModelTwo = null;
	private String tableTitle[] = { "合同号" ,"合同名称"};
	private JButton okButton;
	private JButton cancelButton;
	
	private String str_type = "S4Gcproj";
	private String relation ="IMAN_master_form";
	
	private ArrayList<String[]> hetonglist = new ArrayList<String[]>();
	
	private ProgressBarThread progressbar = null ;
	
	public S4HTJSDialog(AbstractAIFApplication app,TCComponentFolder targetfolder)
	{
		super(AIFUtility.getActiveDesktop());
		this.app = app;
		this.session = (TCSession) this.app.getSession();
		this.targetfolder = targetfolder;
		initUI();
	}
	
	private void initUI()
	{
		setTitle("选择合同对象");
		setResizable(false);
		JPanel mainPanel = new JPanel(new PropertyLayout());
		getContentPane().add(mainPanel);
		
		tableModelTwo = new DefaultTableModel(tableTitle,0);
		SelectTable = new JTable(tableModelTwo);
		SelectTable.getTableHeader().setReorderingAllowed(false) ;
		setColumnWidth(SelectTable, 0, 122);
		setColumnWidth(SelectTable, 1, 123);
		JScrollPane selectscrollPane = new JScrollPane(SelectTable);
		selectscrollPane.setPreferredSize(new Dimension(250,200));
		JPanel panel1 =new JPanel(new PropertyLayout());
		JLabel lable =new JLabel("请选择合同:");
		
		JPanel buttonPanel = new JPanel();
		okButton =new JButton("确定");
		okButton.setEnabled(false);
		okButton.addActionListener(this);
		cancelButton =new JButton("取消");
		cancelButton.addActionListener(this);
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		panel1.add("1.1.left",new JLabel("  "));
		panel1.add("1.2.left",lable);
		panel1.add("2.1.left",new JLabel(" "));
		panel1.add("2.2.left",selectscrollPane);
		
		mainPanel.add("1.1.left",panel1);
		mainPanel.add("2.1.right",buttonPanel);
		setSize(new Dimension(330,300));
		setLocation(400,200);
		
		SelectTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		SelectTable.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(SelectTable.getSelectedRow()>=0){
					okButton.setEnabled(true);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		initTableData();
		this.setVisible(true);
		this.pack();
		this.centerToScreen();
	}
	
	/*
	 * 设置表格初始化列宽
	 */
	public void setColumnWidth(JTable table, int index, int width) {
		TableColumn tc = table.getColumnModel().getColumn(index);
		tc.setPreferredWidth(width);
		tc.setWidth(width);
		tc.setMinWidth(width);
		table.getTableHeader().getColumnModel().getColumn(index).setMinWidth(
				width);
	}
	
	/*
	 * 初始化SelectTable表格
	 */
	public void initTableData() {
		hetonglist.clear();
		OracleConnect oraconn = new OracleConnect();
		try {
			Connection conn = oraconn.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select * from CUX.CUX_PLM_OM_CONT_IFACE where rowid in(select min(rowid) from " +
					"CUX.CUX_PLM_OM_CONT_IFACE group by CONTRACT_NUMBER)";
			ResultSet result = stmt.executeQuery(sql);
			if(result!=null )
			{
				while(result.next())
				{
					String[] tempvalue = new String[2];
					tempvalue[0] = String.valueOf(result.getInt("CONTRACT_NUMBER"));
					tempvalue[1] = result.getString("PROJECT_NAME");
					hetonglist.add(tempvalue);
				}
			}
			stmt.close();
			oraconn.closeConn(conn);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(hetonglist!=null && hetonglist.size()>0)
		{
			for (int i = 0; i < hetonglist.size(); i++) {
				String[] tempvalue = hetonglist.get(i);
				String[] newRow = {"", ""};
				tableModelTwo.addRow(newRow);
				SelectTable.setValueAt(tempvalue[0], i, 0);
				SelectTable.setValueAt(tempvalue[1], i, 1);
			}
		}
		
	}
	
	// 关闭对话框
	private void closeDialog() {
		this.setVisible(false);
		this.dispose();
	}
	
	
	//创建ITEM
	public TCComponentItem createItem(String id,String name) {
		try {
			TCComponentItemType itemtype = (TCComponentItemType) session.getTypeComponent(str_type);
			TCComponentItem item = itemtype.create(id, "",
					str_type, name, str_type, null);
			return item;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//调用系统零组件 ID查询
	public InterfaceAIFComponent[] query(String id){
		InterfaceAIFComponent[] items = null;
		try {
			TCTextService tcService =session.getTextService();
			String askKey[]={tcService.getTextValue("ItemID")};
			String askValue[]={id};
			items =  session.search("零组件 ID", askKey, askValue);
		} catch (TCException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
	
	
	//更新表单属性
	private boolean updateForm(TCComponentForm master_form,String itemid,String object_name)
	{
		boolean isOK = false;
		//更新主表单两个基本属性
		try {
			master_form.setProperty("s4contractno", itemid);
			master_form.setProperty("s4projectnam", object_name);
		} catch (TCException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<S4HeToneInfo> hetonginfolist = new ArrayList<S4HeToneInfo>();
	
		OracleConnect oraconn = new OracleConnect();
		try {
			Connection conn = oraconn.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select * from CUX.CUX_PLM_OM_CONT_IFACE where CONTRACT_NUMBER="+itemid+"";
			ResultSet result = stmt.executeQuery(sql);
			if(result!=null )
			{
				while(result.next())
				{
					S4HeToneInfo oneinfo = new S4HeToneInfo();
					
					oneinfo.setS4totalprice(String.valueOf(result.getInt("TOTAL_PRICE"))==null?"":String.valueOf(result.getInt("TOTAL_PRICE")));
					oneinfo.setS4orderunit(result.getString("CUSTOMER_NAME")==null?"":result.getString("CUSTOMER_NAME"));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					oneinfo.setS4deliverydate(sdf.format(result.getDate("DELIVERY_DATE"))==null?"":sdf.format(result.getDate("DELIVERY_DATE")));
					oneinfo.setS4vdc(result.getString("VDC")==null?"":result.getString("VDC"));
					oneinfo.setS4ddcc(result.getString("DDCC")==null?"":result.getString("DDCC"));
					oneinfo.setS4babintasize(result.getString("BABINTASIZE")==null?"":result.getString("BABINTASIZE"));
					
					//后增加编码和描述
					oneinfo.setS4Gcode(result.getString("ORDERED_ITEM_DSP")==null?"":result.getString("ORDERED_ITEM_DSP"));
					oneinfo.setS4Gdescription(result.getString("ITEM_DESCRIPTION")==null?"":result.getString("ITEM_DESCRIPTION"));
					
					hetonginfolist.add(oneinfo);
				}
			}
			stmt.close();
			oraconn.closeConn(conn);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(hetonginfolist!=null && hetonginfolist.size()>0)
		{
			try {
				master_form.setProperty("s4totalprice", hetonginfolist.get(0).getS4totalprice());
				master_form.setProperty("s4orderunit", hetonginfolist.get(0).getS4orderunit());
				master_form.setProperty("s4deliverydate", hetonginfolist.get(0).getS4deliverydate());
			} catch (TCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				String[] attris = new String[]{"s4coding1","s4discriptions1","s4vdc1","s4ddcc1","s4babintasize1"};
				String[][] tempvalues = new String[hetonginfolist.size()][5];
				TCProperty[] pros = master_form.getTCProperties(attris);
				for(int i=0;i<hetonginfolist.size();i++)
				{
					S4HeToneInfo oneinfo = hetonginfolist.get(i);
					tempvalues[i][0] = oneinfo.getS4Gcode();
					tempvalues[i][1] = oneinfo.getS4Gdescription();
					tempvalues[i][2] = oneinfo.getS4vdc();
					tempvalues[i][3] = oneinfo.getS4ddcc();
					tempvalues[i][4] = oneinfo.getS4babintasize();
					System.out.println("tempvalues["+i+"][0]"+tempvalues[i][0]);
					System.out.println("tempvalues["+i+"][1]"+tempvalues[i][1]);
					System.out.println("tempvalues["+i+"][2]"+tempvalues[i][2]);
					System.out.println("tempvalues["+i+"][3]"+tempvalues[i][3]);
					System.out.println("tempvalues["+i+"][4]"+tempvalues[i][4]);
				}
				
				for(int i=0;i<pros.length;i++)
				{
					TCProperty onepor = pros[i];
					String[] values = new String[tempvalues.length];
					for(int j=0;j<tempvalues.length;j++)
					{
						values[j] = tempvalues[j][i];
					}
					onepor.setStringValueArray(values);
					
				}
				master_form.setTCProperties(pros);
				isOK = true;
			} catch (TCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return isOK;
	}
	
	
	private void updateTable(String itemid,boolean result,String errmessage)
	{
		String state = "N";
		if(result)
		{
			state = "Y";
			errmessage = "";
		}
		
		// 实例化数据库连接
		OracleConnect oraconn = new OracleConnect();
		try {
			Connection conn = oraconn.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "update CUX.CUX_PLM_OM_CONT_IFACE set PROCESS_FLAG='"+state+"',ERROR_MSG='"+errmessage+"' " +
					"where CONTRACT_NUMBER="+itemid+"";
			int k = stmt.executeUpdate(sql);
			if(k>0)
				System.out.println("更新合同表成功");
			
			stmt.close();
			oraconn.closeConn(conn);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == okButton)
		{
			if(SelectTable.getSelectedRow()>=0)
			{
				this.session.queueOperation(new AbstractAIFOperation(){
					@Override
					public void executeOperation() throws Exception {
						// TODO Auto-generated method stub
						progressbar = new ProgressBarThread("合同接收" ,"合同接收中,请稍等...");
						progressbar.start();
						int[] row = SelectTable.getSelectedRows();
						for(int i=0;i<row.length;i++)
						{
							String[] tempvalue = hetonglist.get(row[i]);
							String item_id = tempvalue[0];
							String item_name = tempvalue[1];
							InterfaceAIFComponent[] items = query(item_id);
							if(items==null || items.length==0)
							{
								TCComponentItem item = createItem(item_id, item_name);
								try {
									if(item!=null)
										targetfolder.add("contents", item);
									else
									{
										progressbar.stopBar();
										MessageBox.post(item_id+"合同无法接收失败，创建失败","提示",MessageBox.WARNING);
										return;
									}
										
									
									TCComponentForm master_form = (TCComponentForm) item.getRelatedComponent(relation);
									//更新表单属性
									boolean result = updateForm(master_form, item_id,item_name);
									//更新中间表传送标记
									updateTable(item_id,result,"接收失败");
									if(result)
									{
										continue;
									}
									else
									{
										progressbar.stopBar();
										MessageBox.post(item_id+"合同接收失败","提示",MessageBox.WARNING);
										return;
									}
								} catch (TCException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							else
							{
								progressbar.stopBar();
								MessageBox.post("合同号为："+item_id+"已存在，无法创建","提示",MessageBox.WARNING);
								return;
							}
						}
						progressbar.stopBar();
						MessageBox.post("所选合同接收成功","提示",MessageBox.INFORMATION);
						return;
					}});
			}
		}
		else if(e.getSource() == cancelButton)
		{
			closeDialog();
		}
	}
	
}

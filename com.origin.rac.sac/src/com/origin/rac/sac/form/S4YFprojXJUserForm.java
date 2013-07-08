package com.origin.rac.sac.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import cn.com.origin.util.SACGridBagLaiYRenderer;
import cn.com.origin.util.SACTextAreaEditor1280;
import cn.com.origin.util.SACTextFieldEditorLov;
import cn.com.origin.util.SACDocument1280;
import cn.com.origin.util.SACDocument256;
import cn.com.origin.util.SACDocument32;
import cn.com.origin.util.SACDocument4000;
import cn.com.origin.util.SACGridBagRenderer;
import cn.com.origin.util.SACJTextField128;
import cn.com.origin.util.SACJTextField1280;
import cn.com.origin.util.SACJTextField32;
import cn.com.origin.util.SACJTextField64;
import cn.com.origin.util.SACTextFieldEditor1280;
import cn.com.origin.util.SACTextFieldEditor32;
import cn.com.origin.util.SACTextFieldEditor64;
import cn.com.origin.util.SACTextFieldEditorSJ;
import cn.com.origin.util.SAC_TableCellRenderer;


import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.commands.namedreferences.ImportFilesOperation;
import com.teamcenter.rac.commands.open.OpenFormDialog;
import com.teamcenter.rac.form.AbstractTCForm;
import com.teamcenter.rac.kernel.ListOfValuesInfo;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentDatasetType;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentFormType;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemType;
import com.teamcenter.rac.kernel.TCComponentListOfValues;
import com.teamcenter.rac.kernel.TCComponentListOfValuesType;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCFormProperty;
import com.teamcenter.rac.kernel.TCProperty;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.DateButton;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.rac.util.PropertyLayout;                               //布局的新的方法
import com.teamcenter.rac.util.iTextArea;

public class S4YFprojXJUserForm extends AbstractTCForm {

	private static final long serialVersionUID = 1L;
	private TCComponentForm form = null;
	private TCSession session = null;
	private TCProperty prjName = null;//拟项目名称
	//private TCProperty prjId = null;//研制令号
	// private TCProperty prjClass = null;//研制部门
	// private TCProperty prjMan = null;//项目负责人
	private TCProperty prjContent = null;// 项目内容
	private TCProperty adoStand = null;// 采用标准
	private TCProperty startT = null;// 起始时间
	private TCProperty endT = null;// 起始时间
	private TCProperty name = null;// 姓名
	private TCProperty sex = null;// 性别
	private TCProperty title = null;// 职称
	private TCProperty age = null;// 年龄
	private TCProperty mainWork = null;// 承担本项目主要工作
	private TCProperty inMonth = null;// 投入月数
	private TCProperty remark = null;// 备注
	private TCProperty prjCost = null;// 项目总费用
	private TCProperty ina = null;// 其中a年
	private TCProperty inb = null;// 其中b年
	private TCProperty inc = null;// 其中c年
	private TCProperty remark1 = null;// 备注1
	private TCProperty preStage = null;// 预研阶段
	private TCProperty prjDesign = null;// 方案设计
	private TCProperty proTest = null;// 样机试制
	private TCProperty standC = null;// 检测与试运行
	private TCProperty designCom = null;// 设计确认
	private TCProperty cbareson = null;// 计算根据及理由
	// private TCProperty proposer = null;//申请人
	// private TCProperty tddHead = null;//研制部门技术负责人
	// private TCProperty tsection = null;//技术部门
	// private TCProperty xiangmuyueb = null;//项目月报
	private TCProperty fujian =null;//附件

	private String[] strStartT = null;
	private String[] strEndT = null;
	private String[] strName = null;
	private String[] strSex = null;
	private String[] strTitle = null;
	private String[] strAge = null;
	private String[] strMainWork = null;
	private String[] strInMonth = null;
	private String[] strRemark = null;
	private String[] StrInta = null;
	private String[] StrIntb = null;
	private String[] StrIntc = null;
	private String[] strRemark1 = null;
	private String[] strPreStage = null;
	private String[] strPrjDesign = null;
	private String[] strProTest = null;
	private String[] strStandC = null;
	private String[] strDesignCom = null;
	private String[] strCbareson = null;
	// private String[] strXiangmyb = null;
	private String strfujian[] = null;//附件
	

	public SACJTextField128 textPrjName = null;// 拟项目名称
	public SACJTextField32 textPrjId = null;// 研制令号
	// public SACJTextField128 textPrjClass = null;//研制部门
	// public SACJTextField32 textPrjMan = null;//项目负责人
	public JTextArea textPrjContent = null;// 项目内容
	public JTextArea textAdoStand = null;// 采用标准
	public JTable tableJinDuAnPai = null;// 进度安排
	public JTable tableXiangMuZuRenYuan = null;// 项目组人员
	public SACJTextField32 textPrjCost = null;// 项目总费用
	public JTable tableJingJiLaiYuan = null;// 经费来源
	public JTable tableYuSuanZhiChu = null;// 预算支出
	// public JTable tableXiangMuYueBao = null;//项目月报
	// public SACJTextField32 textProposer = null;//申请
	// public SACJTextField32 textTddhead = null;//研制部门技术负责人
	// public SACJTextField32 textTsection = null;//技术部门

	// public JTextField textseiban = null;//seiban号
	public SACJTextField32 textseiban = null;                // seiban号
	public JComboBox projCheckbox = null;// 项目类型选择框
	public JComboBox teamCheckbox = null;// 集团项目选择框
	// public String[] proj_str =
	// {"","装置类小结项目","装置类鉴定项目","软件类小结项目","软件类鉴定项目","课题类项目","集团项目","系统类项目"};//默认项目类型选择框的内容
	public String[] proj_str1;
	public String[] proj_str;
	public String[] folder_str;
	public String[] team_str = { "", "是", "否" };// 默认集团项目选择框的内容
	public HashMap<String, String> map_all = new HashMap<String, String>();
	public HashMap<String, Vector<String>> map_1 = new HashMap<String, Vector<String>>();
	public HashMap<String, Vector<String>> map_2 = new HashMap<String, Vector<String>>();
	public HashMap<String, Vector<String>> map_3 = new HashMap<String, Vector<String>>();
	public boolean flag = false;
	public boolean flag1 = false;
	public Vector<String> v_info = new Vector<String>();                       //用来控制是否为必填的属性
	public Vector<TCComponentFolder> v_folder = new Vector<TCComponentFolder>();

	//private SACJTextField1280 syfwiTextField;// 适用范围 (需要修改的地方)

    private JTextArea syfwiTextField;            //适用范围
	private DateButton qznyidatebutton;// 起止年月
	private DateButton jznyidatebutton;// 截止年月
	//private SACJTextField128 cddwybmiTextField;// 承担单位与部门
	private JComboBox  cddwybmiTextField=null;
	private SACJTextField128 cbdwTextField;//承办部门                                                新增加的部分
	
	private JTextArea ysfsiTextField; // 研式方式
	private SACJTextField32 hzdwiTextField;// 合作单位
	private SACJTextField32 hzfsiTextField;// 合作方式
	private SACJTextField64 xmzysiTextField;// 项目总预算
	private SACJTextField64 zjlyiTextField;// 资金来源
	public JTextArea mbjsspiTextField;// 目标技术水平
	private iTextArea zliTextField;// 专利
	private iTextArea zzqiTextField;// 著作权
	private iTextArea lwiTextField;// 论文
	public JTextArea cgysfsiTextField;// 成果验收方式
	private SACJTextField32 xmiTextField1;// 姓名1
	private JComboBox zc_Box1;// 职称1
	private SACJTextField32 zyiTextField1;// 专业1
	private JComboBox yanzdw_Box1;// 研制单位1
	private SACJTextField32 yanziTextField1;// 研制部门1
	private JTextArea lxfsiTextField1;// 联系方式1
	private JTextArea emailiTextField1;// EMail1
	private SACJTextField32 xmiTextField2;// 姓名2
	private JComboBox zc_Box2;// 职称2
	private SACJTextField32 zyiTextField2;// 专业2
	private JComboBox yanzdw_Box2;// 研制单位2
	private SACJTextField32 yanziTextField2;// 研制部门2
	private JTextArea lxfsiTextField2;// 联系方式2
	private JTextArea emailiTextField2;// EMail2
	private SACJTextField32 xmiTextField3;// 姓名3
	private JComboBox zc_Box3;// 职称3
	private SACJTextField32 zyiTextField3;// 专业3
	private JComboBox yanzdw_Box3;// 研制单位3
	private SACJTextField32 yanziTextField3;// 研制部门3
	private JTextArea lxfsiTextField3;// 联系方式3
	private JTextArea emailiTextField3;// EMail3
	private String syfw_str = "[可填写的字符上限为1280][适用范围：项目技术方案及成果产品的使用范围]";//适用范围的提示字符
	private String hzfs_str = "[技术合作、引进、外包等]";
	private String zjly_str = "[公司自筹、政府或上级单位拨款、社会募集等]";
	private String yafs_str = "[注明是自主研发、合作研试、引进接产等，如有合作和外包，说明合作方或包方技术能力。]";
	private String mbjssp_str = "[可填写的字符上限为1280][国际领先水平、国际先进水平、国内领先水平、国内先进水平]";
	private String cgysfs_str = "[国家级或省部级鉴定、企业级鉴定、集团或用户组织验收]";
	private String tixing_str = "[可填写的字符上限为4000]";
	private String tel_str = "Tel:" + "\n" + "Mobile:";
	private String  str_seiban="[由接口人员填写]";                         //需要加载的地方
	private Font font;
	public DefaultTableModel tableModelTwo = null;
	public DefaultTableModel tableModelThree = null;
	public JTabbedPane jTabbedPane = null;
	public int num = 0;
	public int num1 = 0;
	private String relation = "IMAN_reference";
	private String yc_relation = "S4Prj_evadata";
	private TCFormProperty formProperties[] = null;
	private TCComponentItem item = null;
	private AIFComponentContext[] inter;
	private String[] properties_name = { "object_name", "item_id" };
	private String[] values = null;
	private String proj_name = "";
	private String proj_id = "";
	// private String default_date = "2013-03-08";
	private String[] dw_str = null;
	private String[] zc_str = null;
	private HashMap<String, Object> component_map = new HashMap<String, Object>();
	
	
	public String path=null;//附件路径
	public int fujiancount=0;//附件个数
	public LinkLabel filelabel[]=new LinkLabel[50];
	public JPanel accessoryPanel=new JPanel(new GridLayout(1,1));
	
	public JPanel p[]=new JPanel[50];
	JPanel panel3=new JPanel(new BorderLayout());

	public S4YFprojXJUserForm(TCComponentForm arg0) throws Exception {
		super(arg0);
		form = arg0;
		session = (TCSession) form.getSession();
		proj_str1 = session.getPreferenceService().getStringArray(4,
				"SAC_Project_Name");
		if (proj_str1 == null || proj_str1.length == 0) {
			MessageBox.post("未配置首选项：SAC_Project_Name的值", "提示",
					MessageBox.INFORMATION);
			return;
		} else {
			proj_str = new String[proj_str1.length + 1];
			for (int i = 0; i < proj_str1.length; i++) {
				proj_str[0] = "";
				proj_str[i + 1] = proj_str1[i];
			}
		}
		folder_str = session.getPreferenceService().getStringArray(4,
				"SAC_Auto_Create_Item");
		if (folder_str == null || folder_str.length == 0) {
			MessageBox.post("未配置首选项：SAC_Auto_Create_Item的值", "提示",
					MessageBox.INFORMATION);
			return;
		} else {
			for (int i = 0; i < folder_str.length; i++) {
				String[] str = folder_str[i].split(":");
				map_all.put(str[0], str[1]);
			}
		}
		// 得到职称的LOV值
		String title_str[] = session.getPreferenceService().getStringArray(4,
				"SAC_title_Lov");
		if (title_str == null || title_str.length == 0) {
			MessageBox.post("未配置首选项：SAC_title_Lov的值", "提示",
					MessageBox.INFORMATION);
			return;
		} else {
			zc_str = new String[title_str.length + 1];
			zc_str[0] = "";
			for (int i = 0; i < title_str.length; i++) {
				zc_str[i + 1] = title_str[i];

			}
		}
		// 得到职称的LOV值
		String department_str[] = session.getPreferenceService()
				.getStringArray(4, "SAC_Department_Lov");
		if (department_str == null || department_str.length == 0) {
			MessageBox.post("未配置首选项：SAC_Department_Lov的值", "提示",
					MessageBox.INFORMATION);
			return;
		} else {
			dw_str = new String[department_str.length + 1];
			dw_str[0] = "";
			for (int i = 0; i < department_str.length; i++) {
				dw_str[i + 1] = department_str[i];

			}
		}
		v_info.removeAllElements();
		num = form.getTCProperty("s4name").getStringArrayValue().length;          //这两句是干嘛的
		num1 = form.getTCProperty("s4Projmonthly").getStringArrayValue().length;

		inter = form.getPrimary();                              //应该是得到页面的引用
		if (inter.length > 0) {
			// 得到Item
			item = (TCComponentItem) inter[0].getComponent();
		}
		values = item.getProperties(properties_name);
		proj_name = values[0];
		System.out.println("proj_name==>"+proj_name);
	//	proj_id = values[1];                                                      //对研制令号进行修改的地方
		System.out.println("proj_id==>"+proj_id);
		initUI();
		loadForm();
	}

	public void loadForm() throws TCException {

		formProperties = form.getAllFormProperties();
		for (int n = 0; n < formProperties.length; n++) {
			String str = formProperties[n].getPropertyName();
			if (str.equals("s4Prj_name")) {                                  //项目内容
				prjName = formProperties[n];
			}
			else if (str.equals("s4prj_content")) {                                  //项目内容
				prjContent = formProperties[n];
			}else if(str.equals("s4prj_id")){
				textPrjId.setText(formProperties[n].getStringValue());                    //修改的地方
				System.out.println("s4prj_id==>"+formProperties[n].getStringValue());
			}			
			else if (str.equals("s4ado_stand")) {
				adoStand = formProperties[n];
			} else if (str.equals("s4start_t")) {
				startT = formProperties[n];
			} else if (str.equals("s4uptime")) {
				endT = formProperties[n];
			} else if (str.equals("s4name")) {                            //姓名
				name = formProperties[n];
			} else if (str.equals("s4sex")) {
				sex = formProperties[n];
			} else if (str.equals("s4title")) {
				title = formProperties[n];
			} else if (str.equals("s4age")) {
				age = formProperties[n];
			} else if (str.equals("s4mainwork")) {
				mainWork = formProperties[n];
			} else if (str.equals("s4inmonth")) {
				inMonth = formProperties[n];
			} else if (str.equals("s4remark")) {
				remark = formProperties[n];
			} else if (str.equals("s4prj_cost")) {
				prjCost = formProperties[n];
			} else if (str.equals("s4ina")) {
				ina = formProperties[n];
			} else if (str.equals("s4inb")) {
				inb = formProperties[n];
			} else if (str.equals("s4inc")) {
				inc = formProperties[n];
			} else if (str.equals("s4remark1")) {
				remark1 = formProperties[n];
			} else if (str.equals("s4pre_stage")) {
				preStage = formProperties[n];
			} else if (str.equals("s4prj_design")) {
				prjDesign = formProperties[n];
			} else if (str.equals("s4pro_test")) {
				proTest = formProperties[n];
			} else if (str.equals("s4tandc")) {
				standC = formProperties[n];
			} else if (str.equals("s4designcom")) {
				designCom = formProperties[n];
			} else if (str.equals("s4cbareson")) {
				cbareson = formProperties[n];
			} else if (str.equals("s4scope")) {
				if (formProperties[n].getStringValue() != null
						&& !"".equals(formProperties[n].getStringValue()
								.toString())) {
					syfwiTextField.setText(formProperties[n].getStringValue());
				}				
				else {
					syfwiTextField.setText(syfw_str);           
				}				
			} else if (str.equals("s4timed")) {
				if (formProperties[n].getStringValue() != null
						&& !"".equals(formProperties[n].getStringValue())) {
					qznyidatebutton.setDate(formProperties[n].getStringValue());              //DateButton控件从系统中取值
				}
			} else if (str.equals("s4timedup")) {
				if (formProperties[n].getStringValue() != null
						&& !"".equals(formProperties[n].getStringValue())) {
					jznyidatebutton.setDate(formProperties[n].getStringValue());
				}
			} else if (str.equals("s4tandd")) {
				cddwybmiTextField.setSelectedItem(formProperties[n].getStringValue());        //进行修改的地方
			} else if(str.equals("s4tandd1")){
				cbdwTextField.setText(formProperties[n].getStringValue());  
			}
						
			else if (str.equals("s4Grind_testw")) {
				if (formProperties[n].getStringValue() != null
						&& !"".equals(formProperties[n].getStringValue()
								.toString())) {
					ysfsiTextField.setText(formProperties[n].getStringValue());
				} else {
					ysfsiTextField.setText(yafs_str);
				}
			} else if (str.equals("s4Cooperator")) {
				hzdwiTextField.setText(formProperties[n].getStringValue());
			} else if (str.equals("s4Cooperation")) {
				if (formProperties[n].getStringValue() != null
						&& !"".equals(formProperties[n].getStringValue()
								.toString())) {
					hzfsiTextField.setText(formProperties[n].getStringValue());
				}
			} else if (str.equals("s4prj_budget")) {
				xmzysiTextField.setText(formProperties[n].getStringValue());
			} else if (str.equals("s4cap_source")) {
				if (formProperties[n].getStringValue() != null
						&& !"".equals(formProperties[n].getStringValue()
								.toString())) {
					zjlyiTextField.setText(formProperties[n].getStringValue());
				}
			} else if (str.equals("s4goal_resu")) {
				if (formProperties[n].getStringValue() != null
						&& !"".equals(formProperties[n].getStringValue()
								.toString())) {
					mbjsspiTextField
							.setText(formProperties[n].getStringValue());
				} else {
					mbjsspiTextField.setText(mbjssp_str);
				}
			} else if (str.equals("s4patent")) {
				zliTextField.setText(formProperties[n].getStringValue());
			} else if (str.equals("s4copyright")) {
				zzqiTextField.setText(formProperties[n].getStringValue());
			} else if (str.equals("s4thesis")) {
				lwiTextField.setText(formProperties[n].getStringValue());
			} else if (str.equals("s4Results_acceway")) {
				if (formProperties[n].getStringValue() != null
						&& !"".equals(formProperties[n].getStringValue()
								.toString())) {
					cgysfsiTextField
							.setText(formProperties[n].getStringValue());
				} else {
					cgysfsiTextField.setText(cgysfs_str);
				}
			} else if (str.equals("s4name2")) {
				xmiTextField1.setText(formProperties[n].getStringValue());
			} else if (str.equals("s4title2")) {
				zc_Box1.setSelectedItem(formProperties[n].getStringValue());
			} else if (str.equals("s4profession")) {
				zyiTextField1.setText(formProperties[n].getStringValue());
			} else if (str.equals("s4department")) {
				yanzdw_Box1.setSelectedItem(formProperties[n].getStringValue());
			} else if (str.equals("s4prj_class")) {
				yanziTextField1.setText(formProperties[n].getStringValue());
			} else if (str.equals("s4contact_way")) {
				if (formProperties[n].getStringValue() != null
						&& !"".equals(formProperties[n].getStringValue()
								.toString())) {
					lxfsiTextField1.setText(formProperties[n].getStringValue());
				} else {
					lxfsiTextField1.setText(tel_str);           
				}
			} else if (str.equals("s4Email")) {
				emailiTextField1.setText(formProperties[n].getStringValue());
			} else if (str.equals("s4name1")) {
				xmiTextField2.setText(formProperties[n].getStringValue());
			} else if (str.equals("s4title1")) {
				zc_Box2.setSelectedItem(formProperties[n].getStringValue());
			} else if (str.equals("s4profession1")) {
				zyiTextField2.setText(formProperties[n].getStringValue());
			} else if (str.equals("s4department1")) {
				yanzdw_Box2.setSelectedItem(formProperties[n].getStringValue());
			} else if (str.equals("s4prj_class2")) {
				yanziTextField2.setText(formProperties[n].getStringValue());
			} else if (str.equals("s4contact_way1")) {
				if (formProperties[n].getStringValue() != null
						&& !"".equals(formProperties[n].getStringValue()
								.toString())) {
					lxfsiTextField2.setText(formProperties[n].getStringValue());
				} else {
					lxfsiTextField2.setText(tel_str);
				}
			} else if (str.equals("s4Email1")) {
				emailiTextField2.setText(formProperties[n].getStringValue());
			} else if (str.equals("s4name3")) {
				xmiTextField3.setText(formProperties[n].getStringValue());
			} else if (str.equals("s4title3")) {
				zc_Box3.setSelectedItem(formProperties[n].getStringValue());
			} else if (str.equals("s4profession3")) {
				zyiTextField3.setText(formProperties[n].getStringValue());
			} else if (str.equals("s4department3")) {
				yanzdw_Box3.setSelectedItem(formProperties[n].getStringValue());
			} else if (str.equals("s4prj_class3")) {
				yanziTextField3.setText(formProperties[n].getStringValue());
			} else if (str.equals("s4contact_way3")) {
				if (formProperties[n].getStringValue() != null
						&& !"".equals(formProperties[n].getStringValue()
								.toString())) {
					lxfsiTextField3.setText(formProperties[n].getStringValue());
				} else {
					lxfsiTextField3.setText(tel_str);
				}
			} else if (str.equals("s4Email3")) {
				emailiTextField3.setText(formProperties[n].getStringValue());
			} else if (str.equals("s4seiban")) {                          //修改的地方  塞班号
				if (formProperties[n].getStringValue() != null
						&& !"".equals(formProperties[n].getStringValue()
								.toString())) {
					textseiban.setText(formProperties[n].getStringValue());
				} else {
					textseiban.setText(str_seiban);
				}								
			} else if (str.equals("s4Projtype")) {
				projCheckbox
						.setSelectedItem(formProperties[n].getStringValue());
			} else if (str.equals("s4teamevent")) {
				teamCheckbox
						.setSelectedItem(formProperties[n].getStringValue());
			}
			 else if (str.equals("s4Conreattach")) {
			fujian=form.getTCProperty("s4Conreattach");
			 }
		}

		textPrjName.setText(proj_name);
	//	textPrjId.setText(prjId.getStringValue());
		if (prjContent.getStringValue() == null
				|| prjContent.getStringValue().equals("")) {
			textPrjContent.setText(tixing_str);
		} else {
			textPrjContent.setText(prjContent.getStringValue());
		}
		if (adoStand.getStringValue() == null
				|| adoStand.getStringValue().equals("")) {
			textAdoStand.setText("采用标准：" + tixing_str);
		} else {
			textAdoStand.setText(adoStand.getStringValue());
		}

		strStartT = startT.getStringArrayValue();
		for (int i = 0; i < strStartT.length; i++) {
			tableJinDuAnPai.setValueAt(strStartT[i], i, 2);
		}
		strEndT = endT.getStringArrayValue();
		for (int i = 0; i < strEndT.length; i++) {
			tableJinDuAnPai.setValueAt(strEndT[i], i, 3);        
		}

		textPrjCost.setText(prjCost.getStringValue());

		strName = name.getStringArrayValue();
		for (int i = 0; i < strName.length; i++) {
			tableXiangMuZuRenYuan.setValueAt(strName[i], i, 0);
		}
		strSex = sex.getStringArrayValue();
		for (int i = 0; i < strSex.length; i++) {
			tableXiangMuZuRenYuan.setValueAt(strSex[i], i, 1);            //项目组人员的性别
		}
		strTitle = title.getStringArrayValue();
		for (int i = 0; i < strTitle.length; i++) {
			tableXiangMuZuRenYuan.setValueAt(strTitle[i], i, 2);            //项目组人员的职称
		}
		strAge = age.getStringArrayValue();
		for (int i = 0; i < strAge.length; i++) {
			tableXiangMuZuRenYuan.setValueAt(strAge[i], i, 3);
		}
		strMainWork = mainWork.getStringArrayValue();
		for (int i = 0; i < strMainWork.length; i++) {
			tableXiangMuZuRenYuan.setValueAt(strMainWork[i], i, 4);
		}
		strInMonth = inMonth.getStringArrayValue();
		for (int i = 0; i < strInMonth.length; i++) {
			tableXiangMuZuRenYuan.setValueAt(strInMonth[i], i, 5);
		}
		strRemark = remark.getStringArrayValue();
		for (int i = 0; i < strRemark.length; i++) {
			tableXiangMuZuRenYuan.setValueAt(strRemark[i], i, 6);
		}

		StrInta = ina.getStringArrayValue();
		for (int i = 0; i < StrInta.length; i++) {
			tableJingJiLaiYuan.setValueAt(StrInta[i], i, 1);
		}
		StrIntb = inb.getStringArrayValue();
		for (int i = 0; i < StrIntb.length; i++) {
			tableJingJiLaiYuan.setValueAt(StrIntb[i], i, 2);
		}
		StrIntc = inc.getStringArrayValue();
		for (int i = 0; i < StrIntc.length; i++) {
			tableJingJiLaiYuan.setValueAt(StrIntc[i], i, 3);
		}
		strRemark1 = remark1.getStringArrayValue();
		for (int i = 0; i < strRemark1.length; i++) {
			tableJingJiLaiYuan.setValueAt(strRemark1[i], i, 4);
		}

		strPreStage = preStage.getStringArrayValue();
		for (int i = 0; i < strPreStage.length; i++) {
			tableYuSuanZhiChu.setValueAt(strPreStage[i], i, 1);
		}
		strPrjDesign = prjDesign.getStringArrayValue();
		for (int i = 0; i < strPrjDesign.length; i++) {
			tableYuSuanZhiChu.setValueAt(strPrjDesign[i], i, 2);
		}
		strProTest = proTest.getStringArrayValue();
		for (int i = 0; i < strProTest.length; i++) {
			tableYuSuanZhiChu.setValueAt(strProTest[i], i, 3);
		}
		strStandC = standC.getStringArrayValue();
		for (int i = 0; i < strStandC.length; i++) {
			tableYuSuanZhiChu.setValueAt(strStandC[i], i, 4);
		}
		strDesignCom = designCom.getStringArrayValue();
		for (int i = 0; i < strDesignCom.length; i++) {
			tableYuSuanZhiChu.setValueAt(strDesignCom[i], i, 5);
		}
		strCbareson = cbareson.getStringArrayValue();
		for (int i = 0; i < strCbareson.length; i++) {
			tableYuSuanZhiChu.setValueAt(strCbareson[i], i, 6);
		}
		
		fujiancount=fujian.getStringArrayValue().length;//附件个数
        strfujian=fujian.getStringArrayValue();
		
		if(fujiancount>0){
		accessoryPanel=new JPanel(new GridLayout(1,1));
		for(int i=0;i<fujiancount;i++)
	 	{
    	p[i]=new JPanel(new FlowLayout(FlowLayout.LEFT));
    	//System.out.println(form.getProperty("s4accessorylink").toString());
    	//s4CSQXBGRevisionUI.filelabel[i]=new LinkLabel(strfujian[i],new File(form.getProperty("s4accessorylink").toString()));
    	filelabel[i]=new LinkLabel(strfujian[i],new File(""));
    	filelabel[i].setForeground(Color.BLUE);
    	//System.out.println(s4CSQXBGRevisionUI.filelabel[i].getText());
    	p[i].add(filelabel[i]);
       	final JLabel label_delete=new JLabel("删除");
    	p[i].add(label_delete);
    	accessoryPanel.setLayout(new GridLayout(accessoryPanel.getComponentCount()+1,1));//调整附件Panel的布局
    	accessoryPanel.add(	p[i]);
    	//System.out.println("*******fujian mianban count**********"+s4CSQXBGRevisionUI.accessoryPanel.getComponentCount());
    	label_delete.addMouseListener(new MouseAdapter(){
			public void mouseExited(MouseEvent e) {  
				label_delete.setForeground(Color.BLACK);
						    	  }                     
			public void mouseEntered(MouseEvent e) { 
				label_delete.setForeground(Color.RED);
				label_delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
				
			public void mouseClicked(MouseEvent e) {
				int result=JOptionPane.showConfirmDialog(null, "您确定要删除该附件吗？","删除附件",JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_OPTION){
					accessoryPanel.setLayout(new GridLayout(accessoryPanel.getComponentCount()-1,1));//调整附件Panel的布局
					int j=0;
					for(int i=0;i<fujiancount;i++)
						if(filelabel[i].getParent()==e.getComponent().getParent())//删除--附件
							{
							   j=i;//选择删除的那个附件
							   try {  
									
									AbstractAIFApplication app= AIFUtility.getCurrentApplication();
									TCSession session=(TCSession)app.getSession();
									TCComponentDatasetType tccomponentDatasetType=(TCComponentDatasetType) session.getTypeComponent("Dataset");
									TCComponentDataset dataset=tccomponentDatasetType.find(filelabel[i].getText());
									//System.out.println(dataset.getUid());
							//		TCComponentItemRevision mItemRevision=(TCComponentItemRevision)app.getTargetContext().getParentComponent();
							//		if (mItemRevision==null){
							//			System.out.println("请选中表单对象");
							//			return;
							//			}
								//	mItemRevision.remove("IMAN_specification",dataset );
									if(dataset==null)//数据集已经被删除，此时不做任何动作
									    {}
									else
									{ 
									   dataset.delete();
									   dataset.clearCache();
									}
									}catch (TCException err) {
										// TODO Auto-generated catch block
										err.printStackTrace();
									}  
							   break;
							}
					for(;j<fujiancount-1;j++){
						filelabel[j].setText(filelabel[j+1].getText());
						filelabel[j].setPath(filelabel[j+1].getPath());
					}
					fujiancount--;
					accessoryPanel.remove(p[fujiancount]);
					accessoryPanel.setLayout(new GridLayout(fujiancount,1));
				//	accessoryPanel.repaint();
					jTabbedPane.repaint();//实时刷新标签上的内容
				//	jTabbedPane.getParent().repaint();
				//	jTabbedPane.setBounds(0, 0, 800, 800);
					jTabbedPane.setBounds(0, 0, jTabbedPane.getWidth(), jTabbedPane.getHeight());
					
				} 
				}
    	}
			);
	 	}
    	panel3.add(accessoryPanel,BorderLayout.CENTER);
	//	s4CSQXBGRevisionUI.textstatus.setEditable(false);
		}
	    System.out.println("********loading end*********");
	}

	@Override
	public boolean isFormSavable(boolean arg0) {

		Vector<String> vec_t = new Vector<String>();
		int size = component_map.size();
		Iterator iterator = component_map.keySet().iterator();
		while (iterator.hasNext()) {
			String info_name = (String) iterator.next();
			Object value = component_map.get(info_name);
			if (value != null) {
				String jcomponent_type = value.getClass().toString();
				if (jcomponent_type.indexOf("JComboBox") > 0) {
					JComboBox combomx = (JComboBox) value;
					String str_com = combomx.getSelectedItem().toString();
					if (str_com == null || "".equals(str_com)) {
						vec_t.add(info_name);
					}
				} else if (jcomponent_type.indexOf("SACJTextField32") > 0) {
					SACJTextField32 jTextField32 = (SACJTextField32) value;
					String str_32 = jTextField32.getText().toString();
					if (str_32 == null || "".equals(str_32)) {
						vec_t.add(info_name);
					} else {
						if (str_32.equals(hzfs_str)) {
							vec_t.add(info_name);
						}
					}
				} else if (jcomponent_type.indexOf("SACJTextField1280") > 0) {
					SACJTextField1280 jTextField1280 = (SACJTextField1280) value;
					String str_1280 = jTextField1280.getText().toString();
					if (str_1280 == null || "".equals(str_1280)) {
						vec_t.add(info_name);
					} else {
						if (str_1280.equals(syfw_str)) {
							vec_t.add(info_name);
						}
					}
				} else if (jcomponent_type.indexOf("SACJTextField64") > 0) {
					SACJTextField64 jTextField64 = (SACJTextField64) value;
					String str_64 = jTextField64.getText().toString();
					if (str_64 == null || "".equals(str_64)) {
						vec_t.add(info_name);
					} else {
						if (str_64.equals(zjly_str)) {         
							vec_t.add(info_name);
						}
					}
				} else if (jcomponent_type.indexOf("SACJTextField128") > 0) {
					SACJTextField128 jTextField128 = (SACJTextField128) value;
					String str_128 = jTextField128.getText().toString();
					if (str_128 == null || "".equals(str_128)) {
						vec_t.add(info_name);
					}
				} else if (jcomponent_type.indexOf("JTextArea") > 0) {
					JTextArea jtextarea = (JTextArea) value;
					String str_area = jtextarea.getText().toString();
					if (str_area == null || "".equals(str_area)) {
						vec_t.add(info_name);
					} else {
						if (str_area.equals(yafs_str)
								|| str_area.equals(cgysfs_str)
								|| str_area.equals(tixing_str)
								|| str_area.equals(tel_str)
								|| str_area.equals(mbjssp_str)
								|| str_area.equals("采用标准：" + tixing_str)
								|| str_area.equals("采用标准：")) {
							vec_t.add(info_name);
						}
					}
				} else if (jcomponent_type.indexOf("iTextArea") > 0) {
					iTextArea itextarea = (iTextArea) value;
					String str_iarea = itextarea.getText().toString();
					if (str_iarea == null || "".equals(str_iarea)) {
						vec_t.add(info_name);
					}
				}
			}
		}
		// 判断tableJinDuAnPai中是不是有没有没填
		if (tableJinDuAnPai.getEditingRow() <= tableJinDuAnPai.getRowCount()
				&& tableJinDuAnPai.getCellEditor() != null) {
			tableJinDuAnPai.getCellEditor().stopCellEditing();
		}
		Object objTempqssj = null;
		Object objTempjzsj = null;
		String strTempqssj = null;
		String strTempjzsj = null;
		for (int i = 0; i < tableJinDuAnPai.getRowCount(); i++) {
			objTempqssj = tableJinDuAnPai.getValueAt(i, 2);
			strTempqssj = String.valueOf(objTempqssj);
			objTempjzsj = tableJinDuAnPai.getValueAt(i, 3);
			strTempjzsj = String.valueOf(objTempjzsj);
			if (objTempqssj == null || "".equals(strTempqssj)) {
				if (!vec_t.contains("起始时间")) {
					vec_t.add("起始时间");
				}
			}
			if (objTempjzsj == null || "".equals(strTempjzsj)) {
				if (!vec_t.contains("截至时间")) {
					vec_t.add("截至时间");
				}
			}
		}
		// 判断tableXiangMuZuRenYuan中是不是有没有没填
		System.out.println("tableXiangMuZuRenYuan--->:"
				+ tableXiangMuZuRenYuan.getRowCount());
		if (tableXiangMuZuRenYuan.getRowCount() <= 0) {
			if (!vec_t.contains("项目组人员")) {
				vec_t.add("项目组人员");
			}
		} else {
			if (tableXiangMuZuRenYuan.getEditingRow() <= tableXiangMuZuRenYuan
					.getRowCount()
					&& tableXiangMuZuRenYuan.getCellEditor() != null) {
				tableXiangMuZuRenYuan.getCellEditor().stopCellEditing();
			}
			String strTempxm = null;
			String strTempxb = null;
			String strTempzc = null;
			String strTempnl = null;
			String strTempzygz = null;
			String strTemptrys = null;
			String strTempbz = null;
			for (int i = 0; i < tableXiangMuZuRenYuan.getRowCount(); i++) {
				strTempxm = (String) tableXiangMuZuRenYuan.getValueAt(i, 0);
				strTempxb = (String) tableXiangMuZuRenYuan.getValueAt(i, 1);
				strTempzc = (String) tableXiangMuZuRenYuan.getValueAt(i, 2);
				strTempnl = (String) tableXiangMuZuRenYuan.getValueAt(i, 3);
				strTempzygz = (String) tableXiangMuZuRenYuan.getValueAt(i, 4);
				strTemptrys = (String) tableXiangMuZuRenYuan.getValueAt(i, 5);
				strTempbz = (String) tableXiangMuZuRenYuan.getValueAt(i, 6);      //备注
				if (strTempxm == null || "".equals(strTempxm)) {
					if (!vec_t.contains("项目组人员姓名")) {
						vec_t.add("项目组人员姓名");
					}
				}
				if (strTempxb == null || "".equals(strTempxb)) {
					if (!vec_t.contains("项目组人员性别")) {
						System.out.println("dfff");
						vec_t.add("项目组人员性别");
					}
				}
				if (strTempzc == null || "".equals(strTempzc)) {
					if (!vec_t.contains("项目组人员职称")) {
						System.out.println("dfff");
						vec_t.add("项目组人员职称");
					}
				}
				if (strTempnl == null || "".equals(strTempnl)) {
					if (!vec_t.contains("项目组人员年龄")) {
						System.out.println("dfff");
						vec_t.add("项目组人员年龄");
					}
				}
				if (strTempzygz == null || "".equals(strTempzygz)) {
					if (!vec_t.contains("承担本项目主要工作")) {
						System.out.println("dfff");
						vec_t.add("承担本项目主要工作");
					}
				}
				if (strTemptrys == null || "".equals(strTemptrys)) {
					if (!vec_t.contains("投入月数")) {
						System.out.println("dfff");
						vec_t.add("投入月数");
					}
				}
				/*if (strTempbz == null || "".equals(strTempbz)) {                    //进行了修改的地方
					if (!vec_t.contains("项目组人员备注")) {
						System.out.println("dfff");
						vec_t.add("项目组人员备注");
					}
				}*/
			}
		}
		// 判断tableJingJiLaiYuan中是不是有没有没填
		if (tableJingJiLaiYuan.getEditingRow() <= tableJingJiLaiYuan
				.getRowCount()
				&& tableJingJiLaiYuan.getCellEditor() != null) {
			tableJingJiLaiYuan.getCellEditor().stopCellEditing();
		}
		String strTempa = null;
		String strTempjflybz = null;
		int j=0;
		String strheji=null;
		strheji=(String) tableJingJiLaiYuan.getValueAt(4,1);
		System.out.println("strheji==>"+strheji);
		for (int i = 0; i < tableJingJiLaiYuan.getRowCount()-1; i++) {             //这里需要进行修改
			strTempa = (String) tableJingJiLaiYuan.getValueAt(i, 1);
			strTempjflybz = (String) tableJingJiLaiYuan.getValueAt(i, 4);
			if (strTempa == null || "".equals(strTempa)) {
				//if (!vec_t.contains("其中a年")) {
				//	vec_t.add("其中a年");
				//}
				j++;
				//System.out.println("此时的j==>"+j);
			} else if ("其中（）年*".equals(strTempa)) {
				if (!vec_t.contains("其中a年")) {
					vec_t.add("其中a年");
				}
			}
			
			/*if(j==0){
				if (!vec_t.contains("其中a年")) {
					vec_t.add("其中a年");
					}
			}*/
			/*if (strTempjflybz == null || "".equals(strTempjflybz)) {
				if (!vec_t.contains("经费来源备注")) {
					vec_t.add("经费来源备注");
				}
			}*/
		}
		System.out.println("此时的j==>"+j);
		if(j>2){
			if (!vec_t.contains("其中a年")) {
				vec_t.add("其中a年");
			}
		}
		
		if(strheji==null||"".equals(strheji)){
			System.out.println("可以执行到这里");
			if (!vec_t.contains("经费来源合计")) {
				vec_t.add("经费来源合计");
			}
		}
		/*
		 * //判断tableYuSuanZhiChu中是不是有没有没填
		 * if(tableYuSuanZhiChu.getEditingRow()<=tableYuSuanZhiChu
		 * .getRowCount()&&tableYuSuanZhiChu.getCellEditor()!=null){
		 * tableYuSuanZhiChu.getCellEditor().stopCellEditing(); } String
		 * strTempyyjd = null; String strTempfasj = null; String strTempyjcs =
		 * null; String strTempjcysyx = null; String strTempsjqr = null; String
		 * strTempjsgj = null; for (int i = 0; i <
		 * tableYuSuanZhiChu.getRowCount(); i++) { strTempyyjd=(String)
		 * tableYuSuanZhiChu.getValueAt(i, 1); strTempfasj = (String)
		 * tableYuSuanZhiChu.getValueAt(i, 2); strTempyjcs = (String)
		 * tableYuSuanZhiChu.getValueAt(i, 3); strTempjcysyx = (String)
		 * tableYuSuanZhiChu.getValueAt(i, 4); strTempsjqr = (String)
		 * tableYuSuanZhiChu.getValueAt(i, 5); strTempjsgj = (String)
		 * tableYuSuanZhiChu.getValueAt(i, 6); if(strTempyyjd == null ||
		 * "".equals(strTempyyjd)){ if(!vec_t.contains("预研阶段")){
		 * vec_t.add("预研阶段"); } } if(strTempfasj == null ||
		 * "".equals(strTempfasj)){ if(!vec_t.contains("方案设计")){
		 * System.out.println("dfff"); vec_t.add("方案设计"); } } if(strTempyjcs ==
		 * null || "".equals(strTempyjcs)){ if(!vec_t.contains("样机试制")){
		 * System.out.println("dfff"); vec_t.add("样机试制"); } } if(strTempjcysyx
		 * == null || "".equals(strTempjcysyx)){ if(!vec_t.contains("检测与试运行")){
		 * System.out.println("dfff"); vec_t.add("检测与试运行"); } } if(strTempsjqr
		 * == null || "".equals(strTempsjqr)){ if(!vec_t.contains("设计确认")){
		 * System.out.println("dfff"); vec_t.add("设计确认"); } } if(strTempjsgj ==
		 * null || "".equals(strTempjsgj)){ if(!vec_t.contains("计算根据及理由")){
		 * System.out.println("dfff"); vec_t.add("计算根据及理由"); } } }
		 */
		if (vec_t.size() > 0) {
			MessageBox.post(vec_t + "这" +
					"些属性不能为空,请检查", "Message",
					MessageBox.INFORMATION);
			return false;
		}
		return super.isFormSavable(arg0);
	}

	public void saveForm() {
		try {
			// 得到项目所选择的类型
			String proj_name = projCheckbox.getSelectedItem().toString();
			// 判断这个项目类型在首选项中有没有配置,如果没有配置则不需要检查
			String tem = map_all.get(proj_name);
			if (tem != null) {
				// 如果首选项中有这个项目类型,则检查配置的增加的文档在所在的文档结构中有没有存在
				String[] s1 = tem.split(";");
				for (int j = 0; j < s1.length; j++) {
					String[] s2 = s1[j].split(",");
					String[] s3 = s2[1].split("\\*");
					Vector<String> v = new Vector<String>();
					for (int i = 0; i < s3.length; i++) {
						v.add(s3[i]);
					}
					map_1.put(s2[0], v);
				}
				TCComponent[] coms = item.getRelatedComponents(relation);
				TCComponentFolder parentFolder = null;
				if (coms != null && coms.length > 0) {
					for (int i = 0; i < coms.length; i++) {
						if ("S4Prj_Folder".equals(coms[i].getType().toString())) {
							parentFolder = (TCComponentFolder) coms[i];
						}

					}
					if (parentFolder != null) {
						TCComponent[] com_folders = parentFolder
								.getRelatedComponents("contents");
						if (com_folders != null && com_folders.length > 0) {
							for (int j = 0; j < com_folders.length; j++) {
								if (com_folders[j] instanceof TCComponentFolder) {
									TCComponentFolder subFolder = (TCComponentFolder) com_folders[j];
									getAllFolders(subFolder);
								}
							}
							if (flag) {
								// System.out.println("v_folder---->:"+v_folder);
								// System.out.println("map_2====>:"+map_2);
								Vector<String> vec_item = new Vector<String>();
								// Vector<String> vec_item_name = new
								// Vector<String>();
								Vector<String> vec_item_type = new Vector<String>();
								for (int i = 0; i < v_folder.size(); i++) {
									TCComponentFolder folder = v_folder.get(i);
									System.out.println("folder====>:"
											+ folder.toString());
									vec_item = map_1.get(folder.toString());
									System.out.println("vec_item===>:"
											+ vec_item);
									if (vec_item != null) {
										// vec_item_name =
										// map_2.get(folder.toString());
										vec_item_type = map_3.get(folder
												.toString());
										for (int j = 0; j < vec_item.size(); j++) {
											String str = vec_item.get(j);
											String[] s = str.split("=");
											if (!vec_item_type.contains(s[1])) {
												createItem(s[1], s[0], folder);
												if (!v_info.contains(s[0])) {
													v_info.add(s[0]);
												}
											}
										}
									}
								}
								if (v_info.size() > 0) {
									MessageBox.post(
											"项目类型更改补充了如下项目文档:" + v_info, "",
											MessageBox.INFORMATION);
								}
							}
						}
					}
				}
			}

			for (int j = 0; j < formProperties.length; j++) {                                   //进行了数据的保存
				String string = formProperties[j].getPropertyName();
				if (string.equals("s4scope")) {
					formProperties[j].setStringValueData(syfwiTextField
							.getText());
				} else if (string.equals("s4timed")) {
					formProperties[j].setStringValueData(qznyidatebutton
							.getDateString());
				} else if (string.equals("s4timedup")) {
					formProperties[j].setStringValueData(jznyidatebutton
							.getDateString());
				} else if (string.equals("s4tandd")) {                          //修改的地方
					formProperties[j].setStringValueData(cddwybmiTextField
							.getSelectedItem().toString());
				} else if (string.equals("s4tandd1")) {                          //修改的地方
					formProperties[j].setStringValueData(cbdwTextField
							.getText());
				} else if(string.equals("s4prj_id")){                                 //研制令号
					formProperties[j].setStringValueData(textPrjId.getText());
					System.out.println("textPrjId.getText()"+textPrjId.getText());
				}								
				else if (string.equals("s4Grind_testw")) {
					formProperties[j].setStringValueData(ysfsiTextField
							.getText());
				} else if (string.equals("s4Cooperator")) {
					formProperties[j].setStringValueData(hzdwiTextField
							.getText());
				} else if (string.equals("s4Cooperation")) {
					formProperties[j].setStringValueData(hzfsiTextField
							.getText());
				} else if (string.equals("s4prj_budget")) {
					formProperties[j].setStringValueData(xmzysiTextField
							.getText());
				} else if (string.equals("s4cap_source")) {
					formProperties[j].setStringValueData(zjlyiTextField
							.getText());
				} else if (string.equals("s4goal_resu")) {
					formProperties[j].setStringValueData(mbjsspiTextField
							.getText());
				} else if (string.equals("s4patent")) {
					formProperties[j]
							.setStringValueData(zliTextField.getText());
				} else if (string.equals("s4copyright")) {
					formProperties[j].setStringValueData(zzqiTextField
							.getText());
				} else if (string.equals("s4thesis")) {
					formProperties[j]
							.setStringValueData(lwiTextField.getText());
				} else if (string.equals("s4Results_acceway")) {
					formProperties[j].setStringValueData(cgysfsiTextField
							.getText());
				} else if (string.equals("s4name2")) {
					formProperties[j].setStringValueData(xmiTextField1
							.getText());
				} else if (string.equals("s4title2")) {
					formProperties[j].setStringValue((String) zc_Box1
							.getSelectedItem().toString());
				} else if (string.equals("s4profession")) {
					formProperties[j].setStringValueData(zyiTextField1
							.getText());
				} else if (string.equals("s4department")) {
					formProperties[j].setStringValue((String) yanzdw_Box1
							.getSelectedItem().toString());
				} else if (string.equals("s4prj_class")) {
					formProperties[j].setStringValue(yanziTextField1.getText());
				} else if (string.equals("s4contact_way")) {
					formProperties[j].setStringValueData(lxfsiTextField1
							.getText());
				} else if (string.equals("s4Email")) {
					formProperties[j].setStringValueData(emailiTextField1
							.getText());
				} else if (string.equals("s4name1")) {
					formProperties[j].setStringValueData(xmiTextField2
							.getText());
				} else if (string.equals("s4title1")) {
					formProperties[j].setStringValue((String) zc_Box2
							.getSelectedItem().toString());
				} else if (string.equals("s4profession1")) {
					formProperties[j].setStringValueData(zyiTextField2
							.getText());
				} else if (string.equals("s4department1")) {
					formProperties[j].setStringValue((String) yanzdw_Box2
							.getSelectedItem().toString());
				} else if (string.equals("s4prj_class2")) {
					formProperties[j].setStringValue(yanziTextField2.getText());
				} else if (string.equals("s4contact_way1")) {
					formProperties[j].setStringValueData(lxfsiTextField2
							.getText());
				} else if (string.equals("s4Email1")) {
					formProperties[j].setStringValueData(emailiTextField2
							.getText());
				} else if (string.equals("s4name3")) {
					formProperties[j].setStringValueData(xmiTextField3
							.getText());
				} else if (string.equals("s4title3")) {
					formProperties[j].setStringValue((String) zc_Box3
							.getSelectedItem().toString());
				} else if (string.equals("s4profession3")) {
					formProperties[j].setStringValueData(zyiTextField3
							.getText());
				} else if (string.equals("s4department3")) {
					formProperties[j].setStringValue((String) yanzdw_Box3
							.getSelectedItem().toString());
				} else if (string.equals("s4prj_class3")) {
					formProperties[j].setStringValue(yanziTextField3.getText());
				} else if (string.equals("s4contact_way3")) {
					formProperties[j].setStringValueData(lxfsiTextField3
							.getText());
				} else if (string.equals("s4Email3")) {
					formProperties[j].setStringValueData(emailiTextField3
							.getText());
				} else if (string.equals("s4seiban")) {                
					if(textseiban.getText().equals(str_seiban))
					{
					}
					else
					{
					     formProperties[j].setStringValueData(textseiban.getText());
					}
				} else if (string.equals("s4Projtype")) {
					formProperties[j].setStringValueData(projCheckbox
							.getSelectedItem().toString());
				} else if (string.equals("s4teamevent")) {
					formProperties[j].setStringValueData(teamCheckbox
							.getSelectedItem().toString());
				}
			}
			// prjName.setStringValueData(textPrjName.getText());
		  // prjId.setStringValueData(textPrjId.getText());                 //修改了的地方    研制令号
			// prjClass.setStringValueData(textPrjClass.getText());
			// prjMan.setStringValueData(textPrjMan.getText());
			prjContent.setStringValueData(textPrjContent.getText());
			adoStand.setStringValueData(textAdoStand.getText());

			if (tableJinDuAnPai.getCellEditor() != null) {
				tableJinDuAnPai.getCellEditor().stopCellEditing();
			}
			strStartT = new String[tableJinDuAnPai.getRowCount()];
			for (int i = 0; i < tableJinDuAnPai.getRowCount(); i++) {
				if ((String) tableJinDuAnPai.getValueAt(i, 2) == null) {
					strStartT[i] = "";
				} else {
					strStartT[i] = (String) tableJinDuAnPai.getValueAt(i, 2);
				}

			}
			strEndT = new String[tableJinDuAnPai.getRowCount()];
			for (int i = 0; i < tableJinDuAnPai.getRowCount(); i++) {
				if ((String) tableJinDuAnPai.getValueAt(i, 3) == null) {
					strEndT[i] = "";
				} else {
					strEndT[i] = (String) tableJinDuAnPai.getValueAt(i, 3);
				}

			}
			startT.setStringValueArray(strStartT);
			endT.setStringValueArray(strEndT);

			if (tableXiangMuZuRenYuan.getCellEditor() != null) {
				tableXiangMuZuRenYuan.getCellEditor().stopCellEditing();
			}
			strName = new String[tableXiangMuZuRenYuan.getRowCount()];
			strSex = new String[tableXiangMuZuRenYuan.getRowCount()];
			strTitle = new String[tableXiangMuZuRenYuan.getRowCount()];
			strAge = new String[tableXiangMuZuRenYuan.getRowCount()];
			strMainWork = new String[tableXiangMuZuRenYuan.getRowCount()];
			strInMonth = new String[tableXiangMuZuRenYuan.getRowCount()];
			strRemark = new String[tableXiangMuZuRenYuan.getRowCount()];
			for (int i = 0; i < tableXiangMuZuRenYuan.getRowCount(); i++) {
				if ((String) tableXiangMuZuRenYuan.getValueAt(i, 0) == null) {
					strName[i] = "";
				} else {
					strName[i] = (String) tableXiangMuZuRenYuan
							.getValueAt(i, 0);
				}
				if ((String) tableXiangMuZuRenYuan.getValueAt(i, 1) == null) {
					strSex[i] = "";
				} else {
					strSex[i] = (String) tableXiangMuZuRenYuan.getValueAt(i, 1);
				}
				if ((String) tableXiangMuZuRenYuan.getValueAt(i, 2) == null) {
					strTitle[i] = "";
				} else {
					strTitle[i] = (String) tableXiangMuZuRenYuan.getValueAt(i,
							2);
				}
				if ((String) tableXiangMuZuRenYuan.getValueAt(i, 3) == null) {
					strAge[i] = "";
				} else {
					strAge[i] = (String) tableXiangMuZuRenYuan.getValueAt(i, 3);
				}
				if ((String) tableXiangMuZuRenYuan.getValueAt(i, 4) == null) {
					strMainWork[i] = "";
				} else {
					strMainWork[i] = (String) tableXiangMuZuRenYuan.getValueAt(
							i, 4);
				}
				if ((String) tableXiangMuZuRenYuan.getValueAt(i, 5) == null) {
					strInMonth[i] = "";
				} else {
					strInMonth[i] = (String) tableXiangMuZuRenYuan.getValueAt(
							i, 5);
				}
				if ((String) tableXiangMuZuRenYuan.getValueAt(i, 6) == null) {
					strRemark[i] = "";
				} else {
					strRemark[i] = (String) tableXiangMuZuRenYuan.getValueAt(i,
							6);
				}
			}
			name.setStringValueArray(strName);
			sex.setStringValueArray(strSex);
			title.setStringValueArray(strTitle);
			age.setStringValueArray(strAge);
			mainWork.setStringValueArray(strMainWork);
			inMonth.setStringValueArray(strInMonth);
			remark.setStringValueArray(strRemark);

			if (tableJingJiLaiYuan.getCellEditor() != null) {
				tableJingJiLaiYuan.getCellEditor().stopCellEditing();
			}
			StrInta = new String[tableJingJiLaiYuan.getRowCount()];
			StrIntb = new String[tableJingJiLaiYuan.getRowCount()];
			StrIntc = new String[tableJingJiLaiYuan.getRowCount()];
			strRemark1 = new String[tableJingJiLaiYuan.getRowCount()];
			for (int i = 0; i < tableJingJiLaiYuan.getRowCount(); i++) {
				if ((String) tableJingJiLaiYuan.getValueAt(i, 1) == null) {
					if (i == 0) {
						StrInta[i] = "其中（）年";
					} else {
						StrInta[i] = "";
					}
				} else {
					StrInta[i] = (String) tableJingJiLaiYuan.getValueAt(i, 1);
				}
				if ((String) tableJingJiLaiYuan.getValueAt(i, 2) == null) {
					if (i == 0) {
						StrIntb[i] = "其中（）年";
					} else {
						StrIntb[i] = "";
					}
				} else {
					StrIntb[i] = (String) tableJingJiLaiYuan.getValueAt(i, 2);
				}
				if ((String) tableJingJiLaiYuan.getValueAt(i, 3) == null) {
					if (i == 0) {
						StrIntc[0] = "其中（）年";
					} else {
						StrIntc[i] = "";
					}
				} else {
					StrIntc[i] = (String) tableJingJiLaiYuan.getValueAt(i, 3);
				}
				if ((String) tableJingJiLaiYuan.getValueAt(i, 4) == null) {
					strRemark1[i] = "";
				} else {
					strRemark1[i] = (String) tableJingJiLaiYuan
							.getValueAt(i, 4);
				}
			}
			ina.setStringValueArray(StrInta);
			inb.setStringValueArray(StrIntb);
			inc.setStringValueArray(StrIntc);
			remark1.setStringValueArray(strRemark1);

			if (tableYuSuanZhiChu.getCellEditor() != null) {
				tableYuSuanZhiChu.getCellEditor().stopCellEditing();
			}
			strPreStage = new String[tableYuSuanZhiChu.getRowCount()];
			strPrjDesign = new String[tableYuSuanZhiChu.getRowCount()];
			strProTest = new String[tableYuSuanZhiChu.getRowCount()];
			strStandC = new String[tableYuSuanZhiChu.getRowCount()];
			strDesignCom = new String[tableYuSuanZhiChu.getRowCount()];
			strCbareson = new String[tableYuSuanZhiChu.getRowCount()];
			for (int i = 0; i < tableYuSuanZhiChu.getRowCount(); i++) {
				if ((String) tableYuSuanZhiChu.getValueAt(i, 1) == null) {
					strPreStage[i] = "";
				} else {
					strPreStage[i] = (String) tableYuSuanZhiChu
							.getValueAt(i, 1);
				}
				if ((String) tableYuSuanZhiChu.getValueAt(i, 2) == null) {
					strPrjDesign[i] = "";
				} else {
					strPrjDesign[i] = (String) tableYuSuanZhiChu.getValueAt(i,
							2);
				}
				if ((String) tableYuSuanZhiChu.getValueAt(i, 3) == null) {
					strProTest[i] = "";
				} else {
					strProTest[i] = (String) tableYuSuanZhiChu.getValueAt(i, 3);
				}
				if ((String) tableYuSuanZhiChu.getValueAt(i, 4) == null) {
					strStandC[i] = "";
				} else {
					strStandC[i] = (String) tableYuSuanZhiChu.getValueAt(i, 4);
				}
				if ((String) tableYuSuanZhiChu.getValueAt(i, 5) == null) {
					strDesignCom[i] = "";
				} else {
					strDesignCom[i] = (String) tableYuSuanZhiChu.getValueAt(i,
							5);
				}
				if ((String) tableYuSuanZhiChu.getValueAt(i, 6) == null) {
					strCbareson[i] = "";
				} else {
					strCbareson[i] = (String) tableYuSuanZhiChu
							.getValueAt(i, 6);
				}
			}

			/*
			 * if(tableXiangMuYueBao.getCellEditor()!=null){
			 * tableXiangMuYueBao.getCellEditor().stopCellEditing(); }
			 * strXiangmyb = new String[tableXiangMuYueBao.getRowCount()]; for
			 * (int i = 0; i < tableXiangMuYueBao.getRowCount(); i++) {
			 * if((String)tableXiangMuYueBao.getValueAt(i, 0) == null){
			 * strXiangmyb[i] = ""; } else{ strXiangmyb[i] =
			 * (String)tableXiangMuYueBao.getValueAt(i, 0); } }
			 */

			preStage.setStringValueArray(strPreStage);
			prjDesign.setStringValueArray(strPrjDesign);
			proTest.setStringValueArray(strProTest);
			standC.setStringValueArray(strStandC);
			designCom.setStringValueArray(strDesignCom);
			cbareson.setStringValueArray(strCbareson);
			// xiangmuyueb.setStringValueArray(strXiangmyb);

			prjCost.setStringValueData(textPrjCost.getText());
			
			prjName.setStringValueData(item.getProperties(properties_name)[0]);
			
			strfujian=new String[fujiancount];
			for(int i=0;i<fujiancount;i++)
			{  
				strfujian[i]=(String)filelabel[i].getText();
			}
			fujian.setStringValueArray(strfujian);
			System.out.println("**** save 前表单附件个数*****"+fujiancount);
			

			// proposer.setStringValueData(textProposer.getText());
			// tddHead.setStringValueData(textTddhead.getText());
			// tsection.setStringValueData(textTsection.getText());

			TCProperty[] tcProperty = new TCProperty[24];
			// tcProperty[0] = prjName;
			// tcProperty[1] = prjId;
			// tcProperty[2] = prjClass;
			// tcProperty[3] = prjMan;
			tcProperty[0] = prjContent;
			tcProperty[1] = adoStand;
			tcProperty[2] = startT;
			tcProperty[3] = endT;
			tcProperty[4] = name;
			tcProperty[5] = sex;
			tcProperty[6] = title;
			tcProperty[7] = age;
			tcProperty[8] = mainWork;
			tcProperty[9] = inMonth;
			tcProperty[10] = remark;
			tcProperty[11] = prjCost;
			tcProperty[12] = ina;
			tcProperty[13] = inb;
			tcProperty[14] = inc;
			tcProperty[15] = remark1;
			tcProperty[16] = preStage;
			tcProperty[17] = prjDesign;
			tcProperty[18] = proTest;
			tcProperty[19] = standC;
			tcProperty[20] = designCom;
			tcProperty[21] = cbareson;
			
			tcProperty[22] = fujian;
			tcProperty[23] = prjName;
		//	tcProperty[22] = prjId;
			// tcProperty[25] = proposer;
			// tcProperty[26] = tddHead;
			// tcProperty[27] = tsection;
			// tcProperty[28] = xiangmuyueb;
			form.setTCProperties(formProperties);
			form.setTCProperties(tcProperty);
			
			
			for(int i=0;i<fujiancount;i++){
				
				AbstractAIFApplication app= AIFUtility.getCurrentApplication();
				TCSession session=(TCSession)app.getSession();
				TCComponentDatasetType tccomponentDatasetType=(TCComponentDatasetType) session.getTypeComponent("Dataset");
				TCComponentDataset predataset=tccomponentDatasetType.find(filelabel[i].getText());
				if(predataset==null)
				try{

				//	AbstractAIFApplication app= AIFUtility.getCurrentApplication();
				//  TCComponentItemRevision mItemRevision=(TCComponentItemRevision)app.getTargetContext().getParentComponent();
				//  TCSession session=(TCSession)app.getSession();
				//	TCComponentDatasetType tccomponentDatasetType=null;
					if(filelabel[i].getText().endsWith(".doc"))
			        tccomponentDatasetType=(TCComponentDatasetType) session.getTypeComponent("MSWord");
					else	if(filelabel[i].getText().endsWith(".docx"))
					tccomponentDatasetType=(TCComponentDatasetType) session.getTypeComponent("MSWordX");	
					
					else	if(filelabel[i].getText().endsWith(".xls"))
				        tccomponentDatasetType=(TCComponentDatasetType) session.getTypeComponent("MSExcel");
					else	if(filelabel[i].getText().endsWith(".xlsx"))
				        tccomponentDatasetType=(TCComponentDatasetType) session.getTypeComponent("MSExcelX");
					
					else	if(filelabel[i].getPath().toString().endsWith(".txt"))
				        tccomponentDatasetType=(TCComponentDatasetType) session.getTypeComponent("Text");
					else	
				        tccomponentDatasetType=(TCComponentDatasetType) session.getTypeComponent("Text");
				//	NewDatasetCommand datasetCmd=new NewDatasetCommand(session,s4CSQXBGRevisionUI.filelabel[i].getText(),"",s4CSQXBGRevisionUI.filelabel[i].getText(),"",null,tccomponentDatasetType.toString(),null);
				//	NewDatasetOperation datasetOp=new NewDatasetOperation(datasetCmd);
				//	datasetOp.executeOperation();
				//	TCComponentDataset dataset =(TCComponentDataset) datasetOp.getNewDataset();	
					
			   TCComponentDataset dataset =tccomponentDatasetType.create(filelabel[i].getText(), tccomponentDatasetType.toString(),  tccomponentDatasetType.toString());
	           ImportFilesOperation fileOp=null;
	            if(filelabel[i].getText().endsWith(".doc"))
	        	    fileOp=new ImportFilesOperation(dataset,filelabel[i].getPath(),"MSWord","BINARY","word",null); 
	            else   if(filelabel[i].getText().endsWith(".docx"))
	        	    fileOp=new ImportFilesOperation(dataset,filelabel[i].getPath(),"MSWordX","BINARY","word",null);  
	            
	            else	if(filelabel[i].getText().endsWith(".xls"))
					fileOp=new ImportFilesOperation(dataset,filelabel[i].getPath(),"MSExcel","BINARY","excel",null); 
	            else	if(filelabel[i].getText().endsWith(".xlsx"))
					fileOp=new ImportFilesOperation(dataset,filelabel[i].getPath(),"MSExcelX","BINARY","excel",null);  
	            else	if(filelabel[i].getText().endsWith(".txt"))
					fileOp=new ImportFilesOperation(dataset,filelabel[i].getPath(),"Text","BINARY","Text",null);  
	            else	
		        	    fileOp=new ImportFilesOperation(dataset,filelabel[i].getPath(),"Text","BINARY","Text",null); 

			fileOp.executeOperation();
	     
	    //    mItemRevision.add("IMAN_specification", dataset);
				}catch(Exception e){
					e.printStackTrace();
				}

			} 
				
		}catch(Exception e){
		e.printStackTrace();	
		}	
}

	/*
	 * 递归获取所有子Folder
	 */
	public void getAllFolders(TCComponentFolder folder) {
		try {
			String folder_name = folder.toString();
			Vector<String> vec_item = new Vector<String>();
			vec_item = map_1.get(folder_name);
			TCComponent[] coms = folder.getRelatedComponents("contents");
			Vector<String> vec_item_name = new Vector<String>();
			Vector<String> vec_item_type = new Vector<String>();
			if (coms != null && coms.length > 0) {
				for (int j = 0; j < coms.length; j++) {
					if (coms[j] instanceof TCComponentItem) {
						TCComponentItem item = (TCComponentItem) coms[j];
						String item_name = item.getProperty("object_name")
								.toString();
						String item_type = item.getType().toString();
						// 如果在首选项中有配置新增文档,则先判断有没有在文档结构中存在
						if (vec_item != null) {
							for (int i = 0; i < vec_item.size(); i++) {
								String str = vec_item.get(i);
								String[] s = str.split("=");
								if (!/* item_name.equals(s[0]) && */item_type
										.equals(s[1])) {
									flag = true;
									if (!v_folder.contains(folder)) {
										v_folder.add(folder);
									}
								}
							}
						}
						vec_item_name.add(item_name);
						vec_item_type.add(item_type);
					} else if (coms[j] instanceof TCComponentFolder) {
						TCComponentFolder subFolder = (TCComponentFolder) coms[j];
						getAllFolders(subFolder);
					}
				}
			} else {
				if (!v_folder.contains(folder)) {
					v_folder.add(folder);
				}
			}
			map_2.put(folder_name, vec_item_name);
			map_3.put(folder_name, vec_item_type);
		} catch (TCException e) {
			e.printStackTrace();
		}
	}

	// 根据类型和名称创建零组件对象
	private void createItem(String type, String name, TCComponentFolder parent) {
		try {
			TCComponentItemType itemType = (TCComponentItemType) session
					.getTypeComponent(type);
			String itemId = itemType.getNewID(); // 获取编码
			String itemRev = itemType.getNewRev(null); // 获取版本编码
			TCComponentItem item = itemType.create(itemId, itemRev, type, name,
					"", null);
			if (item != null)
				parent.add("contents", item);
		} catch (TCException e) {
			e.printStackTrace();
		}
	}

	public void initUI() {
		setLayout(new GridLayout(1, 1));
		jTabbedPane = new JTabbedPane(JTabbedPane.NORTH);
		jTabbedPane.setBounds(0, 0, 800, 800);

		jTabbedPane.addTab("项目基本属性", firstPanel());
		jTabbedPane.addTab("项目费用", SecondPanel());
		add(jTabbedPane);

	}

	public JPanel firstPanel() {
		// JPanel panel_all = new JPanel(new PropertyLayout());
		JPanel panel_all = new JPanel();
		panel_all.setLayout(new BoxLayout(panel_all, BoxLayout.Y_AXIS));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel panelOne = new JPanel();
		panelOne.setLayout(new BoxLayout(panelOne, BoxLayout.X_AXIS));
		// JPanel panelOne = new JPanel(new GridLayout(4, 5));
		// TitledBorder titleBorder = BorderFactory.createTitledBorder("");
		// titleBorder.setTitlePosition(2);
		// panelOne.setBorder(titleBorder);
		JPanel panelOne1 = new JPanel(new PropertyLayout());
		JPanel panelOne2 = new JPanel(new PropertyLayout());
		JPanel panelOne3 = new JPanel(new PropertyLayout());

		JLabel lbPrjName = new JLabel("  项目名称");
		textPrjName = new SACJTextField128(70);
		textPrjName.setEnabled(false);
		JLabel lbPrjId = new JLabel(" 研制令号");
		textPrjId = new SACJTextField32(74);
		textPrjId.setEnabled(true);                         //进行修改的地方
		/*
		 * JLabel lbPrjClass = new JLabel("  研制部门"); textPrjClass = new
		 * SACJTextField128(70); JLabel lbPrjMan = new JLabel(" 项目负责人");
		 * textPrjMan = new SACJTextField32(74);
		 */
		JLabel lbXmlx = new JLabel("  项目类型*");
		projCheckbox = new JComboBox(proj_str);
		projCheckbox.setPreferredSize(new Dimension(410, 23));
		JLabel lbJtxm = new JLabel(" 集团项目*");
		teamCheckbox = new JComboBox(team_str);
		component_map.put("集团项目", teamCheckbox);
		teamCheckbox.setPreferredSize(new Dimension(62, 23));
		JLabel lbseiban = new JLabel(" seiban号");
		textseiban = new SACJTextField32(53);
		textseiban.setText(str_seiban);
		textseiban.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (str_seiban.equals(textseiban.getText().toString())) {
					textseiban.setText("");
				}

			}
		});
		
		
		
		
		
		
		JLabel lbKong = new JLabel("  ");

		panelOne1.add("1.1.rihgt", lbPrjName);
		panelOne1.add("1.2.rihgt", textPrjName);
		// panelOne1.add("2.1.rihgt",lbPrjClass);
		// panelOne1.add("2.2.rihgt",textPrjClass);
		panelOne1.add("2.1.rihgt", lbXmlx);
		panelOne1.add("2.2.rihgt", projCheckbox);

		panelOne2.add("1.1.rihgt", lbKong);

		panelOne3.add("1.1.rihgt", lbPrjId);
		panelOne3.add("1.2.rihgt", textPrjId);
		// panelOne3.add("2.1.rihgt",lbPrjMan);
		// panelOne3.add("2.2.rihgt",textPrjMan);
		panelOne3.add("2.1.rihgt", lbJtxm);
		panelOne3.add("2.2.rihgt", teamCheckbox);
		panelOne3.add("2.3.rihgt", lbseiban);
		panelOne3.add("2.4.rihgt", textseiban);

		panelOne.add("1.1.rihgt", panelOne1);
		panelOne.add("1.2.rihgt", panelOne2);
		panelOne.add("1.3.rihgt", panelOne3);

		font = new Font("宋体", Font.BOLD, 12);
		JPanel panelFive = new JPanel(new GridLayout(1, 1));
		TitledBorder titleBorderFive = BorderFactory.createTitledBorder("");
		titleBorderFive.setTitlePosition(2);
		panelFive.setBorder(titleBorderFive);
		// 项目简况面板
		JPanel xmjk_Panel = bulidxmjk_Panel();
		// xmjk_Panel.setPreferredSize(new Dimension(1200,350));
		// 构造项目简况标签
		TitledBorder xmjkBorder = BorderFactory.createTitledBorder("项目简况");
		xmjkBorder.setTitlePosition(TitledBorder.TOP);
		xmjkBorder.setTitleFont(font);
		xmjk_Panel.setBorder(xmjkBorder);
		panelFive.add(xmjk_Panel);

		JPanel panelSix = new JPanel(new GridLayout(1, 1));
		TitledBorder titleBorderSix = BorderFactory.createTitledBorder("");
		titleBorderSix.setTitlePosition(2);
		panelSix.setBorder(titleBorderSix);
		// 项目负责人面板
		JPanel xmfzr_Panel = bulidxmfzr_Panel();
		// 构造项目负责人标签
		TitledBorder xmfzrBorder = BorderFactory.createTitledBorder("项目负责人");
		xmfzrBorder.setTitlePosition(TitledBorder.TOP);
		xmfzrBorder.setTitleFont(font);
		xmfzr_Panel.setBorder(xmfzrBorder);
		panelSix.add(xmfzr_Panel);

		JPanel panelTwo = new JPanel(new GridLayout(2, 1, 2, 2));
		TitledBorder titleBorderPrjContent = BorderFactory
				.createTitledBorder("项目内容*");
		titleBorderPrjContent.setTitleFont(font);
		titleBorderPrjContent.setTitlePosition(2);
		panelTwo.setBorder(titleBorderPrjContent);
		textPrjContent = new JTextArea(5, 20);
		component_map.put("项目内容", textPrjContent);
		textPrjContent.setLineWrap(true);
		textPrjContent.setText(tixing_str);
		textPrjContent.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (tixing_str.equals(textPrjContent.getText().toString())) {
					textPrjContent.setText("");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

		});
		// 设置项目内容输入最大值
		SACDocument4000 prjContent_doc = new SACDocument4000();
		textPrjContent.setDocument(prjContent_doc);
		JScrollPane textPrjContent_scrollPane = new JScrollPane(textPrjContent);
		textAdoStand = new JTextArea(5, 10);
		component_map.put("采用标准", textAdoStand);
		textAdoStand.setText("采用标准：" + tixing_str);
		textAdoStand.setLineWrap(true);
		textAdoStand.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (("采用标准：" + tixing_str).equals(textAdoStand.getText()
						.toString())) {
					textAdoStand.setText("采用标准：");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

		});
		// 设置采用标准输入最大值
		SACDocument4000 adoStand_doc = new SACDocument4000();
		textAdoStand.setDocument(adoStand_doc);
		JScrollPane textAdoStand_scrollPane = new JScrollPane(textAdoStand);
		panelTwo.add(textPrjContent_scrollPane);
		panelTwo.add(textAdoStand_scrollPane);

		JPanel panelThree = new JPanel(new GridLayout(1, 0));
		TitledBorder titleBorderJinDuAnPai = BorderFactory
				.createTitledBorder("进度安排*");
		titleBorderJinDuAnPai.setTitleFont(font);
		titleBorderJinDuAnPai.setTitlePosition(2);
		panelThree.setBorder(titleBorderJinDuAnPai);
		Object[] columnNamesJinDuAnPai = { "阶段*", "阶段名称*", "起始时间*", "截止时间*" };
		Object[][] dataJinDuAnPai = { { "A", "立项阶段", "", "" },
				{ "B", "设计阶段", "", "" }, { "C", "样机试制阶段", "", "" },
				{ "D", "测试及运行阶段", "", "" }, { "E", "项目验收", "", "" } };

		DefaultTableModel tableModel = new DefaultTableModel(dataJinDuAnPai,
				columnNamesJinDuAnPai) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				if (column < 2) {
					return false;
				} else {
					return true;
				}
			}
		};
		tableJinDuAnPai = new JTable(tableModel);
		tableJinDuAnPai.getTableHeader().setReorderingAllowed(false);
		tableJinDuAnPai.setRowHeight(20);
		// tableJinDuAnPai.setValueAt("2012-08-10", 0, 2);
		tableJinDuAnPai.setPreferredScrollableViewportSize(new Dimension(500,
				105));
		// tableJinDuAnPai.getColumnModel().getColumn(2).setCellEditor(new
		// SACTextFieldEditorSJ(tableJinDuAnPai));
		// tableJinDuAnPai.getColumnModel().getColumn(3).setCellEditor(new
		// SACTextFieldEditorSJ(tableJinDuAnPai));
		for (int i = 0; i < 4; i++) {
			if (i == 2 || i == 3) {
				tableJinDuAnPai.getColumnModel().getColumn(i).setCellEditor(
						new SACTextFieldEditorSJ(tableJinDuAnPai));
				// tableJinDuAnPai.getColumnModel().getColumn(i).setCellRenderer(new
				// SAC_TableCellRenderer());
			}
		}

		JScrollPane scrollPaneJinDuAnPai = new JScrollPane(tableJinDuAnPai);
		panelThree.add(scrollPaneJinDuAnPai);

		// JPanel panelFour = new JPanel(new GridLayout(2, 1));
		JPanel panelFour = new JPanel();
		panelFour.setLayout(new BoxLayout(panelFour, BoxLayout.Y_AXIS));
		TitledBorder titleBorderXiangMuZuRenYuan = BorderFactory
				.createTitledBorder("项目组人员");
		titleBorderXiangMuZuRenYuan.setTitleFont(font);
		titleBorderXiangMuZuRenYuan.setTitlePosition(2);
		panelFour.setBorder(titleBorderXiangMuZuRenYuan);
		Object[] columnNamesXiangMuZuRenYuan = { "姓名*", "性别*", "职称*", "年龄*",
				"承担本项目主要工作*", "投入月数*", "备注" };
		Object[][] dataXiangMuZuRenYuan = new Object[num][7];
		tableModelTwo = new DefaultTableModel(dataXiangMuZuRenYuan,
				columnNamesXiangMuZuRenYuan);
		tableXiangMuZuRenYuan = new JTable(tableModelTwo);
		tableXiangMuZuRenYuan.getTableHeader().setReorderingAllowed(false);
		tableXiangMuZuRenYuan.getColumnModel().getColumn(4).setPreferredWidth(
				180);
		tableXiangMuZuRenYuan.getColumnModel().getColumn(6).setPreferredWidth(
				250);
		// tableXiangMuZuRenYuan.setRowHeight(50);
		tableXiangMuZuRenYuan.setPreferredScrollableViewportSize(new Dimension(
				500, 180));
		for (int i = 0; i < 7; i++) {
			if (i == 4) {
				tableXiangMuZuRenYuan
						.getColumnModel()
						.getColumn(i)
						.setCellEditor(
								new SACTextFieldEditor64(tableXiangMuZuRenYuan));
			} else if (i == 6) {
				tableXiangMuZuRenYuan
						.getColumnModel()
						.getColumn(i)
						.setCellEditor(
								new SACTextAreaEditor1280(tableXiangMuZuRenYuan));
				tableXiangMuZuRenYuan.getColumnModel().getColumn(i)
						.setCellRenderer(new SACGridBagRenderer());
			} else if (i == 2) {
				tableXiangMuZuRenYuan.getColumnModel().getColumn(i)
						.setCellEditor(
								new SACTextFieldEditorLov(
										tableXiangMuZuRenYuan, zc_str));
			} else {
				tableXiangMuZuRenYuan
						.getColumnModel()
						.getColumn(i)
						.setCellEditor(
								new SACTextFieldEditor32(tableXiangMuZuRenYuan));
			}
		}
		JScrollPane scrollPaneXiangMuZuRenYuan = new JScrollPane(
				tableXiangMuZuRenYuan);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		JButton addButton = new JButton("添加行");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] newRow = { "", "", "", "", "", "", "" };
				tableModelTwo.addRow(newRow);
				// tableXiangMuZuRenYuan = new JTable(tableModelTwo);
			}
		});
		JButton deleteButton = new JButton("删除行");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectedRow = tableXiangMuZuRenYuan.getSelectedRows();// 获得选中行的索引
				for (int i = 0; i < selectedRow.length; i++) {
					if (selectedRow[i] != -1) // 存在选中行
					{
						tableModelTwo.removeRow(tableXiangMuZuRenYuan
								.getSelectedRow()); // 删除行
						// tableXiangMuZuRenYuan = new JTable(tableModelTwo);
					}
				}
			}
		});
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);

		
		TitledBorder titleBorderPingShenJieLunFuJian = BorderFactory.createTitledBorder("评审结论附件");
		titleBorderPingShenJieLunFuJian.setTitleFont(font);
		titleBorderPingShenJieLunFuJian.setTitlePosition(2);
		panel3.setBorder(titleBorderPingShenJieLunFuJian);
		JPanel panel3_explore=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton explore_button=new JButton("浏览");
			explore_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fDialog=new JFileChooser();
				fDialog.setDialogTitle("请选择所添加的附件");
				fDialog.setApproveButtonText("打开");
				fDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(JFileChooser.APPROVE_OPTION==fDialog.showOpenDialog(new Frame())){
					for(int i=0;i<fujiancount;i++){
						System.out.println(filelabel[i].getText());
						if(filelabel[i].getText().equals(fDialog.getSelectedFile().getName()))
						{
							JOptionPane.showMessageDialog(null, "温馨提示：您所添加的文件已存在，请重命名后再添加","提示",JOptionPane.ERROR_MESSAGE);
							return;

						}
					}
					path=fDialog.getSelectedFile().getPath();
					filelabel[fujiancount]=new LinkLabel(fDialog.getSelectedFile().getName(),new File(path));
					filelabel[fujiancount].setForeground(Color.BLUE);
					p[fujiancount]=new JPanel(new FlowLayout(FlowLayout.LEFT));
					p[fujiancount].add(filelabel[fujiancount]);
					final JLabel label_delete=new JLabel("删除");
					p[fujiancount].add(label_delete);
					accessoryPanel.setLayout(new GridLayout(accessoryPanel.getComponentCount()+1,1));//调整附件Panel的布局
					accessoryPanel.add(p[fujiancount]);
					label_delete.addMouseListener(new MouseAdapter(){
					public void mouseExited(MouseEvent e) {  
						label_delete.setForeground(Color.BLACK);
								    	  }                     
					public void mouseEntered(MouseEvent e) { 
						label_delete.setForeground(Color.RED);
						label_delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					}
						
						public void mouseClicked(MouseEvent e) {
						//	JOptionPane.showConfirmDialog(null, "您确定要删除该附件吗？","删除附件",JOptionPane.YES_NO_OPTION);
							int result=JOptionPane.showConfirmDialog(null, "您确定要删除该附件吗？","删除附件",JOptionPane.YES_NO_OPTION);
							if(result==JOptionPane.YES_OPTION){
							accessoryPanel.setLayout(new GridLayout(accessoryPanel.getComponentCount()-1,1));//调整附件Panel的布局
							int j=0;
							for(int i=0;i<fujiancount;i++)
								if(filelabel[i].getParent()==e.getComponent().getParent())
									{
									   j=i;//选择删除的那个附件  
									   break;
									}
							for(;j<fujiancount-1;j++){
								filelabel[j].setText(filelabel[j+1].getText());
								filelabel[j].setPath(filelabel[j+1].getPath());
								//filelabel[j]=new LinkLabel(filelabel[j+1].getText(),filelabel[j+1].getPath());
								//filelabel[j].setSize(j-1);
								System.out.println("删除的下一个附件名称为："+filelabel[j].getText());
								
							}
						
							//accessoryPanel.remove(p);
							fujiancount--;
							accessoryPanel.remove(p[fujiancount]);
							accessoryPanel.setLayout(new GridLayout(fujiancount,1));
						//	accessoryPanel.repaint();
						    jTabbedPane.repaint();//实时刷新标签上的内容
						//	jTabbedPane.getParent().repaint();
						//   jTabbedPane.getParent().setPreferredSize(new Dimension(accessoryPanel.getWidth(),accessoryPanel.getHeight()));
							//	jTabbedPane.setBounds(0, 0, 800, 800);
						//	jTabbedPane.setBounds(0, 0, 800, 800);
							jTabbedPane.setBounds(0, 0, jTabbedPane.getWidth(), jTabbedPane.getHeight());
					
							}
					}
						}
					);
					fujiancount++;
					jTabbedPane.repaint();//实时刷新标签上的内容

				}
				
			}
			});
			panel3_explore.add(explore_button);
			panel3.add(panel3_explore,BorderLayout.NORTH);
			panel3.add(accessoryPanel,BorderLayout.CENTER);
		
		
		JLabel lbZhushi = new JLabel(
				"注：人月投入数结算，要根据项目规模，人员同时参与的项目数量合理考虑本项目人员的投入比例。     "+"\n"+   "          加*号的为必填选项");
		lbZhushi.setPreferredSize(new Dimension(300, 15));
		panelFour.add(scrollPaneXiangMuZuRenYuan);
		panelFour.add(buttonPanel);
		
		panelFour.add(panel3);
		panelFour.add(lbZhushi);

		/*
		 * JPanel panelSeven = new JPanel(); panelSeven.setLayout(new
		 * BoxLayout(panelSeven, BoxLayout.Y_AXIS)); TitledBorder
		 * titleBorderSeven = BorderFactory.createTitledBorder("项目月报");
		 * titleBorderSeven.setTitlePosition(2);
		 * panelSeven.setBorder(titleBorderSeven); Object[]
		 * columnNamesXiangMuYueBao = {"项目月报"}; Object[][] dataXiangMuYueBao =
		 * new Object[num1][1]; tableModelThree = new
		 * DefaultTableModel(dataXiangMuYueBao,columnNamesXiangMuYueBao);
		 * tableXiangMuYueBao = new JTable(tableModelThree);
		 * tableXiangMuYueBao.getTableHeader().setReorderingAllowed(false); //
		 * tableXiangMuYueBao
		 * .getColumnModel().getColumn(0).setPreferredWidth(180);
		 * tableXiangMuYueBao.setRowHeight(20);
		 * tableXiangMuYueBao.setPreferredScrollableViewportSize(new
		 * Dimension(500, 100)); JScrollPane scrollPaneXiangMuYueBao = new
		 * JScrollPane(tableXiangMuYueBao);
		 * 
		 * JPanel buttonPanel1 = new JPanel(new GridLayout(1, 2)); JButton
		 * addButton1 = new JButton("添加行"); addButton1.addActionListener(new
		 * ActionListener() { public void actionPerformed(ActionEvent e) {
		 * String[] newRow = {""}; tableModelThree.addRow(newRow); } }); JButton
		 * deleteButton1 = new JButton("删除行");
		 * deleteButton1.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { int[] selectedRow =
		 * tableXiangMuYueBao.getSelectedRows();//获得选中行的索引 for(int i = 0; i <
		 * selectedRow.length; i++){ if(selectedRow[i]!=-1) //存在选中行 {
		 * tableModelThree.removeRow(tableXiangMuYueBao.getSelectedRow()); //删除行
		 * } } } }); buttonPanel1.add(addButton1);
		 * buttonPanel1.add(deleteButton1);
		 * 
		 * panelSeven.add(scrollPaneXiangMuYueBao);
		 * panelSeven.add(buttonPanel1);
		 */

		panel.add(panelSix);
		panel.add(panelFive);
		panel.add(panelTwo);
		panel.add(panelThree);
		panel.add(panelFour);
		// panel.add(panelSeven);
		
	//	panel_all.add("1.1.left", panelOne);
	//	panel_all.add("2.1.left", panel);
		
		JPanel ptemp0 = new JPanel();
		ptemp0.setLayout(new BoxLayout(ptemp0, BoxLayout.Y_AXIS));
		
		ptemp0.add("1.1.left", panelOne);
		ptemp0.add("2.1.left", panel);
		
		JScrollPane sp=new JScrollPane(ptemp0);
		
		panel_all.add(sp);

		return panel_all;
	}

	JLabel lbPrjCost;

	public JPanel SecondPanel() {
		// JPanel panel = new JPanel(new GridLayout(3, 1));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel panelOne = new JPanel(new PropertyLayout());
		// panelOne.setPreferredSize(new Dimension(800, 30));
		// TitledBorder titleBorder = BorderFactory.createTitledBorder("");
		// titleBorder.setTitlePosition(2);
		// panelOne.setBorder(titleBorder);
		lbPrjCost = new JLabel("    项目总费用*：");

		textPrjCost = new SACJTextField32(25);
		component_map.put("项目总费用", textPrjCost);
		JLabel lbCost = new JLabel("  万元 ");
		panelOne.add("1.1.left", new JLabel(""));
		panelOne.add("2.1.left", lbPrjCost);
		panelOne.add("2.2.left", textPrjCost);
		panelOne.add("2.3.left", lbCost);

		JPanel panelTwo = new JPanel();
		panelTwo.setLayout(new BoxLayout(panelTwo, BoxLayout.Y_AXIS));
		TitledBorder titleBorderJingJiLaiYuan = BorderFactory
				.createTitledBorder("");
		titleBorderJingJiLaiYuan.setTitlePosition(2);
		panelTwo.setBorder(titleBorderJingJiLaiYuan);
		Object[] columnNamesJingJiLaiYuan = { "", "", "", "", "" };
		Object[][] dataJingJiLaiYuan = {
				{ "经费来源", "其中（）年*", "其中（）年", "其中（）年", "备注" },
				{ "申请总公司资助", "", "", "", "" }, { "申请公司外资助", "", "", "", "" },
				{ "研制部门自筹金额", "", "", "", "" }, { "合    计", "", "", "", "" } };

		DefaultTableCellRenderer dtc = new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				if (row == 0) {
					// setForeground(Color.GRAY);
					setBackground(lbPrjCost.getBackground());
				} else {
					setBackground(Color.WHITE);
				}
				return super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);

			}
		};

		DefaultTableModel tableModel = new DefaultTableModel(dataJingJiLaiYuan,
				columnNamesJingJiLaiYuan) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				if (column < 1 || (row == 0 && column == 4)) {
					return false;
				} else {
					return true;
				}
			}
		};
		tableJingJiLaiYuan = new JTable(tableModel);
		tableJingJiLaiYuan.getTableHeader().setReorderingAllowed(false);
		tableJingJiLaiYuan.getColumnModel().getColumn(0).setCellRenderer(dtc);
		tableJingJiLaiYuan.getColumnModel().getColumn(1).setCellRenderer(dtc);
		tableJingJiLaiYuan.getColumnModel().getColumn(2).setCellRenderer(dtc);
		tableJingJiLaiYuan.getColumnModel().getColumn(3).setCellRenderer(dtc);
		tableJingJiLaiYuan.getColumnModel().getColumn(4).setCellRenderer(dtc);
		tableJingJiLaiYuan.getColumnModel().getColumn(4).setPreferredWidth(300);
		tableJingJiLaiYuan.setPreferredScrollableViewportSize(new Dimension(
				1000, 145));
		tableJingJiLaiYuan.setRowHeight(0, 20);
		for (int i = 1; i < tableJingJiLaiYuan.getRowCount(); i++) {
			tableJingJiLaiYuan.setRowHeight(i, 50);
		}
		JScrollPane scrollPaneJinDuAnPai = new JScrollPane(tableJingJiLaiYuan);
		for (int i = 0; i < 5; i++) {
			if (i == 4) {
				tableJingJiLaiYuan.getColumnModel().getColumn(i).setCellEditor(
						new SACTextAreaEditor1280(tableJingJiLaiYuan));
				tableJingJiLaiYuan.getColumnModel().getColumn(i)
						.setCellRenderer(
								new SACGridBagLaiYRenderer(lbPrjCost
										.getBackground()));
			} else {
				tableJingJiLaiYuan.getColumnModel().getColumn(i).setCellEditor(
						new SACTextFieldEditor32(tableJingJiLaiYuan));
			}
		}
		panelTwo.add(scrollPaneJinDuAnPai);

		JPanel panelThree = new JPanel(new GridLayout(1, 0));
		TitledBorder titleBorderYuSuanZhiChu = BorderFactory
				.createTitledBorder("");
		titleBorderYuSuanZhiChu.setTitlePosition(2);
		panelThree.setBorder(titleBorderYuSuanZhiChu);
		Object[] columnNamesYuSuanZhiChu = { "预算支出科目", "预研阶段(万元)",
				"方案设计(万元)", "样机测试(万元)", "检测与试运行(万元)", "设计确认(万元)", "计算根据及理由" };
		Object[][] dataXiangYuSuanZhiChu = {
				{ "（一）直接费用", "", "", "", "", "", "" },
				{ "1.人员表", "", "", "", "", "", "" },
				{ " （1）研究人员工资", "", "", "", "", "", "" },
				{ " （2）临时工工资", "", "", "", "", "", "" },
				{ "2.设备及软件费", "", "", "", "", "", "" },
				{ " （1）购置", "", "", "", "", "", "见附件（点击此处）" },
				{ " （2）试制", "", "", "", "", "", "" },
				{ "3.业务费", "", "", "", "", "", "" },
				{ " （1）材料、元器件费用", "", "", "", "", "", "见附件（点击此处）" },
				{ " （2）样机加工费", "", "", "", "", "", "" },
				{ " （3）测试试验费", "", "", "", "", "", "见附件（点击此处）" },
				{ " （4）资料费", "", "", "", "", "", "" },
				{ " （5）会议费", "", "", "", "", "", "" },
				{ " （6）差旅费", "", "", "", "", "", "" },
				{ "4.工程化设计费", "", "", "", "", "", "" },
				{ "5.其他直接费用", "", "", "", "", "", "" },
				{ "（二）间接费用", "", "", "", "", "", "" },
				{ "1.现有仪器设备使用费", "", "", "", "", "", "" },
				{ "2.直接管理费用", "", "", "", "", "", "" },
				{ "3.其他间接费用", "", "", "", "", "", "" },
				{ "（三）合作研究支持", "", "", "", "", "", "" },
				{ "1.合作支出1", "", "", "", "", "", "" },
				{ "2.合作支出2", "", "", "", "", "", "" },
				{ "合    计", "", "", "", "", "", "" } };
		DefaultTableModel tableModelYuSuanZhiChu = new DefaultTableModel(
				dataXiangYuSuanZhiChu, columnNamesYuSuanZhiChu) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				if (column < 1 || (row == 5 && column == 6)
						|| (row == 8 && column == 6)
						|| (row == 10 && column == 6)) {
					return false;
				} else {
					return true;
				}
			}
		};
		tableYuSuanZhiChu = new JTable(tableModelYuSuanZhiChu);
		tableYuSuanZhiChu.getTableHeader().setReorderingAllowed(false);
		tableYuSuanZhiChu.getColumnModel().getColumn(0).setPreferredWidth(140);
		tableYuSuanZhiChu.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableYuSuanZhiChu.setPreferredScrollableViewportSize(new Dimension(
				1000, 410));
		tableYuSuanZhiChu.setRowHeight(20);
		// tableXiangMuZuRenYuan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for (int i = 0; i < 7; i++) {
			if (i == 1) {
				tableYuSuanZhiChu.getColumnModel().getColumn(i).setCellEditor(
						new SACTextFieldEditor1280(tableYuSuanZhiChu));
			} else if (i == 6) {
				tableYuSuanZhiChu.getColumnModel().getColumn(i).setCellEditor(
						new SACTextFieldEditor64(tableYuSuanZhiChu));
			} else {
				tableYuSuanZhiChu.getColumnModel().getColumn(i).setCellEditor(
						new SACTextFieldEditor32(tableYuSuanZhiChu));
			}
		}
		JScrollPane scrollPaneXiangMuZuRenYuan = new JScrollPane(
				tableYuSuanZhiChu);
		tableYuSuanZhiChu.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					int row = tableYuSuanZhiChu.getSelectedRow();
					int column = tableYuSuanZhiChu.getSelectedColumn();
					if (row == 5 && column == 6) {
						String form_name = "设备及软件购置费";
						String form_type = "S4SRGZF";
						openform(form_name, form_type);
					} else if (row == 8 && column == 6) {
						String form_name = "关键材料、元器件费";
						String form_type = "S4GCYF";
						openform(form_name, form_type);
					} else if (row == 10 && column == 6) {
						String form_name = "测试、试验费";
						String form_type = "S4CSSYF";
						openform(form_name, form_type);
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

		});
		panelThree.add(scrollPaneXiangMuZuRenYuan);

		// JPanel panelFour = new JPanel(new PropertyLayout());
		// panelFour.setSize(new Dimension(800,90));
		// TitledBorder titleBorderA = BorderFactory.createTitledBorder("");
		// titleBorderA.setTitlePosition(2);
		// panelFour.setBorder(titleBorderA);

		// JLabel lbProposer = new JLabel("    申请");
		// textProposer = new SACJTextField32(25);
		// JLabel lbA = new JLabel("（签名）");
		// JLabel lbTddhead = new JLabel("研制部门技术负责人  ");
		// textTddhead = new SACJTextField32(25);
		// JLabel lbB = new JLabel("（签名）");
		// JLabel lbTsection = new JLabel("    技术部门");
		// textTsection = new SACJTextField32(25);
		// JLabel lbC = new JLabel("（签名）");
		//
		// panelFour.add("1.1.left",new JLabel(""));
		// panelFour.add("2.1.left",lbProposer);
		// panelFour.add("2.2.left",textProposer);
		// panelFour.add("2.3.left",lbA);
		// panelFour.add("2.4.left",new JLabel("        "));
		// panelFour.add("2.5.left",lbTddhead);
		// panelFour.add("2.6.left",textTddhead);
		// panelFour.add("2.7.left",lbB);
		// panelFour.add("3.1.left",lbTsection);
		// panelFour.add("3.2.left",textTsection);
		// panelFour.add("3.3.left",lbC);

		panel.add("1.1.left", panelOne);
		panel.add("2.1.left", panelTwo);
		panel.add("3.1.left", panelThree);
		JPanel panelFour = new JPanel(new GridLayout(1, 0));
		JPanel panelFive = new JPanel(new GridLayout(1, 0));
		JPanel panelsix = new JPanel(new GridLayout(1, 0));
		JPanel panelseven = new JPanel(new GridLayout(1, 0));
		JPanel paneleight = new JPanel(new GridLayout(1, 0));
		JPanel panelnine = new JPanel(new GridLayout(1, 0));
		JPanel panel1 = new JPanel(new GridLayout(1, 0));
		JPanel panel2 = new JPanel(new GridLayout(1, 0));
		JPanel panel3 = new JPanel(new GridLayout(1, 0));
		panel.add("4.1.left", panelFour);
		panel.add("5.1.left", panelFive);
		panel.add("6.1.left", panelsix);
		panel.add("7.1.left", panelseven);
		panel.add("8.1.left", paneleight);
		panel.add("9.1.left", panelnine);
		panel.add("10.1.left", panel1);
		panel.add("11.1.left", panel2);
		panel.add("12.1.left", panel3);

		return panel;
	}

	/**
	 * 单击相应表格单元格打开form
	 * */
	public void openform(String form_name, String form_type) {
		try {
			// 首先判断有没有这个form
			/*
			 * TCComponent[] coms = item.getRelatedComponents(relation);
			 * TCComponentFolder parentFolder = null; if(coms!=null &&
			 * coms.length>0){ for (int i = 0; i < coms.length; i++) {
			 * if("Folder".equals(coms[i].getType().toString())){ parentFolder =
			 * (TCComponentFolder) coms[i]; } }
			 * 
			 * }
			 */
			TCComponent[] forms = item.getRelatedComponents(yc_relation);
			System.out.println("length====>:" + forms.length);
			System.out.println("form_name====>:" + form_name);
			if (forms != null && forms.length > 0) {
				TCComponentForm open_form = null;
				for (int i = 0; i < forms.length; i++) {
					System.out.println("2222----->:"
							+ forms[i].getProperty("object_name").toString());
					// if(form_type(forms[i].getType().toString())){
					if (form_name.equals(forms[i].getProperty("object_name")
							.toString())) {
						open_form = (TCComponentForm) forms[i];
					}
				}
				if (open_form != null) {
					System.out.println("gz_form===>;"
							+ open_form.getProperty("object_name"));
					OpenFormDialog openbzjkjjd = new OpenFormDialog(open_form);
					openbzjkjjd.showDialog();
				} else {
					TCComponentForm create_form = createrjgzfyForm(form_name,
							form_type);
					item.add(yc_relation, create_form);
					OpenFormDialog openbzjkjjd = new OpenFormDialog(create_form);
					openbzjkjjd.showDialog();
				}
			} else {
				TCComponentForm create_form = createrjgzfyForm(form_name,
						form_type);
				item.add(yc_relation, create_form);
				OpenFormDialog openbzjkjjd = new OpenFormDialog(create_form);
				openbzjkjjd.showDialog();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 构造项目简况的Panel
	 */
	public JPanel bulidxmjk_Panel() {
		JPanel panel_xmjk = new JPanel();
		panel_xmjk.setLayout(new BoxLayout(panel_xmjk, BoxLayout.Y_AXIS));
		JPanel panel_1 = new JPanel(new PropertyLayout());
		            //进行了修改的地方
		JLabel syfw = new JLabel("适用范围*");
		syfwiTextField = new JTextArea(3, 152);    //正在进行的地方
		component_map.put("适用范围", syfwiTextField);
		
		syfwiTextField .setLineWrap(true);
		syfwiTextField.setText(syfw_str); 
		SACDocument1280 syfw_doc = new SACDocument1280();
		syfwiTextField.setDocument(syfw_doc);
		syfwiTextField.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (syfw_str.equals(syfwiTextField.getText().toString())) {
					syfwiTextField.setText("");
				}

			}
		});
				
		//syfwiTextField = new SACJTextField1280(152);
		
		/*syfwiTextField.setText(syfw_str);
		syfwiTextField.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (syfw_str.equals(syfwiTextField.getText().toString())) {
					syfwiTextField.setText("");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

		});*/
		JLabel qzny = new JLabel("起始年月*");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		qznyidatebutton = new DateButton(sdf);
		// qznyidatebutton.setDate(default_date);
		qznyidatebutton.setPreferredSize(new Dimension(420, 22));
		JLabel jzny = new JLabel("截止年月*");
		jznyidatebutton = new DateButton(sdf);
		// jznyidatebutton.setDate(default_date);
		jznyidatebutton.setPreferredSize(new Dimension(440, 22));
		JLabel cddwybm = new JLabel("承担单位*");                      //修改的地方
		cddwybmiTextField = new JComboBox(dw_str);;
		component_map.put("承担单位", cddwybmiTextField);
		JLabel cb_bm = new JLabel("承担部门*");                      //修改的地方
		cbdwTextField = new SACJTextField128(70);
		component_map.put("承担部门", cbdwTextField);
		
		
		
		
		JLabel yzfs = new JLabel("研试方式*");
		ysfsiTextField = new JTextArea(3, 152);                                //需要修改的地方
		component_map.put("研试方式", ysfsiTextField);
		ysfsiTextField.setLineWrap(true);
		ysfsiTextField.setText(yafs_str);
		// 设置研试方式输入最大值
		SACDocument256 yzfs_doc = new SACDocument256();
		ysfsiTextField.setDocument(yzfs_doc);
		ysfsiTextField.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (yafs_str.equals(ysfsiTextField.getText().toString())) {
					ysfsiTextField.setText("");
				}

			}
		});
		JScrollPane ysfs_jspane = new JScrollPane(ysfsiTextField);
		JLabel hzdw = new JLabel("合作单位*");
		hzdwiTextField = new SACJTextField32(70);
		component_map.put("合作单位", hzdwiTextField);
		JLabel hzfs = new JLabel("合作方式*");
		hzfsiTextField = new SACJTextField32(71);
		component_map.put("合作方式", hzfsiTextField);
		hzfsiTextField.setText(hzfs_str);
		hzfsiTextField.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (hzfs_str.equals(hzfsiTextField.getText().toString())) {
					hzfsiTextField.setText("");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

		});
		JLabel xmzys = new JLabel("项目总预算(万元)*");
		xmzysiTextField = new SACJTextField64(70);
		component_map.put("项目总预算", xmzysiTextField);
		JLabel zjly = new JLabel("资金来源*");
		zjlyiTextField = new SACJTextField64(71);
		component_map.put("资金来源", zjlyiTextField);
		zjlyiTextField.setText(zjly_str);
		zjlyiTextField.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (zjly_str.equals(zjlyiTextField.getText().toString())) {
					zjlyiTextField.setText("");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

		});
		// 预期目标Panel
		JPanel panel_yqmb = new JPanel(new PropertyLayout());
		// 构造预期目标及成果标签
		TitledBorder yqmbBorder = BorderFactory.createTitledBorder("预期目标及成果");
		yqmbBorder.setTitlePosition(TitledBorder.TOP);
		yqmbBorder.setTitleFont(font);
		panel_yqmb.setBorder(yqmbBorder);

		JPanel panel_mb = new JPanel(new PropertyLayout());
		JLabel mbjssp = new JLabel("目标技术水平*");
		mbjsspiTextField = new JTextArea(10, 50);
		component_map.put("目标技术水平", mbjsspiTextField);
		mbjsspiTextField.setLineWrap(true);
		mbjsspiTextField.setText(mbjssp_str);
		// 设置目标技术水平输入最大值
		SACDocument1280 mbjssp_doc = new SACDocument1280();
		mbjsspiTextField.setDocument(mbjssp_doc);
		JScrollPane mbjssp_jspane = new JScrollPane(mbjsspiTextField);
		mbjsspiTextField.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (mbjssp_str.equals(mbjsspiTextField.getText().toString())) {
					mbjsspiTextField.setText("");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

		});

		JPanel panel_zl = new JPanel(new PropertyLayout());
		JLabel zlzzlw = new JLabel("专利/著作权/论文*");
		// 专利与论文Panel
		JPanel panel_zlylw = new JPanel(new PropertyLayout());
		JLabel zl = new JLabel("专利*");
		zliTextField = new iTextArea(3, 40);
		component_map.put("专利", zliTextField);
		zliTextField.setLineWrap(true);
		// 设置专利输入最大值                                                                                              最大值竟然只有32个字符
		SACDocument32 zl_doc = new SACDocument32();
		zliTextField.setDocument(zl_doc);
		JScrollPane zl_jspane = new JScrollPane(zliTextField);
		JLabel zzq = new JLabel("著作权*");
		zzqiTextField = new iTextArea(3, 40);
		component_map.put("著作权", zzqiTextField);
		zzqiTextField.setLineWrap(true);
		// 设置著作权输入最大值
		SACDocument32 zzq_doc = new SACDocument32();
		zzqiTextField.setDocument(zzq_doc);
		JScrollPane zzq_jspane = new JScrollPane(zzqiTextField);
		JLabel lw = new JLabel("论文*");
		lwiTextField = new iTextArea(3, 40);
		component_map.put("论文", lwiTextField);
		lwiTextField.setLineWrap(true);
		// 设置论文输入最大值
		SACDocument32 lw_doc = new SACDocument32();
		lwiTextField.setDocument(lw_doc);
		JScrollPane lw_jspane = new JScrollPane(lwiTextField);

		panel_mb.add("1.1.left", mbjssp);
		panel_mb.add("2.1.left", mbjssp_jspane);

		panel_zlylw.add("1.1.left", zl);
		panel_zlylw.add("1.2.left", zl_jspane);
		panel_zlylw.add("2.1.left", zzq);
		panel_zlylw.add("2.2.left", zzq_jspane);
		panel_zlylw.add("3.1.left", lw);
		panel_zlylw.add("3.2.left", lw_jspane);

		panel_zl.add("1.1.left", zlzzlw);
		panel_zl.add("2.1.left", panel_zlylw);

		JPanel panel_cg = new JPanel(new PropertyLayout());
		JLabel cgysfs = new JLabel("成果验收方式*");
		cgysfsiTextField = new JTextArea(10, 52);
		component_map.put("成果验收方式", cgysfsiTextField);
		cgysfsiTextField.setLineWrap(true);
		cgysfsiTextField.setText(cgysfs_str);
		// 设置成果验收方式输入最大值
		SACDocument256 cgysfs_doc = new SACDocument256();
		cgysfsiTextField.setDocument(cgysfs_doc);
		JScrollPane cgysfs_jspane = new JScrollPane(cgysfsiTextField);
		cgysfsiTextField.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (cgysfs_str.equals(cgysfsiTextField.getText().toString())) {
					cgysfsiTextField.setText("");
				}

			}
		});
		panel_cg.add("1.1.left", cgysfs);                                          //成果验收方式
		panel_cg.add("2.1.left", cgysfs_jspane);

		panel_yqmb.add("1.1.left", new JLabel("               "));
		panel_yqmb.add("1.2.left", panel_mb);
		panel_yqmb.add("1.3.left", panel_zl);
		panel_yqmb.add("1.4.left", panel_cg);

		// panel_1.add("1.1.left",xmmc);
		// panel_1.add("1.2.left",xmmciTextField);
		// panel_1.add("2.1.left",yzlh);
		// panel_1.add("2.2.left",yzlhiTextField);
		panel_1.add("1.1.left", syfw);
		panel_1.add("1.2.left", syfwiTextField);
		panel_1.add("2.1.left", qzny);
		panel_1.add("2.2.left", qznyidatebutton);
		panel_1.add("2.3.left", jzny);
		panel_1.add("2.4.left", jznyidatebutton);
		panel_1.add("3.1.left", cddwybm);
		panel_1.add("3.2.left", cddwybmiTextField);
		panel_1.add("3.3.left",cb_bm);
		panel_1.add("3.4.left",cbdwTextField);
		panel_1.add("4.1.left", yzfs);
		panel_1.add("4.2.left", ysfs_jspane);
		panel_1.add("5.1.left", hzdw);
		panel_1.add("5.2.left", hzdwiTextField);
		panel_1.add("5.3.left", hzfs);
		panel_1.add("5.4.left", hzfsiTextField);
		panel_1.add("6.1.left", xmzys);
		panel_1.add("6.2.left", xmzysiTextField);
		panel_1.add("6.3.left", zjly);
		panel_1.add("6.4.left", zjlyiTextField);
		panel_xmjk.add("1.1.left", panel_1);
		panel_xmjk.add("2.1.left", panel_yqmb);

		return panel_xmjk;

	}

	/**
	 * 构造项目负责人的Panel
	 * */
	public JPanel bulidxmfzr_Panel() {
		JPanel panel_fz = new JPanel();
		panel_fz.setLayout(new BoxLayout(panel_fz, BoxLayout.Y_AXIS));

		JPanel panel_fzr = new JPanel(new PropertyLayout());
		JLabel xm1 = new JLabel("姓名*");
		xmiTextField1 = new SACJTextField32(46);
		component_map.put("姓名", xmiTextField1);
		JLabel zc1 = new JLabel("职称*");
		zc_Box1 = new JComboBox(zc_str);
		component_map.put("职称", zc_Box1);
		zc_Box1.setPreferredSize(new Dimension(278, 20));
		JLabel zy1 = new JLabel("专业*");
		zyiTextField1 = new SACJTextField32(47);
		component_map.put("专业", zyiTextField1);
		JLabel yzdw1 = new JLabel("研制单位*");
		yanzdw_Box1 = new JComboBox(dw_str);
		component_map.put("研制单位", yanzdw_Box1);
		yanzdw_Box1.setPreferredSize(new Dimension(282, 20));
		JLabel yzbm1 = new JLabel("研制部门*");
		yanziTextField1 = new SACJTextField32(95);
		component_map.put("研制部门", yanziTextField1);
		JLabel lxfs1 = new JLabel("联系方式*");
		lxfsiTextField1 = new JTextArea(3, 72);
		component_map.put("联系方式", lxfsiTextField1);
		lxfsiTextField1.setLineWrap(true);
		lxfsiTextField1.setText(tel_str);
		// 设置联系方式输入最大值
		SACDocument32 lxfs1_doc = new SACDocument32();
		lxfsiTextField1.setDocument(lxfs1_doc);
		lxfsiTextField1.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (tel_str.equals(lxfsiTextField1.getText().toString())) {
					lxfsiTextField1.setText("");
				}

			}
		});
		JScrollPane lxfs1_jspane = new JScrollPane(lxfsiTextField1);
		JLabel email1 = new JLabel(" Email*");
		emailiTextField1 = new JTextArea(3, 71);
		component_map.put("Email", emailiTextField1);
		emailiTextField1.setLineWrap(true);
		// 设置Email输入最大值
		SACDocument32 email1_doc = new SACDocument32();
		emailiTextField1.setDocument(email1_doc);
		JScrollPane email1_jspane = new JScrollPane(emailiTextField1);

		JLabel xm2 = new JLabel("姓名");
		xmiTextField2 = new SACJTextField32(46);
		JLabel zc2 = new JLabel("职称");
		zc_Box2 = new JComboBox(zc_str);
		zc_Box2.setPreferredSize(new Dimension(278, 20));
		JLabel zy2 = new JLabel("专业");
		zyiTextField2 = new SACJTextField32(47);
		JLabel dwbm2 = new JLabel("研制单位");
		yanzdw_Box2 = new JComboBox(dw_str);
		yanzdw_Box2.setPreferredSize(new Dimension(282, 20));
		JLabel yzbm2 = new JLabel("研制部门");
		yanziTextField2 = new SACJTextField32(95);
		JLabel lxfs2 = new JLabel("联系方式");
		lxfsiTextField2 = new JTextArea(3, 72);
		lxfsiTextField2.setText(tel_str);
		lxfsiTextField2.setLineWrap(true);
		// 设置联系方式输入最大值
		SACDocument32 lxfs2_doc = new SACDocument32();
		lxfsiTextField2.setDocument(lxfs2_doc);
		lxfsiTextField2.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (tel_str.equals(lxfsiTextField2.getText().toString())) {
					lxfsiTextField2.setText("");
				}

			}
		});
		JScrollPane lxfs2_jspane = new JScrollPane(lxfsiTextField2);
		JLabel email2 = new JLabel(" Email");
		emailiTextField2 = new JTextArea(3, 71);
		emailiTextField2.setLineWrap(true);
		// 设置Email输入最大值
		SACDocument32 email2_doc = new SACDocument32();
		emailiTextField2.setDocument(email2_doc);
		JScrollPane email2_jspane = new JScrollPane(emailiTextField2);

		JLabel xm3 = new JLabel("姓名");
		xmiTextField3 = new SACJTextField32(46);
		JLabel zc3 = new JLabel("职称");
		zc_Box3 = new JComboBox(zc_str);
		zc_Box3.setPreferredSize(new Dimension(278, 20));
		JLabel zy3 = new JLabel("专业");
		zyiTextField3 = new SACJTextField32(47);
		JLabel dwbm3 = new JLabel("研制单位");
		yanzdw_Box3 = new JComboBox(dw_str);
		yanzdw_Box3.setPreferredSize(new Dimension(282, 20));
		JLabel yzbm3 = new JLabel("研制部门");
		yanziTextField3 = new SACJTextField32(95);
		JLabel lxfs3 = new JLabel("联系方式");
		lxfsiTextField3 = new JTextArea(3, 72);
		lxfsiTextField3.setText(tel_str);
		// 设置联系方式输入最大值
		SACDocument32 lxfs3_doc = new SACDocument32();
		lxfsiTextField3.setDocument(lxfs3_doc);
		lxfsiTextField3.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (tel_str.equals(lxfsiTextField3.getText().toString())) {
					lxfsiTextField3.setText("");
				}

			}
		});
		JScrollPane lxfs3_jspane = new JScrollPane(lxfsiTextField3);
		JLabel email3 = new JLabel(" Email");
		emailiTextField3 = new JTextArea(3, 71);
		emailiTextField3.setLineWrap(true);
		// 设置Email输入最大值
		SACDocument32 email3_doc = new SACDocument32();
		emailiTextField3.setDocument(email3_doc);
		JScrollPane email3_jspane = new JScrollPane(emailiTextField3);

		panel_fzr.add("1.1.left", xm1);
		panel_fzr.add("1.2.left", new JLabel("      "));
		panel_fzr.add("1.3.left", xmiTextField1);
		panel_fzr.add("1.4.left", zc1);
		panel_fzr.add("1.5.left", zc_Box1);
		panel_fzr.add("1.6.left", zy1);
		panel_fzr.add("1.7.left", zyiTextField1);
		panel_fzr.add("2.1.left", yzdw1);
		panel_fzr.add("2.2.left", new JLabel("      "));
		panel_fzr.add("2.3.left", yanzdw_Box1);
		panel_fzr.add("2.4.left", yzbm1);
		panel_fzr.add("2.5.left", yanziTextField1);
		panel_fzr.add("3.1.left", lxfs1);
		panel_fzr.add("3.2.left", new JLabel("      "));
		panel_fzr.add("3.3.left", lxfs1_jspane);
		panel_fzr.add("3.4.left", email1);
		panel_fzr.add("3.5.left", email1_jspane);
		panel_fzr.add("4.1.left", xm2);
		panel_fzr.add("4.2.left", new JLabel("      "));
		panel_fzr.add("4.3.left", xmiTextField2);
		panel_fzr.add("4.4.left", zc2);
		panel_fzr.add("4.5.left", zc_Box2);
		panel_fzr.add("4.6.left", zy2);
		panel_fzr.add("4.7.left", zyiTextField2);
		panel_fzr.add("5.1.left", dwbm2);
		panel_fzr.add("5.2.left", new JLabel("      "));
		panel_fzr.add("5.3.left", yanzdw_Box2);
		panel_fzr.add("5.4.left", yzbm2);
		panel_fzr.add("5.5.left", yanziTextField2);
		panel_fzr.add("6.1.left", lxfs2);
		panel_fzr.add("6.2.left", new JLabel("      "));
		panel_fzr.add("6.3.left", lxfs2_jspane);
		panel_fzr.add("6.4.left", email2);
		panel_fzr.add("6.5.left", email2_jspane);
		panel_fzr.add("7.1.left", xm3);
		panel_fzr.add("7.2.left", new JLabel("      "));
		panel_fzr.add("7.3.left", xmiTextField3);
		panel_fzr.add("7.4.left", zc3);
		panel_fzr.add("7.5.left", zc_Box3);
		panel_fzr.add("7.6.left", zy3);
		panel_fzr.add("7.7.left", zyiTextField3);
		panel_fzr.add("8.1.left", dwbm3);
		panel_fzr.add("8.2.left", new JLabel("      "));
		panel_fzr.add("8.3.left", yanzdw_Box3);
		panel_fzr.add("8.4.left", yzbm3);
		panel_fzr.add("8.5.left", yanziTextField3);
		panel_fzr.add("9.1.left", lxfs3);
		panel_fzr.add("9.2.left", new JLabel("      "));
		panel_fzr.add("9.3.left", lxfs3_jspane);
		panel_fzr.add("9.4.left", email3);
		panel_fzr.add("9.5.left", email3_jspane);

		panel_fz.add(panel_fzr);
		return panel_fz;

	}

	/**
	 * 创建Form
	 * 
	 * */
	public TCComponentForm createrjgzfyForm(String name, String form_type) {
		try {
			TCComponentFormType type = (TCComponentFormType) session
					.getTypeComponent(form_type);
			TCComponentForm form = type.create(name, "", form_type);
			return form;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得Lov列表值
	 * 
	 * @param strLOVName
	 * @return
	 */
	private Object[] getListOfValues(String strLOVName) {

		Object[] arrayObject = null;
		try {
			TCComponentListOfValuesType listType = (TCComponentListOfValuesType) session
					.getTypeComponent("ListOfValuesString");
			TCComponentListOfValues[] listValues = listType.find(strLOVName);
			if (listValues != null) {
				for (int i = 0; i < listValues.length; i++) {
					ListOfValuesInfo listValuesInfo = listValues[i]
							.getListOfValues();
					arrayObject = listValuesInfo.getListOfValues();
				}
			}
		} catch (TCException e) {
			e.printStackTrace();
		}

		return arrayObject;
	}
}

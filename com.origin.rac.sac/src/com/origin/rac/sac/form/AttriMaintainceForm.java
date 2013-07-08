package com.origin.rac.sac.form;
/**
 * @file AttriMaintainceForm.java
 *
 * @brief 物料版本Form客制化
 * 
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import cn.com.origin.util.JAutoCompleteComboBox;


import cn.com.origin.util.SACDocument;
import cn.com.origin.util.SACJTextField128;
import cn.com.origin.util.SACJTextField160;
import cn.com.origin.util.SACJTextField32;
import cn.com.origin.util.SACMyDocument;

import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.form.AbstractTCForm;
import com.teamcenter.rac.kernel.*;
import com.teamcenter.rac.util.*;

public class AttriMaintainceForm extends AbstractTCForm
{
    private TCFormProperty[] formProperties = null;
	private String[] optionKeys = null;
	private String preferenceName = "SAC_AMF";

    private Registry registry = Registry.getRegistry(this);
    private TCComponentForm form;
    private TCSession tcsession;
    private Font font;
    private SACJTextField32 wlbmiTextField;//物料编码
    private JComboBox wlmbiTextField;//物料模板
    private SACJTextField160 wlmsiTextField;//物料描述
    private JComboBox wlztiTextField;//物料状态
    private SACJTextField32 sccsiTextField;//生产厂商
    private SACJTextField32 dhdmiTextField;//订货代码
    private SACJTextField32 dydyiTextField;//电源电压
    private SACJTextField32 ctdianliTextField;//CT电流
    private JCheckBox kcwliTextField;//库存物料
    private JComboBox zydwiTextField;//主要单位
    private JCheckBox yxgxsmiTextField;//允许更新说明
    private SACJTextField32 gxhiTextField;//工序号
    private JComboBox sfyxwliTextField;//是否原型物料
    
    private JComboBox zzdmiTextField1;             //组织代码1
    private JComboBox mrcgyiTextField1;//默认采购员编号1
    private JComboBox jihyiTextField1;//计划员编号1
    private iTextField tqq_gdiTextField1;//固定提前期1
    private JComboBox kclbjiTextField1;//SAC_库存类别集1
    private JComboBox cwlbjiTextField1;//SAC_财务类别集1
    private JComboBox jhlbjiTextField1;//SAC_计划类别集1
    private JComboBox cplbjiTextField1;//SAC_产品类别集1
    private JComboBox zkciTextField1;//子库存1
    private SACJTextField32 hwiTextField1;//货位1
    private iTextField gdgytsiTextField1;//固定供应天数1
    private iTextField gdplzjiTextField1;//固定批量增加1
    private JComboBox kcjhffiTextField1;//库存计划方法1
    private SACJTextField32 jmbjgiTextField1;//价目表价格1
    private JComboBox zldwiTextField1;//重量单位1
    private SACJTextField32 dwzliTextField1;//单位重量1
    private JComboBox tjdwiTextField1;//体积单位1
    private SACJTextField32 dwtjiTextField1;//单位体积1
    
    private JComboBox zzdmiTextField2;                //组织代码2
    private JComboBox mrcgyiTextField2;//默认采购员编号2
    private JComboBox jihyiTextField2;//计划员编号2
    private iTextField tqq_gdiTextField2;//固定提前期2
//    private JComboBox kclbjiTextField2;//SAC_库存类别集2
    private JComboBox cwlbjiTextField2;//SAC_财务类别集2
    private JComboBox jhlbjiTextField2;//SAC_计划类别集2
    private JComboBox cplbjiTextField2;//SAC_产品类别集2
    private JComboBox zkciTextField2;//子库存2
    private SACJTextField32 hwiTextField2;//货位2
    private iTextField gdgytsiTextField2;//固定供应天数2
    private iTextField gdplzjiTextField2;//固定批量增加2
    private JComboBox kcjhffiTextField2;//库存计划方法2
    private SACJTextField32 jmbjgiTextField2;//价目表价格2
    private JComboBox zldwiTextField2;//重量单位2
    private SACJTextField32 dwzliTextField2;//单位重量2
    private JComboBox tjdwiTextField2;//体积单位2
    private SACJTextField32 dwtjiTextField2;//单位体积2
    private String[] sfyxwl_str = {"N","Y"};
    private HashMap<String, Object> component_map = new HashMap<String, Object>();
    private String Yes = "Y";
    private String No = "N";
    private String str1=null;   //组织代码1
    private String str2=null;   //组织代码2
    private String[] kc_str=null;  		 //库存类别集
    private String[] jhybh_str=null;	 //计划员编号
    private String[] zzdm_str =null;
    private String[] wlzt_str =null;
    private String[] mrcgy_str = null;
    private String[] kclbj_str = null;
    private String[] cwlbj_str = null;
    private String[] jhlbj_str = null;
    private String[] cplbj_str = null;
    private String[] zkc_str = null;
    private String[] kcjhff_str = null;
    private String[] dw_str = null;
    private String[] wlmb_str = null;
    public DefaultTableModel tableModel = null;
    private String allow_num = "[0-9]";
    private SACMyDocument doc_gdtqq = null;
    private SACMyDocument doc_gdgyts = null;
    private SACMyDocument doc_gdplzj = null;
    private String attri1 = "s4Passing_State";
    private String attri2 = "s4Primary_Unit_of_M";
    private String attri3 = "s4Wpromaterials";
    private String attri4 = "s4Inventory_Item";
    private String cdzt_str = "";//传递状态的值
    private Color color;
    
    private boolean is_first=false;
    private boolean is_first0=false;//保证组织代码一定在子库存前执行
    
    public AttriMaintainceForm(TCComponentForm tccomponentform) throws Exception {
    	super(tccomponentform);
		form = tccomponentform;
		tcsession = (TCSession) form.getSession();
		font=new Font("宋体",Font.BOLD,12);
		cdzt_str = form.getProperty(attri1).toString();
		System.out.println("cdzt_str===>:"+cdzt_str);
		String[] all_str = tcsession.getPreferenceService().getStringArray(4, "SAC_Attri_Lov");
		if(all_str == null || all_str.length == 0){
			MessageBox.post("未配置首选项：SAC_Attri_Lov", "提示", MessageBox.INFORMATION);
			return;
		}else{
			//组织代码LOV
			String s_zzdm = all_str[0];
			String zzdm[] = null;
			zzdm = s_zzdm.split(":")[1].split(",");
			zzdm_str = new String[zzdm.length+1];
			for (int i = 0; i < zzdm.length; i++) {
				zzdm_str[0] = "";
				zzdm_str[i+1] = zzdm[i];
			}
			//物料状态LOV
			String s_wlzt = all_str[1];
			String wlzt[] = null;
			wlzt = s_wlzt.split(":")[1].split(",");
			wlzt_str = new String[wlzt.length+1];
			for (int i = 0; i < wlzt.length; i++) {
				wlzt_str[0] = "";
				wlzt_str[i+1] = wlzt[i];
			}
			//默认采购员LOV
			String s_mrcgy = all_str[2];
			String mrcgy[] = null;
			mrcgy = s_mrcgy.split(":")[1].split(",");
			mrcgy_str = new String[mrcgy.length+1];
			for (int i = 0; i < mrcgy.length; i++) {
				mrcgy_str[0] = "";
				mrcgy_str[i+1] = mrcgy[i];
			}
			//SAC_库存类别集LOV
			String s_kclbj = all_str[3];
			String kclbj[] = null;
			kclbj = s_kclbj.split(":")[1].split(",");
			kclbj_str = new String[kclbj.length+1];
			for (int i = 0; i < kclbj.length; i++) {
				kclbj_str[0] = "";
				kclbj_str[i+1] = kclbj[i];
			}
			//SAC_财务类别集LOV
			String s_cwlbj = all_str[4];
			String cwlbj[] = null;
			cwlbj = s_cwlbj.split(":")[1].split(",");
			cwlbj_str = new String[cwlbj.length+1];
			for (int i = 0; i < cwlbj.length; i++) {
				cwlbj_str[0] = "";
				cwlbj_str[i+1] = cwlbj[i];
			}
			//SAC_计划类别集LOV
			String s_jhlbj = all_str[5];
			String jhlbj[] = null;
			jhlbj = s_jhlbj.split(":")[1].split(",");
			jhlbj_str = new String[jhlbj.length+1];
			for (int i = 0; i < jhlbj.length; i++) {
				jhlbj_str[0] = "";
				jhlbj_str[i+1] = jhlbj[i];
			}
			//SAC_产品类别集LOV
			String s_cplbj = all_str[6];
			String cplbj[] = null;
			cplbj = s_cplbj.split(":")[1].split(",");
			cplbj_str = new String[cplbj.length+1];
			for (int i = 0; i < cplbj.length; i++) {
				cplbj_str[0] = "";
				cplbj_str[i+1] = cplbj[i];
			}
			//子库存LOV
			String s_zkc = all_str[7];
			String zkc[] = null;
			zkc = s_zkc.split(":")[1].split(",");
			zkc_str = new String[zkc.length+1];
			for (int i = 0; i < zkc.length; i++) {
				zkc_str[0] = "";
				zkc_str[i+1] = zkc[i];
			}
			//库存计划方法LOV
			String s_kcjhff = all_str[8];
			String kcjhff[] = null;
			kcjhff = s_kcjhff.split(":")[1].split(",");
			kcjhff_str = new String[kcjhff.length+1];
			for (int i = 0; i < kcjhff.length; i++) {
				kcjhff_str[0] = "";
				kcjhff_str[i+1] = kcjhff[i];
			}
			//主要单位LOV
			String s_dw = all_str[9];
			String dw[] = null;
			dw = s_dw.split(":")[1].split(",");
			dw_str = new String[dw.length+1];
			for (int i = 0; i < dw.length; i++) {
				dw_str[0] = "";
				dw_str[i+1] = dw[i];
			}
			//物料模板LOV
			String s_wlmb = all_str[10];
			String wlmb[] = null;
			wlmb = s_wlmb.split(":")[1].split(";");
			wlmb_str = new String[wlmb.length+1];
			for (int i = 0; i < wlmb.length; i++) {
				wlmb_str[0] = "";
				wlmb_str[i+1] = wlmb[i];
			}
			//计划员编号
			String s_jhybh=all_str[11];
			String jhybh[]=null;
			jhybh= s_jhybh.split(":")[1].split(",");
			jhybh_str=new String[jhybh.length+1];
			for (int i = 0; i < jhybh.length; i++) {
				jhybh_str[0] = "";
				//System.out.println(wlmb_str[i]);
				jhybh_str[i+1] = jhybh[i];
			}
		}
		//下面是对库存类别集所做的修改
		String[] kc_str1 = tcsession.getPreferenceService().getStringArray(4, "SAC_KC_Lov");
		if(kc_str1 == null || kc_str1.length == 0){
			MessageBox.post("未配置首选项：SAC_KC_Lov", "提示", MessageBox.INFORMATION);
			return;
		}else{
			//库存类别集
			String  s_kclb=kc_str1[0];
			String  kclb[]=null;
			kclb=s_kclb.split(":")[1].split(",");
			kc_str=new String[kclb.length+1];
			for (int i = 0; i < kclb.length; i++) {
				kc_str[0] = "";
				//System.out.println(wlmb_str[i]);
				kc_str[i+1] = kclb[i];
			}
		}
		optionKeys = getTCPreferenceArray(tcsession, preferenceName);
		if(optionKeys == null || optionKeys.length == 0){
			MessageBox.post("未配置首选项：SAC_AMF", "提示", MessageBox.INFORMATION);
			return;
		}
		initializeUI();
		if("Y".equals(cdzt_str)){
			zydwiTextField.getComponent(0).removeMouseListener(zydwiTextField.getMouseListeners()[0]);
			zydwiTextField.removeMouseListener(zydwiTextField.getMouseListeners()[0]);
			sfyxwliTextField.getComponent(0).removeMouseListener(sfyxwliTextField.getMouseListeners()[0]);
			sfyxwliTextField.removeMouseListener(sfyxwliTextField.getMouseListeners()[0]);
		}
		loadForm();
    }
    /*
     * Form加载数据
     * 
     * */
    public void loadForm() throws TCException {
    	try {
    		formProperties = form.getAllFormProperties();
    		int k = formProperties.length;
    		for (int i = 0; i < k; i++) {
    			String str = formProperties[i].getPropertyName();
    		/*	if(formProperties[i].getPropertyName().equals("s4tissue15")){
    				str="s4Childstock1";
    			};
    			if(formProperties[i].getPropertyName().equals("s4Childstock1")){
    				str="s4tissue15";
    			}
    		*/
    			if(formProperties[i].getPropertyName().equals("s4Childstock" )&&is_first==false){
    				str="s4tissue14";
    			}
    			if(formProperties[i].getPropertyName().equals("s4tissue14")&&is_first==true){
    				str="s4Childstock";
    			};

    			if(formProperties[i].getPropertyName().equals("s4Childstock1")&&is_first0==false){
    				str="s4tissue15";
    			}
    			if(formProperties[i].getPropertyName().equals("s4tissue15")&&is_first0==true){
    				str="s4Childstock1";
    			};
    			if(str.equals("s4Materialt")){
    				if(formProperties[i].getStringValue()==null ||"".equals(formProperties[i].getStringValue()))
    				{}
    				else
    					wlmbiTextField.setSelectedItem(formProperties[i].getStringValue());	
    			} else if(str.equals("s4Mdescription")){
    				wlmsiTextField.setText(formProperties[i].getStringValue());	
    			} else if(str.equals("s4Item_Status")){
    				wlztiTextField.setSelectedItem(formProperties[i].getStringValue());	
    			} else if(str.equals("s4vendor")){
    				sccsiTextField.setText(formProperties[i].getStringValue());	
    			} else if(str.equals("s4contact_maker")){
    				dhdmiTextField.setText(formProperties[i].getStringValue());	
    			} else if(str.equals("s4Supply_vol")){
    				dydyiTextField.setText(formProperties[i].getStringValue());	
    			} else if(str.equals("s4CT_current")){
    				ctdianliTextField.setText(formProperties[i].getStringValue());	
    			} /*else if(str.equals("s4Inventory_Item")){
    				if(Yes.equals(formProperties[i].getStringValue())){
    					kcwliTextField.setSelected(true);
    				}else{
    					kcwliTextField.setSelected(false);
    				}
    			} */else if(str.equals("s4Primary_Unit_of_M")){
    				zydwiTextField.setSelectedItem(formProperties[i].getStringValue());	
    			} else if(str.equals("s4Allow_Description_U")){
    				if(Yes.equals(formProperties[i].getStringValue())){
    					yxgxsmiTextField.setSelected(true);
    				}else{
    					yxgxsmiTextField.setSelected(false);
    				}
    			} else if(str.equals("s4opernumber")){
    				gxhiTextField.setText(formProperties[i].getStringValue());	
    			} else if(str.equals("s4Wpromaterials")){
    				sfyxwliTextField.setSelectedItem(formProperties[i].getStringValue());	
    			} else if(str.equals("s4tissue14")){	                   
    				if(!"Y".equals(cdzt_str)){
    					is_first=true;
    						if(form.getTCProperty("s4tissue14").getStringValue()==null ||"".equals(form.getTCProperty("s4tissue14").getStringValue()))
    	    				{}
    	    				else
    						   zzdmiTextField1.setSelectedItem(form.getTCProperty("s4tissue14").getStringValue());
//    						zzdmiTextField1.setSelectedItem(form.getTCProperty("s4tissue14").getStringValue());
    				}
    			} else if(str.equals("s4Default_Buyer")){
    				if(!"Y".equals(cdzt_str)){
    					mrcgyiTextField1.setSelectedItem(formProperties[i].getStringValue());	
    				}
    			} else if(str.equals("s4Planner")){                         //计划员编号
    				if(!"Y".equals(cdzt_str)){
    					System.out.println("formProperties[i]==>"+formProperties[i].getStringValue());
    					String str1=formProperties[i].getStringValue();
    					String str2=str1.split("\\*")[0];
    					System.out.println("str2==>"+str2);
    					jihyiTextField1.setEditable(true);
    					jihyiTextField1.setSelectedItem(str2);	
    					jihyiTextField1.setEditable(false);
    				}
    			} else if(str.equals("s4Fixed_Lead_Time")){
    				if(!"Y".equals(cdzt_str)){
    					tqq_gdiTextField1.setText(formProperties[i].getStringValue());	
    				}
    			} else if(str.equals("s4SAC_Inventory_c")){
    			//	if(!"Y".equals(cdzt_str)){
    					//kclbjiTextField1.setSelectedItem(formProperties[i].getStringValue().split("\\*")[0]);	
    					kclbjiTextField1.setEditable(true);
    					kclbjiTextField1.setSelectedItem(formProperties[i].getStringValue());
    					kclbjiTextField1.setEditable(false);
    			//	}
    			
    			} else if(str.equals("s4SAC_Financial_c")){
    				if(!"Y".equals(cdzt_str)){										//对财务类别集进行的修改
    				//	cwlbjiTextField1.setSelectedItem(formProperties[i].getStringValue());	
    					if((formProperties[i].getStringValue()==null)||"".equals(formProperties[i].getStringValue()))
    					{
    						cwlbjiTextField1.setEditable(true);
    						cwlbjiTextField1.setSelectedItem("01.0");
    						cwlbjiTextField1.setEditable(false);
    					}
    					else
    					{
    						//cwlbjiTextField1.setSelectedItem(formProperties[i].getStringValue().split("\\*")[0]);	
    						cwlbjiTextField1.setEditable(true);
    						cwlbjiTextField1.setSelectedItem(formProperties[i].getStringValue());
    						cwlbjiTextField1.setEditable(false);
    					}
    				}
    			} else if(str.equals("s4SAC_Plan_c")){								//对计划类别集进行的修改
    				if(!"Y".equals(cdzt_str)){												
    					//jhlbjiTextField1.setSelectedItem(formProperties[i].getStringValue());
    					
    					if((formProperties[i].getStringValue()==null)||"".equals(formProperties[i].getStringValue()))
    					{
    						if(form.getType().equals("S4DQDMRevisionMaster")||form.getType().equals("S4RKDMRevisionMaster")){
    						jhlbjiTextField1.setEditable(true);
    						jhlbjiTextField1.setSelectedItem("MZ0.W0");
    						jhlbjiTextField1.setEditable(false);
    					     }
    					}
    					else
    					{
    						jhlbjiTextField1.setEditable(true);
    						jhlbjiTextField1.setSelectedItem(formProperties[i].getStringValue().split("\\*")[0]);	
    						jhlbjiTextField1.setEditable(false);
    					}
    					
    				}
    			} else if(str.equals("s4SAC_Pro_c")){
    				if(!"Y".equals(cdzt_str)){
    					cplbjiTextField1.setEditable(true);
    					cplbjiTextField1.setSelectedItem(formProperties[i].getStringValue());	
    					cplbjiTextField1.setEditable(false);
    				}
    			} else if(str.equals("s4Childstock")){
    				if(!"Y".equals(cdzt_str)){
    					System.out.println("********组织一子库存*********"+form.getTCProperty("s4Childstock").getStringValue());
    					zkciTextField1.setEditable(true);
    					zkciTextField1.setSelectedItem(form.getTCProperty("s4Childstock").getStringValue());
    					zkciTextField1.setEditable(false);
    					System.out.println("********组织一子库存11111*********"+form.getTCProperty("s4Childstock").getStringValue());
    				}
    			} else if(str.equals("s4cargo_s")){
    				if(!"Y".equals(cdzt_str)){
    					hwiTextField1.setText(formProperties[i].getStringValue());	
    				}
    			} else if(str.equals("s4Fixed_Days_Supply")){
    				if(!"Y".equals(cdzt_str)){
    					gdgytsiTextField1.setText(formProperties[i].getStringValue());	
    				}
    			} else if(str.equals("s4Fixed_Lot_Size_M")){
    				if(!"Y".equals(cdzt_str)){
    					gdplzjiTextField1.setText(formProperties[i].getStringValue());	
    				}
    			} else if(str.equals("s4Inventory_Planning_M")){
    				if(!"Y".equals(cdzt_str)){
    					kcjhffiTextField1.setSelectedItem(formProperties[i].getStringValue());	
    				}
    			} else if(str.equals("s4List_Price")){
    				if(!"Y".equals(cdzt_str)){
    					jmbjgiTextField1.setText(formProperties[i].getStringValue());	
    				}
    			} else if(str.equals("s4Weight_Unit_of_Mea")){
    				if(!"Y".equals(cdzt_str)){
    					zldwiTextField1.setSelectedItem(formProperties[i].getStringValue());	
    				}
    			}  else if(str.equals("s4Unit_Weight")){
    				if(!"Y".equals(cdzt_str)){
    					dwzliTextField1.setText(formProperties[i].getStringValue());	
    				}
    			}  else if(str.equals("s4Volume_Unit_of_Mea")){
    				if(!"Y".equals(cdzt_str)){
    					tjdwiTextField1.setSelectedItem(formProperties[i].getStringValue());	
    				}
    			}  else if(str.equals("s4Unit_Volume")){
    				if(!"Y".equals(cdzt_str)){
    					dwtjiTextField1.setText(formProperties[i].getStringValue());	
    				}
    			} else if(str.equals("s4tissue15")){
    				if(!"Y".equals(cdzt_str)){
    					is_first0=true;
    					zzdmiTextField2.setSelectedItem(form.getTCProperty("s4tissue15").getStringValue());	//组织代码2
    				}
    			} else if(str.equals("s4Default_Buyer1")){
    				if(!"Y".equals(cdzt_str)){
    					mrcgyiTextField2.setSelectedItem(formProperties[i].getStringValue());	
    				}
    			} else if(str.equals("s4Planner1")){
    				if(!"Y".equals(cdzt_str)){
    					jihyiTextField2.setEditable(true);
    					jihyiTextField2.setSelectedItem(formProperties[i].getStringValue());	
    					jihyiTextField2.setEditable(false);
    				}
    			} else if(str.equals("s4Fixed_Lead_Time1")){
    				if(!"Y".equals(cdzt_str)){
    					tqq_gdiTextField2.setText(formProperties[i].getStringValue());	
    				}
    			} /*else if(str.equals("s4SAC_Inventory_c1")){
    				if(!"Y".equals(cdzt_str)){
    					kclbjiTextField2.setEditable(true);
    					kclbjiTextField2.setSelectedItem(formProperties[i].getStringValue().split("\\*")[0]);	
    					kclbjiTextField2.setEditable(false);
    				}
    			}*/ else if(str.equals("s4SAC_Financial_c1")){                     //对财务类别集进行的修改
    				if(!"Y".equals(cdzt_str)){
    					//cwlbjiTextField2.setSelectedItem(formProperties[i].getStringValue());	
    				/*	if((formProperties[i].getStringValue()==null)||"".equals(formProperties[i].getStringValue()))
    					{
    						cwlbjiTextField2.setEditable(true);
    						cwlbjiTextField2.setSelectedItem("01.0");
    						cwlbjiTextField2.setEditable(false);
    					}
    					else
    					{
    					*/	cwlbjiTextField2.setEditable(true);
    						cwlbjiTextField2.setSelectedItem(formProperties[i].getStringValue().split("\\*")[0]);
    						cwlbjiTextField2.setEditable(false);
    				/*	}*/
    				}
    			} else if(str.equals("s4SAC_Plan_c1")){
    				if(!"Y".equals(cdzt_str)){
    					//jhlbjiTextField2.setSelectedItem(formProperties[i].getStringValue());
    				/*	if((formProperties[i].getStringValue()==null)||"".equals(formProperties[i].getStringValue()))
    					{
    						jhlbjiTextField2.setEditable(true);
    						jhlbjiTextField2.setSelectedItem("MZ0.W0");
    						jhlbjiTextField2.setEditable(false);
    					}
    					else
    					{*/
    						jhlbjiTextField2.setEditable(true);
    						jhlbjiTextField2.setSelectedItem(formProperties[i].getStringValue().split("\\*")[0]);
    						jhlbjiTextField2.setEditable(false);
    				/*	}*/
    				
    				}
    			} else if(str.equals("s4SAC_Pro_c1")){
    				if(!"Y".equals(cdzt_str)){
    					cplbjiTextField2.setEditable(true);
    					cplbjiTextField2.setSelectedItem(formProperties[i].getStringValue());	
    					cplbjiTextField2.setEditable(false);
    				}
    			} else if(str.equals("s4Childstock1")){
    				if(!"Y".equals(cdzt_str)){    					
    					System.out.println("********组织二子库存*********"+form.getTCProperty("s4Childstock1").getStringValue());
    					zkciTextField2.setEditable(true);
    					zkciTextField2.setSelectedItem(form.getTCProperty("s4Childstock1").getStringValue());
    					System.out.println("********组织二子库存22222*********"+zkciTextField2.getSelectedItem().toString());
    					zkciTextField2.setEditable(false);
    				}
    			} else if(str.equals("s4cargo_s1")){
    				if(!"Y".equals(cdzt_str)){
    					hwiTextField2.setText(formProperties[i].getStringValue());	
    				}
    			} else if(str.equals("s4Fixed_Days_Supply1")){
    				if(!"Y".equals(cdzt_str)){
    					gdgytsiTextField2.setText(formProperties[i].getStringValue());	
    				}
    			} else if(str.equals("s4Fixed_Lot_Size_M1")){
    				if(!"Y".equals(cdzt_str)){
    					gdplzjiTextField2.setText(formProperties[i].getStringValue());	
    				}
    			} else if(str.equals("s4Inventory_Planning_M1")){
    				if(!"Y".equals(cdzt_str)){
    					kcjhffiTextField2.setSelectedItem(formProperties[i].getStringValue());	
    				}
    			} else if(str.equals("s4List_Price1")){
    				if(!"Y".equals(cdzt_str)){
    					jmbjgiTextField2.setText(formProperties[i].getStringValue());	
    				}
    			} else if(str.equals("s4Weight_Unit_of_Mea1")){
    				if(!"Y".equals(cdzt_str)){
    					zldwiTextField2.setSelectedItem(formProperties[i].getStringValue());	
    				}
    			}  else if(str.equals("s4Unit_Weight1")){
    				if(!"Y".equals(cdzt_str)){
    					dwzliTextField2.setText(formProperties[i].getStringValue());	
    				}
    			}  else if(str.equals("s4Volume_Unit_of_Mea1")){
    				if(!"Y".equals(cdzt_str)){
    					tjdwiTextField2.setSelectedItem(formProperties[i].getStringValue());	
    				}
    			}  else if(str.equals("s4Unit_Volume1")){
    				if(!"Y".equals(cdzt_str)){
    					dwtjiTextField2.setText(formProperties[i].getStringValue());	
    				}
    			}
    			else if(str.equals("s4Materialid")){
    				if(formProperties[i].getStringValue().equals("")||formProperties[i].getStringValue()==null){
    					try {
    						wlbmiTextField.setText(getItemId());
    					} catch (TCException e1) {
    						// TODO Auto-generated catch block
    						e1.printStackTrace();
    					}
    					}
    					else
    					wlbmiTextField.setText(formProperties[i].getStringValue());	
    					
    					wlbmiTextField.setEnabled(false);
    			}
    	        
    			
    			
    		
    		/*if("Y".equals(cdzt_str)){
    			if(zydwiTextField.getComponentCount()>0){
    				int a = zydwiTextField.getMouseListeners().length;
    				System.out.println("a->:"+a);
    				zydwiTextField.getComponent(0).removeMouseListener(zydwiTextField.getMouseListeners()[0]);
    				zydwiTextField.removeMouseListener(zydwiTextField.getMouseListeners()[0]);
    			}
    			zydwiTextField.getComponent(0).removeMouseListener(zydwiTextField.getMouseListeners()[0]);
    			zydwiTextField.removeMouseListener(zydwiTextField.getMouseListeners()[0]);
    			sfyxwliTextField.getComponent(0).removeMouseListener(sfyxwliTextField.getMouseListeners()[0]);
    			sfyxwliTextField.removeMouseListener(sfyxwliTextField.getMouseListeners()[0]);
    		}*/
    		
    		if (wlztiTextField.getSelectedItem().equals("")) {
    			wlztiTextField.setSelectedItem("有效");
    		}
    		}
    	} catch (TCException tcexception) {
    		throw tcexception;
    	}
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
				if (jcomponent_type.indexOf("SACJTextField32") > 0) {
					SACJTextField32 textfield = (SACJTextField32) value;
					String str_com = textfield.getText().toString();
					if(str_com == null || "".equals(str_com)){
						vec_t.add(info_name);
					}
				} else if(jcomponent_type.indexOf("JComboBox") > 0){
					JComboBox combomx = (JComboBox) value;
					String str_com = combomx.getSelectedItem().toString();
					if(str_com == null || "".equals(str_com)){
						vec_t.add(info_name);
					}
				} else if(jcomponent_type.indexOf("SACJTextField160") > 0){
					SACJTextField160 textfield = (SACJTextField160) value;
					String str_com = textfield.getText().toString();
					if(str_com == null || "".equals(str_com)){
						vec_t.add(info_name);
					}
				}
			}
		}
		
		if(vec_t.size()>0){
			MessageBox.post(vec_t+"这些属性不能为空,请检查", "提示",
					MessageBox.INFORMATION);
			return false;
		}
		if(form.getType().equals("S4DQDMRevisionMaster")||form.getType().equals("S4RKDMRevisionMaster")){
		String contactNum=null;
		try {
			contactNum =getContactNum();
		} catch (TCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(contactNum.equals("")||contactNum==null)
		{
			MessageBox.post("请将该物料放在工程项目文件夹下,否则描述属性不带合同号,不能维护属性!", "提示",MessageBox.INFORMATION);
			return false;
		}
		else if(contactNum.equals("ERROR"))
		{
			MessageBox.post("同一个工程的一次性物料不允许存在于多个合同中，请手动处理", "提示",MessageBox.INFORMATION);
			return false;
		}
		else {
			try {
				setContactNum(contactNum);
			} catch (TCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		if (!isAllEmptyField()) {
			System.out.println("-----2-----");
			

			if (isEmpty(zzdmiTextField2.getSelectedItem().toString())
					|| isEmpty(cwlbjiTextField2.getSelectedItem().toString())
					|| isEmpty(jhlbjiTextField2.getSelectedItem().toString())) {

    	   		MessageBox.post("组织2中带 *号字段为必填项", "提示", MessageBox.INFORMATION);
    	   		return false;
			}
		
		}
		/*if(!"Y".equals(cdzt_str)){
			//判断组织1和组织2中的组织代码是否相同
			String zzdm1_str = zzdmiTextField1.getSelectedItem().toString();
			String zzdm2_str = zzdmiTextField2.getSelectedItem().toString();
			if(zzdm1_str.equals(zzdm2_str)){
				MessageBox.post("组织1和组织2中的组织代码相同,请检查", "提示",
						MessageBox.INFORMATION);
				return false;
				
			}
		}*/
		/*//在物料修改的时候控制一些属性不能修改
		try {
			//如果已经传递过ERP,控制主组织上面两属性值不能修改
			if("Y".equals(cdzt_str)){
				String zydw = form.getProperty(attri2).toString();
				System.out.println("zydw==---=>:"+zydw);
				String sfyxwl = form.getProperty(attri3).toString();
				System.out.println("sfyxwl==--sfyxwl-=>:"+sfyxwl);
				String zydw_combox = zydwiTextField.getSelectedItem().toString();
				System.out.println("zydw_combox---->:"+zydw_combox);
				String sfyxwl_combox = sfyxwliTextField.getSelectedItem().toString();
				System.out.println("sfyxwl_combox--====-->:"+sfyxwl_combox);
				String kcwl = form.getProperty(attri4).toString();
				boolean kcwl_flag = false;
				if("Y".equals(kcwl)){
					kcwl_flag = true;
				}
				boolean kcwl_checkbox = kcwliTextField.isSelected();
				String kcwl_flag = "";
				if(kcwl_checkbox){
					kcwl_flag = "Y";
				}else{
					kcwl_flag = "N";
				}
				if(!zydw.equals(zydw_combox) || !sfyxwl.equals(sfyxwl_combox) || !kcwl.equals(kcwl_flag)){
					MessageBox.post("不能修改主要单位或者是否原型物料或者库存物料的值,请检查", "提示",
							MessageBox.INFORMATION);
					return false;
				}
    		}
			
		} catch (TCException e) {
			e.printStackTrace();
		}*/
    	
    	return super.isFormSavable(arg0);
    	
    }
    
    
    /*
     * Form保存数据
     * 
     * */
    public void saveForm() {
    	
    	try {

 //   		String contactNum=getContactNum();
 //  		System.out.println("************合同号***************"+contactNum);
 //   		if(contactNum.equals("")||contactNum==null)
 //   		{
 //   			MessageBox.post("该物料不在工程项目文件夹下，不带有合同号", "提示",MessageBox.INFORMATION);
 //   			return;
 //   		}
    		
    		int k = formProperties.length;
			for (int i = 0; i < k; i++) {
				String str = formProperties[i].getPropertyName();
				if(str.equals("s4Materialt")){
					formProperties[i].setStringValueData(wlmbiTextField.getSelectedItem().toString());
    			} else if(str.equals("s4Mdescription")){
    				if(form.getType().equals("S4DQDMRevisionMaster")||form.getType().equals("S4RKDMRevisionMaster")){
    				      if(wlmsiTextField.getText().endsWith("("+getContactNum()+")"))
    				         {
    					      formProperties[i].setStringValueData(wlmsiTextField.getText());
    				         }
    				      else
    				        formProperties[i].setStringValueData(wlmsiTextField.getText()+"("+getContactNum()+")");
    				}
    				else     
    					formProperties[i].setStringValueData(wlmsiTextField.getText());
    			} else if(str.equals("s4Item_Status")){
    				formProperties[i].setStringValueData(wlztiTextField.getSelectedItem().toString());
    			} else if(str.equals("s4vendor")){
    				formProperties[i].setStringValueData(sccsiTextField.getText());
    			} else if(str.equals("s4contact_maker")){
    				formProperties[i].setStringValueData(dhdmiTextField.getText());
    			} else if(str.equals("s4Supply_vol")){
    				formProperties[i].setStringValueData(dydyiTextField.getText());
    			} else if(str.equals("s4CT_current")){
    				formProperties[i].setStringValueData(ctdianliTextField.getText());
    			} /*else if(str.equals("s4Inventory_Item")){
    				if(kcwliTextField.isSelected()){
    					formProperties[i].setStringValueData(Yes);
    				}else{
    					formProperties[i].setStringValueData(No);
    				}
    			} */else if(str.equals("s4Primary_Unit_of_M")){
    				formProperties[i].setStringValueData(zydwiTextField.getSelectedItem().toString());
    			} else if(str.equals("s4Allow_Description_U")){
    				if(yxgxsmiTextField.isSelected()){
    					formProperties[i].setStringValueData(Yes);
    				}else{
    					formProperties[i].setStringValueData(No);
    				}
    			} else if(str.equals("s4opernumber")){
    				formProperties[i].setStringValueData(gxhiTextField.getText());
    			} else if(str.equals("s4Wpromaterials")){
    				formProperties[i].setStringValueData(sfyxwliTextField.getSelectedItem().toString());
    			} else if(str.equals("s4tissue14")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(zzdmiTextField1.getSelectedItem().toString());
    				}
    			} else if(str.equals("s4Default_Buyer")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(mrcgyiTextField1.getSelectedItem().toString());
    				}
    			} else if(str.equals("s4Planner")){
    				if(!"Y".equals(cdzt_str)){
    					if(jihyiTextField1.getSelectedItem()==null){
    					formProperties[i].setStringValueData("");
					}
					else
    					formProperties[i].setStringValueData(jihyiTextField1.getSelectedItem().toString());
    				}
    			} else if(str.equals("s4Fixed_Lead_Time")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(tqq_gdiTextField1.getText());
    				}
    			} else if(str.equals("s4SAC_Inventory_c")){
    				if(!"Y".equals(cdzt_str)){
    					System.out.println("22=======>:"+kclbjiTextField1.getSelectedItem().toString());
    					System.out.println("33333333333333=======>:"+kclbjiTextField1.getSelectedItem().toString().getBytes().length);
    					formProperties[i].setStringValueData(kclbjiTextField1.getSelectedItem().toString());
    				}
    			} else if(str.equals("s4SAC_Financial_c")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(cwlbjiTextField1.getSelectedItem().toString());
    				}
    			} else if(str.equals("s4SAC_Plan_c")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(jhlbjiTextField1.getSelectedItem().toString());
    				}
    			} else if(str.equals("s4SAC_Pro_c")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(cplbjiTextField1.getSelectedItem().toString());
    				}
    			} else if(str.equals("s4Childstock")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(zkciTextField1.getSelectedItem().toString());
    				}
    			} /*else if(str.equals("s4cargo_s")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(hwiTextField1.getText());
    				}
    			} */else if(str.equals("s4Fixed_Days_Supply")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(gdgytsiTextField1.getText());
    				}
    			} else if(str.equals("s4Fixed_Lot_Size_M")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(gdplzjiTextField1.getText());
    				}
    			} else if(str.equals("s4Inventory_Planning_M")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(kcjhffiTextField1.getSelectedItem().toString());
    				}
    			} else if(str.equals("s4List_Price")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(jmbjgiTextField1.getText());
    				}
    			} else if(str.equals("s4Weight_Unit_of_Mea")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(zldwiTextField1.getSelectedItem().toString());
    				}
    			}  else if(str.equals("s4Unit_Weight")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(dwzliTextField1.getText());
    				}
    			}  else if(str.equals("s4Volume_Unit_of_Mea")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(tjdwiTextField1.getSelectedItem().toString());
    				}
    			}  else if(str.equals("s4Unit_Volume")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(dwtjiTextField1.getText());
    				}
    			}  else if(str.equals("s4tissue15")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(zzdmiTextField2.getSelectedItem().toString());
    				}
    			} else if(str.equals("s4Default_Buyer1")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(mrcgyiTextField2.getSelectedItem().toString());
    				}
    			} else if(str.equals("s4Planner1")){
    				if(!"Y".equals(cdzt_str)){
    					if(jihyiTextField2.getSelectedItem()==null){
    						formProperties[i].setStringValueData("");
    					}
    					else
    					formProperties[i].setStringValueData(jihyiTextField2.getSelectedItem().toString());
    				}
    			} else if(str.equals("s4Fixed_Lead_Time1")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(tqq_gdiTextField2.getText());
    				}
    			}/* else if(str.equals("s4SAC_Inventory_c1")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(kclbjiTextField2.getSelectedItem().toString());
    				}
    			}*/ else if(str.equals("s4SAC_Financial_c1")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(cwlbjiTextField2.getSelectedItem().toString());
    				}
    			} else if(str.equals("s4SAC_Plan_c1")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(jhlbjiTextField2.getSelectedItem().toString());
    				}
    			} else if(str.equals("s4SAC_Pro_c1")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(cplbjiTextField2.getSelectedItem().toString());
    				}
    			} else if(str.equals("s4Childstock1")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(zkciTextField2.getSelectedItem().toString());
    				}
    			} /*else if(str.equals("s4cargo_s1")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(hwiTextField2.getText());
    				}
    			} */else if(str.equals("s4Fixed_Days_Supply1")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(gdgytsiTextField2.getText());
    				}
    			} else if(str.equals("s4Fixed_Lot_Size_M1")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(gdplzjiTextField2.getText());
    				}
    			} else if(str.equals("s4Inventory_Planning_M1")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(kcjhffiTextField2.getSelectedItem().toString());
    				}
    			} else if(str.equals("s4List_Price1")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(jmbjgiTextField2.getText());
    				}
    			} else if(str.equals("s4Weight_Unit_of_Mea1")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(zldwiTextField2.getSelectedItem().toString());
    				}
    			}  else if(str.equals("s4Unit_Weight1")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(dwzliTextField2.getText());
    				}
    			}  else if(str.equals("s4Volume_Unit_of_Mea1")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(tjdwiTextField2.getSelectedItem().toString());
    				}
    			}  else if(str.equals("s4Unit_Volume1")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(dwtjiTextField2.getText());
    				}
    			}  
    			else if(str.equals("s4Materialid")){
    				if(!"Y".equals(cdzt_str)){
    					formProperties[i].setStringValueData(wlbmiTextField.getText());
    				}
    			}
				
			
			}
			form.setTCProperties(formProperties);
		} catch (Exception exception) {
				exception.printStackTrace();
				MessageBox.post(exception);
			}
   }
    
    private JPanel buildMainInformation(){
    	JPanel all_panel = new JPanel(new GridLayout(1, 0));
    	color = all_panel.getBackground();
    	TitledBorder titleBorder = BorderFactory.createTitledBorder("维护属性");
    	titleBorder.setTitlePosition(2);
    	titleBorder.setTitleFont(font);
		all_panel.setBorder(titleBorder);
		all_panel.setLayout(new BoxLayout(all_panel, BoxLayout.Y_AXIS));
    	JPanel jpanel = new JPanel(new PropertyLayout());
    	JPanel jpanel2 = new JPanel(new PropertyLayout());
    	JPanel jpanel3 = new JPanel(new PropertyLayout());
    	//ly--
    	JLabel wlbm = new JLabel("物料编码");
    	wlbmiTextField =new SACJTextField32(35);
    	//--ly
    	JLabel wlms = new JLabel("物料描述*");
    	wlmsiTextField = new SACJTextField160(35);
    	/*//ly--
		SACDocument docwlmsiTextField=new SACDocument();
		docwlmsiTextField.setMaxLength(512);
		wlmsiTextField.setDocument(docwlmsiTextField);
		//--ly
*/    	component_map.put("物料描述", wlmsiTextField);
    	JLabel wlzt = new JLabel("物料状态*");
    	wlztiTextField = new JComboBox(wlzt_str);
    	wlztiTextField.setPreferredSize(new Dimension(216,23));
    	component_map.put("物料状态", wlztiTextField);
    	JLabel sccs = new JLabel("生产厂商");
    	sccsiTextField = new SACJTextField32(35);
    	JLabel dhdm = new JLabel("订货代码");
    	dhdmiTextField = new SACJTextField32(35);
    	JLabel dydy = new JLabel("电源电压");
    	dydyiTextField = new SACJTextField32(35);
    	JLabel kclbj = new JLabel("SAC_库存类别集*");
    	if(form.getType().equals("S4DQDMRevisionMaster")||form.getType().equals("S4RKDMRevisionMaster"))
    	{
    		kclbjiTextField1 = new JComboBox(kc_str);
    	}
    	else
    		kclbjiTextField1 = new JComboBox(kclbj_str);
    	kclbjiTextField1.setPreferredSize(new Dimension(216,23));
    	kclbjiTextField1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(kclbjiTextField1.getSelectedItem().toString().contains("*")){
					String s1=kclbjiTextField1.getSelectedItem().toString();
					String s2=s1.split("\\*")[0];
					kclbjiTextField1.setEditable(true);
					kclbjiTextField1.setSelectedItem(s2);
					kclbjiTextField1.setEditable(false);
				}
			}
    		
    	});
    	component_map.put("SAC_库存类别集", kclbjiTextField1);
    	
    	JLabel ctdianl = new JLabel("CT电流");
    	ctdianliTextField = new SACJTextField32(35);
    	JLabel kcwl = new JLabel("库存物料");
    	kcwliTextField = new JCheckBox();
    	JLabel zydw = new JLabel("主要单位*");
    	zydwiTextField = new JComboBox(dw_str);
    	component_map.put("主要单位", zydwiTextField);
    	zydwiTextField.setPreferredSize(new Dimension(216,23));
    	JLabel yxgxsm = new JLabel("允许更新说明");
    	yxgxsmiTextField = new JCheckBox();
    	JLabel wlmb = new JLabel("物料模板*");
    	wlmbiTextField = new JComboBox(wlmb_str);
    	wlmbiTextField.setPreferredSize(new Dimension(216,23));
    	wlmbiTextField.setSelectedItem("SAC外购件(推式)");
    	component_map.put("物料模板", wlmbiTextField);
    	JLabel gxh = new JLabel("工序号");
    	gxhiTextField = new SACJTextField32(35);
    	JLabel sfyxwl = new JLabel("是否原型物料*");
    	sfyxwliTextField = new JComboBox(sfyxwl_str);
    	sfyxwliTextField.setPreferredSize(new Dimension(216,23));
    	
    	jpanel2.add("1.1.right",new JLabel(" "));
    	jpanel2.add("2.1.right",wlbm);
    	jpanel2.add("2.2.right",wlbmiTextField);
    	jpanel2.add("3.1.right",wlmb);
    	jpanel2.add("3.2.right",wlmbiTextField);
    	jpanel2.add("4.1.right",wlms);
    	jpanel2.add("4.2.right",wlmsiTextField);
    	jpanel2.add("5.1.right",wlzt);
    	jpanel2.add("5.2.right",wlztiTextField);
    	jpanel2.add("6.1.right",sccs);
    	jpanel2.add("6.2.right",sccsiTextField);
    	jpanel2.add("7.1.right",dhdm);
    	jpanel2.add("7.2.right",dhdmiTextField);
    	jpanel2.add("8.1.right",dydy);
    	jpanel2.add("8.2.right",dydyiTextField);
//   	jpanel2.add("8.1.right",kclbj);
//    	jpanel2.add("8.2.right",kclbjiTextField1);
    	
    	
    	jpanel3.add("1.1.right",new JLabel(" "));
    	jpanel3.add("2.1.right",ctdianl);
    	jpanel3.add("2.2.right",ctdianliTextField);
    	jpanel3.add("3.1.right",zydw);
    	jpanel3.add("3.2.right",zydwiTextField);
    	jpanel3.add("4.1.right",yxgxsm);
    	jpanel3.add("4.2.right",yxgxsmiTextField);
 //   	jpanel3.add("5.1.right",kcwl);
 //   	jpanel3.add("5.2.right",kcwliTextField);
    	jpanel3.add("5.1.right",kclbj);
    	jpanel3.add("5.2.right",kclbjiTextField1);
    	
    	jpanel3.add("6.1.right",gxh);
    	jpanel3.add("6.2.right",gxhiTextField);
    	jpanel3.add("7.1.right",sfyxwl);
    	jpanel3.add("7.2.right",sfyxwliTextField);
    	
    	jpanel.add("1.1.right",new JLabel("       "));
    	jpanel.add("1.2.right",jpanel2);
    	jpanel.add("1.3.right",new JLabel("       "));
    	jpanel.add("1.4.right",jpanel3);
    	jpanel.add("1.5.right",new JLabel("       "));
    	all_panel.add(jpanel);
    	return all_panel;
    }

    private JPanel buildZuZhi1Information(){                               //组织代码1
    	JPanel all_panel = new JPanel(new GridLayout(1, 0));
    	TitledBorder titleBorder = BorderFactory.createTitledBorder("维护属性");
    	titleBorder.setTitlePosition(2);
    	titleBorder.setTitleFont(font);
		all_panel.setBorder(titleBorder);
		all_panel.setLayout(new BoxLayout(all_panel, BoxLayout.Y_AXIS));
    	JPanel jpanel = new JPanel(new PropertyLayout());
    	JPanel jpanel2 = new JPanel(new PropertyLayout());
    	JPanel jpanel3 = new JPanel(new PropertyLayout());
    	JLabel zzdm = new JLabel("组织代码*");
    	zzdmiTextField1 = new JComboBox(zzdm_str);
    	zzdmiTextField1.setPreferredSize(new Dimension(216,23));
    	if(form.getType().equals("S4DQDMRevisionMaster")||form.getType().equals("S4RKDMRevisionMaster")){
		      zzdmiTextField1.setSelectedItem("MZ0"); //组织代码1
		}
    	zzdmiTextField1.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
        	   	str1=zzdmiTextField1.getSelectedItem().toString();	
        	   	System.out.println("str1==>"+str1);
        	   	if(str1.equals(str2)&&str1!=null&&str2!=null&&!"".equals(str1)&&!"".equals(str2))
        	   	{
        	   		MessageBox.post("与组织2的组织代码重复！", "提示", MessageBox.INFORMATION);
        	   		zzdmiTextField1.setSelectedItem("");
        	   	}
        	   	
        	   	if (zzdmiTextField1.getSelectedItem().equals("")) {
		        	zkciTextField1.removeAllItems();
		        	zkciTextField1.addItem("");
		        	zkciTextField1.setSelectedIndex(0);
		        }
        	   	if(zzdmiTextField1.getSelectedItem().toString().trim().equals("P31")){
        	    //	jihyiTextField1 = new JComboBox(jhybh_str);    //计划员编号
        	   		jihyiTextField1.removeAllItems();
        	    	for(int i=0;i<jhybh_str.length;i++){
        	    		jihyiTextField1.addItem(jhybh_str[i]);//计划员编号
        	    	}
        	    }
        	    else 
        	    	{
        	    	jihyiTextField1.removeAllItems();
        	    	}
				for(int i=0;i<optionKeys.length;i++)
				{
					
					if(zzdmiTextField1.getSelectedItem().equals(optionKeys[i].split("=")[0].trim()))
					{
						zkciTextField1.removeAllItems();
						System.out.println("**********1******");
						zkciTextField1.addItem("");
						String[] kczz = optionKeys[i].split("=")[1].split(",");
						for (int j=0; j<kczz.length; j++) {  							
						//	System.out.println(kczz[j]);
							zkciTextField1.addItem( kczz[j].trim());
						}
						zkciTextField1.setSelectedIndex(0);
						System.out.println("**********1-end******");
						}
					else{}
						}
    		}
    	  }
   		);
    	
    	component_map.put("组织代码", zzdmiTextField1);
    	JLabel mrcgy = new JLabel("默认采购员");
    	Arrays.sort(mrcgy_str);
    	mrcgyiTextField1 = new JAutoCompleteComboBox(mrcgy_str);
    	mrcgyiTextField1.setSelectedIndex(-1);
    	mrcgyiTextField1.setPreferredSize(new Dimension(216,23));
    	JLabel jihy = new JLabel("计划员编号");
    	jihyiTextField1 = new JComboBox();    //计划员编号
    	jihyiTextField1.setPreferredSize(new Dimension(216,23));
    	jihyiTextField1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(jihyiTextField1.getSelectedItem()==null){}
				else if(jihyiTextField1.getSelectedItem().toString().contains("*")){
					String s1=jihyiTextField1.getSelectedItem().toString();
					String s2=s1.split("\\*")[0];
					jihyiTextField1.setEditable(true);
					jihyiTextField1.setSelectedItem(s2);
					jihyiTextField1.setEditable(false);
				}
				
			}
    		
    		
    	});
 /*
    	jihyiTextField1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(jihyiTextField1.getSelectedItem().toString().contains("*")){
					String s1=jihyiTextField1.getSelectedItem().toString();
					String s2=s1.split("\\*")[0];
					jihyiTextField1.setEditable(true);
					jihyiTextField1.setSelectedItem(s2);
					jihyiTextField1.setEditable(false);
				}
			}
    		
    		
    	});
  */  	
    	JLabel tqq_gd = new JLabel("固定提前期");
    	tqq_gdiTextField1 = new iTextField(35);
    	doc_gdtqq = new SACMyDocument();
    	tqq_gdiTextField1.setDocument(doc_gdtqq);
    	doc_gdtqq.setMaxLength(32);
    	doc_gdtqq.setCharLimit(allow_num);		
    	JLabel cwlbj = new JLabel("SAC_财务类别集*");
    	cwlbjiTextField1 = new JComboBox(cwlbj_str);
    	cwlbjiTextField1.setPreferredSize(new Dimension(216,23));
    	cwlbjiTextField1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(cwlbjiTextField1.getSelectedItem().toString().contains("*")){
					String s1=cwlbjiTextField1.getSelectedItem().toString();
					String s2=s1.split("\\*")[0];
					cwlbjiTextField1.setEditable(true);
					cwlbjiTextField1.setSelectedItem(s2);
					cwlbjiTextField1.setEditable(false);
				}
				
			}
    		
    	});
    	component_map.put("SAC_财务类别集", cwlbjiTextField1);
    	JLabel jhlbj = new JLabel("SAC_计划类别集*");
    	jhlbjiTextField1 = new JComboBox(jhlbj_str);
    	jhlbjiTextField1.setPreferredSize(new Dimension(216,23));
    	jhlbjiTextField1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(jhlbjiTextField1.getSelectedItem().toString().contains("*")){
					String s1=jhlbjiTextField1.getSelectedItem().toString();
					String s2=s1.split("\\*")[0];
					jhlbjiTextField1.setEditable(true);
					jhlbjiTextField1.setSelectedItem(s2);
					jhlbjiTextField1.setEditable(false);
				}
			}
    		
    	});
    	component_map.put("SAC_计划类别集", jhlbjiTextField1);
    	JLabel cplbj = new JLabel("SAC_产品类别集");
    	cplbjiTextField1 = new JComboBox(cplbj_str);
    	cplbjiTextField1.setPreferredSize(new Dimension(216,23));
    	cplbjiTextField1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(cplbjiTextField1.getSelectedItem().toString().contains("*")){
					String s1=cplbjiTextField1.getSelectedItem().toString();
					String s2=s1.split("\\*")[0];
					cplbjiTextField1.setEditable(true);
					cplbjiTextField1.setSelectedItem(s2);
					cplbjiTextField1.setEditable(false);
				}
			}
    		
    	});
    	
    	JLabel zkc = new JLabel("供应子库存");
    	zkciTextField1 = new JComboBox(zkc_str);
    	zkciTextField1.setSelectedItem("");
    	zkciTextField1.setPreferredSize(new Dimension(216,23));
    	zkciTextField1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(zkciTextField1.getSelectedItem()==null){}
				else if(zkciTextField1.getSelectedItem().toString().contains("*")){
					String s1=zkciTextField1.getSelectedItem().toString();
					String s2=s1.split("\\*")[0];
					zkciTextField1.setEditable(true);
					zkciTextField1.setSelectedItem(s2);
					zkciTextField1.setEditable(false);
				}
			}
    	});

    	JLabel hw = new JLabel("货位");
    	hwiTextField1 = new SACJTextField32(35);
    	hwiTextField1.setEnabled(false);
    	JLabel gdgyts = new JLabel("固定供应天数");
    	gdgytsiTextField1 = new iTextField(35);
    	doc_gdgyts = new SACMyDocument();
    	gdgytsiTextField1.setDocument(doc_gdgyts);
    	doc_gdgyts.setMaxLength(32);
    	doc_gdgyts.setCharLimit(allow_num);
    	JLabel gdplzj = new JLabel("固定批量增加");
    	gdplzjiTextField1 = new iTextField(35);
    	doc_gdplzj = new SACMyDocument();
    	gdplzjiTextField1.setDocument(doc_gdplzj);
    	doc_gdplzj.setMaxLength(32);
    	doc_gdplzj.setCharLimit(allow_num);
    	JLabel kcjhff = new JLabel("库存计划方法");
    	kcjhffiTextField1 = new JComboBox(kcjhff_str);
    	kcjhffiTextField1.setPreferredSize(new Dimension(216,23));
    	JLabel jmbjg = new JLabel("价目表价格");
    	jmbjgiTextField1 = new SACJTextField32(35);
    	JLabel zldw = new JLabel("重量单位");
    	zldwiTextField1 = new JComboBox(dw_str);
    	zldwiTextField1.setPreferredSize(new Dimension(216,23));
    	JLabel dwzl = new JLabel("单位重量");
    	dwzliTextField1 = new SACJTextField32(35);
    	JLabel tjdw = new JLabel("体积单位");
    	tjdwiTextField1 = new JComboBox(dw_str);
    	tjdwiTextField1.setPreferredSize(new Dimension(216,23));
    	JLabel dwtj = new JLabel("单位体积");
    	dwtjiTextField1 = new SACJTextField32(35);
    	
    	
    	
    	jpanel2.add("1.1.right",new JLabel(" "));
    	jpanel2.add("2.1.right",zzdm);
    	jpanel2.add("2.2.right",zzdmiTextField1);
    	jpanel2.add("3.1.right",mrcgy);
    	jpanel2.add("3.2.right",mrcgyiTextField1);
    	jpanel2.add("4.1.right",jihy);
    	jpanel2.add("4.2.right",jihyiTextField1);
    	jpanel2.add("5.1.right",tqq_gd);
    	jpanel2.add("5.2.right",tqq_gdiTextField1);
    	jpanel2.add("6.1.right",cwlbj);
    	jpanel2.add("6.2.right",cwlbjiTextField1);
    	jpanel2.add("7.1.right",jhlbj);
    	jpanel2.add("7.2.right",jhlbjiTextField1);
/*    	
    	jpanel2.add("8.1.right",cplbj);
    	jpanel2.add("8.2.right",cplbjiTextField1);
    	jpanel2.add("9.1.right",kcjhff);
    	jpanel2.add("9.2.right",kcjhffiTextField1);
*/    	
    	
    	jpanel3.add("1.1.right",new JLabel(" "));
    	jpanel3.add("2.1.right",gdgyts);
    	jpanel3.add("2.2.right",gdgytsiTextField1);
    	jpanel3.add("3.1.right",gdplzj);
    	jpanel3.add("3.2.right",gdplzjiTextField1);
    	jpanel3.add("4.1.right",jmbjg);
    	jpanel3.add("4.2.right",jmbjgiTextField1);
/*    	
    	jpanel3.add("5.1.right",zldw);
    	jpanel3.add("5.2.right",zldwiTextField1);
    	jpanel3.add("6.1.right",dwzl);
    	jpanel3.add("6.2.right",dwzliTextField1);
    	jpanel3.add("7.1.right",tjdw);
    	jpanel3.add("7.2.right",tjdwiTextField1);
    	jpanel3.add("8.1.right",dwtj);
    	jpanel3.add("8.2.right",dwtjiTextField1);
*/   	
    	jpanel3.add("5.1.right",cplbj);
    	jpanel3.add("5.2.right",cplbjiTextField1);
    	jpanel3.add("6.1.right",kcjhff);
    	jpanel3.add("6.2.right",kcjhffiTextField1);
    	jpanel3.add("7.1.right",zkc);
    	jpanel3.add("7.2.right",zkciTextField1);
    	
    	jpanel.add("1.1.right",new JLabel("       "));
    	jpanel.add("1.2.right",jpanel2);
    	jpanel.add("1.3.right",new JLabel("       "));
    	jpanel.add("1.4.right",jpanel3);
    	jpanel.add("1.5.right",new JLabel("       "));
    	all_panel.add(jpanel);
    	return all_panel;
    } 
    
    private JPanel buildZuZhi2Information(){                                 //组织代码2
    	JPanel all_panel = new JPanel(new GridLayout(1, 0));
    	TitledBorder titleBorder = BorderFactory.createTitledBorder("维护属性");
    	titleBorder.setTitlePosition(2);
    	titleBorder.setTitleFont(font);
		all_panel.setBorder(titleBorder);
		all_panel.setLayout(new BoxLayout(all_panel, BoxLayout.Y_AXIS));
    	JPanel jpanel = new JPanel(new PropertyLayout());
    	JPanel jpanel2 = new JPanel(new PropertyLayout());
    	JPanel jpanel3 = new JPanel(new PropertyLayout());
    	JLabel zzdm = new JLabel("组织代码*");
    	zzdmiTextField2 = new JComboBox(zzdm_str);
//    	zzdmiTextField2.setSelectedIndex(0);
    	zzdmiTextField2.setPreferredSize(new Dimension(216,23));       //对下面的进行了修改
    	zzdmiTextField2.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
        	   	str2=zzdmiTextField2.getSelectedItem().toString();	
        	   	if(str2.equals(str1)&&str1!=null&&str2!=null&&!"".equals(str1)&&!"".equals(str2))
        	   	{
        	   		MessageBox.post("与组织1的组织代码重复!", "提示", MessageBox.INFORMATION);
        	   		zzdmiTextField2.setSelectedItem("");
        	   	}
        	   	
        	    if (zzdmiTextField2.getSelectedItem().equals("")) {
		        	zkciTextField2.removeAllItems();
		        	zkciTextField2.addItem("");
		        	zkciTextField2.setSelectedIndex(0);
		        }
        		if(zzdmiTextField2.getSelectedItem().toString().trim().equals("P31")){
            	    //	jihyiTextField1 = new JComboBox(jhybh_str);    //计划员编号
        			   jihyiTextField2.removeAllItems();
            	    	for(int i=0;i<jhybh_str.length;i++){
            	    		jihyiTextField2.addItem(jhybh_str[i]);//计划员编号
            	    	}
            	    }
            	 else 
            	    {
            	    	jihyiTextField2.removeAllItems();
            	    }
        	   	
        		for(int i=0;i<optionKeys.length;i++)
				{	
					if(zzdmiTextField2.getSelectedItem().equals(optionKeys[i].split("=")[0].trim()))
					{
						zkciTextField2.removeAllItems();
						System.out.println("**********2******");
						zkciTextField2.addItem("");
						String[] kczz = optionKeys[i].split("=")[1].split(",");
						for (int j=0; j<kczz.length; j++) {
						//	System.out.println(kczz[j]);
							zkciTextField2.addItem( kczz[j].trim());
						}
						zkciTextField2.setSelectedIndex(0);
						System.out.println("**********2-end******"+zkciTextField2.getSelectedItem().toString());
						}
					else{}
						}					
					
    		}
    	  }
   		);
    	
    	
    	JLabel mrcgy = new JLabel("默认采购员");
    	mrcgyiTextField2 = new JAutoCompleteComboBox(mrcgy_str);
    	Arrays.sort(mrcgy_str);
    	mrcgyiTextField2.setSelectedIndex(-1);
    	mrcgyiTextField2.setPreferredSize(new Dimension(216,23));
    	JLabel jihy = new JLabel("计划员编号");
    	jihyiTextField2 = new JComboBox(jhybh_str);    //计划员编号
    	jihyiTextField2.setPreferredSize(new Dimension(216,23));
    	jihyiTextField2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(jihyiTextField2.getSelectedItem()==null){}
				else if(jihyiTextField2.getSelectedItem().toString().contains("*")){
					String s1=jihyiTextField2.getSelectedItem().toString();
					String s2=s1.split("\\*")[0];
					jihyiTextField2.setEditable(true);
					jihyiTextField2.setSelectedItem(s2);
					jihyiTextField2.setEditable(false);
				}
			}   		
    	});
    	JLabel tqq_gd = new JLabel("固定提前期");
    	tqq_gdiTextField2 = new iTextField(35);
    	doc_gdtqq = new SACMyDocument();
    	tqq_gdiTextField2.setDocument(doc_gdtqq);
    	doc_gdtqq.setMaxLength(32);
    	doc_gdtqq.setCharLimit(allow_num);
/*    	JLabel kclbj = new JLabel("SAC_库存类别集*");
    	if(form.getType().equals("S4DQDMRevisionMaster")||form.getType().equals("S4RKDMRevisionMaster"))
    	{
    		kclbjiTextField2= new JComboBox(kc_str);
    	}
    	else
    		kclbjiTextField2 = new JComboBox(kclbj_str);
    	kclbjiTextField2.setPreferredSize(new Dimension(216,23));
    	kclbjiTextField2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(kclbjiTextField2.getSelectedItem().toString().contains("*")){
					String s1=kclbjiTextField2.getSelectedItem().toString();
					String s2=s1.split("\\*")[0];
					kclbjiTextField2.setEditable(true);
					kclbjiTextField2.setSelectedItem(s2);
					kclbjiTextField2.setEditable(false);
				}
			}
    		
    	});
    	*/
    	JLabel cwlbj = new JLabel("SAC_财务类别集*");
    	cwlbjiTextField2 = new JComboBox(cwlbj_str);
    	cwlbjiTextField2.setPreferredSize(new Dimension(216,23));
    	cwlbjiTextField2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(cwlbjiTextField2.getSelectedItem().toString().contains("*")){
					String s1=cwlbjiTextField2.getSelectedItem().toString();
					String s2=s1.split("\\*")[0];
					cwlbjiTextField2.setEditable(true);
					cwlbjiTextField2.setSelectedItem(s2);
					cwlbjiTextField2.setEditable(false);
				}
			}
    		
    	});
    	JLabel jhlbj = new JLabel("SAC_计划类别集*");
    	jhlbjiTextField2 = new JComboBox(jhlbj_str);
    	jhlbjiTextField2.setPreferredSize(new Dimension(216,23));
    	jhlbjiTextField2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(jhlbjiTextField2.getSelectedItem().toString().contains("*")){
					String s1=jhlbjiTextField2.getSelectedItem().toString();
					String s2=s1.split("\\*")[0];
					jhlbjiTextField2.setEditable(true);
					jhlbjiTextField2.setSelectedItem(s2);
					jhlbjiTextField2.setEditable(false);
				}
			}
    		
    	});
    	JLabel cplbj = new JLabel("SAC_产品类别集");
    	cplbjiTextField2 = new JComboBox(cplbj_str);
    	cplbjiTextField2.setPreferredSize(new Dimension(216,23));
    	cplbjiTextField2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(cplbjiTextField2.getSelectedItem().toString().contains("*")){
					String s1=cplbjiTextField2.getSelectedItem().toString();
					String s2=s1.split("\\*")[0];
					cplbjiTextField2.setEditable(true);
					cplbjiTextField2.setSelectedItem(s2);
					cplbjiTextField2.setEditable(false);
				}
			}
    		
    	});
    	
    	JLabel zkc = new JLabel("供应子库存");
    	zkciTextField2 = new JComboBox(zkc_str);
    	zkciTextField2.setSelectedItem("");
    	zkciTextField2.setPreferredSize(new Dimension(216,23));
    	zkciTextField2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(zkciTextField2.getSelectedItem()==null){}
				else if(zkciTextField2.getSelectedItem().toString().contains("*")){
					String s1=zkciTextField2.getSelectedItem().toString();
					String s2=s1.split("\\*")[0];
					zkciTextField2.setEditable(true);
					zkciTextField2.setSelectedItem(s2);
					zkciTextField2.setEditable(false);
				}
			}
    		
    	});
    	JLabel hw = new JLabel("货位");
    	hwiTextField2 = new SACJTextField32(35);
    	hwiTextField2.setEnabled(false);
    	JLabel gdgyts = new JLabel("固定供应天数");
    	gdgytsiTextField2 = new iTextField(35);
    	doc_gdgyts = new SACMyDocument();
    	gdgytsiTextField2.setDocument(doc_gdgyts);
    	doc_gdgyts.setMaxLength(32);
    	doc_gdgyts.setCharLimit(allow_num);
    	JLabel gdplzj = new JLabel("固定批量增加");
    	gdplzjiTextField2 = new iTextField(35);
    	doc_gdplzj = new SACMyDocument();
    	gdplzjiTextField2.setDocument(doc_gdplzj);
    	doc_gdplzj.setMaxLength(32);
    	doc_gdplzj.setCharLimit(allow_num);
    	JLabel kcjhff = new JLabel("库存计划方法");
    	kcjhffiTextField2 = new JComboBox(kcjhff_str);
    	kcjhffiTextField2.setPreferredSize(new Dimension(216,23));
    	JLabel jmbjg = new JLabel("价目表价格");
    	jmbjgiTextField2 = new SACJTextField32(35);
    	JLabel zldw = new JLabel("重量单位");
    	zldwiTextField2 = new JComboBox(dw_str);
    	zldwiTextField2.setPreferredSize(new Dimension(216,23));
    	JLabel dwzl = new JLabel("单位重量");
    	dwzliTextField2 = new SACJTextField32(35);
    	JLabel tjdw = new JLabel("体积单位");
    	tjdwiTextField2 = new JComboBox(dw_str);
    	tjdwiTextField2.setPreferredSize(new Dimension(216,23));
    	JLabel dwtj = new JLabel("单位体积");
    	dwtjiTextField2 = new SACJTextField32(35);
    	
    	
    	
    	jpanel2.add("1.1.right",new JLabel(" "));
    	jpanel2.add("2.1.right",zzdm);
    	jpanel2.add("2.2.right",zzdmiTextField2);
    	jpanel2.add("3.1.right",mrcgy);
    	jpanel2.add("3.2.right",mrcgyiTextField2);
    	jpanel2.add("4.1.right",jihy);
    	jpanel2.add("4.2.right",jihyiTextField2);
    	jpanel2.add("5.1.right",tqq_gd);
    	jpanel2.add("5.2.right",tqq_gdiTextField2);
//    	jpanel2.add("6.1.right",kclbj);
//    	jpanel2.add("6.2.right",kclbjiTextField2);
    	jpanel2.add("6.1.right",cwlbj);
    	jpanel2.add("6.2.right",cwlbjiTextField2);
    	jpanel2.add("7.1.right",jhlbj);
    	jpanel2.add("7.2.right",jhlbjiTextField2);
/*    	
    	jpanel2.add("8.1.right",cplbj);
    	jpanel2.add("8.2.right",cplbjiTextField2);
    	jpanel2.add("9.1.right",kcjhff);
    	jpanel2.add("9.2.right",kcjhffiTextField2);
*/   	
    	
    	jpanel3.add("1.1.right",new JLabel(" "));
    	jpanel3.add("2.1.right",gdgyts);
    	jpanel3.add("2.2.right",gdgytsiTextField2);
    	jpanel3.add("3.1.right",gdplzj);
    	jpanel3.add("3.2.right",gdplzjiTextField2);
    	jpanel3.add("4.1.right",jmbjg);
    	jpanel3.add("4.2.right",jmbjgiTextField2);
/*    	
    	jpanel3.add("5.1.right",zldw);
    	jpanel3.add("5.2.right",zldwiTextField2);
    	jpanel3.add("6.1.right",dwzl);
    	jpanel3.add("6.2.right",dwzliTextField2);
    	jpanel3.add("7.1.right",tjdw);
    	jpanel3.add("7.2.right",tjdwiTextField2);
    	jpanel3.add("8.1.right",dwtj);
    	jpanel3.add("8.2.right",dwtjiTextField2);
*/
    	jpanel3.add("5.1.right",cplbj);
    	jpanel3.add("5.2.right",cplbjiTextField2);
    	jpanel3.add("6.1.right",kcjhff);
    	jpanel3.add("6.2.right",kcjhffiTextField2);
    	jpanel3.add("7.1.right",zkc);
    	jpanel3.add("7.2.right",zkciTextField2);
    	
    	jpanel.add("1.1.right",new JLabel("       "));
    	jpanel.add("1.2.right",jpanel2);
    	jpanel.add("1.3.right",new JLabel("       "));
    	jpanel.add("1.4.right",jpanel3);
    	jpanel.add("1.5.right",new JLabel("       "));
    	all_panel.add(jpanel);
    	return all_panel;
    }
    
    
    /*
     * Form界面初始化
     * 
     * */
    private void initializeUI() {
    	setLayout(new VerticalLayout(4, 4, 4, 4, 4));
    	JTabbedPane jtabbedPane = new JTabbedPane();
    	jtabbedPane.setTabPlacement(JTabbedPane.TOP);// 设置标签置放位置
    	jtabbedPane.setPreferredSize(new Dimension(1300,600));
//    	jtabbedPane.addTab("主组织", buildMainInformation());
//		jtabbedPane.addTab("组织1", buildZuZhi1Information());
//		jtabbedPane.addTab("组织2", buildZuZhi2Information());
    	if("Y".equals(cdzt_str)){
    		jtabbedPane.addTab("主组织", buildMainInformation());
    	}else{
    		jtabbedPane.addTab("主组织", buildMainInformation());
    		jtabbedPane.addTab("组织1", buildZuZhi1Information());
    		jtabbedPane.addTab("组织2", buildZuZhi2Information());
    	}
		add("top", jtabbedPane);

	}
    
    private String[] getTCPreferenceArray(TCSession tcSession, String preferenceName) {
		
		String[] preString = null;
	
		TCPreferenceService tcPreservice = tcSession.getPreferenceService();
		
		preString = tcPreservice.getStringArray(TCPreferenceService.TC_preference_site, preferenceName);

		return preString;
	}
	public String getContactNum() throws TCException {
	//	String[] temp=null;
		Vector<String> temp = new Vector<String>();
		AIFComponentContext[] aif = form.getPrimary();
		AIFComponentContext[] aif1 = null;
		AIFComponentContext[] aif2 = null;

		if (aif != null && aif.length > 0) {
			TCComponentItemRevision	ir = (TCComponentItemRevision) aif[0].getComponent();
			aif1 = ir.whereReferenced();
			if (aif1 != null && aif1.length > 0) {
			TCComponentItem	item = (TCComponentItem) aif1[0].getComponent();
			aif2 = item.whereReferenced();
			int j=0;
            if (aif2 != null && aif2.length > 0) {	
            //	temp=new String[aif2.length];
            	for(int i=0;i<aif2.length;i++){
                     TCComponentFolder folder = (TCComponentFolder) aif2[i].getComponent();
                     String s=folder.getTCProperty("gov_classification").getStringValue();
                     if(s.equals("")||s==null)
                      {}
                     else
                      { 
                     //temp[j]=s;
                     //j++;
                    	temp.add(s);
                      }
            	}
            }
			}
		}
		System.out.println("************除去空前************"+aif2.length);
		System.out.println("************除去空后************"+temp.size());
		if(temp.size()==0) //属性值全为空
			return "";
		else if(temp.size()==1)//属性值有一个不为空，其他全为空
			return temp.elementAt(0);
		else      //属性值有多个不为空
		{
		  for(int i=0;i<temp.size()-1;i++){
			if(temp.elementAt(i).trim().equals(temp.elementAt(i+1).trim())==false)
				return "ERROR";
		  }
		  return temp.elementAt(0);
		}
	}
	public void setContactNum(String contactNumber) throws TCException {
		AIFComponentContext[] aif = form.getPrimary();
		AIFComponentContext[] aif1 = null;

		if (aif != null && aif.length > 0) {
			TCComponentItemRevision	ir = (TCComponentItemRevision) aif[0].getComponent();
			if(ir.getProperty("gov_classification")==null||ir.getProperty("gov_classification").equals(""))
			  ir.setProperty("gov_classification", contactNumber);
			aif1 = ir.whereReferenced();
			if (aif1 != null && aif1.length > 0) {
			TCComponentItem	item = (TCComponentItem) aif1[0].getComponent();
			if(item.getProperty("gov_classification")==null||item.getProperty("gov_classification").equals(""))
			item.setProperty("gov_classification", contactNumber);
			}
	}
	}
	public String getItemId() throws TCException {
		AIFComponentContext[] aif = form.getPrimary();
		AIFComponentContext[] aif1 = null;

		if (aif != null && aif.length > 0) {
			TCComponentItemRevision	ir = (TCComponentItemRevision) aif[0].getComponent();;
			aif1 = ir.whereReferenced();
			if (aif1 != null && aif1.length > 0) {
			TCComponentItem	item = (TCComponentItem) aif1[0].getComponent();
		
			 System.out.println("item_id_________________"+item.getProperty("item_id"));
			 return item.getProperty("item_id");
			}
	}
		return null;
	}
	
	private boolean isAllEmptyField() {
		
		if (mrcgyiTextField2 == null
			|| tqq_gdiTextField2 == null
			|| cwlbjiTextField2 == null
			|| zzdmiTextField2 == null
			|| jhlbjiTextField2 == null
			|| zkciTextField2 == null
			|| gdgytsiTextField2 == null
			|| gdplzjiTextField2 == null
			|| kcjhffiTextField2 == null
			|| jmbjgiTextField2 == null
			|| zkciTextField2 == null) {
			
			return true;
		}
    	if (isEmpty(mrcgyiTextField2.getSelectedItem().toString())
    			&& isEmpty(tqq_gdiTextField2.getText())
    			&& isEmpty(zzdmiTextField2.getSelectedItem().toString())
    			&& isEmpty(cwlbjiTextField2.getSelectedItem().toString())
    			&& isEmpty(jhlbjiTextField2.getSelectedItem().toString())
    			&& isEmpty(zkciTextField2.getSelectedItem().toString())
    			&& isEmpty(gdgytsiTextField2.getText())
    			&& isEmpty(gdplzjiTextField2.getText())
    			&& isEmpty(kcjhffiTextField2.getSelectedItem().toString())
    			&& isEmpty(jmbjgiTextField2.getText()) 
    			&& isEmpty(zkciTextField2.getSelectedItem().toString())) {
    		return true;
    	}
    	return false;
    }
    
    private boolean isEmpty(String input) {
    	if (input == null || input.equals("")) {
    		return true;
    	}
    	else return false;
    }

}
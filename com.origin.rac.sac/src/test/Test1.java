package test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Vector;

import javax.mail.search.FromStringTerm;

import com.teamcenter.rac.util.MessageBox;

public class Test1 {

	/**
	 * @param args
	 */
	
	private static HashMap<String,String> map = new HashMap<String,String>();
	private static String[] templates = null;
	private static String[] options = {"SAC费用物料模板=客户订单=s4Customer_Ordered=Yes","SAC费用物料模板=启用客户订单=s4Customer_Orders_Ena=Yes",
										"SAC费用物料模板=可退货=s4Returnable=Yes","SAC费用物料模板=MRP计划方法=s4MRP_Planning_Method=Not planned",
										"SAC费用物料模板=应纳税=s4Taxable= "};
	private static String name = "SAC费用物料模板";
//	private static HashMap<String,Vector> map1 = new HashMap<String,Vector>();
//	private static HashMap<String,Vector> map2 = new HashMap<String,Vector>();
	private static Vector<Vector<String>> vec_all = new Vector<Vector<String>>();
	private static Vector v1 = new Vector();
	private static Vector v2 = new Vector();
	private static Vector v3 = new Vector();
	private static Vector v_all = new Vector();
	private static String[] vec5 = {"000106","66","物料6"};
	private static String[] vec6 = {"000107","77","物料7"};
	private static String[] vec7 = {"000108","88","物料8"};
	private static String[] vec8 = {"000109","99","物料9"};
	private static Vector<String[]> vec_dd = new Vector<String[]>();
//	private static Vector<Vector<String>> vec_dd1 = new Vector<Vector<String>>();
	private static Vector<String[]> vec_dd1 = new Vector<String[]>();
	private static Vector<String[]> vec_dd2 = new Vector<String[]>();
	private static Vector v_1 = new Vector();
	private static Vector v_2 = new Vector();
	private static Vector v_3 = new Vector();
	
	private static HashMap map_all = new HashMap();
	private static HashMap<String,Vector<String>> map_1 = new HashMap<String,Vector<String>>();
//	private static String ste = "[qq, 管理, 配置基线发布报告]";
	private static String[] ste ={"装置类鉴定项目:管理,PCB评审决策表=S4PCBPS*样机评审报告=S4YJPSBG;A,查新报告=S4CXBG;B,硬件需求说明书=S4YJXQ*单板硬件需求说明书=S4DBYJXQ*装置总体设计=S4ZZZT",
								"软件类鉴定项目:管理,配置状态报告、项目成员工作周报;A,设计任务书、项目估算表;B,产品标准、产品型号名称申请表"
	};

	public static String[] proj_str1 = {"装置类小结项目","装置类鉴定项目","软件类小结项目","软件类鉴定项目","课题类项目","集团项目","系统类项目"};//默认项目类型选择框的内容
	public static String[] proj_str = null;
	private static String sss="PCB评审决策表=S4PCBPS*样机评审报告=S4YJPSBG";
	private static String[] ks = {"职称:哈记得,的境况,康复科","部门:哦哦,品牌,离开"};
	private static String jah = "BHJ:看看大家点击";
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static boolean bom_flag = false;
	private static boolean bom_flag1 = false;
	private static boolean bom_flag2 = false;
	private static boolean gylx_flag = false;
	private static boolean gylx_flag1 = false;
	private static boolean gylx_flag2 = false;
	private static String nnmm = "订单行物料图纸";
	private static String dddd = "订单行物料图纸dhjaj大家看）（dhjaj大家看）（dhjaj大家看）（dhjaj大家看）（dhjaj大家看）（dhjaj大家看）（dhjaj大家看）（dhjaj大家看）（dhjaj大家看）";
	private static String[] sjkd = {"mm=kkk","ooo=ppp","yy="};
	private static String[] name_bm = null;
	
	
	
	
	
	public static void main(String[] args) {
		if(dddd.getBytes().length>=160){
			
			System.out.println("dddd--->:"+dddd.getBytes().length);
		}
		/*if(dddd.contains(nnmm)){
			System.out.println("dddd========>:");
		}else{
			System.out.println("BBBBBBBBB");
		}*/
		/*String h = nnmm.substring(nnmm.length()-5, nnmm.length());
		System.out.println("h--->:"+h);*/
		/*String[] ds = nnmm.split("\\.");
		System.out.println(ds[0]);*/
		/*String[] arr = nnmm.split(";");
		System.out.println(""+arr.length);
		if(arr.length>0){
			if(arr.length==1){
				System.out.println("arr[0]===>:"+arr[0]);
				if("".equals(arr[0])){
					System.out.println("11--->:"+arr[0]);
					
				}else{
					System.out.println("2222--->:"+arr[0]);
				}
			}else{
				System.out.println("11--->:"+arr[0]);
				System.out.println("2222--->:"+arr[1]);
			}
			
		}*/
		
		/*for (int i = 0; i < sjkd.length; i++) {
			
			String[] tmp_ECO_zz_lx_yy  = sjkd[i].split("=");
			System.out.println("tmp_ECO_zz_lx_yy===>:"+tmp_ECO_zz_lx_yy.length);
			if(tmp_ECO_zz_lx_yy[0].equals("yy")){
				if(tmp_ECO_zz_lx_yy.length ==2){
					System.out.println("AAA");
					name_bm = tmp_ECO_zz_lx_yy[1].split(",");
				}else if(tmp_ECO_zz_lx_yy.length ==1){
					System.out.println("bb");
					name_bm = null;
				}
				
				System.out.println("name_bm==-----=>:"+name_bm.length);
				for (int j = 0; j < name_bm.length; j++) {
					System.out.println(name_bm[j]);
					
				}
			}
		}*/
		/*for (int i = 0; i < proj_str1.length; i++) {
			System.out.println("FFFFFFFFFFFFFFF");
			setUO(proj_str1[i]);
			
		}
		if(!bom_flag1){
			
			System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGG");
		}*/
		
		/*if(!gylx_flag && !gylx_flag1 && !gylx_flag2 && !bom_flag && !bom_flag1 && bom_flag2){
			System.out.println("VVVVVVVVVVVVVv");
			
		}
		if(!gylx_flag && !gylx_flag1 && !gylx_flag2 && !bom_flag && !bom_flag1 && !bom_flag2){
			System.out.println("BBBBBBBBBBBBBBBB");
		}*/
		
		/*if(!gylx_flag && !gylx_flag1 && !gylx_flag2 && !bom_flag && !bom_flag && !bom_flag2){
			System.out.println("目前没有您需要检查的工艺路线和BOM");
			
		}else{
			System.out.println("工程BOM及工艺路线批量传递检查完毕");
			
		}*/
		
		
		/*for (int i = 0; i < proj_str1.length; i++) {
			System.out.println("proj_str1-->:"+proj_str1[i]);
			if(i==3){
				continue;
			}
			
			System.out.println("BBBBBBB");
		}*/
		/*String mk = jah.split(":")[0];
		System.out.println("mk====>:"+mk);*/
//		String s = sdf.format("2012/2/15");
		/*String s = "S4Yjsksks";
		String n = s.substring(2, s.length());
		System.out.println("n===>:"+n);*/
		/*String[] nn = new String[as.length+1];
		nn[0] = "";
		for (int i = 0; i < as.length; i++) {
			nn[i+1] = as[i];
			
		}
		for (int i = 0; i < nn.length; i++) {
			System.out.println("1--->:"+nn[i]);
		}*/
		/*String s0 = ks[0];
		System.out.println("s0===>:"+s0);
		String zc[] = null;
		String[] sf = s0.split(":");
		zc = sf[1].split(",");
		for (int i = 0; i < zc.length; i++) {
			System.out.println("zc--->:"+zc[i]);
		}*/
		/*String h = ks.substring(0, 5);
		System.out.println("h--->:"+h);*/
//		String[] s3 = sss.split("\\*");
//		System.out.println("s3--->:"+s3.length);
		
		/*if(proj_str1 == null || proj_str1.length == 0){
			
		}else{
			proj_str = new String[proj_str1.length+1];
			for (int i = 0; i < proj_str1.length; i++) {
				
				proj_str[0] = "";
				proj_str[i+1] = proj_str1[i];
			}
			System.out.println("proj_str"+proj_str.length);
			for (int i = 0; i < proj_str.length; i++) {
				
				System.out.println("i--->:"+proj_str[i]);
			}
		}*/
		
		
		
		/*for (int i = 0; i < ste.length; i++) {
			String[] str = ste[i].split(":");
			map_all.put(str[0], str[1]);
			
		}
//		System.out.println(""+map_all);
		String tem = (String) map_all.get("装置类鉴定项目");
		System.out.println("tem===>:"+tem);
		if(tem!=null){
			String[] s1 = tem.split(";");
			for (int j = 0; j < s1.length; j++) {
				System.out.println("s1==>:"+s1[j]);
				String[] s2 = s1[j].split(",");
				System.out.println("s2===>:"+s2[1]);
				String[] s3 = s2[1].split("\\*");
				Vector<String> v = new Vector<String>();
				for (int i = 0; i < s3.length; i++) {
					v.add(s3[i]);
				}
				System.out.println("s3------>:"+s3[0]);
				map_1.put(s2[0], v);
			}
			
			System.out.println("map_1====>:"+map_1);
			Vector<String> vec = map_1.get("C");
			System.out.println("vec===>:"+vec);
		}*/
		/*String[] a = ste.split(",");
		System.out.println(""+a.length);
		System.out.println(a[a.length-1]);
		String s = a[a.length-1];
		String d = s.substring(1, s.length()-1);
		System.out.println("d===>:"+d);*/
		/*System.out.println("s1--->:"+s1[0]);
		if(" 配置基线发布报告".equals(s1[0])){
			System.out.println("dddddddddd");
		}*/
		
		// TODO Auto-generated method stub
		/*v_1.add("000106");
		v_1.add("000107");
		v_1.add("000108");
		v_1.add("000109");
		v_2.add("66");
		v_2.add("77");
		v_2.add("88");
		v_2.add("99");
		v_3.add("物料6");
		v_3.add("物料7");
		v_3.add("物料8");
		v_3.add("物料9");
		v_all.add(v_1);
		v_all.add(v_2);
		v_all.add(v_3);
		Vector v_p =  new Vector();
		
		
		for (int i = 0; i < v_all.size(); i++) {
			System.out.println(""+v_all.get(i));
			Vector v =(Vector) v_all.get(i);
			String[] str = new String[v.size()];
			for (int j = 0; j < v.size(); j++) {
				str[j] = (String) v.get(j);
				System.out.println("str[j]--->:"+str[j]);
			}
			v_p.add(str);
		}
		System.out.println("v_p--->:"+v_p.size());
		for (int i = 0; i < v_p.size(); i++) {
			String[] str = (String[]) v_p.get(i);
			System.out.println(""+str[0]);
		}*/
		
		/*vec_dd.add(vec5);
		vec_dd.add(vec6);
		vec_dd.add(vec7);
		vec_dd.add(vec8);
		for (int i = 0; i < 3; i++) {
			String[] str1 = new String[vec_dd.size()];
			Vector v = new Vector();
			String[] str = new String[3];
			for (int j = 0; j < vec_dd.size(); j++) {
				str[i] = vec_dd.get(j)[i];
				str1[j] = str[i];
//				v.add(str[i]);
			}
			
			for (int j = 0; j < str1.length; j++) {
				System.out.println(""+str1[j]);
				
			}
			vec_dd1.add(str1);
		}
		
		System.out.println(""+vec_dd1);
		System.out.println("2---->:"+vec_dd1.size());
		for (int i = 0; i < vec_dd1.size(); i++) {
			String []str = vec_dd1.get(i);
			System.out.println("length------->:"+str.length);
			System.out.println(""+str[0]);
			System.out.println(""+str[1]);
			System.out.println(""+str[2]);
			System.out.println(""+str[3]);
		}*/
		/*v1.add("1");
		v1.add("2");
		v1.add("3");
		v2.add("4");
		v2.add("5");
		v2.add("6");
		v3.add("7");
		v3.add("8");
		v3.add("9");
		v_all.add(v1);
		v_all.add(v2);
		v_all.add(v3);
		map1.put("001",v_all);
		map2.put("001",v1);
		System.out.println("map1-->:"+map1);
		System.out.println("map2--22222>:"+map2);*/
		/*int a = 13;
		double k = Double.valueOf(a);
		double b = k/2;
		int t = a/2;
		int m = a%2;
		if(m==0){
			System.out.println("aaa");
			for (int i = 0; i < a; i++) {
				if(i<t){
					System.out.println("i----->:"+i);
				}else if(i>=t){
					System.out.println("i---tttttt-->:"+i);
				}
			}
		}else if(m==1){
			System.out.println("bbb");
			for (int i = 0; i < a; i++) {
				if(i<(t+1)){
					System.out.println("i---mmm===1-->:"+i);
				}else if(i>=(t+1)){
					System.out.println("i---mmm===1-ttttttttttttt->:"+i);
				}
				
				
			}
		}
		
		System.out.println("mm====>:"+m);
		System.out.println(""+b);*/
//		System.out.println("ddd==>:"+getRightStr(b));
		/*BigDecimal bd=new BigDecimal(b); 
	    bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP); 
	    System.out.println("bd.doubleValue()"+bd.doubleValue());*/
		/*map.put("1", "a,b");
		map.put("2", "c");
		map.put("3", "d,e,f");
		map.put("4", "g");
		String tem = map.get("2");
		String[] str = tem.split(",");
		System.out.println("11=str=>:"+str.length);
		templates = new String[str.length];
		if(str.length>1){
			for (int i = 0; i < str.length; i++) {
				templates[i] = str[i];
			}
		}else{
			templates[0] = tem;
		}
		System.out.println("222=======>:"+templates.length);
		for (int i = 0; i < templates.length; i++) {
			System.out.println("i--->:"+templates[i]);
		}*/
		/*for (int i = 0; i < options.length; i++) {
			Vector<String> vec = new Vector<String>();
			String[] str = options[i].split("=");
			System.out.println("str=====length===>:"+str.length);
			System.out.println("str[0]--->:"+str[0]);
			if(name.equals(str[0])){
				System.out.println("str[1]=======>:"+str[1]);
				System.out.println("str[2]=======>:"+str[2]);
				System.out.println("str[3]=======>:"+str[3]);
				vec.add(str[1]);
				vec.add(str[2]);
				vec.add(str[3]);
			}
			
			
			System.out.println("vec---》："+vec);
			vec_all.add(vec);
		}
		System.out.println("vec_all---=======>:："+vec_all);
		System.out.println(""+vec_all.get(0).get(1));*/
		
	}
	
	public static void setUO(String s){
		if("软件类小结项目".equals(s)){
			
			System.out.println("KKKKK");
			bom_flag1 = true;
			return;
			
		}
	}
	
	/**
	 * 对double型保留两位有效数字
	 * */
	 private static double getRightStr(Double sNum)
	    {	
		 	double db = new Double(new DecimalFormat("0.0").format(sNum));
	        return db;
	    }

}

package cn.com.origin.util;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import com.teamcenter.rac.util.MessageBox;

public class SACJTextArea1280 extends JTextArea {

	public SACJTextArea1280() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SACJTextArea1280(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	public Document createDefaultModel(){
		return  (new NumberDocument(getText()));
	}
	
	static class NumberDocument extends PlainDocument
	{
		String str1 = "" ;
		public NumberDocument(String sr)
		{
			str1 = sr ;
		}
		public void insertString(int offs,String str,AttributeSet a)
		{
			if(str == null)
			{
				return ;
			}
			try {
				if(getText(0, getLength()).getBytes().length+str.getBytes().length <=1280)
				{
					super.insertString(offs, new String(str).trim(), a);
				}else
				{
					MessageBox.post("您文本区域输入的内容长度超过了1280", "提示", MessageBox.INFORMATION);
					System.out.println("wei kkkkkkkk===>:"+getText(0, getLength()).getBytes().length);
				}
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

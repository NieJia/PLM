package cn.com.origin.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.teamcenter.rac.util.MessageBox;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Java 中文本组件MQDocument文档：采用正则表达式来判断输入是否合法
 *
 * @author 
 * @blog 
 */

public class SACDocument extends PlainDocument {
	
	private int maxLength = -1; // 输入字符最大长度的限制，-1为不限制
	
	private Toolkit toolkit = null; // 用来在错误的时候发出系统声音

	private boolean beep = false; // 是否发声，true为发出声音

	
	// 构造方法

	public SACDocument() {
		super();
		this.init();
	}

	public SACDocument(Content c) {
		super(c);
		this.init();
	}

	/**
	 * 所有构造都需要的公共方法
	 */
	private void init() {
		toolkit = Toolkit.getDefaultToolkit();
	}

	// 构造方法结束

	/**
	 * 设置文本框所允许输入的最大字符长度
	 * 
	 * @param maxLength
	 *            最大字符长度
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * 取消文本框最大字符长度的限制
	 */
	public void cancelMaxLength() {
		this.maxLength = -1;
	}

	/**
	 * 使所有限制设置恢复默认
	 */
	public void reset() {
		cancelMaxLength();
	}

	/**
	 * 错误时发声开关
	 * 
	 * @param beep
	 *            true为发声音
	 */
	public void errorBeep(boolean beep) {
		this.beep = beep;
	}

	/**
	 * 输入错误时是否发声
	 * 
	 * @return boolean true为发声
	 */
	public boolean isErrorBeep() {
		return beep;
	}

	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException, NumberFormatException {
		// 若字符串为空，直接返回。
		if (str == null) {
			return;
		}
		boolean b = true;
		//System.out.println(str);
		if (maxLength > -1 && (this.getText(0,this.getLength()).getBytes().length+str.getBytes().length) >maxLength) {
			b = false;
			MessageBox.post("您输入的内容长度超过了"+this.maxLength, "提示", MessageBox.INFORMATION);
		}
		char[] ch = str.toCharArray();
		/*
		for (int i = 0; i < ch.length; i++) {
			String temp = String.valueOf(ch[i]);
			// 如果要输入的字符不在允许范围内
			if (!isOfLimit(temp)) {
				b = false;
			}
			// 如果有字符长度限制，并且现在的字符长度已经大于或等于限制
			if (maxLength > -1 && (getText(0,getLength()).getBytes().length+str.getBytes().length) >maxLength){//str.getBytes().length
				b = false;
				MessageBox.post("您输入的内容长度超过了"+this.maxLength, "提示", MessageBox.INFORMATION);
			}
			
		}
		*/
		
		
		/*
		 * 
		// 如果内容设置了最大数字
		if (isMaxValue) {
			String s = this.getText(0, this.getLength()); // 文档中已有的字符
			s = s.substring(0, offs) + str + s.substring(offs, s.length());
			if (Double.parseDouble(s) > maxValue) {
				if (beep) {
					toolkit.beep(); // 发出声音
				}
				return;
			}
		}
		
       */
		
		// 如果输入不合法
		if (!b) {
			if (beep) {
				toolkit.beep(); // 发出声音
			}
			return;
		}

		super.insertString(offs, new String(ch), a);
	}

}


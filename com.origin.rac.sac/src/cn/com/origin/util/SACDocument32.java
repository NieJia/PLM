package cn.com.origin.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.teamcenter.rac.util.MessageBox;

import java.awt.Toolkit;
import java.util.regex.Pattern;

/**
 * Java 中文本组件MQDocument文档：采用正则表达式来判断输入是否合法
 *
 * @author 
 * @blog 
 */

public class SACDocument32 extends PlainDocument {

	private String limit = null; // 输入字符限制的正则表达式

	private int maxLength = -1; // 输入字符最大长度的限制，-1为不限制

	private double maxValue = 0; // 如果输入的是数字，则最大值限制

	private boolean isMaxValue = false; // 是否采用了最大值限制

	private Toolkit toolkit = null; // 用来在错误的时候发出系统声音

	private boolean beep = false; // 是否发声，true为发出声音

	// 构造方法

	public SACDocument32() {
		super();
		this.init();
	}

	public SACDocument32(Content c) {
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
	 * 设置字符限制条件
	 * 
	 * @param limit
	 *  限制条件 正则表达式
	 */
	public void setCharLimit(String limit) {
		this.limit = limit;
	}

	/**
	 * 返回字符限制的条件
	 * 
	 * @return 条件
	 */
	public String getCharLimit() {
		return this.limit;
	}

	/**
	 * 清除所有限制字符
	 */
	public void clearLimit() {
		this.limit = null;
	}

	/**
	 * 字符输入限制是否包含该字符
	 * 
	 * @param input
	 *            字符
	 * @return true为包含，false为不包含
	 */
	public boolean isOfLimit(CharSequence input) {
		if (limit == null) {
			return true;
		} else {
			return Pattern.compile(limit).matcher(input).find();
		}
	}

	/**
	 * 字符输入的限制组是否为空
	 * 
	 * @return true为空，false为有
	 */
	public boolean isEmptyLimit() {
		if (limit == null) {
			return true;
		} else {
			return false;
		}
	}

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
	 * 如果输入的为纯数字，则可用此方法来设置数字的最大值
	 * 
	 * @param maxValue
	 *            最大值
	 */
	public void setMaxValue(double maxValue) {
		this.isMaxValue = true;
		this.maxValue = maxValue;
	}

	/**
	 * 文本框是否限制了数字内容的最大数值
	 * 
	 * @return true为限制了
	 */
	public boolean isMaxValue() {
		return this.isMaxValue;
	}

	/**
	 * 返回限制数字内容最大值
	 * 
	 * @return double类最大值，如果没有限制会返回0
	 */
	public double getMaxValue() {
		return this.maxValue;
	}

	/**
	 * 取消数字内容的最大值设置
	 */
	public void cancelMaxValue() {
		this.isMaxValue = false;
		this.maxValue = 0;
	}

	/**
	 * 使所有限制设置恢复默认
	 */
	public void reset() {
		clearLimit();
		cancelMaxLength();
		cancelMaxValue();
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
		if(str == null)
		{
			return ;
		}
		try {
			if(getText(0, getLength()).getBytes().length+str.getBytes().length <=32)
			{
				super.insertString(offs, new String(str).trim(), a);
			}else
			{
				MessageBox.post("您文本区域输入的内容长度超过了32", "提示", MessageBox.INFORMATION);
				System.out.println("wei kkkkkkkk===>:"+str.getBytes().length);
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}


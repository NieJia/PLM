package cn.com.origin.util;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.util.Collections;
import java.util.Arrays;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

/**
 * @author xul
 * 带有自动检查功能的CombBox
 */
public class JAutoCompleteComboBox
    extends JComboBox {
  private AutoCompleter completer;

  public JAutoCompleteComboBox() {
    super();
    addCompleter();
  }

  public JAutoCompleteComboBox(ComboBoxModel cm) {
    super(cm);
    addCompleter();
  }

  public JAutoCompleteComboBox(Object[] items) {
    super(items);
    addCompleter();
  }

  public JAutoCompleteComboBox(List v) {
    super( (Vector) v);
    addCompleter();
  }

  private void addCompleter() {
    setEditable(true);
    completer = new AutoCompleter(this);
  }

  public void autoComplete(String str) {
    this.completer.autoComplete(str, str.length());
  }

  public String getText() {
    return ( (JTextField) getEditor().getEditorComponent()).getText();
  }

  public void setText(String text) {
    ( (JTextField) getEditor().getEditorComponent()).setText(text);
  }

  public boolean containsItem(String itemString) {
    for (int i = 0; i < this.getModel().getSize(); i++) {
      String _item = "" + this.getModel().getElementAt(i);
      if (_item.equals(itemString)) {
        return true;
      }
    }
    return false;
  }

  /*
   * 测试方法
   */
  /*public static void main(String[] args) {
    JFrame frame = new JFrame();
    Object[] items = new Object[] {
        "海尔","九阳","jiuyang","abc", "虚假","王刚","没底", "美的", "徐亮", "hlp"};
    //排序内容
    //java.util.ArrayList list = new java.util.ArrayList(Arrays.asList(items));
    //Collections.sort(list);
    //JComboBox cmb = new JAutoCompleteComboBox(list.toArray());
    Arrays.sort(items);
    JComboBox cmb = new JAutoCompleteComboBox(items);
    cmb.setSelectedIndex(-1);
    frame.getContentPane().add(cmb);
    frame.setSize(400, 80);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }*/
  
}

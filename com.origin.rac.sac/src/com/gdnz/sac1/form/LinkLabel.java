package com.gdnz.sac1.form;
import java.awt.Color; 
import java.awt.Cursor; 
import java.awt.Desktop; 

import java.awt.event.MouseAdapter; 
import java.awt.event.MouseEvent; 
import java.io.File;
import java.io.IOException; 

import javax.swing.JLabel; 

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentDatasetType;
import com.teamcenter.rac.kernel.TCComponentTcFile;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
/**  * 超链接文本标签  *  */
class LinkLabel extends JLabel{      
	private static final long serialVersionUID = 1L;     
	private String text;/**超链接显示的文字*/        
	private File filelink=null;/**保存连接*/
	private TCComponentDataset dataset=null;
	private Color preColor=null;/**保存标签的默认颜色*/ 
	private String  datasetid=null;/**保存此链接字的长度*/    

	public void setPath(File flink){
		this.filelink=flink;
	}
	public File getPath(){
		return this.filelink;
	}
	public void setText(String t){
		this.text=t;
	}
	public String getText(){
		return this.text;
	}
	public void setDataserid(String t){
		this.datasetid=t;
	}
	public String getDatasetid(){
		return this.datasetid;
	}
	/* 构造一个超链接      * @param vText 显示的文字      * @param vLink 连接地址      */ 
	public LinkLabel(String vText, File vLink) {         
		//super("<html>"+vText+"</html>");         
		this.text=vText;                        
		this.filelink=vLink;                  
		this.addMouseListener(new MouseAdapter() {    
		public void mouseExited(MouseEvent e) {  
			LinkLabel.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); 
			if(preColor!=null)                     
			LinkLabel.this.setForeground(preColor);
			//LinkLabel.this.setText("<html>"+text+"</html>");
			LinkLabel.this.setText(text);
				    	  }                     
	public void mouseEntered(MouseEvent e) { 
		LinkLabel.this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		preColor=LinkLabel.this.getForeground();                 
		LinkLabel.this.setForeground(Color.RED);
		//LinkLabel.this.setText("<html><u>"+text+"</u></html>");
		LinkLabel.this.setText(text);
		}                               
	public void mouseClicked(MouseEvent e) {
		if(filelink.exists()){
			  try{
			  Desktop.getDesktop().open(filelink);
			  }
			  catch (IOException er) {
					// TODO Auto-generated catch block
					er.printStackTrace();
				}   
		}
		else{
         
			try{
			AbstractAIFApplication app= AIFUtility.getCurrentApplication();
			TCSession session=(TCSession)app.getSession();
			TCComponentDatasetType tccomponentDatasetType=(TCComponentDatasetType) session.getTypeComponent("Dataset");
			dataset=tccomponentDatasetType.find(text);
			if(dataset==null){
				System.out.println("附件不存在");
				MessageBox.post("附件不存在","提示",MessageBox.INFORMATION);
				return;
			}
			TCComponentTcFile[] files=dataset.getTcFiles();
			if(files.length ==0){
				System.out.println("附件不存在");
				MessageBox.post("附件不存在","提示",MessageBox.INFORMATION);
				return;
			}
			else
			   {
			//	File fmsFile=files[0].getFmsFile();
			//	Desktop.getDesktop().open(fmsFile);
			    dataset.open();
			   }
			//dataset.openForView();
			//dataset.openForEdit();
          } catch (IOException er) {
				// TODO Auto-generated catch block
				er.printStackTrace();
			} catch (TCException err) {
			// TODO Auto-generated catch block
			   err.printStackTrace();
		}           
		}
		  }
	

	}
		);    
	} 
}	

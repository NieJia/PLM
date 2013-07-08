/**
 * @file ProgressBar.java
 *
 * @brief Create progress bar
 * 
 * @author Yanghui
 * 
 * @history 
 * ================================================================
 * Date               Name                    Description of Change
 * 25-July-2008       Yanghui              this class is used to create
 * 										   progress bar.
 */
package cn.com.origin.util;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.util.PropertyLayout;

/**
 * @class ProgressBar
 * @brief Create progress bar
 * @author Yanghui
 */
public class ProgressBar extends AbstractAIFDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @var ProgressBar.progressbar
	 * @brief JProgressBar
	 */
	private JProgressBar progressbar;

	/**
	 * @var ProgressBar.label
	 * @brief label used to tips
	 */
	private JLabel label;

	/**
	 * @var ProgressBar.timer
	 * @brief timer used to timing operation
	 */
	private Timer timer;

	/**
	 * @var ProgressBar.bool
	 * @brief bool used to flag thread return
	 */
	private boolean bool = false;

	/**
	 * @var Progressbar.registry
	 * @brief Registry
	 */
	// private Registry registry;
	/**
	 * @fn public ProgressBar()
	 * @brief constructor
	 * @param[in] null
	 */
	private String showLable = null;

	public ProgressBar(String showlable) {
		super(true);
		showLable = showlable;
	}

	/**
	 * @fn public void setBool(boolean bool)
	 * @brief set bool value
	 * @param[in] bool
	 * @param[out] null
	 */
	public void setBool(boolean bool) {
		this.bool = bool;
	}

	/**
	 * @fn private void initUI()
	 * @brief createDialog method
	 * @param[in] null
	 * @param[out] null
	 */
	public void initUI() {
		Container container = getContentPane();
		JPanel mainPanel = new JPanel(new PropertyLayout());
		label = new JLabel(showLable, JLabel.CENTER);
		progressbar = new JProgressBar();
		progressbar.setOrientation(JProgressBar.HORIZONTAL);
		progressbar.setMinimum(0);
		progressbar.setMaximum(100);
		progressbar.setValue(0);
		progressbar.setPreferredSize(new Dimension(400, 15));
		progressbar.setBorderPainted(true);
		timer = new Timer(50, (ActionListener) this);
		timer.setRepeats(false);
		mainPanel.add("1.1.center", new JLabel(" "));
		mainPanel.add("2.1.center", label);
		mainPanel.add("3.1.center", progressbar);
		mainPanel.add("4.1.center", new JLabel(" "));
		container.add(mainPanel);

		TaskThread thread = new TaskThread(this);
		thread.start();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				bool = true;
			}
		});
		pack();
		centerToScreen();
		setVisible(true);
	}

	/**
	 * @class TaskThread
	 * @brief Create progressbar
	 * 
	 */
	class TaskThread extends Thread {
		private ProgressBar bar;

		public TaskThread(ProgressBar bar) {
			this.bar = bar;
		}

		public void run() {
			if (bool == false) {
				// Set Status is running.
				// session.setStatus(registry.getString("export Running"));
			}
			for (int i = 0; i < i + 1; i++) {
				timer.start();
				int value = progressbar.getValue();
				if (value < 100) {
					value = value + 5;
					progressbar.setValue(value);
				} else {
					timer.stop();
					progressbar.setValue(0);
				}
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (bool == true) {
					bar.setVisible(false);
					bar.dispose();
					return;

				}

			}
		}
	}

	public void actionPerformed(ActionEvent arg0) {

	}
}

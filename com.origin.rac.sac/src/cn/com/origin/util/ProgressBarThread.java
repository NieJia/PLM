package cn.com.origin.util;


public class ProgressBarThread extends Thread {

	private ProgressBar bar;

	private String title;

	public ProgressBarThread(String title, String message) {
		this.title = title;
		bar = new ProgressBar(message);
	}

	public void run() {
		bar.setTitle(title);
		bar.initUI();
	}

	public void stopBar() {
		bar.setBool(true);
	}
}

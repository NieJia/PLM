package com.origin.rac.sac.autocadtopdf;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class JieFindFileDialog extends JFrame {
	private static final long serialVersionUID = 1L;

	public String init() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("Ñ¡ÔñÍ¼Ö½ÏÂÔØÂ·¾¶");
		chooser.showOpenDialog(this);
		File file = chooser.getSelectedFile();
		return file.getAbsolutePath().toString();
	}
}

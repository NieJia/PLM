package com.origin.rac.sac.eco;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class JieCopyFile {
	public void copyFile(File sourceFile, File targetFile) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}

	// 在指定的目录下，判断是否存在name的文件
	public Boolean isFileExist(String path, String name) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}

		String[] str = file.list();
		for (String string : str) {
			if (string.equals(name)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 复制文件(以超快的速度复制文件)
	 * @param srcFile
	 * 源文件File
	 * @param destDir
	 * 目标目录File
	 * @param newFileName
	 * 新文件名
	 * @return 实际复制的字节数，如果文件、目录不存在、文件为null或者发生IO异常，返回-1
	 */
	public String copyFile(File srcFile, File destDir, String newFileName) {

		long copySizes = 0;

		if (!srcFile.exists()) {
			System.out.println("源文件不存在");
			copySizes = -1;

		} else if (!destDir.exists()) {
			System.out.println("目标目录不存在");
			copySizes = -1;

		} else if (newFileName == null) {
			System.out.println("文件名为null");
			copySizes = -1;

		} else {

			try {
				FileInputStream fis = new FileInputStream(srcFile);
				FileOutputStream fos = new FileOutputStream(new File(destDir,
						newFileName));

				FileChannel fcin = fis.getChannel();
				FileChannel fcout = fos.getChannel();

				// long size = fcin.size();
				fcin.transferTo(0, fcin.size(), fcout);
				fcin.close();
				fcout.close();
				fis.close();
				fos.close();
				return destDir + "\\" + newFileName;

			} catch (FileNotFoundException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		return newFileName;
	}
}

package cn.com.origin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {
	public String readProperties(String key) throws IOException {
		Properties p = new Properties();
		InputStream in = null;
		in = getClass().getResourceAsStream("locale.properties");
		p.load(in);
		String value = p.getProperty(key);
		System.out.println(value);
		in.close();
		return value;
	}
}

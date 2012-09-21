package com.pluseq.reyad.bundle;

import java.io.*;
import java.util.Properties;

public class BundleManager {
	public Bundle fromPropertyFile(File file) throws FileNotFoundException, IOException
	{
		Properties properties = new Properties();
		properties.load(new FileInputStream(file));
		Bundle b = new Bundle();
		
		b.setBaseDirectory(file.getParent());
		b.setName(file.getName());
		return b;
	}
}

package com.sytecso.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.sytecso.config.logger.SytecsoLogger;
public class PropertiesFile {
	public final  Properties getPropValues(String propFileName) throws IOException {
		Properties prop = new Properties();
		File initialFile = new File(propFileName);
		InputStream inputStream = new FileInputStream(initialFile);
		try {
			prop.load(inputStream);
		} catch (Exception e) {
			SytecsoLogger.error("property file '" + propFileName + "' not found in the classpath",e);
		} finally {
			inputStream.close();
		}
		return prop;
	}
}

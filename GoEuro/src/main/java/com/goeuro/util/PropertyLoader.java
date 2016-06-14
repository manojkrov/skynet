package com.goeuro.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum PropertyLoader {

	INSTANCE;

	private Properties properties = new Properties();

	private PropertyLoader() {
		load();
	}

	public void load() {
		
		try (InputStream inStream = getClass().getClassLoader().getResourceAsStream(Constants.DEFAULT_FILEPATH)) {
			properties.load(inStream);
		} catch (IOException e) {
			throw new RuntimeException("Error loading properties ", e);
		} 

	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	public void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}
	

}

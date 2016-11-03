package com.ee.hotel.application.testsuite.framework;

public class SystemProperties {
	//Keys, set or override defaults from command line or ide using -Dkey=value
	private final static String BROWSERS_KEY = "browsers";
	
	//Default values
	private final static String BROWSERS_DEFAULT = "Firefox";
	
	//instantiations
	private final static String BROWSERS = System.getProperties().containsKey(BROWSERS_KEY) ? System.getProperty(BROWSERS_KEY) : BROWSERS_DEFAULT;
	
	//getter methods
	public static String getBrowsers() {
		return BROWSERS;
	}
	public static String getDefaultBrowser() {
		return BROWSERS_DEFAULT;
	}
}

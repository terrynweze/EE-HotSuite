package com.ee.hotel.application.testsuite.utils;

public final class SystemPropertyUtil {
	//Keys, set or override defaults from command line or ide using -Dkey=value
	private final static String baseUrlKey = "base.url";
	//Default values
	private final static String baseUrlDefault = "http://hotel-test.equalexperts.io/";

	
	//instantiations
	private final static String baseUrl = System.getProperties().containsKey(baseUrlKey) ? System.getProperty(baseUrlKey) : baseUrlDefault;
	
	//getter methods
	public static String getBaseUrl() {
		return baseUrl;
	}
	
}

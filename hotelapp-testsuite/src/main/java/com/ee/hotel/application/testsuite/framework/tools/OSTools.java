package com.ee.hotel.application.testsuite.framework.tools;

/**
 * @author Terry Nweze
 *
 * Helper class to detect which OS the tests are running under.
 */
public class OSTools {
	private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
	
	private OSTools() { /* Static Class not intended to be constructed */ }
	
	public static boolean isMac() {
		return OS_NAME.indexOf("mac") >= 0;
	}
	
	public static boolean isUnix() {
		return OS_NAME.indexOf("nux") >= 0 || OS_NAME.indexOf("nix") >= 0;
	}

}

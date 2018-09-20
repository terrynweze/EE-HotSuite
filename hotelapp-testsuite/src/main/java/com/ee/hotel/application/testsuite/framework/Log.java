package com.ee.hotel.application.testsuite.framework;

import org.testng.Reporter;

public class Log {
	public void logTestName(String browser, String testname){
		logBrowserName(browser.toUpperCase());
		logMessage("   TEST: " + testname.toUpperCase());
	}
	
	public void logTestStep(String msg){
		logMessage("     TEST STEP: " + msg);
	}
	
	public void logStepTrace(String msg){
		logMessage("       TRACE: " + msg);
	}
	
	public void logProblem(String msg){
		logMessage("         FAILURE: " + msg);
	}
	
	public void logSuccess(String msg){
		logMessage("         SUCCESS: " + msg);
	}
	
	public void logInfo(String msg){
		logMessage("         INFO: " + msg);
	}
	
	public void logBrowserName(String name){
		logMessage("TEST BROWSER: " + name);
	}
	
	private void logMessage(String msg){
		Reporter.log(msg);
	}
}

package com.ee.hotel.application.testsuite.framework;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import com.ee.hotel.application.testsuite.framework.Browser.Browsers;

public abstract class BaseTest {

	@BeforeMethod(alwaysRun = true)
	protected void beforeMethod(Object[] params) {
		SeDriver se = ((SeDriver) params[0]);
		Browsers myBrowser = se.currentBrowser();
		se.startSession(myBrowser);
	}

	@AfterMethod(alwaysRun = true)
	protected void afterMethod(Object[] params) {
		SeDriver se = ((SeDriver) params[0]);
		se.browser().quit();
	}

	@DataProvider(name = "DefaultDataProvider", parallel = true)
	public Object[][] DefaultDataProvider() {
		return createSeDriver(getBrowserList(SystemProperties.getBrowsers(), ","));
	}
	
	public static Object[][] createSeDriver(List<Browser.Browsers> browsers) {
        List<List<Object>> list = new ArrayList<List<Object>>();
        for (Browser.Browsers browser : browsers) {
        	List<Object> seHelper = new ArrayList<Object>();
        	SeDriver se = new SeDriver(browser);
            seHelper.add(se);
            list.add(seHelper);
        }

        return objectArrayFromList(list);
    }
	

	public static List<Browser.Browsers> getBrowserList(String delimitedString, String delimiter) {
		List<Browser.Browsers> list = new ArrayList<Browser.Browsers>();
		for (String cellContents : delimitedString.split(delimiter)) {
			try {
				list.add(Browser.Browsers.valueOf(cellContents));
			} catch (IllegalArgumentException e) {
				System.out.println("unrecognized browser in pom.xml: '" + cellContents + "' reverting to default browser: " + SystemProperties.getDefaultBrowser());
				list.add(Browser.Browsers.valueOf(SystemProperties.getDefaultBrowser()));
			}
		}
		return list;
	}
	
	private static Object[][] objectArrayFromList(List<List<Object>> list) {
   	 Object[][] objectArray = new Object[list.size()][];
        for (int row = 0; row < list.size(); row++) {
       	 objectArray[row] = list.get(row).toArray();
        }
        return objectArray;
   }

}

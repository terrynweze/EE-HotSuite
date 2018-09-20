package com.ee.hotel.application.testsuite.framework;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Browser {
	private SeDriver se;

	/**
	 * Supported Browsers
	 */
	public static enum Browsers {
		Firefox
	}

	public Browser(SeDriver se) {
		this.se = se;
	}

	/**
	 * Close browser and browser session.
	 * <p/>
	 * !! Should be called after all tests !!
	 */
	public void quit() {
		try {
			if (se.driver() instanceof RemoteWebDriver && ((RemoteWebDriver) se.driver()).getSessionId() != null) {
				// se.driver().close();
				se.driver().quit();
			} else {
				se.driver().quit();
			}
		} catch (Exception e) {
			// chrome drive quit throws errors when window is already closed
		}
	}

	/**
	 * Load a new url in the browser.
	 * 
	 * @param url
	 */
	public boolean get(String url) {
		if (url == null) {
			return false;
		}
		try {
			se.driver().get(url);
		} catch (Throwable t) {
			System.out.println(t);
			return false;
		}
		if (se.driver().getTitle() == null || se.driver().getTitle().equals("Problem loading page")
				|| se.driver().getTitle().contains("Oops! Google Chrome could not connect to")
				|| se.driver().getTitle().equals("Internet Explorer cannot display the webpage")) {
			return false;
		} else
			return true;
	}

	/**
	 * Executes javascript on an element and returns the result as a java String
	 *
	 * @param javascript
	 *            snippit of javascript that returns a string
	 * @param webElement
	 *            Element to execute the javascript on, reference in snippit as
	 *            "arguments[0]"
	 * @return Returned value of the snippit
	 */
	public String executeJavascriptOnWebElement(String javascript, WebElement webElement) {
		try {
			Object result = ((JavascriptExecutor) se.driver()).executeScript(javascript, webElement);
			if (result != null)
				return result.toString();
			else
				return "";
		} catch (Throwable t) {
			System.out.println(t);
			return "";
		}

	}

	/**
	 * Refresh current page
	 */
	public void refresh() {
		se.driver().navigate().refresh();
	}
}

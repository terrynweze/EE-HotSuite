package com.ee.hotel.application.testsuite.framework;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.ee.hotel.application.testsuite.framework.Browser.Browsers;
import com.ee.hotel.application.testsuite.framework.tools.OSTools;

public class SeDriver {
	private WebDriver driver;
	private final Browser browser;
	private final Element element;
	private final Verification verification;
	private final Log log;

	private Browser.Browsers currentBrowser;

	DesiredCapabilities capabilities;

	public SeDriver(Browsers browserType) {
		setBrowserType(browserType);
		browser = new Browser(this);
		element = new Element(this);
		verification = new Verification(this);
		log = new Log();
	}

	/**
	 * Set the device type
	 * 
	 * @param deviceType
	 */
	public void setBrowserType(Browsers browserType) {
		this.currentBrowser = browserType;
	}

	/**
	 * Current Browser Type for this session
	 */
	public Browsers currentBrowser() {
		return currentBrowser;
	}

	public Browser browser() {
		return browser;
	}

	/**
	 * WebDriver object backing this SeHelper object;
	 */
	public WebDriver driver() {
		return driver;
	}

	/**
	 * Element object backing this SeHelper object;
	 */
	public Element element() {
		return element;
	}

	/**
	 * Verification object backing this SeHelper object;
	 */
	public Verification verify() {
		return verification;
	}
	
	/**
	 * Log object backing this SeHelper object;
	 */
	public Log log() {
		return log;
	}

	/**
	 * Starts a Browser session using the specified Browser
	 *
	 * @param myBrowser
	 *            Browser to create for
	 */
	public void startSession(Browsers myBrowser) {
		Proxy proxy = new Proxy();
		proxy.setProxyType(ProxyType.AUTODETECT);
		startSession(myBrowser, proxy);
	}

	/**
	 * Create a new WebDriver object for the specified browser and pointed at
	 * the specific proxy
	 * 
	 * @param myBrowser
	 *            Browser to create for
	 * @param proxy
	 *            Proxy to use
	 */
	public void startSession(Browsers myBrowser, Proxy proxy) {
		startSession(myBrowser, buildDriver(myBrowser, proxy));
	}

	/**
	 * Starts a Browser session using the specified Browser and pre-created
	 * WebDriver
	 *
	 * @param myBrowser
	 *            Browser to create for
	 * @param driver
	 *            WebDriver to use
	 */
	public void startSession(Browsers myBrowser, WebDriver driver) {
		this.currentBrowser = myBrowser;
		this.driver = driver;
	}

	private WebDriver buildDriver(Browsers myBrowser, Proxy proxy) {
		switch (myBrowser) {
		case Firefox:
			// set geko driver path
			setGekoDriverPathProperty();

			capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", true);
			capabilities.setCapability(CapabilityType.PROXY, proxy);
			capabilities.setCapability(FirefoxDriver.PROFILE, getFirefoxProfile());
			return new FirefoxDriver(capabilities);
		default:
			return null;
		}
	}

	private void setGekoDriverPathProperty() {
		System.setProperty("webdriver.gecko.driver", getGekodriverPath());
	}

	private String getGekodriverPath() {
		String gekodriverFilename = "geckodriver.exe";

		if (OSTools.isMac() || OSTools.isUnix())
			gekodriverFilename = "geckodriver";

		return System.getProperty("user.dir") + "/drivers/" + gekodriverFilename;
	}

	/**
	 * Builds a standard firefox profile
	 * 
	 * @return standard firefox profile
	 */
	private FirefoxProfile getFirefoxProfile() {
		ProfilesIni allProfiles = new ProfilesIni();
		FirefoxProfile profile = allProfiles.getProfile("default");
		if (profile == null)
			profile = allProfiles.getProfile("default");
		//profile.setEnableNativeEvents(false);
		return getFirefoxProfile(profile);
	}

	/**
	 * Builds a bew firefox profile
	 * 
	 * @return standard firefox profile
	 */
	private FirefoxProfile getFirefoxProfile(FirefoxProfile profile) {
		if (profile == null)
			profile = new FirefoxProfile();
		//profile.setEnableNativeEvents(false);
		return profile;
	}
}

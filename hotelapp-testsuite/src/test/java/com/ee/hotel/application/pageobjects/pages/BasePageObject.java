package com.ee.hotel.application.pageobjects.pages;

import com.ee.hotel.application.testsuite.framework.SeDriver;
import com.ee.hotel.application.testsuite.utils.SystemPropertyUtil;

public abstract class BasePageObject {

	private String url = SystemPropertyUtil.getBaseUrl();
	protected SeDriver se;

	public BasePageObject(SeDriver se) {
		this.se = se;
	}

	public BasePageObject(SeDriver se, String url) {
		this.se = se;
		this.url = url;
	}

	/**
	 * navigate to the base url
	 * 
	 * @return
	 */
	protected BasePageObject navigateTo() {
		if (url.length() > 0 && se.browser().get(url))
			return this;
		else
			return null;
	}

	public String getUrl() {
		return url;
	}	
}

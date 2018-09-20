package com.ee.hotel.application.testsuite.framework;

import org.testng.Assert;

public class Verification {
	private SeDriver se;

	public Verification(SeDriver se) {
		this.se = se;
	}

	public void assertEquals(String testname, boolean actual, boolean expected) {
		Assert.assertEquals(actual, expected, testname);
	}

	public void assertTrue(String testname, boolean actual) {
		assertEquals(testname, actual, true);
	}

	public void assertFalse(String testname, boolean actual) {
		assertEquals(testname, actual, false);
	}
}

package com.ee.hotel.application.testsuite.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Booking {
	private String firstname;
	private String lastname;
	private String price;
	private String deposit;
	private String checkin;
	private String checkout;

	public Booking() {
	}

	public Booking(String firstname, String lastname) {
		String deposit;

		if (getRandomBoolean()) {
			deposit = "true";
		} else {
			deposit = "false";
		}

		setFirstname(firstname);
		setLastname(lastname);
		setPrice(new Integer(new Random().nextInt(2000) + 1000).toString());
		setDeposit(deposit);
		setCheckin(getDate(1));
		
		int cDate = new Random().nextInt(15) + 2;
		setCheckout(getDate(cDate));
	}

	public Booking(String firstname, String lastname, String price, String deposit, String checkin, String checkout) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.price = price;
		this.deposit = deposit;
		this.checkin = checkin;
		this.checkout = checkout;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPrice() {
		return this.price;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public String getDeposit() {
		return this.deposit;
	}

	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}

	public String getCheckin() {
		return this.checkin;
	}

	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}

	public String getCheckout() {
		return this.checkout;
	}
	
	public boolean getRandomBoolean() {
		return Math.random() < 0.5;
	}
	
	public String getDate(int addition) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		c.add(Calendar.DATE, addition);
		Date d = c.getTime();
		
		String strDate = sdfDate.format(d);
	    return strDate;
	}
}

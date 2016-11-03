package com.ee.hotel.application.testsuite.tests;

import org.testng.annotations.Test;

import com.ee.hotel.application.pageobjects.pages.BookingPage;
import com.ee.hotel.application.testsuite.domain.Booking;
import com.ee.hotel.application.testsuite.framework.BaseTest;
import com.ee.hotel.application.testsuite.framework.SeDriver;

public class HotelBookingTests extends BaseTest {

	@Test(description = "", dataProvider = "DefaultDataProvider", groups = { "sanity" })
	public void CorrectBookingFormElementsDisplayed(SeDriver se) {
		se.log().logTestName(se.currentBrowser().toString(),"Correct Booking Form Elements Ares Displayed");
		
		BookingPage bookingPage = new BookingPage(se);
		
		bookingPage.givenIAmOnTheBookingPage().
			thenTheCorrectFormFieldsAreDisplayed();
	}
	
	@Test(description = "", dataProvider = "DefaultDataProvider", groups = { "sanity" })
	public void SubmitAValidBooking(SeDriver se) {
		se.log().logTestName(se.currentBrowser().toString(),"Submit A Valid Booking");
		
		Booking booking = new Booking("Test", "Booking");
		BookingPage bookingPage = new BookingPage(se);
		
		bookingPage.givenIAmOnTheBookingPage().
			whenIsubmitValidBooking(booking).
			thenANewBookingIsAdded(booking);
	}
	
	@Test(description = "", dataProvider = "DefaultDataProvider", groups = { "sanity" })
	public void DeleteABooking(SeDriver se) {
		se.log().logTestName(se.currentBrowser().toString(),"Delete A Booking");
		
		Booking booking = new Booking("Delete", "Booking");
		BookingPage bookingPage = new BookingPage(se);
		
		bookingPage.givenABookingExistsFor(booking).
			whenIDeleteTheBooking(booking).
			thenTheBookingIsNotDisplayed(booking);
	}
}

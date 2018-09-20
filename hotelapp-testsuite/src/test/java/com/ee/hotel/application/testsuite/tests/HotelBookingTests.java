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
	
	@Test(description = "", dataProvider = "DefaultDataProvider", groups = { "sanity" })
	public void PriceAcceptsSingleDecimalPoint(SeDriver se) {
		se.log().logTestName(se.currentBrowser().toString(),"Price field accepts single decimal point");
		
		Booking booking = new Booking("Single", "Decimal");
		booking.setPrice("22.22");
		BookingPage bookingPage = new BookingPage(se);
		
		bookingPage.givenICreateANewBooking(booking, BookingPage.EXPECTED_SUCCESS).
			thenANewBookingIsAdded(booking);
	}
	
	@Test(description = "", dataProvider = "DefaultDataProvider", groups = { "sanity" })
	public void MultipleDecimalsInPriceNotAccepted(SeDriver se) {
		se.log().logTestName(se.currentBrowser().toString(),"Multiple Decimals In Price Not Accepted");
		
		Booking booking = new Booking("Multiple", "Decimals");
		booking.setPrice("22.2.2");
		BookingPage bookingPage = new BookingPage(se);
		
		bookingPage.givenICreateANewBooking(booking, BookingPage.EXPECTED_FAILURE).
			thenTheBookingIsNotDisplayed(booking);
	}
	
	@Test(description = "", dataProvider = "DefaultDataProvider", groups = { "sanity" })
	public void CharactersInPriceNotAccepted(SeDriver se) {
		se.log().logTestName(se.currentBrowser().toString(),"Characters In Price Not Accepted");
		
		Booking booking = new Booking("characters", "in-price");
		booking.setPrice("abc");
		BookingPage bookingPage = new BookingPage(se);
		
		bookingPage.givenICreateANewBooking(booking, BookingPage.EXPECTED_FAILURE).
			thenTheBookingIsNotDisplayed(booking);
	}
	
	@Test(description = "", dataProvider = "DefaultDataProvider", groups = { "sanity" })
	public void UnableToCreateBookingsInThePast(SeDriver se) {
		se.log().logTestName(se.currentBrowser().toString(),"Unable to create bookings in the past");
		
		Booking booking = new Booking("past", "booking");
		booking.setCheckin(booking.getDate(-4));
		BookingPage bookingPage = new BookingPage(se);
		
		bookingPage.givenICreateANewBooking(booking, BookingPage.EXPECTED_FAILURE).
			thenTheBookingIsNotDisplayed(booking);
	}
}

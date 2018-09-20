package com.ee.hotel.application.pageobjects.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ee.hotel.application.testsuite.domain.Booking;
import com.ee.hotel.application.testsuite.framework.SeDriver;

public class BookingPage extends BasePageObject {
	public final static boolean EXPECTED_SUCCESS = true;
	public final static boolean EXPECTED_FAILURE = false;
	
	// elements
	private By BOOKINGPAGE_LOCATOR = By.id("form");

	private By FIRSTNAME_INPUT_FIELD = By.id("firstname");
	private By SURNAME_INPUT_FIELD = By.id("lastname");
	private By PRICE_INPUT_FIELD = By.id("totalprice");
	private By DEPOSIT_DROPDOWN_MENU = By.id("depositpaid");
	private By CHECKIN_INPUT_FIELD = By.id("checkin");
	private By CHECKOUT_INPUT_FIELD = By.id("checkout");
	private By SAVE_BTN = By.cssSelector("#form > .row > div:nth-of-type(7) > input");
	
	

	public BookingPage(SeDriver se) {
		super(se);
	}

	protected BookingPage navigateTo() {
		se.log().logStepTrace("Navigating to Booking Page");
		if (super.navigateTo() != null)
			return this;
		else
			return null;
	}

	protected BookingPage waitForPageToLoad(int... secondsToWait) {
		// determine how long to wait
		int secsToWait = se.element().getTimeOut();
		if (secondsToWait.length > 0) {
			secsToWait = secondsToWait[0];
		}

		// perform the wait
		if (se.element().waitForElementToBeClickable(BOOKINGPAGE_LOCATOR, secsToWait) != null)
			return this;
		else
			return null;
	}

	// steps
	// Given
	public BookingPage givenIAmOnTheBookingPage() {
		se.log().logTestStep("Given I Am On The Booking Page");
		if (navigateTo().waitForPageToLoad() != null)
			return this;
		else
			return null;

	}
	
	public BookingPage givenICreateANewBooking(Booking booking, boolean expectedResult) {
		se.log().logTestStep("Given I create a new booking");
		
		if (expectedResult) {
			return attemptToCreateValidBooking(booking);
		}
		
		return attemptToCreateInValidBooking(booking);		
	}
	
	public BookingPage attemptToCreateValidBooking(Booking booking) {
		se.log().logStepTrace("Attempting to create a valid booking");
		if (navigateTo().waitForPageToLoad() != null){
			if (submitBooking(booking, true)){
				se.log().logSuccess("Booking created for: " + generateBookingLogString(booking));
				return this;
			}
			se.log().logProblem("Unable to make booking for: " + generateBookingLogString(booking));
			return this;
		}
		else{
			return null;
		}
	}
	
	public BookingPage attemptToCreateInValidBooking(Booking booking) {
		se.log().logStepTrace("Attempting to create an invalid booking");
		if (navigateTo().waitForPageToLoad() != null){
			if (submitBooking(booking, false)){
				se.log().logProblem("Booking was created for: " + generateBookingLogString(booking));
				return this;
			}
			se.log().logSuccess("Unable to make booking for: " + generateBookingLogString(booking));
			return this;
		}
		else{
			return null;
		}
	}

	public BookingPage givenABookingExistsFor(Booking booking) {
		se.log().logTestStep("Given A Booking Exists For A Provided Booking");
		
		if (navigateTo().waitForPageToLoad() != null){
			if (submitBooking(booking, true)){
				se.log().logSuccess("Booking exists for: " + generateBookingLogString(booking));
				return this;
			}
			return null;
		}
		else{
			return null;
		}

		
	}

	public BookingPage whenIsubmitValidBooking(Booking booking) {
		se.log().logTestStep("When I Submit A Valid Booking");
		
		if (submitBooking(booking, true))
			return this;
		return null;
	}

	public BookingPage whenIDeleteTheBooking(Booking booking) {
		se.log().logTestStep("When I Delete The Booking");
		
		if (deleteBooking(booking)){
			se.log().logSuccess("Booking deleted for booking: " + generateBookingLogString(booking));
		} else{
			se.log().logProblem("Booking NOT deleted for booking: " + generateBookingLogString(booking));
		}
			
		return this;
	}

	public void thenANewBookingIsAdded(Booking booking) {
		se.log().logTestStep("Then A New Booking Is Added");
		
		se.element().sleep(2 * 1000);
		se.browser().refresh();
		se.verify().assertTrue("The booking is present: ", isBookingPresent(booking, true));

	}

	public void thenTheBookingIsNotDisplayed(Booking booking) {
		se.log().logTestStep("Then The Booking Is Not Displayed");
		
		se.element().sleep(2 * 1000);
		se.browser().refresh();

		se.verify().assertFalse("The booking is present: ", isBookingPresent(booking, false));
	}

	public void thenTheCorrectFormFieldsAreDisplayed() {
		se.log().logTestStep("Then The Correct Form Fields Are Displayed");
		
		se.verify().assertTrue("The Firstname input field is displayed: ",
				se.element().isVisible(FIRSTNAME_INPUT_FIELD, 3));
		se.verify().assertTrue("The Lasttname input field is displayed: ",
				se.element().isVisible(SURNAME_INPUT_FIELD, 3));
		se.verify().assertTrue("The Price input field is displayed: ", se.element().isVisible(PRICE_INPUT_FIELD, 3));
		se.verify().assertTrue("The Deposit drop down menu is displayed: ",
				se.element().isVisible(DEPOSIT_DROPDOWN_MENU, 3));
		se.verify().assertTrue("The Check In input field is displayed: ",
				se.element().isVisible(CHECKIN_INPUT_FIELD, 3));
		se.verify().assertTrue("The Check Out input field is displayed: ",
				se.element().isVisible(CHECKOUT_INPUT_FIELD, 3));
		se.verify().assertTrue("The Save button is displayed: ", se.element().isVisible(SAVE_BTN, 3));
	}
	
	//Micro Steps
	
	public boolean submitBooking(Booking booking, boolean expected) {
		int errors = 0;
		String fName = booking.getFirstname();
		String lName = booking.getLastname();
		String price = booking.getPrice();
		String deposit = booking.getDeposit();
		String checkin = booking.getCheckin();
		String checkout = booking.getCheckout();

		if (se.element().enterText(FIRSTNAME_INPUT_FIELD, fName) == null)
			errors++;
		if (se.element().enterText(SURNAME_INPUT_FIELD, lName) == null)
			errors++;
		if (se.element().enterText(PRICE_INPUT_FIELD, price) == null)
			errors++;
		if (se.element().selectElement(DEPOSIT_DROPDOWN_MENU, deposit) == null)
			errors++;
		if (se.element().enterText(CHECKIN_INPUT_FIELD, checkin) == null)
			errors++;
		if (se.element().enterText(CHECKOUT_INPUT_FIELD, checkout) == null)
			errors++;
		if (errors < 1) {
			if (submitForm()) {
				se.log().logInfo("Submitted Booking for: " + generateBookingLogString(booking));
				if(isBookingPresent(booking, expected)) {
					return true;
				}
				return false;
				
			}
		}
		
		se.log().logProblem("Problem Submitting Booking for: "+ generateBookingLogString(booking));
		return false;
	}

	public boolean submitForm() {
		return se.element().clickElement(SAVE_BTN) != null;
	}

	public List<WebElement> getBookings() {
		return se.element().getElements(By.cssSelector("#bookings > .row"));
	}

	public boolean isBookingPresent(Booking booking, boolean expected) {
		se.browser().refresh();
		se.element().sleep(4 * 1000);
		List<WebElement> bookings = getBookings();
		
		for (int i = 0; i < bookings.size(); i++) {
			String fName = se.element()
					.getChildElement(bookings.get(i),
							By.cssSelector("#bookings > div:nth-of-type(" + (i + 1) + ") > div:nth-of-type(1)"))
					.getText();
			String lName = se.element()
					.getChildElement(bookings.get(i),
							By.cssSelector("#bookings > div:nth-of-type(" + (i + 1) + ") > div:nth-of-type(2)"))
					.getText();
			String price = se.element()
					.getChildElement(bookings.get(i),
							By.cssSelector("#bookings > div:nth-of-type(" + (i + 1) + ") > div:nth-of-type(3)"))
					.getText();
			String deposit = se.element()
					.getChildElement(bookings.get(i),
							By.cssSelector("#bookings > div:nth-of-type(" + (i + 1) + ") > div:nth-of-type(4)"))
					.getText();
			String checkin = se.element()
					.getChildElement(bookings.get(i),
							By.cssSelector("#bookings > div:nth-of-type(" + (i + 1) + ") > div:nth-of-type(5)"))
					.getText();
			String checkout = se.element()
					.getChildElement(bookings.get(i),
							By.cssSelector("#bookings > div:nth-of-type(" + (i + 1) + ") > div:nth-of-type(6)"))
					.getText();

			if(isBookingPresent(booking, fName, lName, price, deposit,checkin, checkout)){
				se.log().logInfo("Booking has been located for: " + generateBookingLogString(booking));
				return true;	
			}
			
		}
		
		se.log().logInfo("Unable to locate Booking for: "+ generateBookingLogString(booking));
		
		if(expected){
			se.log().logProblem("Booking is not present for: " + generateBookingLogString(booking));
		}else{
			se.log().logSuccess("Booking is not present as expected for: "+ generateBookingLogString(booking));
		}
		
		return false;
		
	}

	public boolean deleteBooking(Booking booking) {
		se.log().logStepTrace("Deleting Booking");
		se.element().sleep(4 * 1000);
		List<WebElement> bookings = getBookings();
		boolean found = false;
		int retryCount = 3;
		
		for (int count = 0; found == false  && count < retryCount; count++){
			for (int i = 0; i < bookings.size(); i++) {
				String fName = se.element()
						.getChildElement(bookings.get(i),
								By.cssSelector("#bookings > div:nth-of-type(" + (i + 1) + ") > div:nth-of-type(1)"))
						.getText();
				String lName = se.element()
						.getChildElement(bookings.get(i),
								By.cssSelector("#bookings > div:nth-of-type(" + (i + 1) + ") > div:nth-of-type(2)"))
						.getText();
				String price = se.element()
						.getChildElement(bookings.get(i),
								By.cssSelector("#bookings > div:nth-of-type(" + (i + 1) + ") > div:nth-of-type(3)"))
						.getText();
				String deposit = se.element()
						.getChildElement(bookings.get(i),
								By.cssSelector("#bookings > div:nth-of-type(" + (i + 1) + ") > div:nth-of-type(4)"))
						.getText();
				String checkin = se.element()
						.getChildElement(bookings.get(i),
								By.cssSelector("#bookings > div:nth-of-type(" + (i + 1) + ") > div:nth-of-type(5)"))
						.getText();
				String checkout = se.element()
						.getChildElement(bookings.get(i),
								By.cssSelector("#bookings > div:nth-of-type(" + (i + 1) + ") > div:nth-of-type(6)"))
						.getText();

				if(isBookingPresent(booking, fName, lName, price, deposit,checkin, checkout)){
					if(se.element().clickChildElement(bookings.get(i),
							By.cssSelector("#bookings > div:nth-of-type(" + (i + 1) + ") > div:nth-of-type(7) > input")) != null){
						found = true;
					} 
				}
			}
		}
		
		if(found){
			se.log().logSuccess("Deleted Booking for Booking: " + generateBookingLogString(booking));
		} else{
			se.log().logProblem("Unable to locate Booking for: "+ generateBookingLogString(booking));
		}
		
		return found;
	}
	
	private boolean isBookingPresent(Booking booking, String fName, String lName, String price, String deposit, String checkin, String checkout){
		if (booking.getFirstname().equalsIgnoreCase(fName) && booking.getLastname().equalsIgnoreCase(lName)
				&& booking.getPrice().equalsIgnoreCase(price) && booking.getDeposit().equalsIgnoreCase(deposit)
				&& booking.getCheckin().equalsIgnoreCase(checkin)
				&& booking.getCheckout().equalsIgnoreCase(checkout)) {
			return true;
		}
		return false;
	}
	
	public String generateBookingLogString(Booking booking){
		String fName = booking.getFirstname();
		String lName = booking.getLastname();
		String price = booking.getPrice();
		String deposit = booking.getDeposit();
		String checkin = booking.getCheckin();
		String checkout = booking.getCheckout();
		
		return "'" + fName + " " +  lName + 
				"' between the dates: (" + checkin + " & " + checkout + ") at a price of: '" +
				price + "' with a deposit status of: '" + deposit + ".";
	}

}

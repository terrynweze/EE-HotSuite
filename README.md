EE-Hotel-booking-suite
=====================================

This is automation suite to test the creation and deletion of a hotel booking. 

The project has 7 tests that can be located within the test file: com.ee.hotel.application.testsuite.tests.HotelBookingTests.java

Test 1 - 'Correct Booking Form Elements Ares Displayed'

	Navigates to the Booking Page and verifies the correct form elements are displayed.
Test 2 - 'Submit A Valid Booking'

	Navigates to the Booking Page and adds a booking using valid data.
Test 3 - 'Delete A Booking'
	
	This test first creates a booking and then deletes it.
Test 4 - 'Price field accepts single decimal point'
	
	This test attempts to create a booking that has a single decimal point in the price field and verifies the booking was made successfully.
Test 5 - 'Multiple Decimals In Price Not Accepted'
	
	This test attempts to create a booking which has more than 1 decimal point within the price field and ensures the booking is not accepted.
Test 6 - 'Characters In Price Not Accepted'
	
	This test attempts to create a booking which has characters in the price field and ensures the booking is not accepted.
Test 7 - 'Unable to create bookings in the past'
	
	This test case attempts to create a booking in the past and verifies you are unable to do so. 
	(This test fails in the suite as there is a defect in the application that allows you to create bookings in the past)

The test suite makes use of Maven and TestNG with the report being generated using ReportNG.
Interaction with the browser is done so using Selenium WebDriver and the Page Object Model has been adopted.

```
You will need:

- Java 1.8 installed (Does not work with Java below 1.8)
- Maven Installed (I use version 3.3.9)
- Eclipse (Or another Java IDE)
- Firefox Browser Installed (Version 47 or above)

#### Important: This suite should work on both windows and mac platforms however has only been tested on a Mac. If possible please use a Mac to execute the test suite

```

In order to execute the automation suite navigate to the Project directory within a Terminal/CMD window and run the command: *'mvn clean test'*.

3 tests will execute in parallel. Report file can be found *'target/html/index.html'*

At present the suite has been configured to execute against the Firefox browser. It has been developed in such a way that it is simple to add other browsers to the Framework e.g. adding an Enum reference within the Browser.java file and instantiating the browse within the SeDriver.java file.

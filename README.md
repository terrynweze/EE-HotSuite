EE-Hotel-booking-suite
=====================================

This is automation suite to test the creation and deletion of a hotel booking. 

The project has 3 tests that can be located within the test file: com.ee.hotel.application.testsuite.tests.HotelBookingTests.java

Test 1 - 'Correct Booking Form Elements Ares Displayed'

	Navigates to the Booking Page and verifies the correct form elements are displayed.
Test 2 - 'Submit A Valid Booking'

	Navigates to the Booking Page and adds a booking using valid data.
Test 3 - 'Delete A Booking'
	
	This test first creates a booking and then deletes it.

The test suite makes use of Maven and TestNG with the report being generated using ReportNG.
Interaction with the browser is done so using Selenium WebDriver and the Page Object Model has been adopted.

```
You will need:

- Java 1.8 installed (Does not work with Java below 1.8)
- Maven Installed (I use version 3.3.9)
- Eclipse (Or another Java IDE)
- Firefox Browser Installed (Version 47 or above)
```

In order to execute the automation suite navigate to the Project directory within a Terminal/CMD window and run the command: *'mvn clean test'*.

The 3 tests will execute in parallel. Report file can be found *'target/html/index.html'*

At present the suite has been configured to execute against the Firefox borwser. It has been developed in such a way that it is simple to add other browsers to the Framework e.g. adding an Enum reference within the Browser.java file and instantiating the browse within the SeDriver.java file.

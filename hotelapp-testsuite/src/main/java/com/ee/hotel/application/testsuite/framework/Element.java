package com.ee.hotel.application.testsuite.framework;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;

import com.ee.hotel.application.testsuite.framework.Browser.Browsers;

public class Element {
	private SeDriver se;
	private int defaultTimeOut = 20;
    private int globalSeTimeOut = 20;
    private int typeDelay = 5;
    
    public static String mouseOverEvent = "var element = arguments[0];"
			+ "var mouseEventObj = document.createEvent('MouseEvents');"
			+ "mouseEventObj.initEvent( 'mouseover', true, true );"
			+ "element.dispatchEvent(mouseEventObj);";
	
	public Element(SeDriver se){
		this.se = se;
	}
	
	/**
     * Waits for a certain text to show up within a certain element
     *
     * @param locator - The element to wait for
     * @param text    - The text to wait for
     * @return true if element with text is found within the timeout window
     */
    private static ExpectedCondition<Boolean> containsText(final By locator, final String text) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                List<WebElement> elements = driver.findElements(locator);
                for (WebElement webElement : elements) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", webElement);
                    if (santizeText(webElement.getText()).toLowerCase().contains(santizeText(text.toLowerCase())))
                        return true;
                }
                return false;
            }
            public String toString() {
                return String.format("element found by %s to contain text %s", locator, text);
            }
        };
    }
    private static ExpectedCondition<Boolean> containsText(final WebElement parentElement, final By locator, final String text) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
            	List<WebElement> elements = parentElement.findElements(locator);
                for (WebElement element : elements) {
                	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", element);
                    if (santizeText(element.getText()).toLowerCase().contains(santizeText(text.toLowerCase())))
                        return true;
                }
                return false;
                
            }
            public String toString() {
                return String.format("child element of %s found by %s to contain text %s",
                		parentElement.getAttribute("class") , locator, text);
            }
        };
    }
    private static ExpectedCondition<Boolean> containsText(final WebElement parentElement, final By locator, final int nthElement, final String text) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
            	WebElement element = parentElement.findElements(locator).get(nthElement);
            	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", element);
                if (santizeText(element.getText()).toLowerCase().contains(santizeText(text.toLowerCase())))
                    return true;
                return false;
            }
            public String toString() {
                return String.format("(%d) child element of %s found by %s to contain text %s", 
                		nthElement, parentElement.getAttribute("class") , locator, text);
            }
        };
    }

    private static ExpectedCondition<WebElement> getElementContainsText(final By locator, final String text) {
        return new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver driver) {
                List<WebElement> elements = driver.findElements(locator);
                for (WebElement webElement : elements) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", webElement);
                    if (webElement.getText().contains(text))
                        return webElement;
                }
                return null;
            }
            public String toString() {
                return String.format("element found by %s to contain text %s", locator, text);
            }
        };
    }

    private static ExpectedCondition<Boolean> containsExactText(final By locator, final String text) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                List<WebElement> elements = driver.findElements(locator);
                for (WebElement webElement : elements) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", webElement);
                    if (santizeText(webElement.getText()).equals(santizeText(text)))
                        return true;
                }
                return false;
            }
            public String toString() {
                return String.format("element found by %s to contain exact text %s", locator, text);
            }
        };
    }

    private static ExpectedCondition<Boolean> textLengthIsGreaterThan(final By locator, final int textLength) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                List<WebElement> elements = driver.findElements(locator);
                for (WebElement webElement : elements) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", webElement);
                    return webElement.getText().length() > textLength;
                }
                return false;
            }
            public String toString() {
                return String.format("element found by %s to have length greater than %d", locator, textLength);
            }
        };
    }

    private static ExpectedCondition<Boolean> containsValue(final By locator, final String value) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                List<WebElement> elements = driver.findElements(locator);
                for (WebElement webElement : elements) {
                	
                    if (santizeText(webElement.getAttribute("value")).contains(santizeText(value)))
                        return true;
                }
                return false;
            }
            public String toString() {
                return String.format("element found by %s to contain value %s", locator, value);
            }
        };
    }
    private static ExpectedCondition<Boolean> containsValue(final WebElement parentElement, final By locator, final String text) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
            	List<WebElement> elements = parentElement.findElements(locator);
                for (WebElement element : elements) {
                	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", element);
                    if (santizeText(element.getAttribute("value")).toLowerCase().contains(santizeText(text.toLowerCase())))
                        return true;
                }
                return false;
            }
            public String toString() {
                return String.format("child element of %s found by %s to contain value %s", 
                		parentElement.getAttribute("class") , locator, text);
            }
        };
    }
    /**
     * Return child element if it is displayed, or return null if not found or not displayed.
     * @param parentElement
     * @param locator
     * @return
     */
    private static ExpectedCondition<WebElement> childIsLoaded(final WebElement parentElement, final By locator) {
    	return new ExpectedCondition<WebElement>() {
        	public WebElement apply(WebDriver driver) {
            	WebElement element = parentElement.findElement(locator);
                if (element.isDisplayed() && element.isEnabled())
            	   return element;
               return null;
            }
            public String toString() {
                return String.format("child element of %s found by %s is loaded", 
                		parentElement.getAttribute("class") , locator);
            }
        };
    }
    /**
     * Return child element if it is displayed, or return null if not found or not displayed.
     * @param parentElement
     * @param locator
     * @return
     */
    private static ExpectedCondition<WebElement> childIsLoaded(final WebElement parentElement, final By locator, final int index) {
    	return new ExpectedCondition<WebElement>() {
        	public WebElement apply(WebDriver driver) {
            	List<WebElement> elements = parentElement.findElements(locator);
                if (elements.size() > 0 && elements.get(index).isDisplayed() && elements.get(index).isEnabled())
            	   return elements.get(index);
               return null;
            }
            public String toString() {
                return String.format("(%d) child element of %s found by %s is loaded", 
                		index, parentElement.getAttribute("class") , locator);
            }
        };
    }
    private static ExpectedCondition<WebElement> childIsLoaded(final By parentElement, final By locator, final int index) {
    	return new ExpectedCondition<WebElement>() {
        	public WebElement apply(WebDriver driver) {
            	List<WebElement> elements = driver.findElement(parentElement).findElements(locator);
                if (elements.size() -1 >= index && elements.get(index).isDisplayed() && elements.get(index).isEnabled())
            	   return elements.get(index);
               return null;
            }
            public String toString() {
                return String.format("(%d) child element of %s found by %s is loaded", 
                		index, parentElement, locator);
            }
        };
    }

    /**
     * Waits for a certain text to show up within a certain element
     *
     * @param locator    - The element to wait for
     * @param nthElement
     * @param text       - The text to wait for
     * @return true if element with text is found within the timeout window
     */
    private static ExpectedCondition<Boolean> containsText(final By locator, final int nthElement, final String text) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                List<WebElement> elements = driver.findElements(locator);
                if (elements.size() < nthElement + 1)
                    return false;
                try {
                    WebElement element = elements.get(nthElement);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", element);
                    return element.getText().contains(text);
                } catch (Exception e) {
                    return false;
                }
            }
            public String toString() {
                return String.format("(%d) element found by %s is contains text %s", 
                		nthElement, locator, text);
            }
        };
    }
    

    private static ExpectedCondition<Boolean> elementIsDisplayed(final WebElement element) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return element.isDisplayed();
            }
            public String toString() {
                return String.format("element %s is displayed", 
                		element.getAttribute("class"));
            }
        };
    }

    /**
     * Waits for one of the two elements to display
     *
     * @param locator1 - The first element to wait for
     * @param locator2 - The second element to wait for
     * @return true if one of the element displays within time window
     */

    private static ExpectedCondition<Boolean> elementIsDisplayed(final By locator1, final By locator2) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return driver.findElement(locator1).isDisplayed() || driver.findElement(locator2).isDisplayed();
            }
            public String toString() {
                return String.format("elements found by %s and %s are displayed", 
                		locator1, locator2);
            }
        };
    }

    /**
     * An expectation for checking an element is visible and enabled such that you
     * can click it.
     */
    public static ExpectedCondition<Boolean> elementToBeClickable(
            final By locator, final int nthElement) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                List<WebElement> elements = driver.findElements(locator);
                return (elements.get(nthElement).isDisplayed() && elements.get(nthElement).isEnabled());
            }
            public String toString() {
                return String.format("(%d) element %s is clickable", 
                		nthElement, locator);
            }
        };
    }

    public static ExpectedCondition<Boolean> textToBePresentInElement(
            final By locator, final String text) {

        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    String elementText = santizeText(driver.findElement(locator).getText());
                    String elementValue = santizeText(driver.findElement(locator).getAttribute("value"));
                    return elementText.contains(santizeText(text)) || elementValue.contains(santizeText(text));
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("text ('%s') to be present in element found by %s",
                        text, locator);
            }
        };
    }
    public static ExpectedCondition<Boolean> textToBePresentInElement(
            final By locator, final int nthElement, final String text) {

        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                	Collator collator = Collator.getInstance(Locale.US);
                    String elementText = santizeText(driver.findElements(locator).get(nthElement).getText());
                    String elementValue = santizeText(driver.findElements(locator).get(nthElement).getAttribute("value"));
                    int comparisonText = collator.compare(elementText, text);
                    int comparisonValue = collator.compare(elementValue, text);
                    return comparisonText == 0 || comparisonValue == 0;
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("text ('%s') to be present in element found by %s",
                        text, locator);
            }
        };
    }
    
    public static ExpectedCondition<Boolean> textToBePresentInElement(
            final WebElement element, final String text) {

        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    String elementText = element.getText();
                    String elementValue = element.getAttribute("value");
                    return elementText.contains(text) || elementValue.contains(text);
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("text ('%s') to be present in element found by %s",
                        text, element);
            }
        };
    }
    public static ExpectedCondition<WebDriver> frameExists(
            final String frameName) {
        return new ExpectedCondition<WebDriver>() {
            public WebDriver apply(WebDriver driver) {
                return driver.switchTo().frame(frameName);
            }
            public String toString() {
                return String.format("frame with name %s exists", 
                		frameName);
            }
        };
    }

    /**
     * Change the default timeout for the waits
     *
     * @param seconds
     */
    public Element setTimeOut(int seconds) {
        globalSeTimeOut = seconds;
        return this;
    }

    /**
     * Reset the timeout back to default
     */
    public Element resetTimeOut() {
        globalSeTimeOut = defaultTimeOut;
        return this;
    }

    /**
     * Gets the current setting for the timeout
     *
     * @return the current timeout setting
     */
    public int getTimeOut() {
        return globalSeTimeOut;
    }

    /**
     * Wait for an element, using the default timeout Element.globalSeTimeOut
     *
     * @param locator
     * @return
     */
    public Element waitForElement(final By locator) {
        return waitForElement(locator, globalSeTimeOut);
    }

    /**
     * Wait for an element to be displayed, using the default timeout Element.globalSeTimeOut
     *
     * @param locator
     * @return
     */
    public Element waitForElementIsDisplayed(final By locator) {
        return waitForElementIsDisplayed(locator, globalSeTimeOut);
    }
    /**
     * Wait for an element to be displayed, using the default timeout Element.globalSeTimeOut
     *
     * @param locator
     * @return
     */
    public Element waitForNthElementIsDisplayed(final By locator, int nthElement) {
        return waitForElementIsDisplayed(locator, nthElement, globalSeTimeOut);
    }

    /**
     * Wait for an element to be displayed, using the default timeout Element.globalSeTimeOut
     *
     * @param locator1
     * @param locator2
     * @return
     */
    public Element waitForElementIsDisplayed(final By locator1, final By locator2) {
        return waitForElementIsDisplayed(locator1, locator2, globalSeTimeOut);
    }

    /**
     * Wait for an element to be displayed, using the default timeout Element.globalSeTimeOut
     *
     * @param element
     * @return
     */
    public Element waitForElementIsDisplayed(WebElement element) {
        return waitForElementIsDisplayed(element, globalSeTimeOut);
    }

    /**
     * Wait for an element, using specified timeout
     *
     * @param locator
     * @param timeOutInSeconds
     * @return
     */
    public Element waitForElement(final By locator, int timeOutInSeconds) {
        try {
            new WebDriverWait(se.driver(), timeOutInSeconds)
                    .ignoring(RuntimeException.class)
                    .until(new ExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver d) {
                            return d.findElement(locator);
                        }
                    });
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        }

    }
    /**
     * Wait for a child element
     * @param parentElement
     * @param locator
     * @return
     */
    public Element waitForChildElement(final WebElement parentElement, final By locator) {
        try {
            new WebDriverWait(se.driver(), globalSeTimeOut)
                    .ignoring(RuntimeException.class)
                    .until(childIsLoaded(parentElement, locator)
                    );
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        }

    }
    public Element waitForChildElement(final By parentElement, final By locator, final int nthElement) {
        try {
            new WebDriverWait(se.driver(), globalSeTimeOut)
                    .ignoring(RuntimeException.class)
                    .until(childIsLoaded(parentElement, locator, nthElement)
                    );
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        }

    }

    /**
     * Wait for an element to be displayed, using specified timeout
     *
     * @param locator
     * @param timeOutInSeconds
     * @return
     */
    public Element waitForElementIsDisplayed(final By locator, int timeOutInSeconds) {
        try {
            new WebDriverWait(se.driver(), timeOutInSeconds)
                    .ignoring(RuntimeException.class)
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                            return d.findElement(locator).isDisplayed();
                        }
                        public String toString() {
                            return String.format("element found by %s is displayed",locator);
                        }
                    });
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        }

    }
    /**
     * Wait for an element to be displayed, using specified timeout
     *
     * @param locator
     * @param timeOutInSeconds
     * @return
     */
    public Element waitForElementIsDisplayed(final By locator, final int nthElement, int timeOutInSeconds) {
        try {
            new WebDriverWait(se.driver(), timeOutInSeconds)
                    .ignoring(RuntimeException.class)
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                            return d.findElements(locator).get(nthElement).isDisplayed();
                        }
                        public String toString() {
                            return String.format("(%d) element found by %s is displayed", nthElement, locator);
                        }
                    });
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        }

    }

    /**
     * Wait for an element to be displayed, using specified timeout
     *
     * @param locator1
     * @param timeOutInSeconds
     * @return
     */
    public Element waitForElementIsDisplayed(final By locator1, final By locator2, int timeOutInSeconds) {
        try {
            new WebDriverWait(se.driver(), timeOutInSeconds)
                    .ignoring(RuntimeException.class)
                    .until(elementIsDisplayed(locator1, locator2));
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    /**
     * Wait for an element to be displayed, using specified timeout
     *
     * @param element
     * @param timeOutInSeconds
     * @return
     */
    public Element waitForElementIsDisplayed(final WebElement element, int timeOutInSeconds) {
        try {
            new WebDriverWait(se.driver(), timeOutInSeconds)
                    .ignoring(RuntimeException.class)
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                            return element.isDisplayed();
                        }
                    });
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        }
    }

    /**
     * Wait for an element to be click-able
     *
     * @param locator
     * @param timeOutInSeconds
     * @return
     */
    public Element waitForElementToBeClickable(final By locator, int timeOutInSeconds) {
        //se.log().logSeStep("Waiting for element to be clickable: " + locator.toString());
        WebDriverWait wait = new WebDriverWait(se.driver(), timeOutInSeconds);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    /**
     * Wait for an element to be click-able
     *
     * @param locator
     * @return
     */
    public Element waitForElementToBeClickable(final By locator) {
        return waitForElementToBeClickable(locator, globalSeTimeOut);
    }

    /**
     * Wait for an element to be click-able
     *
     * @param locator
     * @return
     */
    public Element waitForNthElementToBeClickable(final By locator, int nthElement) {
        try {
            new WebDriverWait(se.driver(), globalSeTimeOut)
                    .ignoring(RuntimeException.class)
                    .until(elementToBeClickable(locator, nthElement));
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    public Element waitForElementToBeClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(se.driver(), globalSeTimeOut);
        try {
            wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(element));
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    

    public Element waitForTextIsPresentInChild(final WebElement parent, final By locator, String text) {
        WebDriverWait wait = new WebDriverWait(se.driver(), globalSeTimeOut);
        try {
            wait.ignoring(StaleElementReferenceException.class).until(containsText(parent, locator, text));
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }
    public Element waitForTextIsPresentInChild(final WebElement parent, final By locator, final int nthElement, String text) {
        WebDriverWait wait = new WebDriverWait(se.driver(), globalSeTimeOut);
        try {
            wait.ignoring(StaleElementReferenceException.class).until(containsText(parent, locator, nthElement, text));
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }
    
    public Element waitForExactTextIsPresent(final By locator, String text) {
        WebDriverWait wait = new WebDriverWait(se.driver(), globalSeTimeOut);
        try {
            wait.ignoring(StaleElementReferenceException.class).until(containsExactText(locator, text));
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    public Element waitForValueIsPresent(final By locator, String value) {
         WebDriverWait wait = new WebDriverWait(se.driver(), globalSeTimeOut);
        try {
            wait.ignoring(StaleElementReferenceException.class).until(containsValue(locator, value));
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }
    
    public Element waitForValueIsPresentInChild(final WebElement parent, final By locator, String text) {
        WebDriverWait wait = new WebDriverWait(se.driver(), globalSeTimeOut);
        try {
            wait.ignoring(StaleElementReferenceException.class).until(containsValue(parent, locator, text));
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    /**
     * wait for some text to appear within an element
     * will return false if text does not appear before the timeOutInSeconds is reached
     *
     * @param locator
     * @param text
     * @return
     */
    public Element waitForTextIsPresent(final By locator, String text, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(se.driver(), timeOutInSeconds);
        try {
            wait.ignoring(StaleElementReferenceException.class).until(containsText(locator, text));
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    public Element waitForExactTextIsPresent(final By locator, String text, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(se.driver(), timeOutInSeconds);
        try {
            wait.ignoring(StaleElementReferenceException.class).until(containsExactText(locator, text));
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    /**
     * wait for some text to appear within an element
     * will return false if text does not appear before the globalSeTimeOut is reached
     *
     * @param locator
     * @param nthElement
     * @param text
     * @return
     */
    public Element waitForTextIsPresent(final By locator, int nthElement, String text) {
        return waitForTextIsPresent(locator, nthElement, text, globalSeTimeOut);
    }

    /**
     * wait for some text to appear within an element
     * will return false if text does not appear before the timeOutInSeconds is reached
     *
     * @param locator
     * @param nthElement
     * @param text
     * @return
     */
    public Element waitForTextIsPresent(final By locator, int nthElement, String text, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(se.driver(), timeOutInSeconds);
        try {
            wait.ignoring(StaleElementReferenceException.class).until(containsText(locator, nthElement, text));
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    /**
     * Wait for an element such as a spinner or modal, to go away.
     *
     * @param locator
     * @return true if element disappears within the timeout limit
     */
    public Element waitForElementToDisappear(final By locator) {
        boolean elementIsFound = true;
        int timeout = 0;
        do {
            try {
                WebElement element = se.driver().findElement(locator);
                if (element.isDisplayed() && element.isEnabled()) //&& !element.getAttribute("style").contains("none")
                {
                    sleep(1000);
                    timeout++;
                } else
                    elementIsFound = false;
            } catch (Exception e) {
                elementIsFound = false;
            }
        } while (elementIsFound && (timeout < globalSeTimeOut));

        if (!elementIsFound)
        	return this;
        else
        	return null;
    }

    /**
     * Wait for an element such as a spinner or modal, to go away.
     *
     * @param element
     * @return true if element disappears within the timeout limit
     */
    public Element waitForElementToDisappear(WebElement element) {
        boolean elementIsFound = true;
        int timeout = 0;
        do {
            try {
                if (element.isDisplayed() && element.isEnabled()) //&& !element.getAttribute("style").contains("none")
                {
                    sleep(1000);
                    timeout++;
                } else
                    elementIsFound = false;
            } catch (Exception e) {
                elementIsFound = false;
            }
        } while (elementIsFound && (timeout < globalSeTimeOut));

        if (!elementIsFound)
        	return this;
        else
        	return null;
    }

    
    /**
     * Wait for an element such as a spinner or modal, to go away.
     *
     * @param locator
     * @return true if element disappears within the timeout limit
     */
    public Element waitForElementToDisappear(final By locator, int timeoutSeconds) {
        boolean elementIsFound = true;
        int timeout = 0;
        do {
            try {
                WebElement element = se.driver().findElement(locator);
                if (element.isDisplayed() && element.isEnabled()) //&& !element.getAttribute("style").contains("none")
                {
                    sleep(1000);
                    timeout++;
                } else
                    elementIsFound = false;
            } catch (Exception e) {
                elementIsFound = false;
            }
        } while (elementIsFound && (timeout < timeoutSeconds));

        if (!elementIsFound)
        	return this;
        else
        	return null;
    }

    /**
     * Wait for an element with some text such as a "loading...", to go away.
     *
     * @param locator
     * @return true if element disappears within the timeout limit
     */
    public Element waitForElementWithTextToDisappear(final By locator, String text) {
        boolean elementIsFound = true;
        int timeout = 0;
        
        do {
            try {
                WebElement element = se.driver().findElement(locator);
                if (element.isDisplayed() && element.isEnabled()) {
                    if (se.driver().findElement(locator).getText().contains(text)) {
                        sleep(1000);
                        timeout++;
                    } else
                        elementIsFound = false;

                } else
                    elementIsFound = false;
            } catch (Exception e) {
                elementIsFound = false;
            }

        } while (elementIsFound && (timeout < globalSeTimeOut));

        if (!elementIsFound)
        	return this;
        else
        	return null;
    }

    public Element waitForElementWithAttributeToAppear(final By locator, final String attribute, final String attributeValue, int timeOutInSeconds) {
        try {
            boolean result =
            		new WebDriverWait(se.driver(), timeOutInSeconds)
                    .ignoring(RuntimeException.class)
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                            return d.findElement(locator).getAttribute(attribute).contains(attributeValue);
                        }
                    });
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    public Element waitForElementWithAttributeToAppear(final By locator, int nthElement, String attribute, String attributeValue, int waitTime) {
        boolean elementIsFound = false;
        int timeout = 0;
        
do {
            String attr = getAttribute(locator, attribute, nthElement);
            if (attr == null || attr.isEmpty()) {
                sleep(1000);
                timeout++;
                continue;
            } else if (attr.contains(attributeValue)) {
                elementIsFound = true;
            } else {
                sleep(1000);
                timeout++;
            }

        } while (!elementIsFound && (timeout < waitTime));

        if (elementIsFound)
        	return this;
        else
        	return null;
    }

    public Element waitForPageTitle(String text) {
        WebDriverWait wait = new WebDriverWait(se.driver(), globalSeTimeOut);
        try {
            wait.until(ExpectedConditions.titleContains(text));
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    /**
     * wait for the length of the text in the element to be greater than
     * will return false if text does not appear before the timeOutInSeconds is reached
     *
     * @param locator
     * @param minTextLength
     * @return
     * @author Terry Nweze
     */
    public Element waitForTextLengthIsGreaterThan(final By locator, int minTextLength) {
        WebDriverWait wait = new WebDriverWait(se.driver(), globalSeTimeOut);
        try {
            wait.ignoring(StaleElementReferenceException.class).until(textLengthIsGreaterThan(locator, minTextLength));
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    public boolean isClickable(final By locator) {
        try {
            if (se.driver().findElement(locator).isEnabled() && se.driver().findElement(locator).isDisplayed())
                return true;
            else
                return false;
        } catch (Exception e) {
            //swallow
            return false;
        }
    }
    
    /**
     * Checks if a certain element has focus
     *
     * @param locator    - The element to check for focus
     * @return true if element has focus
     */
    public boolean hasFocus(final By locator){
    	se.driver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        
        WebElement element = se.driver().findElement(locator);
        if (element == null)
            return false;
        return element.equals(se.driver().switchTo().activeElement());
    }

    /**
     * Returns true if element exists
     *
     * @param locator
     * @return
     */
    public boolean exists(final By locator) {
        se.driver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            se.driver().findElement(locator);
            return true;
        } catch (Exception e) {
            //swallow
            return false;
        }
    }
    
    /**
     * Returns true if element exists
     *
     * @param element
     * @return
     */
    public boolean exists(WebElement element) {
        try {
            element.isDisplayed();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * Returns true if element exists
     *
     * @param locator
     * @param nthElement
     * @return
     */
    public boolean exists(final By locator, int nthElement) {
        se.driver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            List<WebElement> elements = se.driver().findElements(locator);
            elements.get(nthElement);
            return true;
        } catch (Exception e) {
        	//swallow
            return false;
        }
    }


    /**
     * Returns true if element exists with text
     *
     * @param locator
     * @param text
     * @return
     */
    public boolean existsWithText(final By locator, String text) {
        se.driver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            List<WebElement> elements = getElements(locator);
            for (WebElement webElement : elements) {
                if (webElement.getText().contains(text))
                    return true;
            }
            return false;
        } catch (Exception e) {
        	//swallow
            return false;
        }
    }

    public boolean existsWithExactText(final By locator, String text) {
        se.driver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            List<WebElement> elements = getElements(locator);
            for (WebElement webElement : elements) {
                if (webElement.getText().equals(text))
                    return true;
            }
            return false;
        } catch (Exception e) {
        	//swallow
            return false;
        }
    }

    /**
     * Returns true if element exists with text
     *
     * @param locator
     * @param nthElement
     * @param text
     * @return
     */
    public boolean existsWithText(final By locator, int nthElement, String text) {
        se.driver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            List<WebElement> elements = se.driver().findElements(locator);
            WebElement element = elements.get(nthElement);
            if (element.getText().contains(text))
            	return true;
            else
            	return false;
        } catch (Exception e) {
        	//swallow
            return false;
        }
    }

    /**
     * Search frames for an Element
     *
     * @param locator
     * @return
     */
    private WebElement searchForElement(final By locator, final int timeOutInSeconds) {
        if (waitForElement(locator, timeOutInSeconds) != null) {
            return getElement(locator);
        }
        return null;
    }

    private WebElement searchForElement(final By locator) {
        return searchForElement(locator, globalSeTimeOut);
    }


    /**
     * Search frames for Elements
     *
     * @param locator
     * @return
     */
    private List<WebElement> searchForElements(final By locator) {
        if (waitForElement(locator) != null) {
            return getElements(locator);
        }
        return null;
    }

    /**
     * Finds and returns the element
     *
     * @param locator
     * @return
     */
    public WebElement getElement(final By locator) {
        try {
            return se.driver().findElement(locator);
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    public WebElement getElementContainingText(final By locator, String containsText, int timeout) {
        try {
            return new WebDriverWait(se.driver(), timeout)
                    .ignoring(RuntimeException.class)
                    .until(getElementContainsText(locator, containsText));
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    public WebElement getElementContainingText(final By locator, String containsText) {
        return getElementContainingText(locator, containsText, globalSeTimeOut);
    }

    /**
     * Get a child element of a parent element. 
     * Will automatically wait for child element to be displayed.
     * Will return null if parent is null.
     * @param parent
     * @param locator
     * @return
     */
    public WebElement getChildElement(WebElement parent, final By locator) {
        
        if (parent == null)
            return null;
        
        WebDriverWait wait = new WebDriverWait(se.driver(), globalSeTimeOut);
        try {
            return wait.until(childIsLoaded(parent, locator));
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
        
    }
    /**
     * Get a child element of a parent element. 
     * Will automatically wait for child element to be displayed.
     * Will return null if parent is null.
     * @param parent
     * @param locator
     * @return
     */
    public WebElement getChildElement(WebElement parent, final By locator, final int index) {
        
        if (parent == null)
            return null;
        
        WebDriverWait wait = new WebDriverWait(se.driver(), globalSeTimeOut);
        try {
            return wait.until(childIsLoaded(parent, locator, index));
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
        
    }
    public WebElement getChildElement(final By parent, final By locator, final int index) {
        if (parent == null)
            return null;
        
        WebDriverWait wait = new WebDriverWait(se.driver(), globalSeTimeOut);
        try {
            return wait.until(childIsLoaded(parent, locator, index));
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
        
    }
    
    /**
     * Wait for, finds, and returns the element
     *
     * @param locator
     * @return
     */
    private WebElement waitForAndGetElement(final By locator) {
        WebElement element;
        waitForElement(locator);
        try {
            element = se.driver().findElement(locator);
            return element;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    private List<WebElement> waitForAndGetElements(final By locator) {
        List<WebElement> element;
        waitForElement(locator);
        try {
            element = se.driver().findElements(locator);
            return element;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    @SuppressWarnings("unused")
    private WebElement sleepAndReturnElement(final By locator, int sleep) {
        sleep(sleep);
        return getElement(locator);
    }

    @SuppressWarnings("unused")
    private WebElement sleepAndReturnElement(WebElement element, int sleep) {
        sleep(sleep);
        return element;
    }

    @SuppressWarnings("unused")
    private List<WebElement> sleepAndReturnElements(final By locator, int sleep) {
        sleep(sleep);
        return getElements(locator);
    }

    /**
     * Search frames for an Element
     *
     * @param locator
     * @return
     */
    private WebElement searchForClickableElement(final By locator) {
        return searchForClickableElement(locator, globalSeTimeOut);
    }

    private WebElement searchForClickableElement(final By locator, int timeOutInSeconds) {
        if (waitForElementToBeClickable(locator, timeOutInSeconds) != null) {
            return getElement(locator);
        }
        return null;
    }

    /**
     * Search frames for Elements
     *
     * @param locator
     * @return
     */
    private List<WebElement> searchForClickableElements(final By locator) {
        return searchForClickableElements(locator, globalSeTimeOut);
    }

    private List<WebElement> searchForClickableElements(final By locator, int timeOutInSeconds) {
        if (waitForElementToBeClickable(locator, timeOutInSeconds) != null) {
            return getElements(locator);
        }
        return null;
    }


    /**
     * Finds and returns a list of matching elements
     *
     * @param locator
     * @return
     */
    public List<WebElement> getElements(final By locator) {
        try {
            return se.driver().findElements(locator);
        } catch (Throwable t) {
            System.out.println(t);
            return new ArrayList<WebElement>();
        }
    }

    /**
     * Click the element
     *
     * @param locator
     * @return
     */
    public Element clickElement(final By locator) {
        return clickElement(locator, null, false, -1, null, -1, -1, null, false);
    }

    public Element clickElement(final By locator, final By successLocator) {
        return clickElement(locator, null, false, -1, null, -1, -1, successLocator, false);
    }

    public Element clickElement(final By locator, final By successLocator, final boolean shouldDisappear) {
        return clickElement(locator, null, false, -1, null, -1, -1, successLocator, shouldDisappear);
    }

    public Element clickElement(final By locator, final int nthElement) {
        return clickElement(locator, null, false, nthElement, null, -1, -1, null, false);
    }

    public Element clickElementContainingText(final By locator, final String containsText) {
        return clickElement(locator, containsText, false, -1, null, -1, -1, null, false);
    }

    public Element clickElementContainingText(final By locator, final String containsText, final By successLocator) {
        return clickElement(locator, containsText, false, -1, null, -1, -1, successLocator, false);
    }

    public Element clickElementContainingText(final By locator, final String containsText, final By successLocator, final boolean shouldDisappear) {
        return clickElement(locator, containsText, false, -1, null, -1, -1, successLocator, shouldDisappear);
    }

    public Element clickElementContainingExactText(final By locator, final String containsText) {
        return clickElement(locator, containsText, true, -1, null, -1, -1, null, false);
    }

    /**
     * Click an element at location x,y inside the element
     *
     * @param locator
     * @param x
     * @param y
     * @return
     */
    public Element clickElementAtPoint(final By locator, int x, int y) {
        return clickElement(locator, null, false, -1, null, x, y, null, false);
    }

    /**
     * Click an element at location x,y inside the element
     *
     * @param locator
     * @param nthElement
     * @param x
     * @param y
     * @return
     */
    public Element clickElementAtPoint(final By locator, int nthElement, int x, int y) {
        return clickElement(locator, null, false, nthElement, null, x, y, null, false);
    }

    public Element clickElementContainingTextAtPoint(final By locator, String containsText, int x, int y) {
        return clickElement(locator, containsText, false, -1, null, x, y, null, false);
    }

    /**
     * Click an element inside a frame
     *
     * @param locator
     * @param nthElement
     * @param frameName
     * @return
     */
    public Element clickElement(final By locator, int nthElement, String frameName) {
        return clickElement(locator, null, false, nthElement, frameName, -1, -1, null, false);
    }

    public Element clickElement(final By locator, final String containsText, final boolean exactText, final int nthElement, final String frameName, final int x, final int y, final By successLocator, final boolean shouldDisappear) {
        // Build logging message from options
        try {
            new WebDriverWait(se.driver(), globalSeTimeOut)
                    .ignoring(Throwable.class)
                    .withMessage("Unable to click on element")
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver driver) {
                            // Switch frame is specified
                            if (frameName != null && !frameName.isEmpty())
                                switchToFrame(frameName);

                            // Find targetElement
                            WebElement targetElement = null;
                            if (containsText != null && !containsText.isEmpty()) {
                                for (WebElement element : se.driver().findElements(locator)) {
                                    String elementText = element.getText();
                                    if (elementText.equals(containsText) || (!exactText && elementText.contains(containsText))) {
                                        targetElement = element;
                                        break;
                                    }
                                }
                            } else if (nthElement != -1) {
                                targetElement = se.driver().findElements(locator).get(nthElement);
                            } else {
                                targetElement = se.driver().findElement(locator);
                            }

                            // Click on the element
                            if (targetElement != null) {
                                if (x != -1 || y != -1) {
                                    new Actions(driver).moveToElement(targetElement).moveByOffset(x, y).click().perform();
                                } else {
                                    targetElement.click();
                                }
                            }

                            // Check for success locator
                            boolean success;
                            if (successLocator == null) {
                            	success = (targetElement != null);
                            }
                            else
                            {
                            	success = (shouldDisappear ? waitForElementToDisappear(successLocator, 5) != null : waitForElementIsDisplayed(successLocator, 5) != null);
                            }
                            
                            // Switch frame back to default, if frame was specified
                            if (success && frameName != null && !frameName.isEmpty())
                                returnToDefaultContent();

                            // Did we do it?
                            return success;
                        }
                    });
            return this;
        } catch (Throwable t) {
            System.out.println(t);
           
            // Switch frame back to default, if a frame was specified
            if (frameName != null && !frameName.isEmpty())
                returnToDefaultContent();
            return null;
        }
    }
    
    public Element doubleClickElement(final By locator, final String containsText, final boolean exactText, final int nthElement, final String frameName, final int x, final int y, final By successLocator, final boolean shouldDisappear) {
        try {
            new WebDriverWait(se.driver(), globalSeTimeOut)
                    .ignoring(Throwable.class)
                    .withMessage("Unable to click on element")
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver driver) {
                            // Switch frame is specified
                            if (frameName != null && !frameName.isEmpty())
                                switchToFrame(frameName);

                            // Find targetElement
                            WebElement targetElement = null;
                            if (containsText != null && !containsText.isEmpty()) {
                                for (WebElement element : se.driver().findElements(locator)) {
                                    String elementText = element.getText();
                                    if (elementText.equals(containsText) || (!exactText && elementText.contains(containsText))) {
                                        targetElement = element;
                                        break;
                                    }
                                }
                            } else if (nthElement != -1) {
                                targetElement = se.driver().findElements(locator).get(nthElement);
                            } else {
                                targetElement = se.driver().findElement(locator);
                            }

                            // Click on the element
                            if (targetElement != null) {
                                if (x != -1 || y != -1) {
                                    new Actions(driver).moveToElement(targetElement).moveByOffset(x, y).doubleClick().perform();
                                } else {
                                	new Actions(driver).doubleClick(targetElement).perform();
                                }
                            }

                            // Check for success locator
                            boolean success = (targetElement != null && successLocator == null) || (shouldDisappear ? waitForElementToDisappear(successLocator, 5) != null : waitForElementIsDisplayed(successLocator, 5) != null);

                            // Switch frame back to default, if frame was specified
                            if (success && frameName != null && !frameName.isEmpty())
                                returnToDefaultContent();

                            // Did we do it?
                            return success;
                        }
                    });
            return this;
        } catch (Throwable t) {
            System.out.println(t);
           
            // Switch frame back to default, if a frame was specified
            if (frameName != null && !frameName.isEmpty())
                returnToDefaultContent();
            return null;
        }
    }

    /**
     * Click the element
     *
     * @param locator
     * @return
     */
    public Element doubleClickElement(final By locator) {
        return doubleClickElement(locator, null, false, -1, null, -1, -1, null, false);
    }

    public Element doubleClickElement(final By locator, final By successLocator) {
        return doubleClickElement(locator, null, false, -1, null, -1, -1, successLocator, false);
    }

    public Element doubleClickElement(final By locator, final By successLocator, final boolean shouldDisappear) {
        return doubleClickElement(locator, null, false, -1, null, -1, -1, successLocator, shouldDisappear);
    }

    public Element doubleClickElement(final By locator, final int nthElement) {
        return doubleClickElement(locator, null, false, nthElement, null, -1, -1, null, false);
    }

    public Element doubleClickElementContainingText(final By locator, final String containsText) {
        return doubleClickElement(locator, containsText, false, -1, null, -1, -1, null, false);
    }

    public Element doubleClickElementContainingText(final By locator, final String containsText, final By successLocator) {
        return doubleClickElement(locator, containsText, false, -1, null, -1, -1, successLocator, false);
    }

    public Element doubleClickElementContainingText(final By locator, final String containsText, final By successLocator, final boolean shouldDisappear) {
        return doubleClickElement(locator, containsText, false, -1, null, -1, -1, successLocator, shouldDisappear);
    }

    public Element doubleClickElementContainingExactText(final By locator, final String containsText) {
        return doubleClickElement(locator, containsText, true, -1, null, -1, -1, null, false);
    }


    public Element rightClickElement(final By locator) {
        WebElement element;
        
element = searchForClickableElement(locator);
        if (element != null) {
            Actions builder = new Actions(se.driver());
            Action action = builder.contextClick(element).build();
            action.perform();
            return this;
        } else
            return null;
    }

    public Element clickChildElement(WebElement webElement, final By locator) {
        return clickChildElement(webElement, locator, 0);
    }
    
    public Element clickChildElement(WebElement webElement, final By locator, int index) {
        final WebElement element;
        element = getChildElement(webElement, locator, index);
        
        try {
            boolean result =
            		new WebDriverWait(se.driver(), globalSeTimeOut)
                    .ignoring(RuntimeException.class)
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                            element.click();
                            return true;
                        }
                    });
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }
    
    public Element doublClickChildElement(WebElement webElement, final By locator, int index) {
        if (webElement != null) {
            hover(webElement);
            waitForElementToBeClickable(webElement);
            new Actions(se.driver()).doubleClick(webElement).perform();
            return this;
        } else
            return null;
    }
    public Element clickChildElementContainingText(WebElement webElement, final By locator, String containsText) {
        List<WebElement> elements;
        
        if (webElement == null)
            return null;
        try {
        	elements = webElement.findElements(locator);
            for (WebElement element : elements) {
                if (element.getText().contains(containsText)) {
                	element.click();
                    return this;
                }
            }
		} catch (Throwable t) {
			System.out.println(t);
		}
        return null;
        
    }
    public Element doubleClickChildElementContainingText(WebElement webElement, final By locator, String containsText) {
        List<WebElement> elements;
        if (webElement == null)
            return null;
        try {
        	elements = webElement.findElements(locator);
            for (WebElement element : elements) {
                if (element.getText().contains(containsText)) {
                	new Actions(se.driver()).doubleClick(element).perform();
                    return this;
                }
            }
		} catch (Throwable t) {
			System.out.println(t);
		}
        return null;
        
    }

    public Element hover(WebElement element) {
        Actions actions = new Actions(se.driver());
        Action action = actions.moveToElement(element).build();
        action.perform();
        return this;
    }

    public Element hover(WebElement element, int x, int y) {
        Actions actions = new Actions(se.driver());
        Action action = actions.moveToElement(element, x, y).build();
        action.perform();
        return this;
    }

    /**
     * Hover over an element
     *
     * @param locator
     * @param nthElement
     * @return
     */
    public Element hover(final By locator, int nthElement) {
        List<WebElement> elements;

        elements = searchForElements(locator);
        if (elements != null && elements.size() > 0) {
            Actions action = new Actions(se.driver());
            action = action.moveToElement(elements.get(nthElement));
            action.perform();
            return this;
        } else
            return null;
    }

    /**
     * Hover over an element, and then click on another element that pops up.
     *
     * @param locatorForHover
     * @param locatorForClick
     * @return
     */
    public Element hoverAndClick(final By locatorForHover, final By locatorForClick) {
        WebElement hoverOverElement;
        hoverOverElement = searchForElement(locatorForHover);
        if (hoverOverElement != null) {
            Actions action = new Actions(se.driver());
            action = action.moveToElement(hoverOverElement).moveToElement(waitForAndGetElement(locatorForClick)).click();
            action.perform();
            return this;
        } else
            return null;

    }

    /**
     * Hover over an element, and then click on another element that pops up.
     *
     * @param locatorForHover
     * @param locatorForClick
     * @param nthElement      for hover
     * @return
     */
    public Element hoverAndClick(final By locatorForHover, final By locatorForClick, int nthElement) {
        List<WebElement> hoverOverElement;
        
        hoverOverElement = searchForElements(locatorForHover);
        if (hoverOverElement != null) {
            Actions action = new Actions(se.driver());
            action = action.moveToElement(hoverOverElement.get(nthElement)).moveToElement(getElement(locatorForClick)).click();
            action.perform();
            return this;
        } else
            return null;

    }


    public Element dragAndDrop(By dragElement, By dropElement, int msDelayBeforeDrop) {
        return dragAndDrop(dragElement, null, -1, dropElement, null, -1, null, msDelayBeforeDrop, -1, -1);
    }

    public Element dragAndDrop(By dragElement, By dropElement, int msDelayBeforeDrop, int x, int y) {
        return dragAndDrop(dragElement, null, -1, dropElement, null, -1, null, msDelayBeforeDrop, x, y);
    }
    public Element dragAndDrop(By dragElement, int nthDragElement, By dropElement, int nthDropElement, int msDelayBeforeDrop) {
        return dragAndDrop(dragElement, null, nthDragElement, dropElement, null, nthDropElement, null, msDelayBeforeDrop, -1, -1);
    }

    public Element dragAndDropAcrossFrames(By dragElement, By dropElement, String iFrameName) {
        return dragAndDrop(dragElement, null, -1, dropElement, null, -1, iFrameName, 0, -1, -1);
    }

    public Element dragAndDropAcrossFrames(By dragElement, By dropElement, String iFrameName, int msDelayBeforeDrop) {
        return dragAndDrop(dragElement, null, -1, dropElement, null, -1, iFrameName, msDelayBeforeDrop, -1, -1);
    }

    public Element dragAndDropAcrossFrames(By dragElement, By dropElement, String iFrameName, int msDelayBeforeDrop, int x, int y) {
        return dragAndDrop(dragElement, null, -1, dropElement, null, -1, iFrameName, msDelayBeforeDrop, x, y);
    }

    public Element dragAndDropAcrossFrames(By dragElement, String dragElementText, By dropElement, String dropElementText, String iFrameName, int msDelayBeforeDrop) {
        return dragAndDrop(dragElement, dragElementText, -1, dropElement, dropElementText, -1, iFrameName, msDelayBeforeDrop, -1, -1);
    }

    public Element dragAndDropAcrossFrames(By dragElement, String dragElementText, By dropElement, String dropElementText, String iFrameName, int msDelayBeforeDrop, int x, int y) {
        return dragAndDrop(dragElement, dragElementText, -1, dropElement, dropElementText, -1, iFrameName, msDelayBeforeDrop, x, y);
    }

    public Element dragAndDropAcrossFrames(By dragElement, int nthDragElement, By dropElement, int nthDropElement, String iFrameName, int msDelayBeforeDrop) {
        return dragAndDrop(dragElement, null, nthDragElement, dropElement, null, nthDropElement, iFrameName, msDelayBeforeDrop, -1, -1);
    }

    public Element dragAndDrop(By dragElementLocator, String dragElementText, int nthDragElement, By dropElementLocator, String dropElementText, int nthDropElement, String iFrameName, int msDelayBeforeDrop, int x, int y) {
        try {

            // Init our action builder;
            Actions builder = new Actions(se.driver());

            // Find the dragElement
            WebElement dragElement;
            if (dragElementText != null && !dragElementText.isEmpty())
                dragElement = getElementContainingText(dragElementLocator, dragElementText);
            else if (nthDragElement != -1)
                dragElement = waitForAndGetElements(dragElementLocator).get(nthDragElement);
            else
                dragElement = waitForAndGetElement(dragElementLocator);

            // Pickup the dragElement
            builder.clickAndHold(dragElement).perform();

            // If delay required, delay
            if (msDelayBeforeDrop > 0)
                sleep(msDelayBeforeDrop / 2);

            // If across frames, switch frame
            if (iFrameName != null && !iFrameName.isEmpty())
                switchToFrame(iFrameName);

            // Find the dropElement
            WebElement dropElement;
            if (dropElementText != null && !dropElementText.isEmpty())
                dropElement = getElementContainingText(dropElementLocator, dropElementText);
            else if (nthDropElement != -1)
                dropElement = waitForAndGetElements(dropElementLocator).get(nthDropElement);
            else
                dropElement = waitForAndGetElement(dropElementLocator);

            // Move to the dropElement
            if (x != -1 || y != -1) {
                // If we are dropping "Above" the target, drag to the element, then move to above
                if (y < 0) {
                    // Make sure there is space above the element
                    ((JavascriptExecutor) se.driver()).executeScript("arguments[0].scrollIntoView(); document.body.scrollTop += " + (y - 5), dropElement);
                    builder.moveToElement(dropElement).perform();
                    sleep(msDelayBeforeDrop / 2);
                }
                builder.moveToElement(dropElement, x, y).perform();
            } else {
                builder.moveToElement(dropElement).perform();
            }

            // If delay required, delay
            if (msDelayBeforeDrop > 0)
                sleep(msDelayBeforeDrop / 2);

            // Release the drag element
            builder.release().perform();
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }
    public Element drag(By dragElement, By toElement, int msDelay) {
        return drag(dragElement, null, -1, toElement, null, -1, null, msDelay, -1, -1);
    }

    public Element drag(By dragElement, By toElement, int msDelay, int x, int y) {
        return drag(dragElement, null, -1, toElement, null, -1, null, msDelay, x, y);
    }

    public Element drag(By dragElement, By toElement, String iFrameName) {
        return drag(dragElement, null, -1, toElement, null, -1, iFrameName, 0, -1, -1);
    }
    public Element drag(By dragElement, int nthDragElement, By toElement, int nthToElement, int msDelay) {
        return drag(dragElement, null, nthDragElement, toElement, null, nthToElement, null, msDelay, -1, -1);
    }

    public Element dragAcrossFrames(By dragElement, By toElement, String iFrameName, int msDelay) {
        return drag(dragElement, null, -1, toElement, null, -1, iFrameName, msDelay, -1, -1);
    }

    public Element dragAcrossFrames(By dragElement, By toElement, String iFrameName, int msDelay, int x, int y) {
        return drag(dragElement, null, -1, toElement, null, -1, iFrameName, msDelay, x, y);
    }

    public Element dragAcrossFrames(By dragElement, String dragElementText, By toElement, String toElementText, String iFrameName, int msDelay) {
        return drag(dragElement, dragElementText, -1, toElement, toElementText, -1, iFrameName, msDelay, -1, -1);
    }

    public Element dragAcrossFrames(By dragElement, String dragElementText, By toElement, String toElementText, String iFrameName, int msDelay, int x, int y) {
        return drag(dragElement, dragElementText, -1, toElement, toElementText, -1, iFrameName, msDelay, x, y);
    }

    public Element dragAcrossFrames(By dragElement, int nthDragElement, By toElement, int nthToElement, String iFrameName, int msDelay) {
        return drag(dragElement, null, nthDragElement, toElement, null, nthToElement, iFrameName, msDelay, -1, -1);
    }
    public Element drag(By dragElementLocator, String dragElementText, int nthDragElement, By toElementLocator, String toElementText, int nthToElement, String iFrameName, int msDelay, int x, int y) {
    	try {
            // Init our action builder;
            Actions builder = new Actions(se.driver());

            // Find the dragElement
            WebElement dragElement;
            if (dragElementText != null && !dragElementText.isEmpty())
                dragElement = getElementContainingText(dragElementLocator, dragElementText);
            else if (nthDragElement != -1)
                dragElement = waitForAndGetElements(dragElementLocator).get(nthDragElement);
            else
                dragElement = waitForAndGetElement(dragElementLocator);

            // Pickup the dragElement
            builder.clickAndHold(dragElement).perform();

            // If delay required, delay
            if (msDelay > 0)
                sleep(msDelay);

            // If across frames, switch frame
            if (iFrameName != null && !iFrameName.isEmpty())
                switchToFrame(iFrameName);

            // Find the dropElement
            WebElement dropElement;
            if (toElementText != null && !toElementText.isEmpty())
                dropElement = getElementContainingText(toElementLocator, toElementText);
            else if (nthToElement != -1)
                dropElement = waitForAndGetElements(toElementLocator).get(nthToElement);
            else
                dropElement = waitForAndGetElement(toElementLocator);

            // Move to the dropElement
            if (x != -1 || y != -1) {
                // If we are dropping "Above" the target, drag to the element, then move to above
                if (y < 0) {
                    // Make sure there is space above the element
                    ((JavascriptExecutor) se.driver()).executeScript("arguments[0].scrollIntoView(); document.body.scrollTop += " + (y - 5), dropElement);
                    builder.moveToElement(dropElement).perform();
                }
                builder.moveToElement(dropElement, x, y).perform();
            } else {
                builder.moveToElement(dropElement).perform();
            }

            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }


    /**
     * Get the number frames on the page
     *
     * @return int number of frames
     */
    public int getNumberFrames() {
        int iframes = 0;
        int framesets = 0;
        List<WebElement> elements = getElements(By.tagName("iframe"));
        if (elements != null)
            iframes = elements.size();
        elements = getElements(By.tagName("frameset"));
        if (elements != null)
            framesets = elements.size();
        return iframes + framesets;
    }

    /**
     * Get the number of windows open
     *
     * @return int number of windows
     */
    public int getNumberWindows() {
        return se.driver().getWindowHandles().size();
    }


    public Element enterText(By locator, Keys keys) {
        WebElement element;
        element = searchForClickableElement(locator);
        if (element != null) {
            try {
                element.sendKeys(keys);
                return this;
            } catch (Throwable t) {
                System.out.println(t);
                return null;
            } 
        } else
            return null;

    }
    public Element enterTextInChildContainingText(final WebElement parent, final By locator, final String containsText, final String textToType) {
        try {
           new WebDriverWait(se.driver(), globalSeTimeOut)
                    .ignoring(RuntimeException.class)
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                        	List<WebElement> elements= parent.findElements(locator);
                        	for(WebElement element: elements) {
                        		if(element.getText().contains(containsText)) {
                        			element.clear();
                        			if (textToType.length()  >0 )
                        				element.sendKeys(textToType);
                                    return element.getText().equals(textToType);
                        		}
                        	}
                        	return false;
                        }
                    });
           return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }
    
    public Element enterTextInChild(final WebElement parent, final By locator,final String textToType) {
        try {
            new WebDriverWait(se.driver(), globalSeTimeOut)
                    .ignoring(RuntimeException.class)
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                        	WebElement element = parent.findElement(locator);
                        	element.clear();
                        	sleep(250);
                        	if (textToType.length()  >0 ){
                            	for(char ch: textToType.toCharArray()) {
                            		element.sendKeys(String.valueOf(ch));
                            		sleep(typeDelay);
                            	}
                            	
                            }
                            sleep(250);
                            return new WebDriverWait(d, 2).until(textToBePresentInElement(element, textToType));
                        }
                    });
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }
    
    
    /**
     * Enter text into element
     *
     * @param locator
     * @param textToType
     * @return
     */
    public Element enterText(final By locator, final String textToType) {
    	WebElement e = se.driver().findElement(locator);
    	e.clear();
    	sleep(250);
    	
    	for(char ch: textToType.toCharArray()) {
    		e.sendKeys(String.valueOf(ch));
    		sleep(typeDelay);
    	}
    	
    	sleep(250);
    		
    	if(textToBePresentInElement(locator, textToType) != null){
    		return this;
    	}
    		
    	
    	return null;
 
    }
    
    public Element enterText(final By locator, final int nthElement, final String textToType) {
        try {
            new WebDriverWait(se.driver(), globalSeTimeOut)
                    .ignoring(RuntimeException.class)
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                            WebElement element = d.findElements(locator).get(nthElement);
                            element.clear();
                            sleep(250);
                            if (textToType.length()  >0 ){
                            	for(char ch: textToType.toCharArray()) {
                            		element.sendKeys(String.valueOf(ch));
                            		sleep(typeDelay);
                            	}
                            	
                            }
                            sleep(250);
                            return new WebDriverWait(d, 2).until(textToBePresentInElement(locator, nthElement, textToType));
                        }
                    });
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }
    
    private static String santizeText(String text) {
    	for (Keys key : Keys.values() ){
    		text = text.replace(key, "?");
    	}
    	text = text.replace("\n", "?");
    	return text;
    }

    public Element clearText(final By locator) {
        try {
            new WebDriverWait(se.driver(), globalSeTimeOut)
                    .ignoring(RuntimeException.class)
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                            WebElement element = d.findElement(locator);
                            element.clear();
                            return true;
                        }
                    });
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }
    public Element clearText(final By locator, final int nthElement) {
        try {
            new WebDriverWait(se.driver(), globalSeTimeOut)
                    .ignoring(RuntimeException.class)
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                            WebElement element = d.findElements(locator).get(nthElement);
                            element.clear();
                            return true;
                        }
                    });
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    public Element sendKeys(final By locator, final CharSequence keysToSend) {
        try {
            new WebDriverWait(se.driver(), globalSeTimeOut)
                    .ignoring(RuntimeException.class)
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                            WebElement element = d.findElement(locator);
                            element.sendKeys(keysToSend);
                            return true;
                        }
                    });
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }
    public Element sendKeys(final By locator, final int nthElement, final CharSequence keysToSend) {
        try {
            new WebDriverWait(se.driver(), globalSeTimeOut)
                    .ignoring(RuntimeException.class)
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                            WebElement element = d.findElements(locator).get(nthElement);
                            element.sendKeys(keysToSend);
                            return true;
                        }
                    });
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    /**
     * Get the text from the element
     *
     * @param locator
     * @return
     */
    public String getText(final By locator) {
        String text = "";
        try {
            text = new WebDriverWait(se.driver(), globalSeTimeOut)
                    .ignoring(RuntimeException.class)
                    .until(new ExpectedCondition<String>() {
                        public String apply(WebDriver d) {
                            WebElement element = d.findElement(locator);
                            String text = element.getText();
                            return text;
                            
                        }
                    });
            return text;
        } catch (Throwable t) {
            System.out.println(t);
            return "";
        } 
    }

    /**
     * Get the text from the element
     *
     * @param locator
     * @param nthElement
     * @return
     */
    public String getText(final By locator, final int nthElement) {
        String text = "";
        try {
            text = new WebDriverWait(se.driver(), globalSeTimeOut)
                    .ignoring(RuntimeException.class)
                    .until(new ExpectedCondition<String>() {
                        public String apply(WebDriver d) {
                            WebElement element = d.findElements(locator).get(nthElement);
                            String text = element.getText();
                            return text;
                        }
                    });
            return text;
        } catch (Throwable t) {
            System.out.println(t);
            return "";
        } 
    }

    public List<String> getTextFromMultiple(final By locator) {
        List<String> foundText = new ArrayList<String>();
        List<WebElement> elements = searchForElements(locator);
        for (WebElement element : elements) {
            try {
            	if(element.isDisplayed())
            		foundText.add(element.getText());
            } catch (Throwable t) {
                System.out.println(t);
            } 
        }
        return foundText;
    }

    /**
     * Get the value from the element
     *
     * @param locator
     * @return value attribute of element
     */
    public String getValue(final By locator) {
        return getAttribute(locator, "value");
    }

    public String getValue(final By locator, int nthElement) {
    	return getAttribute(locator, "value", nthElement);
    }

    public String getCssProperty(final By locator, String propertyName) {
        WebElement element;
        String text = null;
        element = searchForElement(locator);
        if (element != null) {
            try {
                text = element.getCssValue(propertyName);
            } catch (Throwable t) {
                System.out.println(t);
            } 
            return text;
        } else
            return text;

    }

    public String getCssProperty(final By locator, int nthElement, String propertyName) {
        WebElement element;
        String text = null;
        element = searchForElements(locator).get(nthElement);
        if (element != null) {
            try {
                text = element.getCssValue(propertyName);
            } catch (Throwable t) {
                System.out.println(t);
            } 
            return text;
        } else
            return text;

    }

    /**
     * Get a html attribute from the element
     *
     * @param locator
     * @param attr
     * @return
     */
    public String getAttribute(final By locator, final String attr) {
        String text = null;
        try {
            text = new WebDriverWait(se.driver(), globalSeTimeOut)
                    .ignoring(RuntimeException.class)
                    .until(new ExpectedCondition<String>() {
                        public String apply(WebDriver d) {
                            WebElement element = d.findElement(locator);
                            String text = element.getAttribute(attr);
                            return text;
                        }
                    });
            return text;
        } catch (Throwable t) {
            System.out.println(t);
            return "";
        } 
    }

    /**
     * Get a html attribute from the element
     *
     * @param locator
     * @param attr
     * @param nthElement
     * @return
     */
    public String getAttribute(final By locator, final String attr, final int nthElement) {
        String text = "";
        
        try {
            text = new WebDriverWait(se.driver(), globalSeTimeOut)
                    .ignoring(RuntimeException.class)
                    .until(new ExpectedCondition<String>() {
                        public String apply(WebDriver d) {
                            WebElement element = d.findElements(locator).get(nthElement);
                            String text = element.getAttribute(attr);
                            return text;
                        }
                    });
            return text;
        } catch (Throwable t) {
            System.out.println(t);
            return "";
        } 
    }

    /**
     * Selects an item from a drop down
     *
     * @param locator
     * @param selection
     * @return
     */
    public Element selectElement(final By locator, String selection) {
        
        String browserName = ((RemoteWebDriver) se.driver()).getCapabilities().getBrowserName();
        //Select class does not work on android
        if (browserName.equals("android")) {
            WebElement select = getElement(locator);
            List<WebElement> options = select.findElements(By.tagName("option"));
            int optionNum = 0;
            for (WebElement option : options) {
                if (option.getText().equals(selection)) {
                    try {
                        ((JavascriptExecutor) se.driver()).executeScript("document.getElementById(\"newLocationSource\").options[" + optionNum + "].selected = true");
                        sleep(2000);
                        return this;
                    } catch (Throwable t) {
                       System.out.println(t);
                    }
                    break;
                }
                optionNum++;
            }
            return null;

        } else {
            WebElement element = searchForElement(locator);
            if (element != null) {
                Select select = new Select(element);
                try {
                    select.deselectAll();

                } catch (Exception e) {
                    //ignore
                }
                select.selectByVisibleText(selection);
                return this;
            } else
                return null;
        }

    }

    /**
     * Selects an item from a drop down
     *
     * @param locator
     * @param value
     * @return
     */
    public Element selectElementByValue(final By locator, String value) {
        WebElement element = searchForElement(locator);
        if (element != null) {
            Select select = new Select(element);
            try {
                select.deselectAll();
            } catch (Exception e) {
                //ignore
            }
            select.selectByValue(value);
            return this;
        } else
            return null;

    }

    /**
     * Selects an item from a drop down
     *
     * @param locator
     * @param selection
     * @param nthElement
     * @return
     */
    public Element selectElement(final By locator, String selection, int nthElement) {
        
        List<WebElement> elements = searchForElements(locator);
        if (elements != null) {
            Select select = new Select(elements.get(nthElement));
            try {
                select.deselectAll();

            } catch (Exception e) {
                //ignore
            }
            select.selectByVisibleText(selection);
            return this;
        } else
            return null;
    }

    /**
     * Selects an item from the drop down, the blank row at the top is index 0
     *
     * @param locator
     * @param index
     * @return
     */
    public Element selectElement(final By locator, int index) {
        
        WebElement element = searchForElement(locator);
        if (element != null) {
            Select select = new Select(element);
            try {
                select.deselectAll();

            } catch (Exception e) {
                //ignore
            }
            select.selectByIndex(index);
            return this;
        } else
            return null;
    }

    /**
     * Selects an item from the drop down, the blank row at the top is index 0
     *
     * @param locator
     * @param index
     * @param nthElement
     * @return
     */
    public Element selectElement(final By locator, int index, int nthElement) {
        
        List<WebElement> elements = searchForElements(locator);
        if (elements != null) {
            Select select = new Select(elements.get(nthElement));
            try {
                select.deselectAll();

            } catch (Exception e) {
                //ignore
            }
            select.selectByIndex(index);
            return this;
        } else
            return null;
    }

    /**
     * Returns true of element is visible
     *
     * @param locator
     * @return
     */
    public boolean isVisible(final By locator, final int timeOutInSeconds) {
        
        WebElement element = searchForElement(locator, timeOutInSeconds);
        try {
        	return element != null && element.isDisplayed() && element.isEnabled();
		} catch (Exception e) {
			return false;
		}
        
    }

    public boolean isVisible(final By locator) {
        return isVisible(locator, globalSeTimeOut);
    }

    /**
     * Switch to a frame by name
     *
     * @param iFrameName
     * @return
     */
    public Element switchToFrame(String iFrameName) {
        try {
            new WebDriverWait(se.driver(), globalSeTimeOut)
                    .ignoring(RuntimeException.class)
                    .until(frameExists(iFrameName));
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }
    

    /**
     * Switch to a frame by WebElement
     *
     * @param frame
     * @return
     * @author Terry Nweze
     */
    public Element switchToFrame(WebElement frame) {
        try {
            se.driver().switchTo().frame(frame);
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    /**
     * Switch to a frame by index
     *
     * @param iFrameIndex
     * @return
     */
    public Element switchToFrame(int iFrameIndex) {
        try {
            se.driver().switchTo().frame(iFrameIndex);;
            return this;
        } catch (Throwable t) {
            System.out.println(t);
            return null;
        } 
    }

    /**
     * Returns to the default browser window or frame
     */
    public Element returnToDefaultContent() {
        se.driver().switchTo().defaultContent();
        return this;
    }

    /**
     * Returns the number of matching elements
     *
     * @param locator
     * @return
     */
    public int getNumberOfElements(By locator) {
        List<WebElement> elements = getElements(locator);
        if (elements == null)
            return 0;
        return elements.size();
    }

    /**
     * Returns a 2-D array representing the table
     *
     * @param tableBodyLocator
     * @return
     */
    public String[][] getTable(By tableBodyLocator) {
        String tableBodyLocatorString = tableBodyLocator.toString().split(": ")[1];
        String tableHeaderLocatorString = tableBodyLocatorString + " tr th";
        String tableRowsLocatorString = tableBodyLocatorString + " tr";
        String tableDataCellsLocatorString = tableBodyLocatorString + " tr td";
        By tableHeaderLocator = By.cssSelector(tableHeaderLocatorString);
        By tableRowsLocator = By.cssSelector(tableRowsLocatorString);
        By tableDataCellsLocator = By.cssSelector(tableDataCellsLocatorString);
        int numberOfColumns = getNumberOfElements(tableHeaderLocator);
        int numberOfRows = getNumberOfElements(tableRowsLocator);
        String[][] table = new String[numberOfRows][numberOfColumns];
        List<WebElement> tableDataCells = getElements(tableDataCellsLocator);
        int row = 0;
        for (int i = 0; i < numberOfColumns; i++) {
            table[row][i] = getText(tableHeaderLocator, i);
        }
        row = 1;
        int col = 0;
        for (int i = 0; i < tableDataCells.size(); i++) {


            table[row][col++] = tableDataCells.get(i).getText();

            if (col == numberOfColumns) {
                col = 0;
                row++;
            }
        }
        return table;
    }

    /**
     * Prints the table to the console
     *
     * @param table
     */
    public void printTable(String[][] table) {
        for (String[] aRow : table) {
            for (String aCell : aRow) {
                System.out.println(aCell);
            }
        }
    }

    /**
     * Make sure Element is not null at this point
     * Will end test on NullPointerException
     * @return
     */
    public Element mustSucceed() {
    	return this;
    }
    
    public Element sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			return null;
		}
		
		return this;
	}
}

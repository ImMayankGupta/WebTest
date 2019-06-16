package com.automation.getpageobjects;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import static org.testng.Assert.fail;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static com.automation.getpageobjects.ObjectFileReader.getELementFromFile;
import static com.automation.utils.ConfigPropertyReader.getProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class GetPage extends BaseUi {

	protected WebDriver webdriver;
	String pageName;

	public GetPage(WebDriver driver, String pageName) {
		super(driver, pageName);
		this.webdriver = driver;
		this.pageName = pageName;
	}

	protected void switchToNestedFrames(String frameNames) {
		switchToDefaultContent();
		String[] frameIdentifiers = frameNames.split(":");
		for (String frameId : frameIdentifiers) {
			wait.waitForFrameToBeAvailableAndSwitchToIt(getLocator(frameId.trim()));
		}
	}

	protected WebElement element(String elementToken) {
		return element(elementToken, "");
	}

	public void pageRefresh() {
		driver.navigate().refresh();
	}

	protected WebElement element(String elementToken, String replacement1, String replacement2)
			throws NoSuchElementException {
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBeVisible(
					webdriver.findElement(getLocator(elementToken, replacement1, replacement2)));
		} catch (NoSuchElementException excp) {
			fail("FAILED: Element " + elementToken + " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}

	protected WebElement element(String elementToken, String replacement) throws NoSuchElementException,ClassCastException{
		WebElement elem = null;
		try {
			wait.hardWait(2);
			elem = webdriver.findElement(getLocator(elementToken, replacement));
			//elem = wait.waitForElementToBeVisible(webdriver.findElement(getLocator(elementToken, replacement)));
		} catch (NoSuchElementException excp) {
			fail("FAILED: Element '" + elementToken + "' not found on the " + this.pageName + " !!!");
		}
		catch (ClassCastException excp) {
			
		}
		return elem;
	}

	protected List<WebElement> elements(String elementToken, String replacement) {
		return wait.waitForElementsToBeVisible(webdriver.findElements(getLocator(elementToken, replacement)));
	}

	protected List<WebElement> elements(String elementToken, String replacement1, String replacement2) {
		return webdriver.findElements(getLocator(elementToken, replacement1, replacement2));
	}

	protected List<WebElement> elements(String elementToken) {
		return elements(elementToken, "");
	}

	
	protected void _waitForElementToDisappear(String elementToken, String replacement) {
		int i = 0;
		int initTimeout = wait.getTimeout();
		wait.resetImplicitTimeout(2);
		int count;
		while (i <= 20) {
			if (replacement.isEmpty())
				count = elements(elementToken).size();
			else
				count = elements(elementToken, replacement).size();
			if (count == 0)
				break;
			i += 2;
		}
		wait.resetImplicitTimeout(initTimeout);
	}

	protected void waitForElementToDisappear(String elementToken) {
		_waitForElementToDisappear(elementToken, "");
	}

	protected void waitForElementToDisappear(String elementToken, String replacement) {
		_waitForElementToDisappear(elementToken, replacement);
	}

	protected void isStringMatching(String actual, String expected) {
		logMessage("Comparing 2 strings");
		logMessage("EXPECTED STRING :" + expected);
		logMessage("ACTUAL STRING :" + actual);
		Assert.assertEquals(actual, expected, "The strings does not match!!!");
		logMessage("String compare ASSERTION passed.");
	}

	protected boolean isElementDisplayed(String elementName, String elementTextReplace) {
		wait.waitForElementToBeVisible(element(elementName, elementTextReplace));
		boolean result = element(elementName, elementTextReplace).isDisplayed();
		assertTrue(result, "ASSERTION FAILED: element '" + elementName + "with text " + elementTextReplace
				+ "' is not displayed.");
		logMessage("ASSERTION PASSED: element " + elementName + " with text " + elementTextReplace + " is displayed.");
		return result;
	}

	protected boolean isElementDisplayed(String elementName, String elementTextReplace1, String elementTextReplace2,
			String elementTextReplace3) {
		wait.waitForElementToBeVisible(
				element(elementName, elementTextReplace1, elementTextReplace2, elementTextReplace3));
		boolean result = element(elementName, elementTextReplace1, elementTextReplace2, elementTextReplace3)
				.isDisplayed();
		assertTrue(result, "ASSERT FAILED : Element '" + elementName + "with text " + elementTextReplace1
				+ elementTextReplace2 + elementTextReplace3 + "' is not displayed.");
		logMessage("ASSERT PASSED : Element " + elementName + " with text " + elementTextReplace1 + elementTextReplace2
				+ elementTextReplace3 + " is displayed.");
		return result;
	}

	protected WebElement element(String elementToken, String replacement1, String replacement2, String replacement3)
			throws NoSuchElementException {
		WebElement elem = null;
		By locator = getLocator(elementToken, replacement1, replacement2, replacement3);
		try {
			elem = wait.waitForElementToBeVisible(webdriver.findElement(locator));
		} catch (TimeoutException excp) {
			throw new NoSuchElementException("Element " + elementToken + " with locator "
					+ locator.toString().substring(2) + " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}

	protected boolean isElementDisplayed(String elementName, String elementTextReplace1, String elementTextReplace2) {
		wait.waitForElementToBeVisible(element(elementName, elementTextReplace1, elementTextReplace2));
		boolean result = element(elementName, elementTextReplace1, elementTextReplace2).isDisplayed();
		assertTrue(result, "ASSERT FAILED : Element '" + elementName + "with text " + elementTextReplace1
				+ elementTextReplace2 + "' is not displayed.");
		logMessage("ASSERT PASSED : Element " + elementName + " with text " + elementTextReplace1 + ","
				+ elementTextReplace2 + " is displayed.");
		return result;
	}

	protected boolean isElementNotDisplayed(String elementName, String elementTextReplace) {
		wait.waitForElementToBeVisible(element(elementName, elementTextReplace));
		boolean result = element(elementName, elementTextReplace).isDisplayed();
		assertTrue(!result,
				"ASSERTION FAILED: element '" + elementName + "with text " + elementTextReplace + "' is displayed.");
		logMessage("ASSERTION PASSED: element " + elementName + " with text " + elementTextReplace
				+ " is  not displayed.");
		return result;
	}

	protected void verifyElementText(String elementName, String expectedText) {
		wait.waitForElementToBeVisible(element(elementName));
		assertEquals(element(elementName).getText().trim(), expectedText,
				"ASSERTION FAILED: element '" + elementName + "' Text is not as expected: ");
		logMessage("ASSERTION PASSED: element " + elementName + " is visible and Text is " + expectedText);
	}

	protected void verifyElementTextContains(String elementName, String expectedText) {
		wait.waitForElementToBeVisible(element(elementName));
		assertThat("ASSERTION FAILED: element '" + elementName + "' Text is not as expected: ",
				element(elementName).getText().trim(), containsString(expectedText));
		logMessage("ASSERTION PASSED: element " + elementName + " is visible and Text is " + expectedText);
	}

	protected boolean isElementDisplayed(String elementName) {
		wait.waitForElementToBeVisible(element(elementName));
		boolean result = element(elementName).isDisplayed();
		assertTrue(result, "ASSERTION FAILED: element '" + elementName + "' is not displayed.");
		logMessage("ASSERTION PASSED: element " + elementName + " is displayed.");
		return result;
	}

	protected boolean isElementNotDisplayed(String elementName) {
		boolean result;
		try {
			wait.waitForPageToLoadCompletely();
			driver.findElement(getLocator(elementName));
			result = false;
		} catch (NoSuchElementException excp) {
			result = true;
		}
		assertTrue(result, "ASSERTION Failed: element '" + elementName + "' is displayed.");
		logMessage("ASSERTION Passed: element " + elementName + " is not displayed as expected!!!");
		return result;
	}

	protected void doHoverOverElement(WebElement element) {
		Actions builder = new Actions(driver);
		builder.moveToElement(element).build().perform();
	}

	protected boolean isElementEnabled(String elementName, boolean expected) {
		wait.waitForElementToBeVisible(element(elementName));
		boolean result = expected && element(elementName).isEnabled();
		assertTrue(result, "ASSERTION FAILED: element '" + elementName + "' is  ENABLED :- " + !expected);
		logMessage("ASSERTION PASSED: element " + elementName + " is enabled :- " + expected);
		return result;
	}

	protected By getLocator(String elementToken, String replacement1, String replacement2, String replacement3) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement1);
		locator[2] = locator[2].replaceFirst("\\%\\{.+?\\}", replacement2);
		locator[2] = locator[2].replaceFirst("\\#\\{.+?\\}", replacement3);
		return getBy(locator[1].trim(), locator[2].trim());
	}

	protected By getLocator(String elementToken) {
		return getLocator(elementToken, "");
	}

	protected By getLocator(String elementToken, String replacement) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceAll("\\$\\{.+\\}", replacement);
		return getBy(locator[1].trim(), locator[2].trim());
	}

	protected By getLocator(String elementToken, String replacement1, String replacement2) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement1);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement2);
		return getBy(locator[1].trim(), locator[2].trim());
	}

	public void clickOnFirstLinkBasedOnProvidedText(String elementToken, String linkText) {
		element(elementToken, linkText).click();
	}

	public boolean matchGivenPatternWithProvidedText(String pattern, String text) {
		Matcher matcher = Pattern.compile(pattern).matcher(text);
		return matcher.matches();
	}

	private By getBy(String locatorType, String locatorValue) {
		switch (Locators.valueOf(locatorType)) {
		case id:
			return By.id(locatorValue);
		case xpath:
			return By.xpath(locatorValue);
		case css:
			return By.cssSelector(locatorValue);
		case name:
			return By.name(locatorValue);
		case classname:
			return By.className(locatorValue);
		case linktext:
			return By.linkText(locatorValue);
		default:
			return By.id(locatorValue);
		}
	}

	protected boolean checkIfElementIsThere(String eleString, String replacementEleString) {
		boolean flag = false;
		wait.resetImplicitTimeout(2);
		wait.resetImplicitTimeout(10);
		try {
			if (webdriver.findElement(getLocator(eleString, replacementEleString)).isDisplayed()) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (NoSuchElementException ex) {
			flag = false;
		}
		return flag;
	}

	protected boolean checkIfElementIsThere(String eleString) {
		boolean flag = false;
		wait.resetImplicitTimeout(2);
		wait.resetImplicitTimeout(10);
		try {
			if (webdriver.findElement(getLocator(eleString)).isDisplayed()) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (NoSuchElementException ex) {
			flag = false;
		}
		return flag;
	}
}

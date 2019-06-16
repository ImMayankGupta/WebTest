/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.getpageobjects;

import static com.automation.getpageobjects.ObjectFileReader.getPageTitleFromFile;
import static com.automation.utils.ConfigPropertyReader.getProperty;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.automation.utils.SeleniumWait;


public class BaseUi {

	WebDriver driver;
	protected SeleniumWait wait;
	private String pageName;


	protected BaseUi(WebDriver driver, String pageName) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		this.pageName = pageName;
		this.wait = new SeleniumWait(driver, Integer.parseInt(getProperty(
				"Config.properties", "timeout")));
	}

	protected String getPageTitle() {
		return driver.getTitle();
	}

	protected void logMessage(String message) {
		Reporter.log(message, true);
	}

	protected String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	protected void verifyPageTitleExact() {
		String pageTitle = getPageTitleFromFile(pageName);
		verifyPageTitleExact(pageTitle.trim());
	}

	protected void verifyPageTitleExact(String expectedPagetitle) {
		if ((
			(expectedPagetitle=="") || (expectedPagetitle == null) || (expectedPagetitle.isEmpty()))  && (getProperty("browser").equalsIgnoreCase("chrome"))){
			expectedPagetitle = getCurrentURL();
		}
		try {
			wait.waitForPageTitleToBeExact(expectedPagetitle);
			logMessage("TEST PASSED: PageTitle for " + pageName + " is exactly: '"
					+ expectedPagetitle + "'");
		} catch (TimeoutException ex) {
			Assert.fail("TEST FAILED: PageTitle for " + pageName + " is not exactly: '"
					+ expectedPagetitle + "'!!!\n instead it is :- " + driver.getTitle());
		}
	}

	
	protected void verifyPageTitleContains() {
		String expectedPagetitle = getPageTitleFromFile(pageName).trim();
		verifyPageTitleContains(expectedPagetitle);
	}

	protected void verifyPageTitleContains(String expectedPagetitle) {

		if ((
					(expectedPagetitle=="") || (expectedPagetitle == null) || (expectedPagetitle.isEmpty()))
					&& (getProperty("browser").equalsIgnoreCase("chrome"))){
			expectedPagetitle = getCurrentURL();
		}
		try {
			wait.waitForPageTitleToContain(expectedPagetitle);
		} catch (TimeoutException exp) {
			String actualPageTitle = driver.getTitle().trim();
			logMessage("TEST FAILED: As actual Page Title: '" + actualPageTitle
					+ "' does not contain expected Page Title : '"
					+ expectedPagetitle + "'.");
		}
		String actualPageTitle = getPageTitle().trim();
		logMessage("TEST PASSED: PageTitle for " + actualPageTitle + " contains: '"
				+ expectedPagetitle + "'.");
	}

	protected WebElement getElementByIndex(List<WebElement> elementlist,
			int index) {
		return elementlist.get(index);
	}

	protected WebElement getElementByExactText(List<WebElement> elementlist,
			String elementtext) {
		WebElement element = null;
		for (WebElement elem : elementlist) {
			if (elem.getText().equalsIgnoreCase(elementtext.trim())) {
				element = elem;
			}
		}
	
		if (element == null) {
		}
		return element;
	}

	protected WebElement getElementByContainsText(List<WebElement> elementlist,
			String elementtext) {
		WebElement element = null;
		for (WebElement elem : elementlist) {
			if (elem.getText().contains(elementtext.trim())) {
				element = elem;
			}
		}
		if (element == null) {
		}
		return element;
	}

	protected void switchToFrame(WebElement element) {
		wait.waitForElementToBeVisible(element);
		driver.switchTo().frame(element);
	}

	public void switchToFrame(int i) {
		driver.switchTo().frame(i);
	}

	public void switchToFrame(String id) {
		driver.switchTo().frame(id);
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	protected void executeJavascript(String script) {
		((JavascriptExecutor) driver).executeScript(script);
	}

	protected void hover(WebElement element) {
		Actions hoverOver = new Actions(driver);
		hoverOver.moveToElement(element).build().perform();
	}

	protected void handleAlert() {
		try {
			switchToAlert().accept();
			logMessage("Alert handled..");
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			System.out.println("No Alert window appeared...");
		}
	}

	private Alert switchToAlert() {
		WebDriverWait wait = new WebDriverWait(driver, 1);
		return (Alert) ExpectedConditions.alertIsPresent();
	}

	protected void selectProvidedTextFromDropDown(WebElement el, String text) {
		wait.hardWait(1);
		Select sel = new Select(el);
		try {
			sel.selectByValue(text);
		} catch (StaleElementReferenceException ex1) {
			sel.selectByVisibleText(text);
			logMessage("Select Element " + el + " after catching Stale Element Exception");
		} 
	}
	
	protected void selectValueByVisibkeTextFromDropDown(WebElement el, String text) {
		wait.hardWait(1);
		Select sel = new Select(el);
		try {
			sel.selectByVisibleText(text);
		} catch (StaleElementReferenceException ex1) {
			sel.selectByVisibleText(text);
			logMessage("Select Element " + el + " after catching Stale Element Exception");
		} 
	}

	protected void scrollDown(WebElement element) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", element);
	}

	protected void hoverClick(WebElement element) {
		Actions hoverClick = new Actions(driver);
		hoverClick.moveToElement(element).click().build().perform();
	}

	protected void click(WebElement element) {
		try {
			element.click();
			logMessage("Clicked Element " + element + "");
		} catch (StaleElementReferenceException ex1) {
			
			element.click();
			logMessage("Clicked Element " + element + " after catching Stale Element Exception");
		} catch (WebDriverException ex3) {
			wait.waitForElementToBeClickable(element);
			scrollDown(element);
			element.click();
			logMessage("Clicked Element " + element + " after catching WebDriver Exception");
		} catch (Exception ex2) {
			logMessage("Element " + element + " could not be clicked! " + ex2.getMessage());
		}
	}
	
	public void clickUsingXpathInJavaScriptExecutor(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}
	
	public String getElementText(WebElement element) {
		return element.getText();

	}

}

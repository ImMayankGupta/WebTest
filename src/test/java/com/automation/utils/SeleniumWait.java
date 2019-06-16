package com.automation.utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumWait{
    
    WebDriver driver;
    WebDriverWait wait;
    
    int timeout;
    
    public SeleniumWait(WebDriver driver, int timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
        this.timeout = timeout;
    }

    /**
     * Returns webElement found by the locator if element is visible
     *
     * @param locator
     * @return
     */
    public WebElement getWhenVisible(By locator) {
        WebElement element;
        element = (WebElement) ExpectedConditions
                .visibilityOfElementLocated(locator);
        return element;
    }
    
    public WebElement getWhenClickable(By locator) {
        WebElement element;
        element = (WebElement) ExpectedConditions.elementToBeClickable(locator);
        return element;
    }
    
    public boolean waitForPageTitleToBeExact(String expectedPagetitle) {
        return ExpectedConditions.titleIs(expectedPagetitle) != null;
    }
    
    public boolean waitForPageTitleToContain(String expectedPagetitle) {
        return ExpectedConditions.titleContains(expectedPagetitle) != null;
    }
    
    public WebElement waitForElementToBeVisible(WebElement element) {
        return (WebElement)ExpectedConditions.visibilityOf(element);
    }
    
    public void waitForFrameToBeAvailableAndSwitchToIt(By locator) {
        ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator);
    }
    
    public List<WebElement> waitForElementsToBeVisible(List<WebElement> elements) {
        return (List<WebElement>) ExpectedConditions.visibilityOfAllElements(elements);
    }
    
    public boolean waitForElementToBeInVisible(By locator) {
        return ExpectedConditions.invisibilityOfElementLocated(locator) != null;
    }
    
    public WebElement waitForElementToBeClickable(WebElement element) {
        return (WebElement) ExpectedConditions.elementToBeClickable(element);
    }
    
    public void clickWhenReady(By locator) {
        WebElement element = (WebElement) ExpectedConditions
                .elementToBeClickable(locator);
        element.click();
    }

    public void waitForPageTitleToAppearCompletely(String txtPageTitle) {
    	WebDriverWait wait = new WebDriverWait(driver, 30);
		ExpectedConditions.titleIs(txtPageTitle);
    }
    
    public void waitForMsgToastToDisappear() {
        int i = 0;
        resetImplicitTimeout(1);
        try {
            while (driver.findElement(By.className("toast-message")).isDisplayed() && i <= timeout) {
                hardWait(1);                
                i++;
            }
        } catch (Exception e) {
        }
        resetImplicitTimeout(timeout);        
    }
    
    public void waitForElementToDisappear(WebElement element) {
        int i = 0;
        resetImplicitTimeout(2);
        try {
            while (element.isDisplayed() && i <= timeout) {
                hardWait(1);                
                i++;
            }
        } catch (Exception e) {
        }
        resetImplicitTimeout(timeout);        
    }
    
    public void resetImplicitTimeout(int newTimeOut) {
        try {
            driver.manage().timeouts().implicitlyWait(newTimeOut, TimeUnit.SECONDS);
        } catch (Exception e) {	
        }
    }

    // TODO Implement Wait for page load for page synchronizations
    public void waitForPageToLoadCompletely() {
        ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//*"));
    }

    public void hardWait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    public int getTimeout() {
    	return timeout;
    }
    
}

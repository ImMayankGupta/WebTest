package com.automation.keywords;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.openqa.selenium.WebDriver;


import com.automation.getpageobjects.GetPage;

public class WebTestActions extends GetPage{
	
	WebDriver driver;
	
	public WebTestActions(WebDriver driver) {
		super(driver, "WebTest");
		this.driver = driver;
	}
	
	public void clickOnSignInIcon() {
		hoverClick(element("signIn"));
		logMessage("Step: clicked on Sign In icon \n");
	}

	public void enterEmailAddressInCreateAccountBox(String email) {
		element("email_create").sendKeys(email);
		logMessage("Step: successfully entered email address in create account box \n");
	}

	public void clickOnCreateAccountButton() {
		hoverClick(element("createAccount_btn"));
		logMessage("Step: clicked on create account button \n");
	}

	public void enterPersonalInformation(String name, String surname,String pwd,String post,String phone,String mobile) {
		hoverClick(element("title"));
		element("firstName").sendKeys(name);
		element("lastName").sendKeys(surname);
		element("password").sendKeys(pwd);
		selectProvidedTextFromDropDown(element("day"),"1");
		selectProvidedTextFromDropDown(element("month"),"1");
		selectProvidedTextFromDropDown(element("year"),"2000");
		element("company").sendKeys(name);
		element("address1").sendKeys(name);
		element("address2").sendKeys(name);
		element("city").sendKeys(name);
		selectValueByVisibkeTextFromDropDown(element("state"),"Colorado");
		element("postcode").sendKeys(post);
		element("other").sendKeys(name);
		element("phone").sendKeys(phone);
		element("mobile").sendKeys(mobile);
		element("alias").sendKeys(name);
		hoverClick(element("submit"));
		logMessage("Step: successfully entered personal information \n");
	}

	public void verifyDetailsOnMyAccountPage(String name, String surname) {
		assertEquals(element("heading").getText(), "MY ACCOUNT");	
		assertEquals(element("account").getText(), name + " " + surname);
		assertTrue(element("infoaccount").getText().contains("Welcome to your account."));
		assertTrue(element("logout").isDisplayed());
        assertTrue(driver.getCurrentUrl().contains("controller=my-account"));
        logMessage("Step: successfully verified details on my account page \n");
        
	}

	public void enterEmailAddressAndPassword(String existingUserEmail, String existingUserPassword) {
		element("email").sendKeys(existingUserEmail);
		element("password").sendKeys(existingUserPassword);
		logMessage("Step: entered email address and password in login form \n");
	}

	public void clickOnSubmitLoginButton() {
		hoverClick(element("submitLogin"));
		logMessage("Step: clicked on Login button \n");
	}

	public void clickOnSignOutButton() {
		hoverClick(element("logout"));
		logMessage("Step: clicked on Sign out button \n");
	}

	public void clickOnWomenTab() {
		hoverClick(element("womenTab"));
		logMessage("Step: clicked on Women tab \n");
	}

	public void addItemToCart() {
		hoverClick(element("item"));
		hoverClick(element("itemAddToCart"));
		logMessage("Step: selected item to cart \n");
	}

	public void clickOnProceedToCheckoutButtonOnSummaryPage() {
		hoverClick(element("checkout"));
		hoverClick(element("summaryCheckout"));
		logMessage("Step: clicked on proceed to checkout button on summary page \n");
	}

	public void clickOnProceedToCheckoutButtonOnSignInPage() {
		hoverClick(element("checkout"));
		logMessage("Step: clicked on proceed to checkout button on sign in page \n");
	}

	public void clickOnProceedToCheckoutButtonOnAddressPage() {
		hoverClick(element("addressCheckout"));
		logMessage("Step: clicked on proceed to checkout button on address page \n");
	}

	public void selectCheckboxForTermsServices() {
		hoverClick(element("agree"));
		logMessage("Step: select terms and service \n");
	}

	public void clickOnProceedToCheckoutButtonOnShippingPage() {
		hoverClick(element("addressCheckout"));
		logMessage("Step: clicked on proceed to checkout button on shipping page \n");
	}

	public void selectPaymentOption() {
		hoverClick(element("bankWire"));
		logMessage("Step: selected payment option \n");
	}

	public void clickOnConfirmOrderButton() {
		hoverClick(element("confirmOrder"));
		logMessage("Step: clicked on confirm order button \n");
	}
	
	public void verifyDetailsOnConfirmationPage() {
		assertEquals(element("heading").getText(), "ORDER CONFIRMATION");	
		assertTrue(element("shippingTab").isDisplayed());
		assertTrue(element("paymentTab").isDisplayed());
		assertTrue(element("indent").getText().contains("Your order on My Store is complete."));	
		assertTrue(driver.getCurrentUrl().contains("controller=order-confirmation"));
		logMessage("Step: successfully verified details on confirmation page\n");
	}
}

package com.automation.tests;

import static com.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.Date;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.automation.TestSessionInitiator;


public class WebTest {

	TestSessionInitiator test;
	String timestamp = String.valueOf(new Date().getTime());
    String email = "hf_challenge_" + timestamp + "@hf" + timestamp.substring(7) + ".com";
    
    @Parameters("browser")
	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication(getYamlValue("loginUrl"));
	}

	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.stepStartMessage(method.getName());
	}
   
	@Test()
	public void test1_verifySignUpFunctionality(){
		test.webTestActions.clickOnSignInIcon();
	    test.webTestActions.enterEmailAddressInCreateAccountBox(email);
        test.webTestActions.clickOnCreateAccountButton();
		test.webTestActions.enterPersonalInformation(getYamlValue("name"),getYamlValue("surname"),getYamlValue("password")
				,getYamlValue("post"),getYamlValue("phone"),getYamlValue("mobile"));
		test.webTestActions.verifyDetailsOnMyAccountPage(getYamlValue("name"),getYamlValue("surname"));
		test.webTestActions.clickOnSignOutButton();
	}
	
	@Test()
	public void test2_verifyLogInFunctionality(){
		test.webTestActions.clickOnSignInIcon();
		test.webTestActions.enterEmailAddressAndPassword(getYamlValue("existingUserEmail"),getYamlValue("existingUserPassword"));
		test.webTestActions.clickOnSubmitLoginButton();
		test.webTestActions.verifyDetailsOnMyAccountPage(getYamlValue("firstname"),getYamlValue("lastname"));
	}
	
	@Test()
	public void test3_verifyCheckOutFunctionality(){
		test.webTestActions.clickOnWomenTab();
		test.webTestActions.addItemToCart();
		test.webTestActions.clickOnProceedToCheckoutButtonOnSummaryPage();
		test.webTestActions.clickOnProceedToCheckoutButtonOnSignInPage();
		test.webTestActions.clickOnProceedToCheckoutButtonOnAddressPage();
		test.webTestActions.selectCheckboxForTermsServices();
		test.webTestActions.clickOnProceedToCheckoutButtonOnShippingPage();
		test.webTestActions.selectPaymentOption();
		test.webTestActions.clickOnConfirmOrderButton();
		test.webTestActions.verifyDetailsOnConfirmationPage();
	}

	
	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}
	
	@AfterMethod
	public void handleTestMethod(Method method) {
		test.stepFinishMessage(method.getName());
	}

	@AfterClass
	public void close_Test_Session() {
		test.closeBrowserSession();
	}

}

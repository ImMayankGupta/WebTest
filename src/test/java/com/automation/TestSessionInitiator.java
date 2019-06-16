package com.automation;

import static com.automation.utils.ConfigPropertyReader.getProperty;
import static com.automation.utils.YamlReader.getYamlValue;
import static com.automation.utils.YamlReader.setYamlFilePath;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import com.automation.keywords.WebTestActions;
import com.automation.utils.TakeScreenshot;


public class TestSessionInitiator {

	protected WebDriver driver;
	private final WebDriverFactory wdfactory;
	String browser;
	String seleniumserver;
	String seleniumserverhost;
	String appbaseurl;
	String applicationpath;
	String chromedriverpath;
	String datafileloc = "";
	static int timeout;
	Map<String, Object> chromeOptions = null;
	DesiredCapabilities capabilities;

	public WebTestActions webTestActions;
	
	public TakeScreenshot takescreenshot;
	public WebDriver getDriver() {
		return this.driver;
	}

	private void _initPage() {
		webTestActions= new WebTestActions(driver);
	}

	public TestSessionInitiator(String testname) {
		wdfactory = new WebDriverFactory();
		testInitiator(testname);
	}

	private void testInitiator(String testname) {
		setYamlFilePath();
		_configureBrowser();
		_initPage();
		takescreenshot = new TakeScreenshot(testname, this.driver);
	}
	private void _configureBrowser() {
		driver = wdfactory.getDriver(_getSessionConfig());
		driver.manage().window().maximize();
		driver.manage()
				.timeouts()
				.implicitlyWait(Integer.parseInt(getProperty("timeout")),
						TimeUnit.SECONDS);
	}

	private Map<String, String> _getSessionConfig() {
		String[] configKeys = { "tier", "browser", "seleniumserver", "seleniumserverhost", "timeout", "driverpath",
		"product" };
Map<String, String> config = new HashMap<String, String>();
for (String string : configKeys) {
	try {
		if (System.getProperty(string).isEmpty())
			config.put(string, getProperty("./Config.properties", string));
		else
			config.put(string, System.getProperty(string));
	} catch (NullPointerException e) {
		config.put(string, getProperty("./Config.properties", string));
	}
}
return config;
	}

	public void launchApplication() {
		launchApplication(getYamlValue("app_url"));
	}

	public void launchApplication(String loginUrl) {
		Reporter.log("\nThe application url is :- " + loginUrl, true);
		Reporter.log("The test browser is :- " + _getSessionConfig().get("browser") + "\n", true);
		driver.manage().deleteAllCookies();
		driver.get(loginUrl);
	}

	public void openUrl(String url) {
		driver.get(url);
	}

	public void closeBrowserSession() {
		Reporter.log("\n", true);
		driver.quit();
	}
	
	public void stepStartMessage(String testStepName) {
		Reporter.log(" ", true);
		Reporter.log("***** STARTING TEST STEP:- " + testStepName.toUpperCase() + " *****", true);
		Reporter.log(" ", true);
	}

	public void stepFinishMessage(String name) {
		Reporter.log(" ", true);
		Reporter.log("***** Completed TEST STEP:- " + name.toUpperCase() + " *****", true);
		Reporter.log(" ", true);
	}
	
}

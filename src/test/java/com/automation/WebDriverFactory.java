
package com.automation;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverFactory {

    private static String browser;
    String seleniumServer;
    private static final DesiredCapabilities capabilities = new DesiredCapabilities();

    public WebDriver getDriver(Map<String, String> seleniumconfig) {
    	seleniumServer = seleniumconfig.get("seleniumserver");

		browser = seleniumconfig.get("browser");

		if (seleniumServer.equalsIgnoreCase("local")) {
			if (browser.equalsIgnoreCase("firefox")) {
				if (System.getProperty("os.name").equals("Linux")) {
					return getChromeDriver(seleniumconfig.get("driverpath") + "geckodriver");
				} else if (System.getProperty("os.name").contains("Windows")) {
					return getFirefoxDriver(seleniumconfig.get("driverpath") + "geckodriver.exe");
				}
				
			} else if (browser.equalsIgnoreCase("chrome")) {

				if (System.getProperty("os.name").equals("Linux")) {
					return getChromeDriver(seleniumconfig.get("driverpath") + "chromedriver");
				} else if (System.getProperty("os.name").contains("Windows")) {
					return getChromeDriver(seleniumconfig.get("driverpath") + "chromedriver.exe");
				}

			} else if (browser.equalsIgnoreCase("Safari")) {
				return getSafariDriver();
			} else if ((browser.equalsIgnoreCase("ie")) || (browser.equalsIgnoreCase("internetexplorer"))
					|| (browser.equalsIgnoreCase("internet explorer"))) {
				return getInternetExplorerDriver(seleniumconfig.get("driverpath") + "IEDriverServer.exe");
			}
		}
		if (seleniumServer.equalsIgnoreCase("remote")) {
			return setRemoteDriver(seleniumconfig);
		}
		return new FirefoxDriver();
    }

    private WebDriver setRemoteDriver(Map<String, String> selConfig) {
        DesiredCapabilities cap = null;
        browser = selConfig.get("browser");
        if (browser.equalsIgnoreCase("firefox")) {
            cap = DesiredCapabilities.firefox();
        } else if (browser.equalsIgnoreCase("chrome")) {
            cap = DesiredCapabilities.chrome();
        } else if (browser.equalsIgnoreCase("Safari")) {
            cap = DesiredCapabilities.safari();
        } else if ((browser.equalsIgnoreCase("ie"))
                || (browser.equalsIgnoreCase("internetexplorer"))
                || (browser.equalsIgnoreCase("internet explorer"))) {
            cap = DesiredCapabilities.internetExplorer();
        }
        String seleniuhubaddress = selConfig.get("seleniumserverhost");
        URL selserverhost = null;
        try {
            selserverhost = new URL(seleniuhubaddress);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        cap.setJavascriptEnabled(true);
        return new RemoteWebDriver(selserverhost, cap);
    }

    @SuppressWarnings("deprecation")
	private static WebDriver getChromeDriver(String driverpath) {
    	System.setProperty("webdriver.chrome.driver", driverpath);
		ChromeOptions options = new ChromeOptions();
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();

		chromePrefs.put("download.prompt_for_download", "false");
		// disable flash and the PDF viewer
		chromePrefs.put("plugins.plugins_disabled", new String[] { "Adobe Flash Player", "Chrome PDF Viewer" });

		chromePrefs.put("profile.default_content_settings.popups", 0);
		//chromePrefs.put("download.default_directory", downloadFilePath);
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("--disable-extensions");
		options.addArguments("test-type");
		options.addArguments("--disable-impl-side-painting");

		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		return new ChromeDriver(cap);
    }

    private static WebDriver getInternetExplorerDriver(String driverpath) {
        System.setProperty("webdriver.ie.driver", driverpath);
        capabilities.setCapability("ignoreZoomSetting", true);
        capabilities.setCapability("ignoreZoomLevel", true);
        return new InternetExplorerDriver(capabilities);
    }

    private static WebDriver getSafariDriver() {
        return new SafariDriver();
    }

    private static WebDriver getFirefoxDriver(String path) {
		System.setProperty("webdriver.gecko.driver", path);
		FirefoxProfile profile;
	    profile = new FirefoxProfile();

		// profile = new FirefoxProfile();
		profile.setPreference("browser.download.useDownloadDir", true);
		profile.setPreference("browser.cache.disk.enable", false);
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("intl.accept_languages", "UTF-8");
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"application/pdf, application/csv, application/ris, text/csv,text/plain, application/zip, application/x-zip, application/x-zip-compressed, application/download, application/octet-stream,text/csv,application/vnd.ms-excel");
		profile.setPreference("plugin.disable_full_page_plugin_for_types",
				"application/pdf,application/vnd.adobe.xfdf,application/vnd.fdf,application/vnd.adobe.xdp+xml");
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.manager.focusWhenStarting", false);
		//profile.setPreference("browser.download.dir", downloadFilePath);

		profile.setPreference("browser.download.manager.showAlertOnComplete", false);

		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("pdfjs.disabled", true);
		profile.setPreference("browser.download.panel.shown", false);
		profile.setPreference("browser.download.useToolkitUI", true);
		profile.setPreference("dom.disable_beforeunload", true);
		profile.setPreference("browser.download.manager.closeWhenDone", true);
		// profile.setPreference("network.http.phishy-userpass-length", 255);
		// profile.setPreference("network.automatic-ntlm-auth.trusted-uris","yourDomain");
		// return new FirefoxDriver(ffbinary,profile);

//		FirefoxOptions options = new FirefoxOptions();
//		options.setProfile(profile);
		return new FirefoxDriver((Capabilities) profile);
	}
}

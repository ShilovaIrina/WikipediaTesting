package testframework.helpers;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class CustomDriver {

    public static void createDriver() {
        Configuration.pageLoadStrategy = "eager";
        Configuration.browser = ConfigHelper.getBrowserName();
        Configuration.browserSize = ConfigHelper.getWindowWidth() + "x" + ConfigHelper.getWindowHeight();
        Configuration.pageLoadTimeout = 60000;

        DesiredCapabilities capabilities = new DesiredCapabilities();
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-infobars");
        chromeOptions.addArguments("--lang=ru-ru");

        chromeOptions.addExtensions(new File("src/main/resources/extension.crx"));
        if (ConfigHelper.isHeadless())
            chromeOptions.addArguments("--headless");
        if (ConfigHelper.isRemote()) {
            chromeOptions.addArguments("--window-size=1920x1080");
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("sessionTimeout", "10m");
            if (ConfigHelper.isVideo()) {
                capabilities.setCapability("enableVideo", true);
                capabilities.setCapability("videoFrameRate", 24);
            }
            capabilities.setVersion("chrome_90_csp"); //chrome_90_csp
            Configuration.remote = ConfigHelper.getRemoteUrl();
            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            Configuration.browserCapabilities = capabilities;
        } else {
            if ("chrome".equals(ConfigHelper.getBrowserName())) {
                chromeOptions.addArguments("--window-size=1920x1080");
                System.setProperty("webdriver.chrome.whitelistedIps", "");
                capabilities.setBrowserName(String.valueOf(BrowserName.CHROME));
                capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            } else {
                if ("firefox".equals(ConfigHelper.getBrowserName())) {
                    FirefoxOptions firefoxOptions = new FirefoxOptions();

                    firefoxOptions.addArguments("--no-sandbox");
                    firefoxOptions.addArguments("--disable-notifications");
                    firefoxOptions.addArguments("--disable-infobars");
                    firefoxOptions.addArguments("--lang=ru-ru");

                    capabilities.setBrowserName(String.valueOf(BrowserName.FIREFOX));
                    capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
                } else {
                    throw new RuntimeException("Unknown browser name: " + ConfigHelper.getBrowserName());
                }
            }
        }
    }
}


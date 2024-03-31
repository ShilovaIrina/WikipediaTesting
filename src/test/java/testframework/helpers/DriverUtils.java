package testframework.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class DriverUtils {
    private static final Logger LOG = LogManager.getLogger(AttachmentsHelper.class);

    private static String getRemoteSessionId() {
        return ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
    }

    public static String getRemoteSessionIdFromSelenoid() {
        return getRemoteSessionId().replace("selenoid", "");
    }

    public static byte[] getScreenshotAsBytes() {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    public static byte[] getPageSourceAsBytes() {
        return getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
    }

    public static URL getVideoUrl(String sessionId) {
        String videoUrl = ConfigHelper.getVideoStorageUrl() + sessionId + ".mp4";

        try {
            return new URL(videoUrl);
        } catch (MalformedURLException e) {
            LOG.warn("[ALLURE VIDEO ATTACHMENT ERROR] Wrong test video url, {}", videoUrl);
            e.printStackTrace();
        }
        return null;
    }
}

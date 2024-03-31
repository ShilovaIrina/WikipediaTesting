package testframework.helpers;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.openqa.selenium.logging.LogType.BROWSER;

@SuppressWarnings("ALL")
public class AttachmentsHelper {
    private static final Logger LOG = LogManager.getLogger(AttachmentsHelper.class);

    private AttachmentsHelper() {
    }

    @Attachment(value = "{attachName}", type = "text/plain")
    private static String addMessage(String attachName, String text) {
        return text;
    }

    public static void addBrowserConsoleLogs() {
        addMessage("Browser console logs", String.join("\n", Selenide.getWebDriverLogs(BROWSER)));
    }

    @Attachment(value = "{attachName}", type = "image/png")
    public static byte[] addScreenshotAs(String attachName) {
        return DriverUtils.getScreenshotAsBytes();
    }

    @Attachment(value = "Page source", type = "text/plain")
    public static byte[] attachPageSource() {
        return getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
    }


    public static void addVideo(String sessionId) {
        URL videoUrl = DriverUtils.getVideoUrl(sessionId);
        if (videoUrl != null) {
            InputStream videoInputStream = null;
            sleep(1000);

            for (int i = 0; i < 10; i++) {
                try {
                    videoInputStream = videoUrl.openStream();
                    break;
                } catch (FileNotFoundException e) {
                    sleep(1000);
                } catch (IOException e) {
                    LOG.warn("[ALLURE VIDEO ATTACHMENT ERROR] Cant attach allure video, {}", videoUrl);
                    e.printStackTrace();
                }
            }
            Allure.addAttachment("Video", "video/mp4", videoInputStream, "mp4");
        }
    }
}

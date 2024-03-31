package testframework.pagemodel;

import com.codeborne.selenide.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import testframework.helpers.BrowserName;
import testframework.helpers.ConfigHelper;
import testframework.helpers.CustomDriver;
import testframework.helpers.Runner;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.parameter;
import static io.qameta.allure.Allure.step;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static testframework.helpers.AttachmentsHelper.*;

@SuppressWarnings("UnusedDeclaration")
@Execution(CONCURRENT)
@Timeout(420)
@ExtendWith(Runner.class)
public class TestBase {
    static final Logger LOG = LogManager.getLogger(TestBase.class);
    static final Boolean HEADLESS = null;
    static final int elementTimeoutSec = 30;

    private static String testURL = "https://www.wikipedia.org/";


    private SelenideDriver seDriver;
    private SelenideConfig seConfig;

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface PageTypeAnno {
        String value() default "mainPage";
    }

    static void checkPageTypeAnno(TestInfo testInfo) throws Exception {
        Optional<Class<?>> testClass = testInfo.getTestClass();
        Optional<Method> testMethod = testInfo.getTestMethod();
        String pageType = "";
        if (testMethod.isPresent() && testMethod.get().isAnnotationPresent(PageTypeAnno.class)) {
            pageType = testMethod.get().getAnnotation(PageTypeAnno.class).value();
        } else if (testClass.isPresent() && testClass.get().isAnnotationPresent(PageTypeAnno.class)) {
            pageType = testClass.get().getAnnotation(PageTypeAnno.class).value();
        }
        if (Objects.equals(pageType, "mainPage")) {
            testURL = ConfigHelper.getMainPageUrl();
        } else if (Objects.equals(pageType, "articlePage")) {
            testURL = ConfigHelper.getArticlePageUrl();
        } else if (Objects.equals(pageType, "extSearchPage")) {
            testURL = ConfigHelper.getExtSearchPageUrl();
        }
    }

    /**
     * Инициализация окружения автотестов
     * Создание драйвера и подключение к БД
     */
    @BeforeAll
    static void setUpOnce(TestInfo testInfo) throws Exception {

        CustomDriver.createDriver();
        checkPageTypeAnno(testInfo);

        open(testURL);

    }

    ;

    /**
     * Инициализирует браузер и осуществляет вход под администратором
     */
    @BeforeEach
    public void setUpEach(TestInfo testInfo) throws Exception {

        SelenideElement setitle = $x("//title").should(exist, Duration.ofSeconds(elementTimeoutSec));

        String title = Selenide.title();

        Pattern pattern = Pattern.compile("v\\d\\S*\\d");
        assert title != null;
        Matcher matcher = pattern.matcher(title);
        String version = "неизвестно";
        if (matcher.find()) {
            version = matcher.group();
        }

        parameter("Версия", version);

    }

    public static void info(String message) {
        LOG.info(message);
    }

    /**
     * Осуществляет уборку после выполнения тестов.
     * Делает выход из системы и закрывает браузер
     */
    @AfterEach
    public void finish() throws Exception {
        quitTest();
    }

    @AfterAll
    public static void quitAndClean() throws Exception, Throwable {
        Selenide.clearBrowserLocalStorage();
    }

    ;

    public static void quitTest() throws Exception {
        String sessionId = ((RemoteWebDriver) getWebDriver()).getSessionId().toString().replace("selenoid", "");
        step("Attachments", () -> {
            addScreenshotAs("Last screenshot");
            attachPageSource();
            //addBrowserConsoleLogs();
        });

        if (ConfigHelper.isVideo()) {
            step("Video", () -> addVideo(sessionId));
        }
        WebDriverRunner.getAndCheckWebDriver().navigate().refresh();
        //WebDriverRunner.clearBrowserCache();
    }

}
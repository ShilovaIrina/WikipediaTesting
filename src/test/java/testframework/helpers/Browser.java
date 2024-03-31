package testframework.helpers;

import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.actions;

@SuppressWarnings("WeakerAccess")
public final class Browser {
    private static final Logger LOG = LogManager.getLogger(Browser.class);
    private static String URL;
    private static final Boolean HEADLESS = ConfigHelper.isHeadless();

    private final WebDriver driver = HEADLESS ? new ChromeDriver(getChromeOptions()) : new ChromeDriver();
    //private static final Actions actionsProvider = new Actions(WebDriverRunner.getWebDriver());
    private final WebDriverWait wait = new WebDriverWait(driver, ConfigHelper.getWaitTimeoutSec(), ConfigHelper.getSleepIntervalMillis());

    public static ChromeOptions getChromeOptions() {
        Boolean headless = ConfigHelper.isHeadless();
        return new ChromeOptions().setHeadless(headless).addArguments("--window-size=1800x1000", "--disable-features=VizDisplayCompositor");
    }

    public static String getUri() {
        return URL;
    }

    public static void setUri(String newURL) {
        URL = newURL;
    }

    /**
     * Вызывает клик левой клавишей мыши на переданный элемент
     * Логирует нажатие с указание названия элемента {@code elemName}
     *
     * @param elm      элемент для вызова клика
     * @param elemName название объекта для указания в логе
     */
    public static void clickElem(SelenideElement elm, String elemName) {
        if (elm == null) throw new NotFoundException("Не найден элемент \"" + elemName + "\"");
        elm.click();
        LOG.info("Нажатие - {}", elemName);
    }

    /**
     * Возвращает объект драйвера {@link WebDriver WebDriver}, через который осуществляется взаимодействие с браузером
     *
     * @return объект-драйвер {@code WebDriver}
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Возвращает объект типа {@code WebDriverWait}, который используется для
     * обработки ожиданий по условиям в тестах
     * <blockquote>Примеры:
     * <pre>{@code
     *    Browser.getWait().until(ExpectedConditions.presenceOfElementLocated(subMenuSelector))
     * }</pre></blockquote>
     *
     * @return объект типа {@code WebDriverWait}
     */
    @Deprecated
    public WebDriverWait getWait() {
        return wait;
    }

    /**
     * Делается попытка найти {@code WebElement} по переданному локатору {@param locator}
     *
     * @param locator локатор, по которому ищется элемент
     * @return объект {@code WebElement}
     */
    public WebElement getElement(By locator) {
        return getDriver().findElements(locator).stream()
                .filter(WebElement::isEnabled)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Не найден объект " + locator.toString()));
    }

    /**
     * Отправить браузеру события клика на клавиши клавиатуры
     *
     * @param keys коды клавиш клавиатуры
     */
    public static void sendKeys(CharSequence keys) {
        Action keyPressed = actions().sendKeys(keys).build();
        keyPressed.perform();
    }

    /**
     * Отправить браузеру события нажатия на клавиши клавиатуры
     *
     * @param keys коды клавиш клавиатуры
     */
    public static void keyDown(CharSequence keys) {
        Action keyPressed = actions().keyDown(keys).build();
        keyPressed.perform();
    }

    /**
     * Отправить браузеру события отпускания клавиш клавиатуры
     *
     * @param keys коды клавиш клавиатуры
     */
    public static void keyUp(CharSequence keys) {
        Action keyPressed = actions().keyUp(keys).build();
        keyPressed.perform();
    }
}

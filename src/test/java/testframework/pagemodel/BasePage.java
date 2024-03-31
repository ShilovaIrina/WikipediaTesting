package testframework.pagemodel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import testframework.helpers.Browser;

import java.time.Duration;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;

/**
 * Базовый класс описывающий
 * поля и методы для всех страниц
 */
abstract public class BasePage {
    protected Logger LOG;
    protected String frameTitle;
    protected SelenideElement SEARCH_FIELD_LOCATOR;
    protected SelenideElement SUGGESTIONS_DROPDOWN_LOCATOR;
    protected SelenideElement SEARCH_BUTTON_LOCATOR;
    protected int elementTimeoutSec;

    abstract public void waitUntilFormOpened(String tabCaptionPart);

    abstract public SelenideElement setSearchFieldValue(String searchQuery);

    abstract public SelenideElement setSearchFieldValueWithMarkCheck(String searchQuery);

    public void pressEnter() {
        Browser.sendKeys(Keys.ENTER);
    }

    public SelenideElement pressSearchButton() {
        SEARCH_BUTTON_LOCATOR.shouldBe(Condition.and("Доступен для взаимодействия", visible, enabled), Duration.ofSeconds(elementTimeoutSec));
        SEARCH_BUTTON_LOCATOR.click();
        return SEARCH_BUTTON_LOCATOR;
    }

    abstract public void selectValueFromCombobox(String value);

}
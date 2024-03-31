package testframework.pagemodel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Класс для взаимодейтсвия
 * с элементами страницы с типом "Расширенный поиск"
 */
public class ExtSearchPage extends BasePage {
    SelenideElement SEARCH_FIELD_LOCATOR_UPPER = $x("//div[@id='simpleSearch']//input");
    SelenideElement SUGGESTIONS_DROPDOWN_LOCATOR_UPPER = $x("//div[@class='suggestions-results']");
    SelenideElement SEARCH_BUTTON_LOCATOR_UPPER = $x("//input[@id='searchButton' and @type='submit']");

    public ExtSearchPage(String title) throws Exception {

        waitUntilFormOpened(title);
        frameTitle = title;

        LOG = LogManager.getLogger(ExtSearchPage.class);

        SEARCH_FIELD_LOCATOR = $x("//div[@id='searchText']//input");
        SUGGESTIONS_DROPDOWN_LOCATOR = $x("//div[contains(@class, 'oo-ui-widget') and @role='listbox']");
        SEARCH_BUTTON_LOCATOR = $x("//button[@type='submit']");

        elementTimeoutSec = 15;
    }

    public void waitUntilFormOpened(String tabCaptionPart) {
        SelenideElement tabSlot = $x(
                "//h1[@id = 'firstHeading' and contains(.,'" + tabCaptionPart + "')]");
        tabSlot.shouldBe(visible, Duration.ofSeconds(elementTimeoutSec));
    }

    public SelenideElement setSearchFieldValue(String searchQuery) {
        SEARCH_FIELD_LOCATOR
                .shouldBe(Condition.and("Доступен для взаимодействия", visible, enabled), Duration.ofSeconds(elementTimeoutSec));
        SEARCH_FIELD_LOCATOR.setValue(searchQuery);

        Assertions.assertEquals(searchQuery, SEARCH_FIELD_LOCATOR.getValue());
        /*if(searchQuery.length()>0){
            SUGGESTIONS_DROPDOWN_LOCATOR
                    .shouldBe(Condition.and("Доступен для взаимодействия", visible, enabled), Duration.ofSeconds(elementTimeoutSec));
        }*/
        return SEARCH_FIELD_LOCATOR;
    }

    public SelenideElement setSearchFieldValueWithMarkCheck(String searchQuery) {
        return null;
    }

    public void selectValueFromCombobox(String value) {
        SelenideElement valueLocator = $x("//div[contains(@class, 'oo-ui-widget') and @role='listbox']//a[contains(@class, 'oo-ui-labelElement-label') and @title='" + value + "']");
        Assertions.assertEquals(value, valueLocator.getText());
        valueLocator.click();
    }

    public SelenideElement setSearchFieldValueUpper(String searchQuery) {
        this.SEARCH_FIELD_LOCATOR_UPPER
                .shouldBe(Condition.and("Доступен для взаимодействия", visible, enabled), Duration.ofSeconds(elementTimeoutSec));
        SEARCH_FIELD_LOCATOR_UPPER.setValue(searchQuery);

        Assertions.assertEquals(searchQuery, SEARCH_FIELD_LOCATOR_UPPER.getValue());
        if (searchQuery.length() > 0) {
            SUGGESTIONS_DROPDOWN_LOCATOR_UPPER
                    .shouldBe(Condition.and("Доступен для взаимодействия", visible, enabled), Duration.ofSeconds(elementTimeoutSec));
        }
        return SEARCH_FIELD_LOCATOR_UPPER;
    }

    public SelenideElement setSearchFieldValueWithMarkCheckUpper(String searchQuery) {
        setSearchFieldValueUpper(searchQuery);
        if (searchQuery.length() > 0) {
            Assertions.assertEquals(searchQuery, SUGGESTIONS_DROPDOWN_LOCATOR_UPPER.find(By.xpath(".//a[@title='" + searchQuery + "' and contains(@class,'mw-searchSuggest-link')]")).getText());
        }
        return SEARCH_FIELD_LOCATOR_UPPER;
    }

    public void selectValueFromComboboxUpper(String value) {
        SelenideElement valueLocator = SUGGESTIONS_DROPDOWN_LOCATOR_UPPER.find(By.xpath(".//a[@title='" + value + "' and contains(@class,'mw-searchSuggest-link')]//div"));
        Assertions.assertEquals(value, valueLocator.getText());
        valueLocator.click();

    }

    public void selectSpecialValueFromComboboxUpper(String value) {
        SelenideElement valueLocator = $x("//div[@class='suggestions-special']//div[@class='special-query']");
        Assertions.assertEquals(value, valueLocator.getText());
        valueLocator.click();

    }

    public SelenideElement pressSearchButtonUpper() {
        SEARCH_BUTTON_LOCATOR_UPPER.shouldBe(Condition.and("Доступен для взаимодействия", visible, enabled), Duration.ofSeconds(elementTimeoutSec));
        SEARCH_BUTTON_LOCATOR_UPPER.click();
        return SEARCH_BUTTON_LOCATOR_UPPER;
    }
}
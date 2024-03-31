package testframework.pagemodel;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;

/**
 * Класс для взаимодейтсвия
 * с элементами страницы с типом "Статья"
 */
public class ArticlePage extends BasePage {
    public ArticlePage(String title) throws Exception {

        waitUntilFormOpened(title);
        frameTitle = title;

        LOG = LogManager.getLogger(ArticlePage.class);

        SEARCH_FIELD_LOCATOR = $x("//div[@id='simpleSearch']//input");
        SUGGESTIONS_DROPDOWN_LOCATOR = $x("//div[@class='suggestions-results']");
        SEARCH_BUTTON_LOCATOR = $x("//input[@id='searchButton' and @type='submit']");
        elementTimeoutSec = 15;
    }

    public void waitUntilFormOpened(String tabCaptionPart) {
        SelenideElement tabSlot = $x(
                "//h1[@id = 'firstHeading']//span[@class='mw-page-title-main' and contains(.,'" + tabCaptionPart + "')]");
        tabSlot.shouldBe(visible, Duration.ofSeconds(elementTimeoutSec));
    }

    public SelenideElement setSearchFieldValue(String searchQuery) {
        SEARCH_FIELD_LOCATOR
                .shouldBe(Condition.and("Доступен для взаимодействия", visible, enabled), Duration.ofSeconds(elementTimeoutSec));
        SEARCH_FIELD_LOCATOR.setValue(searchQuery);

        Assertions.assertEquals(searchQuery, SEARCH_FIELD_LOCATOR.getValue());
        if (searchQuery.length() > 0) {
            SUGGESTIONS_DROPDOWN_LOCATOR
                    .shouldBe(Condition.and("Доступен для взаимодействия", visible, enabled), Duration.ofSeconds(elementTimeoutSec));
        }
        return SEARCH_FIELD_LOCATOR;
    }

    public SelenideElement setSearchFieldValueWithMarkCheck(String searchQuery) {
        setSearchFieldValue(searchQuery);
        if (searchQuery.length() > 0) {
            Assertions.assertEquals(searchQuery, SUGGESTIONS_DROPDOWN_LOCATOR.find(By.xpath(".//a[@title='" + searchQuery + "' and contains(@class,'mw-searchSuggest-link')]")).getText());
        }
        return SEARCH_FIELD_LOCATOR;
    }

    public void selectValueFromCombobox(String value) {
        SelenideElement valueLocator = SUGGESTIONS_DROPDOWN_LOCATOR.find(By.xpath(".//a[@title='" + value + "' and contains(@class,'mw-searchSuggest-link')]//div"));
        Assertions.assertEquals(value, valueLocator.getText());
        valueLocator.click();

    }

    public void selectSpecialValueFromCombobox(String value) {
        SelenideElement valueLocator = $x("//div[@class='suggestions-special']//div[@class='special-query']");
        Assertions.assertEquals(value, valueLocator.getText());
        valueLocator.click();

    }
}

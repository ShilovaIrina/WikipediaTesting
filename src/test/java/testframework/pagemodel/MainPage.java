package testframework.pagemodel;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

/**
 * Класс для взаимодейтсвия
 * с элементами страницы с типом "Главная страница"
 */
public class MainPage extends BasePage {

    public MainPage() throws Exception {

        waitUntilFormOpened("Wikipedia");
        frameTitle = "Wikipedia";

        LOG = LogManager.getLogger(MainPage.class);

        SEARCH_FIELD_LOCATOR = $x("//div[@id='search-input']//input");
        SUGGESTIONS_DROPDOWN_LOCATOR = $x("//div[@class='suggestions-dropdown']");
        SEARCH_BUTTON_LOCATOR = $x("//button[@type='submit']");
        elementTimeoutSec = 15;
    }

    public void waitUntilFormOpened(String tabCaptionPart) {
        SelenideElement tabSlot = $x(
                "//span[contains(@class,'central-textlogo__image') and contains(.,'" + tabCaptionPart + "')]");
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
            Assertions.assertEquals(searchQuery, SUGGESTIONS_DROPDOWN_LOCATOR.find(By.xpath(".//div[contains(@class,'suggestion-text')]//h3[contains(@class, 'suggestion-title')]")).getText());
        }
        return SEARCH_FIELD_LOCATOR;
    }

    public void selectValueFromCombobox(String value) {
        SelenideElement valueLocator = SUGGESTIONS_DROPDOWN_LOCATOR.find(By.xpath(".//div[@class='suggestion-text']//h3[@class='suggestion-title']"));
        Assertions.assertEquals(value, valueLocator.getText());
        valueLocator.click();

    }

}

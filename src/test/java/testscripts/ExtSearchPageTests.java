package testscripts;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import testframework.helpers.Browser;
import testframework.pagemodel.ArticlePage;
import testframework.pagemodel.ExtSearchPage;
import testframework.pagemodel.TestBase;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestBase.PageTypeAnno("extSearchPage")
public class ExtSearchPageTests extends TestBase {

    /**
     * Тест, проверяющий, что при вводе запроса в расширенный поиск
     * мы получаем страницу содержащую статьи "Результаты поиска" по слову из запроса
     */

    @Test
    @AllureId("")
    @Order(1)
    @DisplayName("Переход в статью с заголовком \"Программирование\" по кнопке")
    void goToProgrammingArticlePageButton() throws Exception {
        ExtSearchPage extSearchPage = new ExtSearchPage("Результаты поиска");
        extSearchPage.setSearchFieldValue("Программирование");
        Browser.sendKeys(Keys.SPACE);
        extSearchPage.pressSearchButton();
        extSearchPage = new ExtSearchPage("Результаты поиска");

    }

    /**
     * Тест, проверяющий, что при вводе запроса в расширенный поиск
     * мы получаем страницу "Результатами поиска" по запросу "Программирование",
     * а затем на вводит поле запрос "Sony" и выбирает статьи по первому слову
     * из запроса в комбобоксе
     */

    @Test
    @AllureId("")
    @Order(2)
    @DisplayName("Переход в поиск по запросу \"Sony\" по комбобоксу")
    void goToProgrammingArticlePageCombo() throws Exception {
        ExtSearchPage extSearchPage = new ExtSearchPage("Результаты поиска");
        extSearchPage.setSearchFieldValue("Программирование");
        extSearchPage.setSearchFieldValue("Sony");
        Browser.sendKeys(Keys.SPACE);
        extSearchPage.selectValueFromCombobox("Sony");
        extSearchPage = new ExtSearchPage("Результаты поиска");

    }

    /**
     * Тест, проверяющий, что при вводе запроса в расширенный поиск
     * мы получаем страницу соответствующую статьями по совпадению с нашим запросом и
     * нас перенаправляют с "Результатов поиска"
     */

    @Test
    @AllureId("")
    @Order(3)
    @DisplayName("Переход в статью с заголовком \"Программирование\" по кнопке")
    void goToProgrammingArticlePageButtonUpper() throws Exception {
        ExtSearchPage extSearchPage = new ExtSearchPage("Результаты поиска");
        extSearchPage.setSearchFieldValueUpper("Программирование");
        Browser.sendKeys(Keys.SPACE);
        extSearchPage.pressSearchButtonUpper();
        ArticlePage articlePage = new ArticlePage("Программирование");

    }

    /**
     * Тест, проверяющий, что при вводе запроса в расширенный поиск
     * мы получаем страницу соответствующую первому выплывающему в комбобоксе запросу
     */

    @Test
    @AllureId("")
    @Order(4)
    @DisplayName("Переход в статью с заголовком \"Sony\" по комбобоксу (верхний поиск)")
    void goToProgrammingArticlePageComboUpper() throws Exception {
        ArticlePage articlePage = new ArticlePage("Программирование");
        articlePage.setSearchFieldValue("Sony");
        Browser.sendKeys(Keys.SPACE);
        articlePage.selectSpecialValueFromCombobox("Sony");

        ExtSearchPage extSearchPage = new ExtSearchPage("Результаты поиска");
        extSearchPage.setSearchFieldValueUpper("Sony");
        extSearchPage.selectValueFromComboboxUpper("Sony");
        articlePage = new ArticlePage("Sony");

    }

    /**
     * Тест, проверяющий, что при выборе элемента "Поиск страниц содержащих" в комбобоксе поиска
     * мы получаем страницу соответствующую нашему запросу и
     * нас перенаправляют с "Sony" на расширенный поиск с "Результатами поиска"
     */

    @Test
    @AllureId("")
    @Order(5)
    @DisplayName("Выбор элемента \"Поиск страниц содержащих\" в комбобоксе поиска")
    void checkIncludingPages() throws Exception {
        ArticlePage articlePage = new ArticlePage("Sony");
        articlePage.setSearchFieldValue("Sony");
        Browser.sendKeys(Keys.SPACE);
        articlePage.selectSpecialValueFromCombobox("Sony");

        ExtSearchPage extSearchPage = new ExtSearchPage("Результаты поиска");
        extSearchPage.setSearchFieldValueUpper("Sony");
        extSearchPage.selectSpecialValueFromComboboxUpper("Sony");
        extSearchPage = new ExtSearchPage("Результаты поиска");
    }

    /**
     * Тест, проверяющий, что при отправке пустого запроса
     * нас перенаправит к странице расширенного поиска
     */

    @Test
    @AllureId("")
    @Order(6)
    @DisplayName("Пустой ввод")
    void emptyInput() throws Exception {
        ExtSearchPage extSearchPage = new ExtSearchPage("Результаты поиска");
        extSearchPage.pressSearchButton();
        extSearchPage = new ExtSearchPage("Результаты поиска");
        extSearchPage.pressSearchButtonUpper();
        extSearchPage = new ExtSearchPage("Поиск");
    }

    /**
     * Тест, проверяющий, что при вводе в строку поиска данных с ошибкой
     * нас перенаправляет на страницу расширенного поиска, внизу появляется надпись
     * "Показаны результаты для «парк». Для «пар4» результаты не найдены."
     */
    @Test
    @AllureId("")
    @Order(7)
    @DisplayName("Ввод некорректного значения")
    void incorrectInput() throws Exception {
        ExtSearchPage extSearchPage = new ExtSearchPage("Поиск");
        extSearchPage.setSearchFieldValue("Пар4");
        extSearchPage.pressSearchButton();
        extSearchPage = new ExtSearchPage("Результаты поиска");
    }
}

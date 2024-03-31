package testscripts;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import testframework.pagemodel.ArticlePage;
import testframework.pagemodel.ExtSearchPage;
import testframework.helpers.Browser;
import testframework.pagemodel.TestBase;

import static com.codeborne.selenide.Selenide.back;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestBase.PageTypeAnno("articlePage")
public class ArticlePageTests extends TestBase {

    /**
     * Тест, проверяющий, что при вводе в строку поиска
     * мы получаем страницу соответствующую нашему запросу и
     * нас перенаправляют с "Sony" на страницу запроса "Программирование"
     */
    @Test
    @AllureId("")
    @Order(1)
    @DisplayName("Переход в статью с заголовком \"Программирование\"")
    void goToProgrammingArticlePage() throws Exception {
        ArticlePage articlePage = new ArticlePage("Sony");
        articlePage.setSearchFieldValueWithMarkCheck("Программирование");
        articlePage.pressSearchButton();
        articlePage = new ArticlePage("Программирование");

    }

    /**
     * Тест, проверяющий, что при вводе в строку поиска появляется комбобокс
     * мы получаем страницу соответствующую нашему запросу и
     * нас перенаправляют с "Sony" на страницу запроса "Программирование"
     */

    @Test
    @AllureId("")
    @Order(2)
    @DisplayName("Переход в статью с заголовком \"Sony\" по комбобоксу")
    void goToProgrammingArticlePageCombo() throws Exception {
        ArticlePage articlePage = new ArticlePage("Программирование");
        articlePage.setSearchFieldValueWithMarkCheck("Sony");
        articlePage.selectValueFromCombobox("Sony");
        articlePage = new ArticlePage("Sony");

    }

    /**
     * Тест, проверяющий, что при выборе элемента "Поиск страниц содержащих" в комбобоксе поиска
     * мы получаем страницу соответствующую нашему запросу и
     * нас перенаправляют с "Sony" на расширенный поиск с "Результатами поиска"
     */

    @Test
    @AllureId("")
    @Order(3)
    @DisplayName("Выбор элемента \"Поиск страниц содержащих\" в комбобоксе поиска")
    void checkIncludingPages() throws Exception {
        ArticlePage articlePage = new ArticlePage("Sony");
        articlePage.setSearchFieldValueWithMarkCheck("Sony");
        Browser.sendKeys(Keys.SPACE);
        articlePage.selectSpecialValueFromCombobox("Sony");
        ExtSearchPage extSearchPage = new ExtSearchPage("Результаты поиска");
        back();
    }

    /**
     * Тест, проверяющий, что при отправке пустого запроса
     * нас перенаправит к странице расширенного поиска
     */
    @Test
    @AllureId("")
    @Order(4)
    @DisplayName("Пустой ввод")
    void emptyInput() throws Exception {
        ArticlePage articlePage = new ArticlePage("Sony");
        articlePage.pressSearchButton();
        ExtSearchPage extSearchPage = new ExtSearchPage("Поиск");
        back();
    }

    /**
     * Тест, проверяющий, что при вводе в строку поиска данных с ошибкой
     * нас перенаправляет со страницы "Программирование" на страницу расширенного поиска,
     * внизу появляется надпись "Показаны результаты для «парк». Для «Son4» результаты не найдены."
     */

    @Test
    @AllureId("")
    @Order(5)
    @DisplayName("Ввод некорректного значения")
    void incorrectInput() throws Exception {
        ArticlePage articlePage = new ArticlePage("Программирование");
        articlePage.setSearchFieldValue("Son4");
        articlePage.pressSearchButton();
        ExtSearchPage extSearchPage = new ExtSearchPage("Результаты поиска");
    }
}

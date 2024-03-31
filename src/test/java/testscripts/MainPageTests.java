package testscripts;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NotFoundException;
import testframework.pagemodel.ArticlePage;
import testframework.pagemodel.ExtSearchPage;
import testframework.pagemodel.MainPage;
import testframework.pagemodel.TestBase;

import static com.codeborne.selenide.Selenide.back;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestBase.PageTypeAnno("mainPage")
public class MainPageTests extends TestBase {

    /**
     * Тест, проверяющий, что при вводе в строку поиска
     * мы получаем страницу соответствующую нашему запросу
     */
    @Test
    @AllureId("")
    @Order(1)
    @DisplayName("Переход в статью с заголовком \"Программирование\"")
    void goToProgrammingArticlePage() throws Exception {
        MainPage mainPage = new MainPage();
        mainPage.setSearchFieldValueWithMarkCheck("Программирование");
        mainPage.pressSearchButton();
        ArticlePage articlePage = new ArticlePage("Программирование");
        back();

    }

    /**
     * Тест, проверяющий, что при вводе в строку поиска из комбобокса
     * мы получаем страницу соответствующую нашему запросу
     */
    @Test
    @AllureId("")
    @Order(2)
    @DisplayName("Переход в статью с заголовком \"Sony\" по комбобоксу")
    void goToProgrammingArticlePageCombo() throws Exception {
        MainPage mainPage = new MainPage();
        mainPage.setSearchFieldValue("Sony");
        mainPage.selectValueFromCombobox("Sony");
        ArticlePage articlePage = new ArticlePage("Sony");
        back();
    }

    /**
     * Тест, проверяющий, что при отправке пустого запроса
     * нас перенаправит к странице расширенного поиска
     */
    @Test
    @AllureId("")
    @Order(3)
    @DisplayName("Пустой ввод")
    void emptyInput() throws Exception {
        MainPage mainPage = new MainPage();
        mainPage.pressSearchButton();
        ExtSearchPage extSearchPage = new ExtSearchPage("Поиск");
        back();
    }

    /**
     * Тест, проверяющий, что при вводе в строку поиска данных с ошибкой
     * нас перенаправляет на страницу расширенного поиска, внизу появляется надпись
     * "Показаны результаты для «парк». Для «пар4» результаты не найдены."
     */
    @Test
    @AllureId("")
    @Order(4)
    @DisplayName("Ввод некорректного значения")
    void incorrectInput() throws Exception {
        MainPage mainPage = new MainPage();
        mainPage.setSearchFieldValue("Пар4");
        mainPage.pressSearchButton();
        ExtSearchPage extSearchPage = new ExtSearchPage("Результаты поиска");
        back();
    }

    /**
     * Тест, проверяющий, что при вводе в строку поиска появляется комбобокс с
     * "Поиск страниц содержащих"
     */
    @Test
    @AllureId("")
    @Order(5)
    @DisplayName("Выбор элемента \"Поиск страниц содержащих\" в комбобоксе поиска")
    void checkIncludingPages() throws Exception {
        throw new NotFoundException("Пропуск теста по причине отсутствия элемента \"Поиск страниц содержащих\" в комбобоксе поиска");
    }
}

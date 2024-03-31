Для осуществления автосестов к пользователя должен быть браузер Firefox.
- Для запуска одного теста ввести в консоль gradlew test --tests="*.ArticlePageTests.goToProgrammingArticlePage"
- Для нескольких тестов ввести в консоль gradlew test --tests="*.ArticlePageTests"
- Для запуска всех тестов ввести в консоль gradlew test --tests="testscripts.*"

Для получения отчета по всем автосестам ввести в консоль команду gradlew allureReport
Команада для открытия отчета gradlew allureServe
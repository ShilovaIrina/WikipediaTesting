package testframework.helpers;

@org.aeonbits.owner.Config.LoadPolicy(org.aeonbits.owner.Config.LoadType.MERGE)
@org.aeonbits.owner.Config.Sources({
        "system:properties",
        "classpath:testconfig.properties"
})
public interface Config extends org.aeonbits.owner.Config {

    @DefaultValue("https://www.wikipedia.org/")
    @Key("mainPageUrl")
    String mainPageUrl();

    @DefaultValue("https://ru.wikipedia.org/wiki/Хаб")
    @Key("articlePageUrl")
    String articlePageUrl();

    @DefaultValue("https://ru.wikipedia.org/w/index.php?search=Пара&title=Служебная:Поиск&profile=advanced&fulltext=1&ns0=1")
    @Key("extSearchPageUrl")
    String extSearchPageUrl();

    @DefaultValue("40")
    @Key("waitTimeoutSec")
    int waitTimeout();

    @DefaultValue("200")
    @Key("sleepIntervalMillis")
    int sleepInterval();

    @DefaultValue("true")
    @Key("headless")
    Boolean headles();

    @DefaultValue("")
    @Key("videoStorageUrl")
    String videoStorageUrl();

    @DefaultValue("1600")
    @Key("windowWidth")
    int windowWidth();

    @DefaultValue("900")
    @Key("windowHeight")
    int windowHeight();

    @DefaultValue("chrome")
    @Key("webdriver.browserName")
    String browserName();

    @DefaultValue("false")
    @Key("webdriver.remote")
    boolean remote();

    @DefaultValue("false")
    @Key("webdriver.video")
    boolean isVideo();

    @Key("webdriver.remote.url")
    @DefaultValue("http://")
    String remoteUrl();
}

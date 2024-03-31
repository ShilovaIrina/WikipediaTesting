package testframework.helpers;

import org.aeonbits.owner.ConfigFactory;

public class ConfigHelper {

    public static String getMainPageUrl() {
        return getWebConfig().mainPageUrl();
    }

    public static String getArticlePageUrl() {
        return getWebConfig().articlePageUrl();
    }

    public static String getExtSearchPageUrl() {
        return getWebConfig().extSearchPageUrl();
    }

    public static Integer getWaitTimeoutSec() {
        return getWebConfig().waitTimeout();
    }

    public static String getBrowserName() {
        return getWebConfig().browserName();
    }

    public static Integer getSleepIntervalMillis() {
        return getWebConfig().sleepInterval();
    }

    public static Boolean isHeadless() {
        return getWebConfig().headles();
    }

    public static Boolean isRemote() {
        return getWebConfig().remote();
    }

    public static Boolean isVideo() {
        return getWebConfig().isVideo();
    }

    public static String getRemoteUrl() {
        return getWebConfig().remoteUrl();
    }

    public static String getVideoStorageUrl() {
        return getWebConfig().videoStorageUrl();
    }

    public static int getWindowWidth() {
        return getWebConfig().windowWidth();
    }

    public static int getWindowHeight() {
        return getWebConfig().windowHeight();
    }

    private static Config getWebConfig() {
        return ConfigFactory.newInstance().create(Config.class);
    }
}
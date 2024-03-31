package testframework.helpers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import static io.qameta.allure.Allure.step;

import testframework.pagemodel.TestBase;


public class Runner implements TestExecutionExceptionHandler {

    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {

        step("Attachments", () -> {
            AttachmentsHelper.addScreenshotAs("Last screenshot");
            AttachmentsHelper.attachPageSource();
            //addBrowserConsoleLogs();
        });

        TestBase.quitTest();

        throw throwable;

    }


}


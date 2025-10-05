package ui.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.ConfigProvider;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


public class BaseWebTest {

    @BeforeEach
    public void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));

        Configuration.browser = ConfigProvider.getWebConfig().browserName();
        Configuration.headless = ConfigProvider.getWebConfig().isHeadless();
        Configuration.baseUrl = ConfigProvider.getWebConfig().baseUrl();
        Configuration.timeout = ConfigProvider.getWebConfig().defaultTimeout();
        Configuration.pageLoadTimeout = ConfigProvider.getWebConfig().pageLoadTimeout();
        Configuration.browserSize = ConfigProvider.getWebConfig().browserWidth() + "x" + 
                                     ConfigProvider.getWebConfig().browserHeight();

        if (ConfigProvider.getWebConfig().remoteUrl() != null && 
            !ConfigProvider.getWebConfig().remoteUrl().isEmpty()) {
            Configuration.remote = ConfigProvider.getWebConfig().remoteUrl();
        }
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
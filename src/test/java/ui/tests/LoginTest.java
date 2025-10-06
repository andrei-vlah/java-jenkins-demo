package ui.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.MainPage;

import static com.codeborne.selenide.Condition.text;

@Epic("UI Tests")
@Feature("Authentication")
@Tag("smoke")
@Tag("ui")
public class LoginTest extends BaseWebTest {

    private final LoginPage loginPage = new LoginPage();
    private final MainPage mainPage = new MainPage();

    @Test
    @Story("Login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify successful user login")
    public void successfulLoginTest() {
        loginPage.openPage()
                .login("standard_user", "secret_sauce");

        mainPage.verifyPageIsDisplayed();
    }

    @Test
    @Story("Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify locked out user error message")
    public void lockedOutUserLoginTest() {
        loginPage.openPage()
                .login("locked_out_user", "secret_sauce");

        loginPage.getErrorMessage()
                .shouldHave(text("Epic sadface: Sorry, this user has been locked out."));
    }
}
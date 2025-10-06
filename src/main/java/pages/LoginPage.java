package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage<LoginPage> {

    private final SelenideElement usernameInput = $("#user-name");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement loginButton = $("#login-button");
    private final SelenideElement errorMessage = $("[data-test='error']");

    @Step("Open Login page")
    public LoginPage openPage() {
        return openPage("/");
    }

    @Step("Enter username: {username}")
    public LoginPage enterUsername(String username) {
        setValue(usernameInput, username);
        return this;
    }

    @Step("Enter password")
    public LoginPage enterPassword(String password) {
        setValue(passwordInput, password);
        return this;
    }

    @Step("Click Login button")
    public void clickLoginButton() {
        click(loginButton);
    }

    @Step("Perform login with username: {username}")
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    @Step("Verify Error message is displayed")
    public SelenideElement getErrorMessage() {
        return shouldBeVisible(errorMessage);
    }
}
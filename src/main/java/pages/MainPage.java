package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class MainPage extends BasePage<MainPage> {

    private final SelenideElement header = $(".app_logo");
    private final SelenideElement logoutButton = $("#logout");

    @Step("Verify Main page is displayed")
    public MainPage verifyPageIsDisplayed() {
        shouldBeVisible(header);
        return this;
    }

    @Step("Click Logout button")
    public void logout() {
        click(logoutButton);
    }
}
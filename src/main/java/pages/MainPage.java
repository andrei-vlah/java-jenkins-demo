package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    private final SelenideElement header = $(".app_logo");
    private final SelenideElement logoutButton = $("#logout");

    @Step("Verify Main page is displayed")
    public MainPage verifyPageIsDisplayed() {
        header.shouldBe(visible);
        return this;
    }

    @Step("Click Logout button")
    public void logout() {
        logoutButton.shouldBe(visible).click();
    }
}
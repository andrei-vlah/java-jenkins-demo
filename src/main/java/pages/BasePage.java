package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;

public abstract class BasePage<T> {

    @Step("Open page: {url}")
    public T openPage(String url) {
        open(url);
        return (T) this;
    }

    @Step("Verify element: {element} is visible")
    protected SelenideElement shouldBeVisible(SelenideElement element) {
        return element.shouldBe(visible);
    }

    @Step("Click on element: {element}")
    protected void click(SelenideElement element) {
        element.shouldBe(visible).click();
    }

    @Step("Enter text: {value} into element: {element}")
    protected void setValue(SelenideElement element, String value) {
        element.shouldBe(visible).setValue(value);
    }
}
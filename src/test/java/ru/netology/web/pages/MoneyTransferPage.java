package ru.netology.web.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;
import ru.netology.web.pages.DashboardPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MoneyTransferPage {
    private SelenideElement amount = $("[data-test-id=amount] input");
    private SelenideElement transferFrom = $("[data-test-id=from] input");
    private SelenideElement transferConfirmation = $("[data-test-id=action-transfer]");
    private SelenideElement transferPageHeader = $("[data-test-id=dashboard]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public MoneyTransferPage() {
        transferPageHeader.shouldBe(visible);
    }

    public DashboardPage moneyTransfer(String depositAmount, DataHelper.CardInfo cardInfo) {
        makeDeposit(depositAmount, cardInfo);
        return new DashboardPage();
    }

    public void makeDeposit(String depositAmount, DataHelper.CardInfo cardInfo) {
        amount.setValue(depositAmount);
        transferFrom.setValue(cardInfo.getCard());
        transferConfirmation.click();
    }

    public void errorNotification(String expectedText) {
        errorNotification.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Ошибка! Произошла ошибка"));
    }
}

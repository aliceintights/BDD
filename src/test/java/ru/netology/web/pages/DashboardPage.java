package ru.netology.web.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {

    private SelenideElement header = $("[data-test-id=dashboard]");
    private SelenideElement depositButton = $("[data-test-id=action-deposit]");

    private final SelenideElement realoadButton = $("[data-test-id=action-reload]");
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        header.shouldBe(visible);
    }

    public MoneyTransferPage makeDeposit(){
        depositButton.click();
        return new MoneyTransferPage();
    }

    public int getCardBalance(String maskedCardNumber) {
        var text = cards.findBy(text(maskedCardNumber)).getText();
        return extactBalance(text);
    }

    public int getCardBalance(int index) {
        var text = cards.get(index).getText();
        return extactBalance(text);
    }

    public MoneyTransferPage selectCardForMoneyDeposit(DataHelper.CardInfo cardInfo) {
        cards.findBy(attribute("data-test-id", cardInfo.getCardID())).$("button").click();
        return new MoneyTransferPage();
    }

    public void reloadDashboardPage() {
        realoadButton.click();
        header.shouldBe(visible);
    }

    private int extactBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}

package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.pages.DashboardPage;
import ru.netology.web.pages.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

class DashboardPageTest {

    DashboardPage dashboardPage;
    DataHelper.CardInfo firstCardInfo;
    DataHelper.CardInfo secondCardInfo;
    int firstCardBalance;
    int secondCardBalance;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
        var loginPage = new LoginPage();
        var loginInfo = DataHelper.getLoginInfo();
        var verificationPage = loginPage.validLogin(loginInfo);
        var verificationCode = DataHelper.getVerificationCode(loginInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        firstCardInfo = DataHelper.getFirstCardInfo();
        secondCardInfo = DataHelper.getSecondCardInfo();
        firstCardBalance = dashboardPage.getCardBalance(getMaskedNumber(firstCardInfo.getCard()));
        secondCardBalance = dashboardPage.getCardBalance(1);
    }

    @Test
    void shouldTestMoneyTransferFromOneCardToAnother() {

        var amount = generateValidAmount(firstCardBalance);
        var expectedBalanceFirstCard = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var moneyTransferPage = dashboardPage.selectCardForMoneyDeposit(secondCardInfo);
        dashboardPage = moneyTransferPage.moneyTransfer(String.valueOf(amount), firstCardInfo);
        dashboardPage.reloadDashboardPage();
        var actualFirstCardBalance = dashboardPage.getCardBalance(getMaskedNumber(firstCardInfo.getCard()));
        var actualSecondCardBalance = dashboardPage.getCardBalance(1);
        assertAll(() -> assertEquals(expectedBalanceFirstCard, actualFirstCardBalance),
                () -> assertEquals(expectedBalanceSecondCard, actualSecondCardBalance));
    }

    @Test
    void shouldGetErrorNotificationIfAmountIsMoreThanBalance() {

        var amount = generateInvalidAmount(secondCardBalance);
        var moneyTransferPage = dashboardPage.selectCardForMoneyDeposit(firstCardInfo);
        moneyTransferPage.makeDeposit(String.valueOf(amount), secondCardInfo);
        moneyTransferPage.errorNotification("Ошибка! Произошла ошибка");
        dashboardPage.reloadDashboardPage();
        var actualFirstCardBalance = dashboardPage.getCardBalance(getMaskedNumber(firstCardInfo.getCard()));
        var actualSecondCardBalance = dashboardPage.getCardBalance(getMaskedNumber(secondCardInfo.getCard()));
        assertAll(() -> assertEquals(firstCardBalance, actualFirstCardBalance),
                () -> assertEquals(secondCardBalance, actualSecondCardBalance));
    }
}
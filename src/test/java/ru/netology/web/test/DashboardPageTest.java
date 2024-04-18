package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.pages.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DashboardPageTest {
    @Test
    void shouldTestMoneyTransferFromOneCardToAnother() {
        open("http://localhost:9999/");

        var loginPage = new LoginPage();
        var loginInfo = DataHelper.getLoginInfo();
        var verificationPage = loginPage.validLogin(loginInfo);
        var verificationCode = DataHelper.getVerificationCode(loginInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCardInfo();
        var secondCardInfo = DataHelper.getSecondCardInfo();
//        var depositAmount = DataHelper.generateValidAmount(firstCardInfo);
//        var depositAmount = DataHelper.getFirstCardInfo();
        var moneyTransferPage = dashboardPage.selectCardForMoneyDeposit(firstCardInfo);
        var makeDepositPage = moneyTransferPage.moneyTransfer("5000", secondCardInfo);
    }

    @Test
    void shouldTestMoneyTransferIfSomeFieldsAreEmpty() {
        open("http://localhost:9999/");

        var loginPage = new LoginPage();
        var loginInfo = DataHelper.getLoginInfo();
        var verificationPage = loginPage.validLogin(loginInfo);
        var verificationCode = DataHelper.getVerificationCode(loginInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCardInfo();
        var secondCardInfo = DataHelper.getSecondCardInfo();
        var moneyTransferPage = dashboardPage.selectCardForMoneyDeposit(firstCardInfo);
        var makeDepositPage = moneyTransferPage.errorNotification();
    }

    @Test
    void shouldCheckCardBalance() {
        open("http://localhost:9999/");

        var loginPage = new LoginPage();
        var loginInfo = DataHelper.getLoginInfo();
        var verificationPage = loginPage.validLogin(loginInfo);
        var verificationCode = DataHelper.getVerificationCode(loginInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCardInfo();
        var secondCardInfo = DataHelper.getSecondCardInfo();
        assertEquals(dashboardPage.getCardBalance(0), dashboardPage.getCardBalance(1));
    }

    @Test
    void shouldCheckCardBalanceAfter5000Deposit() {
        open("http://localhost:9999/");

        var loginPage = new LoginPage();
        var loginInfo = DataHelper.getLoginInfo();
        var verificationPage = loginPage.validLogin(loginInfo);
        var verificationCode = DataHelper.getVerificationCode(loginInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCardInfo();
        var secondCardInfo = DataHelper.getSecondCardInfo();
        var moneyTransferPage = dashboardPage.selectCardForMoneyDeposit(firstCardInfo);
        var makeDepositPage = moneyTransferPage.moneyTransfer("5000", secondCardInfo);
        assertEquals(dashboardPage.getCardBalance(0), 15000);
    }
}
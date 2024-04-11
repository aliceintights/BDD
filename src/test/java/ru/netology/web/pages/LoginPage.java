package ru.netology.web.pages;

import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    public VerificationPage validLogin(DataHelper.LoginInfo info) {
        $("[data-test-id='login'] input").setValue(info.getLogin());
        $("[data-test-id='password'] input").setValue(info.getPassword());
        $("[data-test-id='action-login']").click();
        return new VerificationPage();
    }
}
package ru.netology.web.data;

import lombok.Value;

import static com.codeborne.selenide.Selenide.$;

public class DataHelper {
    @Value
    public static class LoginInfo {
        private String login;
        private String password;
    }

    public static LoginInfo getLoginInfo() {
        return new LoginInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode(LoginInfo loginInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo{
        private String card;
        private String cardID;
    }

    public static CardInfo getFirstCardInfo() {
        return new CardInfo("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public static CardInfo getSecondCardInfo() {
        return new CardInfo("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }
}
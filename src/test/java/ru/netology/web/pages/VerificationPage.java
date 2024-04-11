package ru.netology.web.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;
import ru.netology.web.pages.DashboardPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id='code'] input");
    private SelenideElement verufyButton = $("[data-test-id='action-verify']");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verufyButton.click();
        return new DashboardPage();
    }
}

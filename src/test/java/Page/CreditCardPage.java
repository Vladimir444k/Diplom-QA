package Page;

import Data.DataHelper;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditCardPage {
    private final SelenideElement cardNumb = $(".input [placeholder='0000 0000 0000 0000']");
    private final SelenideElement month = $(".input [placeholder='08']");
    private final SelenideElement year = $(".input [placeholder='22']");
    private final SelenideElement fieldCardHold = $$(".input__top").find(text("Владелец")).parent();
    private final SelenideElement nameCardHold = fieldCardHold.$(".input__control");
    private final SelenideElement cvc = $(".input [placeholder='999']");
    private final SelenideElement proceedButton = $(".form-field button");
    private final SelenideElement paymentApproveNot = $(".notification_status_ok");
    private final SelenideElement paymentDeclineNot = $(".notification_status_error");
    private final SelenideElement incorrectFormat = $(byText("Неверный формат"));
    private final SelenideElement emptyField = $(byText("Поле обязательно для заполнения"));
    private final SelenideElement invalidExpiredCard = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement expiredCard = $(byText("Истёк срок действия карты"));
    private final ElementsCollection resultLinks = $$(".input__top");

    public void creditCardFullInf(DataHelper.CardInfo cardInfo) {
        cardNumb.setValue(cardInfo.getNumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        nameCardHold.setValue(cardInfo.getHolder());
        cvc.setValue(cardInfo.getCvc());
        proceedButton.click();
    }

    public void valueFieldNumbCard() {
        var fieldNumbCard = resultLinks.find(text("Номер карты")).parent();
        fieldNumbCard.shouldHave(text("Неверный формат"));
    }

    public void valueFieldMonth() {
        var fieldNumbCard = resultLinks.find(text("Месяц")).parent();
        fieldNumbCard.shouldHave(text("Неверный формат"));
    }

    public void valueFieldYear() {
        var fieldNumbCard = resultLinks.find(text("Год")).parent();
        fieldNumbCard.shouldHave(text("Неверный формат"));
    }

    public void valueFieldCodeCvc() {
        var fieldNumbCard = resultLinks.find(text("CVC/CVV")).parent();
        fieldNumbCard.shouldHave(text("Неверный формат"));
    }

    public void valueFieldHolder() {
        var fieldNumbCard = resultLinks.find(text("Владелец")).parent();
        fieldNumbCard.shouldHave(text("Поле обязательно для заполнения"));
    }

    public void valueFieldHolderTwo() {
        var fieldNumbCard = resultLinks.find(text("Владелец")).parent();
        fieldNumbCard.shouldHave(text("Неверный формат"));
    }

    public void improperFormatPassNot() {
        incorrectFormat.shouldBe(visible);
    }

    public void emptyFieldNot() {
        emptyField.shouldBe(visible);
    }

    public void invalidExpiredDateNot() {
        invalidExpiredCard.shouldBe(visible);
    }

    public void expiredDatePassNot() {
        expiredCard.shouldBe(visible);
    }

    public void paymentApprove() {
        paymentApproveNot.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void paymentDecline() {
        paymentDeclineNot.shouldBe(visible, Duration.ofSeconds(15));
    }
}

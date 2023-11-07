package Page;

import Data.DataHelper;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DebitCardPage {
    private final SelenideElement numberCardField = $(".input [placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthCard = $("input[placeholder='08']");
    private final SelenideElement yearCard = $("input[placeholder='22']");
    private final SelenideElement fieldCardHolder = $$("input_top").find(text("Владелец")).parent();
    private final SelenideElement nameHolderCard = fieldCardHolder.$("input_control");
    private final SelenideElement codeCVC = $("input[placeholder='999']");
    private final SelenideElement buttonCont = $$("button").find(exactText("Продолжить"));
    private final ElementsCollection resultLinks = $$(".input_top");
    private final SelenideElement incorrectFormat = $(byText("Неверный формат"));
    private final SelenideElement emptyField = $(byText("Поле обязательно для заполнения"));
    private final SelenideElement invalidExpiredDate = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement expiredCard = $(byText("Истек срок действия карты"));
    private final SelenideElement paymentApproveNotification = $(".notification_status_ok");
    private final SelenideElement paymentDeclineNotification = $(".notification_status_error");

    public void fillCardInfForSelectedWay(DataHelper.CardInfo cardInfo) {
        numberCardField.setValue(cardInfo.getNumber());
        monthCard.setValue(cardInfo.getMonth());
        yearCard.setValue(cardInfo.getYear());
        nameHolderCard.setValue(cardInfo.getHolder());
        codeCVC.setValue(cardInfo.getCvc());
        buttonCont.click();
    }

    public void improperFormatNot() {
        incorrectFormat.shouldBe(visible);
    }

    public void emptyFieldNot() {
        emptyField.shouldBe(visible);
    }

    public void invalidExpiredDateNot() {
        invalidExpiredDate.shouldBe(visible);
    }

    public void expiredDatePassNot() {
        expiredCard.shouldBe(visible);
    }

    public void paymentApproved() {
        paymentApproveNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void paymentDeclined() {
        paymentDeclineNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void valueNumCard() {
        var fieldNumCard = resultLinks.find(text("Номер карты")).parent();
        fieldNumCard.shouldHave(text("Неверный формат"));
    }

    public void valueFieldMonth() {
        var fieldNumCard = resultLinks.find(text("Месяц")).parent();
        fieldNumCard.shouldHave(text("Неверный формат"));
    }

    public void valueFieldYear() {
        var fieldNumCard = resultLinks.find(text("Год")).parent();
        fieldNumCard.shouldHave(text("Неверный формат"));
    }

    public void valueFieldCodeCvc() {
        var fieldNumCard = resultLinks.find(text("CVC/CVV")).parent();
        fieldNumCard.shouldHave(text("Неверный формат"));
    }

    public void valueFieldHolder() {
        var fieldNumCard = resultLinks.find(text("Владелец")).parent();
        fieldNumCard.shouldHave(text("Поле обязательно для заполнения"));
    }

    public void valueFieldHolderTwo() {
        var fieldNumCard = resultLinks.find(text("Владелец")).parent();
        fieldNumCard.shouldHave(text("Неверный формат"));
    }
}

package Page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private final SelenideElement buttonBuyDebitCard = $$(".button_text").find(exactText("Купить"));
    private final SelenideElement buttonBuyCreditCard = $$("button_text").find(exactText("Купить в кредит"));

    public PaymentPage() {
        SelenideElement paymentBySelectedWayHeader = $$(".heading").findBy(exactText("Путешествие дня"));
        paymentBySelectedWayHeader.shouldBe(visible);
    }

    public DebitCardPage selectBuyDebitCard() {
        buttonBuyDebitCard.click();
        return new DebitCardPage();
    }

    public CreditCardPage selectBuyCreditCard() {
        buttonBuyCreditCard.click();
        return new CreditCardPage();
    }
}

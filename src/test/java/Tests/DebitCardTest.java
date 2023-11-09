package Tests;

import Data.DBHelper;
import Data.DataHelper;
import Page.PaymentPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitCardTest {
    @BeforeEach
    void openPage() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080");
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Nested
    class testPurchaseByCardWithDifferentStatus {
        @Test
        void testPurchaseWithApproveDebitCard() {
            var validCardInf = DataHelper.getApproveCard();
            var paymentPage = new PaymentPage().selectBuyDebitCard();
            paymentPage.fillCardInfForSelectedWay(validCardInf);
            paymentPage.paymentApproved();
            assertEquals("APPROVED", DBHelper.getPaymentStatusDebitCard());
        }

        @Test
        void testPurchaseWithDeclineDebitCard() {
            var invalidCardInf = DataHelper.getDeclineCard();
            var paymentPage = new PaymentPage().selectBuyDebitCard();
            paymentPage.fillCardInfForSelectedWay(invalidCardInf);
            paymentPage.paymentDeclined();
            assertEquals("DECLINED", DBHelper.getPaymentStatusDebitCard());
        }
    }

    @Nested
    class testFieldNumbTests {
        @Test
        void testGetNotificationEmptyFields() {
            var incorrectCardInfo = DataHelper.getCardWithEmptyFields();
            var paymentPage = new PaymentPage().selectBuyDebitCard();
            paymentPage.fillCardInfForSelectedWay(incorrectCardInfo);
            paymentPage.emptyFieldNot();
            paymentPage.improperFormatNot();
            paymentPage.valueFieldCodeCvc();
            paymentPage.valueFieldYear();
            paymentPage.valueFieldMonth();
            paymentPage.valueNumCard();
            paymentPage.valueFieldHolder();
        }

        @Test
        public void testShortCardNumb() {
            var incorrectCardNumb = DataHelper.getCardWithShortNum();
            var paymentPage = new PaymentPage().selectBuyDebitCard();
            paymentPage.fillCardInfForSelectedWay(incorrectCardNumb);
            paymentPage.valueNumCard();
        }

        @Test
        public void testCardFromZero() {
            var incorrectCardNumb = DataHelper.getCardNumFromZero();
            var paymentPage = new PaymentPage().selectBuyDebitCard();
            paymentPage.fillCardInfForSelectedWay(incorrectCardNumb);
            paymentPage.paymentDeclined();
        }
    }

    @Nested
    class testFieldCardMonth {
        @Test
        public void testNumbMonthMore12() {
            var incorrectCardMonth = DataHelper.getMonthIfMore12();
            var paymentPage = new PaymentPage().selectBuyDebitCard();
            paymentPage.fillCardInfForSelectedWay(incorrectCardMonth);
            paymentPage.invalidExpiredDateNot();
        }
        @Test
        public void testNumberOfMonthOneDigit() {
            var incorrectCardMonth = DataHelper.getMonthWhenOneDigit();
            var paymentPage = new PaymentPage().selectBuyDebitCard();
            paymentPage.fillCardInfForSelectedWay(incorrectCardMonth);
            paymentPage.valueFieldMonth();
        }
        @Test
        public void testNumbMonthFromZero() {
            var incorrectCardMonth = DataHelper.getMonthFromZero();
            var paymentPage = new PaymentPage().selectBuyDebitCard();
            paymentPage.fillCardInfForSelectedWay(incorrectCardMonth);
            paymentPage.paymentDeclined();
            paymentPage.valueFieldMonth();
        }
    }

    @Nested
    class testFieldsCardYear {
        @Test
        public void testInvalidYearIfZero() {
            var incorrectCardYear = DataHelper.getYearFromZero();
            var paymentPage = new PaymentPage().selectBuyDebitCard();
            paymentPage.fillCardInfForSelectedWay(incorrectCardYear);
            paymentPage.expiredDatePassNot();
        }
        @Test
        public void testFutureYear() {
            var incorrectCardYear = DataHelper.getFutureYear();
            var paymentPage = new PaymentPage().selectBuyDebitCard();
            paymentPage.fillCardInfForSelectedWay(incorrectCardYear);
            paymentPage.paymentApproved();
        }
        @Test
        public void testNumbOfYearOneDigit() {
            var incorrectCardYear = DataHelper.getYearFromOneDigit();
            var paymentPage = new PaymentPage().selectBuyDebitCard();
            paymentPage.fillCardInfForSelectedWay(incorrectCardYear);
            paymentPage.improperFormatNot();
        }
        @Test
        public void testPastYear() {
            var incorrectCardInf = DataHelper.getPastYear();
            var paymentPage = new PaymentPage().selectBuyDebitCard();
            paymentPage.fillCardInfForSelectedWay(incorrectCardInf);
            paymentPage.expiredDatePassNot();
        }
    }

    @Nested
    class testFieldsCardOwner {
        @Test
        public void testCardHolderNameIsSpecSymbols() {
            var incorrectCardHold = DataHelper.getInvalidCardHolder();
            var paymentPage = new PaymentPage().selectBuyDebitCard();
            paymentPage.fillCardInfForSelectedWay(incorrectCardHold);
            paymentPage.paymentDeclined();
            paymentPage.valueFieldHolderTwo();
        }
        @Test
        public void testCardHolderNameIfRussian() {
            var incorrectCardHold = DataHelper.getCardRussianHolderName();
            var paymentPage = new PaymentPage().selectBuyDebitCard();
            paymentPage.fillCardInfForSelectedWay(incorrectCardHold);
            paymentPage.paymentDeclined();
            paymentPage.valueFieldHolderTwo();
        }
        @Test
        public void testDoubleNameOfHolder() {
            var correctCardHold = DataHelper.getDoubleNameHold();
            var paymentPage = new PaymentPage().selectBuyDebitCard();
            paymentPage.fillCardInfForSelectedWay(correctCardHold);
            paymentPage.paymentApproved();
        }
    }

    @Nested
    class testFieldsCardCVC {
        @Test
        public void testCvcFromOneDigit() {
            var incorrectCardCvc = DataHelper.getCvcFromOneDigit();
            var paymentPage = new PaymentPage().selectBuyDebitCard();
            paymentPage.fillCardInfForSelectedWay(incorrectCardCvc);
            paymentPage.valueFieldCodeCvc();
        }
        @Test
        public void testCvcFromTwoDigit() {
            var incorrectCardCvc = DataHelper.getCvcFromTwoDigit();
            var paymentPage = new PaymentPage().selectBuyDebitCard();
            paymentPage.fillCardInfForSelectedWay(incorrectCardCvc);
            paymentPage.valueFieldCodeCvc();
        }
        @Test
        public void testCvcFromThreeZero() {
            var cardCvc = DataHelper.getCvcFromThreeZero();
            var paymentPage = new PaymentPage().selectBuyDebitCard();
            paymentPage.fillCardInfForSelectedWay(cardCvc);
            paymentPage.paymentApproved();
        }
    }
}

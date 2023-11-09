package Tests;

import Data.DBHelper;
import Data.DataHelper;
import Page.PaymentPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.checkerframework.checker.units.qual.N;
import org.junit.jupiter.api.*;

import javax.xml.crypto.Data;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

public class CreditCardTest {
    @BeforeEach
    void openPage() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080");
    }

    @BeforeAll
    static void setupAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
    @Nested
    class testPurchaseByCardWithDiffStatus {
        @Test
        void testPurchaseWithApproveCreditCard() throws SQLException {
            var validCardInf = DataHelper.getApproveCard();
            var travelPage = new PaymentPage();
            var creditCardPage = travelPage.selectBuyCreditCard();
            creditCardPage.creditCardFullInf(validCardInf);
            creditCardPage.paymentApprove();
            assertEquals("APPROVE", DBHelper.getPaymentStatusCreditCard());
        }
        @Test
        void testPurchaseWithDeclineCreditCard() throws SQLException {
            var invalidCardInf = DataHelper.getDeclineCard();
            var travelPage = new PaymentPage();
            var creditCardPage = travelPage.selectBuyCreditCard();
            creditCardPage.creditCardFullInf(invalidCardInf);
            creditCardPage.paymentDecline();
            assertEquals("DECLINED", DBHelper.getPaymentStatusCreditCard());
        }
    }

    @Nested
    class testFieldsCardNumb {
        @Test
        void testNotificationEmptyFields() {
            var invalidCardInf = DataHelper.getCardWithEmptyFields();
            var travelPage = new PaymentPage();
            var creditCardPage = travelPage.selectBuyCreditCard();
            creditCardPage.creditCardFullInf(invalidCardInf);
            creditCardPage.emptyFieldNot();
            creditCardPage.improperFormatPassNot();
            creditCardPage.valueFieldCodeCvc();
            creditCardPage.valueFieldHolder();
            creditCardPage.valueFieldMonth();
            creditCardPage.valueFieldYear();
            creditCardPage.valueFieldNumbCard();
        }
        @Test
        public void testFieldNumbCardShortNumber() {
            var invalidCardNumb = DataHelper.getCardWithShortNum();
            var travelPage = new PaymentPage();
            var creditCardPage = travelPage.selectBuyCreditCard();
            creditCardPage.creditCardFullInf(invalidCardNumb);
            creditCardPage.valueFieldNumbCard();
        }
        @Test
        public void testNumbCardFromZero() {
            var invalidCardNumb = DataHelper.getCardNumFromZero();
            var travelPage = new PaymentPage();
            var creditCardPage = travelPage.selectBuyCreditCard();
            creditCardPage.creditCardFullInf(invalidCardNumb);
        }
    }

    @Nested
    class testFieldMonth {
        @Test
        public void testNumbOfMonthMore12() {
            var invalidCardMonth = DataHelper.getMonthIfMore12();
            var travelPage = new PaymentPage();
            var creditCardPage = travelPage.selectBuyCreditCard();
            creditCardPage.creditCardFullInf(invalidCardMonth);
            creditCardPage.invalidExpiredDateNot();
        }
        @Test
        public void testNumbOfMonthFromOneDigit() {
            var invalidCardMonth = DataHelper.getMonthWhenOneDigit();
            var travelPage = new PaymentPage();
            var creditCardPage = travelPage.selectBuyCreditCard();
            creditCardPage.creditCardFullInf(invalidCardMonth);
            creditCardPage.valueFieldMonth();
        }
        @Test
        public void testNumbOfMonthFromZero() {
            var invalidCardMonth = DataHelper.getMonthFromZero();
            var travelPage = new PaymentPage();
            var creditCardPage = travelPage.selectBuyCreditCard();
            creditCardPage.creditCardFullInf(invalidCardMonth);
        }
    }

    @Nested
    class testFieldYear {
        @Test
        public void testYearFromZero() {
            var invalidCardYear = DataHelper.getYearFromZero();
            var travelPage = new PaymentPage();
            var creditCardPage = travelPage.selectBuyCreditCard();
            creditCardPage.creditCardFullInf(invalidCardYear);
            creditCardPage.expiredDatePassNot();
        }
        @Test
        public void testFutureYear() {
            var invalidCardYear = DataHelper.getFutureYear();
            var travelPage = new PaymentPage();
            var creditCardPage = travelPage.selectBuyCreditCard();
            creditCardPage.creditCardFullInf(invalidCardYear);
            creditCardPage.paymentApprove();
        }
        @Test
        public void testNumbOfYearFromOneDigit() {
            var invalidCardYear = DataHelper.getYearFromOneDigit();
            var travelPage = new PaymentPage();
            var creditCardPage = travelPage.selectBuyCreditCard();
            creditCardPage.creditCardFullInf(invalidCardYear);
            creditCardPage.improperFormatPassNot();
        }
        @Test
        public void testPastYear() {
            var invalidCardYear = DataHelper.getPastYear();
            var travelPage = new PaymentPage();
            var creditCardPage = travelPage.selectBuyCreditCard();
            creditCardPage.creditCardFullInf(invalidCardYear);
            creditCardPage.expiredDatePassNot();
        }
    }

    @Nested
    class testFieldCardOwner {
        @Test
        public void testFieldHolderFromSpecSymbols() {
            var invalidCardHolder = DataHelper.getInvalidCardHolder();
            var travelPage = new PaymentPage();
            var creditCardPage = travelPage.selectBuyCreditCard();
            creditCardPage.creditCardFullInf(invalidCardHolder);
            creditCardPage.valueFieldHolderTwo();
        }
        @Test
        public void testCardHolderNameIfRussian() {
            var invalidCardHolder = DataHelper.getCardRussianHolderName();
            var travelPage = new PaymentPage();
            var creditCardPage = travelPage.selectBuyCreditCard();
            creditCardPage.creditCardFullInf(invalidCardHolder);;
            creditCardPage.valueFieldHolderTwo();
        }
        @Test
        public void testCardHolderIfDoubleName() {
            var validCardHolder = DataHelper.getDoubleNameHold();
            var travelPage = new PaymentPage();
            var creditCardPage = travelPage.selectBuyCreditCard();
            creditCardPage.creditCardFullInf(validCardHolder);
            creditCardPage.paymentApprove();
        }
    }

    @Nested
    class testFieldCardCvc {
        @Test
        public void testCvcFromOneDigit() {
            var invalidCvc = DataHelper.getCvcFromOneDigit();
            var travelPage = new PaymentPage();
            var creditCardPage = travelPage.selectBuyCreditCard();
            creditCardPage.creditCardFullInf(invalidCvc);
            creditCardPage.valueFieldCodeCvc();
        }
        @Test
        public void testCvcFromTwoDigit() {
            var invalidCvc = DataHelper.getCvcFromTwoDigit();
            var travelPage = new PaymentPage();
            var creditCardPage = travelPage.selectBuyCreditCard();
            creditCardPage.creditCardFullInf(invalidCvc);
            creditCardPage.valueFieldCodeCvc();
        }
        @Test
        public void testCvcFromThreeZero() {
            var validCvc = DataHelper.getCvcFromThreeZero();
            var travelPage = new PaymentPage();
            var creditCardPage = travelPage.selectBuyCreditCard();
            creditCardPage.creditCardFullInf(validCvc);
            creditCardPage.paymentApprove();
        }
    }
}

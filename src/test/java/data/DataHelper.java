package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class DataHelper {
    private DataHelper() {
    }
    @Value
    public static class CardInfo {
       private String number;
       private String month;
       private String year;
       private String holder;
       private String cvc;
    }
    static Faker faker = new Faker(new Locale("en"));
    static DateTimeFormatter format = DateTimeFormatter.ofPattern("MM");
    static String  monthEndOfAct = LocalDate.now().plusMonths(4).format(format);
    static DateTimeFormatter formatYear = DateTimeFormatter.ofPattern("yy");
    static String yearEndOfAct = LocalDate.now().plusYears(2).format(formatYear);
    static String nameHolder = faker.name().fullName();
    static String cvc = Integer.toString(faker.number().numberBetween(100, 999));

    public static CardInfo getApproveCard() {
        return new CardInfo("4444 4444 4444 4441",monthEndOfAct, yearEndOfAct, nameHolder, cvc);
    }

    public static CardInfo getDeclineCard() {
        return new CardInfo("4444 4444 4444 4442", monthEndOfAct, yearEndOfAct, nameHolder, cvc);
    }

    public static CardInfo getCardWithEmptyFields() {
        return new CardInfo("","","", "","");
    }

    public static CardInfo getCardWithShortNum() {
        return new CardInfo("4444 4444 4444 444", monthEndOfAct, yearEndOfAct, nameHolder, cvc);
    }

    public static CardInfo getCardNumFromZero() {
        return new CardInfo("0000 0000 0000 0000", monthEndOfAct, yearEndOfAct, nameHolder, cvc);
    }

    public static CardInfo getMonthIfMore12() {
        return new CardInfo("4444 4444 4444 4441", "19", yearEndOfAct, nameHolder, cvc);
    }

    public static CardInfo getMonthWhenOneDigit() {
        return new CardInfo("4444 4444 4444 4441", "3", yearEndOfAct, nameHolder, cvc);
    }

    public static CardInfo getMonthFromZero() {
        return new CardInfo("4444 4444 4444 4441", "0", yearEndOfAct, nameHolder, cvc );
    }

    public static CardInfo getYearFromZero() {
        return new CardInfo("4444 4444 4444 4441", monthEndOfAct, "00", nameHolder, cvc);
    }

    public static CardInfo getFutureYear() {
        return new CardInfo("4444 4444 4444 4441", monthEndOfAct, yearEndOfAct, nameHolder, cvc);
    }

    public static CardInfo getYearFromOneDigit() {
        return new CardInfo("4444 4444 4444 4441", monthEndOfAct, "4", nameHolder, cvc);
    }

    public static CardInfo getPastYear() {
        return new CardInfo("4444 4444 4444 4441", monthEndOfAct, "22", nameHolder, cvc);
    }

    public static CardInfo getDoubleNameHold() {
        return new CardInfo("4444 4444 4444 4441", monthEndOfAct, yearEndOfAct, "Daniel-Dominic Petrov", cvc);
    }

    public static CardInfo getInvalidCardHolder() {
        return new CardInfo("4444 4444 4444 4441", monthEndOfAct, yearEndOfAct, "&*^*^%&%&^12", cvc);
    }

    public static CardInfo getCardRussianHolderName() {
        return new CardInfo("4444 4444 4444 4441", monthEndOfAct, yearEndOfAct, "Даниил-Доминик Петров", cvc);
    }

    public static CardInfo getCvcFromOneDigit() {
        return new CardInfo("4444 4444 4444 4441", monthEndOfAct, yearEndOfAct, nameHolder, "2");
    }

    public static CardInfo getCvcFromTwoDigit() {
        return new CardInfo("4444 4444 4444 4441", monthEndOfAct, yearEndOfAct, nameHolder, "24");
    }

    public static CardInfo getCvcFromThreeZero() {
        return new CardInfo("4444 4444 4444 4441", monthEndOfAct, yearEndOfAct, nameHolder, "000");
    }
}

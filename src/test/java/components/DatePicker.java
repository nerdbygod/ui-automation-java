package components;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class DatePicker {
    SelenideElement datePicker = $(".react-datepicker");
    SelenideElement monthSelect = $(".react-datepicker__month-select");
    SelenideElement yearSelect = $(".react-datepicker__year-select");
    SelenideElement currentMonth = $(".react-datepicker__current-month");


    public enum Month {
        January, February, March, April,
        May, June, July, August,
        September, October, November, December;
    }

    public String formatDate(int day, Month month, int year) {
        return month.toString() + " " + ordinal(day) + ", " + year;
    }

    private static String ordinal(int i) {
        String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        return switch (i % 100) {
            case 11, 12, 13 -> i + "th";
            default -> i + suffixes[i % 10];
        };
    }

    public void pickMonth(Month month) {
        String monthString = month.toString();
        String monthOrdinal = String.valueOf(month.ordinal());
        monthSelect.selectOptionByValue(monthOrdinal);
        Assertions.assertTrue(currentMonth.text().startsWith(monthString));
    }

    public void pickYear(int year) {
        String yearString = String.valueOf(year);
        yearSelect.selectOptionByValue(yearString);
        Assertions.assertTrue(currentMonth.text().endsWith(yearString));
    }

    public void pickDay(int day, Month month, int year) {
        $("[aria-label='Choose Thursday, " + formatDate(day, month, year) + "']")
                .click(ClickOptions.usingJavaScript());
    }

    public void assertDatePickerDisappeared() {
        datePicker.shouldNotBe(visible);
    }

}
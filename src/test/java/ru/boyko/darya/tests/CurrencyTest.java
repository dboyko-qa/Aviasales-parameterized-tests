package ru.boyko.darya.tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@DisplayName("Тесты на проверку изменения валюты на сайте Aviasales для отработки параметризации в JUnit")
public class CurrencyTest extends TestBase{
    private static final String SIMPLE_DATE_FORMAT = "MMM dd yyyy";
    private static final String DEFAULT_ORIGIN_AIRPORT = "Москва";
    private static final String DEFAULT_DESTIONATION_AIRPORT = "Санкт-Петербург";
    Faker faker = new Faker();
    private String[] getTripDates(){
        Locale locEn = new Locale("en", "US");
        int fromDays = 7;
        int toDays = 14;
        String startDate = new SimpleDateFormat(SIMPLE_DATE_FORMAT, locEn)
                .format(faker.date().future(fromDays, TimeUnit.DAYS));
        String endDate = new SimpleDateFormat(SIMPLE_DATE_FORMAT, locEn)
                .format(faker.date().future(toDays,fromDays, TimeUnit.DAYS));

        return new String[] {startDate, endDate};
    }

    // test for CsvSource parameterized test
    @CsvSource(value = {
            "EUR | €",
            "RUB | ₽"
    },
            delimiter = '|')
    @ParameterizedTest(name = "Для валюты {0} в результатах показывается знак {1}")
    public void selectCurrencyTest(String currencyAbbr, String currencySign) {

        String[] tripDates = getTripDates();
        String startDate = tripDates[0];
        String endDate = tripDates[1];

        aviaMainPage.openPage()
                .turnOffBookingCheckbox()
                .setCurrency(currencyAbbr)
                .setOriginAirport(DEFAULT_ORIGIN_AIRPORT)
                .setDestinationAirport(DEFAULT_DESTIONATION_AIRPORT)
                .setStartEndDates(startDate, endDate)
                .findTickets()
                .verifyCurrencySignInResult(currencySign);
    }

    // test for ValueSource parameterized test
    @ValueSource(strings = {
            "USD",
            "GBP"
    })
    @ParameterizedTest(name = "Для валюты {0} показываются результаты")
    public void searchWithCurrencyTest(String currencyAbbr) {

        String[] tripDates = getTripDates();
        String startDate = tripDates[0];
        String endDate = tripDates[1];

        aviaMainPage.openPage()
                .turnOffBookingCheckbox()
                .setCurrency(currencyAbbr)
                .setOriginAirport(DEFAULT_ORIGIN_AIRPORT)
                .setDestinationAirport(DEFAULT_DESTIONATION_AIRPORT)
                .setStartEndDates(startDate, endDate)
                .findTickets()
                .isResultBlockExist();
    }


}

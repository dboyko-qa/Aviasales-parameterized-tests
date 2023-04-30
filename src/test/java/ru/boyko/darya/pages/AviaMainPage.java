package ru.boyko.darya.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;

import java.util.List;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class AviaMainPage {
    SelenideElement countrySettings = $(".s__q8JaLokdctjkb1_n4aw0");
    SelenideElement currencyLine = $(".s__DYtpBgCNdfaX2lp6GLt9");
    SelenideElement resultBlock = $("[data-test-id='ticket-desktop']");
    SelenideElement originAirport = $("[data-test-id='origin-autocomplete-field']");
    SelenideElement destinationAirport = $("[data-test-id='destination-autocomplete-field']");


    public Boolean isResultBlockExist(){
        return resultBlock.exists();
    }

    public AviaMainPage verifyCurrencySignInResult(String currencySign){
        $("[data-test-id='currency-sign']").shouldHave(Condition.text(currencySign));
        return this;
    }

    public AviaMainPage findTickets() {
        $("[data-test-id='form-submit']").click();
        return this;
    }

    public AviaMainPage setStartEndDates(String startDate, String endDate) {
        //locator for calendar element changes sometimes so add condition to call one or another locator
        if ($(".trip-duration__field").exists()) {
            $(".trip-duration__field").click();
        } else {
            $("[data-test-id='start-date-field']").click();
        };
        String calendarDateLocator = "[aria-label*='%s']";
        $(String.format(calendarDateLocator,startDate)).click();
        $(String.format(calendarDateLocator,endDate)).click();
        return this;
    }

    public AviaMainPage setDestinationAirport(String destinationAirportName) {
        destinationAirport.setValue(destinationAirportName).pressEnter();
        return this;
    }

    public AviaMainPage enterOriginAirport(String originAirportName) {

        originAirport.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        originAirport.sendKeys(originAirportName);
        return this;
    }

    public AviaMainPage setOriginAirport(String originAirportName) {

        enterOriginAirport(originAirportName);
        originAirport.pressEnter();
        return this;
    }

    public AviaMainPage setCurrency(String currencyName) {
        countrySettings.click();
        currencyLine.$(byText(currencyName)).click();
        return this;
    }

    public AviaMainPage turnOffBookingCheckbox() {
        WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("uncheck_hotel_cookie", "true"));
        return this;
    }

    public AviaMainPage openPage() {
        open("https://www.aviasales.ru");
        return this;
    }

    public AviaMainPage verifySuggestedAirports(List<String> expectedAirports){

        $$("[data-test-id*='suggested-airport'] span span").shouldHave(CollectionCondition.textsInAnyOrder(expectedAirports));
        return this;

    }
}

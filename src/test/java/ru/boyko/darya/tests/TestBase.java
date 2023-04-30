package ru.boyko.darya.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import ru.boyko.darya.pages.AviaMainPage;

public class TestBase {
    AviaMainPage aviaMainPage = new AviaMainPage();
    @BeforeEach
    private void beforeEach(){
        Configuration.browserSize = "1920x1080";
    }
}

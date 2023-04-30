package ru.boyko.darya.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

@DisplayName("Тесты на проверку выбора аэропорта на сайте Aviasales для отработки параметризации в JUnit")
public class AirportsTest extends TestBase{
    static Stream<Arguments> expectedAirportsProvider() {
        return Stream.of(
                Arguments.of(
                        "Москва", List.of("Домодедово", "Внуково", "Жуковский", "Шереметьево")
                ),
                Arguments.of(
                        "Стамбул", List.of("Стамбул Новый аэропорт", "Сабиха Гёкчен")
                )
        );
    }

    @MethodSource("expectedAirportsProvider")
    @ParameterizedTest(name="Для города {0} предлагаются аэропорты {1}")
    void multipleAirportsTest(String airport, List<String> expectedAirports) {
        aviaMainPage.openPage()
                .enterOriginAirport(airport)
                .verifySuggestedAirports(expectedAirports);

    }
}

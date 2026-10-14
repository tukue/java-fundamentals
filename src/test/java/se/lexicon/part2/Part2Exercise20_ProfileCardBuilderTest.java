package se.lexicon.part2;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Part2Exercise20_ProfileCardBuilderTest {

    @Test
    void calculateAge_BirthDateInPast_returnsCorrectYears() {
        LocalDate birthDate = LocalDate.of(1998, 4, 22);
        LocalDate today = LocalDate.of(2026, 10, 14);

        assertEquals(28, Part2Exercise20_ProfileCardBuilder.calculateAge(birthDate, today));
    }

    @Test
    void calculateAge_BirthDateToday_returnsZero() {
        LocalDate birthDate = LocalDate.of(2026, 10, 14);
        LocalDate today = LocalDate.of(2026, 10, 14);

        assertEquals(0, Part2Exercise20_ProfileCardBuilder.calculateAge(birthDate, today));
    }
}

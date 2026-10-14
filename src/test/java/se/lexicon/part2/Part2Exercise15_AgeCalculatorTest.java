package se.lexicon.part2;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Part2Exercise15_AgeCalculatorTest {

    @Test
    void calculateAgeYears_AlreadyPassedBirthday_returnsCorrectAge() {
        LocalDate birthDate = LocalDate.of(2000, 3, 15);
        LocalDate today = LocalDate.of(2026, 10, 14);

        assertEquals(26, Part2Exercise15_AgeCalculator.calculateAgeYears(birthDate, today));
    }

    @Test
    void calculateAgeYears_BirthdayNotReached_returnsPreviousAge() {
        LocalDate birthDate = LocalDate.of(2000, 12, 31);
        LocalDate today = LocalDate.of(2026, 1, 10);

        assertEquals(25, Part2Exercise15_AgeCalculator.calculateAgeYears(birthDate, today));
    }

    @Test
    void daysUntilNextBirthday_BeforeBirthday_returnsRemainingDays() {
        LocalDate birthDate = LocalDate.of(2000, 3, 15);
        LocalDate today = LocalDate.of(2026, 10, 14);

        assertEquals(151, Part2Exercise15_AgeCalculator.daysUntilNextBirthday(birthDate, today));
    }

    @Test
    void daysUntilNextBirthday_OnBirthday_returnsZero() {
        LocalDate birthDate = LocalDate.of(2000, 3, 15);
        LocalDate today = LocalDate.of(2026, 3, 15);

        assertEquals(0, Part2Exercise15_AgeCalculator.daysUntilNextBirthday(birthDate, today));
    }
}

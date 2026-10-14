package se.lexicon.part2;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Part2Exercise16_EventCountdownTest {

    @Test
    void daysBetween_FutureDate_returnsPositive() {
        LocalDate today = LocalDate.of(2026, 6, 11);
        LocalDate eventDate = LocalDate.of(2026, 6, 20);

        assertEquals(9, Part2Exercise16_EventCountdown.daysBetween(today, eventDate));
    }

    @Test
    void daysBetween_PastDate_returnsNegative() {
        LocalDate today = LocalDate.of(2026, 6, 11);
        LocalDate eventDate = LocalDate.of(2026, 1, 1);

        assertEquals(-161, Part2Exercise16_EventCountdown.daysBetween(today, eventDate));
    }

    @Test
    void daysBetween_SameDate_returnsZero() {
        LocalDate today = LocalDate.of(2026, 6, 11);
        LocalDate eventDate = LocalDate.of(2026, 6, 11);

        assertEquals(0, Part2Exercise16_EventCountdown.daysBetween(today, eventDate));
    }

    @Test
    void countdownMessage_InvalidDate_returnsHelpfulMessage() {
        LocalDate today = LocalDate.of(2026, 6, 11);

        assertEquals("Invalid date format. Please use yyyy-MM-dd.",
                Part2Exercise16_EventCountdown.countdownMessage("Event", "not-a-date", today));
    }
}

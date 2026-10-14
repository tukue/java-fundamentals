package se.lexicon.part2;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Part2Exercise14_TodaysCalendarTest {

    @Test
    void buildDateText_ThursdayDate_returnsExpected() {
        LocalDate date = LocalDate.of(2026, 6, 11);
        assertEquals("Thursday, 11 June 2026",
                Part2Exercise14_TodaysCalendar.buildDateText(date));
    }

    @Test
    void weekdayOrWeekend_Saturday_returnsWeekend() {
        LocalDate date = LocalDate.of(2026, 6, 13);
        assertEquals("Weekend",
                Part2Exercise14_TodaysCalendar.weekdayOrWeekend(date));
    }

    @Test
    void weekdayOrWeekend_Monday_returnsWeekday() {
        LocalDate date = LocalDate.of(2026, 6, 15);
        assertEquals("Weekday",
                Part2Exercise14_TodaysCalendar.weekdayOrWeekend(date));
    }
}

package se.lexicon;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Exercise13_WeekdayOrWeekendTest {
    @Test
    void monday_returnsWeekday() {
        assertEquals("Weekday", weekDayOrWeekend("Monday"));
    }

    @Test
    void friday_returnsWeekday() {
        assertEquals("Weekday", weekDayOrWeekend("Friday"));
    }

    @Test
    void saturday_returnsWeekend() {
        assertEquals("Weekend", weekDayOrWeekend("Saturday"));
    }

    @Test
    void sunday_returnsWeekend() {
        assertEquals("Weekend", weekDayOrWeekend("Sunday"));
    }

    @Test
    void unknownDay_returnsNotSure() {
        assertEquals("Hmm, I'm not sure about that day", weekDayOrWeekend("everyday"));
    }

    private String weekDayOrWeekend(String day) {
        return switch (day) {
            case "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" -> "Weekday";
            case "Saturday", "Sunday" -> "Weekend";
            default -> "Hmm, I'm not sure about that day";
        };
    }
}
package se.lexicon.part2;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Part2Exercise18_DateRangePrinterTest {

    @Test
    void generateDateRange_IncludesStartAndEndDates() {
        LocalDate startDate = LocalDate.of(2026, 6, 8);
        LocalDate endDate = LocalDate.of(2026, 6, 12);

        List<String> dates = Part2Exercise18_DateRangePrinter.generateDateRange(startDate, endDate);

        assertEquals(5, dates.size());
        assertEquals("08/06/2026 (Monday)", dates.get(0));
        assertEquals("12/06/2026 (Friday)", dates.get(4));
    }

    @Test
    void generateDateRange_SingleDay_returnsOneDate() {
        LocalDate startDate = LocalDate.of(2026, 6, 11);
        LocalDate endDate = LocalDate.of(2026, 6, 11);

        List<String> dates = Part2Exercise18_DateRangePrinter.generateDateRange(startDate, endDate);

        assertEquals(1, dates.size());
        assertEquals("11/06/2026 (Thursday)", dates.get(0));
    }
}

package se.lexicon.part2;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Part2Exercise17_AppointmentPlannerTest {

    @Test
    void sortAppointments_SortsByDateTime() {
        var gym = new Part2Exercise17_AppointmentPlanner.Appointment(
                "Gym", LocalDateTime.of(2026, 6, 11, 17, 0));
        var meeting = new Part2Exercise17_AppointmentPlanner.Appointment(
                "Team Meeting", LocalDateTime.of(2026, 6, 12, 14, 30));
        var dentist = new Part2Exercise17_AppointmentPlanner.Appointment(
                "Dentist", LocalDateTime.of(2026, 6, 15, 9, 0));

        var sorted = Part2Exercise17_AppointmentPlanner.sortAppointments(List.of(dentist, meeting, gym));

        assertEquals(List.of("Gym", "Team Meeting", "Dentist"),
                sorted.stream().map(a -> a.name).toList());
    }
}

package se.lexicon.part2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Part2Exercise17_AppointmentPlanner {

    public static class Appointment {
        final String name;
        final LocalDateTime dateTime;

        public Appointment(String name, LocalDateTime dateTime) {
            this.name = name;
            this.dateTime = dateTime;
        }
    }

    public static List<Appointment> sortAppointments(List<Appointment> appointments) {
        var sorted = new ArrayList<>(appointments);
        sorted.sort(Comparator.comparing(a -> a.dateTime));
        return sorted;
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        List<Appointment> appointments = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            System.out.print("Enter appointment " + i + " name: ");
            String name = scanner.nextLine();

            System.out.print("Enter appointment " + i + " date-time (yyyy-MM-dd HH:mm): ");
            String value = scanner.nextLine();

            LocalDateTime dateTime = LocalDateTime.parse(value,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            appointments.add(new Appointment(name, dateTime));
        }

        List<Appointment> sortedAppointments = sortAppointments(appointments);

        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern(
                "eeee, dd MMMM yyyy 'at' HH:mm", Locale.ENGLISH);

        System.out.println();
        System.out.println("--- Your Schedule ---");
        for (int i = 0; i < sortedAppointments.size(); i++) {
            Appointment appointment = sortedAppointments.get(i);
            System.out.println((i + 1) + ". " + String.format("%-14s", appointment.name)
                    + "– " + appointment.dateTime.format(outputFormat));
        }
    }
}

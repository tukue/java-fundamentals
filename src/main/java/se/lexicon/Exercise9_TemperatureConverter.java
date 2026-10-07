package se.lexicon;

import java.util.Scanner;

public class Exercise9_TemperatureConverter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter temperature in Celsius: ");
        var celsius = scanner.nextDouble();

        var fahrenheit = celsius * 9.0 / 5 + 32;
        var kelvin = celsius + 273.15;

        System.out.println("Celsius: " + celsius + " °C");
        System.out.println("Fahrenheit: " + fahrenheit + " °F");
        System.out.println("Kelvin: " + kelvin + " K");
    }
}
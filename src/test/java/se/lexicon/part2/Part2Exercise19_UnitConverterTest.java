package se.lexicon.part2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Part2Exercise19_UnitConverterTest {

    @Test
    void convert_KilometersToMiles_returnsExpectedValue() {
        assertEquals(62.1371, Part2Exercise19_UnitConverter.convert(100), 0.001);
    }

    @Test
    void convert_KilogramsToPounds_returnsExpectedValue() {
        assertEquals(110.231, Part2Exercise19_UnitConverter.convert(50, "lbs"), 0.001);
    }

    @Test
    void toFahrenheit_Celsius_returnsExpectedValue() {
        assertEquals(68.0, Part2Exercise19_UnitConverter.toFahrenheit(20), 0.001);
    }

    @Test
    void toKelvin_Celsius_returnsExpectedValue() {
        assertEquals(293.15, Part2Exercise19_UnitConverter.toKelvin(20), 0.001);
    }
}

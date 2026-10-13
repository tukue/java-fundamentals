package se.lexicon.part2;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Part2Exercise13_ReceiptBuilderTest {
    @Test
    void grandTotal_ThreePrices_returnsSum() {
        assertEquals(75.50, Part2Exercise13_ReceiptBuilder.grandTotal(List.of(35.00, 22.50, 18.00)), 0.0001);
    }

    @Test
    void grandTotal_Empty_returns0() {
        assertEquals(0.0, Part2Exercise13_ReceiptBuilder.grandTotal(List.of()), 0.0001);
    }

    @Test
    void buildReceipt_SampleItems_containsLinesAndTotal() {
        var receipt = Part2Exercise13_ReceiptBuilder.buildReceipt(
                List.of("Avocado", "Milk", "Bread"),
                List.of(35.00, 22.50, 18.00));
        assertTrue(receipt.contains("Avocado"));
        assertTrue(receipt.contains("Milk"));
        assertTrue(receipt.contains("Bread"));
        assertTrue(receipt.contains("Receipt"));
        assertTrue(receipt.contains("75.50"));
    }

    @Test
    void formatLine_AlignsPrice() {
        assertEquals("Avocado                 35.00",
                Part2Exercise13_ReceiptBuilder.formatLine("Avocado", 35.00));
    }
}

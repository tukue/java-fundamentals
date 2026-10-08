package se.lexicon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderTest {

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setCustomerName("Test");
        order.setItemName("Latte");
        order.setQuantity(2);
        order.setUnitPrice(40.00);
    }

    private double total() {
        order.calculateSubtotal();
        order.calculateDiscount();
        order.calculateVAT();
        order.calculateTotal();
        return order.getTotal();
    }

    @Test
    void nonMemberNoDiscount_totalIsSubtotalPlusVat() {
        order.setMember(false);
        var expected = 80.00 + (80.00 * 0.12);
        assertEquals(expected, total(), 0.001);
    }

    @Test
    void member_gets15PercentDiscount() {
        order.setMember(true);
        var discounted = 80.00 * 0.85;
        var expected = discounted + (discounted * 0.12);
        assertEquals(expected, total(), 0.001);
    }

    @Test
    void nonMemberAbove150_gets10PercentDiscount() {
        order.setQuantity(4); // 160.00
        order.setMember(false);
        var discounted = 160.00 * 0.90;
        var expected = discounted + (discounted * 0.12);
        assertEquals(expected, total(), 0.001);
    }

    @Test
    void nonMemberExactly150_getsNoDiscount() {
        order.setQuantity(3); // 120.00
        order.setUnitPrice(50.00); // 150.00 exactly - threshold is > 150
        order.setMember(false);
        var expected = 150.00 + (150.00 * 0.12);
        assertEquals(expected, total(), 0.001);
    }

    @Test
    void memberDiscountTakesPriorityOverBulkDiscount() {
        order.setQuantity(4); // 160.00, member gets 15% not 10%
        order.setMember(true);
        var discounted = 160.00 * 0.85;
        var expected = discounted + (discounted * 0.12);
        assertEquals(expected, total(), 0.001);
    }

    @Test
    void quantityAffectsSubtotal() {
        order.setQuantity(3);
        order.setMember(false);
        var expected = 120.00 + (120.00 * 0.12);
        assertEquals(expected, total(), 0.001);
    }

    @Test
    void printReceipt_outputsCustomerItemAndTotal() {
        order.setMember(false);
        total();

        var out = new ByteArrayOutputStream();
        var original = System.out;
        System.setOut(new PrintStream(out));
        try {
            order.printReceipt();
        } finally {
            System.setOut(original);
        }

        var receipt = out.toString();
        assertTrue(receipt.contains("Customer  : Test"));
        assertTrue(receipt.contains("Latte x 2"));
        assertTrue(receipt.contains(String.format("TOTAL     : %.2f SEK", order.getTotal())));
    }

    @Test
    void printReceipt_includesDiscountLineOnlyWhenDiscountApplied() {
        order.setMember(true);
        total();

        var out = new ByteArrayOutputStream();
        var original = System.out;
        System.setOut(new PrintStream(out));
        try {
            order.printReceipt();
        } finally {
            System.setOut(original);
        }

        assertTrue(out.toString().contains("Discount  : -"));
    }
}

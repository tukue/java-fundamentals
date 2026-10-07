package se.lexicon;

import java.util.Scanner;

public class Exercise3_ShoppingReceipt {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        var item1 = "Apple";
        var qty1 = 2;
        var price1 = 15.00;

        var item2 = "Milk";
        var qty2 = 1;
        var price2 = 22.50;

        var item3 = "Bread";
        var qty3 = 3;
        var price3 = 18.00;

        var total1 = qty1 * price1;
        var total2 = qty2 * price2;
        var total3 = qty3 * price3;
        var grandTotal = total1 + total2 + total3;

        System.out.println("==============================");
        System.out.println("Receipt");
        System.out.println("==============================");
        System.out.println(item1 + " " + qty1 + " x " + price1 + " = " + total1 + " SEK");
        System.out.println(item2 + " " + qty2 + " x " + price2 + " = " + total2 + " SEK");
        System.out.println();
        System.out.println(item3 + " " + qty3 + " x " + price3 + " = " + total3 + " SEK");
        System.out.println("Grand Total: " + grandTotal + " SEK");
    }
}
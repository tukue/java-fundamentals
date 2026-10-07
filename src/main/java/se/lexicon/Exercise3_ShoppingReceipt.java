package se.lexicon;

import java.util.Scanner;

public class Exercise3_ShoppingReceipt {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Item 1
        String item1 = "Apple";
        int qty1 = 2;
        double price1 = 15.00;

        // Item 2
        String item2 = "Milk";
        int qty2 = 1;
        double price2 = 22.50;

        // Item 3
        String item3 = "Bread";
        int qty3 = 3;
        double price3 = 18.00;

        double total1 = qty1 * price1;
        double total2 = qty2 * price2;
        double total3 = qty3 * price3;
        double grandTotal = total1 + total2 + total3;

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
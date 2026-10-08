package se.lexicon;

import java.util.Scanner;

public class CafeApp {

    private static final Scanner SCANNER = new Scanner(System.in);

    // Menu items: number, name, price
    private static final String[][] MENU = {
            {"1", "Espresso", "25.00"},
            {"2", "Cappuccino", "35.00"},
            {"3", "Latte", "40.00"},
            {"4", "Croissant", "30.00"},
            {"5", "Sandwich", "55.00"}
    };

    public static void main(String[] args) {
        System.out.print("Welcome! What is your name? ");
        var name = SCANNER.nextLine();

        System.out.println("\nHi " + name + "! Here is our menu:");
        printMenu();

        var isMember = isLoyaltyMember();

        while (true) {
            System.out.print("\nEnter item number (1-5, or 0 to finish): ");
            var itemNum = SCANNER.nextInt();
            if (itemNum == 0) break;

            System.out.print("How many? ");
            var quantity = SCANNER.nextInt();
            SCANNER.nextLine(); // consume newline

            var item = getItemByNumber(itemNum);
            if (item == null) {
                System.out.println("Invalid item number. Try again.");
                continue;
            }

            var price = Double.parseDouble(item[2]);
            var subtotal = price * quantity;

            var discountPercent = calculateDiscountPercent(isMember, subtotal);
            var discountAmount = subtotal * discountPercent;
            var afterDiscount = subtotal - discountAmount;
            var vat = afterDiscount * 0.12;
            var total = afterDiscount + vat;

            printReceipt(name, item[1], quantity, price, subtotal, discountAmount, vat, total);

            System.out.print("\nNext item number (1-5, or 0 to finish for this customer): ");
            itemNum = SCANNER.nextInt();
            if (itemNum == 0) {
                System.out.println("\n   Thank you, " + name + "!");
                System.out.println("   See you next time.");
                break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("==============================");
        System.out.println("       Lexicon Cafe");
        System.out.println("==============================");
        for (var row : MENU) {
            System.out.printf("%-2s. %-12s %.2f SEK%n", row[0], row[1], Double.parseDouble(row[2]));
        }
        System.out.println("==============================");
    }

    private static boolean isLoyaltyMember() {
        while (true) {
            System.out.print("Loyalty member? (yes/no): ");
            var input = SCANNER.nextLine().trim().toLowerCase();
            if (input.equals("yes")) return true;
            if (input.equals("no")) return false;
            System.out.println("Please enter 'yes' or 'no'.");
        }
    }

    private static String[] getItemByNumber(int number) {
        for (var row : MENU) {
            var num = Integer.parseInt(row[0]);
            if (num == number) return row;
        }
        return null;
    }

    private static double calculateDiscountPercent(boolean isMember, double subtotal) {
        if (isMember) {
            return 0.15;
        }
        if (subtotal > 150.0) {
            return 0.10;
        }
        return 0.0;
    }

    private static void printReceipt(String customer, String item, int quantity,
                                     double unitPrice, double subtotal, double discount,
                                     double vat, double total) {
        System.out.println("==============================");
        System.out.println("      LEXICON CAFE");
        System.out.println("==============================");
        System.out.printf("Customer  : %s%n", customer);
        System.out.printf("Item      : %s x %d%n", item, quantity);
        System.out.printf("Subtotal  : %.2f SEK%n", subtotal);
        if (discount > 0) {
            System.out.printf("Discount  : -%.2f SEK%n", discount);
        }
        System.out.printf("VAT       : %.2f SEK%n", vat);
        System.out.println("------------------------------");
        System.out.printf("TOTAL     : %.2f SEK%n", total);
        System.out.println("   Thank you, " + customer + "!");
        System.out.println("   See you next time.");
    }
}
package se.lexicon;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CafeApp {

    private static final Scanner SCANNER = new Scanner(System.in);

    private static final String[][] MENU = {
            {"1", "Espresso", "25.00"},
            {"2", "Cappuccino", "35.00"},
            {"3", "Latte", "40.00"},
            {"4", "Croissant", "30.00"},
            {"5", "Sandwich", "55.00"}
    };

    public static void main(String[] args) {
        var totalRevenue = 0.0;
        var customersServed = 0;

        while (true) {
            System.out.print("Welcome! What is your name (or 'done' to close): ");
            var name = SCANNER.nextLine();

            if (name.trim().toLowerCase().equals("done")) {
                printEndOfDayReport(customersServed, totalRevenue);
                break;
            }

            System.out.println("\nHi " + name + "! Here is our menu:");
            printMenu();

            var isMember = isLoyaltyMember();
            var customerSubtotal = processCustomerOrder(isMember);

            totalRevenue += customerSubtotal;
            customersServed++;

            System.out.println("\n   Thank you, " + name + "!");
            System.out.println("   See you next time.");
        }
    }

    private static double processCustomerOrder(boolean isMember) {
        var subtotal = 0.0;

        while (true) {
            var itemNum = getValidItemNumber();
            if (itemNum == 0) break;

            var quantity = getValidQuantity();
            if (quantity <= 0) {
                System.out.println("Quantity must be greater than 0. Try again.");
                continue;
            }

            var item = getItemByNumber(itemNum);
            if (item == null) {
                System.out.println("Invalid item number. Try again.");
                continue;
            }

            var price = Double.parseDouble(item[2]);
            var itemSubtotal = price * quantity;
            subtotal += itemSubtotal;

            var discountPercent = calculateDiscountPercent(isMember, subtotal);
            var discountAmount = subtotal * discountPercent;
            var afterDiscount = subtotal - discountAmount;
            var vat = afterDiscount * 0.12;
            var total = afterDiscount + vat;

            printReceipt(name, item[1], quantity, price, subtotal, discountAmount, vat, total);
        }

        return subtotal;
    }

    private static int getValidItemNumber() {
        while (true) {
            System.out.print("\nEnter item number (1-5, or 0 to finish): ");
            if (SCANNER.hasNextInt()) {
                var num = SCANNER.nextInt();
                SCANNER.nextLine(); // consume newline
                if (num >= 0 && num <= 5) {
                    return num;
                }
            } else {
                SCANNER.nextLine(); // discard invalid input
            }
            System.out.println("Invalid input. Please enter a number between 0 and 5.");
        }
    }

    private static int getValidQuantity() {
        while (true) {
            System.out.print("How many? ");
            if (SCANNER.hasNextInt()) {
                var num = SCANNER.nextInt();
                SCANNER.nextLine(); // consume newline
                if (num > 0) {
                    return num;
                }
            } else {
                SCANNER.nextLine(); // discard invalid input
            }
            System.out.println("Invalid quantity. Please enter a positive number.");
        }
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
    }

    private static void printEndOfDayReport(int customersServed, double totalRevenue) {
        System.out.println("==============================");
        System.out.println("      END OF DAY REPORT");
        System.out.println("==============================");
        System.out.printf("Customers served : %d%n", customersServed);
        System.out.printf("Total revenue    : %.2f SEK%n", totalRevenue);
        System.out.println("==============================");
    }
}
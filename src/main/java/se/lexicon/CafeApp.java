package se.lexicon;

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

            var order = new Order();
            order.setCustomerName(name);

            var isMember = isLoyaltyMember();
            order.setMember(isMember);

            var itemNum = getValidItemNumber();
            if (itemNum == 0) {
                System.out.println("\n   Thank you, " + name + "!");
                System.out.println("   See you next time.");
                continue;
            }

            var item = getItemByNumber(itemNum);
            var price = Double.parseDouble(item[2]);

            System.out.print("How many? ");
            var quantity = SCANNER.nextInt();
            SCANNER.nextLine(); // consume newline

            order.setItemName(item[1]);
            order.setQuantity(quantity);
            order.setUnitPrice(price);

            order.calculateSubtotal();
            order.calculateDiscount();
            order.calculateVAT();
            order.calculateTotal();

            order.printReceipt();

            totalRevenue += order.getTotal();
            customersServed++;

            System.out.println("\n   Thank you, " + name + "!");
            System.out.println("   See you next time.");
        }
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

    private static void printMenu() {
        System.out.println("==============================");
        System.out.println("       Lexicon Cafe");
        System.out.println("==============================");
        for (var row : MENU) {
            System.out.printf("%-2s. %-12s %.2f SEK%n", row[0], row[1], Double.parseDouble(row[2]));
        }
        System.out.println("==============================");
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
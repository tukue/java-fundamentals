package se.lexicon.part2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Part2Exercise13_ReceiptBuilder {

    public static String formatLine(String name, double price) {
        return String.format("%-24s%5.2f", name, price);
    }

    public static double grandTotal(List<Double> prices) {
        var total = 0.0;
        for (var price : prices) {
            total += price;
        }
        return total;
    }

    public static String buildReceipt(List<String> names, List<Double> prices) {
        var sb = new StringBuilder();
        sb.append("==============================\n");
        sb.append("           Receipt\n");
        sb.append("==============================\n");
        for (var i = 0; i < names.size(); i++) {
            sb.append(formatLine(names.get(i), prices.get(i))).append("\n");
        }
        sb.append("------------------------------\n");
        sb.append(formatLine("Grand Total:", grandTotal(prices))).append("\n");
        sb.append("==============================");
        return sb.toString();
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var names = new ArrayList<String>();
        var prices = new ArrayList<Double>();
        while (true) {
            System.out.print("Enter item name (or 'done'): ");
            var name = scanner.nextLine();
            if (name.equalsIgnoreCase("done")) {
                break;
            }
            System.out.print("Enter price: ");
            var price = Double.parseDouble(scanner.nextLine().trim());
            names.add(name);
            prices.add(price);
        }
        System.out.println();
        System.out.println(buildReceipt(names, prices));
    }
}

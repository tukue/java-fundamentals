/*
 * Collection choice: ArrayList<String>
 *
 * - A shopping list is ordered, so we can print it numbered (1, 2, 3...).
 * - The same item can appear more than once (e.g. two bags of milk), so a List
 *   is a better fit than a Set.
 * - ArrayList gives fast indexed access for printing and remove-by-name for
 *   updating the list.
 */
package se.lexicon.collections;

import java.util.ArrayList;
import java.util.List;

public class Exercise01_ShoppingList {

    public static List<String> createShoppingList() {
        var items = new ArrayList<String>();
        items.add("Milk");
        items.add("Bread");
        items.add("Eggs");
        items.add("Butter");
        items.add("Coffee");
        return items;
    }

    public static boolean removeItem(List<String> items, String item) {
        return items.remove(item);
    }

    public static String numberedList(List<String> items) {
        var builder = new StringBuilder();
        for (var i = 0; i < items.size(); i++) {
            builder.append(i + 1).append(". ").append(items.get(i)).append(System.lineSeparator());
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        var items = createShoppingList();
        System.out.println("Shopping list:");
        System.out.print(numberedList(items));

        removeItem(items, "Bread");

        System.out.println("Updated list:");
        System.out.print(numberedList(items));
    }
}

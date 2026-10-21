package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class Exercise01_ShoppingListTest {

    @Test
    void createShoppingList_containsFiveItems() {
        assertEquals(5, Exercise01_ShoppingList.createShoppingList().size());
    }

    @Test
    void removeItem_existingItem_removesIt() {
        var items = new ArrayList<>(Exercise01_ShoppingList.createShoppingList());
        assertTrue(Exercise01_ShoppingList.removeItem(items, "Milk"));
        assertEquals(4, items.size());
        assertFalse(items.contains("Milk"));
    }

    @Test
    void removeItem_missingItem_returnsFalseAndKeepsList() {
        var items = new ArrayList<>(Exercise01_ShoppingList.createShoppingList());
        assertFalse(Exercise01_ShoppingList.removeItem(items, "Pizza"));
        assertEquals(5, items.size());
    }

    @Test
    void numberedList_numbersEachItem() {
        var result = Exercise01_ShoppingList.numberedList(java.util.List.of("Milk", "Bread"));
        assertTrue(result.contains("1. Milk"));
        assertTrue(result.contains("2. Bread"));
    }
}

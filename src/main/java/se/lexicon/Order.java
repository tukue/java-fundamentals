package se.lexicon;

public class Order {

    private String customerName;
    private String itemName;
    private int quantity;
    private double unitPrice;
    private boolean isMember;
    private double subtotal;
    private double discountAmount;
    private double vat;
    private double total;

    public void setCustomerName(String name) {
        this.customerName = name;
    }

    public void setItemName(String item) {
        this.itemName = item;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnitPrice(double price) {
        this.unitPrice = price;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    public void calculateSubtotal() {
        subtotal = unitPrice * quantity;
    }

    public void calculateDiscount() {
        if (isMember) {
            discountAmount = subtotal * 0.15;
        } else if (subtotal > 150.0) {
            discountAmount = subtotal * 0.10;
        } else {
            discountAmount = 0.0;
        }
    }

    public void calculateVAT() {
        vat = (subtotal - discountAmount) * 0.12;
    }

    public void calculateTotal() {
        total = subtotal - discountAmount + vat;
    }

    public double getTotal() {
        return total;
    }

    public void printReceipt() {
        System.out.println("==============================");
        System.out.println("      LEXICON CAFE");
        System.out.println("==============================");
        System.out.printf("Customer  : %s%n", customerName);
        System.out.printf("Item      : %s x %d%n", itemName, quantity);
        System.out.printf("Subtotal  : %.2f SEK%n", subtotal);
        if (discountAmount > 0) {
            System.out.printf("Discount  : -%.2f SEK%n", discountAmount);
        }
        System.out.printf("VAT       : %.2f SEK%n", vat);
        System.out.println("------------------------------");
        System.out.printf("TOTAL     : %.2f SEK%n", total);
        System.out.println("   Thank you, " + customerName + "!");
        System.out.println("   See you next time.");
    }
}
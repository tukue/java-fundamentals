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

    public void calculateTotals() {
        calculateSubtotal();
        calculateDiscount();
        calculateVAT();
        calculateTotal();
    }

    public double getTotal() {
        return total;
    }

    public String formatReceipt() {
        var sb = new StringBuilder();
        sb.append("==============================\n");
        sb.append("      LEXICON CAFE\n");
        sb.append("==============================\n");
        sb.append(String.format("Customer  : %s%n", customerName));
        sb.append(String.format("Item      : %s x %d%n", itemName, quantity));
        sb.append(String.format("Subtotal  : %.2f SEK%n", subtotal));
        if (discountAmount > 0) {
            sb.append(String.format("Discount  : -%.2f SEK%n", discountAmount));
        }
        sb.append(String.format("VAT       : %.2f SEK%n", vat));
        sb.append("------------------------------\n");
        sb.append(String.format("TOTAL     : %.2f SEK%n", total));
        sb.append("   Thank you, " + customerName + "!\n");
        sb.append("   See you next time.\n");
        return sb.toString();
    }

    public void printReceipt() {
        System.out.print(formatReceipt());
    }
}
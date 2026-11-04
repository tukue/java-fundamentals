package se.lexicon.bank;

public abstract class Account {
    private final String holder;
    private double balance;

    protected Account(String holder, double balance) {
        if (holder == null || holder.isBlank()) {
            throw new IllegalArgumentException("Holder name cannot be blank");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.holder = holder;
        this.balance = balance;
    }

    public String getHolder() {
        return holder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Deposit cannot be negative");
        }
        balance += amount;
    }

    protected void changeBalance(double amount) {
        balance += amount;
    }

    public abstract void processMonth();

    public void printSummary() {
        System.out.printf("%s | Balance: %.2f kr%n", holder, balance);
    }
}

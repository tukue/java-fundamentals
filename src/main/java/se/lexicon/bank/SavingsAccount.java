package se.lexicon.bank;

public class SavingsAccount extends Account {
    private final double interestRate;

    public SavingsAccount(String holder, double balance, double interestRate) {
        super(holder, balance);
        this.interestRate = interestRate;
    }

    @Override
    public void processMonth() {
        changeBalance(getBalance() * interestRate);
        System.out.println(getHolder() + ": interest credited");
    }
}

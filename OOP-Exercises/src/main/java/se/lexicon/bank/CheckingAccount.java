package se.lexicon.bank;

public class CheckingAccount extends Account {
    private final double monthlyFee;

    public CheckingAccount(String holder, double balance, double monthlyFee) {
        super(holder, balance);
        this.monthlyFee = monthlyFee;
    }

    @Override
    public void processMonth() {
        changeBalance(-monthlyFee);
        System.out.println(getHolder() + ": monthly fee charged");
    }
}

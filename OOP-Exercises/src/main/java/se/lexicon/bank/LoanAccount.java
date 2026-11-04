package se.lexicon.bank;

public class LoanAccount extends Account {
    private final double loanRate;

    public LoanAccount(String holder, double balance, double loanRate) {
        super(holder, balance);
        this.loanRate = loanRate;
    }

    @Override
    public void processMonth() {
        changeBalance(getBalance() * loanRate);
        System.out.println(getHolder() + ": loan interest added");
    }
}

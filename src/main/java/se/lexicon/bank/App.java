package se.lexicon.bank;

public class App {
    public static void main(String[] args) {
        Bank bank = new Bank("Lexicon Bank");
        bank.addAccount(new SavingsAccount("Alex", 1_000, 0.02));
        bank.addAccount(new CheckingAccount("Smith", 500, 25));
        bank.addAccount(new LoanAccount("Alex", 5_000, 0.03));

        System.out.println("Starting balances:");
        bank.printAllSummaries();
        bank.processAllAccounts();
        System.out.println("Balances after month-end processing:");
        bank.printAllSummaries();
    }
}

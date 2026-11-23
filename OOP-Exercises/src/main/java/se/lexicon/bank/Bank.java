package se.lexicon.bank;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final String name;
    private final List<Account> accounts = new ArrayList<>();

    public Bank(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Bank name cannot be blank");
        }
        this.name = name;
    }

    public void addAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        accounts.add(account);
    }

    public void processAllAccounts() {
        System.out.println("Processing accounts at " + name + ":");
        for (Account account : accounts) {
            account.processMonth();
        }
    }

    public void printAllSummaries() {
        for (Account account : accounts) {
            account.printSummary();
        }
    }
}

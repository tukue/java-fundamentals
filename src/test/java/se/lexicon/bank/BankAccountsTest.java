package se.lexicon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankAccountsTest {
    @Test
    void savingsAccountEarnsInterest() {
        SavingsAccount account = new SavingsAccount("Alex", 1_000, 0.02);
        account.processMonth();
        assertEquals(1_020, account.getBalance(), 0.000001);
    }

    @Test
    void checkingAccountIsChargedItsMonthlyFee() {
        CheckingAccount account = new CheckingAccount("Smith", 500, 25);
        account.processMonth();
        assertEquals(475, account.getBalance(), 0.000001);
    }

    @Test
    void loanBalanceAccumulatesInterest() {
        LoanAccount account = new LoanAccount("Alex", 5_000, 0.03);
        account.processMonth();
        assertEquals(5_150, account.getBalance(), 0.000001);
    }

    @Test
    void depositAddsFundsAndRejectsNegativeAmounts() {
        SavingsAccount account = new SavingsAccount("Alex", 100, 0.02);
        account.deposit(50);
        assertEquals(150, account.getBalance(), 0.000001);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-1));
    }

    @Test
    void constructorsAndBankRejectInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> new SavingsAccount("  ", 100, 0.02));
        assertThrows(IllegalArgumentException.class, () -> new SavingsAccount("Alex", -1, 0.02));
        assertThrows(IllegalArgumentException.class, () -> new Bank(" "));
        Bank bank = new Bank("Lexicon Bank");
        assertThrows(IllegalArgumentException.class, () -> bank.addAccount(null));
    }

    @Test
    void bankProcessesEveryAccountType() {
        Bank bank = new Bank("Lexicon Bank");
        SavingsAccount savings = new SavingsAccount("Alex", 1_000, 0.02);
        CheckingAccount checking = new CheckingAccount("Smith", 500, 25);
        LoanAccount loan = new LoanAccount("Alex", 5_000, 0.03);
        bank.addAccount(savings);
        bank.addAccount(checking);
        bank.addAccount(loan);

        bank.processAllAccounts();

        assertEquals(1_020, savings.getBalance(), 0.000001);
        assertEquals(475, checking.getBalance(), 0.000001);
        assertEquals(5_150, loan.getBalance(), 0.000001);
    }
}

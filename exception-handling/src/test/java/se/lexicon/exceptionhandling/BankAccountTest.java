package se.lexicon.exceptionhandling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankAccountTest {
    @Test
    void depositAndWithdraw_updateBalance() throws InsufficientBalanceException {
        BankAccount account = new BankAccount(100.0);
        account.deposit(25.0);
        account.withdraw(40.0);
        assertEquals(85.0, account.getBalance());
    }

    @Test
    void withdraw_moreThanBalance_throwsCustomExceptionWithoutChangingBalance() {
        BankAccount account = new BankAccount(50.0);
        assertThrows(InsufficientBalanceException.class, () -> account.withdraw(60.0));
        assertEquals(50.0, account.getBalance());
    }
}

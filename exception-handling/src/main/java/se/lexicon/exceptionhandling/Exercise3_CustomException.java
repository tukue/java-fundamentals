package se.lexicon.exceptionhandling;

import java.util.Scanner;

public class Exercise3_CustomException {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankAccount account = new BankAccount(100.00);
        System.out.printf("Account balance: $%.2f%n", account.getBalance());
        System.out.print("Deposit amount (enter 0 to skip): ");
        double deposit = scanner.nextDouble();
        if (deposit > 0) {
            account.deposit(deposit);
        }

        System.out.print("Withdrawal amount: ");
        double withdrawal = scanner.nextDouble();
        try {
            account.withdraw(withdrawal);
            System.out.printf("Withdrawal successful. Balance: $%.2f%n", account.getBalance());
        } catch (InsufficientBalanceException exception) {
            System.out.println("Withdrawal declined: " + exception.getMessage());
            System.out.printf("Balance remains: $%.2f%n", account.getBalance());
        }
    }
}

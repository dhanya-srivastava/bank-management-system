package bank;

import bank.exceptions.InsufficientFundsException;
import bank.exceptions.InvalidAccountException;
import bank.exceptions.InvalidAmountException;
import bank.service.BankService;

import java.util.List;
import java.util.Scanner;

/**
 * Console entry point for the Bank Account Management System.
 * Presents a simple numbered menu and delegates all business logic
 * to BankService, keeping this class focused purely on I/O.
 */
public class BankApp {

    private final Scanner scanner = new Scanner(System.in);
    private final BankService bankService = new BankService();

    public static void main(String[] args) {
        new BankApp().run();
    }

    private void run() {
        System.out.println("=========================================");
        System.out.println(" BANK ACCOUNT MANAGEMENT SYSTEM");
        System.out.println("=========================================");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> createAccount();
                    case "2" -> deposit();
                    case "3" -> withdraw();
                    case "4" -> transfer();
                    case "5" -> viewAccount();
                    case "6" -> viewStatement();
                    case "7" -> listAllAccounts();
                    case "8" -> closeAccount();
                    case "0" -> {
                        running = false;
                        System.out.println("Thank you for using the Bank Management System. Goodbye!");
                    }
                    default -> System.out.println("Invalid option. Please choose a number from the menu.");
                }
            } catch (InvalidAccountException | InvalidAmountException | InsufficientFundsException e) {
                System.out.println("Operation failed: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer Funds");
        System.out.println("5. View Account Details");
        System.out.println("6. View Statement");
        System.out.println("7. List All Accounts");
        System.out.println("8. Close Account");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private void createAccount() throws Exception {
        System.out.print("New account number: ");
        String accNum = scanner.nextLine().trim();
        System.out.print("Owner ID: ");
        String ownerId = scanner.nextLine().trim();
        System.out.print("Owner name: ");
        String ownerName = scanner.nextLine().trim();
        System.out.print("Account type (SAVINGS/CURRENT): ");
        String type = scanner.nextLine().trim();
        System.out.print("Opening balance: ");
        double balance = Double.parseDouble(scanner.nextLine().trim());

        Account acc = bankService.createAccount(accNum, ownerId, ownerName, type, balance);
        System.out.println("Account created successfully:\n" + acc);
    }

    private void deposit() throws Exception {
        System.out.print("Account number: ");
        String accNum = scanner.nextLine().trim();
        System.out.print("Amount to deposit: ");
        double amount = Double.parseDouble(scanner.nextLine().trim());

        bankService.deposit(accNum, amount);
        System.out.println("Deposit successful. New balance: " + bankService.getAccount(accNum).getBalance());
    }

    private void withdraw() throws Exception {
        System.out.print("Account number: ");
        String accNum = scanner.nextLine().trim();
        System.out.print("Amount to withdraw: ");
        double amount = Double.parseDouble(scanner.nextLine().trim());

        bankService.withdraw(accNum, amount);
        System.out.println("Withdrawal successful. New balance: " + bankService.getAccount(accNum).getBalance());
    }

    private void transfer() throws Exception {
        System.out.print("From account number: ");
        String from = scanner.nextLine().trim();
        System.out.print("To account number: ");
        String to = scanner.nextLine().trim();
        System.out.print("Amount to transfer: ");
        double amount = Double.parseDouble(scanner.nextLine().trim());

        bankService.transfer(from, to, amount);
        System.out.println("Transfer successful.");
    }

    private void viewAccount() throws Exception {
        System.out.print("Account number: ");
        String accNum = scanner.nextLine().trim();
        System.out.println(bankService.getAccount(accNum));
    }

    private void viewStatement() throws Exception {
        System.out.print("Account number: ");
        String accNum = scanner.nextLine().trim();
        List<bank.Transaction> history = bankService.getStatement(accNum);
        if (history.isEmpty()) {
            System.out.println("No transactions found for this account yet.");
            return;
        }
        System.out.println("--- Statement for " + accNum + " ---");
        for (bank.Transaction t : history) {
            System.out.println(t);
        }
    }

    private void listAllAccounts() {
        List<Account> accounts = bankService.getAllAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        accounts.forEach(System.out::println);
    }

    private void closeAccount() throws Exception {
        System.out.print("Account number to close: ");
        String accNum = scanner.nextLine().trim();
        bankService.closeAccount(accNum);
        System.out.println("Account closed successfully.");
    }
}

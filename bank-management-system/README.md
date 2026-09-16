# Bank Account Management System

A console-based Bank Account Management System built in Java, using JDBC + MySQL for persistent storage. Built as a VITyarthi "Build Your Own Project" submission for **Programming in Java**.

## Overview

This system lets a bank manage customer accounts and process transactions safely from the command line. It demonstrates core Java concepts — inheritance and polymorphism (Savings/Current account types), custom checked exceptions, the Collections framework, JDBC transactions, and file-based logging — while enforcing account rules like minimum balance and overdraft limits.

## Features

- Create Savings or Current accounts with rule-specific validation
- Deposit and withdraw funds with input and balance validation
- Transfer funds between accounts as a single atomic (all-or-nothing) operation
- View individual account details and full transaction statements
- List and close accounts
- Every transaction is logged to `bank_transactions.log` with a timestamp
- Custom exceptions (`InsufficientFundsException`, `InvalidAccountException`, `InvalidAmountException`) provide clear, specific error messages

## Technologies / Tools Used

- Java 17+ (core language, no external frameworks)
- JDBC with MySQL for persistent storage
- `java.util.logging` for transaction logging
- Git for version control

## Project Structure

```
bank-management-system/
├── README.md
├── statement.md
├── src/
│   └── bank/
│       ├── Account.java
│       ├── SavingsAccount.java
│       ├── CurrentAccount.java
│       ├── Customer.java
│       ├── Transaction.java
│       ├── BankApp.java
│       ├── db/DBConnection.java
│       ├── service/BankService.java
│       └── exceptions/
│           ├── InsufficientFundsException.java
│           ├── InvalidAccountException.java
│           └── InvalidAmountException.java
├── resources/
│   └── schema.sql
├── diagrams/
└── docs/
    └── project-report.pdf
```

## Setup & Installation

### 1. Prerequisites
- JDK 17 or later
- MySQL Server (8.0+ recommended)
- [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) JAR file on your classpath

### 2. Set up the database
```bash
mysql -u root -p < resources/schema.sql
```

### 3. Configure the connection
Edit `src/bank/db/DBConnection.java` and update:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/bank_management_system?useSSL=false&serverTimezone=UTC";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password_here";
```

### 4. Compile
```bash
javac -d out -cp "path/to/mysql-connector-j-x.x.x.jar" $(find src -name "*.java")
```

### 5. Run
```bash
java -cp "out:path/to/mysql-connector-j-x.x.x.jar" bank.BankApp
```
(On Windows, use `;` instead of `:` in the classpath.)

## Testing Instructions

1. Start the app and choose **1. Create Account** — create a SAVINGS account with an opening balance above ₹500.
2. Choose **2. Deposit** and deposit some funds — confirm the balance updates.
3. Choose **3. Withdraw** an amount that would break the minimum balance rule — confirm it's rejected with a clear error.
4. Create a second account and use **4. Transfer Funds** to move money between them — confirm both balances update correctly.
5. Choose **6. View Statement** to confirm every transaction was logged with the correct running balance.
6. Check `bank_transactions.log` in the project root to confirm transactions were logged to file.

## Screenshots

*(Add screenshots of the menu, a successful transaction, and an error case here before submission.)*

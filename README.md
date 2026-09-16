# 🏦 Bank Account Management System

A console-based **Bank Account Management System** developed in **Java** using **JDBC and MySQL** for persistent data storage.

The system allows bank staff to create and manage customer accounts, perform deposits, withdrawals and transfers, and view transaction statements through a command-line interface.

---

## 📌 Project Overview

This project demonstrates important Java programming concepts including:

- Object-Oriented Programming
- Inheritance
- Polymorphism
- Custom Checked Exceptions
- Collections Framework
- JDBC
- MySQL Database
- JDBC Transactions
- File-Based Logging
- Input Validation

The system supports both **Savings** and **Current** accounts with account-specific rules.

---

## ✨ Features

- Create Savings and Current accounts
- View account details
- Deposit money
- Withdraw money
- Transfer money between accounts
- View transaction statements
- Close accounts
- Maintain transaction history
- Store account data using MySQL
- Log transactions to a file
- Validate account numbers and transaction amounts
- Handle insufficient funds using custom exceptions
- Use JDBC transactions with commit and rollback

---

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java 17+ | Application development |
| JDBC | Database connectivity |
| MySQL | Persistent data storage |
| Java Collections | In-memory data management |
| `java.util.logging` | Transaction logging |
| Git & GitHub | Version control |

---

## 📂 Project Structure

```text
bank-management-system/
│
├── README.md
├── statement.md
├── bank_transactions.log
│
├── src/
│   └── bank/
│       ├── Account.java
│       ├── SavingsAccount.java
│       ├── CurrentAccount.java
│       ├── Customer.java
│       ├── Transaction.java
│       ├── BankApp.java
│       │
│       ├── db/
│       │   └── DBConnection.java
│       │
│       ├── service/
│       │   └── BankService.java
│       │
│       └── exceptions/
│           ├── InsufficientFundsException.java
│           ├── InvalidAccountException.java
│           └── InvalidAmountException.java
│
├── resources/
│   └── schema.sql
│
├── diagrams/
│   └── architecture.png
│
└── docs/
    └── project-report.pdf



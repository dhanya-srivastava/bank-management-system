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


⚙️ Requirements

Before running the project, install:

JDK 17 or later
MySQL Server 8.0 or later
MySQL Connector/J
🗄️ Database Setup

Start MySQL Server and run:

mysql -u root -p < resources/schema.sql
🔐 Database Configuration

Open:

src/bank/db/DBConnection.java

Update your MySQL credentials:

private static final String DB_URL =
    "jdbc:mysql://localhost:3306/bank_management_system?useSSL=false&serverTimezone=UTC";

private static final String DB_USER = "root";

private static final String DB_PASSWORD = "your_password_here";

Do not upload your actual MySQL password to GitHub.

▶️ Compile

On Windows:

javac -d out -cp "path/to/mysql-connector-j-x.x.x.jar" src/bank/*.java src/bank/db/*.java src/bank/service/*.java src/bank/exceptions/*.java
🚀 Run
java -cp "out;path/to/mysql-connector-j-x.x.x.jar" bank.BankApp

On Linux/macOS, use : instead of ; in the classpath.

🧪 Testing

The application can be tested by performing:

Create a Savings or Current account.
Deposit money.
Withdraw money.
Transfer money between accounts.
View the transaction statement.
Check bank_transactions.log for transaction records.
💡 Java Concepts Demonstrated
Inheritance

SavingsAccount and CurrentAccount inherit common functionality from Account.

Polymorphism

Different account types apply their own banking rules.

Exception Handling

Custom checked exceptions are used:

InsufficientFundsException
InvalidAccountException
InvalidAmountException
Collections Framework

Collections are used for managing account and transaction data.

JDBC Transactions

Fund transfers use database transactions with commit and rollback to maintain consistency.

📝 Logging

Banking operations are recorded in:

bank_transactions.log

Example operations include:

DEPOSIT
WITHDRAWAL
TRANSFER
ACCOUNT CREATION
🎯 Objectives
Develop a functional banking system using Java.
Demonstrate Object-Oriented Programming.
Connect Java with MySQL using JDBC.
Implement safe financial transactions.
Apply exception handling and validation.
Maintain transaction history and logging.
⚠️ Limitations

The current system does not include:

Multi-currency accounts
Automatic interest calculation
Loan management
Web/mobile interface
Online customer authentication
🔮 Future Enhancements
Web-based banking interface
Mobile application
Customer authentication
Multi-factor authentication
Automatic interest calculation
Loan management
Email/SMS notifications
Improved security and encryption
👩‍💻 Project Information

Project: Bank Account Management System
Language: Java
Database: MySQL
Connectivity: JDBC
Interface: Console / Command Line
Purpose: Programming in Java Project

📜 License

This project is created for educational purposes.

### One important thing

Your existing README already has the correct project details, including the **features, Java/JDBC/MySQL technologies, project structure, setup instructions, and testing steps**. :contentReference[oaicite:0]{index=0} :contentReference[oaicite:1]{index=1}

After replacing your README with the above, run:

```powershell
git add README.md
git commit -m "Update README"
git push


<img width="1940" height="828" alt="Screenshot 2026-09-16 195013" src="https://github.com/user-attachments/assets/970d2503-576d-49f1-825c-d68e5b2d6e67" />

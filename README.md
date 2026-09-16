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
```
---
⚙️ Requirements

Before running the project, install:
JDK 17 or later
MySQL Server 8.0 or later
MySQL Connector/J
🗄️ Database Setup
Start MySQL Server and run:
mysql -u root -p < resources/schema.sql

---
🔐 Database Configuration

Open:
src/bank/db/DBConnection.java
Update your MySQL credentials:
private static final String DB_URL =
    "jdbc:mysql://localhost:3306/bank_management_system?useSSL=false&serverTimezone=UTC";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password_here";
Do not upload your actual MySQL password to GitHub.

---
▶️ Compile

On Windows:
javac -d out -cp "path/to/mysql-connector-j-x.x.x.jar" src/bank/*.java src/bank/db/*.java src/bank/service/*.java src/bank/exceptions/*.java

---
🚀 Run
java -cp "out;path/to/mysql-connector-j-x.x.x.jar" bank.BankApp
On Linux/macOS, use : instead of ; in the classpath.

---
🧪 Testing

The application can be tested by performing:
-Create a Savings or Current account.
-Deposit money.
-Withdraw money.
-Transfer money between accounts.
-View the transaction statement.
-Check bank_transactions.log for transaction records.

---
💡 Java Concepts Demonstrated

-Inheritance
-SavingsAccount and CurrentAccount inherit common functionality from Account.
-Polymorphism
-Different account types apply their own banking rules.
-Exception Handling
-Custom checked exceptions are used:
-InsufficientFundsException
-InvalidAccountException
-InvalidAmountException
-Collections Framework
-Collections are used for managing account and transaction data.
-JDBC Transactions
-Fund transfers use database transactions with commit and rollback to maintain consistency.

---
📝 Logging

-Banking operations are recorded in:
-bank_transactions.log

Example operations include:
-DEPOSIT
-WITHDRAWAL
-TRANSFER
-ACCOUNT CREATION

---
🎯 Objectives
-Develop a functional banking system using Java.
-Demonstrate Object-Oriented Programming.
-Connect Java with MySQL using JDBC.
-Implement safe financial transactions.
-Apply exception handling and validation.
-Maintain transaction history and logging.

---
⚠️ Limitations

The current system does not include:
-Multi-currency accounts
-Automatic interest calculation
-Loan management
-Web/mobile interface
-Online customer authentication

---
🔮 Future Enhancements

-Web-based banking interface
-Mobile application
-Customer authentication
-Multi-factor authentication
-Automatic interest calculation
-Loan management
-Email/SMS notifications
-Improved security and encryption

---
👩‍💻 Project Information

-Project: Bank Account Management System
-Language: Java
-Database: MySQL
-Connectivity: JDBC
-Interface: Console / Command Line
-Purpose: Programming in Java Project

---
📜 License

This project is created for educational purposes.

### One important thing

Your existing README already has the correct project details, including the **features, Java/JDBC/MySQL technologies, project structure, setup instructions, and testing steps**. :contentReference[oaicite:0]{index=0} :contentReference[oaicite:1]{index=1}

After replacing your README with the above, run:

```powershell
git add README.md
git commit -m "Update README"
git push
```
---
## 👨‍💻 Author

Dhanya Srivastava  
B.Tech AI & ML  

---
## 📖 References
-Oracle Java Documentation – Java Programming and JDBC concepts.
-MySQL Documentation – MySQL database and SQL concepts.
-MySQL Connector/J Documentation – Java connectivity with MySQL.
-VITyarthi – Build Your Own Project General Project Instructions and Submission Guidelines.
-Project source code, schema.sql, README.md, and statement.md.

--- 
<img width="1940" height="828" alt="Screenshot 2026-09-16 195013" src="https://github.com/user-attachments/assets/9078555b-95a8-4bd9-abd4-91625bbc8a00" />
<img width="816" height="418" alt="Screenshot 2026-09-16 194938" src="https://github.com/user-attachments/assets/901de710-822d-4294-8d84-fcba2f8e5555" />
<img width="1220" height="526" alt="Screenshot 2026-09-16 194721" src="https://github.com/user-attachments/assets/ab63e90a-0be0-4df7-a9b2-74975ed2caac" />
<img width="2046" height="634" alt="Screenshot 2026-09-16 194659" src="https://github.com/user-attachments/assets/fa921b5b-d398-485d-a57c-8932bb514b63" />
<img width="1316" height="524" alt="Screenshot 2026-09-16 194633" src="https://github.com/user-attachments/assets/291e5219-cb9a-4411-b470-c286b49a361e" />
<img width="1324" height="678" alt="Screenshot 2026-09-16 194605" src="https://github.com/user-attachments/assets/1777e952-578e-4211-a2bf-0541df51697b" />
<img width="1398" height="644" alt="Screenshot 2026-09-16 194527" src="https://github.com/user-attachments/assets/fc684506-752c-49e8-84a3-2bb8d1ad5f1d" />
<img width="1348" height="640" alt="Screenshot 2026-09-16 194459" src="https://github.com/user-attachments/assets/85b35508-ef6d-400e-8657-ab8cc9d47fa8" />
<img width="1306" height="922" alt="Screenshot 2026-09-16 194359" src="https://github.com/user-attachments/assets/4c9888d3-dbc0-495d-b585-4911dd7337c6" />
<img width="2338" height="786" alt="Screenshot 2026-09-16 193934" src="https://github.com/user-attachments/assets/f578bae3-922c-4851-b3b3-d731c011a6be" />
<img width="766" height="658" alt="Screenshot 2026-09-16 193235" src="https://github.com/user-attachments/assets/f798927c-2fd2-4cb5-9ae4-942aba4d795e" />
<img width="1498" height="828" alt="Screenshot 2026-09-16 192910" src="https://github.com/user-attachments/assets/914f93b2-9ab9-4b24-95f9-e45a3f53cb73" />
<img width="1870" height="642" alt="Screenshot 2026-09-16 192801" src="https://github.com/user-attachments/assets/d4a0de14-858e-4ae6-a650-ed29eb65ce81" />
<img width="1372" height="454" alt="Screenshot 2026-09-16 192720" src="https://github.com/user-attachments/assets/44d6887d-e866-45fd-b683-2e57c34ede0a" />
<img width="1472" height="850" alt="Screenshot 2026-09-16 192318" src="https://github.com/user-attachments/assets/9ca02626-c994-4898-92f0-61f4249944d1" />



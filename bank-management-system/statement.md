# Problem Statement

Manual or fragmented bank record-keeping leads to errors in balance tracking, slow transaction processing, and poor auditability. This project builds a console-based **Bank Account Management System** in Java that lets a bank manage customer accounts, process transactions (deposit, withdraw, transfer) safely, and generate statements — enforcing validation and consistency that manual processes cannot guarantee.

## Scope

The system covers:
- Account creation and closure for two account types (Savings, Current)
- Deposit, withdrawal, and inter-account transfer operations, each validated against account-specific rules (minimum balance for Savings, overdraft limit for Current)
- Transaction history and statement generation, backed by persistent MySQL storage
- Transaction-level logging to a local log file for auditability

The system does **not** cover: multi-currency accounts, interest auto-crediting, loan management, or a web/mobile front end — these are noted as future enhancements.

## Target Users

- **Bank Admin/Staff** — creates and manages customer accounts, processes transactions, views all account activity
- **Customer** (represented via staff-operated console in this version) — the account holder whose balance and transaction history the system tracks

## High-Level Features

- Create, view, and close Savings or Current accounts
- Deposit and withdraw funds with rule-based validation
- Atomic fund transfers between accounts (commit/rollback via JDBC transactions)
- Full transaction statement per account
- Persistent storage via MySQL, transaction logging via `java.util.logging`
- Clear, specific error handling through custom checked exceptions

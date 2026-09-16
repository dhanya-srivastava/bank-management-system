-- Bank Account Management System - Database Schema
-- Run this once against your MySQL server before starting the app.

CREATE DATABASE IF NOT EXISTS bank_management_system;
USE bank_management_system;

CREATE TABLE IF NOT EXISTS accounts (
    account_number  VARCHAR(20)  PRIMARY KEY,
    owner_id        VARCHAR(20)  NOT NULL,
    owner_name      VARCHAR(100) NOT NULL,
    balance         DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    account_type    ENUM('SAVINGS', 'CURRENT') NOT NULL,
    active          BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transactions (
    transaction_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number  VARCHAR(20) NOT NULL,
    type            ENUM('DEPOSIT', 'WITHDRAWAL', 'TRANSFER_IN', 'TRANSFER_OUT') NOT NULL,
    amount          DECIMAL(15,2) NOT NULL,
    balance_after   DECIMAL(15,2) NOT NULL,
    created_at      TIMESTAMP NOT NULL,
    FOREIGN KEY (account_number) REFERENCES accounts(account_number)
);

-- Optional: a couple of sample rows to test with immediately
-- INSERT INTO accounts (account_number, owner_id, owner_name, balance, account_type, active)
-- VALUES ('ACC1001', 'CUST01', 'Aditi Sharma', 10000.00, 'SAVINGS', TRUE);

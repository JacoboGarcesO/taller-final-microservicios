CREATE SCHEMA IF NOT EXISTS sch_bank;
CREATE SCHEMA IF NOT EXISTS sch_account;
CREATE SCHEMA IF NOT EXISTS sch_transaction;
CREATE SCHEMA IF NOT EXISTS sch_transfer;

CREATE TABLE IF NOT EXISTS sch_bank.banks (
    bank_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS sch_transaction.transactions (
    transaction_id SERIAL PRIMARY KEY,
    account_number VARCHAR(100) NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    currency VARCHAR(10) NOT NULL,
    status VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS sch_account.accounts (
    account_id SERIAL PRIMARY KEY,
    account_number VARCHAR(100) NOT NULL,
    account_type VARCHAR(50) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    balance DOUBLE PRECISION NOT NULL,
    bank_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS sch_transfer.transfers (
    transfer_id SERIAL PRIMARY KEY,
    source_account_number VARCHAR(100) NOT NULL,
    destination_account_number VARCHAR(100) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    currency VARCHAR(10) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
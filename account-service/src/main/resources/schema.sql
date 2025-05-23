CREATE TABLE IF NOT EXISTS accounts (
    account_number BIGINT AUTO_INCREMENT PRIMARY KEY,
    bank_id BIGINT NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
    status VARCHAR(20) NOT NULL
);

INSERT INTO accounts(bank_id, account_type, balance, status) VALUES (1, 'AHORROS', 100000.00, 'active');
INSERT INTO accounts(bank_id, account_type, balance, status) VALUES (2, 'CORRIENTE', 250000.00, 'active');
INSERT INTO accounts(bank_id, account_type, balance, status) VALUES (3, 'AHORROS', 50000.00, 'blocked');
INSERT INTO accounts(bank_id, account_type, balance, status) VALUES (1, 'CORRIENTE', 75000.00, 'active');
INSERT INTO accounts(bank_id, account_type, balance, status) VALUES (4, 'AHORROS', 120000.00, 'closed');
INSERT INTO accounts(bank_id, account_type, balance, status) VALUES (3, 'AHORROS', 50000.00, 'active');
INSERT INTO accounts(bank_id, account_type, balance, status) VALUES (1, 'CORRIENTE', 75000.00, 'active');
INSERT INTO accounts(bank_id, account_type, balance, status) VALUES (4, 'AHORROS', 120000.00, 'active');

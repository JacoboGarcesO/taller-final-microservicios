CREATE TABLE IF NOT EXISTS accounts (
    account_number BIGINT AUTO_INCREMENT PRIMARY KEY,
    bank_id BIGINT NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
    status VARCHAR(20) NOT NULL
);

INSERT INTO accounts(bank_id, account_type, balance, status) VALUES (1, 'AHORROS', 1000000.00, 'active');
INSERT INTO accounts(bank_id, account_type, balance, status) VALUES (2, 'AHORROS', 2000000.00, 'active');
INSERT INTO accounts(bank_id, account_type, balance, status) VALUES (3, 'AHORROS', 3000000.00, 'active');
INSERT INTO accounts(bank_id, account_type, balance, status) VALUES (1, 'CORRIENTE', 4000000.00, 'active');
INSERT INTO accounts(bank_id, account_type, balance, status) VALUES (4, 'CORRIENTE', 5000000.00, 'active');
INSERT INTO accounts(bank_id, account_type, balance, status) VALUES (3, 'CORRIENTE', 6000000.00, 'closed');
INSERT INTO accounts(bank_id, account_type, balance, status) VALUES (1, 'CORRIENTE', 70000000.00, 'closed');
INSERT INTO accounts(bank_id, account_type, balance, status) VALUES (4, 'AHORROS', 1200000.00, 'blocked');
INSERT INTO accounts(bank_id, account_type, balance, status) VALUES (3, 'CORRIENTE', 61000000.00, 'closed');
INSERT INTO accounts(bank_id, account_type, balance, status) VALUES (1, 'CORRIENTE', 70000000.00, 'closed');
INSERT INTO accounts(bank_id, account_type, balance, status) VALUES (4, 'AHORROS', 80000000.00, 'blocked');

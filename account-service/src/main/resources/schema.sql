CREATE TABLE IF NOT EXISTS accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(50) NOT NULL,
    bank_id BIGINT NOT NULL,
    balance DECIMAL(19, 2) NOT NULL
);

INSERT INTO accounts (number, bank_id, balance) VALUES ('1234567890', 1, 1000.00);
INSERT INTO accounts (number, bank_id, balance) VALUES ('9876543210', 2, 500.50);
INSERT INTO accounts (number, bank_id, balance) VALUES ('4567891230', 1, 120000.99);
INSERT INTO accounts (number, bank_id, balance) VALUES ('4567891230', 3, 620000.99);


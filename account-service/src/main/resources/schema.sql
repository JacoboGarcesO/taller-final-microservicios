CREATE TABLE IF NOT EXISTS accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bank_id BIGINT NOT NULL,
    type VARCHAR(50) NOT NULL,
    number VARCHAR(200) NULL
);

INSERT INTO accounts(bank_id, type, number) VALUES (1, 'Ahorros', '10450001');
INSERT INTO accounts(bank_id, type, number) VALUES (1, 'Corriente', '10450002');
INSERT INTO accounts(bank_id, type, number) VALUES (2, 'Ahorros', '10450003');
INSERT INTO accounts(bank_id, type, number) VALUES (3, 'Ahorros', '10450004');
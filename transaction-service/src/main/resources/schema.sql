CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL,  -- 'deposito' o 'retiro'
    amount DECIMAL(19, 2) NOT NULL,
    date TIMESTAMP NOT NULL
);
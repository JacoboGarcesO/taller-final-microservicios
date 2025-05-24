CREATE TABLE IF NOT EXISTS cuentas (
    numero_cuenta BIGINT AUTO_INCREMENT PRIMARY KEY,
    banco_id BIGINT NOT NULL,
    tipo_cuenta VARCHAR(20) NOT NULL,
    saldo DECIMAL(12, 4) NOT NULL DEFAULT 0.00,
    saldo_sobregiro DECIMAL(12, 4) DEFAULT 0.00,
    estado VARCHAR(2) NOT NULL
);

INSERT INTO cuentas (banco_id, tipo_cuenta, saldo, saldo_sobregiro, estado) VALUES ( 1, 'Ahorros', 1500.00, 0.00, ' A');

INSERT INTO cuentas (banco_id, tipo_cuenta, saldo, saldo_sobregiro, estado) VALUES ( 2, 'Corriente', 500.00, 200.00, ' A');

INSERT INTO cuentas (banco_id, tipo_cuenta, saldo, saldo_sobregiro, estado) VALUES ( 1, 'Ahorros', 250.75, 0.00, ' I');

INSERT INTO cuentas (banco_id, tipo_cuenta, saldo, saldo_sobregiro, estado) VALUES ( 3, 'Corriente', 10000.00, 1000.00, ' A');

INSERT INTO cuentas (banco_id, tipo_cuenta, saldo, saldo_sobregiro, estado) VALUES ( 2, 'Ahorros', 300.00, 0.00, ' C');

INSERT INTO cuentas (banco_id, tipo_cuenta, saldo, saldo_sobregiro, estado) VALUES ( 3, 'Corriente', 0.00, 500.00, ' E');
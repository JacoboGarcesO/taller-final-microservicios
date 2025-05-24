CREATE TABLE IF NOT EXISTS transacciones (
    numero_rastreo BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_cuenta VARCHAR(20) NOT NULL,
    banco BIGINT NOT NULL,
    cuenta BIGINT NOT NULL,
    tipo_transaccion CHAR(1) NOT NULL,
    monto DECIMAL(12, 4) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
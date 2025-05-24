CREATE TABLE IF NOT EXISTS banks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(200) NULL
);

INSERT INTO banks(name, address) VALUES ('Bancolombia', 'Cll3 No 100');
INSERT INTO banks(name, address) VALUES ('Banco de bogota', 'Primera diagonal cll 233');
INSERT INTO banks(name, address) VALUES ('Davivienda', 'Clla 2 crra 73 No 23 -80');
INSERT INTO banks(name, address) VALUES ('Nu', 'CC. Santa Fe local 120');
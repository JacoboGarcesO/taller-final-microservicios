CREATE TABLE IF NOT EXISTS banks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(20) NOT NULL UNIQUE,
    address VARCHAR(200),
    phone VARCHAR(20),
    country VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO banks(name, code, address, phone, country) VALUES ('Banco Nacional', 'BN001', 'Av. Central 123, Ciudad', '+57 123456789', 'Colombia');
INSERT INTO banks(name, code, address, phone, country) VALUES ('Banco del Pacífico', 'BP002', 'Calle 45 #67-89, Medellín', '+57 234567890', 'Colombia');
INSERT INTO banks(name, code, address, phone, country) VALUES ('Banco Andino', 'BA003', 'Cra. 10 #20-30, Bogotá', '+57 345678901', 'Colombia');
INSERT INTO banks(name, code, address, phone, country) VALUES ('Banco Universal', 'BU004', 'Calle 50 #15-20, Cali', '+57 456789012', 'Colombia');
INSERT INTO banks(name, code, address, phone, country) VALUES ('Banco Popular', 'BP005', 'Av. Libertad 200, Barranquilla', '+57 567890123', 'Colombia');
INSERT INTO banks(name, code, address, phone, country) VALUES ('Banco de Occidente', 'BO006', 'Calle 80 #30-40, Cartagena', '+57 678901234', 'Colombia');
INSERT INTO banks(name, code, address, phone, country) VALUES ('Banco Central', 'BC007', 'Av. Sur 300, Bucaramanga', '+57 789012345', 'Colombia');
INSERT INTO banks(name, code, address, phone, country) VALUES ('Banco Express', 'BE008', 'Cra. 5 #60-70, Pereira', '+57 890123456', 'Colombia');
INSERT INTO banks(name, code, address, phone, country) VALUES ('Banco Digital', 'BD009', 'Calle 100 #25-35, Manizales', '+57 901234567', 'Colombia');
INSERT INTO banks(name, code, address, phone, country) VALUES ('Banco Futuro', 'BF010', 'Av. Norte 400, Armenia', '+57 912345678', 'Colombia');
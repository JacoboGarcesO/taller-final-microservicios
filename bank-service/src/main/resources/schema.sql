CREATE TABLE IF NOT EXISTS banks (
    bank_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(200) NOT NULL
);

INSERT INTO banks(name, description) VALUES
('Bancolombia', 'Líder en servicios financieros.'),
('Davivienda', 'Famoso por su casa roja y su app.'),
('BBVA', 'Banco digital con presencia global.'),
('Banco Falabella', 'Banco retail con foco en consumo.'),
('Itaú', 'Banco digital de origen brasileño.'),
('Banco de Bogotá', 'Uno de los bancos más antiguos del país.'),
('Banco AV Villas', 'Parte del Grupo Aval, con enfoque digital.'),
('Banco Popular', 'Enfocado en créditos de libranza.'),
('Banco Caja Social', 'Especializado en banca social.'),
('Banco Agrario', 'Apoya al sector agropecuario.'),
('Scotiabank Colpatria', 'Multinacional con amplia cobertura.'),
('Finandina', 'Especialista en créditos y consumo.'),
('Banco Pichincha', 'Filial ecuatoriana con servicios personales.'),
('Coomeva', 'Cooperativa financiera con servicios bancarios.'),
('Serfinanza', 'Banco aliado de Olímpica con foco comercial.');
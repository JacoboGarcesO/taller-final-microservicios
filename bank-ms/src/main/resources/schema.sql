CREATE TABLE IF NOT EXISTS banks (
    bank_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    nit VARCHAR(20) NOT NULL,
    description VARCHAR(100) NOT NULL
);

INSERT INTO banks(name, nit, description) VALUES
('Bancolombia S.A.', '900123456-7', 'El banco líder en servicios financieros integrales en Colombia.'),
('Banco Davivienda S.A.', '900234567-8', 'Reconocido por su emblemática casa roja y su innovadora aplicación móvil.'),
('Banco Bilbao Vizcaya Argentaria (BBVA) Colombia', '900345678-9', 'Banco digital con presencia global y soluciones financieras modernas.'),
('Banco Falabella S.A.', '900456789-0', 'Banco retail enfocado en productos de consumo y crédito personal.'),
('Itaú Corpbanca Colombia', '900567890-1', 'Banco digital de origen brasileño con amplia oferta financiera.'),
('Banco de Bogotá S.A.', '900678901-2', 'Una de las instituciones financieras más antiguas y sólidas del país.'),
('Banco AV Villas', '900789012-3', 'Parte del Grupo Aval, con enfoque en banca digital y servicios integrales.'),
('Banco Popular S.A.', '900890123-4', 'Especialista en créditos de libranza y soluciones para empleados públicos.'),
('Banco Caja Social S.A.', '900901234-5', 'Institución enfocada en banca social y microfinanzas.'),
('Banco Agrario de Colombia S.A.', '900012345-6', 'Apoya el desarrollo del sector agropecuario y rural.'),
('Scotiabank Colpatria S.A.', '900123450-1', 'Multinacional con amplia cobertura y servicios para personas y empresas.'),
('Finandina S.A.', '900234560-2', 'Especialista en créditos para consumo y soluciones financieras.'),
('Banco Pichincha Colombia S.A.', '900345670-3', 'Filial del banco ecuatoriano con servicios personalizados.'),
('Coomeva Financiera', '900456780-4', 'Cooperativa financiera con amplia gama de servicios bancarios.'),
('Serfinanza S.A.', '900567890-5', 'Banco aliado de Olímpica, con foco en comercio y crédito.'),
('Banco GNB Sudameris Colombia', '900678900-6', 'Banco especializado en banca comercial y empresarial.'),
('Banco Caja Social', '900789000-7', 'Fuerte presencia en microcréditos y desarrollo social.'),
('Banco Colpatria S.A.', '900890000-8', 'Banco que ofrece soluciones financieras para personas y empresas.'),
('Bancoomeva', '900901000-9', 'Cooperativa con servicios financieros integrales en todo el país.'),
('Banco de Occidente', '900012300-0', 'Parte del Grupo Aval, con soluciones para banca personal y empresarial.');

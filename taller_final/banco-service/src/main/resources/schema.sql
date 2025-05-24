CREATE TABLE IF NOT EXISTS bancos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(200) NOT NULL,
    country VARCHAR(50) NOT NULL
);

INSERT INTO bancos(name, description, country) VALUES ('Bancolombia','Somos un grupo financiero líder, con más de 146 años de experiencia y que evoluciona para asumir los desafíos que trae un entorno en constante transformación.', 'Colombia');
INSERT INTO bancos(name, description, country) VALUES ('NU','Banco NU', 'Colombia');
INSERT INTO bancos(name, description, country) VALUES ('Occidente','Banco de occidente', 'Colombia');
INSERT INTO bancos(name, description, country) VALUES ('BBVA','Banco BBVA', 'España');
INSERT INTO bancos(name, description, country) VALUES ('Pichincha','Banco pichincha', 'Ecuador');
INSERT INTO bancos(name, description, country) VALUES ('CityBank','Banco de Nueva York', 'Estados Unidos');
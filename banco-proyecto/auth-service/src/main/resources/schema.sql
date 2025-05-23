CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(200) NOT NULL,


);

INSERT INTO usuario (username, password) VALUES ('admin', 'nn565');
INSERT INTO usuario (username, password) VALUES ('user', 'kk768');
INSERT INTO usuario (username, password) VALUES ('admin', 'll87');
INSERT INTO usuario (username, password) VALUES ('user', 'mm1234');

CREATE database museu_db;

USE museu_db;

CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL CHECK (CHAR_LENGTH(nome) BETWEEN 3 AND 255),
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario ENUM('administrador', 'aluno') NOT NULL,
    CONSTRAINT chk_email CHECK (email LIKE '%_@__%.__%')
);

CREATE TABLE personagens (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL CHECK (CHAR_LENGTH(nome) BETWEEN 3 AND 100),
    biografia TEXT NOT NULL CHECK (CHAR_LENGTH(biografia) <= 1000),
    tipo_personagem ENUM('artista_plastico', 'artista_popular', 'escritor', 'politico', 'governante', 
                         'cientista', 'militar', 'ativista', 'religioso', 'educador', 'empresario', 
                         'explorador', 'heroi_folclorico') NOT NULL,
    imagem_url VARCHAR(255)
);

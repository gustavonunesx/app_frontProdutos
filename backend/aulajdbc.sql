CREATE DATABASE IF NOT EXISTS aulajdbc;
USE aulajdbc;

DROP TABLE IF EXISTS produtos;
DROP TABLE IF EXISTS categoria;

CREATE TABLE categoria (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE produtos (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    estoque INT NOT NULL,
    categoria_id INT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

INSERT INTO produtos (nome, preco, estoque, categoria_id)
VALUES ('SmartPhone', 1500.00, 25, 1);


INSERT INTO categoria (nome)
VALUES ('Eletrônicos'), ('Periféricos'), ('Informática');



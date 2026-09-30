CREATE DATABASE IF NOT EXISTS estacionamento;
USE estacionamento;

CREATE TABLE IF NOT EXISTS veiculo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(10) NOT NULL UNIQUE,
    modelo VARCHAR(100) NOT NULL,
    valor_hora DECIMAL(10,2) NOT NULL,
    horas_estacionado INT NOT NULL DEFAULT 0,
    estacionado BOOLEAN NOT NULL DEFAULT FALSE,
    tipo VARCHAR(20) NOT NULL
);

SELECT * FROM veiculo;





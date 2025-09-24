CREATE DATABASE dbAnimais;
USE dbAnimais;

-- área perigosa
drop table tbAnimais;
drop table tbPredacao;

CREATE TABLE tbAnimais (
    nomeAnimal VARCHAR(30) PRIMARY KEY,
    evolucao VARCHAR(30), -- nome Animal apenas uma evolução
    evoluirPontos INT, -- pontos necessário para evoluir
    FOREIGN KEY (evolucao) REFERENCES tbAnimais(nomeAnimal)
);

CREATE TABLE tbPredacao (
	idPredacao INT PRIMARY KEY AUTO_INCREMENT,
    nomePredador VARCHAR(30) NOT NULL,
    nomePresa VARCHAR(30) NOT NULL,
    pontosEvolucao INT NOT NULL,
    FOREIGN KEY (nomePredador) REFERENCES tbAnimais(nomeAnimal),
    FOREIGN KEY (nomePresa) REFERENCES tbAnimais(nomeAnimal)
);

SELECT * FROM tbAnimais;
SELECT * FROM tbPredacao;

-- insert de animais
INSERT tbAnimais (nomeAnimal) values
('gorila'),
('avestruz'),
('leão'),
('elefante africano')
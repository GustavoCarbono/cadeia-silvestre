CREATE DATABASE dbAnimais;
USE dbAnimais;

-- área perigosa
drop table tbAnimais;
drop table tbPredacao;

CREATE TABLE tbAnimais (
    nomeAnimal VARCHAR(30) PRIMARY KEY,
    evolucao VARCHAR(30), -- nome Animal apenas uma evolução
    evoluirPontos INT, -- pontos necessário para evoluir
    nivel INT NOT NULL,
    img VARCHAR(60),
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
INSERT tbAnimais (nomeAnimal, evolucao, evoluirPontos, nivel, img) values
('macaco', 'babuíno', 50, 1, '/images/AnimaisPrincipais/babuíno.png'),
('ema', 'avestruz', 50, 1, '/images/AnimaisPrincipais/avestruz.png'),
('puma', 'hiena', 50, 1, '/images/AnimaisPrincipais/hiena.png'),
('Elefante-Pigmeu-de-Bornéu', 'elefante africano', 50, 1, '/images/AnimaisPrincipais/elefanteAfricano.png');

INSERT tbAnimais (nomeAnimal, evolucao, evoluirPontos, nivel, img) values
('babuíno', 'gorila', 100, 2, '/images/AnimaisPrincipais/babuíno.png'),
('avestruz', 'aepyornis', 100, 2, '/images/AnimaisPrincipais/avestruz.png'),
('hiena', 'leão', 100, 2, '/images/AnimaisPrincipais/hiena.png'),
('elefante africano', 'mamute', 100, 2, '/images/AnimaisPrincipais/elefanteAfricano.png');

INSERT tbAnimais (nomeAnimal, nivel) values
('gorila', 10),
('aepyornis', 10),
('leão', 10),
('mamute', 10);

-- Presas
INSERT INTO tbAnimais (nomeAnimal, nivel, img) VALUES
('cavalo', 0, '/images/AnimaisSegundários/cavalo.png'),
('cervo', 0, '/images/AnimaisSegundários/cervo.png'),
('cobra', 0, '/images/AnimaisSegundários/cobra.png'),
('coelho', 0, '/images/AnimaisSegundários/coelho.png'),
('esquilo', 0, '/images/AnimaisSegundários/esquilo.png'),
('gafanhoto', 0, '/images/AnimaisSegundários/gafanhoto.png'),
('galinha', 0, '/images/AnimaisSegundários/galinha.png'),
('lagarta', 0, '/images/AnimaisSegundários/lagarta.png'),
('lagarto', 0, '/images/AnimaisSegundários/lagarto.png'),
('matinhodois', 0, '/images/AnimaisSegundários/matinhodois.png'),
('matinhoum', 0, '/images/AnimaisSegundários/matinhoum.png'),
('minhoca', 0, '/images/AnimaisSegundários/minhoca.png'),
('muda', 0, '/images/AnimaisSegundários/muda.png'),
('ovelha', 0, '/images/AnimaisSegundários/ovelha.png'),
('pombo', 0, '/images/AnimaisSegundários/pombo.png'),
('porco', 0, '/images/AnimaisSegundários/porco.png'),
('sapo', 0, '/images/AnimaisSegundários/sapo.png'),
('tartaruga', 0, '/images/AnimaisSegundários/tartaruga.png'),
('tatu', 0, '/images/AnimaisSegundários/tatu.png'),
('urso', 0, '/images/AnimaisSegundários/urso.png');

-- macaco
INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('macaco', 'esquilo', 10),
('macaco', 'muda', 10),
('macaco', 'cobra', 10),
('macaco', 'gafanhoto', 10),
('macaco', 'lagarta', 10);

-- ema
INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('ema', 'pombo', 10),
('ema', 'gafanhoto', 10),
('ema', 'lagarta', 10),
('ema', 'tatu', 10),
('ema', 'minhoca', 10);

-- puma
INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('puma', 'coelho', 10),
('puma', 'cervo', 10),
('puma', 'tatu', 10),
('puma', 'ovelha', 10),
('puma', 'sapo', 10);

-- Elefante-Pigmeu-de-Bornéu
INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('Elefante-Pigmeu-de-Bornéu', 'galinha', 10),
('Elefante-Pigmeu-de-Bornéu', 'minhoca', 10),
('Elefante-Pigmeu-de-Bornéu', 'tatu', 10),
('Elefante-Pigmeu-de-Bornéu', 'lagarta', 10),
('Elefante-Pigmeu-de-Bornéu', 'pombo', 10);
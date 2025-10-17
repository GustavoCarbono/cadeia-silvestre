CREATE DATABASE dbAnimais;
USE dbAnimais;



SELECT * FROM tbAnimais;
SELECT * FROM tbPredacao;


CREATE TABLE tbAnimais (
    nomeAnimal VARCHAR(30) PRIMARY KEY,
    evolucao VARCHAR(30), -- nome Animal apenas uma evolução
    evoluirPontos INT, -- pontos necessário para evoluir
    nivel INT NOT NULL,
    img VARCHAR(60),
    FOREIGN KEY (evolucao) REFERENCES tbAnimais(nomeAnimal)
);

INSERT INTO tbAnimais (nomeAnimal, nivel, img) VALUES
('TRex', 5, '/images/AnimaisPrincipais/gen4/trex.png'),
('Fenix', 5, '/images/AnimaisPrincipais/gen4/fenix.png'),
('Leão', 5, '/images/AnimaisPrincipais/gen4/leao.png'),
('Lobo-Terrivel', 5, '/images/AnimaisPrincipais/gen4/lobo-terrivel.png');

INSERT INTO tbAnimais (nomeAnimal, evolucao, evoluirPontos, nivel, img) VALUES
('Crocodilo', 'TRex', 40, 4, '/images/AnimaisPrincipais/gen3/crocodilo.png'),
('Harpia', 'Fenix', 40, 4, '/images/AnimaisPrincipais/gen3/harpia.png'),
('Jaguar', 'Leão', 40, 4, '/images/AnimaisPrincipais/gen3/jaguar.png'),
('Lobo', 'Lobo-Terrível', 40, 4, '/images/AnimaisPrincipais/gen3/lobo.png');

INSERT INTO tbAnimais (nomeAnimal, evolucao, evoluirPontos, nivel, img) VALUES
('Serpente', 'Crocodilo', 30, 3, '/images/AnimaisPrincipais/gen2/serpente.png'),
('Aguia', 'Harpia', 30, 3, '/images/AnimaisPrincipais/gen2/aguia.png'),
('Raposa', 'Jaguar', 30, 3, '/images/AnimaisPrincipais/gen2/raposa.png'),
('Coiote', 'Lobo', 30, 3, '/images/AnimaisPrincipais/gen2/coiote.png');

INSERT INTO tbAnimais (nomeAnimal, evolucao, evoluirPontos, nivel, img) VALUES
('Teiu', 'Serpente', 30, 2, '/images/AnimaisPrincipais/gen1/teiu.png'),
('Corvo', 'Aguia', 30, 2, '/images/AnimaisPrincipais/gen1/corvo.png'),
('Doninha', 'Raposa', 30, 2, '/images/AnimaisPrincipais/gen1/doninha.png'),
('Feneco', 'Coiote', 30, 2, '/images/AnimaisPrincipais/gen1/feneco.png');


INSERT INTO tbAnimais (nomeAnimal, evolucao, evoluirPontos, nivel, img) VALUES
('Camaleão', 'Teiu', 20, 1, '/images/AnimaisPrincipais/gen0/camaleao.png'),
('Beija-flor', 'Corvo', 20, 1, '/images/AnimaisPrincipais/gen0/beija-flor.png'),
('Rato', 'Doninha', 20, 1, '/images/AnimaisPrincipais/gen0/rato.png'),
('Dormouse', 'Feneco', 20, 1, '/images/AnimaisPrincipais/gen0/dormouse.png');

-- Presas
-- Nível 0: plantas/frutas
INSERT INTO tbAnimais (nomeAnimal, nivel, img) VALUES
('Grama', 0, '/images/AnimaisSegundários/grama.png'),
('Maçã', 0, '/images/AnimaisSegundários/maca.png'),
('Banana', 0, '/images/AnimaisSegundários/banana.png'),
('Alface', 0, '/images/AnimaisSegundários/alface.png'),
('Uva', 0, '/images/AnimaisSegundários/uva.png'),
('Morango', 0, '/images/AnimaisSegundários/morango.png');

-- Nível 1: insetos/pequenos animais
INSERT INTO tbAnimais (nomeAnimal, nivel, img) VALUES
('Lagarta', 1, '/images/AnimaisSegundários/lagarta.png'),
('Minhoca', 1, '/images/AnimaisSegundários/minhoca.png'),
('Gafanhoto', 1, '/images/AnimaisSegundários/gafanhoto.png'),
('Formiga', 1, '/images/AnimaisSegundários/formiga.png'),
('Aranha', 1, '/images/AnimaisSegundários/aranha.png'),
('Abelha', 1, '/images/AnimaisSegundários/abelha.png');

-- Nível 2: pequenos vertebrados/aves
INSERT INTO tbAnimais (nomeAnimal, nivel, img) VALUES
('Coelho', 2, '/images/AnimaisSegundários/coelho.png'),
('Pombo', 2, '/images/AnimaisSegundários/pombo.png'),
('Galinha', 2, '/images/AnimaisSegundários/galinha.png'),
('Roedor', 2, '/images/AnimaisSegundários/roedor.png'),
('Pato', 2, '/images/AnimaisSegundários/pato.png'),
('Galo', 2, '/images/AnimaisSegundários/galo.png');

-- Nível 3: mamíferos maiores
INSERT INTO tbAnimais (nomeAnimal, nivel, img) VALUES
('Porco', 3, '/images/AnimaisSegundários/porco.png'),
('Cervo', 3, '/images/AnimaisSegundários/cervo.png'),
('Ovelha', 3, '/images/AnimaisSegundários/ovelha.png'),
('Lebre', 3, '/images/AnimaisSegundários/lebre.png'),
('Veado', 3, '/images/AnimaisSegundários/veado.png'),
('Javali', 3, '/images/AnimaisSegundários/javali.png');


CREATE TABLE tbPredacao (
	idPredacao INT PRIMARY KEY AUTO_INCREMENT,
    nomePredador VARCHAR(30) NOT NULL,
    nomePresa VARCHAR(30) NOT NULL,
    pontosEvolucao INT NOT NULL,
    FOREIGN KEY (nomePredador) REFERENCES tbAnimais(nomeAnimal),
    FOREIGN KEY (nomePresa) REFERENCES tbAnimais(nomeAnimal)
);


INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('Camaleão', 'grama', 10),
('Camaleão', 'maca', 10),
('Camaleão', 'banana', 10);

-- Beijaflor
INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('Beija-flor', 'maca', 10),
('Beija-flor', 'alface', 10),
('Beija-flor', 'uva', 10);

-- Rato
INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('Rato', 'morango', 10),
('Rato', 'grama', 10),
('Rato', 'banana', 10);

-- Dormouse
INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('Dormouse', 'alface', 10),
('Dormouse', 'morango', 10),
('Dormouse', 'alface', 10);

-----------------

-- Teiu
INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('Teiu', 'lagarta', 10),
('Teiu', 'minhoca', 10),
('Teiu', 'gafanhoto', 10);

-- Corvo
INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('Corvo', 'formiga', 10),
('Corvo', 'aranha', 10),
('Corvo', 'abelha', 10);

-- Doninha
INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('Doninha', 'gafanhoto', 10),
('Doninha', 'lagarta', 10),
('Doninha', 'formiga', 10);

-- Feneco
INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('Feneco', 'abelha', 10),
('Feneco', 'aranha', 10),
('Feneco', 'minhoca', 10);

------------

-- Serpente
INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('Serpente', 'coelho', 10),
('Serpente', 'pombo', 10),
('Serpente', 'galinha', 10);

-- Aguia
INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('Aguia', 'roedor', 10),
('Aguia', 'pato', 10),
('Aguia', 'galo', 10);

-- Raposa
INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('Raposa', 'galinha', 10),
('Raposa', 'pombo', 10),
('Raposa', 'roedor', 10);

-- Coiote
INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('Coiote', 'galo', 10),
('Coiote', 'pato', 10),
('Coiote', 'galinha', 10);

-----------------

INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('Crocodilo', 'porco', 10),
('Crocodilo', 'cervo', 10),
('Crocodilo', 'ovelha', 10);

-- Harpia
INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('Harpia', 'lebre', 10),
('Harpia', 'veado', 10),
('Harpia', 'javali', 10);

-- Jaguar
INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('Jaguar', 'ovelha', 10),
('Jaguar', 'cervo', 10),
('Jaguar', 'lebre', 10);

-- Lobo
INSERT INTO tbPredacao (nomePredador, nomePresa, pontosEvolucao) VALUES
('Lobo', 'javali', 10),
('Lobo', 'veado', 10),
('Lobo', 'porco', 10);




-- área perigosa
drop table tbAnimais;
drop table tbPredacao;

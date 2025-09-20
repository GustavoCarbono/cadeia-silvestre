package utils;

import java.util.Random;

import model.DAO;
import partida.Animal;
import partida.Jogador;
import partida.Partida;

public class Movimentacao {
	private int numRdm;

	public Posicoes  mover(Jogador jogador, Partida partida, DAO dao) {
		Posicoes posicoes = new Posicoes();
		
		Random rdm = new Random();
		ValidarComportamento comportar = new ValidarComportamento();
		Evoluir evoluir = new Evoluir();
		
		numRdm = rdm.nextInt(6)+1; // dado 1 a 6
		Animal animal = jogador.getAnimal();
		int x = animal.getX()+numRdm;//o quadrado do animal mais o valor rolado
		
		if (x >= partida.getTabuleiro().getGridCount()) {//quantidade de quadrados do tabuleiro
			partida.getTabuleiro().getGrid(animal.getX()).removeAnimal(animal.getId()); // remove o animal do quadrado
			
			posicoes.antigaPos = animal.getX();
			
			animal.setX(x - partida.getTabuleiro().getGridCount());
			partida.getTabuleiro().getGrid(x - partida.getTabuleiro().getGridCount()).addAnimal(animal);
			System.out.println("o animal "+animal.getNome()+"deu uma volta agora está no quadrado "+animal.getX());
			posicoes.novaPos = animal.getX();
			posicoes.animal = animal;
			evoluir.aumentarPontos(animal, partida, (animal.getPontosEvoluir()/2), dao);//adiciona metade do total de pontos da evolução
			
			// quando dar uma volta
			return posicoes;
		} else {
			partida.getTabuleiro().getGrid(animal.getX()) .removeAnimal(animal.getId());// remove o animal do quadrado
			
			posicoes.antigaPos = animal.getX();
			
			animal.setX(x);
			partida.getTabuleiro().getGrid(x).addAnimal(animal); // coloca o animal no novo quadrado
			System.out.println("o animal "+animal.getNome()+" moveu para o quadrado "+animal.getX());
			posicoes.novaPos = animal.getX();
			posicoes.animal = animal;
			// espaço para chamar método de inteface
			comportar.validarComportamento(animal, partida, dao);//aplica o comportamento e predação
			return posicoes;
		}
	}
}
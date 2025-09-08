package utils;

import java.util.Random;

import partida.Animal;
import partida.Jogador;
import partida.Tabuleiro;

public class Movimentacao {
	private int numRdm;
	
	public boolean mover(Jogador jogador, Tabuleiro tabuleiro) {
		Random rdm = new Random();
		ValidarComportamento comportar = new ValidarComportamento();
		
		numRdm = rdm.nextInt(6)+1; // dado 1 a 6
		Animal animal = jogador.getAnimal();
		int x = animal.getX()+numRdm;
		
		if (x >= tabuleiro.getGridCount()) {//quantidade de quadrados do tabuleiro
			
			animal.setX(x - tabuleiro.getGridCount());
			System.out.println("o animal "+animal.getNome()+" moveu para o quadrado "+animal.getX());
			
			// quando dar uma volta
			return true;
		} else {
			tabuleiro.getGrid(animal.getX())
				.removeAnimal(animal.getId());// remove o animal do quadrado

			animal.setX(x);
			tabuleiro.getGrid(x).addAnimal(animal); // coloca o animal no novo quadrado
			System.out.println("o animal "+animal.getNome()+" moveu para o quadrado "+animal.getX());
			
			// espaço para chamar método de inteface
			comportar.validarComportamento(animal, tabuleiro);
			return true;
		}
	}
}
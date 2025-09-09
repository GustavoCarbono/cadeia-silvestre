package utils;

import model.DAO;
import partida.Animal;
import partida.Tabuleiro;

public class Comportamento {
	
	public void comportar(Animal animal, Animal alvo, String comportamento, Tabuleiro tabuleiro) {
		// aplica comportamento
	}
	
	public void predacao(Animal animal, Animal presa, Tabuleiro tabuleiro, int pontos, DAO dao) {
		Evoluir evoluir = new Evoluir();
		tabuleiro.getGrid(animal.getX()).removeAnimal(presa.getId());//remove animal do tabuleiro
		evoluir.aumentarPontos(animal, tabuleiro, pontos, dao);
	}
}
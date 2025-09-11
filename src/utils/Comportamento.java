package utils;

import model.DAO;
import partida.Animal;
import partida.Partida;

public class Comportamento {
	
	public void comportar(Animal animal, Animal alvo, String comportamento, Partida partida) {
		// aplica comportamento
	}
	
	public void predacao(Animal animal, Animal presa, Partida partida, int pontos, DAO dao) {
		Evoluir evoluir = new Evoluir();
		partida.getTabuleiro().getGrid(animal.getX()).removeAnimal(presa.getId());//remove animal do tabuleiro
		evoluir.aumentarPontos(animal, partida, pontos, dao);
	}
}
package utils;

import model.DAO;
import partida.Animal;
import partida.Celula;
import partida.Partida;
import view.Interface;

public class Comportamento {
	
	public void comportar(Animal animal, Animal alvo, String comportamento, Partida partida, Interface gui) {
		// aplica comportamento
	}
		
	public void predacao(Animal animal, Animal presa, Partida partida, int pontos, DAO dao, Interface gui) {
		Evoluir evoluir = new Evoluir();
		Celula celula = (animal.getCaminho() == 0) 
			? partida.getTabuleiro().getGridMain(animal.getX()) 
			: partida.getTabuleiro().getCelAlternativo(animal.getX(), (animal.getCaminho()-1));
		celula.removeAnimal(presa.getId());//remove animal do tabuleiro
		evoluir.aumentarPontos(animal, partida, pontos, dao, gui);
	}
}
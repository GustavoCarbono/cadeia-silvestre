package utils;

import java.util.List;

import partida.Animal;
import partida.Tabuleiro;

public class ValidarComportamento {
	
	public void validarComportamento(Animal animal, Tabuleiro tabuleiro) {
		List<Animal> animais = tabuleiro.getGrid(animal.getX()).getAnimais();
		//método que tem resultado de select
		for(Animal animalUni : animais) {
			// dois if ver se tem animais alvo ou presa
			// se sim chama o método de comportamento ou presa
		}
	}
}
 
package utils;

import java.util.List;

import model.DAO;
import model.PredacaoDAO;
import partida.Animal;
import partida.Partida;
import view.Interface;

public class ValidarComportamento {
	
	public void validarComportamento(Animal animal, Partida partida, DAO dao, Interface gui) {
		List<Animal> animais = (animal.getCaminho() == 0) 
			? partida.getTabuleiro().getGridMain(animal.getX()).getAnimais() 
			: partida.getTabuleiro().getCelAlternativo(animal.getX(), (animal.getCaminho()-1)).getAnimais();//todos os animais do quadrado
		
		//chamando tabela do mysql
		List<PredacaoDAO> predacao = dao.buscarPresa(animal.getNome());
		
		Predacao comportar = new Predacao();
		
		for(Animal animalUni : animais) {//animal isolado
			if (predacao != null) {
				if(animalUni.getDono() != null) {//não tem predação com animais de outros jogadores
					for(PredacaoDAO predacaoUni : predacao) {//verifica se animal alvo é presa
						if(predacaoUni.getNomePresa().equals(animalUni.getNome())) {
							comportar.predacao(animal, animalUni, partida, predacaoUni.getPontosEvolucao(), dao, gui);
						}
					}
				}
			}
		}
	}
}
 
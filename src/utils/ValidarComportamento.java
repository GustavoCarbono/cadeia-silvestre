package utils;

import java.util.List;

import model.ComportamentoDAO;
import model.DAO;
import model.PredacaoDAO;
import partida.Animal;
import partida.Partida;
import view.Interface;

public class ValidarComportamento {
	
	public void validarComportamento(Animal animal, Partida partida, DAO dao, Interface gui) {
		List<Animal> animais = partida.getTabuleiro().getGrid(animal.getX()).getAnimais();//todos os animais do quadrado
		
		//chamando tabela do mysql
		List<ComportamentoDAO> comportamentos = dao.buscarComportamento(animal.getNome());
		List<PredacaoDAO> predacao = dao.buscarPresa(animal.getNome());
		
		Comportamento comportar = new Comportamento();
		
		for(Animal animalUni : animais) {//animal isolado
			if (comportamentos != null) {
				for(ComportamentoDAO comportamento : comportamentos) {
					if(comportamento.getNomeAlvo().equals(animalUni)) {
						comportar.comportar(animal, animalUni, comportamento.getComportamento(), partida, gui);
					}
				}
			}
			if (predacao != null) {
				if(animalUni.getDono() != null) {//não tem predação com animais de outros jogadores
					for(PredacaoDAO predacaoUni : predacao) {//verifica se animal alvo é presa
						if(predacaoUni.getNomePresa().equals(animalUni)) {
							comportar.predacao(animal, animalUni, partida, predacaoUni.getPontosEvolucao(), dao, gui);
						}
					}
				}
			}
		}
	}
}
 
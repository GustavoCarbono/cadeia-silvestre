package utils;

import model.AnimaisDAO;
import model.DAO;
import partida.Animal;
import partida.Celula;
import partida.Tabuleiro;

public class Evoluir {
	
	public void aumentarPontos(Animal animal, Tabuleiro tabuleiro, int pontos, DAO dao) {
		Celula animalCel = tabuleiro.getGrid(animal.getX());
		
		if(animal.getTotalPontos() >= (animal.getPontosEvoluir()+pontos)) {	//verifica se tem pontos para evoluir
			int p = animal.getTotalPontos()-animal.getPontosEvoluir();	//pontos restantes para evolução
			evoluir(animal, tabuleiro, dao);	//evolui
			animal.setPontosEvoluir(pontos-p);	//coloca a sobra dos pontos 
			animalCel.setAnimal(animal, animal.getId());
		} else {
			animal.setPontosEvoluir(animal.getPontosEvoluir()+pontos);	//apenas adiciona pontos
			animalCel.setAnimal(animal, animal.getId());
		}
	}
	
	public void evoluir(Animal animal, Tabuleiro tabuleiro, DAO dao) {
		AnimaisDAO evolucao = dao.buscarAnimal(animal.getEvolucao()); //chama tabela do mysql
		
		//troca o animal para a evolução
		animal.setNome(evolucao.getNome());
		animal.setEvolucao(evolucao.getEvolucao());
		animal.setPontosEvoluir(evolucao.getEvoluirPontos());
		tabuleiro.getGrid(animal.getX()).setAnimal(animal, animal.getId());//aplica no tabuleiro
		//método para atualizar a interface
		//Sistema de finalizar partida se for ultimo animal
	}
}

package utils;

import model.AnimaisDAO;
import model.DAO;
import partida.Animal;
import partida.Celula;
import partida.Partida;
import view.Interface;

public class Evoluir {
	
	public void aumentarPontos(Animal animal, Partida partida, int pontos, DAO dao, Interface gui) {
		Celula celula = (animal.getCaminho() == 0) 
				? partida.getTabuleiro().getGridMain(animal.getX()) 
				: partida.getTabuleiro().getCelAlternativo(animal.getX(), (animal.getCaminho()-1));
		if(animal.getPontosEvoluir() <= (animal.getTotalPontos()+pontos)) {	//verifica se tem pontos para evoluir
			int p = animal.getTotalPontos()-animal.getPontosEvoluir();	//pontos restantes para evolução
			evoluir(animal, partida, dao, gui);	//evolui
			animal.setTotalPontos(pontos-p);	//coloca a sobra dos pontos 
			celula.setAnimal(animal, animal.getId());
		} else {	
			animal.setTotalPontos(animal.getTotalPontos()+pontos);	//apenas adiciona pontos
			celula.setAnimal(animal, animal.getId());
		}
	}
	
	public void evoluir(Animal animal, Partida partida, DAO dao, Interface gui) {
		AnimaisDAO evolucao = dao.buscarAnimal(animal.getEvolucao()); //chama tabela do mysql
		Celula celula = (animal.getCaminho() == 0) 
				? partida.getTabuleiro().getGridMain(animal.getX()) 
				: partida.getTabuleiro().getCelAlternativo(animal.getX(), (animal.getCaminho()-1));
		animal.setNome(evolucao.getNome());
		animal.setEvolucao(evolucao.getEvolucao());
		animal.setPontosEvoluir(evolucao.getEvoluirPontos());
		animal.setImg(evolucao.getImg());
		animal.setTotalPontos(0);
		celula.setAnimal(animal, animal.getId());//aplica no tabuleiro
		gui.atualizarImg(animal, partida);
		//método para atualizar a interface
		if(animal.getEvolucao() == null) {
			System.out.println("acabou");
			partida.setFinalizou(true);
		}
	}
}

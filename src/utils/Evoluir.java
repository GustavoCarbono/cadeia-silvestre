package utils;

import java.awt.Color;

import javax.swing.Timer;

import model.AnimaisDAO;
import model.DAO;
import partida.Animal;
import partida.Celula;
import partida.Partida;
import view.Interface;

public class Evoluir {
	
	public boolean aumentarPontos(Animal animal, Partida partida, int pontos, DAO dao, Interface gui) {
		boolean evolucaoFinal = false;
		Celula celula = (animal.getCaminho() == 0) 
				? partida.getTabuleiro().getGridMain(animal.getX()) 
				: partida.getTabuleiro().getCelAlternativo(animal.getX(), (animal.getCaminho()-1));
		if(animal.getPontosEvoluir() <= (animal.getTotalPontos()+pontos)) {

			evolucaoFinal = evoluir(animal, partida, dao, gui);	//evolui
			celula.setAnimal(animal, animal.getId());
			
		}
		
		else {	
			animal.setTotalPontos(animal.getTotalPontos()+pontos);	//apenas adiciona pontos
			celula.setAnimal(animal, animal.getId());
			
		}
		
		return evolucaoFinal;
	}
	
	public boolean evoluir(Animal animal, Partida partida, DAO dao, Interface gui) {

		
		AnimaisDAO evolucao = dao.buscarAnimal(animal.getEvolucao()); //chama tabela do mysql
		
		
		Celula celula = (animal.getCaminho() == 0) 
				? partida.getTabuleiro().getGridMain(animal.getX()) 
				: partida.getTabuleiro().getCelAlternativo(animal.getX(), (animal.getCaminho()-1));


		String antigoAnimalNome = animal.getNome();
		
		animal.setNome(evolucao.getNome());
		animal.setEvolucao(evolucao.getEvolucao());
		animal.setPontosEvoluir(evolucao.getEvoluirPontos());
		animal.setImg(evolucao.getImg());
		animal.setTotalPontos(0);
		celula.setAnimal(animal, animal.getId());//aplica no tabuleiro
		
		
		gui.atualizarImg(animal, partida);

		gui.atualizarInfoJogador(partida.getOrdemJogador(), partida.getJogadores());
		
		if(DAO.buscarAnimal(animal.getNome()).getNivel() == 5) {
			return true;
		}

		else {
			gui.mostrarMensagemTemporaria(
				    "O jogador " + animal.getDono() + " evoluiu seu animal " + antigoAnimalNome + " para " + animal.getNome() + "!", 
				    3000,
				    new Color(0, 0, 153),
				    3
				);

				// Wait 3 seconds before calling trocarPresas
				new javax.swing.Timer(3000, e -> {
					gui.mostrarMensagemTemporaria(
						    "Tabuleiro: Presas trocadas com sucesso!"
						    + "", 
						    3000,
						    new Color(29, 171, 26),
						    2
						);
					
				    gui.trocarPresas(animal);
				    ((javax.swing.Timer) e.getSource()).stop(); // stop the timer
				}).start();
			
			
			return false;
		}
			
		
	}
}

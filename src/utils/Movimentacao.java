package utils;

import java.util.Random;

import javax.swing.Timer;

import model.DAO;
import partida.Animal;
import partida.Caminho;
import partida.Celula;
import partida.Jogador;
import partida.Partida;
import partida.Tabuleiro;
import view.Interface;

public class Movimentacao {
	private int numRdm;

	public int mover(Jogador jogador, Partida partida, DAO dao, Interface gui) {
		Random rdm = new Random();
		ValidarComportamento comportar = new ValidarComportamento();
		Evoluir evoluir = new Evoluir();
		Tabuleiro tabuleiro = partida.getTabuleiro();
		
		numRdm = rdm.nextInt(6)+1; // dado 1 a 6
		Animal animal = jogador.getAnimal();
		
		Celula celula = (animal.getCaminho() == 0) 
				? tabuleiro.getGridMain(animal.getX()) 
				: tabuleiro.getCelAlternativo(animal.getX(), (animal.getCaminho()-1));
		
		gui.bloquearBotao();
		
		if(celula.getOutroCaminho()) {
			boolean entrar = false;//método que ver se ele quer entrar
			if(entrar) {// se o jogador escolheu sim ou não ir para outro caminho
				animal.setCaminho(celula.getCaminhoAlternativo().getCaminhoId());
				entrarCaminhoAlternativo(animal, celula, celula.getCaminhoAlternativo());
				gui.pegarCelulaView(animal, celula, celula.getCaminhoAlternativo());
				gui.atualizarInfoJogador(partida.getOrdemJogador(), partida.getJogadores());
				numRdm--;
			}
		}
		int passos[] = {numRdm};
		
		Timer timer = new Timer(10, null);
	    timer.addActionListener(e -> {
	        if (passos[0] <= 0) {
	            ((Timer) e.getSource()).stop();
	            
				gui.desbloquearBotao();
	            gui.atualizarInfoJogador(partida.getOrdemJogador(), partida.getJogadores());
	            //comportar.validarComportamento(animal, partida, dao, gui);
	            return;
	        }
	        
	        moverPasso(animal, evoluir, partida, dao, gui);
	        passos[0]--;
	    });

	    timer.start();
		return numRdm;
	}
	
	public void moverPasso(Animal animal, Evoluir evoluir, Partida partida, DAO dao, Interface gui) {
		int pos = animal.getX();
		int caminho = animal.getCaminho();
		Tabuleiro tabuleiro = partida.getTabuleiro();
		
		Celula celula = (caminho == 0) 
				? tabuleiro.getGridMain(pos) 
				: tabuleiro.getCelAlternativo(pos, (caminho-1));
		
		boolean entrar = false;//método que ver se ele quer entrar
		if(celula.getOutroCaminho()) {
			Celula caminhoAlt = celula.getCaminhoAlternativo();
			if(entrar) {
				entrarCaminhoAlternativo(animal, celula, caminhoAlt);
				
				gui.pegarCelulaView(animal, celula, caminhoAlt);
			}
		}
		Caminho celulaAlt = null;
		if (caminho != 0) {
			celulaAlt = tabuleiro.getGridAlternativo(celula.getCaminhoId());
		}
		
		Celula ultimaPos;
		if (caminho == 0) { //pega a ultima posicao do caminho principal ou alternativo
			 ultimaPos = tabuleiro.getGridMain(tabuleiro.getGridMainCount()-1);
		} else { 
			ultimaPos = celulaAlt.getCelula(
					celulaAlt.getCelulaCount()-1);
		}
		
		int count;
		if (caminho == 0) { //pega a ultima posicao do caminho principal ou alternativo
			 count = tabuleiro.getGridMainCount();
		} else { 
			count = celulaAlt.getCelulaCount();
		}
		
		if (celula.getX() == ultimaPos.getX() && !entrar) {
			if (celula.getCaminhoId() == 0) {
				celula.removeAnimal(animal.getId());// remove o animal do quadrado
				animal.setX(pos-count+1);//atualiza o dado da posicao do animal
				System.out.println("o animal "+animal.getNome()+" deu uma volta");
				// coloca o animal no novo quadrado
				tabuleiro.getGridMain(0).addAnimal(animal);
				evoluir.aumentarPontos(animal, partida, (animal.getPontosEvoluir()/2), dao, gui);//adiciona metade do total de pontos da evolução
				
				gui.pegarCelulaView(animal, celula, tabuleiro.getGridMain(0));
			} else {
				sairCaminhoAlternativo(animal, celula, celulaAlt.getFim());
				
				gui.pegarCelulaView(animal, celula, celulaAlt.getFim());
			}
		} else {
			celula.removeAnimal(animal.getId());// remove o animal do quadrado
			animal.setX(pos+1);//atualiza o dado da posicao do animal
			// coloca o animal no novo quadrado
			if(celula.getCaminhoId() == 0) {
				tabuleiro.getGridMain(pos+1).addAnimal(animal);
				
				gui.pegarCelulaView(animal, celula, tabuleiro.getGridMain(pos+1));
			} else {
				celulaAlt.getCelula(pos+1).addAnimal(animal);
				
				gui.pegarCelulaView(animal, celula, celulaAlt.getCelula(pos+1));
			}
		}
	}
	
	public void entrarCaminhoAlternativo(Animal animal, Celula celulaAntiga, Celula celulaNova) {
		if (animal == null || celulaAntiga == null || celulaNova == null) {
			System.err.println("Erro: animal ou célula nula ao tentar entrar em caminho alternativo.");
			return;
		}
		
		celulaAntiga.removeAnimal(animal.getId());// remove o animal do quadrado antigo
		animal.setX(celulaNova.getX());//atualiza o dado da posicao do animal
		animal.setCaminho(celulaNova.getCaminhoId());//atualiza o dado do caminho do animal
		System.out.println("o animal "+animal.getNome()+" saiu do caminho "+celulaAntiga.getCaminhoId()+" para o caminho "+celulaNova.getCaminhoId());
		// coloca o animal no novo caminho
		celulaNova.addAnimal(animal);
	}
	
	public void sairCaminhoAlternativo(Animal animal, Celula celulaAntiga, Celula celulaNova) {
		if (animal == null || celulaAntiga == null || celulaNova == null) {
			System.err.println("Erro: animal ou célula nula ao tentar entrar em caminho alternativo.");
			return;
		}
		celulaAntiga.removeAnimal(animal.getId());// remove o animal do quadrado antigo
		animal.setX(celulaNova.getX());//atualiza o dado da posicao do animal
		animal.setCaminho(celulaNova.getCaminhoId());//atualiza o dado do caminho do animal
		System.out.println("o animal "+animal.getNome()+" saiu do caminho "+celulaAntiga.getCaminhoId()+" para o caminho "+celulaNova.getCaminhoId());
		// coloca o animal no novo caminho
		celulaNova.addAnimal(animal);
	}
}
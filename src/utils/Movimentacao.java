package utils;

import java.awt.Color;
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
    private final Quiz quiz = new Quiz();


	public int mover(Jogador jogador, Partida partida, DAO dao, Interface gui) {
		Random rdm = new Random();
		ValidarComportamento comportar = new ValidarComportamento();
		Evoluir evoluir = new Evoluir();
		Tabuleiro tabuleiro = partida.getTabuleiro();
		
		numRdm = rdm.nextInt(6)+1
				; // dado 1 a 6
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
		


		Timer timer = new Timer(150, null);
	    timer.addActionListener(e -> {
	        if (passos[0] <= 0) {
	            ((Timer) e.getSource()).stop();

            boolean partidaAcabou = false;
            int delay = 500;
            
            String predadorCelula = gui.getCelula(animal.getX()).getPredadorNome();
	        String presaCelula = gui.getCelula(animal.getX()).getPresa();
            String casaQuiz = gui.getCelula(animal.getX()).getQuiz();
	            
	            
            //checagem de casa com presa
            if (presaCelula != null) {
	            	int nivelAnimal = DAO.buscarAnimal(animal.getNome()).getNivel();
	            	int nivelPresa = DAO.buscarAnimal(presaCelula).getNivel(); 
	            	
	            	int diferencaNivel = nivelAnimal - nivelPresa;
	                
	                // Ensure minimum points = 1
	                int points = 30 - 10 * diferencaNivel;
	                points = Math.max(points, 5);
	                
	            	if(nivelAnimal > nivelPresa) {
	            		if(animal.getNivel() == 1 ) {
	            			gui.mostrarMensagemTemporaria(
					             	"O jogador " + jogador.getJogador() + " consumiu um alimento nível " + nivelPresa + "! " + "+" + points + " pontos!",
					             	3000,
					             	new Color(102, 255, 102),
					             	2
			            	);
	            			delay = 3000;
	            		}
	            		else {
			            	gui.mostrarMensagemTemporaria(
					             	"O jogador " + jogador.getJogador() + " capturou uma presa nível " + nivelPresa + "! " + "+" + points + " pontos!"
					             			+ "", 
					             	3000,
					             	new Color(102, 255, 102),
					             	2
			            	);
			            	delay = 3000;
	            		}
			            partidaAcabou = evoluir.aumentarPontos(animal, partida, points, dao, gui);
	            	}
	            	else {
	            		if(nivelPresa == 1 ) {
	            			gui.mostrarMensagemTemporaria(
					             	"O jogador " + jogador.getJogador() + " não conseguiu consumir o alimento. Nivel do Anímal: " + nivelAnimal +" / Nível do Alimento: " + nivelPresa,
					             	3000,
					             	new Color(102, 255, 102),
					             	2
			            	);
	            			delay = 3000;
	            		}
	            		else {
	            			gui.mostrarMensagemTemporaria(
	            					"O jogador " + jogador.getJogador() + " não conseguiu capturar a presa. Nivel do Anímal: " + nivelAnimal +" / Nível da Presa: " + nivelPresa,
					             	3000,
					             	new Color(102, 255, 102),
					             	2
			            	);
	            			delay = 3000;
	            		}
	            		
	            	}
		        }

	    
            //checagem de casa com predador
            else if (predadorCelula != null) {
	            	if(animal.getTotalPontos() == 0) {
	            		gui.mostrarMensagemTemporaria(
	            			"O jogador " + jogador.getJogador() + " foi capturado, porém não tinha nada a perder...",
	            			3000,
	            			new Color(255, 0, 0),
	            			2
	            		);
	            		delay = 3000;
	            		
	            	}
	            	
	            	else if(animal.getTotalPontos() <= 5) {
	            		animal.setTotalPontos(0);
	            		
	            		gui.mostrarMensagemTemporaria(
	            			"O jogador " + jogador.getJogador() + " foi capturado e leveram tudo o que tinha..", 
	            			3000,
	            			new Color(255, 0, 0),
	            			2
	            		);
	            		
	            		delay = 3000;
	            	}
	            	
	            	else {
		            	animal.setTotalPontos(animal.getTotalPontos() - 5);
		            	
		            	gui.mostrarMensagemTemporaria(
		            		"O jogador " + jogador.getJogador() + " foi capturado. -5 pontos...", 
		            		3000,
		            		new Color(255, 0, 0),
		            		2
		            	);
		            	
		            	delay = 3000;
	            	}
	            	
	            	if(animal.getPontosEvoluir() <= animal.getTotalPontos()) {
	     
	            		 partidaAcabou = evoluir.aumentarPontos(animal, partida, 0, dao, gui);
	            	}
	            }
	            
            // casa de quiz
            else if (casaQuiz != null) {
                System.out.println("QUIZ ativado para jogador: " + jogador.getJogador() + " na casa: " + animal.getX());
                quiz.executarQuiz(jogador, partida, gui);
                delay = 3000;
            }
            
            //prevenção pra erro na evolução por dar voltas no tabuleiro. se o animal dessse uma volta que o levasse a evoluir e depois caisse numa casa de predador, o animal evoluiria mesmo com a quantidade de pontos inferior ao necessario
            else { 
            	if(animal.getPontosEvoluir() <= animal.getTotalPontos()) {
            		   
           		 partidaAcabou = evoluir.aumentarPontos(animal, partida, 0, dao, gui);
           	}
	          
	        }
	            
	        if(partidaAcabou == true) {
	            gui.mostrarMensagemTemporaria(
	        			"O jogador " + animal.getDono() + " evoluiu para " + animal.getNome() + 
	        			" e ganhou o jogo!", 
	        			90000, new Color(0, 0, 153), 3
	        );

	        }
	        else {
	        	if(animal.getPontosEvoluir() <= animal.getTotalPontos()) {
         		   
	        		partidaAcabou = evoluir.aumentarPontos(animal, partida, 0, dao, gui);
	           		 
	        	}
		         new Timer(delay, ev -> {

		        	gui.desbloquearBotao();
			       	gui.atualizarInfoJogador(partida.getOrdemJogador(), partida.getJogadores());
			       	gui.atualizarJogadorAtual(partida.getOrdemJogador().get(0).getJogador());
			            
			       	((Timer) ev.getSource()).stop();
		         }).start();
	        }
	            return;
	    }
	        
			moverPasso(animal, evoluir, jogador, partida, dao, gui);
	        passos[0]--;
	        
	    });

	    timer.start();
		return numRdm;
	}
	
	public void moverPasso(Animal animal, Evoluir evoluir, Jogador jogador, Partida partida, DAO dao, Interface gui) {
		int pos = animal.getX();
		int caminho = animal.getCaminho();
		Tabuleiro tabuleiro = partida.getTabuleiro();
		
		Celula celula = (caminho == 0) 
				? tabuleiro.getGridMain(pos) 
				: tabuleiro.getCelAlternativo(pos, (caminho-1));
		
		boolean entrar = false;//método que ver se ele quer entrar
		gui.atualizarImg(animal, partida);
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
				gui.mostrarMensagemTemporaria(
			           "O jogador " + jogador.getJogador() + " completou uma volta! +10 pontos!", 
			           3000,
			           new Color(255, 255, 0),
			           1
			    );
				

				animal.setTotalPontos(animal.getTotalPontos() + 10);

				tabuleiro.getGridMain(0).addAnimal(animal);
				
				gui.atualizarInfoJogador(partida.getOrdemJogador(), partida.getJogadores());
						

				gui.atualizarImg(animal, partida);
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

package utils;

import java.util.ArrayList;
import java.util.List;

import minijogo.Minijogo;
import model.DAO;
import partida.Animal;
import partida.Jogador;
import partida.Partida;
import view.Interface;

public class Aplicacao {
	Partida partida;
	
	public static void main(String[] args) {
		Partida partida;
		DAO dao = new DAO();
		dao.conectar();
		Minijogo minijogo = new Minijogo();
		//Escolher animais e nomes dos jogadores

		//----------------------------------------
		List<Integer> xAlt = new ArrayList<>();
		List<Integer> comecos = new ArrayList<>();
		List<Integer> fins = new ArrayList<>();
		
		xAlt.add(5);//caminho alternativo de 5 casas
		comecos.add(4);//começa no 4º quadrado da casa principal
		fins.add(10);//acaba na casa 10 do caminho principal
		xAlt.add(6);
		comecos.add(10);
		fins.add(17);
		
		// temporario
		List<Jogador> jogadores = new ArrayList<>();
		jogadores.add(new Jogador("Alexandre", new Animal(1, "Macaco", "Babuíno", 50, 1, "Alexandre", "/images/AnimaisPrincipais/monkey.png")));
		jogadores.add(new Jogador("Daniel", new Animal(2, "Ema", "Avestruz", 50, 1, "Daniel", "/images/AnimaisPrincipais/ema.png")));
		jogadores.add(new Jogador("Emanuel", new Animal(3, "Puma", "Hiena", 50, 1, "Emanuel", "/images/AnimaisPrincipais/puma.png")));
		jogadores.add(new Jogador("Gustavo", new Animal(4, "Elefante-Pigmeu-de-Bornéu", "Elefante Africano", 50, 1, "Gustavo", "/images/AnimaisPrincipais/elefante.png")));
		//----------------------------------------
		
		Movimentacao mov = new Movimentacao();
		partida = new Partida(28, jogadores, xAlt, comecos, fins);

		Interface gui = new Interface(partida.getOrdemJogador(), 28, partida);
		gui.setRolarDados(() -> {
			if(partida.getFinalizou() != true) {
				int dado = mov.mover(partida.getOrdemJogador().get(0), partida, dao, gui);//número do dado dentro desse método

				gui.atualizarDados(dado);

					
				partida.aumentarTurnoAtual();
				partida.mudarOrdemJogador();
				

			}
			

		});
		
		
	}

	
	
}

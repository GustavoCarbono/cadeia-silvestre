package utils;

import java.util.ArrayList;
import java.util.List;

import partida.Animal;
import partida.Jogador;
import partida.Partida;

public class Aplicacao {
	public static void main(String[] args) {
		//boolean acabou = false;
		//Escolher animais e nomes dos jogadores
		
		// temporario
		List<Jogador> jogadores = new ArrayList<>();
		jogadores.add(new Jogador("p1", new Animal(1, "macaco", "gorila")));
		jogadores.add(new Jogador("p2", new Animal(2, "ema", "avestruz")));
		jogadores.add(new Jogador("p3", new Animal(3, "puma", "leão")));
		jogadores.add(new Jogador("p4", new Animal(4, "elefante-pigmeu-de-Bornéu", "elefante africano")));
		
		Movimentacao mov = new Movimentacao();
		Partida partida = new Partida(20, jogadores);
		int i = 0;
		do {
			// animação de dado
			mov.mover(partida.getOrdemJogador().get(0), partida.getTabuleiro());
			partida.aumentarTurnoAtual();
			partida.mudarOrdemJogador();
			i++;
		} while (i <= 100);
	}
}

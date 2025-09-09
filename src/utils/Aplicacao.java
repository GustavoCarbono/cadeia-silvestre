package utils;

import java.util.ArrayList;
import java.util.List;

import model.DAO;
import partida.Animal;
import partida.Jogador;
import partida.Partida;

public class Aplicacao {
	public static void main(String[] args) {
		DAO dao = new DAO();
		dao.conectar();
		//boolean acabou = false;
		//Escolher animais e nomes dos jogadores
		
		// temporario
		List<Jogador> jogadores = new ArrayList<>();
		jogadores.add(new Jogador("p1", new Animal(1, "macaco", "gorila", 20, "p1")));
		jogadores.add(new Jogador("p2", new Animal(2, "ema", "avestruz", 20, "p2")));
		jogadores.add(new Jogador("p3", new Animal(3, "puma", "leão", 20, "p3")));
		jogadores.add(new Jogador("p4", new Animal(4, "elefante-pigmeu-de-Bornéu", "elefante africano", 20, "p4")));
		
		Movimentacao mov = new Movimentacao();
		Partida partida = new Partida(20, jogadores);
		do {
			// animação de dado
			mov.mover(partida.getOrdemJogador().get(0), partida.getTabuleiro(), dao);
			partida.aumentarTurnoAtual();
			partida.mudarOrdemJogador();
			if(partida.getTurnoAtual()%8 == 0) {
				//metodo que começa um minijogo
			}
		} while (partida.getTurnoAtual() <= 10);
	}
}

package utils;

import java.util.ArrayList;
import java.util.List;

import model.DAO;
import partida.Animal;
import partida.Jogador;
import partida.Partida;
import view.Interface;

public class Aplicacao {
	public static void main(String[] args) {
		DAO dao = new DAO();
		dao.conectar();
		Interface gui = new Interface();
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
		jogadores.add(new Jogador("p1", new Animal(1, "macaco", "gorila", 20, "p1")));
		jogadores.add(new Jogador("p2", new Animal(2, "ema", "avestruz", 20, "p2")));
		jogadores.add(new Jogador("p3", new Animal(3, "puma", "leão", 20, "p3")));
		jogadores.add(new Jogador("p4", new Animal(4, "elefante-pigmeu-de-Bornéu", "elefante africano", 20, "p4")));
		//----------------------------------------
		
		Movimentacao mov = new Movimentacao();
		Partida partida = new Partida(20, jogadores, xAlt, comecos, fins);
		do {
			// animação de dado
			mov.mover(partida.getOrdemJogador().get(0), partida, dao, gui);//número do dado dentro desse método
			partida.aumentarTurnoAtual();
			partida.mudarOrdemJogador();
			if(partida.getTurnoAtual()%8 == 0) {
				//metodo que começa um minijogo
			}
		} while (!partida.getFinalizou() || partida.getTurnoAtual() < 20);
	}
}

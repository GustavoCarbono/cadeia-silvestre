package utils;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import minijogo.Minijogo;
import model.DAO;
import partida.Animal;
import partida.Jogador;
import partida.Partida;
import view.Interface;

public class Aplicacao {
	public static void main(String[] args) {
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
		
		
		// nomes padrão se usuário cancelar ou deixar vazio
		String nome1 = JOptionPane.showInputDialog(null, "Digite o nome do Jogador 1:", "Nome do Jogador", JOptionPane.QUESTION_MESSAGE);
		String nome2 = JOptionPane.showInputDialog(null, "Digite o nome do Jogador 2:", "Nome do Jogador", JOptionPane.QUESTION_MESSAGE);
		String nome3 = JOptionPane.showInputDialog(null, "Digite o nome do Jogador 3:", "Nome do Jogador", JOptionPane.QUESTION_MESSAGE);
		String nome4 = JOptionPane.showInputDialog(null, "Digite o nome do Jogador 4:", "Nome do Jogador", JOptionPane.QUESTION_MESSAGE);

		
		nome1 = (nome1 == null || nome1.trim().isEmpty()) ? "Jogador 1" : nome1.trim();
		nome2 = (nome2 == null || nome2.trim().isEmpty()) ? "Jogador 2" : nome2.trim();
		nome3 = (nome3 == null || nome3.trim().isEmpty()) ? "Jogador 3" : nome3.trim();
		nome4 = (nome4 == null || nome4.trim().isEmpty()) ? "Jogador 4" : nome4.trim();

		List<Jogador> jogadores = new ArrayList<>();
		jogadores.add(new Jogador(nome1, new Animal(IdUnica.getIdUnico(), "macaco", "babuíno", 20, 1, nome1, "/images/AnimaisPrincipais/monkey.png")));
		jogadores.add(new Jogador(nome2, new Animal(IdUnica.getIdUnico(), "ema", "avestruz", 20, 1, nome2, "/images/AnimaisPrincipais/ema.png")));
		jogadores.add(new Jogador(nome3, new Animal(IdUnica.getIdUnico(), "puma", "hiena", 20, 1, nome3, "/images/AnimaisPrincipais/puma.png")));
		jogadores.add(new Jogador(nome4, new Animal(IdUnica.getIdUnico(), "elefante pequeno", "elefante africano", 20, 1, nome4, "/images/AnimaisPrincipais/elefante.png")));
		//----------------------------------------
		Movimentacao mov = new Movimentacao();
		Partida partida = new Partida(28, jogadores, xAlt, comecos, fins);
		Interface gui = new Interface(partida.getOrdemJogador(), 28, partida);
		AdicionarPresas adicionarPresas = new AdicionarPresas();
		adicionarPresas.addPresas(partida, gui, dao);
		
		gui.setRolarDados(() -> {
			if(!partida.getFinalizou()) {
				int dado = mov.mover(partida.getOrdemJogador().get(0), partida, dao, gui);//número do dado dentro desse método
				gui.atualizarDados(dado);
				partida.aumentarTurnoAtual();
				partida.mudarOrdemJogador();
				if(partida.getTurnoAtual()%8 == 0) {
					minijogo.comecaMinijogo();
				}
			} else {
				//quando acaba partida
			}
		});
	}
	
	
}

package partida;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Partida {
	private List<Jogador> jogadores;
	private Tabuleiro tabuleiro;
	private int turnoAtual;
	private List<Jogador> ordemJogador;
	
	public Partida(int x, List<Jogador> jogadores) {
		tabuleiro = new Tabuleiro(x);
		turnoAtual = 1;
		if (jogadores.size() <= 4 && jogadores.size() > 1) { // de 2 a 4 jogadores			
			this.jogadores = new ArrayList<>(jogadores);
			for(Jogador jogador : jogadores) {
				tabuleiro.getGrid(0).addAnimal(jogador.getAnimal());
			}
			randomizarJogadores();
			ordemJogador = new ArrayList<Jogador>(this.jogadores);
		} else {
			System.out.println("quantidade de jogadores inválida");
		}
	}
	
	public void randomizarJogadores() {
		Collections.shuffle(jogadores);
	}

	public int getTurnoAtual() {
		return turnoAtual;
	}

	public void aumentarTurnoAtual() {
		turnoAtual++;
	}

	public List<Jogador> getJogadores() {
		return jogadores;
	}

	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public List<Jogador> getOrdemJogador() {
		return ordemJogador;
	}

	public void mudarOrdemJogador() {
		Collections.rotate(ordemJogador, -1);
	}	
}
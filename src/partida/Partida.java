package partida;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Partida {
	private List<Jogador> jogadores;
	private Tabuleiro tabuleiro;
	private int turnoAtual;
	private List<Jogador> ordemJogador;
	private boolean finalizou;
	
	public Partida(int x, List<Jogador> jogadoresList, List<Integer> xAlt, List<Integer> comecos, List<Integer> fins, List<Integer> posicoesPresas) {
		tabuleiro = new Tabuleiro(x, xAlt, comecos, fins, posicoesPresas);//inicia tabuleiro
		turnoAtual = 1;
		finalizou = false;
		if (jogadoresList.size() <= 4 && jogadoresList.size() > 0) { // de 1 a 4 jogadores		
			this.jogadores = new ArrayList<>(jogadoresList);
			for(Jogador jogador : jogadores) {
				tabuleiro.getGridMain(0).addAnimal(jogador.getAnimal());//adiciona o animal do jogador no quadrado 0
			}

			ordemJogador = new ArrayList<>(this.jogadores);
		} else {
			System.out.println("quantidade de jogadores inválida");
			finalizou = true;
		}
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
	
	public boolean getFinalizou() {
		return finalizou;
	}
	
	public void setFinalizou(boolean finalizou) {
		this.finalizou = finalizou;
	}
}
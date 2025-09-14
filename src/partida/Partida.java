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
	
	public Partida(int x, List<Jogador> jogadoresList, List<Integer> xAlt, List<Integer> comecos, List<Integer> fins) {
		tabuleiro = new Tabuleiro(x, xAlt, comecos, fins);//inicia tabuleiro
		turnoAtual = 1;
		finalizou = false;
		if (jogadores.size() <= 4 && jogadores.size() > 1) { // de 2 a 4 jogadores			
			this.jogadores = new ArrayList<>(jogadores);
			for(Jogador jogador : jogadores) {
				tabuleiro.getGridMain(0).addAnimal(jogador.getAnimal());//adiciona o animal do jogador no quadrado 0
			}
			randomizarJogadores();//randomiza a ondem dos jogadores
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
	
	public boolean getFinalizou() {
		return finalizou;
	}
	
	public void setFinalizou(boolean finalizou) {
		this.finalizou = finalizou;
	}
}
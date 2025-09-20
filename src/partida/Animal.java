package partida;

import javax.swing.JLabel;

public class Animal {
	private int id;
	private String nome;
	private String dono;
	private String evolucao;
	private int totalPontos;
	private int pontosEvoluir;
	private int x;
	public JLabel img;
	
	public Animal(int id, String nome, String evolucao, int x) {
		this.id = id;
		this.nome = nome;
		this.evolucao = evolucao;
		this.x = x;
	}
	
	public Animal(int id, String nome, String evolucao, int pontosEvoluir, String dono, JLabel img) {
		this.id = id;
		this.nome = nome;
		this.dono = dono;
		this.evolucao = evolucao;
		totalPontos = 0;
		this.pontosEvoluir = pontosEvoluir;
		this.x = 0;
		this.img = img;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEvolucao() {
		return evolucao;
	}

	public void setEvolucao(String evolucao) {
		this.evolucao = evolucao;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getId() {
		return id;
	}

	public int getTotalPontos() {
		return totalPontos;
	}

	public void setTotalPontos(int totalPontos) {
		this.totalPontos = totalPontos;
	}

	public int getPontosEvoluir() {
		return pontosEvoluir;
	}

	public void setPontosEvoluir(int pontosEvoluir) {
		this.pontosEvoluir = pontosEvoluir;
	}

	public String getDono() {
		return dono;
	}
}
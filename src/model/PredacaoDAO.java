package model;

public class PredacaoDAO {
	private String nomePredador;
	private String nomePresa;
	private int pontosEvolucao;

	//tabela do mysql
	
	public PredacaoDAO(String nomePredador, String nomePresa, int pontosEvolucao) {
		this.nomePredador = nomePredador;
		this.nomePresa = nomePresa;
		this.pontosEvolucao = pontosEvolucao;
	}

	public String getNomePredador() {
		return nomePredador;
	}

	public String getNomePresa() {
		return nomePresa;
	}

	public int getPontosEvolucao() {
		return pontosEvolucao;
	}
}

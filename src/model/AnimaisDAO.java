package model;

public class AnimaisDAO {
	private String nome;
	private String evolucao;
	private int evoluirPontos;
	private boolean ultimaEvolucao;
	
	//tabela do mysql
	
	public AnimaisDAO(String nome, String evolucao, int evoluirPontos) {
		this.nome = nome;
		this.evolucao = evolucao;
		this.evoluirPontos = evoluirPontos;
	}

	public String getNome() {
		return nome;
	}

	public String getEvolucao() {
		return evolucao;
	}

	public int getEvoluirPontos() {
		return evoluirPontos;
	}
	
	public boolean getUltimaEvolucao() {
		return ultimaEvolucao;
	}
}

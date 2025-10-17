package model;

public class AnimaisDAO {
	private String nome;
	private String evolucao;
	private int evoluirPontos;
	private int nivel;
	private String img;
	
	//tabela do mysql
	
	public AnimaisDAO(String nome, String evolucao, int evoluirPontos, int nivel, String img) {
		this.nome = nome;
		this.evolucao = evolucao;
		this.evoluirPontos = evoluirPontos;
		this.nivel = nivel;
		this.img = img;
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
	
	public String getImg() {
		return img;
	}
	
	public int getNivel() {
		return nivel;
	}
}

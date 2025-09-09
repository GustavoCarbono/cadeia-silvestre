package model;

public class ComportamentoDAO {
	private String nomeAnimal;
	private String nomeAlvo;
	private String comportamento;

	//tabela do mysql
	
	public ComportamentoDAO(String nomeAnimal, String nomeAlvo, String comportamento) {
		this.nomeAnimal = nomeAnimal;
		this.nomeAlvo = nomeAlvo;
		this.comportamento = comportamento;
	}
	
	public String getNomeAnimal() {
		return nomeAnimal;
	}
	public String getNomeAlvo() {
		return nomeAlvo;
	}
	public String getComportamento() {
		return comportamento;
	}
}

package partida;

public class Animal {
	private int id;
	private String nome;
	private String evolucao;
	private int x;
	
	public Animal(int id, String nome, String evolucao, int x) {
		super();
		this.id = id;
		this.nome = nome;
		this.evolucao = evolucao;
		this.x = x;
	}
	
	public Animal(int id, String nome, String evolucao) {
		super();
		this.id = id;
		this.nome = nome;
		this.evolucao = evolucao;
		this.x = 0;
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
}
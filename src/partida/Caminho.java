package partida;

public class Caminho {
	private int id;
	private Celula[] celulas;
	private Celula comeco;
	private Celula fim;

	public Caminho(int id, Celula[] celulas, Celula comeco, Celula fim) {
		this.id = id;
		this.celulas = celulas;
		this.comeco = comeco;
		this.fim = fim;
	}

	public Celula getCelula(int pos) {
		if(pos >= 0 && pos < celulas.length) {
            return celulas[pos];
        }
        return null;
	}

	public Celula getComeco() {
		return comeco;
	}

	public void setComeco(Celula comeco) {
		this.comeco = comeco;
	}

	public Celula getFim() {
		return fim;
	}

	public void setFim(Celula fim) {
		this.fim = fim;
	}

	public int getId() {
		return id;
	}
	
	public int getCelulaCount() {
		return celulas.length;
	}
}

package partida;

public class Tabuleiro {
	private Celula[] Grid;
	
	public Tabuleiro(int x) {// x = quantidade de casas do tabuleiro
		Grid = new Celula[x];
		for(int i=0; i<x; i++) {
			Grid[i] = new Celula(i);
		}
	}
	
	public Celula getGrid(int x) {
		return Grid[x];
	}
	
	public int getGridCount() {
		return Grid.length;
	}
}
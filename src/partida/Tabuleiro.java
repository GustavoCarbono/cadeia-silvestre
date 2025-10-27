package partida;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tabuleiro {
	private Celula[] Grid;
	private List<Caminho> GridAlternativo;
	private Integer quizPosicao;
	
	// x = quantidade de casas do tabuleiro
	// comecos = casa da grid principal que começa caminho alternativo
	// fins = a casa que conecta o fim do caminho alternativo com a principal
	public Tabuleiro(int x, List<Integer> xAlternativo, List<Integer> comecos, List<Integer> fins, List<Integer> posicoesPresas) {
		Grid = new Celula[x];
		for(int i=0; i<x; i++) {
			Grid[i] = new Celula(0, i);
		}
		GridAlternativo = new ArrayList<>();
		for(int i=0; i<xAlternativo.size(); i++) {
			Celula[] alternativo = new Celula[xAlternativo.get(i)];
			
			for(int j=0; j<xAlternativo.get(i); j++) {
				alternativo[j] = new Celula((i+1), comecos.get(i)+j);
			}
			
			Grid[comecos.get(i)].setCaminhoAlternativo(alternativo[0]);
			alternativo[xAlternativo.get(i)-1].setRetornoCaminho(Grid[fins.get(i)]);
			
			GridAlternativo.add(new Caminho((i+1), alternativo, Grid[comecos.get(i)], Grid[fins.get(i)]));
		}

		// marca uma célula aleatória da grade principal como QUIZ (evita casa 0 e presas)
		if (Grid.length > 2) {
			Random r = new Random();
			int pos;
			do {
				pos = r.nextInt(Grid.length);
			} while (pos == 0 || (posicoesPresas != null && posicoesPresas.contains(pos))); // não colocar quiz na inicial nem em presas
			Grid[pos].setQuiz(true);
			quizPosicao = pos;
			System.out.println("QUIZ posicionado na casa: " + pos);
		}
	}

	public Integer getQuizPosicao() {
		return quizPosicao;
	}
	
	public Celula getGridMain(int x) {
		return Grid[x];
	}
	
	public Celula[] getGridsMain() {
		return Grid;
	}
	
	public int getGridMainCount() {
		return Grid.length;
	}
	
	//todas as casas da grid principal que tem caminho alternativo
	public List<Celula> getComecos() {
	    List<Celula> comecos = new ArrayList<>();
	    for (Caminho grid : GridAlternativo) {
	        comecos.add(grid.getComeco());
	    }
	    return comecos;
	}
	
	public Celula getCelAlternativo(int posicao, int idCaminho) {
		Caminho caminho = getGridAlternativo(idCaminho);
		return caminho != null ? caminho.getCelula(posicao) : null;
	}
	
	public Caminho getGridAlternativo(int idCaminho) {
		for(Caminho caminho : GridAlternativo) {
			if(caminho.getId()==idCaminho) {
				return caminho;
			}
		}
		return null;
	}
}
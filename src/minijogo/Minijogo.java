package minijogo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Minijogo {
	private List<Jogo> minijogos;
	
	public Minijogo() {
		minijogos = new ArrayList<>();
		minijogos.add(new Pesca());
	}
	
	public void comecaMinijogo() {
		Collections.shuffle(minijogos);
		
		Jogo jogo = minijogos.get(0);
		
		jogo.comecar();
		
	}
}

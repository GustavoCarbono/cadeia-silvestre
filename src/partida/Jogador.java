package partida;

import java.awt.Color;

public class Jogador {
	private String jogador;
	private Animal animal; //apenas 1 animal por jogador
	private Color cor;
	
	public Jogador(String jogador, Animal animal) {
		this.jogador = jogador;
		this.animal = animal;
	}

	public String getJogador() {
		return jogador;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public Color getCor() {
		return cor;
	}

	public void setCor(Color cor) {
		this.cor = cor;
	}
}

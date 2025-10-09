package partida;

import java.util.ArrayList;
import java.util.List;

public class Celula {
	private int caminhoId;
	private int x;
	private List<Animal> animais;
	
	private Celula caminhoAlternativo;
	private boolean outroCaminho;
	
	private Celula retornoCaminho;
	
	public Celula(int caminhoId, int x) {
		this.caminhoId = caminhoId;
		this.x = x;
		this.animais = new ArrayList<>();
	}
	
	public int getCaminhoId() {
		return caminhoId;
	}
	
	public void setCaminhoAlternativo(Celula caminho) {
		this.caminhoAlternativo = caminho;
		this.outroCaminho = true;
	}
	
	public Celula getCaminhoAlternativo() {
		return caminhoAlternativo;
	}
	
	public boolean getOutroCaminho() {
		return outroCaminho;
	}
	
	public void setRetornoCaminho(Celula caminho) {
		this.retornoCaminho = caminho;
	}
	
	public Celula getRetornoCaminho() {
		return retornoCaminho;
	}
	
	public int getX() {
		return x;
	}
	
	public Animal getAnimal(int id) {
		if (!animais.isEmpty()) { // ver se tem animais na casa
			for(Animal animal : animais) {
				if (animal.getId() == id) { // comparar id do animal
					return animal;
				}
			}
			System.out.println("Não foi possivel achar o animal1");
			return null;
		} else {
			System.out.println("Não tem animal nessa casa2");
			return null;
		}
	}
	
	public List<Animal> getAnimais() {
		return animais;				//retorna todos os animais
	}
	
	public void setAnimal(Animal animal, int id) {
		if (!animais.isEmpty()) { 	// ver se tem animais na casa
			for(Animal animalUni : animais) {
				if (animalUni.getId() == id) { // pega o animal procurado
					animalUni = animal;
				}
			}
		} else {
			System.out.println("Não tem animal nessa casa");
		}
	}
	
	public void addAnimal(Animal animal) {
		animais.add(animal);
	}
	
	public void removeAnimal(int id) {
		if (!animais.isEmpty()) { // ver se tem animais na casa
			if(!animais.removeIf(animal -> animal.getId() == id)) { // Remove animal por id e ver se foi sucesso a remoção
				System.out.println("Não foi possivel achar o animal");
			}
		} else {
			System.out.println("Não tem animal nessa casa");
		}
	}
	
	public boolean temJogador() {
		for(Animal animal : animais) {
			if(animal.getDono() != null) {
				return true;
			}
		}
		return false;
	}
}
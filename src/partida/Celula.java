package partida;

import java.util.ArrayList;
import java.util.List;

public class Celula {
	public int x;
	private List<Animal> animais;
	
	public Celula(int x) {
		this.x = x;
		this.animais = new ArrayList<>();
	}
	
	public Animal getAnimal(int id) {
		if (!animais.isEmpty()) { // ver se tem animais na casa
			for(Animal animal : animais) {
				if (animal.getId() == id) { // comparar id do animal
					return animal;
				}
			}
			System.out.println("Não foi possivel achar o animal");
			return null;
		} else {
			System.out.println("Não tem animal nessa casa");
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
					animalUni.setNome(animal.getNome());
					animalUni.setEvolucao(animal.getEvolucao());
				}
			}
			System.out.println("Não foi possivel achar o animal");
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
}
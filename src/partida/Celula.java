package partida;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Celula extends JPanel {
	public int x;
	private List<Animal> animais;
	public int id;

	
	public Celula(int x) {
		this.x = x;
		this.animais = new ArrayList<>();
	}
	
	//criacao de celulas na interface
	public Celula(int posX, int posY, int tamanho) {
		setBounds(posX, posY, tamanho, tamanho);
        setBackground(new Color(0, 160, 0));
        setOpaque(true);

        this.animais = new ArrayList<>();
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
					animalUni.setNome(animal.getNome());
					animalUni.setEvolucao(animal.getEvolucao());
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
}
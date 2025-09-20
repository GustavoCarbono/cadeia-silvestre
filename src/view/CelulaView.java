package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class CelulaView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Posicao posCelula;
	private List<AnimalView> animais;
	
	public CelulaView() {
		
	}
	public CelulaView(int posX, int posY, int width, int height) {
		posCelula = new Posicao(posX, posY);
		animais = new ArrayList<>();
		setBounds(posX, posY, width, height);
        setBackground(new Color(0, 160, 0));
        setOpaque(true);
	}
	
	public Posicao getPosCelula() {
		return posCelula;
	}
	
	public List<AnimalView> getAnimais() {
		return animais;
	}
	
	public AnimalView getAnimal(int id) {
		for(AnimalView animal : animais) {
			if (animal.getAnimal().getId() == id) { // comparar id do animal
				return animal;
			}
		}
		System.out.println("Não foi possivel achar o animal");
		return null;
	}
	
	public void setAnimais(AnimalView animal, int id) {
		if (!animais.isEmpty()) { 	// ver se tem animais na casa
			for(int i = 0; i < animais.size(); i++) {
				AnimalView animalUni = animais.get(i);
				if (animalUni.getAnimal().getId() == id) { // pega o animal procurado
					remove(animalUni);
					animal.set(i, animal);
					add(animal);
					revalidate();
	                repaint();
	                return;
				}
			}
		} else {
			System.out.println("Não tem animal nessa casa");
		}
	}
	
	public void addAnimal(AnimalView animal) {
		animais.add(animal);
		add(animal);
		revalidate();
		repaint();
	}
	
	public void removeAnimal(int id) {
		for(AnimalView animal : animais) {
			if(animal.getAnimal().getId() == id) {
				animais.remove(animal);
				remove(animal);
				revalidate();
				repaint();
			}
		}
	}
	
	public void setPosCelula(Posicao posCelula) {
		this.posCelula = posCelula;
	}
}

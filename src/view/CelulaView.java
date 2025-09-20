package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import partida.Celula;

public class CelulaView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Posicao posCelula;
	private Celula celula;
	private List<AnimalView> animais;
	
	public CelulaView(int posX, int posY, int width, int height, Celula celula) {
		this.celula = celula;
		posCelula = new Posicao(posX, posY);
		animais = new ArrayList<>();
		setBounds(posX, posY, width, height);
        setBackground(new Color(0, 160, 0));
        setOpaque(true);
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
					remove(animalUni.getLabel());
					animais.set(i, animal);
					add(animal.getLabel());
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
		if (getAnimal(animal.getAnimal().getId()) == null) {
			animais.add(animal);
			add(animal.getLabel());
			revalidate();
			repaint();
		}
	}
	
	public void removeAnimal(int id) {
		Iterator<AnimalView> iterator = animais.iterator();
	    while(iterator.hasNext()) {
	        AnimalView animal = iterator.next();
	        if(animal.getAnimal().getId() == id) {
	            iterator.remove(); // remove da lista
	            remove(animal.getLabel()); // remove do painel
	            revalidate();
	            repaint();
	            return;
	        }
	    }
	}
	
	public void setPosCelula(Posicao posCelula) {
		this.posCelula = posCelula;
	}
	
	public Celula getCelula() {
		return celula;
	}
}

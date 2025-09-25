package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import partida.Animal;

public class AnimalView {
	private Animal animal;
	private JLabel label;
	private ImageIcon icon;
	
	public AnimalView(Animal animal, int largura, int altura) {
        this.animal = animal;
        
         icon = new ImageIcon(getClass().getResource(animal.getImg()));
        this.label = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH)));
	}
	
	public Animal getAnimal() {
		 return animal;
	}

	public JLabel getLabel() {
		return label;
	}
	
	public void atualizaImg(String animalImg, int largura, int altura) {
		icon = new ImageIcon(getClass().getResource(animalImg));
        this.label = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH)));
	}
}

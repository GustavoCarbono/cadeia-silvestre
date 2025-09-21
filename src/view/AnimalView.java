package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import partida.Animal;

public class AnimalView {
	private Animal animal;
	private JLabel label;
	
	public AnimalView(Animal animal, int largura, int altura) {
        this.animal = animal;
        
        ImageIcon icon = new ImageIcon(getClass().getResource(animal.getImg()));
        this.label = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH)));
	}
	
	public Animal getAnimal() {
		 return animal;
	}

	public JLabel getLabel() {
		return label;
	}
}

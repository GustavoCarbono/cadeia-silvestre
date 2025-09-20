package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import partida.Animal;

public class AnimalView {
	private Animal animal;
	private JLabel label;
	private int offsetX;
	private int offsetY;
	
	public AnimalView(Animal animal, int largura, int altura, int offsetX, int offsetY) {
        this.animal = animal;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        
        ImageIcon icon = new ImageIcon(getClass().getResource(animal.getImg()));
        this.label = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH)));
	}
	
	public Animal getAnimal() {
		 return animal;
	}

	public JLabel getLabel() {
		return label;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}
	
	public void setPosicao(int x, int y) {
		label.setLocation(x+offsetX, y+offsetY);
	}
}

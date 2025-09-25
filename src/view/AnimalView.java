package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import partida.Animal;

public class AnimalView {
	private Animal animal;
	private JLabel label;
	
	public AnimalView(Animal animal, int largura, int altura) {
        this.animal = animal;
        
        this.label = new JLabel();
        atualizaImg(animal, largura, altura);
	}
	
	public Animal getAnimal() {
		 return animal;
	}

	public JLabel getLabel() {
		return label;
	}
	
	public void atualizaImg(Animal animal, int largura, int altura) {
		this.animal = animal;
		
		ImageIcon icon = carregarImagem(animal, largura, altura);
        this.label.setIcon(icon);
        this.label.revalidate(); // força o layout a se ajustar
        this.label.repaint();
	}
	
	public ImageIcon carregarImagem(Animal animal, int largura, int altura) {
		ImageIcon icon = new ImageIcon(getClass().getResource(animal.getImg()));
		return new ImageIcon(icon.getImage().getScaledInstance(largura, altura, java.awt.Image.SCALE_SMOOTH));
	}
}

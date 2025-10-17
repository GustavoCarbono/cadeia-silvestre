package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import partida.Celula;

public class CelulaView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Posicao posCelula;
	private Celula celula;
	private List<AnimalView> animais;
	private String presaNome;
	private String predadorNome;
	private String quiz;
	
	private Image backgroundImage;
	private Image overlayImage;
	private Image pinImage;
	
	public CelulaView(int posX, int posY, int width, int height, Celula celula) {
		this.celula = celula;
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
		return null;
	}
	
	public void setAnimal(AnimalView animal, int id) {
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
	
	 public void setBackgroundImage(String imagePath) {
	        backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
	        repaint();
	    }

	 public void setOverlayImage(String imagePath) {
	        overlayImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
	        repaint();
	    }

	 public void setPinImage(String imagePath) {
		    pinImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
		    repaint();
		}


	 @Override
	 protected void paintComponent(Graphics g) {
	     super.paintComponent(g);

	     if (backgroundImage != null) {
	         int panelW = getWidth();
	         int panelH = getHeight();

	         int imgW = backgroundImage.getWidth(this);
	         int imgH = backgroundImage.getHeight(this);

	 
	         double scale = Math.max(
	             (double) panelW / imgW,
	             (double) panelH / imgH
	         );

	         int newW = (int) (imgW * scale);
	         int newH = (int) (imgH * scale);


	         int x = (panelW - newW) / 2;
	         int y = (panelH - newH) / 2;

	         g.drawImage(backgroundImage, x, y, newW, newH, this);
	     } else {
	         g.setColor(getBackground());
	         g.fillRect(0, 0, getWidth(), getHeight());
	     }
	    
	     if (overlayImage != null) {
	    	    int panelW = getWidth();
	    	    int panelH = getHeight();

	    	    int imgW = overlayImage.getWidth(this);
	    	    int imgH = overlayImage.getHeight(this);

	    	    double scale = Math.min(
	    	        (double) panelW / imgW,
	    	        (double) panelH / imgH
	    	    ) * 0.8;

	    	    int newW = (int) (imgW * scale);
	    	    int newH = (int) (imgH * scale);

	    	    // Center the overlay
	    	    int x = (panelW - newW) / 2;
	    	    int y = (panelH - newH) / 2;

	    	    g.drawImage(overlayImage, x, y, newW, newH, this);
	    	}
	     
	     if (pinImage != null) {
	         int imgW = pinImage.getWidth(this);
	         int imgH = pinImage.getHeight(this);

	         // scale to 20% of panel width (keep proportion)
	         double scale = Math.min(
	             (double) getWidth() * 0.22 / imgW,
	             (double) getHeight() * 0.22 / imgH
	         );

	         int newW = (int) (imgW * scale);
	         int newH = (int) (imgH * scale);

	         // small margin from edges
	         int x = 9;
	         int y = 9;

	         g.drawImage(pinImage, x, y, newW, newH, this);
	     }
	    }
	 
	public void setPosCelula(Posicao posCelula) {
		this.posCelula = posCelula;
	}
	
	public void setPresa(String presaNome) {
		this.presaNome = presaNome;
	}
	
	public String getPresa() {
		return presaNome;
	}
	
	public Celula getCelula() {
		return celula;
	}

	public String getPredadorNome() {
		return predadorNome;
	}

	public void setPredadorNome(String predadorNome) {
		this.predadorNome = predadorNome;
	}

	public String getQuiz() {
		return quiz;
	}

	public void setQuiz(String quiz) {
		this.quiz = quiz;
	}
	
	
	
}

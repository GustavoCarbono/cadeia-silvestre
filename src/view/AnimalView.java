package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import partida.Animal;

public class AnimalView {
    private Animal animal;
    private JLabel label;
    private String fundoPath;
    private int largura, altura;

    public AnimalView(Animal animal, int largura, int altura, String fundoPath) {
        this.animal = animal;
        this.largura = largura;
        this.altura = altura;
        this.fundoPath = fundoPath;
        this.label = new JLabel();
        atualizarImagem();
    }

    public Animal getAnimal() {
        return animal;
    }

    public JLabel getLabel() {
        return label;
    }

    /** Atualiza completamente a imagem do animal (por exemplo, quando evolui). */
    public void atualizarImagem() {

        ImageIcon icon = carregarImagem(fundoPath, animal.getImg(), largura, altura);
        label.setIcon(icon);
        label.revalidate();
        label.repaint();
    }

    public void setAnimal(Animal novoAnimal) {
        this.animal = novoAnimal;
        atualizarImagem();
    }

    public void setFundo(String novoFundoPath) {
        this.fundoPath = novoFundoPath;
        atualizarImagem();
    }

    private ImageIcon carregarImagem(String fundoPath, String animalPath, int largura, int altura) {
        try {
            BufferedImage fundo = ImageIO.read(getClass().getResource(fundoPath));
            BufferedImage animal = ImageIO.read(getClass().getResource(animalPath));

            Image scaledFundo = fundo.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);


            int animalW = (int) (largura * 0.72);
            int animalH = (int) (altura * 0.72);
            Image scaledAnimal = animal.getScaledInstance(animalW, animalH, Image.SCALE_SMOOTH);


            int x = (largura - animalW) / 2;
            int y = (altura - animalH) / 2;

            BufferedImage combined = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = combined.createGraphics();

            g.drawImage(scaledFundo, 0, 0, null);
            g.drawImage(scaledAnimal, x, y, null);
            g.dispose();

            return new ImageIcon(combined);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

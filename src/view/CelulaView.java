package view;

import java.awt.Color;

import javax.swing.JLabel;

public class CelulaView extends JLabel {
	public int posCelula;
	public int numAnimaisNaCasa;
	
	public CelulaView() {
		
	}
	public CelulaView(int posX, int posY, int width, int height) {
		setBounds(posX, posY, width, height);
        setBackground(new Color(0, 160, 0));
        setOpaque(true);
	}
}

package minijogo1;

import javax.swing.*;

public class InterfaceMiniJogo extends JFrame {

	
	public InterfaceMiniJogo() {
		add(new Fase());
		setTitle("Mini jogo 1");
		setSize(1024,728);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		this.setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new InterfaceMiniJogo();
	}

}

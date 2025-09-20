package minijogo;

import java.awt.event.*;

import javax.swing.JFrame;

public class Pesca extends JFrame implements Jogo, KeyListener {
	
	
	public Pesca() {
		
	}
	
	@Override
	public void comecar() {
		
	}
	
	@Override
	 public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());
	}
	
	@Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
}

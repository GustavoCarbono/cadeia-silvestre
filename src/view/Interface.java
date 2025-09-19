package view;

import javax.swing.*;
import java.awt.*;

public class Interface extends JFrame {
    
	private JLayeredPane layeredPane;

    private Posicao posicao = new Posicao();
    private CelulaView celulaBase = new CelulaView();
	private CelulaView celulas[];

    
    // ComboBox para curso
    
    // CheckBoxes para matérias
    
    public Interface() {
    	
    	
        // Configurações da janela
    	setExtendedState(JFrame.MAXIMIZED_BOTH);
    	setTitle("Pergunta 1");
        setSize(400,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(92, 64, 51));
       
        layeredPane = new JLayeredPane();
    	layeredPane.setBounds(0, 0, 1920, 1080);
    	layeredPane.setLayout(null); // absolute positioning
    	getContentPane().add(layeredPane);
    	
        celulas = new CelulaView[70];
        
        celulaBase.posCelula = 1;
        
        criarTabuleiro();
        
        ImageIcon macacoOG = new ImageIcon(getClass().getResource("/images/monkey.png"));
        Image macacoT = macacoOG.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);
        ImageIcon macaco = new ImageIcon(macacoT);
        JLabel macacoLabel = new JLabel(macaco);
   
        ImageIcon emaOG = new ImageIcon(getClass().getResource("/images/ema.png"));
        Image emaT = emaOG.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);
        ImageIcon ema = new ImageIcon(emaT);
        JLabel emaLabel = new JLabel(ema);

        
        
        adicionarAnimalACasa(macacoLabel, 0);
        
        adicionarAnimalACasa(emaLabel, 0);

        
        setVisible(true); 
        
        
    }
    
  
    public void adicionarAnimalACasa(JLabel animal, int idCasa) {
    	
    	if(celulas[idCasa].numAnimaisNaCasa <= 0) {
    		animal.setBounds(celulas[idCasa].getX(), celulas[idCasa].getY(), 40, 30);
    	}
    	else if(celulas[idCasa].numAnimaisNaCasa == 1) {
    		animal.setBounds((celulas[idCasa].getX() + 40), celulas[idCasa].getY(), 40, 30);
    	}
    	else if(celulas[idCasa].numAnimaisNaCasa == 2) {
    		animal.setBounds(celulas[idCasa].getX(), celulas[idCasa].getY() + 40, 40, 30);
    	}
    	else if(celulas[idCasa].numAnimaisNaCasa == 3) {
    		animal.setBounds(celulas[idCasa].getX() + 40, celulas[idCasa].getY() + 40, 40, 30);
    	}
    	else {
    		System.out.println("Erro");
    	}
    	
		layeredPane.add(animal, Integer.valueOf(2));
		System.out.println(celulas[idCasa].numAnimaisNaCasa);
		celulas[idCasa].numAnimaisNaCasa++;
		System.out.println(celulas[idCasa].numAnimaisNaCasa);
    }
    
    public void criarTabuleiro() {
    	posicao.posX = 250;
    	posicao.posY = 712;
    	
    	criarCelulas(1, posicao.posX, posicao.posY, "I");
    	criarCelulas(6, posicao.posX, posicao.posY, "C");
    	criarCelulas(2, posicao.posX, posicao.posY, "E");
    	criarCelulas(2, posicao.posX, posicao.posY, "B");
    	criarCelulas(7, posicao.posX, posicao.posY, "D");
    	criarCelulas(2, posicao.posX, posicao.posY, "C");
    	criarCelulas(2, posicao.posX, posicao.posY, "E");
    	criarCelulas(6, posicao.posX, posicao.posY, "B");
    	criarCelulas(2, posicao.posX, posicao.posY, "D");
    	criarCelulas(2, posicao.posX, posicao.posY, "C");
    	criarCelulas(7, posicao.posX, posicao.posY, "E");
    	criarCelulas(2, posicao.posX, posicao.posY, "B");
    	criarCelulas(1, posicao.posX, posicao.posY, "D");
    	
    }
    

    public Posicao criarCelulas(int qtdCelulas, int ultimoX, int ultimoY, String direcao) {
    	
    	int gap =116;
    	int largura = 115;
    	int altura = 115;
    	
    	switch(direcao) {
    	
	    	case "D":
	    		for(int i = 0; i < qtdCelulas; i++) {
	            	
	            	posicao.posX = ultimoX + gap * (i + 1);
	            	
	            	celulas[celulaBase.posCelula] = new CelulaView(posicao.posX, ultimoY, altura, largura);
	    	        	        
	    	        layeredPane.add(celulas[celulaBase.posCelula],  Integer.valueOf(1));
	    	        celulaBase.posCelula++;
	            
	        	}
	    		
	    		break;
	    		
	    	case "E":
	    		for(int i = 0; i < qtdCelulas; i++) {
	            	posicao.posX = ultimoX - gap * (i + 1);
	            	celulas[celulaBase.posCelula] = new CelulaView(posicao.posX, ultimoY, altura, largura);
	    	           
	    	        layeredPane.add(celulas[celulaBase.posCelula],  Integer.valueOf(1));
	    	        celulaBase.posCelula++;
	            
	        	}
	    		break;
	    		
	    	case "C":
	    		for(int i = 0; i < qtdCelulas; i++) {
	            	posicao.posY = ultimoY - gap * (i + 1);

	    	        celulas[celulaBase.posCelula] = new CelulaView(ultimoX, posicao.posY, altura, largura);
	    	        
	    	        layeredPane.add(celulas[celulaBase.posCelula],  Integer.valueOf(1));
	    	        celulaBase.posCelula++;
	            
	        	}
	    		break;
	    		
	    	case "B":
	    		for(int i = 0; i < qtdCelulas; i++) {
	    			posicao.posY = ultimoY + gap * (i + 1);
	    			
	    	        celulas[celulaBase.posCelula] = new CelulaView(ultimoX, posicao.posY, altura, largura);
	    	 
	    	        layeredPane.add(celulas[celulaBase.posCelula],  Integer.valueOf(1));
	           
	    	        celulaBase.posCelula++;
	    		}
	    		break;
	    		
	    	case "I":
    	        celulas[0] = new CelulaView();
    	        celulas[0].setBackground(new Color(150, 230, 150));	        
    	        celulas[0].setOpaque(true);
    	        celulas[0].setBounds(ultimoX, posicao.posY ,altura, largura);
    	        
    	        
    	        layeredPane.add(celulas[0],  Integer.valueOf(1));
	    		
    	}
    	
    	return posicao;
	}
    


	
    public static void main(String[] args) {
		Interface gui = new Interface();
	}

}



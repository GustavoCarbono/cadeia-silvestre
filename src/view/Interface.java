package view;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import partida.Animal;
import partida.Celula;
import partida.Jogador;

public class Interface extends JFrame {
    
    private Posicao posicao = new Posicao();
    private CelulaView[] celulas;
	private CelulaView celulaBase = new Celula();
	private JLayeredPane layeredPane = new JLayeredPane();
	private int CelulaLargura;
	private int CelulaAltura;
    
    public Interface(List<Jogador> jogadores, int x) {
    	
    	 celulas = new CelulaView[x];
    	
    	CelulaLargura = 115;
    	CelulaAltura = 115;
    	
    	configurarJanela();
    	if(!(jogadores.size()>1&&jogadores.size()<5)) return;
    	List<AnimalView> animalJogador = new ArrayList<>();
    	int[][] offset = new int[][] {
    		{10, 10},
    		{10, 50},
    		{50, 10},
    		{50, 50}
    	};
    	int i = 0;
    	for(Jogador jogador : jogadores) {
    		
    	}
    	
        celulas = new CelulaView[26];
        
        criarTabuleiro();

        JButton btn = new JButton("Rolar Dado (agora funcional)");
		btn.addActionListener(e -> );
		btn.setBounds(1200, 100, 300, 50);
		btn.setBackground(new Color(100, 100, 100));
		btn.setOpaque(true);
		layeredPane.add(btn);
        
        adicionarAnimalACasa(macacoLabel, 0);
        
        adicionarAnimalACasa(emaLabel, 0);

        setVisible(true);
        
    }
    
    public void configurarJanela() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
    	setTitle("Pergunta 1");
        setSize(1920,1080);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(null);
        getContentPane().setBackground(new Color(74, 42, 18));
        
        setVisible(true);

    	layeredPane.setBounds(0, 0, 1920, 1080);
    	layeredPane.setLayout(null); 
    	add(layeredPane);
	}

    public void atualizarAnimalNaTela(AnimalView animal, int antigaPos, int novaPos) {
		//pega a celula que foi retornada do mov.mover
		CelulaView oldCell = celulas[antigaPos + 1];
		
		//remove a imagem da celula
		oldCell.remove(animal); 
		
		//mesmo do de baixo
		oldCell.revalidate();
		
		//repainta o quadrado pra atualizar agora que foi removido o bicho
		oldCell.repaint();		
		
		//isso reseta o cache do java pra imagem não continuar viva depois do repaint
		SwingUtilities.updateComponentTreeUI(layeredPane);
	


		 CelulaView newCell = celulas[novaPos + 1];
		 newCell.add(animal.img); 
		 
		 newCell.revalidate();
		 newCell.repaint();
	
		 SwingUtilities.updateComponentTreeUI(layeredPane);
	    
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

public void criarCelulas(int qtdCelulas, int ultimoX, int ultimoY, String direcao) {
    	
    	int gap = 111;
    	int tamanho = 110;
    	
    	switch(direcao) {
    	
	    	case "D":
	    		for(int i = 0; i < qtdCelulas; i++) {            	
	    			//essa classe de posição é pra poder usar ela no codigo todo com um valor mais flexivel
	            	posicao.posX = ultimoX + gap * (i + 1);	            	
	            	
	            	//celulabase.id é so pra usar como numerador de 0 a 25 pras celulas
	            	celulas[celulaBase.id] = new Celula(posicao.posX, ultimoY, tamanho);
	            	
	            	//layeredpane bota as imagem em cima dos label
	    	        layeredPane.add(celulas[celulaBase.id],  Integer.valueOf(1));	    	        
	    	        celulaBase.id++;
	        	}
	    		
	    		break;
	    		
	    	case "E":
	    		for(int i = 0; i < qtdCelulas; i++) {
	    	        posicao.posX = ultimoX - gap * (i + 1);
	            	celulas[celulaBase.id] = new Celula(posicao.posX, ultimoY, tamanho);
	    	        layeredPane.add(celulas[celulaBase.id],  Integer.valueOf(1));
	    	        celulaBase.id++;     
	        	}
	    		
	    		break;
	    		
	    	case "C":
	    		for(int i = 0; i < qtdCelulas; i++) { 
	    	        posicao.posY = ultimoY - gap * (i + 1);
	            	celulas[celulaBase.id] = new Celula(ultimoX, posicao.posY, tamanho);
	    	        layeredPane.add(celulas[celulaBase.id],  Integer.valueOf(1));	    	        
	    	        celulaBase.id++;
	        	}
	    		
	    		break;
	    		
	    	case "B":
	    		for(int i = 0; i < qtdCelulas; i++) {
	    			posicao.posY = ultimoY + gap * (i + 1);	            	
	            	celulas[celulaBase.id] = new Celula(ultimoX, posicao.posY, tamanho);
	    	        layeredPane.add(celulas[celulaBase.id],  Integer.valueOf(1));	    	        
	    	        celulaBase.id++;
	    		}
	    		
	    		break;
	    		
    	}
    	
	}
    
}



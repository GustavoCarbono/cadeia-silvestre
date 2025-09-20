package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import partida.Jogador;
import partida.Tabuleiro;

public class Interface extends JFrame {
    
    private Posicao posicao;
    private CelulaView[] celulas;
	private int indexBase = 0;
	private JLayeredPane layeredPane = new JLayeredPane();
	private int CelulaLargura;
	private int CelulaAltura;
    
    public Interface(List<Jogador> jogadores, int x, Tabuleiro tabuleiro) {
    	
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
    		animalJogador.add(new AnimalView(jogador.getAnimal(), 40, 40, offset[i][0], offset[i][1]));
    		i++;
    	}
    	
        criarTabuleiro();

        JButton btn = new JButton("Rolar Dado (agora não funcional)");
		//btn.addActionListener(e -> );
		btn.setBounds(1200, 100, 300, 50);
		btn.setBackground(new Color(100, 100, 100));
		btn.setOpaque(true);
		layeredPane.add(btn);

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
		oldCell.removeAnimal(animal.getAnimal().getId()); 
		
		//mesmo do de baixo
		oldCell.revalidate();
		
		//repainta o quadrado pra atualizar agora que foi removido o bicho
		oldCell.repaint();		
		
		//isso reseta o cache do java pra imagem não continuar viva depois do repaint
		SwingUtilities.updateComponentTreeUI(layeredPane);
	


		 CelulaView newCell = celulas[novaPos + 1];
		 newCell.add(animal.getLabel()); 
		 
		 newCell.revalidate();
		 newCell.repaint();
	
		 SwingUtilities.updateComponentTreeUI(layeredPane);
	    
	}
    
    public void criarTabuleiro() {
    	posicao = new Posicao(250, 730);
    	
    	indexBase = 1;
    	
    	criarCelulas(7, posicao.getPosX(), posicao.getPosY(), "C");
    	criarCelulas(6, posicao.getPosX(), posicao.getPosY(), "D");
    	criarCelulas(6, posicao.getPosX(), posicao.getPosY(), "B");
    	criarCelulas(5, posicao.getPosX(), posicao.getPosY(), "E");
    	
    }

public void criarCelulas(int qtdCelulas, int ultimoX, int ultimoY, String direcao) {
    	
    	int gap = 101;
    	int tamanho = 100;
    	
    	switch(direcao) {
    	
	    	case "D":
	    		for(int i = 0; i < qtdCelulas; i++) {            	
	    			//essa classe de posição é pra poder usar ela no codigo todo com um valor mais flexivel
	            	posicao.setPosX(ultimoX + gap * (i + 1));            	
	            	
	            	//celulabase.id é so pra usar como numerador de 0 a 25 pras celulas
	            	celulas[indexBase] = new CelulaView(posicao.getPosX(), ultimoY, tamanho, tamanho);
	            	
	            	//layeredpane bota as imagem em cima dos label
	    	        layeredPane.add(celulas[indexBase],  Integer.valueOf(1));	    	        
	    	        indexBase++;
	        	}
	    		
	    		break;
	    		
	    	case "E":
	    		for(int i = 0; i < qtdCelulas; i++) {
	    			posicao.setPosX(ultimoX - gap * (i + 1));
	            	celulas[indexBase] = new CelulaView(posicao.getPosX(), ultimoY, tamanho, tamanho);
	    	        layeredPane.add(celulas[indexBase],  Integer.valueOf(1));
	    	        indexBase++;     
	        	}
	    		
	    		break;
	    		
	    	case "C":
	    		for(int i = 0; i < qtdCelulas; i++) { 
	    			posicao.setPosY(ultimoY - gap * (i + 1));
	            	celulas[indexBase] = new CelulaView(ultimoX, posicao.getPosY(), tamanho, tamanho);
	    	        layeredPane.add(celulas[indexBase],  Integer.valueOf(1));	    	        
	    	        indexBase++;
	        	}
	    		
	    		break;
	    		
	    	case "B":
	    		for(int i = 0; i < qtdCelulas; i++) {
	    			posicao.setPosY(ultimoY + gap * (i + 1));	            	
	            	celulas[indexBase] = new CelulaView(ultimoX, posicao.getPosY(), tamanho, tamanho);
	    	        layeredPane.add(celulas[indexBase],  Integer.valueOf(1));	    	        
	    	        indexBase++;
	    		}
	    		
	    		break;
	    		
    	}
    	
	}
    
}



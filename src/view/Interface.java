package view;


import java.awt.Color;
import java.awt.Image;
import java.util.*;
import javax.swing.*;


import model.DAO;
import partida.Animal;
import partida.Celula;
import partida.Jogador;
import partida.Partida;
import utils.Movimentacao;
import utils.Posicoes;

public class Interface extends JFrame {

	DAO dao = new DAO();
	List<Jogador> jogadores = new ArrayList<>();
	Movimentacao mov = new Movimentacao();
	JLayeredPane layeredPane = new JLayeredPane();
	Celula celulas[] = new Celula[26];
	Celula celulaBase = new Celula(0);
	Posicao posicao = new Posicao();
	Partida partida;
	
	public Interface() {

		dao.conectar();
		configurarJanela();
		
		//um monte de imagem (quatro)
		ImageIcon macacoOG = new ImageIcon(getClass().getResource("/images/monkey.png"));
        Image macacoT = macacoOG.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon macaco = new ImageIcon(macacoT);
        JLabel macacoLabel = new JLabel(macaco);
   
        ImageIcon emaOG = new ImageIcon(getClass().getResource("/images/ema.png"));
        Image emaT = emaOG.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon ema = new ImageIcon(emaT);
        JLabel emaLabel = new JLabel(ema);

        ImageIcon pumaOG = new ImageIcon(getClass().getResource("/images/puma.png"));
        Image pumaT = pumaOG.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon puma = new ImageIcon(pumaT);
        JLabel pumaLabel = new JLabel(puma);

        ImageIcon elefanteOG = new ImageIcon(getClass().getResource("/images/elefante.png"));
        Image elefanteT = elefanteOG.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon elefante = new ImageIcon(elefanteT);
        JLabel elefanteLabel = new JLabel(elefante);
        

        //botei a imagem num construtor de animal pra usar depois como animal.img
		jogadores.add(new Jogador("p1", new Animal(1, "macaco", "gorila", 20, "p1", macacoLabel)));
		jogadores.add(new Jogador("p2", new Animal(2, "ema", "avestruz", 20, "p2", emaLabel)));
		jogadores.add(new Jogador("p3", new Animal(3, "puma", "leão", 20, "p3", pumaLabel)));
		jogadores.add(new Jogador("p4", new Animal(4, "elefante-pigmeu-de-Bornéu", "elefante africano", 20, "p4", elefanteLabel)));

		partida = new Partida(24, jogadores);
		criarTabuleiro();
		
		JButton btn = new JButton("Rolar Dado (agora funcional)");
		btn.addActionListener(e -> jogar());
		btn.setBounds(1200, 100, 300, 50);
		btn.setBackground(new Color(100, 100, 100));
		btn.setOpaque(true);
		layeredPane.add(btn);
		
		//inicializa todos os animais na primeira celula
		 for(int i = 0; i<4; i++) {
	        celulas[1].add(jogadores.get(i).getAnimal().img);
	        celulas[1].repaint();
	        celulas[1].revalidate();
	      }
	         

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
	
	public void jogar() {
		if(!partida.getFinalizou() || partida.getTurnoAtual() < 20) {

			//botei as informações mais importantes numa classe (nome do bicho, posicao antiga e posicao atual, pra usar dps
			Posicoes posicoes = mov.mover(partida.getOrdemJogador().get(0), partida, dao);
			
			//atualiza a interface
			atualizarAnimalNaTela(posicoes.animal, posicoes.antigaPos, posicoes.novaPos);
			
			partida.getOrdemJogador().get(0).getAnimal();
			partida.aumentarTurnoAtual();
			partida.mudarOrdemJogador();
			
			if(partida.getTurnoAtual()%8 == 0) {
				//metodo que começa um minijogo
			
			} 
		}
	}
	
	public void atualizarAnimalNaTela(Animal animal, int antigaPos, int novaPos) {
		//pega a celula que foi retornada do mov.mover
		Celula oldCell = celulas[antigaPos + 1];
		
		//remove a imagem da celula
		oldCell.remove(animal.img); 
		
		//mesmo do de baixo
		oldCell.revalidate();
		
		//repainta o quadrado pra atualizar agora que foi removido o bicho
		oldCell.repaint();		
		
		//isso reseta o cache do java pra imagem não continuar viva depois do repaint
		SwingUtilities.updateComponentTreeUI(layeredPane);
	


		 Celula newCell = celulas[novaPos + 1];
		 newCell.add(animal.img); 
		 
		 newCell.revalidate();
		 newCell.repaint();
	
		 SwingUtilities.updateComponentTreeUI(layeredPane);
	    
	}
	
	public void criarTabuleiro() {
        
        celulaBase.id = 1;
        
        //posicao do primeiro quadrado
        posicao.posX = 50;
    	posicao.posY = 785;
    	
    	criarCelulas(7, posicao.posX, posicao.posY, "C");
    	criarCelulas(6, posicao.posX, posicao.posY, "D");
    	criarCelulas(6, posicao.posX, posicao.posY, "B");
    	criarCelulas(5, posicao.posX, posicao.posY, "E");
    	

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

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

import model.DAO;
import model.PredacaoDAO;
import partida.Animal;
import partida.Celula;
import partida.Jogador;
import partida.Partida;
import utils.RolarDados;

public class Interface extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
    private CelulaView[] celulas;
	private JLayeredPane layeredPane = new JLayeredPane();
	private RolarDados rolarDados;
	private List<AnimalView> animalJogador;
	private DAO dao = new DAO();
	private List<Jogador> jogadores;
	private Partida partida;
	
	private JPanel panel = new JPanel(new GridBagLayout());
	private JPanel tabuleiro = new JPanel(new GridBagLayout()) { private Image background = new ImageIcon(
	        getClass().getResource("/images/fundo/fundo2.png")
		    ).getImage();

		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		    }}; 
    private JPanel subTabuleiro = new JPanel(null);  
    private JPanel subPanel = new JPanel(new GridBagLayout());
	
	private JPanel infoJogo = new JPanel(new GridLayout(2, 1, 1, 10)); 
    private JPanel acoesJogo = new JPanel(new GridLayout(2, 1, 1, 5));
    private JPanel jogadoresPanel = new JPanel(new GridLayout(4, 1, 1, 10));
    
    private JPanel[] infoJogadores = new JPanel[4];
    private JLabel[][] labelsJogadores = new JLabel[4][4]; 
    private JLabel numeroRodadoLabel;
    private JLabel jogadorAtualLabel;
    private JButton btnDado;
    

    private Color[] coresJogadores = {
            new Color(255, 100, 100),
            new Color(100, 255, 100),
            new Color(100, 100, 255),
            new Color(255, 255, 100)
    };
    
    private Color[] coresCampos = {
            new Color(255, 170, 170),
            new Color(170, 255, 170),
            new Color(170, 170, 255),
            new Color(255, 255, 170)
    };
    
    
	private Font bungeeFont;
    private Font fredokaFont;

    
    public Interface(List<Jogador> jogadores, int x, Partida partida) {
    	this.jogadores = jogadores;
    	this.partida = partida;
    	

    	try {
    	    // Load font from resources folder inside your project
    	    bungeeFont = Font.createFont(
    	        Font.TRUETYPE_FONT,
    	        getClass().getResourceAsStream("/fontes/Bungee-Regular.ttf")
    	    );
    	} catch (Exception e) {
    	    e.printStackTrace();
    	    bungeeFont = new Font("SansSerif", Font.PLAIN, 15); // fallback
    	}
    	
    	try {
    	    // Load font from resources folder inside your project
    	    fredokaFont = Font.createFont(
    	        Font.TRUETYPE_FONT,
    	        getClass().getResourceAsStream("/fontes/LilitaOne-Regular.ttf")
    	    );
    	} catch (Exception e) {
    	    e.printStackTrace();
    	    fredokaFont = new Font("SansSerif", Font.PLAIN, 15); // fallback
    	}
    	
    	configurarJanela();
    	celulas = new CelulaView[x];
    	
    	tabuleiro.setBackground(null);
    	tabuleiro.setOpaque(false);
    	
        tabuleiro.setPreferredSize(new Dimension((int) (getHeight() * 1.32), getHeight()));
        add(tabuleiro, BorderLayout.WEST);
    	
    	panel.setBackground(new Color(48, 176, 255));
        add(panel, BorderLayout.CENTER);
        
        atualizarInterface();
        
        subPanel.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.95), (int) (panel.getHeight() * 0.98)));
        subPanel.setBackground(new Color(255, 255, 255));
        panel.add(subPanel);
        
        subTabuleiro.setPreferredSize(new Dimension((int) (tabuleiro.getWidth() * 0.99), (int) (tabuleiro.getHeight() * 0.99)));
        subTabuleiro.setBackground(null);
        subTabuleiro.setOpaque(false);
        tabuleiro.add(subTabuleiro);
        
        atualizarInterface();
        
        preencherTabuleiro(subTabuleiro, partida);
        
        addInfoJogo_subPanel(subPanel, partida);
        addInfoAcoes_subPanel(subPanel);
        addInfoJogadores_subPanel(subPanel, partida);
        
		atualizarInterface();
		
		if(!(jogadores.size()>0&&jogadores.size()<5)) return;
    	animalJogador = new ArrayList<>();
    	for(Jogador jogador : jogadores) {
    		animalJogador.add(new AnimalView(jogador.getAnimal(), (celulas[0].getWidth() / 2) - 10, (celulas[0].getWidth() / 2) - 10, jogador.getAnimal().getCor().getImagemCor()));
    	}

        for(int i=0; i<animalJogador.size(); i++) {
        	celulas[0].addAnimal(animalJogador.get(i));
    	}
        
        for(int i = 0; i<celulas.length; i++) {
            celulas[i].setBackgroundImage("/images/fundo/casaComum.png");
        }
        
        String[] predadores = new String[4];

        int[] celulasPredadores = new int[4];
        
        celulasPredadores[0] = 4;
        celulasPredadores[1] = 11;
        celulasPredadores[2] = 18;
        celulasPredadores[3] = 25;
        
        for(int i = 0; i<celulasPredadores.length; i++) {
        	celulas[celulasPredadores[i]].setPredadorNome("predador");
        	celulas[celulasPredadores[i]].setBackgroundImage("/images/fundo/casaPredador.png");
        	celulas[celulasPredadores[i]].setOverlayImage("/images/fundo/predadorImagem.png");
        }
        // marcar visualmente a casa do QUIZ, se houver
        Integer quizPos = partida.getTabuleiro().getQuizPosicao();
        if (quizPos != null) {
        	celulas[quizPos].setOverlayImage("/images/MiniJogos/tubarão.png");
        }

        celulas[0].setBackgroundImage("/images/fundo/casaInicial.png");
  

        
     
		for(int j = 0; j < partida.getJogadores().size(); j++) {
	        for(int i = 0; i<3; i++) {
	        	List<PresaView> listaAtual = partida.getJogadores().get(j).getAnimal().getListaDePresas();

	        	celulas[listaAtual.get(i).getPosicao()].setPresa(listaAtual.get(i).getNomePresa());
	        	celulas[listaAtual.get(i).getPosicao()].setOverlayImage("/images/AnimaisSecundários/" + listaAtual.get(i).getNomePresa() + ".png");
	        }
        }
        
    	        
        for(int i = 0; i<celulas.length; i++) {
        	if(celulas[i].getPresa() == null) {
        		
        	}
        	else {
        		celulas[i].setBackgroundImage("/images/fundo/casaPresa.png");
        	}
        	
        }

    }

    
    public void configurarJanela() {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setVisible(true);
	}
    
    public void atualizarInterface() {
        validate();
        repaint();
        revalidate();
    }
    
    public void atualizarImg(Animal animal, Partida partida) {
    	for(AnimalView av : animalJogador) {

    		if(av.getAnimal().getId() == animal.getId()) {

    			av.atualizarImagem();
    			atualizarInterface();
    			return;
    		}
    	}
    }
    
    public CelulaView pegarCelula(Celula celula) {
    	for(int i=0; i<celulas.length; i++) {
    		if(celulas[i].getCelula().getX() == celula.getX()) {
    			return celulas[i];
    		}
    	}
    	return null;
    }
    
    public void setRolarDados(RolarDados dados) {
    	this.rolarDados = dados;
    }
    
    public void pegarCelulaView(Animal animal, Celula antigaCelula, Celula novaCelula) {

    	CelulaView antigaCel=null, novaCel=null;
    	AnimalView animalView=null;

    	for(int i=0;i>=0&& i<celulas.length; i++) {
    		if(celulas[i].getCelula().getCaminhoId() == antigaCelula.getCaminhoId()
    			&& celulas[i].getCelula().getX() == antigaCelula.getX()) {
    			antigaCel = celulas[i];
    		}
    		if(celulas[i].getCelula().getCaminhoId() == novaCelula.getCaminhoId()
        			&& celulas[i].getCelula().getX() == novaCelula.getX()) {
    			novaCel = celulas[i];
        	}
    	}
    	
    	for(int i=0; animalJogador.size()>i;i++) {
    		if(animalJogador.get(i).getAnimal().getId() == animal.getId()) {
    			animalView = animalJogador.get(i);
    		}
    	}
    	if(antigaCel==null || novaCel==null || animalView==null) {System.out.println("não deu certo"); return;}
    	
    	atualizarAnimalNaTela(animalView, antigaCel, novaCel);
    }

    public void atualizarAnimalNaTela(AnimalView animal, CelulaView antigaCel, CelulaView novaCel) {
    
		//remove a imagem da celula
		antigaCel.removeAnimal(animal.getAnimal().getId()); 
		
		//mesmo do de baixo
		novaCel.revalidate();
		
		//repainta o quadrado pra atualizar agora que foi removido o bicho
		antigaCel.repaint();		
		
		//isso reseta o cache do java pra imagem não continuar viva depois do repaint
		SwingUtilities.updateComponentTreeUI(layeredPane);
		novaCel.addAnimal(animal);;
		novaCel.add(animal.getLabel()); 
		
		novaCel.revalidate();
		novaCel.repaint();
		SwingUtilities.updateComponentTreeUI(layeredPane);

	    
	}
    
    public void addInfoJogo_subPanel(JPanel panel, Partida partida) {
        infoJogo.setBackground(null);

        TitledBorder border = BorderFactory.createTitledBorder("Informações");
        border.setTitleFont(bungeeFont.deriveFont(Font.PLAIN, 18f));
        infoJogo.setBorder(border);

        numeroRodadoLabel = new JLabel("Início de Jogo", SwingConstants.CENTER);
        jogadorAtualLabel = new JLabel("Jogador atual: " + partida.getOrdemJogador().get(0).getJogador(), SwingConstants.CENTER);

        numeroRodadoLabel.setFont(fredokaFont.deriveFont(Font.PLAIN, 18f));

        jogadorAtualLabel.setFont(fredokaFont.deriveFont(Font.PLAIN, 18f));
        infoJogo.add(numeroRodadoLabel);
        infoJogo.add(jogadorAtualLabel);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.7;
        gbc.fill = GridBagConstraints.BOTH;
        
        panel.add(infoJogo, gbc);
    }
    
    public void bloquearBotao() {
        btnDado.setEnabled(false);
    }

    public void desbloquearBotao() {
        btnDado.setEnabled(true);
    }
    
    public void addInfoAcoes_subPanel(JPanel panel) {
        TitledBorder border = BorderFactory.createTitledBorder("Ações");
        border.setTitleFont(bungeeFont.deriveFont(Font.PLAIN, 18f));
        acoesJogo.setBorder(border);

        btnDado = new JButton("Rolar o Dado");
        btnDado.setForeground(Color.WHITE);
        btnDado.setBackground(new Color(48, 176, 255)); // nice blue
        btnDado.setFocusPainted(false);

        
        JButton sairJogo = new JButton("Sair do Jogo");
        sairJogo.setForeground(Color.WHITE);
        sairJogo.setBackground(new Color(2
        		, 151, 244)); // nice blue
        sairJogo.setFocusPainted(false);
 
        
        btnDado.setFont(fredokaFont.deriveFont(Font.PLAIN, 18f));
        sairJogo.setFont(fredokaFont.deriveFont(Font.PLAIN, 18f));

        acoesJogo.add(btnDado);
        acoesJogo.add(sairJogo);
        
        acoesJogo.setBackground(null);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.gridy = 1;
        gbc.weighty = 0.9;

        gbc.fill = GridBagConstraints.BOTH;
        
        btnDado.addActionListener(e -> {
			if(rolarDados != null) {
				rolarDados.rolarDados();
			}
		});

        sairJogo.addActionListener(e -> {
        	System.exit(0);
        });

        panel.add(acoesJogo, gbc);
        
    }
    
    public void atualizarDados(int dado) {
        
        numeroRodadoLabel.setText("O dado rolou o número: " + dado);
    }


    
	public void atualizarJogadorAtual(String jogador) {
    	jogadorAtualLabel.setText("Jogador atual: "+jogador);
    }
    
    public void addInfoJogadores_subPanel(JPanel panel, Partida partida) {
        jogadoresPanel.setBackground(null);

        TitledBorder border = BorderFactory.createTitledBorder("Jogadores");
        border.setTitleFont(bungeeFont.deriveFont(Font.PLAIN, 18f));
        jogadoresPanel.setBorder(border);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.gridy = 2;
        gbc.weighty = 1.01;
        gbc.fill = GridBagConstraints.BOTH;
        
        for (int i = 0; i < partida.getJogadores().size(); i++) {
        	
        	Animal animal = partida.getJogadores().get(i).getAnimal();
            infoJogadores[i] = new JPanel(new GridLayout(4, 1));
            
            labelsJogadores[i][0] = new JLabel("Jogador " + (i + 1) +": " + partida.getJogadores().get(i).getJogador()); // name
            labelsJogadores[i][1] = new JLabel("Animal: " + animal.getNome() + " / "
            		+ "Evolução: " + animal.getNivel() + "/5");
            
            labelsJogadores[i][2] = new JLabel("Pontuação: 0/"+animal.getPontosEvoluir());
            labelsJogadores[i][3] = new JLabel("Casa: 0");


            for (int j = 0; j < partida.getJogadores().size(); j++) {
                infoJogadores[i].add(labelsJogadores[i][j]);
            }
            for (int j = 0; j < 1; j++) { 
            	labelsJogadores[i][j].setFont(fredokaFont.deriveFont(Font.PLAIN, 17f));
            	infoJogadores[i].add(labelsJogadores[i][j]);
            }
            
            for (int j = 1; j < 4; j++) {
                labelsJogadores[i][j].setFont(fredokaFont.deriveFont(Font.PLAIN, 14f));

                infoJogadores[i].add(labelsJogadores[i][j]);
            }
           
            infoJogadores[i].setBackground(coresCampos[i]);
            infoJogadores[i].setBorder(BorderFactory.createLineBorder(coresJogadores[i], 3));
            jogadoresPanel.add(infoJogadores[i]);
        }
        
        panel.add(jogadoresPanel, gbc);
    }
    
    public void atualizarInfoJogador(List<Jogador> jogadoresOrdem, List<Jogador> jogadores) {
        for (int i = 0; i < jogadoresOrdem.size(); i++) {
             
        	Jogador jogadorFixo = jogadores.get(i);
     
         	labelsJogadores[i][1].setText("Animal: " + jogadorFixo.getAnimal().getNome()  + " / Evolução: " + DAO.buscarAnimal(jogadorFixo.getAnimal().getNome()).getNivel() + "/5");
            labelsJogadores[i][2].setText("Pontuação: " + jogadorFixo.getAnimal().getTotalPontos()+"/"+jogadorFixo.getAnimal().getPontosEvoluir());
            labelsJogadores[i][3].setText("Casa: " + jogadorFixo.getAnimal().getX());
           
        }
        
        atualizarInterface();
    }
    
    public void preencherTabuleiro(JPanel tabuleiro, Partida partida) {
    	//nem eu entendo isso, só sei que calcula o valor certo pra dimensionar o tamanho da celula e ficar tudo no meio

        int gap = 2; //espaço entre as celulas
        int rows = 7; //qtd de celulas em uma fileira
        int cols = 9; //qtd de celulas em uma coluna
        
        int subWidth = tabuleiro.getWidth();
        int subHeight = tabuleiro.getHeight();

        int cellSizeX = (subWidth - (cols - 1) * gap) / cols;
        int cellSizeY = (subHeight - (rows - 1) * gap) / rows;
        int cellSize = Math.min(cellSizeX, cellSizeY);

        int totalWidth = cellSize * cols + (cols - 1) * gap;
        int totalHeight = cellSize * rows + (rows - 1) * gap;

        int offsetX = (subWidth - totalWidth) / 2;
        int offsetY = (subHeight - totalHeight) / 2;

  
        int posCasa = 0;
        
     // coluna esquerda
        for (int i = rows - 1; i > 0; i--) {
        	int x = offsetX;
            int y = offsetY + i * (cellSize + gap);
            
            CelulaView c = new CelulaView(x, y, cellSize, cellSize,
            		partida.getTabuleiro().getGridMain(posCasa));
        	celulas[posCasa] = c;

            tabuleiro.add(celulas[posCasa]);
            posCasa++;
        }
        
        // coluna de cima
        for (int j = 0; j < cols; j++) {
        	//pra fazer: trocar essas label por classes de celula
        	int x = offsetX + j * (cellSize + gap);
            int y = offsetY;
            
            CelulaView c = new CelulaView(x, y, cellSize, cellSize,
            		partida.getTabuleiro().getGridMain(posCasa)) ;
        	celulas[posCasa] = c;

            tabuleiro.add(celulas[posCasa]);
            
            posCasa++;
        }

        // coluna da direita
        for (int i = 1; i < rows - 1; i++) {
            int x = offsetX + (cols - 1) * (cellSize + gap);
            int y = offsetY + i * (cellSize + gap);
            
            CelulaView c = new CelulaView(x, y, cellSize, cellSize,
            		partida.getTabuleiro().getGridMain(posCasa)); 
        	celulas[posCasa] = c;

            tabuleiro.add( celulas[posCasa]);
            
            posCasa++;
        }

        // coluna de baixo
        for (int j = cols - 1; j > 0; j--) {
            int x = offsetX + j * (cellSize + gap);
            int y = offsetY + (rows - 1) * (cellSize + gap);
            
            ;
            CelulaView c = new CelulaView(x, y, cellSize, cellSize,
            		partida.getTabuleiro().getGridMain(posCasa)) 
    		;
        	celulas[posCasa] = c;

            tabuleiro.add(celulas[posCasa]);
            posCasa++;
        }



               
    }

	public CelulaView getCelula(int x) {
		return celulas[x];
	}



	public void mostrarMensagemTemporaria(String mensagem, int tempoMs, Color cor, int posicao) {
	    final JDialog dialog = new JDialog();
	    dialog.setUndecorated(true);

	    JLabel label = new JLabel(mensagem, SwingConstants.CENTER);
	    label.setFont(fredokaFont.deriveFont(Font.PLAIN, 18f));
	    label.setOpaque(true);
	    label.setBackground(Color.white);
	    label.setForeground(Color.BLACK);

	    Border line = BorderFactory.createLineBorder(cor, 5);
	    Border padding = BorderFactory.createEmptyBorder(30, 60, 30, 60);
	    label.setBorder(new CompoundBorder(line, padding));

	    dialog.add(label);
	    dialog.pack();
	    dialog.setAlwaysOnTop(true);

	    // --- Position control ---
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    int dialogWidth = dialog.getWidth();
	    int dialogHeight = dialog.getHeight();

	    // Base (normal) centered position
	    int x = (screenSize.width - dialogWidth) / 2;
	    int y = (screenSize.height - dialogHeight) / 2;


	    int offset = 150; 
	    y -= (posicao - 1) * offset; 

	    dialog.setLocation(x, y);
	    dialog.setVisible(true);

	    // --- Auto-close timer ---
	    new Timer(tempoMs, e -> dialog.dispose()).start();
	}

    

	public void trocarPresas(Animal animal) {
		List<PresaView> listaNova = new ArrayList<PresaView> ();
		
		for(int j = 0; j<3; j++) {
    		PresaView novaPresa = new PresaView();
    		String presaAtual = DAO.buscarPresa(animal.getNome()).get(j).getNomePresa();
    		
    		novaPresa.setNomePresa(presaAtual);
    		novaPresa.setPosicao(animal.getListaDePresas().get(j).getPosicao());
    		
    		listaNova.add(novaPresa);
    	}
		Collections.shuffle(listaNova);
		
    	animal.setListaDePresas(listaNova);
    	for(int i = 0; i<3; i++) {
	        List<PresaView> listaAtual = animal.getListaDePresas();
	        	
	        celulas[listaAtual.get(i).getPosicao()].setPresa(listaAtual.get(i).getNomePresa());
	        celulas[listaAtual.get(i).getPosicao()].setOverlayImage("/images/AnimaisSecundários/" + listaAtual.get(i).getNomePresa() + ".png");
	     }
        

	}
    
	public void terminarJogo() {
		System.exit(0);
		
	}
    
    
    
}
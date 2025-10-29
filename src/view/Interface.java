package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
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
    private JPanel acoesJogo = new JPanel(new GridLayout(2, 1, 1, 10));
    private JPanel jogadoresPanel = new JPanel(new GridLayout(2, 1, 1, 10));
    
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
    	    bungeeFont = Font.createFont(
    	        Font.TRUETYPE_FONT,
    	        getClass().getResourceAsStream("/fontes/Bungee-Regular.ttf")
    	    );
    	} catch (Exception e) {
    	    e.printStackTrace();
    	    bungeeFont = new Font("SansSerif", Font.PLAIN, 15); 
    	}
    	
    	try {
    	    fredokaFont = Font.createFont(
    	        Font.TRUETYPE_FONT,
    	        getClass().getResourceAsStream("/fontes/LilitaOne-Regular.ttf")
    	    );
    	} catch (Exception e) {
    	    e.printStackTrace();
    	    fredokaFont = new Font("SansSerif", Font.PLAIN, 15);
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
        
        subPanel.setPreferredSize(new Dimension((int) (panel.getWidth() * 0.95), (int) (panel.getHeight() * 0.97)));
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
        
        int[] celulasQuiz = new int[4];
        celulasQuiz[0] = 7;
        celulasQuiz[1] = 13;
        celulasQuiz[2] = 21;
        celulasQuiz[3] = 27;
        
        for(int i = 0; i<celulasQuiz.length; i++) {
        	celulas[celulasQuiz[i]].setBackgroundImage("/images/MiniJogos/tubarão.png");
        	celulas[celulasQuiz[i]].setQuiz("true");
        }
        


        celulas[0].setBackgroundImage("/images/fundo/casaInicial.png");
  

        
     
		for(int j = 0; j < partida.getJogadores().size(); j++) {
	        for(int i = 0; i<6; i++) {
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

     // Bind keys to trigger btnDado
       
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
        border.setTitleFont(bungeeFont.deriveFont(Font.PLAIN, 20f));
        infoJogo.setBorder(border);

        numeroRodadoLabel = new JLabel("Início de Jogo", SwingConstants.CENTER);
        jogadorAtualLabel = new JLabel("Jogador atual: " + partida.getOrdemJogador().get(0).getJogador(), SwingConstants.CENTER);

        numeroRodadoLabel.setFont(fredokaFont.deriveFont(Font.PLAIN, 20f));

        jogadorAtualLabel.setFont(fredokaFont.deriveFont(Font.PLAIN, 20f));
        infoJogo.add(numeroRodadoLabel);
        infoJogo.add(jogadorAtualLabel);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 5, 40, 5);
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.7;
        gbc.fill = GridBagConstraints.BOTH;
        
        panel.add(infoJogo, gbc);
    }
    
    public void addInfoAcoes_subPanel(JPanel panel) {
        TitledBorder border = BorderFactory.createTitledBorder("Ações");
        border.setTitleFont(bungeeFont.deriveFont(Font.PLAIN, 20f));
        acoesJogo.setBorder(border);

        ImageIcon dadoIcon = new ImageIcon(getClass().getResource("/images/fundo/dice.png"));
        Image scaledDado = dadoIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        dadoIcon = new ImageIcon(scaledDado);
        
        btnDado = new JButton("Rolar Dado", dadoIcon);
        btnDado.setForeground(Color.WHITE);
        btnDado.setBackground(new Color(48, 176, 255)); 
        btnDado.setFocusPainted(false);
        
        btnDado.setHorizontalTextPosition(SwingConstants.LEFT);
        btnDado.setVerticalTextPosition(SwingConstants.CENTER);
        btnDado.setIconTextGap(10);
        
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('A'), "rollDice");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('L'), "rollDice");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('a'), "rollDice");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('l'), "rollDice");
        
        panel.getActionMap().put("rollDice", new AbstractAction() {
	       @Override
	       public void actionPerformed(ActionEvent e) {
	           btnDado.doClick(); // simulates the button click
	       }
	   	});

        
        JButton sairJogo = new JButton("Sair do Jogo");
        sairJogo.setForeground(Color.WHITE);
        sairJogo.setBackground(new Color(2, 151, 244)); 
        sairJogo.setFocusPainted(false);
 
        
        btnDado.setFont(fredokaFont.deriveFont(Font.PLAIN, 20f));
        sairJogo.setFont(fredokaFont.deriveFont(Font.PLAIN, 20f));

        acoesJogo.add(btnDado);
        acoesJogo.add(sairJogo);
        
        acoesJogo.setBackground(null);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 5, 20, 5);
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
    
    
    public void addInfoJogadores_subPanel(JPanel panel, Partida partida) {
        jogadoresPanel.setBackground(null);

        TitledBorder border = BorderFactory.createTitledBorder("Jogadores");
        border.setTitleFont(bungeeFont.deriveFont(Font.PLAIN, 20f));
        jogadoresPanel.setBorder(border);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(40, 5, 30, 5);
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
            	labelsJogadores[i][j].setFont(fredokaFont.deriveFont(Font.PLAIN, 22f));
            	infoJogadores[i].add(labelsJogadores[i][j]);
            }
            
            for (int j = 1; j < 4; j++) {
                labelsJogadores[i][j].setFont(fredokaFont.deriveFont(Font.PLAIN, 18f));

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
    
    public void atualizarDados(int dado) {
        
        numeroRodadoLabel.setText("O dado rolou o número: " + dado);
    }


    
	public void atualizarJogadorAtual(String jogador) {
    	jogadorAtualLabel.setText("Jogador atual: "+jogador);
    }
    
    public void bloquearBotao() {
        btnDado.setEnabled(false);
    }

    public void desbloquearBotao() {
        btnDado.setEnabled(true);
    }
    
    public void preencherTabuleiro(JPanel tabuleiro, Partida partida) {
    	//calcula o valor certo pra dimensionar o tamanho da celula e ficar tudo no meio

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
	    label.setFont(fredokaFont.deriveFont(Font.PLAIN, 19f));
	    label.setOpaque(true);
	    label.setBackground(Color.white);
	    label.setForeground(Color.BLACK);

	    Border line = BorderFactory.createLineBorder(cor, 5);
	    Border padding = BorderFactory.createEmptyBorder(30, 60, 30, 60);
	    label.setBorder(new CompoundBorder(line, padding));

	    dialog.add(label);
	    dialog.pack();
	    dialog.setAlwaysOnTop(true);

	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    int dialogWidth = dialog.getWidth();
	    int dialogHeight = dialog.getHeight();

	    int x = (screenSize.width - dialogWidth) / 2;
	    int y = (screenSize.height - dialogHeight) / 2;


	    int offset = 150; 
	    y -= (posicao - 1) * offset; 

	    dialog.setLocation(x, y);
	    dialog.setVisible(true);


	    new Timer(tempoMs, e -> dialog.dispose()).start();
	}

	public void mostrarTelaDeVitoria(Animal animal) {
	    final JDialog dialog = new JDialog();
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	    dialog.setAlwaysOnTop(true);
	    dialog.getContentPane().setBackground(Color.white);
	    dialog.setUndecorated(true);

	    JPanel panel = new JPanel(new GridBagLayout());
	    Border lineP = BorderFactory.createLineBorder(new Color(255, 216, 44), 8);
	    Border paddingP = BorderFactory.createEmptyBorder(30, 60, 30, 60);
	    panel.setBorder(new CompoundBorder(lineP, paddingP));
	    
	    panel.setBackground(Color.white);
	    
	    JPanel panelImages = new JPanel(new GridLayout(1, 2, 40, 10));

	    ImageIcon icon = new ImageIcon(getClass().getResource(animal.getImg())); 
	    Image scaledImg = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
	    JLabel imageLabel = new JLabel(new ImageIcon(scaledImg));
	    imageLabel.setBackground(null);

	    ImageIcon iconTrofeu = new ImageIcon(getClass().getResource("/images/fundo/trofeu.png")); 
	    Image scaledImgTrofeu = iconTrofeu.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
	    JLabel imageTrofeuLabel = new JLabel(new ImageIcon(scaledImgTrofeu));
	    imageTrofeuLabel.setBackground(null);
	    
	    panelImages.setBackground(null);
	    panelImages.add(imageLabel);
	    panelImages.add(imageTrofeuLabel);
	    
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.insets = new Insets(15, 15, 15, 15);
	    gbc.gridy = 0;
	    
	    panel.add(panelImages, gbc);

	    JLabel label = new JLabel(
	    	    "<html><center>" +
	    	    "<span style='font-size:24px; font-weight:bold;'>Parabéns!</span><br>" +
	    	    "O jogador <b>" + animal.getDono() + "</b> evoluiu para <b>" + animal.getNome() + "</b> " +
	    	    "e ganhou o jogo!" +
	    	    "</center></html>",
	    	    SwingConstants.CENTER
	    	);
	    
	    label.setFont(fredokaFont.deriveFont(Font.PLAIN, 19f));
	    label.setOpaque(true);
	    label.setBackground(Color.white);
	    label.setForeground(Color.BLACK);

	    Border line = BorderFactory.createLineBorder(Color.white, 5);
	    Border padding = BorderFactory.createEmptyBorder(20, 10, 20, 10);
	    label.setBorder(new CompoundBorder(line, padding));

	    GridBagConstraints gbcText = new GridBagConstraints();
	    gbcText.gridx = 0;
	    gbcText.insets = new Insets(15, 15, 15, 15);
	    gbcText.gridy = 1;
	    panel.add(label, gbcText);

	    
	    JButton button = new JButton("Sair");
	    Border paddingBtn = BorderFactory.createEmptyBorder(10, 60, 10, 60);
	    button.setBorder(paddingBtn);
	    button.addActionListener(e -> System.exit(0));
	    button.setFont(fredokaFont.deriveFont(Font.PLAIN, 20f));
	    button.setForeground(Color.white);
	    button.setBackground(new Color(255, 216, 44));
	    button.setFocusPainted(false);
	    
	    GridBagConstraints gbcBtn = new GridBagConstraints();
	    gbcBtn.gridx = 0;
	    gbcBtn.insets = new Insets(15, 15, 0, 15);
	    gbcBtn.gridy = 2;
	    panel.add(button, gbcBtn);
	    

	    int dialogWidth = (int) (screenSize.width * 0.3);
	    int dialogHeight = (int) (screenSize.height * 0.5);
	    dialog.setSize(dialogWidth, dialogHeight);

	    dialog.setContentPane(panel);
	    dialog.pack();
	    dialog.setLocationRelativeTo(null);
	    dialog.setVisible(true);
	}


	public void trocarPresas(Animal animal) {
		List<PresaView> listaNova = new ArrayList<PresaView> ();
		
		for(int j = 0; j<6; j++) {
    		PresaView novaPresa = new PresaView();
    		String presaAtual = DAO.buscarPresa(animal.getNome()).get(j).getNomePresa();
    		
    		novaPresa.setNomePresa(presaAtual);
    		novaPresa.setPosicao(animal.getListaDePresas().get(j).getPosicao());
    		
    		listaNova.add(novaPresa);
    	}
		Collections.shuffle(listaNova);
		
    	animal.setListaDePresas(listaNova);
    	for(int i = 0; i<6; i++) {
	        List<PresaView> listaAtual = animal.getListaDePresas();
	        	
	        celulas[listaAtual.get(i).getPosicao()].setPresa(listaAtual.get(i).getNomePresa());
	        celulas[listaAtual.get(i).getPosicao()].setOverlayImage("/images/AnimaisSecundários/" + listaAtual.get(i).getNomePresa() + ".png");
	     }
        

	}
	
	public void mostrarDadoModal(Animal animal, int valor) {
		//configuracoes padrao de jdialog (modal true pra interromper o jogo)
	    JDialog dialog = new JDialog((Frame) null, "Dado", true);
	    dialog.setUndecorated(true);
	    dialog.setBackground(new Color(0,0,0,0));

	    ((JComponent) dialog.getContentPane()).setOpaque(false);
	    
	    // layout gridbag pra deixar tudo no meio
	    dialog.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.anchor = GridBagConstraints.CENTER;
	    
	    ImageIcon rollingGif = new ImageIcon(getClass().getResource("/images/fundo/diceroll.gif"));
	    JLabel label = new JLabel(rollingGif);
	    label.setOpaque(false);
	    label.setHorizontalAlignment(SwingConstants.CENTER);
	    label.setVerticalAlignment(SwingConstants.CENTER);

	    dialog.add(label, gbc);

	    dialog.pack();
	    dialog.setLocationRelativeTo(null);

	    //delay de 1000 segundos pro dado rolar e depois mostrar o valor do dado rolado
	    Timer switchTimer = new Timer(1000, ev -> {
	        ((Timer) ev.getSource()).stop();
	    
	        ImageIcon finalDice = new ImageIcon(getClass().getResource("/images/fundo/dice" + valor + ".png"));
	        int width = 140;
	        int height = 140;
	        Image scaled = finalDice.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
	        finalDice = new ImageIcon(scaled);
	        
	        label.setIcon(finalDice);
	        dialog.pack(); 
	        dialog.setLocationRelativeTo(null);
	    });
	    switchTimer.start();

	    //roda junto do outro timer, fecha o dialog depois de 1.2s dps do switchtimer
	    Timer closeTimer = new Timer(3500, e -> {
	        ((Timer) e.getSource()).stop();
	        dialog.dispose();
	    });
	    closeTimer.start();

	    dialog.setVisible(true); 
	}
    
	public void terminarJogo() {
		System.exit(0);
		
	}
    
    
    
}
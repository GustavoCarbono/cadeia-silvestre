package utils;

import javax.swing.*;

import partida.Animal;
import partida.Jogador;
import partida.Partida;
import view.Interface;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TelaQuiz extends JDialog {
	
    private JButton[] alternativasBtns = new JButton[4];
    public static class Pergunta {
		public final String enunciado;
		public final String[] alternativas;
		public final int indiceCorreto; // 0..3

		public Pergunta(String enunciado, String[] alternativas, int indiceCorreto) {
			this.enunciado = enunciado;
			this.alternativas = alternativas;
			this.indiceCorreto = indiceCorreto;
		}
	}

	private final List<Pergunta> perguntas;
	private final Random random = new Random();

	public TelaQuiz(JFrame parent) {
		super(parent, "Quiz", true);
		perguntas = new ArrayList<>();
		perguntas.add(new Pergunta(
			"O que é uma cadeia alimentar?",
			new String[]{
				"Um grupo de animais que vivem juntos.",
				"A sequência de seres vivos que se alimentam uns dos outros.",
				"A relação entre plantas e o solo.",
				"Um ciclo de reprodução entre espécies."
			}, 1));

		perguntas.add(new Pergunta(
			"Quem são os produtores em uma cadeia alimentar?",
			new String[]{
				"Animais herbívoros",
				"Animais carnívoros",
				"Plantas e algas",
				"Bactérias decompositoras"
			}, 2));

		perguntas.add(new Pergunta(
			"Os consumidores primários se alimentam de:",
			new String[]{
				"Outros carnívoros",
				"Plantas",
				"Restos orgânicos",
				"Decompositores"
			}, 1));

		perguntas.add(new Pergunta(
			"Um consumidor secundário se alimenta de:",
			new String[]{
				"Plantas",
				"Herbívoros",
				"Decompositores",
				"Minerais"
			}, 1));

		perguntas.add(new Pergunta(
			"Qual o papel dos decompositores na cadeia alimentar?",
			new String[]{
				"Produzir oxigênio",
				"Quebrar matéria orgânica e devolver nutrientes ao solo",
				"Caçar outros animais",
				"Filtrar a água"
			}, 1));

		perguntas.add(new Pergunta(
			"Qual sequência representa corretamente uma cadeia alimentar?",
			new String[]{
				"Sol → Planta → Gafanhoto → Sapo → Cobra → Águia",
				"Planta → Sol → Cobra → Sapo → Gafanhoto",
				"Águia → Cobra → Sapo → Gafanhoto → Planta",
				"Sol → Águia → Planta → Cobra → Gafanhoto"
			}, 0));

		perguntas.add(new Pergunta(
			"Qual é a principal fonte de energia para o início de todas as cadeias alimentares?",
			new String[]{
				"Água",
				"Sol",
				"Solo",
				"Oxigênio"
			}, 1));

		perguntas.add(new Pergunta(
			"Um gavião que se alimenta de um rato é um exemplo de:",
			new String[]{
				"Produtor",
				"Consumidor Secundário",
				"Consumidor Primário",
				"Decompositor"
			}, 1));

		perguntas.add(new Pergunta(
			"O que pode acontecer se um elo da cadeia alimentar for eliminado?",
			new String[]{
				"Nada muda.",
				"Toda a cadeia pode ser afetada.",
				"Apenas os produtores desaparecem.",
				"A cadeia se fortalece."
			}, 1));

		perguntas.add(new Pergunta(
			"O que é um nível trófico?",
			new String[]{
				"O local onde o animal vive.",
				"O papel de cada ser vivo na cadeia alimentar.",
				"O tipo de solo onde as plantas crescem.",
				"A quantidade de energia perdida."
			}, 1));
	}

	
    public void executarQuiz(Jogador jogador, Partida partida, Interface gui) {
		
        setTitle("Tela do Quiz");

        // Get screen size and set it
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int)(screenSize.width * 0.4), (int)(screenSize.height * 0.85));
        repaint();
        revalidate();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        setLayout(new GridBagLayout());
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(48, 176, 255), 9));
        getContentPane().setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 20, 10);

        // === Title label ===
        JLabel tituloLabel = new JLabel("Pergunta");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        add(tituloLabel, gbc);
        
        GridBagConstraints gbcPergunta = new GridBagConstraints();
        gbcPergunta.gridx = 0;
        gbcPergunta.gridy = 1; // row 1, below the title
        gbcPergunta.gridwidth = 2; // span 2 columns if your layout has 2 columns
        gbcPergunta.insets = new Insets(50, 0, 30, 0); // top and bottom spacing

        gbcPergunta.fill = GridBagConstraints.HORIZONTAL; 
        gbcPergunta.weightx = 1.0;

        Pergunta p = perguntas.get(random.nextInt(perguntas.size()));
		
        JLabel perguntaLabel = new JLabel();
        int largura = (int) (getWidth() * 0.6); // subtract a bit for padding/margins
        String texto = "<html><body style='width:" + largura + "px;'>" + p.enunciado + "</body></html>";
        perguntaLabel.setText(texto);
        perguntaLabel.setFont(new Font("Arial", Font.BOLD, 24));
        perguntaLabel.setHorizontalAlignment(SwingConstants.LEFT);
        perguntaLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0)); // horizontal padding
        add(perguntaLabel, gbcPergunta);
        
        // === Panel for answer buttons ===
        JPanel alternativasPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        alternativasPanel.setBackground(Color.white);
        
        alternativasPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 50, 20)); // horizontal padding
        Color[] coresBorda = {
                new Color(255, 100, 100),
                new Color(100, 255, 100),
                new Color(100, 100, 255),
                new Color(255, 255, 100)
        };
        
        Color[] coresBtn = {
            new Color(255, 170, 170),
            new Color(170, 255, 170),
            new Color(170, 170, 255),
            new Color(255, 255, 170)
        };

        for (int i = 0; i < 4; i++) {
            int indice = i;

            alternativasBtns[indice] = new JButton(p.alternativas[i]);
            alternativasBtns[indice].setFont(new Font("Arial", Font.PLAIN, 19));
            alternativasBtns[indice].setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(coresBorda[i], 5),
                    BorderFactory.createEmptyBorder(0, 0, 0, 0)         
                ));
            alternativasBtns[indice].setFocusPainted(false);
            alternativasBtns[indice].setCursor(new Cursor(Cursor.HAND_CURSOR));
            alternativasBtns[indice].setBackground(coresBtn[i]);
            alternativasBtns[indice].addActionListener(e -> {
                verificarResposta(jogador, partida, gui, p, indice);
                TelaQuiz.this.dispose();
            });
            

            alternativasPanel.add(alternativasBtns[indice]);
        }

        // Add panel to the frame
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 2;
        gbcPanel.gridwidth = 2;
        gbcPanel.weightx = 1;
        gbcPanel.weighty = 1;  // fill vertical space
        gbcPanel.fill = GridBagConstraints.BOTH;  // fill both directions
        add(alternativasPanel, gbcPanel);
        
        revalidate();
        repaint();
        setVisible(true);
    }

    public void verificarResposta(Jogador jogador, Partida partida, Interface gui, Pergunta p, int resposta) {
    	Animal animal = jogador.getAnimal();
    	
		if (resposta == p.indiceCorreto) {
			animal.setTotalPontos(animal.getTotalPontos() + 10);
			gui.mostrarMensagemTemporaria(
				"Acertou! +10 pontos para " + jogador.getJogador(),
				3000,
				new Color(102, 255, 102),
				2
			);
		} else {
			int novo = Math.max(0, animal.getTotalPontos() - 10);
			animal.setTotalPontos(novo);
			gui.mostrarMensagemTemporaria(
				"Errou! -10 pontos para " + jogador.getJogador(),
				3000,
				new Color(255, 0, 0),
				2
			);
		}

	
		gui.atualizarInfoJogador(partida.getOrdemJogador(), partida.getJogadores());
		this.dispose();
    }
}

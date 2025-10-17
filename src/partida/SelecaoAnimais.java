package partida;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.*;
import java.net.URL;

import utils.Cores;
import view.PresaView;

public class SelecaoAnimais extends JFrame {

	private static final long serialVersionUID = 1L;
	private int jogadorAtual = 0;
	private int totalJogadores;
	private List<Animal> animaisDisponiveis;
	private List<Animal> animaisSelecionados;
	private List<String> nomesJogadores;
	private List<PresaView> presasRato, presasCamaleao, presasBeijaflor, presasDormouse;
	private List<Cores> cores;
	
	private JLabel tituloLabel;
	private JLabel jogadorLabel;
	private JPanel animaisPanel;
	private JButton[] botoesAnimais;
	
	private Consumer<List<Animal>> onSelecionar;

	public SelecaoAnimais(int totalJogadores, List<String> nomesJogadores, 
			List<PresaView> presasRato, List<PresaView> presasCamaleao, 
			List<PresaView> presasBeijaflor, List<PresaView> presasDormouse,
			List<Cores> cores, Consumer<List<Animal>> onSelecionar) {
		
		this.totalJogadores = totalJogadores;
		this.nomesJogadores = nomesJogadores;
		this.presasRato = presasRato;
		this.presasCamaleao = presasCamaleao;
		this.presasBeijaflor = presasBeijaflor;
		this.presasDormouse = presasDormouse;
		this.cores = cores;
		this.onSelecionar = onSelecionar;
		
		this.animaisSelecionados = new ArrayList<>();
		
		setTitle("Seleção de Animais");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new BorderLayout());
		
		criarAnimaisDisponiveis();
		criarInterface();
		atualizarInterface();
	}

	private void criarAnimaisDisponiveis() {
		animaisDisponiveis = new ArrayList<>();
		animaisDisponiveis.add(new Animal(1, "Rato", "Doninha", 20, 1, "", 
			"/images/animaisPrincipais/gen0/rato.png", cores.get(0), presasRato));
		animaisDisponiveis.add(new Animal(2, "Camaleao", "Teiu", 20, 1, "", 
			"/images/animaisPrincipais/gen0/camaleao.png", cores.get(1), presasCamaleao));
		animaisDisponiveis.add(new Animal(3, "Beija-flor", "Corvo", 20, 1, "", 
			"/images/animaisPrincipais/gen0/beija-flor.png", cores.get(2), presasBeijaflor));
		animaisDisponiveis.add(new Animal(4, "Dormouse", "Feneco", 20, 1, "", 
			"/images/animaisPrincipais/gen0/dormouse.png", cores.get(3), presasDormouse));
	}

	private void criarInterface() {
		// Título
		tituloLabel = new JLabel("Seleção de Animais", SwingConstants.CENTER);
		tituloLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
		tituloLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		add(tituloLabel, BorderLayout.NORTH);
		
		// Painel central
		JPanel centro = new JPanel(new BorderLayout());
		
		// Label do jogador atual
		jogadorLabel = new JLabel("", SwingConstants.CENTER);
		jogadorLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
		jogadorLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		centro.add(jogadorLabel, BorderLayout.NORTH);
		
		// Painel dos animais
        animaisPanel = new JPanel(new GridLayout(2, 2, 20, 20));
		animaisPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
		
		botoesAnimais = new JButton[4];
        for (int i = 0; i < 4; i++) {
			Animal animal = animaisDisponiveis.get(i);
            JButton btn = new JButton();
            // Ícone e texto no próprio botão (garante clique em toda área)
            URL res = getClass().getResource(animal.getImg());
            if (res != null) {
                ImageIcon icon = new ImageIcon(res);
                Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                btn.setIcon(new ImageIcon(img));
            }
            btn.setText(animal.getNome());
            btn.setHorizontalTextPosition(SwingConstants.CENTER);
            btn.setVerticalTextPosition(SwingConstants.BOTTOM);

			btn.setPreferredSize(new Dimension(200, 180));
			btn.setFocusPainted(false);
			btn.setBackground(Color.WHITE);
			btn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
			
			final int index = i;
			btn.addActionListener(e -> selecionarAnimal(index));
			
			botoesAnimais[i] = btn;
			animaisPanel.add(btn);
		}
		
		centro.add(animaisPanel, BorderLayout.CENTER);
		add(centro, BorderLayout.CENTER);
	}

	private void selecionarAnimal(int index) {
		Animal animalOriginal = animaisDisponiveis.get(index);
		// Cria uma nova instância do animal com o dono correto
		Animal animalSelecionado = new Animal(animalOriginal.getId(), animalOriginal.getNome(), 
			animalOriginal.getEvolucao(), animalOriginal.getPontosEvoluir(), animalOriginal.getNivel(), 
			nomesJogadores.get(jogadorAtual), animalOriginal.getImg(), animalOriginal.getCor(), 
			animalOriginal.getListaDePresas());
		animaisSelecionados.add(animalSelecionado);
		
		// Remove o animal da lista de disponíveis
		animaisDisponiveis.remove(index);
		
		jogadorAtual++;
		
		if (jogadorAtual >= totalJogadores) {
			// Todos os jogadores selecionaram
			setVisible(false);
			dispose();
			if (onSelecionar != null) {
				onSelecionar.accept(animaisSelecionados);
			}
		} else {
			atualizarInterface();
		}
	}

	private void atualizarInterface() {
		if (jogadorAtual < totalJogadores) {
			jogadorLabel.setText("Jogador " + (jogadorAtual + 1) + ": " + 
				nomesJogadores.get(jogadorAtual) + " - Escolha seu animal");
		}
		
		// Atualiza os botões disponíveis
		animaisPanel.removeAll();
        for (int i = 0; i < animaisDisponiveis.size(); i++) {
            Animal animal = animaisDisponiveis.get(i);
            JButton btn = new JButton();
            URL res = getClass().getResource(animal.getImg());
            if (res != null) {
                ImageIcon icon = new ImageIcon(res);
                Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                btn.setIcon(new ImageIcon(img));
            }
            btn.setText(animal.getNome());
            btn.setHorizontalTextPosition(SwingConstants.CENTER);
            btn.setVerticalTextPosition(SwingConstants.BOTTOM);

			btn.setPreferredSize(new Dimension(200, 180));
			btn.setFocusPainted(false);
			btn.setBackground(Color.WHITE);
			btn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
			
			final int index = i;
			btn.addActionListener(e -> selecionarAnimal(index));
			
			animaisPanel.add(btn);
		}
		
		animaisPanel.revalidate();
		animaisPanel.repaint();
	}
}

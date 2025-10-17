package utils;

import javax.swing.*;

import minijogo.JogadorSelecionado;

import java.awt.*;
import java.util.ArrayList;

public class TelaSelecaoJogadores extends JDialog {
	private Font bungeeFont;
    private Font lilitaFont;
    private ArrayList<JogadorSelecionado> jogadoresSelecionados;

    public TelaSelecaoJogadores(Frame parent) {
    	
        super(parent, "Configuração dos Jogadores", true); // modal dialog
        
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
    		lilitaFont = Font.createFont(
    	        Font.TRUETYPE_FONT,
    	        getClass().getResourceAsStream("/fontes/LilitaOne-Regular.ttf")
    	    );
    	} catch (Exception e) {
    	    e.printStackTrace();
    	    lilitaFont = new Font("SansSerif", Font.PLAIN, 15); // fallback
    	}
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.5);
        int height = (int) (screenSize.height * 0.95);
        setSize(width, height);
        setLocationRelativeTo(parent);

        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(new Color(48, 176, 255));

        // ===== Grid panel (2x2) =====
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 30, 30));
        gridPanel.setPreferredSize(new Dimension((int)(width * 0.9), (int)(height * 0.84)));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20) );
        gridPanel.setBackground(new Color(255, 255, 255));
        Color[] coresJogadores = {
        		 new Color(255, 170, 170),
                 new Color(170, 255, 170),
                 new Color(170, 170, 255),
                 new Color(255, 255, 170)
        };

        Color[] coresJogadoresBorda = {
                new Color(255, 100, 100),
                new Color(100, 255, 100),
                new Color(100, 100, 255),
                new Color(255, 255, 100)
        };
        
        
        String[] animais = {"Rato", "Dormouse", "Beija-flor", "Camaleão"};

        ArrayList<JComboBox<String>> dropdowns = new ArrayList<>();
        ArrayList<JTextField> textFields = new ArrayList<>();

        // ===== Create 4 player panels =====
     // Create 4 player panels
     // Create 4 player panels
        for (int i = 0; i < 4; i++) {
            // Keep playerPanel as GridLayout
            JPanel playerPanel = new JPanel(new GridLayout(2, 1, 0, 50)); // 2 rows, vertical gap 15px
            playerPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(coresJogadoresBorda[i], 5), // border with color
                    BorderFactory.createEmptyBorder(30, 20, 30, 20)           // inner margin
                ));
            playerPanel.setBackground(coresJogadores[i]);
            playerPanel.setPreferredSize(new Dimension(0, 400));

            // ===== First group: label + input =====
            JPanel namePanel = new JPanel(new GridLayout(2, 1, 0, 5)); // small gap between label and input
            namePanel.setOpaque(false);
            JLabel label1 = new JLabel("Nome do Jogador " + (i+1) + ":");
            label1.setHorizontalAlignment(SwingConstants.LEFT);
            label1.setFont(bungeeFont.deriveFont(Font.PLAIN, 18f));
            JTextField input = new JTextField();
            input.setBorder(null);
            input.setFont(lilitaFont.deriveFont(Font.PLAIN, 18f));
            input.setPreferredSize(new Dimension(0, 30)); // smaller height
            textFields.add(input);
            namePanel.add(label1);
            namePanel.add(input);

            // ===== Second group: label + dropdown =====
            JPanel animalPanel = new JPanel(new GridLayout(2, 1, 0, 5)); // small gap between label and dropdown
            animalPanel.setOpaque(false);
            JLabel label2 = new JLabel("Escolha um Animal:");
            label2.setFont(bungeeFont.deriveFont(Font.PLAIN, 18f));
            label2.setHorizontalAlignment(SwingConstants.LEFT);
            
            JComboBox<String> dropdown = new JComboBox<>(animais);
            dropdown.setBorder(null);
            dropdown.setFont(lilitaFont.deriveFont(Font.PLAIN, 16f));
            dropdown.setBackground(Color.WHITE);
            dropdown.setPreferredSize(new Dimension(0, 30)); // smaller height
            dropdown.setSelectedIndex(-1);
            dropdowns.add(dropdown);
            animalPanel.add(label2);
            animalPanel.add(dropdown);

            // Add groups to playerPanel
            playerPanel.add(namePanel);
            playerPanel.add(animalPanel);

            gridPanel.add(playerPanel);
        }


        // Center the grid panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        background.add(gridPanel, gbc);

        // Confirm button
        JButton confirmBtn = new JButton("Confirmar");

	     // Make it bigger
	     confirmBtn.setPreferredSize(new Dimension(200, 50));
	
	     // Blue background and white text
	     confirmBtn.setBackground(new Color(255, 255, 255)); // blue
	     confirmBtn.setForeground(new Color(0, 0, 0));
	     confirmBtn.setBorder(null);
	     // Font
	     confirmBtn.setFont(lilitaFont.deriveFont(Font.BOLD, 20f));
	
	     // Remove the focus painted border for a cleaner look
	     confirmBtn.setFocusPainted(false);
	
	     gbc.gridy = 1;
	     gbc.insets = new Insets(30, 0, 0, 0);
	     background.add(confirmBtn, gbc);

        // Unique selection logic
        for (JComboBox<String> dropdown : dropdowns) {
            dropdown.addActionListener(e -> updateDropdowns(dropdowns));
        }

        confirmBtn.addActionListener(e -> {
        	
            // Collect names and animals
            jogadoresSelecionados = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                JogadorSelecionado j = new JogadorSelecionado();
                j.setNomeJogador(textFields.get(i).getText());
                j.setNomeAnimal((String) dropdowns.get(i).getSelectedItem());
                jogadoresSelecionados.add(j);
            }
            dispose(); // close dialog
        });

        add(background);
    }

    private void updateDropdowns(ArrayList<JComboBox<String>> dropdowns) {
        ArrayList<String> selected = new ArrayList<>();
        
        // Collect current selections
        for (JComboBox<String> combo : dropdowns) {
            Object sel = combo.getSelectedItem();
            if (sel != null) selected.add(sel.toString());
        }

        // Update each dropdown
        for (JComboBox<String> combo : dropdowns) {
            Object current = combo.getSelectedItem();
            combo.removeAllItems();
            for (String animal : new String[]{"Rato", "Dormouse", "Beija-flor", "Camaleão"}) {
                // Only add if not selected by another OR it's the current selection
                if (!selected.contains(animal) || animal.equals(current)) {
                    combo.addItem(animal);
                }
            }
            combo.setSelectedItem(current); // keep current selection
        }
    }

    public ArrayList<JogadorSelecionado> getJogadoresSelecionados() {
        return jogadoresSelecionados;
    }
}

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
    	
        super(parent, "Configuração dos Jogadores", true); 
        
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

    		lilitaFont = Font.createFont(
    	        Font.TRUETYPE_FONT,
    	        getClass().getResourceAsStream("/fontes/LilitaOne-Regular.ttf")
    	    );
    	} catch (Exception e) {
    	    e.printStackTrace();
    	    lilitaFont = new Font("SansSerif", Font.PLAIN, 15); 
    	}
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.41);
        int height = (int) (screenSize.height * 0.95);
        setSize(width, height);
        setLocationRelativeTo(parent);

        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(new Color(48, 176, 255));

        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 30, 30));
        gridPanel.setPreferredSize(new Dimension((int)(width * 0.93), (int)(height * 0.84)));
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

        for (int i = 0; i < 2; i++) {
 
            JPanel playerPanel = new JPanel(new GridLayout(2, 1, 20, 50)); 
            playerPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(coresJogadoresBorda[i], 5),
                    BorderFactory.createEmptyBorder(30, 20, 30, 20)         
                ));
            playerPanel.setBackground(coresJogadores[i]);
            playerPanel.setPreferredSize(new Dimension(200, 400));

            JPanel namePanel = new JPanel(new GridLayout(2, 1, 0, 5)); 
            namePanel.setOpaque(false);
            JLabel label1 = new JLabel("Nome do Jogador " + (i+1) + ":");
            label1.setHorizontalAlignment(SwingConstants.LEFT);
            label1.setFont(bungeeFont.deriveFont(Font.PLAIN, 18f));
            JTextField input = new JTextField();
            input.setBorder(null);
            input.setFont(lilitaFont.deriveFont(Font.PLAIN, 18f));
            input.setPreferredSize(new Dimension(0, 30));
            textFields.add(input);
            namePanel.add(label1);
            namePanel.add(input);

            JPanel animalPanel = new JPanel(new GridLayout(2, 1, 0, 5));
            animalPanel.setOpaque(false);
            JLabel label2 = new JLabel("Escolha um Animal:");
            label2.setFont(bungeeFont.deriveFont(Font.PLAIN, 18f));
            label2.setHorizontalAlignment(SwingConstants.LEFT);
            
            JComboBox<String> dropdown = new JComboBox<>(animais);
            dropdown.setBorder(null);
            dropdown.setFont(lilitaFont.deriveFont(Font.PLAIN, 18f));
            dropdown.setBackground(Color.WHITE);
            dropdown.setPreferredSize(new Dimension(0, 30));
            dropdown.setSelectedIndex(-1);
            dropdowns.add(dropdown);
            animalPanel.add(label2);
            animalPanel.add(dropdown);

            playerPanel.add(namePanel);
            playerPanel.add(animalPanel);

            gridPanel.add(playerPanel);
        }


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        background.add(gridPanel, gbc);


        JButton confirmBtn = new JButton("Confirmar");


	     confirmBtn.setPreferredSize(new Dimension(200, 50));
	

	     confirmBtn.setBackground(new Color(255, 255, 255)); 
	     confirmBtn.setForeground(new Color(0, 0, 0));
	     confirmBtn.setBorder(null);

	     confirmBtn.setFont(lilitaFont.deriveFont(Font.BOLD, 20f));

	     confirmBtn.setFocusPainted(false);
	
	     gbc.gridy = 1;
	     gbc.insets = new Insets(30, 0, 0, 0);
	     background.add(confirmBtn, gbc);

        for (JComboBox<String> dropdown : dropdowns) {
            dropdown.addActionListener(e -> updateDropdowns(dropdowns));
        }

        confirmBtn.addActionListener(e -> {
        	
            jogadoresSelecionados = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                JogadorSelecionado j = new JogadorSelecionado();
                j.setNomeJogador(textFields.get(i).getText());
                j.setNomeAnimal((String) dropdowns.get(i).getSelectedItem());
                jogadoresSelecionados.add(j);
            }
            dispose(); 
        });

        add(background);
    }

    private void updateDropdowns(ArrayList<JComboBox<String>> dropdowns) {
        ArrayList<String> selected = new ArrayList<>();
        
        for (JComboBox<String> combo : dropdowns) {
            Object sel = combo.getSelectedItem();
            if (sel != null) selected.add(sel.toString());
        }

        for (JComboBox<String> combo : dropdowns) {
            Object current = combo.getSelectedItem();
            combo.removeAllItems();
            for (String animal : new String[]{"Rato", "Dormouse", "Beija-flor", "Camaleão"}) {
                
                if (!selected.contains(animal) || animal.equals(current)) {
                    combo.addItem(animal);
                }
            }
            combo.setSelectedItem(current);
        }
    }

    public ArrayList<JogadorSelecionado> getJogadoresSelecionados() {
        return jogadoresSelecionados;
    }
}

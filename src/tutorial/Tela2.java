package tutorial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tela2 extends JDialog {
	private Font bungeeFont;
    private Font lilitaFont;
    private JPanel containerPanel;

    public Tela2(JFrame owner) {
    	super(owner, "Regras Gerais", true); // true = modal
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
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Casas");
        setAlwaysOnTop(true);
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(48, 176, 255), 9));
        // Screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.41);
        int height = (int) (screenSize.height * 0.9);
        setSize(width, height);
        setLocationRelativeTo(null);

        // === Container principal com GridBagLayout ===
        containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setBackground(Color.WHITE);
        containerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 15, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // === Título ===
        JLabel casasTitulo = new JLabel("Casas", SwingConstants.CENTER);
        casasTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        casasTitulo.setFont(bungeeFont.deriveFont(Font.PLAIN, 25f));
        gbc.gridy = 0;
        containerPanel.add(casasTitulo, gbc);

        // === Dados de exemplo ===
        String[] titles = {"Casa Comum", "Casa Presa", "Casa Minigame", "Casa Predador"};
        String[] descriptions = {
                "Se o jogador cair nessa casa, nada acontece",
                "Cada jogador começa com 6 presas, na medida que o jogador evolui, suas presas são trocadas. Caia nessa casa para ganhar pontos se o seu animal for mais evoluído que a presa",
                "Se o jogador cair nessa casa, um quiz sobre cadeias alimentares é acionado, responda corretamente para ganhar pontos",
                "Se o jogador cair nessa casa, perde 5 pontos"
        };
        String[] images = {
                "/images/tutorialImagens/comumExemplo.png",
                "/images/tutorialImagens/presaExemplo.png",
                "/images/tutorialImagens/minigame.png",
                "/images/tutorialImagens/predadorExemplo.png"
        };
        
        Color[] coresPainel = {
        		new Color(128, 255, 170),
        		new Color(204, 255, 153),
        		new Color(255, 255, 153),
        		new Color(255, 153, 153),
        		
        };

        // === Painéis das casas ===
        for (int i = 0; i < 4; i++) {
            gbc.gridy = i + 1;
            containerPanel.add(criarPainelCasa(titles[i], descriptions[i], images[i], coresPainel[i]), gbc);
        }

        // === Botão "Começar" ===
        JButton btnComecar = new JButton("Começar");
        btnComecar.setBorder(null);
        btnComecar.setFocusPainted(false);
        btnComecar.setForeground(Color.white);
        btnComecar.setBackground(new Color(48, 176, 255)); 
        btnComecar.setPreferredSize(new Dimension(200, 60));
        btnComecar.setFont(new Font("Arial", Font.BOLD, 18));
        btnComecar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        gbc.insets = new Insets(45, 0, 0, 0);
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0;
        containerPanel.add(btnComecar, gbc);

        // Adiciona o container à janela
        add(containerPanel);
        setVisible(true);
    }

    private JPanel criarPainelCasa(String titulo, String descricao, String imgPath, Color cor) {
        JPanel painel = new JPanel(new GridBagLayout());

        painel.setBackground(cor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 25);

        // === Imagem à esquerda ===
        JLabel lblImagem = new JLabel();
        lblImagem.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagem.setPreferredSize(new Dimension(100, 100));

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(imgPath));
            Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            lblImagem.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            lblImagem.setText("[Imagem]");
            lblImagem.setHorizontalAlignment(SwingConstants.CENTER);
        }
        

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(lblImagem, gbc);

        // === Título ===
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(bungeeFont.deriveFont(Font.PLAIN, 20f));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 0, 0); // reset insets for title
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(lblTitulo, gbc);

        // === Descrição ===
        JLabel lblDescricao = new JLabel("<html><p style='width:400px'>" + descricao + "</p></html>");
        lblDescricao.setFont(lilitaFont.deriveFont(Font.PLAIN, 17.5f));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 20, 0); // reset insets for title
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(lblDescricao, gbc);

        return painel;
    }

}

package tutorial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tela1 extends JDialog {
	private Font bungeeFont;
    private Font lilitaFont;
    public Tela1(JFrame owner) {
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
         
        setTitle("Regras Gerais");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.4);
        int height = (int) (screenSize.height * 0.9);
        setSize(width, height);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(48, 176, 255), 9));
        // 🔹 Align the window to the left, top of the screen
        setLocationRelativeTo(null);

        // ===== Fundo branco =====
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(Color.WHITE);

        // ===== Painel principal (coluna) =====
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        // --- TÍTULO ---
        JLabel titulo = new JLabel("Regras Gerais", SwingConstants.CENTER);
        titulo.setFont(bungeeFont.deriveFont(Font.PLAIN, 25f));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        // --- SEÇÕES ---
        JPanel objetivoBox = criarCaixa(
                "Objetivo:",
                "Atravesse o tabuleiro do jogo com o seu animal para coletar pontos.<br><br>"
                        + "Alcance uma quantia específica de pontos, capturando presas, dando voltas e acertando perguntas, para evoluir seu animal. <br><br>"
                        + "O primeiro a chegar na quinta evolução ganha o jogo.",
                new Color(190, 230, 255)
        );

        JPanel pontosBox = criarCaixa(
                "Como ganhar pontos:",
                "Dando voltas no tabuleiro = 10 Pontos<br><br>"
                        + "Ganhando minigames = 10 Pontos<br><br>"
                        + "Capturando presas = 20 - 10 - 5 Pontos (Dependendo do nível)",
                new Color(150, 200, 255)
        );

        // --- BOTÃO ---
        JButton proximoBtn = new JButton("Próximo");
        proximoBtn.setBackground(new Color(48, 176, 255)); 
        proximoBtn.setForeground(Color.WHITE);
        proximoBtn.setFocusPainted(false);
        proximoBtn.setFont(new Font("Arial", Font.BOLD, 18));
        proximoBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        proximoBtn.setMaximumSize(new Dimension(300, 50));
        proximoBtn.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        proximoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // ===== Adiciona ao card =====
        card.add(titulo);
        card.add(objetivoBox);
        card.add(Box.createRigidArea(new Dimension(1000, 30)));
        card.add(pontosBox);
        card.add(Box.createRigidArea(new Dimension(100, 100)));
        card.add(proximoBtn);

        // ===== Alinha o card no topo, centralizado horizontalmente =====
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.PAGE_START; // 🔹 Alinha no topo
        gbc.weightx = 1;
        gbc.weighty = 1;
        background.add(card, gbc);

        add(background);
        setVisible(true);
    }

    private JPanel criarCaixa(String titulo, String texto, Color corFundo) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBackground(corFundo);
        box.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 🔹 Reduz a largura máxima das caixas
        int maxWidth = 700;
        box.setMaximumSize(new Dimension(maxWidth, Integer.MAX_VALUE));

        JLabel tituloLbl = new JLabel(titulo);
        tituloLbl.setFont(bungeeFont.deriveFont(Font.PLAIN, 22f));

        JLabel textoLbl = new JLabel("<html>" + texto + "</html>");
        textoLbl.setFont(lilitaFont.deriveFont(Font.PLAIN, 18f));

        box.add(tituloLbl);
        box.add(Box.createRigidArea(new Dimension(0, 10)));
        box.add(textoLbl);

        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        return box;
    }

}

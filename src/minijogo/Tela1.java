package minijogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tela1 extends JFrame {

    public Tela1() {
        setTitle("Regras Gerais");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.5);
        int height = screenSize.height;
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🔹 Align the window to the left, top of the screen
        setLocationRelativeTo(null);

        // ===== Fundo branco =====
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(Color.WHITE);

        // ===== Painel principal (coluna) =====
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        // --- TÍTULO ---
        JLabel titulo = new JLabel("Regras Gerais", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        // --- SEÇÕES ---
        JPanel objetivoBox = criarCaixa(
                "Objetivo:",
                "Obtenha pontos se movimentando (utilizando o dado) com seu animal pelo tabuleiro.<br><br>"
                        + "Alcance uma quantia específica de pontos para evoluir seu animal. "
                        + "O primeiro a evoluir ganha o jogo.",
                new Color(190, 230, 255)
        );

        JPanel pontosBox = criarCaixa(
                "Como ganhar pontos:",
                "Dando voltas no tabuleiro = 10 Pontos<br>"
                        + "Ganhando minigames = 5 Pontos<br>"
                        + "Capturando presas = 5 Pontos",
                new Color(150, 200, 255)
        );

        // --- BOTÃO ---
        JButton proximoBtn = new JButton("Próximo");
        proximoBtn.setBackground(new Color(0, 120, 255));
        proximoBtn.setForeground(Color.WHITE);
        proximoBtn.setFocusPainted(false);
        proximoBtn.setFont(new Font("Arial", Font.BOLD, 18));
        proximoBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        proximoBtn.setMaximumSize(new Dimension(300, 50));
        proximoBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        proximoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Ir para a próxima tela (aqui você implementa a próxima)");
            }
        });

        // ===== Adiciona ao card =====
        card.add(titulo);
        card.add(objetivoBox);
        card.add(Box.createRigidArea(new Dimension(1000, 30)));
        card.add(pontosBox);
        card.add(Box.createRigidArea(new Dimension(100, 170)));
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
        int maxWidth = 600;
        box.setMaximumSize(new Dimension(maxWidth, Integer.MAX_VALUE));

        JLabel tituloLbl = new JLabel(titulo);
        tituloLbl.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel textoLbl = new JLabel("<html><div style='font-size:16px;'>" + texto + "</div></html>");
        textoLbl.setFont(new Font("Arial", Font.PLAIN, 16));

        box.add(tituloLbl);
        box.add(Box.createRigidArea(new Dimension(0, 10)));
        box.add(textoLbl);

        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        return box;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Tela1::new);
    }
}

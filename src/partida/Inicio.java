package partida;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Inicio extends JFrame {
    private JButton btnPlay;
    private JButton btnExit;

    private Runnable aoClicarPlay;

    public Inicio(Runnable aoClicarPlay) {
        this.aoClicarPlay = aoClicarPlay;

        setTitle("Tela de Início");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // tela cheia
        setUndecorated(false); // opcional: remove barra de título para parecer fullscreen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel com imagem de fundo redimensionada
        JPanel painelFundo = new JPanel() {
            private Image fundo = new ImageIcon(getClass().getResource("/images/fundo/Inicio.png")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // desenha imagem redimensionada para preencher todo o painel
                g.drawImage(fundo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        painelFundo.setLayout(new GridBagLayout()); // para centralizar componentes

        // Painel para os botões (para organizar em linha)
        JPanel painelBotoes = new JPanel();
        painelBotoes.setOpaque(false); // deixa transparente para aparecer fundo

        btnPlay = new JButton(new ImageIcon(getClass().getResource("/images/fundo/play.png")));
        btnExit = new JButton(new ImageIcon(getClass().getResource("/images/fundo/exit.png")));

        btnPlay.setBorderPainted(false);
        btnPlay.setContentAreaFilled(false);
        btnExit.setBorderPainted(false);
        btnExit.setContentAreaFilled(false);

        painelBotoes.add(btnPlay);
        painelBotoes.add(Box.createRigidArea(new Dimension(10, 0))); // espaço entre os botões
        painelBotoes.add(btnExit);

        // Adiciona os botões centralizados no painel de fundo
        painelFundo.add(painelBotoes, new GridBagConstraints());

        setContentPane(painelFundo);

        // Ações dos botões
        btnPlay.addActionListener(e -> {
            setVisible(false);
            dispose();
            if (aoClicarPlay != null) {
                aoClicarPlay.run();
            }
        });

        btnExit.addActionListener(e -> System.exit(0));
    }

    public void exibir() {
        setVisible(true);
    }
}

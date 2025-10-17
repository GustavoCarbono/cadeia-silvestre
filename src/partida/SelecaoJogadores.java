package partida;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class SelecaoJogadores extends JFrame {

	private static final long serialVersionUID = 1L;

	private Consumer<Integer> onSelecionar;

	public SelecaoJogadores(Consumer<Integer> onSelecionar) {
		this.onSelecionar = onSelecionar;
		setTitle("Seleção de Jogadores");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new GridBagLayout());

		JPanel container = new JPanel(new GridLayout(1, 4, 20, 20));
		container.setOpaque(false);

		container.add(criarBotao("1 Player", 1));
		container.add(criarBotao("2 Players", 2));
		container.add(criarBotao("3 Players", 3));
		container.add(criarBotao("4 Players", 4));

		add(container, new GridBagConstraints());
	}

	private JButton criarBotao(String texto, int valor) {
		JButton btn = new JButton(texto);
		btn.setFont(new Font("SansSerif", Font.BOLD, 28));
		btn.setFocusPainted(false);
		btn.setBackground(new Color(48, 176, 255));
		btn.setForeground(Color.WHITE);
		btn.addActionListener(e -> {
			setVisible(false);
			dispose();
			if (onSelecionar != null) onSelecionar.accept(valor);
		});
		return btn;
	}
}



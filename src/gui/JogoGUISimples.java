package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import model.DAO;
import partida.*;
import utils.Movimentacao;

public class JogoGUISimples extends JFrame {
    private Partida partida;
    private DAO dao;
    private Movimentacao movimentacao;
    
    // Componentes principais
    private JPanel painelTabuleiro;
    private JPanel painelJogadores;
    private JPanel painelControle;
    private JLabel labelDado;
    private JLabel labelTurno;
    private JLabel labelJogadorAtual;
    private JButton botaoRolarDado;
    private JButton botaoNovoJogo;
    private JButton botaoSair;
    
    // Cores para os jogadores
    private Color[] coresJogadores = {
        new Color(255, 100, 100), // Vermelho
        new Color(100, 255, 100), // Verde
        new Color(100, 100, 255), // Azul
        new Color(255, 255, 100)  // Amarelo
    };
    
    // Células do tabuleiro
    private JLabel[] celulas;
    
    public JogoGUISimples() {
        inicializarComponentes();
        configurarJanela();
        criarInterface();
        conectarEventos();
        iniciarJogo();
    }
    
    private void inicializarComponentes() {
        dao = new DAO();
        dao.conectar();
        movimentacao = new Movimentacao();
        
        // Criar jogadores padrão
        List<Jogador> jogadores = new ArrayList<>();
        jogadores.add(new Jogador("Jogador 1", new Animal(1, "Macaco", "Gorila", 20, "Jogador 1")));
        jogadores.add(new Jogador("Jogador 2", new Animal(2, "Ema", "Avestruz", 20, "Jogador 2")));
        jogadores.add(new Jogador("Jogador 3", new Animal(3, "Puma", "Leão", 20, "Jogador 3")));
        jogadores.add(new Jogador("Jogador 4", new Animal(4, "Elefante", "Elefante Africano", 20, "Jogador 4")));
        
        partida = new Partida(20, jogadores);
        
        // Inicializar componentes
        painelTabuleiro = new JPanel();
        painelJogadores = new JPanel();
        painelControle = new JPanel();
        
        labelDado = new JLabel("🎲", JLabel.CENTER);
        labelTurno = new JLabel("Turno: 1", JLabel.CENTER);
        labelJogadorAtual = new JLabel("Jogador Atual: " + partida.getOrdemJogador().get(0).getJogador(), JLabel.CENTER);
        
        botaoRolarDado = new JButton("Rolar Dado");
        botaoNovoJogo = new JButton("Novo Jogo");
        botaoSair = new JButton("Sair");
        
        // Configurar fonte do dado
        labelDado.setFont(new Font("Arial", Font.BOLD, 48));
        labelTurno.setFont(new Font("Arial", Font.BOLD, 16));
        labelJogadorAtual.setFont(new Font("Arial", Font.BOLD, 16));
    }
    
    private void configurarJanela() {
        setTitle("Cadeia Silvestre - Jogo de Tabuleiro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);
    }
    
    private void criarInterface() {
        // Painel do tabuleiro
        criarTabuleiro();
        add(painelTabuleiro, BorderLayout.CENTER);
        
        // Painel lateral direito
        JPanel painelLateral = new JPanel(new BorderLayout());
        painelLateral.setPreferredSize(new Dimension(250, 0));
        painelLateral.setBorder(BorderFactory.createTitledBorder("Controles"));
        
        // Painel de dados
        JPanel painelDados = new JPanel(new GridLayout(3, 1, 5, 5));
        painelDados.setBorder(BorderFactory.createTitledBorder("Dados"));
        painelDados.add(labelDado);
        painelDados.add(labelTurno);
        painelDados.add(labelJogadorAtual);
        
        // Painel de controle
        painelControle.setLayout(new GridLayout(3, 1, 5, 5));
        painelControle.setBorder(BorderFactory.createTitledBorder("Ações"));
        painelControle.add(botaoRolarDado);
        painelControle.add(botaoNovoJogo);
        painelControle.add(botaoSair);
        
        // Painel de jogadores
        criarPainelJogadores();
        
        painelLateral.add(painelDados, BorderLayout.NORTH);
        painelLateral.add(painelControle, BorderLayout.CENTER);
        painelLateral.add(painelJogadores, BorderLayout.SOUTH);
        
        add(painelLateral, BorderLayout.EAST);
        
        // Atualizar interface
        atualizarInterface();
    }
    
    private void criarTabuleiro() {
        painelTabuleiro.setLayout(new GridLayout(4, 5, 2, 2));
        painelTabuleiro.setBorder(BorderFactory.createTitledBorder("Tabuleiro"));
        painelTabuleiro.setBackground(new Color(34, 139, 34)); // Verde floresta
        
        celulas = new JLabel[20];
        
        for (int i = 0; i < 20; i++) {
            celulas[i] = new JLabel();
            celulas[i].setPreferredSize(new Dimension(80, 80));
            celulas[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            celulas[i].setBackground(new Color(245, 245, 220)); // Bege claro
            celulas[i].setOpaque(true);
            celulas[i].setHorizontalAlignment(JLabel.CENTER);
            celulas[i].setVerticalAlignment(JLabel.CENTER);
            celulas[i].setText(String.valueOf(i));
            celulas[i].setFont(new Font("Arial", Font.BOLD, 12));
            
            // Destacar posição inicial
            if (i == 0) {
                celulas[i].setBackground(new Color(255, 215, 0)); // Dourado
            }
            
            painelTabuleiro.add(celulas[i]);
        }
    }
    
    private void criarPainelJogadores() {
        painelJogadores.setLayout(new GridLayout(4, 1, 5, 5));
        painelJogadores.setBorder(BorderFactory.createTitledBorder("Jogadores"));
        
        for (int i = 0; i < partida.getJogadores().size(); i++) {
            Jogador jogador = partida.getJogadores().get(i);
            JPanel painelJogador = new JPanel(new BorderLayout());
            painelJogador.setBorder(BorderFactory.createLineBorder(coresJogadores[i], 2));
            painelJogador.setBackground(new Color(240, 240, 240));
            
            JLabel nomeJogador = new JLabel(jogador.getJogador());
            nomeJogador.setFont(new Font("Arial", Font.BOLD, 12));
            
            JLabel animalJogador = new JLabel(jogador.getAnimal().getNome());
            animalJogador.setFont(new Font("Arial", Font.ITALIC, 10));
            
            JLabel pontosJogador = new JLabel("Pontos: " + jogador.getAnimal().getPontosEvoluir());
            pontosJogador.setFont(new Font("Arial", Font.PLAIN, 10));
            
            JLabel posicaoJogador = new JLabel("Posição: " + jogador.getAnimal().getX());
            posicaoJogador.setFont(new Font("Arial", Font.PLAIN, 10));
            
            JPanel infoJogador = new JPanel(new GridLayout(3, 1));
            infoJogador.add(animalJogador);
            infoJogador.add(pontosJogador);
            infoJogador.add(posicaoJogador);
            
            painelJogador.add(nomeJogador, BorderLayout.NORTH);
            painelJogador.add(infoJogador, BorderLayout.CENTER);
            
            painelJogadores.add(painelJogador);
        }
    }
    
    private void conectarEventos() {
        botaoRolarDado.addActionListener(e -> rolarDado());
        botaoNovoJogo.addActionListener(e -> novoJogo());
        botaoSair.addActionListener(e -> System.exit(0));
    }
    
    private void iniciarJogo() {
        JOptionPane.showMessageDialog(this, 
            "Bem-vindo ao Cadeia Silvestre!\n\n" +
            "• Clique em 'Rolar Dado' para jogar\n" +
            "• Cada jogador move seu animal pelo tabuleiro\n" +
            "• Ganha quem evoluir primeiro\n\n" +
            "Boa sorte!", 
            "Início do Jogo", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void rolarDado() {
        if (partida.getFinalizou()) {
            JOptionPane.showMessageDialog(this, "O jogo já terminou!");
            return;
        }
        
        Jogador jogadorAtual = partida.getOrdemJogador().get(0);
        
        // Animar o dado
        animarDado();
        
        // Executar movimento
        boolean movimentoRealizado = movimentacao.mover(jogadorAtual, partida, dao);
        
        if (movimentoRealizado) {
            // Atualizar interface
            atualizarInterface();
            
            // Verificar se o jogo terminou
            if (partida.getFinalizou()) {
                JOptionPane.showMessageDialog(this, "Parabéns! " + jogadorAtual.getJogador() + " venceu o jogo!");
            } else {
                // Próximo turno
                partida.aumentarTurnoAtual();
                partida.mudarOrdemJogador();
                atualizarInterface();
            }
        }
    }
    
    private void animarDado() {
        botaoRolarDado.setEnabled(false);
        
        Timer timer = new Timer(100, new ActionListener() {
            private int contador = 0;
            private String[] facesDado = {"⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (contador < 10) {
                    labelDado.setText(facesDado[contador % 6]);
                    contador++;
                } else {
                    ((Timer) e.getSource()).stop();
                    botaoRolarDado.setEnabled(true);
                }
            }
        });
        
        timer.start();
    }
    
    private void novoJogo() {
        int opcao = JOptionPane.showConfirmDialog(this, 
            "Deseja iniciar um novo jogo?", 
            "Novo Jogo", 
            JOptionPane.YES_NO_OPTION);
        
        if (opcao == JOptionPane.YES_OPTION) {
            // Recriar jogadores
            List<Jogador> jogadores = new ArrayList<>();
            jogadores.add(new Jogador("Jogador 1", new Animal(1, "Macaco", "Gorila", 20, "Jogador 1")));
            jogadores.add(new Jogador("Jogador 2", new Animal(2, "Ema", "Avestruz", 20, "Jogador 2")));
            jogadores.add(new Jogador("Jogador 3", new Animal(3, "Puma", "Leão", 20, "Jogador 3")));
            jogadores.add(new Jogador("Jogador 4", new Animal(4, "Elefante", "Elefante Africano", 20, "Jogador 4")));
            
            partida = new Partida(20, jogadores);
            atualizarInterface();
        }
    }
    
    private void atualizarInterface() {
        // Limpar todas as células
        for (int i = 0; i < 20; i++) {
            celulas[i].setText(String.valueOf(i));
            celulas[i].setBackground(new Color(245, 245, 220));
            celulas[i].setForeground(Color.BLACK);
        }
        
        // Destacar posição inicial
        celulas[0].setBackground(new Color(255, 215, 0));
        
        // Atualizar posições dos animais
        for (int i = 0; i < partida.getJogadores().size(); i++) {
            Jogador jogador = partida.getJogadores().get(i);
            Animal animal = jogador.getAnimal();
            int posicao = animal.getX();
            
            if (posicao < 20) {
                String textoAtual = celulas[posicao].getText();
                String novoTexto = textoAtual + "\n" + animal.getNome().substring(0, Math.min(3, animal.getNome().length()));
                celulas[posicao].setText(novoTexto);
                celulas[posicao].setBackground(coresJogadores[i]);
                celulas[posicao].setForeground(Color.WHITE);
            }
        }
        
        // Atualizar informações
        labelTurno.setText("Turno: " + partida.getTurnoAtual());
        if (!partida.getOrdemJogador().isEmpty()) {
            labelJogadorAtual.setText("Jogador Atual: " + partida.getOrdemJogador().get(0).getJogador());
        }
        
        // Atualizar painel de jogadores
        painelJogadores.removeAll();
        criarPainelJogadores();
        painelJogadores.revalidate();
        painelJogadores.repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JogoGUISimples().setVisible(true);
        });
    }
}

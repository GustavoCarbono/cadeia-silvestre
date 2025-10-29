package utils;

import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import minijogo.JogadorSelecionado;
import model.DAO;
import model.PredacaoDAO;
import partida.*;
import tutorial.Tela1;
import tutorial.Tela2;
import view.Interface;
import view.PresaView;

public class Aplicacao {
    Partida partida;

    public static void main(String[] args) {
	    	 Tela1 telaTutorial1 = new Tela1(null);

	         
	         Tela2 telaTutorial2 = new Tela2(null);

            DAO dao = new DAO();
            dao.conectar();

      
            List<Integer> xAlt = new ArrayList<>();
            List<Integer> comecos = new ArrayList<>();
            List<Integer> fins = new ArrayList<>();

            // cores setup
            String[] coresNomes = { "red", "green", "blue", "yellow" };
            List<Cores> cores = new ArrayList<>();
            for (String nomeCor : coresNomes) {
                Cores cor = new Cores();
                cor.setNomeCor(nomeCor);
                cor.setImagemCor("/images/fundo/" + nomeCor + ".png");
                cores.add(cor);
            }

            // Tela de seleção de jogadores
            TelaSelecaoJogadores tela = new TelaSelecaoJogadores(null);
            tela.setVisible(true);

            ArrayList<JogadorSelecionado> jogadoresS = tela.getJogadoresSelecionados();
            if(jogadoresS.get(0).getNomeAnimal() == null || jogadoresS.get(1).getNomeAnimal() == null) {
            	 JOptionPane.showMessageDialog(null,  "Erro: 2 jogadores necessários para jogar");
            	 System.exit(0);
            };

            Map<String, AnimalPreset> animalPresets = new HashMap<>();
            animalPresets.put("Rato", new AnimalPreset("Rato", "Doninha", 20, 1, "/images/animaisPrincipais/gen0/rato.png", null));
            animalPresets.put("Camaleão", new AnimalPreset("Camaleão", "Teiu", 20, 1, "/images/animaisPrincipais/gen0/camaleao.png", null));
            animalPresets.put("Beija-flor", new AnimalPreset("Beija-flor", "Corvo", 20, 1, "/images/animaisPrincipais/gen0/beija-flor.png", null));
            animalPresets.put("Dormouse", new AnimalPreset("Dormouse", "Feneco", 20, 1, "/images/animaisPrincipais/gen0/dormouse.png", null));

            // posições aleatórias únicas para presas
            List<Integer> disponiveis = new ArrayList<>();
            for (int pos = 1; pos < 28; pos++) {
                if (pos == 4 || pos == 11 || pos == 18 || pos == 25 || pos == 7 || pos == 13 || pos == 27 || pos == 21)
                    continue;
                disponiveis.add(pos);
            }
            Collections.shuffle(disponiveis);

            List<Integer> posicoesPresas = new ArrayList<>();

            // gerar presas somente para os animais escolhidos
            Map<JogadorSelecionado, List<PresaView>> presasPorJogador = new HashMap<>();
            for (JogadorSelecionado sel : jogadoresS) {
                String nomeAnimal = sel.getNomeAnimal().toLowerCase();
                List<PresaView> presas = new ArrayList<>();

                // busca presas do banco
                List<PredacaoDAO> presasDoBanco = DAO.buscarPresa(nomeAnimal);
                for (int j = 0; j < presasDoBanco.size(); j++) {
                    PredacaoDAO presaEspecifica = presasDoBanco.get(j);
                    PresaView novaPresa = new PresaView();
                    novaPresa.setNomePresa(presaEspecifica.getNomePresa());
                    presas.add(novaPresa);
                }
                Collections.shuffle(presas);

                // atribui posições únicas
                for (PresaView presa : presas) {
                	//adiciona um valor de posicao pra presa, e remove ela das disponiveis
                    int pos = disponiveis.remove(0);
                    presa.setPosicao(pos);
                    posicoesPresas.add(pos);
                }

                presasPorJogador.put(sel, presas);
            }

            // Montar lista de jogadores com animais e presas
            ArrayList<Jogador> jogadores = new ArrayList<>();
            for (int i = 0; i < jogadoresS.size(); i++) {
                JogadorSelecionado sel = jogadoresS.get(i);
                AnimalPreset preset = animalPresets.get(sel.getNomeAnimal());
                if (preset != null) {
                    List<PresaView> presas = presasPorJogador.get(sel);
                    Animal animal = new Animal(
                        i + 1,
                        preset.nomeAnimal,
                        preset.nomeEvolucao,
                        preset.vida,
                        preset.nivel,
                        sel.getNomeJogador(),
                        preset.imgPath,
                        cores.get(i),
                        presas
                    );
                    jogadores.add(new Jogador(sel.getNomeJogador(), animal));
                }
            }

            // iniciar partida
            Movimentacao mov = new Movimentacao();
            Partida partida = new Partida(28, jogadores, xAlt, comecos, fins, posicoesPresas);
            Interface gui = new Interface(partida.getOrdemJogador(), 28, partida);

            gui.setRolarDados(() -> {
                if (!partida.getFinalizou()) {
                    int dado = mov.mover(partida.getOrdemJogador().get(0), partida, dao, gui);
                    gui.atualizarDados(dado);
                    partida.aumentarTurnoAtual();
                    partida.mudarOrdemJogador();
                }
            });

    }
}

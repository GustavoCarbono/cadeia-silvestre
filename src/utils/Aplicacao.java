package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import minijogo.JogadorSelecionado;
import model.DAO;
import partida.Animal;
import partida.Jogador;
import partida.Partida;
import partida.SelecaoJogadores;
import partida.SelecaoAnimais;
import view.Interface;
import view.PresaView;

public class Aplicacao {
	Partida partida;
	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        DAO dao = new DAO();
        dao.conectar();

			List<PresaView> presasCamaleao = new ArrayList<>();
			List<PresaView> presasDormouse = new ArrayList<>();
			List<PresaView> presasBeijaflor = new ArrayList<>();
			List<PresaView> presasRato = new ArrayList<>();
			
			List<Integer> xAlt = new ArrayList<>();
			List<Integer> comecos = new ArrayList<>();
			List<Integer> fins = new ArrayList<>();

			for(int j = 0; j<3; j++) {
				String presaAtual = DAO.buscarPresa("dormouse").get(j).getNomePresa();
				PresaView novaPresa = new PresaView();
				novaPresa.setNomePresa(presaAtual);
				presasDormouse.add(novaPresa);
			}
			Collections.shuffle(presasDormouse);
			for(int j = 0; j<3; j++) {
				String presaAtual = DAO.buscarPresa("rato").get(j).getNomePresa();
				PresaView novaPresa = new PresaView();
				novaPresa.setNomePresa(presaAtual);
				presasRato.add(novaPresa);
			}
			Collections.shuffle(presasRato);
			for(int j = 0; j<3; j++) {
				String presaAtual = DAO.buscarPresa("camaleao").get(j).getNomePresa();
				PresaView novaPresa = new PresaView();
				novaPresa.setNomePresa(presaAtual);
				presasCamaleao.add(novaPresa);
			}
			Collections.shuffle(presasCamaleao);
			for(int j = 0; j<3; j++) {
				String presaAtual = DAO.buscarPresa("beija-flor").get(j).getNomePresa();
				PresaView novaPresa = new PresaView();
				novaPresa.setNomePresa(presaAtual);
				presasBeijaflor.add(novaPresa);
			}
			Collections.shuffle(presasBeijaflor);

			// posições aleatórias únicas para as 12 presas (exclui inicio e predadores)
			List<Integer> disponiveis = new ArrayList<>();
			for (int pos = 1; pos < 28; pos++) {
				if (pos == 4 || pos == 11 || pos == 18 || pos == 25 || pos == 7 || pos == 13 || pos == 27 || pos == 21) continue;
				disponiveis.add(pos);
			}
			Collections.shuffle(disponiveis);

			// Guarda as posições das presas para excluir do quiz
			List<Integer> posicoesPresas = new ArrayList<>();
			for (int j = 0; j < 3; j++) {
				int pos = disponiveis.remove(0);
				presasDormouse.get(j).setPosicao(pos);
				posicoesPresas.add(pos);
			}
			for (int j = 0; j < 3; j++) {
				int pos = disponiveis.remove(0);
				presasRato.get(j).setPosicao(pos);
				posicoesPresas.add(pos);
			}
			for (int j = 0; j < 3; j++) {
				int pos = disponiveis.remove(0);
				presasCamaleao.get(j).setPosicao(pos);
				posicoesPresas.add(pos);
			}
			for (int j = 0; j < 3; j++) {
				int pos = disponiveis.remove(0);
				presasBeijaflor.get(j).setPosicao(pos);
				posicoesPresas.add(pos);
			}

			String[] coresNomes = new String[4];
			coresNomes[0] = "red";
			coresNomes[1] = "green";
			coresNomes[2] = "blue";
			coresNomes[3] = "yellow";
			List<Cores> cores = new ArrayList<>();
			for(int i = 0; i< coresNomes.length; i++) {
				Cores cor = new Cores();
				cor.setNomeCor(coresNomes[i]);
				cor.setImagemCor("/images/fundo/" + coresNomes[i] + ".png");
				cores.add(cor);
			}

			
			TelaSelecaoJogadores tela = new TelaSelecaoJogadores(null); // modal
            tela.setVisible(true); // blocks until Confirm is pressed

            
            ArrayList<JogadorSelecionado> jogadoresS = tela.getJogadoresSelecionados();

            if(jogadoresS == null) {
            	System.exit(0);
            }
	
            
         // Presets for each animal type
            Map<String, AnimalPreset> animalPresets = new HashMap<>();
            animalPresets.put("Rato", new AnimalPreset("Rato", "Doninha", 20, 1, "/images/animaisPrincipais/gen0/rato.png", presasRato));
            animalPresets.put("Camaleão", new AnimalPreset("Camaleão", "Teiu", 20, 1, "/images/animaisPrincipais/gen0/camaleao.png", presasCamaleao));
            animalPresets.put("Beija-flor", new AnimalPreset("Beija-flor", "Corvo", 20, 1, "/images/animaisPrincipais/gen0/beija-flor.png", presasBeijaflor));
            animalPresets.put("Dormouse", new AnimalPreset("Dormouse", "Feneco", 20, 1, "/images/animaisPrincipais/gen0/dormouse.png", presasDormouse));
			
            ArrayList<Jogador> jogadores = new ArrayList<>();
            for (int i = 0; i < jogadoresS.size(); i++) {
                JogadorSelecionado sel = jogadoresS.get(i);
                AnimalPreset preset = animalPresets.get(sel.getNomeAnimal());
                if (preset != null) {
                    Animal animal = new Animal(
                        i+1,
                        preset.nomeAnimal,
                        preset.nomeEvolucao,
                        preset.vida,
                        preset.nivel,
                        sel.getNomeJogador(),
                        preset.imgPath,
                        cores.get(i),  // assign color per player
                        preset.presas
                    );
                    jogadores.add(new Jogador(sel.getNomeJogador(), animal));
                } else {
                    // fallback if somehow user didn't select a valid animal
                    JOptionPane.showMessageDialog(null, "Animal inválido para o jogador: " + sel.getNomeJogador());
                }
            }
        		//----------------------------------------
				
            
				
				Movimentacao mov = new Movimentacao();
				Partida partida = new Partida(28, jogadores, xAlt, comecos, fins, posicoesPresas);
				Interface gui = new Interface(partida.getOrdemJogador(), 28, partida);
				gui.setRolarDados(() -> {
					if(partida.getFinalizou() != true) {
						int dado = mov.mover(partida.getOrdemJogador().get(0), partida, dao, gui);
						gui.atualizarDados(dado);
						partida.aumentarTurnoAtual();
						partida.mudarOrdemJogador();
					}
				});
			});
			
	}	
}
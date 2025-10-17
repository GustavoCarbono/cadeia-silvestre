package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.DAO;
import partida.Jogador;
import partida.Partida;
import partida.SelecaoAnimais;
import partida.SelecaoJogadores;
import view.Interface;
import view.PresaView;

public class Aplicacao {
	Partida partida;
	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        DAO dao = new DAO();
        dao.conectar();
        Quiz quiz = new Quiz();

        SelecaoJogadores selecao = new SelecaoJogadores(qtd -> {
			List<PresaView> presasCamaleao = new ArrayList<>();
			List<PresaView> presasDormouse = new ArrayList<>();
			List<PresaView> presasBeijaflor = new ArrayList<>();
			List<PresaView> presasRato = new ArrayList<>();
			
			List<Integer> xAlt = new ArrayList<>();
			List<Integer> comecos = new ArrayList<>();
			List<Integer> fins = new ArrayList<>();

			for(int j = 0; j<3; j++) {
				String presaAtual = DAO.buscarPresa("Dormouse").get(j).getNomePresa();
				PresaView novaPresa = new PresaView();
				novaPresa.setNomePresa(presaAtual);
				presasDormouse.add(novaPresa);
			}
			Collections.shuffle(presasDormouse);
			for(int j = 0; j<3; j++) {
				String presaAtual = DAO.buscarPresa("Rato").get(j).getNomePresa();
				PresaView novaPresa = new PresaView();
				novaPresa.setNomePresa(presaAtual);
				presasRato.add(novaPresa);
			}
			Collections.shuffle(presasRato);
			for(int j = 0; j<3; j++) {
				String presaAtual = DAO.buscarPresa("Camaleao").get(j).getNomePresa();
				PresaView novaPresa = new PresaView();
				novaPresa.setNomePresa(presaAtual);
				presasCamaleao.add(novaPresa);
			}
			Collections.shuffle(presasCamaleao);
			for(int j = 0; j<3; j++) {
				String presaAtual = DAO.buscarPresa("Beija-flor").get(j).getNomePresa();
				PresaView novaPresa = new PresaView();
				novaPresa.setNomePresa(presaAtual);
				presasBeijaflor.add(novaPresa);
			}
			Collections.shuffle(presasBeijaflor);

			// posições aleatórias únicas para as 12 presas (exclui inicio e predadores)
			List<Integer> disponiveis = new ArrayList<>();
			for (int pos = 1; pos < 28; pos++) {
				if (pos == 4 || pos == 11 || pos == 18 || pos == 25) continue;
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

			// Coleta nomes dos jogadores
			List<String> nomes = new ArrayList<>();
			for (int i = 0; i < qtd; i++) {
				String entrada = JOptionPane.showInputDialog(null, "Digite o nome do Jogador " + (i+1) + ":", "Nome do Jogador", JOptionPane.QUESTION_MESSAGE);
				nomes.add((entrada == null || entrada.trim().isEmpty()) ? ("Player " + (i+1)) : entrada.trim());
			}

			// Abre tela de seleção de animais
			SelecaoAnimais selecaoAnimais = new SelecaoAnimais(qtd, nomes, presasRato, presasCamaleao, presasBeijaflor, presasDormouse, cores, animaisSelecionados -> {
				List<Jogador> jogadores = new ArrayList<>();
				for (int i = 0; i < animaisSelecionados.size(); i++) {
					jogadores.add(new Jogador(nomes.get(i), animaisSelecionados.get(i)));
				}

				
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
			selecaoAnimais.setVisible(true);
        });
        selecao.setVisible(true);
        });
	}	
}
package utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import partida.Animal;
import partida.Jogador;
import partida.Partida;
import view.Interface;

public class Quiz {

	public static class Pergunta {
		public final String enunciado;
		public final String[] alternativas;
		public final int indiceCorreto; // 0..3

		public Pergunta(String enunciado, String[] alternativas, int indiceCorreto) {
			this.enunciado = enunciado;
			this.alternativas = alternativas;
			this.indiceCorreto = indiceCorreto;
		}
	}

	private final List<Pergunta> perguntas;
	private final Random random = new Random();

	public Quiz() {
		perguntas = new ArrayList<>();
		perguntas.add(new Pergunta(
			"🦁 O que é uma cadeia alimentar?",
			new String[]{
				"Um grupo de animais que vivem juntos.",
				"A sequência de seres vivos que se alimentam uns dos outros.",
				"A relação entre plantas e o solo.",
				"Um ciclo de reprodução entre espécies."
			}, 1));

		perguntas.add(new Pergunta(
			"🌱 Quem são os produtores em uma cadeia alimentar?",
			new String[]{
				"Animais herbívoros",
				"Animais carnívoros",
				"Plantas e algas",
				"Bactérias decompositoras"
			}, 2));

		perguntas.add(new Pergunta(
			"🐇 Os consumidores primários se alimentam de:",
			new String[]{
				"Outros carnívoros",
				"Plantas",
				"Restos orgânicos",
				"Decompositores"
			}, 1));

		perguntas.add(new Pergunta(
			"🐍 Um consumidor secundário se alimenta de:",
			new String[]{
				"Plantas",
				"Herbívoros",
				"Decompositores",
				"Minerais"
			}, 1));

		perguntas.add(new Pergunta(
			"🍄 Qual o papel dos decompositores na cadeia alimentar?",
			new String[]{
				"Produzir oxigênio",
				"Quebrar matéria orgânica e devolver nutrientes ao solo",
				"Caçar outros animais",
				"Filtrar a água"
			}, 1));

		perguntas.add(new Pergunta(
			"🦅 Qual sequência representa corretamente uma cadeia alimentar?",
			new String[]{
				"Sol → Planta → Gafanhoto → Sapo → Cobra → Águia",
				"Planta → Sol → Cobra → Sapo → Gafanhoto",
				"Águia → Cobra → Sapo → Gafanhoto → Planta",
				"Sol → Águia → Planta → Cobra → Gafanhoto"
			}, 0));

		perguntas.add(new Pergunta(
			"🌞 Qual é a principal fonte de energia para o início de todas as cadeias alimentares?",
			new String[]{
				"Água",
				"Sol",
				"Solo",
				"Oxigênio"
			}, 1));

		perguntas.add(new Pergunta(
			"🐠 Em uma cadeia alimentar marinha, quem geralmente é o produtor?",
			new String[]{
				"Peixes",
				"Algas e fitoplâncton",
				"Tubarões",
				"Caranguejos"
			}, 1));

		perguntas.add(new Pergunta(
			"🌳 O que pode acontecer se um elo da cadeia alimentar for eliminado?",
			new String[]{
				"Nada muda.",
				"Toda a cadeia pode ser afetada.",
				"Apenas os produtores desaparecem.",
				"A cadeia se fortalece."
			}, 1));

		perguntas.add(new Pergunta(
			"🐺 O que é um nível trófico?",
			new String[]{
				"O local onde o animal vive.",
				"O papel de cada ser vivo na cadeia alimentar.",
				"O tipo de solo onde as plantas crescem.",
				"A quantidade de energia perdida."
			}, 1));
	}

	public void executarQuiz(Jogador jogador, Partida partida, Interface gui) {
		Pergunta p = perguntas.get(random.nextInt(perguntas.size()));
		String titulo = "QUIZ";
		String msg = p.enunciado + "\n\n" +
			"a) " + p.alternativas[0] + "\n" +
			"b) " + p.alternativas[1] + "\n" +
			"c) " + p.alternativas[2] + "\n" +
			"d) " + p.alternativas[3] + "\n\n" +
			"Escolha a letra (a, b, c ou d):";

		String resp = JOptionPane.showInputDialog(null, msg, titulo, JOptionPane.QUESTION_MESSAGE);
		if (resp == null) return; // cancelado
		resp = resp.trim().toLowerCase();
		int escolha = switch (resp) {
			case "a" -> 0; case "b" -> 1; case "c" -> 2; case "d" -> 3; default -> -1;
		};

		Animal animal = jogador.getAnimal();
		if (escolha == p.indiceCorreto) {
			animal.setTotalPontos(animal.getTotalPontos() + 10);
			gui.mostrarMensagemTemporaria(
				"Acertou! +10 pontos para " + jogador.getJogador(),
				3000,
				new Color(102, 255, 102),
				2
			);
		} else {
			int novo = Math.max(0, animal.getTotalPontos() - 10);
			animal.setTotalPontos(novo);
			gui.mostrarMensagemTemporaria(
				"Errou! -10 pontos para " + jogador.getJogador(),
				3000,
				new Color(255, 0, 0),
				2
			);
		}

		gui.atualizarInfoJogador(partida.getOrdemJogador(), partida.getJogadores());
	}
}



package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.DAO;
import model.PredacaoDAO;
import partida.Animal;
import partida.Celula;
import partida.Jogador;
import partida.Partida;
import view.AnimalView;
import view.Interface;

public class AdicionarPresas {
	private List<Animal> animais;
	
	public void setListaPresas(Partida partida, DAO dao) {
		List<Animal> listaAnimal = new ArrayList<>();
		for(Jogador jogador1 : partida.getJogadores()) {
			System.out.println(jogador1.getJogador());
		}
		
		for(Jogador jogador : partida.getJogadores()) {
			Animal animal = jogador.getAnimal();
			List<String> selecionado = new ArrayList<>();
			List<PredacaoDAO> predacao = dao.buscarPresa(animal.getNome());
			for(PredacaoDAO presa : predacao) {
				selecionado.add(presa.getNomePresa());
			}
			Collections.shuffle(selecionado);
			for(int i=0; i<5; i++) {
				System.out.println("Jogador"+jogador.getJogador()+" tem Animal " + selecionado.get(i)+" tem index "+dao.buscarAnimal(selecionado.get(i)).getImg());
				Animal animalNovo = new Animal(IdUnica.getIdUnico(), selecionado.get(i), dao.buscarAnimal(selecionado.get(i)).getImg());
				listaAnimal.add(animalNovo);
				
			}
		}
		animais = listaAnimal;
	}
	
	public void addPresas(Partida partida, Interface gui, DAO dao) {
		setListaPresas(partida, dao);
		int j = 0;
		for(int i=0; i<28; i++) {
			Celula celula = partida.getTabuleiro().getGridMain(i);
			if(!celula.temJogador()) {
				celula.addAnimal(animais.get(j));
				gui.getCelulas()[i].addAnimal(new AnimalView(animais.get(j), (gui.getCelulas()[i].getWidth() / 2) - 10, (gui.getCelulas()[i].getWidth() / 2) - 10));
				j++;
			}
		}
	}
}
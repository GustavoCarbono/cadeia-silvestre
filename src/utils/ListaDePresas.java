package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.DAO;
import model.PredacaoDAO;
import partida.Animal;
import partida.Jogador;
import partida.Partida;

public class ListaDePresas {
	public List<Animal> getListaPresas(Partida partida, DAO dao) {
		List<Animal> listaAnimal = new ArrayList<>();
		for(Jogador jogador : partida.getJogadores()) {
			Animal animal = jogador.getAnimal();
			List<String> selecionado = new ArrayList<>();
			for(PredacaoDAO presa : dao.buscarPresa(animal.getNome())) {
				selecionado.add(presa.getNomePresa());
			}
			Collections.shuffle(selecionado);
			
			for(int i=0; i<6; i++) {
				listaAnimal.add(new Animal(selecionado.get(i), dao.buscarAnimal(selecionado.get(i)).getImg());
				System.out.println(listaAnimal.get(i));
			}
		}
		return listaAnimal;
	}
}
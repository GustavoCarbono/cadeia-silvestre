package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.DAO;
import partida.Animal;
import partida.Partida;

public class ListaDePresas {
	public List<Animal> getListaPresas(Partida partida, DAO dao) {
		Map<String, List<String>> PresasAnimal = new HashMap<>();
		List<Animal> listaAnimal = new ArrayList<>();
		List<String> presasUnicas = new ArrayList<>();
		List<String> presasComum = new ArrayList<>();
		
	}
}
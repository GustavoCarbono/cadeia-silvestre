package partida;

import java.util.List;

import utils.Cores;
import view.PresaView;

public class Animal {
	private int id;
	private String nome;
	private String dono;
	private String evolucao;
	private int totalPontos;
	private int pontosEvoluir;
	private int nivel;
	private int x;
	private Cores cor;
	private String img;
	private int caminho;//0 = caminho principal, resto igual outros caminho
	private List<PresaView> listaDePresas;
	
	public Animal(int id, String nome, String img) {
		this.id = id;
		this.nome = nome;
		this.img = img;
	}
	
	public Animal(int id, String nome, String evolucao, int pontosEvoluir, int nivel, String dono, String img, Cores cor, List<PresaView> listaDePresas) {
		this.id = id;
		this.nome = nome;
		this.dono = dono;
		this.evolucao = evolucao;
		this.totalPontos = 0;
		this.pontosEvoluir = pontosEvoluir;
		this.nivel = nivel;
		this.x = 0;
		this.img = img;
		this.cor = cor;
		this.caminho = 0;
		this.setListaDePresas(listaDePresas);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEvolucao() {
		return evolucao;
	}

	public void setEvolucao(String evolucao) {
		this.evolucao = evolucao;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getId() {
		return id;
	}

	public int getTotalPontos() {
		return totalPontos;
	}

	public void setTotalPontos(int totalPontos) {
		this.totalPontos = totalPontos;
	}

	public int getPontosEvoluir() {
		return pontosEvoluir;
	}

	public void setPontosEvoluir(int pontosEvoluir) {
		this.pontosEvoluir = pontosEvoluir;
	}

	public String getDono() {
		return dono;
	}

	public int getCaminho() {
		return caminho;
	}

	public void setCaminho(int caminho) {
		this.caminho = caminho;
	}

	public String getImg() {
		return img;
	}
	
	public void setImg(String img) {
		this.img = img;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public List<PresaView> getListaDePresas() {
		return listaDePresas;
	}

	public void setListaDePresas(List<PresaView> listaDePresas) {
		this.listaDePresas = listaDePresas;
	}


	public Cores getCor() {
		return cor;
	}

	public void setCor(Cores cor) {
		this.cor = cor;
	}
}
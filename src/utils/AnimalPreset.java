package utils;

import java.util.List;

import view.PresaView;

public class AnimalPreset {
    String nomeAnimal;
    String nomeEvolucao;
    int vida;
    int nivel;
    String imgPath;
    List<PresaView> presas;

    public AnimalPreset(String nomeAnimal, String nomeEvolucao, int vida, int nivel, String imgPath, List<PresaView> presas) {
        this.nomeAnimal = nomeAnimal;
        this.nomeEvolucao = nomeEvolucao;
        this.vida = vida;
        this.nivel = nivel;
        this.imgPath = imgPath;
        this.presas = presas;
    }
}
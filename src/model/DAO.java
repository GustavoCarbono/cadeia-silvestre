package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO {
	private Connection con;
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/dbAnimais";
	private String user = "root";
	private String password = "1234";
	
	public Connection conectar() {
		try {//conecta com o banco de dados
			Class.forName(driver);
			con = DriverManager.getConnection(url,user, password);
			return con;
		} catch (Exception e) {
			System.out.println("Erro de conexão");
			System.out.println(e);
			return null;
		}
	}
	
	public AnimaisDAO buscarAnimal(String animal) {// busca animais pelo nome
		String sql = "SELECT * FROM tbAnimais WHERE nomeAnimal = '"+animal+"'";
		
		try (PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery()){
			if(rs.next()) {
				return new AnimaisDAO(rs.getString("nomeAnimal"),
							rs.getString("evolucao"), rs.getInt("evoluirPontos"), rs.getString("img"));
			} else {
				System.out.println("resultado não encontrado");
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<PredacaoDAO> buscarPresa(String animal) {// retorna todas as presas do animal
		List<PredacaoDAO> predacao = new ArrayList<>();
		String sql = "SELECT nomePredador, nomePresa, pontosEvolucao FROM tbPredacao WHERE nomePredador = '"+animal+"'";
		
		try (PreparedStatement stmt = con.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while(rs.next()) {
				predacao.add(new PredacaoDAO(rs.getString("nomePredador"),
						rs.getString("nomePresa"), rs.getInt("pontosEvolucao")));
			}
			return predacao;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}

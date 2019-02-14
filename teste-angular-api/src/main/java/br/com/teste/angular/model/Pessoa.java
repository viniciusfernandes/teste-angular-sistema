package br.com.teste.angular.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Pessoa implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3316778299588109410L;
	private String nome;
	private int idade;
	@Id
	private String id;

	public Pessoa() {
	}

	public Pessoa(String nome, int idade, String id) {
		this.nome = nome;
		this.idade = idade;
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}
}

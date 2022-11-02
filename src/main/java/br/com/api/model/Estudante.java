package br.com.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "estudante")
public class Estudante extends EntidadePadrao{
	private String nome;
	
	public Estudante() {
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

}

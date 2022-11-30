package br.com.estudantes.dominio.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "estudante")
public class Estudante extends EntidadePadrao{
	
	@NotBlank(message = "O campo nome é obrigatório")
	private String nome;
	
	@NotNull(message = "O campo email é obrigatório")
	@Email
	private String email;
	
	public Estudante(String nome, String email) {
		this.nome = nome;
		this.email = email;
	}

	public Estudante() {
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Nome: " + this.nome + "\n";
	}

}

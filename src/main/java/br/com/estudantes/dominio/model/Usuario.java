package br.com.estudantes.dominio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "usuario")
public class Usuario extends EntidadePadrao{
	private static final long serialVersionUID = -9167018457513892130L;

	@NotEmpty
	@Column(unique = true, name = "usuario")
	private String nomeUsuario;
	
	@NotEmpty
	private String senha;
	
	@NotEmpty
	private String nome;
	
	@NotNull
	private boolean admin;
	
	public Usuario() {
	}

	public Usuario(String nomeUsuario, String senha, String nome, boolean admin) {
		this.nomeUsuario = nomeUsuario;
		this.senha = senha;
		this.nome = nome;
		this.admin = admin;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	
}

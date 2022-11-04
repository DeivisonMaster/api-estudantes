package br.com.api.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeraSenha {
	public static void main(String[] args) {
		BCryptPasswordEncoder gera = new BCryptPasswordEncoder();
		System.out.println(gera.encode("123")); // 123
	}
}

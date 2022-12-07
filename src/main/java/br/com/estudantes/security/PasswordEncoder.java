package br.com.estudantes.security;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
	private static final String SENHA = "123";
	private static final Logger LOGGER = Logger.getLogger(PasswordEncoder.class);

	public static String geraNovaSenhaCriptografada(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}
	
	public static void main(String[] args) {
		LOGGER.info(geraNovaSenhaCriptografada(SENHA));
	}
}

package br.com.estudantes.security;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class JWTCredenciais {
	private static final Logger LOGGER = Logger.getLogger(JWTCredenciais.class);
	
	static final String CHAVE = "minhaapi";
	static final String TOKEN_PREFIXO = "Bearer ";
	static final String HEADER_STRING = "Authorization";
	static final String SIGN_UP_URL = "/users/sign-up";
	static final long TEMPO_EXPIRACAO = 15552000000L;
	
	public static void main(String[] args) {
		LOGGER.info(geraTempoExpiracaoToken());
	}

	private static long geraTempoExpiracaoToken() {
		return TimeUnit.MILLISECONDS.convert(180, TimeUnit.DAYS);
	}
}

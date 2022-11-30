package br.com.estudantes.security;

import java.util.concurrent.TimeUnit;

public class JWTConstants {
	static final String SECRET = "minhasenha";
	static final String TOKEN_PREFIX = "Bearer ";
	static final String HEADER_STRING = "Authorization";
	static final long TEMPO_EXPIRACAO = 7776000000l;
	static final String SIGN_UP_URL = "/users/sign-up";
	
	public static void main(String[] args) {
		geradorTempoExpiracaoToken();
	}

	private static void geradorTempoExpiracaoToken() {
		System.out.println(TimeUnit.MILLISECONDS.convert(90, TimeUnit.DAYS));
	}
}

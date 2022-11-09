package br.com.api.security;

import java.util.concurrent.TimeUnit;

public class JWTConstants {
	static final String SECRET = "minhasenha";
	static final String TOKEN_PREFIX = "Bearer ";
	static final String HEADER_STRING = "Authorization";
	static final long TEMPO_EXPIRACAO = 86400000l;
	static final String SIGN_UP_URL = "/users/sign-up";
	
//	public static void main(String[] args) {
//		System.out.println(TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
//	}
}

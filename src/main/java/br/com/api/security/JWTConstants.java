package br.com.api.security;

import java.util.concurrent.TimeUnit;

public class JWTConstants {
	static final String SECRET = "brasil";
	static final String TOKEN_PREFIX = "Bearer ";
	static final String HEADER_STRING = "Authorization";
	static final long TEMPO_EXPIRACAO = 86400000l;
	
//	public static void main(String[] args) {
//		System.out.println(TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
//	}
}

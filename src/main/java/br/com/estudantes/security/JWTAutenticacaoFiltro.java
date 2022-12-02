package br.com.estudantes.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.estudantes.dominio.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @description JWT Autenticação
 */
public class JWTAutenticacaoFiltro extends UsernamePasswordAuthenticationFilter {
	private static final Logger LOGGER = Logger.getLogger(JWTAutenticacaoFiltro.class);
	private AuthenticationManager authManager;

	public JWTAutenticacaoFiltro(AuthenticationManager authManager) {
		this.authManager = authManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws FalhaAutenticacaoException {
		try {
			Usuario usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
			return this.authManager
					.authenticate(new UsernamePasswordAuthenticationToken(usuario.getUsuario(), usuario.getSenha()));
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new FalhaAutenticacaoException(e.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String nomeUsuario = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();
		String token = Jwts.builder()
				.setSubject(nomeUsuario)
				.setExpiration(new Date(System.currentTimeMillis() + JWTCredenciais.TEMPO_EXPIRACAO))
				.signWith(SignatureAlgorithm.HS512, JWTCredenciais.CHAVE).compact();
		
		response.addHeader(JWTCredenciais.HEADER_STRING, JWTCredenciais.TOKEN_PREFIXO + token);
	}

}

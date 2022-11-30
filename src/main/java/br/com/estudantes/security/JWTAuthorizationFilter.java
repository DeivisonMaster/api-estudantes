package br.com.estudantes.security;

import static br.com.estudantes.security.JWTConstants.*;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import br.com.estudantes.dominio.service.UsuarioService;
import io.jsonwebtoken.Jwts;

/**
 * @author Deivison
 * 
 * Autorização de recursos na API
 * */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter{
	private UsuarioService usuarioService;

	public JWTAuthorizationFilter(AuthenticationManager authManager, UsuarioService usuarioService) {
		super(authManager);
		this.usuarioService = usuarioService;
	}
	
	/**
	 * Valida se o header da requisição está bem formado
	 * */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(HEADER_STRING);
		if(header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken auth = getAuthToken(request);
		SecurityContextHolder.getContext().setAuthentication(auth);
		chain.doFilter(request, response);
	}
	
	public UsernamePasswordAuthenticationToken getAuthToken(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if(token == null) return null;
		String nomeUsuario = Jwts.parser().setSigningKey(SECRET)
				.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
				.getBody()
				.getSubject();
		UserDetails userDetails = usuarioService.loadUserByUsername(nomeUsuario);
		return nomeUsuario != null ? new UsernamePasswordAuthenticationToken(nomeUsuario, null, userDetails.getAuthorities()) : null;
	}
}

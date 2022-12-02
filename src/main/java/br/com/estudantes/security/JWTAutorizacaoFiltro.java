package br.com.estudantes.security;

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

import br.com.estudantes.dominio.service.CustomUserDetailsService;
import io.jsonwebtoken.Jwts;
import static br.com.estudantes.security.JWTCredenciais.*;

public class JWTAutorizacaoFiltro extends BasicAuthenticationFilter{
	private CustomUserDetailsService customUserService;

	public JWTAutorizacaoFiltro(AuthenticationManager authenticationManager, CustomUserDetailsService customUserService) {
		super(authenticationManager);
		this.customUserService = customUserService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(HEADER_STRING);
		if(null == header || !header.startsWith(TOKEN_PREFIXO)) {
			SecurityContextHolder.getContext().setAuthentication(null); // invalida autenticação
			chain.doFilter(request, response);
			return;
		}
		
		UsernamePasswordAuthenticationToken autenticacaoPorToken = getAutenticacaoPorToken(request);
		SecurityContextHolder.getContext().setAuthentication(autenticacaoPorToken);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAutenticacaoPorToken(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if(null == token) return null;
		
		String nomeUsuario = Jwts.parser()
				.setSigningKey(CHAVE)
				.parseClaimsJws(token.replace(TOKEN_PREFIXO, ""))
				.getBody()
				.getSubject();
		
		UserDetails userDetails = customUserService.loadUserByUsername(nomeUsuario);
		return nomeUsuario != null ? new UsernamePasswordAuthenticationToken(nomeUsuario, null, userDetails.getAuthorities()) : null;
	}
}

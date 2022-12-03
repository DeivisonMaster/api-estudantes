package br.com.estudantes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import br.com.estudantes.dominio.service.CustomUserDetailsService;
import static br.com.estudantes.security.JWTCredenciais.*;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	/**
	 * Configuração Basic Authentication
	 * */
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//			.authorizeRequests()
//			.antMatchers("/*/protegido/**").hasRole("USER")
//			.antMatchers("/*/admin/**").hasRole("ADMIN")
//			.anyRequest()
//			.authenticated()
//			.and()
//			.httpBasic()
//			.and()
//			.csrf()
//			.disable();
//	}
	
	/**
	 * Configuração JWT
	 * */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
			.and().csrf().disable()
			.authorizeRequests().antMatchers(HttpMethod.GET, SIGN_UP_URL).permitAll()
			.antMatchers("/*/protegido/**").hasRole("USER")
			.antMatchers("/*/admin/**").hasRole("ADMIN")
			.and()
			.addFilter(new JWTAutenticacaoFiltro(authenticationManager()))
			.addFilter(new JWTAutorizacaoFiltro(authenticationManager(), customUserDetailsService));
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	// Configuração Autenticação em memória
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder authBuilder) throws Exception {
//		authBuilder.inMemoryAuthentication()
//			.withUser("teste").password("123").roles("USER")
//			.and()
//			.withUser("admin").password("321").roles("USER", "ADMIN");
//	}
}

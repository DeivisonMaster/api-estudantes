package br.com.estudantes.security;

import static br.com.estudantes.security.JWTConstants.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.web.cors.CorsConfiguration;

import br.com.estudantes.dominio.service.UsuarioService;
import io.jsonwebtoken.SignatureAlgorithm;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UsuarioService service;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
			.and()
			.csrf()
			.disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, SIGN_UP_URL).permitAll()
			.antMatchers("/*/protegido/**").hasRole("USER")
		    .antMatchers("/*/admin/**").hasRole("ADMIN")
			.and()
			.addFilter(new JWTAuthFilter(authenticationManager()))
			.addFilter(new JWTAuthorizationFilter(authenticationManager(), service));
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}

// Basic Authentication
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//			.antMatchers("/*/protegido/**").hasRole("USER")
//		    .antMatchers("/*/admin/**").hasRole("ADMIN")
//			.and()
//			.httpBasic()
//			.and()
//			.csrf().disable();
//	}
	
	
// Dados em Memória	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
//		PasswordEncoderFactories.createDelegatingPasswordEncoder();
//		auth.inMemoryAuthentication()
//			.withUser("teste").password(encoder.encode("123")).roles("USER")
//			.and()
//			.withUser("admin").password(encoder.encode("123")).roles("USER", "ADMIN");
//	}
	
}

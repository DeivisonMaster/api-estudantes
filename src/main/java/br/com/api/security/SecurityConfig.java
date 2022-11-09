package br.com.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.api.service.UsuarioService;
import static br.com.api.security.JWTConstants.*;
import io.jsonwebtoken.SignatureAlgorithm;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UsuarioService service;
	
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
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
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
	
// Dados em Memória	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
//		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//		auth.inMemoryAuthentication()
//			.withUser("teste").password(encoder.encode("123")).roles("USER")
//			.and()
//			.withUser("admin").password(encoder.encode("admin")).roles("USER", "ADMIN");
//	}
	
}

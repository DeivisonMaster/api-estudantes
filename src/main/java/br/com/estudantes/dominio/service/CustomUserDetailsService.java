package br.com.estudantes.dominio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.estudantes.dominio.model.Usuario;
import br.com.estudantes.dominio.repository.UsuarioRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService{
	private static final String USUARIO_NAO_ENCONTRADO = "Usuario não encontrado";
	
	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String nomeUsuario) throws UsernameNotFoundException {
		Usuario usuario = Optional.ofNullable(repository.findByNomeUsuario(nomeUsuario))
			.orElseThrow(() -> new UsernameNotFoundException(USUARIO_NAO_ENCONTRADO));
		
		List<GrantedAuthority> listaUsuario = AuthorityUtils.createAuthorityList("ROLE_USER");
		List<GrantedAuthority> listaAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
		
		return new User(usuario.getNomeUsuario(), usuario.getSenha(), usuario.isAdmin() ? listaAdmin : listaUsuario);
	}

}

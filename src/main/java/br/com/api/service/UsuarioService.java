package br.com.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.api.model.Usuario;
import br.com.api.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{
	private static final String USUARIO_NAO_ENCONTRADO = "Usuario não encontrado";
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
		Usuario usuarioPesquisa = Optional.ofNullable(repository.findByUsuario(usuario))
				.orElseThrow(() -> new UsernameNotFoundException(USUARIO_NAO_ENCONTRADO));
		
		List<GrantedAuthority> listaAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
		List<GrantedAuthority> listaUsuario = AuthorityUtils.createAuthorityList("ROLE_USER");
		
		return new User(usuarioPesquisa.getNome(), usuarioPesquisa.getSenha(), usuarioPesquisa.isAdmin() ? listaAdmin : listaUsuario);
	}

}

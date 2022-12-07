package br.com.estudantes.dominio.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estudantes.dominio.model.Usuario;
import br.com.estudantes.dominio.repository.UsuarioRepository;
import br.com.estudantes.security.PasswordEncoder;

@Service
public class UsuarioService {
	private static final String SENHA = "123";
	
	@Autowired
	private UsuarioRepository repository;
	
	@Transactional
	public void geraUsuariosParaTesteBancoH2() {
		Usuario usuarioTeste = new Usuario("teste", PasswordEncoder.geraNovaSenhaCriptografada(SENHA), "teste", Boolean.FALSE.booleanValue());
		salvaUsuario(usuarioTeste);
		
		Usuario usuarioAdmin = new Usuario("admin", PasswordEncoder.geraNovaSenhaCriptografada(SENHA), "admin", Boolean.TRUE.booleanValue());
		salvaUsuario(usuarioAdmin);
	}

	private void salvaUsuario(Usuario usuario) {
		Optional<Usuario> usuarioOptional = Optional.ofNullable(repository.findByNomeUsuario(usuario.getNomeUsuario()));
		if(usuarioOptional.isPresent()) {
			repository.save(usuarioOptional.get());
		}
	}

}

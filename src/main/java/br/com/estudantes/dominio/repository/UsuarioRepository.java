package br.com.estudantes.dominio.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.estudantes.dominio.model.Usuario;

@Repository
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>{
	
	Usuario findByNomeUsuario(String usuario);

}

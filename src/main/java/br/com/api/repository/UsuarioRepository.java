package br.com.api.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.api.model.Usuario;

@Repository
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>{
	
	Usuario findByUsuario(String usuario);

}

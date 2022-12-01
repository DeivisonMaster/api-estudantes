package br.com.estudantes.dominio.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.estudantes.dominio.model.Estudante;

@Repository
public interface EstudantesRepository extends PagingAndSortingRepository<Estudante, Long>{

	Estudante findByNomeIgnoreCaseContaining(String nome);

}


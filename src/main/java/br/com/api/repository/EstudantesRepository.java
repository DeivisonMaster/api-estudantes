package br.com.api.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.api.model.Estudante;

@Repository
public interface EstudantesRepository extends PagingAndSortingRepository<Estudante, Long>{

	Estudante findByNomeIgnoreCaseContaining(String nome);

}

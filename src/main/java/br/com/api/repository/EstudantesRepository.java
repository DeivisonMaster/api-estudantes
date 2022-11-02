package br.com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.model.Estudante;

@Repository
public interface EstudantesRepository extends JpaRepository<Estudante, Long>{

	Estudante findByNomeIgnoreCaseContaining(String nome);

}

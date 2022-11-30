package br.com.estudantes.repository;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.estudantes.dominio.model.Estudante;
import br.com.estudantes.dominio.repository.EstudantesRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EstudanteRepositoryTest {
	
	@Autowired
	private EstudantesRepository repository;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void deveCriarNovoEstudante() {
		Estudante estudante = new Estudante("Deivison Matos", "deivison@email.com");
		repository.save(estudante);
		
		Assertions.assertThat(estudante.getId()).isNotNull();
		Assertions.assertThat(estudante.getNome()).isEqualTo("Deivison Matos");
		Assertions.assertThat(estudante.getEmail()).isEqualTo("deivison@email.com");
	}
}

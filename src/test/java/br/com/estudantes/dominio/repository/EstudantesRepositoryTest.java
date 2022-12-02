package br.com.estudantes.dominio.repository;



import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.estudantes.dominio.model.Estudante;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EstudantesRepositoryTest {
	
	@Autowired
	private EstudantesRepository repository;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void deveCriaUmNovoEstudanteNoBanco() {
		Estudante estudante = new Estudante("Deivison", "deivison@email.com");
		
		Estudante estudanteSalvo = repository.save(estudante);
		
		Assertions.assertThat(estudanteSalvo).isNotNull();
		Assertions.assertThat(estudanteSalvo.getNome()).isEqualTo("Deivison");
		Assertions.assertThat(estudanteSalvo.getEmail()).isEqualTo("deivison@email.com");
	}
	
	@Test
	public void deveExcluirUmEstudanteNoBanco() {
		Estudante estudante = new Estudante("Deivison", "deivison@email.com");
		Estudante estudanteSalvo = repository.save(estudante);
		repository.delete(estudanteSalvo);
		
		Assertions.assertThat(repository.findOne(estudanteSalvo.getId())).isNull();
	}
	
	@Test
	public void deveAtualizarUmEstudanteNoBanco() {
		Estudante estudante = new Estudante("Deivison", "deivison@email.com");
		Estudante estudanteSalvo = repository.save(estudante);
		
		estudanteSalvo.setNome("Deivison 2");
		estudanteSalvo.setEmail("deivison2@email.com");
		Estudante estudanteAtualizado = repository.save(estudante);
		
		Assertions.assertThat(estudanteAtualizado.getNome()).isEqualTo("Deivison 2");
		Assertions.assertThat(estudanteAtualizado.getEmail()).isEqualTo("deivison2@email.com");
	}
	
	@Test
	public void deveLancarConstraintViolationExceptionAoSalvarUmEstudante() {
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("O campo nome é obrigatório");
		repository.save(new Estudante());
	}

}

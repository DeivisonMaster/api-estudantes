package br.com.estudantes.api.controller;


import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.estudantes.dominio.model.Estudante;
import br.com.estudantes.dominio.repository.EstudantesRepository;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EstudanteControllerBasicAuthTest {
	
	@Autowired
	private TestRestTemplate restTemplateTest;
	
	@LocalServerPort
	private int porta;
	
	@MockBean
	private EstudantesRepository estudanteRepositorio;
	
	@Autowired
	private MockMvc mockMvc;
	
	@TestConfiguration
	static class Config{
		
		@Bean
		public RestTemplateBuilder restTemplateBuilder() {
			return new RestTemplateBuilder().basicAuthorization("admin", "123");
		}
	}

	@Test
	public void deveRetornarStatusCode401NaConsultaPorEstudantesComAutenticacaoPorUsuarioESenhaIncorretos() {
		restTemplateTest = restTemplateTest.withBasicAuth("1", "1");
		ResponseEntity<String> response = restTemplateTest.getForEntity("/v1/protegido/api-estudante", String.class);
		
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(401);
	}
	
	@Test
	public void deveRetornarStatusCode401NaConsultaPorIdDeEstudantesComAutenticacaoPorUsuarioESenhaIncorretos() {
		restTemplateTest = restTemplateTest.withBasicAuth("1", "1");
		ResponseEntity<String> response = restTemplateTest.getForEntity("/v1/protegido/api-estudantes/{id}", String.class, 1);
		
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(401);
	}
	
	@Test
	public void deveRetornarStatusCode200NaConsultaPorEstudantesComAutenticacaoPorUsuarioESenhaCorretos() {
		Iterable<Estudante> listaEstudantes = Arrays.asList(new Estudante(1L, "Katia", "katia@email.com"), new Estudante(2L, "Maria", "maria@email.com"));
		BDDMockito.when(estudanteRepositorio.findAll()).thenReturn(listaEstudantes);
		
		ResponseEntity<String> response = restTemplateTest.getForEntity("/v1/protegido/api-estudantes/listarNaoPaginado", String.class);
		
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void deveRetornarStatusCode200NaConsultaPorIdDeEstudanteComAutenticacaoPorUsuarioESenhaCorretos() {
		Estudante estudante = new Estudante(1L, "Katia", "katia@email.com");
		BDDMockito.when(estudanteRepositorio.findOne(1L)).thenReturn(estudante);
		
		ResponseEntity<Estudante> response = restTemplateTest.getForEntity("/v1/protegido/api-estudantes/{id}", Estudante.class, 1L);
		
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
		Assertions.assertThat(response.getBody()).isNotNull();
		Assertions.assertThat(response.getBody().getId()).isEqualTo(estudante.getId());
	}
	
	@Test
	public void deveRetornarStatusCode404NaConsultaPorEstudantesComAutenticacaoPorUsuarioESenhaCorretos() {
		ResponseEntity<Estudante> response = restTemplateTest.getForEntity("/v1/protegido/api-estudantes/{id}", Estudante.class, -1L);
		
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
	}

}

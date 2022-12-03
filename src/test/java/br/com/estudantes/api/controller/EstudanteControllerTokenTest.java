package br.com.estudantes.api.controller;


import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.estudantes.dominio.model.Estudante;
import br.com.estudantes.dominio.repository.EstudantesRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EstudanteControllerTokenTest {
	
	@Autowired
	private TestRestTemplate restTemplateTest;
	
	@LocalServerPort
	private int porta;
	
	@MockBean
	private EstudantesRepository estudanteRepositorio;
	
	private HttpEntity<Void> headerUsuarioComum;
	private HttpEntity<Void> headerUrlAdmin;
	private HttpEntity<Void> headerErro;
	
	@Before
	public void configParaEndpointsParaUsuarioTeste() {
		String usuarioTeste = "{\"usuario\": \"teste\", \"senha\": \"123\"}";
		HttpHeaders headers = restTemplateTest.postForEntity("/login", usuarioTeste, String.class).getHeaders();
		this.headerUsuarioComum = new HttpEntity<>(headers);
	}
	
	@Before
	public void configParaEndpointsParaUsuarioAdmin() {
		String usuarioAdmin = "{\"usuario\": \"admin\", \"senha\": \"123\"}";
		HttpHeaders httpHeaders = restTemplateTest.postForEntity("/login", usuarioAdmin, String.class).getHeaders();
		this.headerUrlAdmin = new HttpEntity<>(httpHeaders);
	}
	
	@Before
	public void configParaEndpointsComHeaderIncorreto() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "1111");
		this.headerErro = new HttpEntity<>(httpHeaders);
	}
	
	@Before
	public void setup() {
		Estudante estudante = new Estudante(1L, "Katia", "katia@email.com");
		BDDMockito.when(estudanteRepositorio.findOne(1L)).thenReturn(estudante);
		
		BDDMockito.doNothing().when(estudanteRepositorio).delete(estudante);
	}
	
	@Test
	public void deveRetornarStatusCode403NaConsultaPorEstudantesComTokenIncorreto() {
		ResponseEntity<String> response = restTemplateTest.exchange("/v1/protegido/api-estudante", HttpMethod.GET, headerErro, String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(403);
	}
	
	@Test
	public void deveRetornarStatusCode403NaConsultaPorIdDeEstudantesComTokenIncorreto() {
		ResponseEntity<String> response = restTemplateTest.exchange("/v1/protegido/api-estudantes/{id}", HttpMethod.GET, headerErro, String.class, 1L);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(403);
	}
	
	@Test
	public void deveRetornarStatusCode200NaConsultaPorEstudantesComTokenCorreto() {
		Iterable<Estudante> listaEstudantes = Arrays.asList(new Estudante(1L, "Katia", "katia@email.com"), new Estudante(2L, "Maria", "maria@email.com"));
		BDDMockito.when(estudanteRepositorio.findAll()).thenReturn(listaEstudantes);
		
		ResponseEntity<Estudante[]> response = restTemplateTest
				.exchange("/v1/protegido/api-estudantes/listarNaoPaginado", HttpMethod.GET, headerUsuarioComum, Estudante[].class);
		
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void deveRetornarStatusCode200NaConsultaPorIdDeEstudanteComTokenCorreto() {
		ResponseEntity<Estudante> response = restTemplateTest.exchange("/v1/protegido/api-estudantes/{id}", HttpMethod.GET, headerUsuarioComum, Estudante.class, 1L);
		
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
		Assertions.assertThat(response.getBody()).isNotNull();
	}
	
	@Test
	public void deveRetornarStatusCode404NaConsultaPorEstudanteComTokenCorreto() {
		ResponseEntity<Estudante> response = restTemplateTest.exchange("/v1/protegido/api-estudantes/{id}", HttpMethod.GET, headerUsuarioComum, Estudante.class, -1L);
		
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
	}
	
	@Test
	public void deveRetornarStatusCode204NaExclusaoDeEstudanteComTokenCorreto() {
		ResponseEntity<String> response = restTemplateTest
				.exchange("/v1/admin/api-estudantes/{id}", HttpMethod.DELETE, headerUrlAdmin, String.class, 1L);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(204);
	}
	
	@Test
	public void deveRetornarStatusCode404NaExclusaoDeEstudanteQueNaoExisteComTokenCorreto() {
		ResponseEntity<String> response = restTemplateTest
				.exchange("/v1/admin/api-estudantes/{id}", HttpMethod.DELETE, headerUrlAdmin, String.class, -1L);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
	}

}

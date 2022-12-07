package br.com.estudantes.api.controller;


import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	private HttpEntity<Void> headerUsuarioComum;
	private HttpEntity<Void> headerUrlAdmin;
	private HttpEntity<Void> headerErro;
	
	@Before
	public void configParaEndpointsParaUsuarioTeste() {
		String usuarioTeste = "{\"nomeUsuario\": \"teste\", \"senha\": \"123\"}";
		HttpHeaders headers = restTemplateTest.postForEntity("/login", usuarioTeste, String.class).getHeaders();
		this.headerUsuarioComum = new HttpEntity<>(headers);
	}
	
	@Before
	public void configParaEndpointsParaUsuarioAdmin() {
		String usuarioAdmin = "{\"nomeUsuario\": \"admin\", \"senha\": \"123\"}";
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
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
		Estudante estudante = new Estudante(1L, "Katia", "katia@email.com");
		BDDMockito.when(estudanteRepositorio.findOne(1L)).thenReturn(estudante);
		
		BDDMockito.when(estudanteRepositorio.save(estudante)).thenReturn(estudante);
		
		BDDMockito.doNothing().when(estudanteRepositorio).delete(estudante);
	}
	
	@Test
	public void deveRetornarStatusCode403NaConsultaPorEstudantesComTokenInvalido() {
		ResponseEntity<String> response = restTemplateTest.exchange("/v1/protegido/api-estudante", HttpMethod.GET, headerErro, String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(403);
	}
	
	@Test
	public void deveRetornarStatusCode403NaConsultaPorIdDeEstudantesComTokenInvalido() {
		ResponseEntity<String> response = restTemplateTest.exchange("/v1/protegido/api-estudantes/{id}", HttpMethod.GET, headerErro, String.class, 1L);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(403);
	}
	
	@Test
	public void deveRetornarStatusCode200NaConsultaPorEstudantesComTokenValido() {
		Iterable<Estudante> listaEstudantes = Arrays.asList(new Estudante(1L, "Katia", "katia@email.com"), new Estudante(2L, "Maria", "maria@email.com"));
		BDDMockito.when(estudanteRepositorio.findAll()).thenReturn(listaEstudantes);
		
		ResponseEntity<Estudante[]> response = restTemplateTest
				.exchange("/v1/protegido/api-estudantes/listarNaoPaginado", HttpMethod.GET, headerUsuarioComum, Estudante[].class);
		
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void deveRetornarStatusCode200NaConsultaPorIdDeEstudanteComTokenValido() {
		ResponseEntity<Estudante> response = restTemplateTest.exchange("/v1/protegido/api-estudantes/{id}", HttpMethod.GET, headerUsuarioComum, Estudante.class, 1L);
		
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
		Assertions.assertThat(response.getBody()).isNotNull();
	}
	
	@Test
	public void deveRetornarStatusCode404NaConsultaPorEstudanteComTokenValido() {
		ResponseEntity<Estudante> response = restTemplateTest
				.exchange("/v1/protegido/api-estudantes/{id}", HttpMethod.GET, headerUsuarioComum, Estudante.class, -1L);
		
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
	}
	
	@Test
	public void deveRetornarStatusCode204NaExclusaoDeEstudanteComTokenValido() {
		ResponseEntity<String> response = restTemplateTest
				.exchange("/v1/admin/api-estudantes/{id}", HttpMethod.DELETE, headerUrlAdmin, String.class, 1L);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(204);
	}
	
	@Test
	public void deveRetornarStatusCode404NaExclusaoDeEstudanteInexistenteComRegraUsuarioAdminEComTokenValido() throws Exception {
		String token = headerUrlAdmin.getHeaders().get("Authorization").get(0);
		mockMvc
			.perform(MockMvcRequestBuilders.delete("/v1/admin/api-estudantes/{id}", -1L).header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status()
					.isNotFound());
	}
	
	@Ignore
	@Test
	public void deveRetornarStatusCode403NaExclusaoDeEstudanteExistenteComRegraDeUsuarioEComTokenValido() throws Exception {
		String token = headerUsuarioComum.getHeaders().get("Authorization").get(0);
		
		BDDMockito.doNothing().when(estudanteRepositorio).delete(1L);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/v1/admin/api-estudantes/{id}", 1L).header("Authorization", token))
			.andExpect(MockMvcResultMatchers.status()
					.isForbidden());
	}
	
	@Test
	public void deveRetonarStatusCode400BadRequestNaCriacaoDeNovoEstudanteComNomeNuloEComTokenValido() {
		Estudante estudante = new Estudante(1L, null, "katia@email.com");
		ResponseEntity<String> response = restTemplateTest
				.exchange("/v1/admin/api-estudantes/", HttpMethod.POST, new HttpEntity<>(estudante, headerUrlAdmin.getHeaders()), String.class);
		
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(400);
		Assertions.assertThat(response.getBody()).contains("msgCampo", "O campo nome é obrigatório");
	}
	
	@Test
	public void deveRetonarStatusCode201NaCriacaoDeNovoEstudanteComTokenValido() {
		Estudante estudante = new Estudante(1L, "katia", "katia@email.com");
		ResponseEntity<Estudante> response = restTemplateTest
				.exchange("/v1/admin/api-estudantes/", HttpMethod.POST, new HttpEntity<>(estudante, headerUrlAdmin.getHeaders()), Estudante.class);
		
		Assertions.assertThat(response.getBody()).isNotNull();
		Assertions.assertThat(response.getBody().getId()).isNotNull();
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(201);
	}
	
}

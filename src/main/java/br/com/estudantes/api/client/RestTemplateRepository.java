package br.com.estudantes.api.client;

import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.estudantes.api.excecoes.RestErros;
import br.com.estudantes.dominio.model.Estudante;
import br.com.estudantes.dominio.model.RestTemplateResponsePageable;

public class RestTemplateRepository {
	private String senha = "123";
	private String usuario = "admin";
	private String url = "http://localhost:8080/v1/protegido/api-estudantes";
	private String urlAdmin = "http://localhost:8080/v1/admin/api-estudantes";
	private RestTemplate restTemplate = new RestTemplateBuilder().rootUri(url).basicAuthorization(usuario, senha).errorHandler(new RestErros()).build();
	private RestTemplate restTemplateAdmin = new RestTemplateBuilder().rootUri(urlAdmin).basicAuthorization(usuario, senha).errorHandler(new RestErros()).build();
	
	public List<Estudante> listar(){
		ResponseEntity<RestTemplateResponsePageable<Estudante>> exchange = restTemplate
				.exchange("/?sort=nome,desc", HttpMethod.GET, null, new ParameterizedTypeReference<RestTemplateResponsePageable<Estudante>>() {});
		return exchange.getBody().getContent();
	};
	
	public Estudante buscarPorId(long id) {
		return restTemplate.getForObject("/{id}", Estudante.class, id);
	}
	
	public Estudante salvar(Estudante estudante) {
		return restTemplateAdmin.exchange("/", HttpMethod.POST, new HttpEntity<>(estudante, getJsonHeader()), Estudante.class).getBody();
	}
	
	public void atualiza(Estudante estudante) {
		restTemplateAdmin.put("/", estudante);
	}
	
	public void excluir(long id) {
		restTemplateAdmin.delete("/{id}", id);
	}
	
	private static HttpHeaders getJsonHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}

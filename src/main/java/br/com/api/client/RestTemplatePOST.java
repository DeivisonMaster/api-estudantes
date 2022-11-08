package br.com.api.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import br.com.api.model.Estudante;
import br.com.api.model.RestTemplateResponsePageable;

public class RestTemplatePOST {
	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplateBuilder()
				.rootUri("http://localhost:8080/v1/admin/api-estudantes")
				.basicAuthorization("admin", "123")
				.build();
			
		Estudante estudante = new Estudante();
		estudante.setEmail("aluno@email.com");
		estudante.setNome("Aluno A");
	    ResponseEntity<Estudante> exchangePost = restTemplate.exchange("/", HttpMethod.POST, new HttpEntity<>(estudante, getJsonHeader()), Estudante.class);
	    System.out.println(exchangePost);
}

	private static HttpHeaders getJsonHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}

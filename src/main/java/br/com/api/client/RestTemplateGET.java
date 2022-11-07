package br.com.api.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.api.model.Estudante;

public class RestTemplateGET {
	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplateBuilder()
				.rootUri("http://localhost:8080/v1/protegido/api-estudantes")
				.basicAuthorization("teste", "123")
				.build();
		
//		obtemObjetoSimples(restTemplate);
		obtemObjetoComposto(restTemplate);
//		obtemArrayObjetos(restTemplate);
//		obtemListaObjetos(restTemplate);
	}

	private static void obtemListaObjetos(RestTemplate restTemplate) {
		ResponseEntity<List<Estudante>> estudantes = restTemplate.exchange("/", HttpMethod.GET, null, new ParameterizedTypeReference<List<Estudante>>() {});
		System.out.println(estudantes.getBody());
	}

	private static void obtemArrayObjetos(RestTemplate restTemplate) {
		Estudante[] arrayEstudante = restTemplate.getForObject("/", Estudante[].class);
		System.out.println(Arrays.toString(arrayEstudante));
	}

	private static void obtemObjetoComposto(RestTemplate restTemplate) {
		ResponseEntity<Estudante> entity = restTemplate.getForEntity("/{id}", Estudante.class,  1);
		System.out.println(entity.getBody());
	}

	private static void obtemObjetoSimples(RestTemplate restTemplate) {
		Estudante estudante = restTemplate.getForObject("/{id}", Estudante.class, 1);
		System.out.println(estudante);
	}
}

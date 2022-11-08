package br.com.api.excecoes;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.DefaultResponseErrorHandler;

@ControllerAdvice
public class RestErros extends DefaultResponseErrorHandler{
	
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		System.out.println("Erro ocorreu");
		return super.hasError(response);
	}
	
	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		System.out.println("Erro ocorreu " + response.getStatusCode());
		System.out.println("Erro ocorreu " + IOUtils.toString(response.getBody(), "UTF-8"));
	}
}

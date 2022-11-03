package br.com.api.excecoes;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<?> handleResourceNotFoundException(RecursoNaoEncontradoException rneException){
		RecursoNaoEncontradoDetalhes rneDetalhes = RecursoNaoEncontradoDetalhes.Builder
			.newBuilder()
			.timestamp(new Date().getTime())
			.status(HttpStatus.NOT_FOUND.value())
			.titulo("Recurso Não Encontrado")
			.detalhe(rneException.getMessage())
			.msgDesenvolvedor(rneException.getClass().getName())
			.build();
		
		return new ResponseEntity<>(rneDetalhes, HttpStatus.NOT_FOUND);
	}
}

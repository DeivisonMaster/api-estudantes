package br.com.estudantes.api.excecoes;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.estudantes.api.excecoes.RecursoNaoEncontradoDetalhes.Builder;

@ControllerAdvice
public class RestExceptionHandler {
	
	/**
	 * @author Deivison
	 * Exceção que trata de recurso buscado não encontrado pela aplicação
	 * @exception RecursoNaoEncontradoDetalhes
	 * @return 
	 * */
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
	
	@ExceptionHandler(PropertyReferenceException.class)
	public ResponseEntity<?> handlePropertyNotFoundException(PropertyReferenceException ex){
		RecursoNaoEncontradoDetalhes rneDetalhes = RecursoNaoEncontradoDetalhes.Builder
			.newBuilder()
			.timestamp(new Date().getTime())
			.status(HttpStatus.NOT_FOUND.value())
			.titulo("Recurso Não Encontrado")
			.detalhe(ex.getMessage())
			.msgDesenvolvedor(ex.getClass().getName())
			.build();
		
		return new ResponseEntity<>(rneDetalhes, HttpStatus.NOT_FOUND);
	}
	
	/**
	 * @author Deivison
	 * Exceção que trata de campos obrigatórios validados não informados na requisição 
	 * @exception MethodArgumentNotValidException
	 * @return 
	 * */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
		String campoErro = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream().map(FieldError::getField).collect(Collectors.joining(","));
		String campoMsgErro = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
		
		EntidadeCamposErroDetalhe camposErroDetalhe = EntidadeCamposErroDetalhe.Builder
			.newBuilder()
			.timestamp(new Date().getTime())
			.status(HttpStatus.BAD_REQUEST.value())
			.titulo("Favor, Informar Campos Obrigatórios")
			.detalhe("Alguns campos obrigatórios não foram informados na requisição")
			.msgDesenvolvedor(methodArgumentNotValidException.getClass().getName())
			.campo(campoErro)
			.msgCampo(campoMsgErro)
			.build();
		
		return new ResponseEntity<>(camposErroDetalhe, HttpStatus.BAD_REQUEST);
	}
}

package br.com.api.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.model.Estudante;
import br.com.api.model.EstudanteNaoEncontrado;
import br.com.api.repository.EstudantesRepository;

@RestController
@RequestMapping("/api-estudantes")
public class EstudanteController {
	private static final String MSG_NÃO_ENCONTRADO = "Estudante não Encontrado!";
	
	@Autowired
	private EstudantesRepository repository;
	
	@GetMapping
	public ResponseEntity<?> listar(){
		return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obtemPorId(@PathVariable("id") long id){
		Optional<Estudante> estudanteOptional = repository.findById(id);
		if(!estudanteOptional.isPresent()) {
			return new ResponseEntity<>(new EstudanteNaoEncontrado(MSG_NÃO_ENCONTRADO), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(estudanteOptional.get(), HttpStatus.OK);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estudante salvar(@RequestBody Estudante estudante) {
		return repository.save(estudante);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable("id") long id){
		Optional<Estudante> estudanteOptional = repository.findById(id);
		if(!estudanteOptional.isPresent()) {
			return new ResponseEntity<>(new EstudanteNaoEncontrado(MSG_NÃO_ENCONTRADO), HttpStatus.NOT_FOUND);
		}
		repository.delete(estudanteOptional.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualiza(@PathVariable("id") long id, @RequestBody Estudante estudante){
		Optional<Estudante> estudanteOptional = repository.findById(id);
		if(!estudanteOptional.isPresent()) {
			return new ResponseEntity<>(new EstudanteNaoEncontrado(MSG_NÃO_ENCONTRADO), HttpStatus.NOT_FOUND);
		}
		estudante.setId(id);
		repository.save(estudante);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

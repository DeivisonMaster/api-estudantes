package br.com.api.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.excecoes.RecursoNaoEncontradoException;
import br.com.api.model.Estudante;
import br.com.api.repository.EstudantesRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1")
public class EstudanteController {
	private static final String MSG_NAO_ENCONTRADO = "Estudante não Encontrado!";
	
	@Autowired
	private EstudantesRepository repository;
	
	@GetMapping(path = "protegido/api-estudantes")
	@ApiOperation(value = "Retorna a listagem de todos os estudantes cadastrados", response = Estudante[].class)
	public ResponseEntity<?> listar(Pageable paginacao){
		return new ResponseEntity<>(repository.findAll(paginacao), HttpStatus.OK);
	}
	
	@GetMapping(path = "protegido/api-estudantes/buscaPorNome/{nome}")
	public ResponseEntity<Estudante> buscaPorNome(@PathVariable String nome){
		return new ResponseEntity<Estudante>(repository.findByNomeIgnoreCaseContaining(nome), HttpStatus.OK);
	}
	
	@GetMapping(path = "protegido/api-estudantes/{id}")
	public ResponseEntity<?> obtemPorId(@PathVariable("id") long id, @AuthenticationPrincipal UserDetails userDetails){
		System.out.println(userDetails);
		Estudante estudante = repository.findOne(id);
		isEstudanteExiste(estudante);
		return new ResponseEntity<>(estudante, HttpStatus.OK);
	}
	
	@PostMapping(path = "admin/api-estudantes")
	@ResponseStatus(HttpStatus.CREATED)
	public Estudante salvar(@Valid @RequestBody Estudante estudante) {
		return repository.save(estudante);
	}
	
	@DeleteMapping(path = "admin/api-estudantes/{id}")
	public ResponseEntity<?> excluir(@PathVariable("id") long id){
		Estudante estudante = repository.findOne(id);
		isEstudanteExiste(estudante);
		repository.delete(estudante);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(path = "admin/api-estudantes")
	public ResponseEntity<?> atualiza(@Valid @RequestBody Estudante estudante){
		Estudante estudantePesquisa = repository.findOne(estudante.getId());
		isEstudanteExiste(estudantePesquisa);
		repository.save(estudante);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private void isEstudanteExiste(Estudante estudante) {
		if(estudante == null) {
			throw new RecursoNaoEncontradoException(MSG_NAO_ENCONTRADO);
		}
	}
}

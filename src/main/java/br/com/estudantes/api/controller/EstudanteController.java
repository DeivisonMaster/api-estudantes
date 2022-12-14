package br.com.estudantes.api.controller;


import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.estudantes.api.excecoes.RecursoNaoEncontradoException;
import br.com.estudantes.dominio.model.Estudante;
import br.com.estudantes.dominio.repository.EstudantesRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1")
public class EstudanteController {
	private static final Logger LOGGER = Logger.getLogger(EstudanteController.class);
	private static final String MSG_NAO_ENCONTRADO = "Estudante não Encontrado!";
	
	@Autowired
	private EstudantesRepository repository;
	
	@GetMapping(path = "protegido/api-estudantes")
	@ApiOperation(value = "Lista todos os Estudantes com Paginação")
	public ResponseEntity<?> listar(Pageable paginacao){
		return new ResponseEntity<>(repository.findAll(paginacao), HttpStatus.OK);
	}
	
	@GetMapping(path = "protegido/api-estudantes/listarNaoPaginado")
	@ApiOperation(value = "Lista todos os Estudantes sem Paginação")
	public ResponseEntity<?> listar(){
		return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(path = "protegido/api-estudantes/buscaPorNome/{nome}")
	@ApiOperation(value = "Consulta Estudante pelo Nome")
	public ResponseEntity<Estudante> buscaPorNome(@PathVariable String nome){
		return new ResponseEntity<Estudante>(repository.findByNomeIgnoreCaseContaining(nome), HttpStatus.OK);
	}
	
	@GetMapping(path = "protegido/api-estudantes/{id}")
	@ApiOperation(value = "Consulta Estudante por Id")
	public ResponseEntity<?> obtemPorId(@PathVariable("id") long id, Authentication auth){
		LOGGER.info(auth);
		
		Estudante estudante = repository.findOne(id);
		isEstudanteExiste(estudante);
		return new ResponseEntity<>(estudante, HttpStatus.OK);
	}
	
	@PostMapping(path = "admin/api-estudantes")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Inclui novo Estudante")
	public Estudante salvar(@Valid @RequestBody Estudante estudante) {
		return repository.save(estudante);
	}
	
	@DeleteMapping(path = "admin/api-estudantes/{id}")
	@ApiOperation(value = "Exclui um Estudante existente")
	public ResponseEntity<?> excluir(@PathVariable("id") long id){
		Estudante estudante = repository.findOne(id);
		isEstudanteExiste(estudante);
		repository.delete(estudante);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(path = "admin/api-estudantes")
	@ApiOperation(value = "Atualiza um Estudante existente")
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

package br.com.estudantes.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estudantes.dominio.service.UsuarioService;

@RestController
@RequestMapping("usuario")
public class UsuarioController {
	@Autowired
	private UsuarioService service;
	
	@EventListener(ApplicationReadyEvent.class)
	@GetMapping("cria-usuarios")
	public ResponseEntity<Void> insereUsuarioBanco(){
		service.geraUsuariosParaTesteBancoH2();
		return ResponseEntity.ok().build();
	}
}

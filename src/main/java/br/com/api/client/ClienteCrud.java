package br.com.api.client;

import br.com.api.model.Estudante;

public class ClienteCrud {
	public static void main(String[] args) {
		RestTemplateRepository repository = new RestTemplateRepository();
		
//		repository.atualiza(estudantePesquisa);
		
//		repository.excluir(4L);
		
		Estudante estudantePesquisa = repository.buscarPorId(122l);
		
//		repository.listar().forEach(estudante -> System.out.println(estudante));
		
	}
}

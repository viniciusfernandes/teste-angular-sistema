package br.com.teste.angular.client.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.teste.angular.client.entity.Pessoa;
import br.com.teste.angular.client.service.PessoaService;

@CrossOrigin
@RestController
@RequestMapping("client/pessoa")
public class PessoaController {
	private PessoaService pessoaService;

	@Autowired
	public PessoaController(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}

	@GetMapping(value = "/listagem")
	public List<Pessoa> pesquisarPessoa() {
		return pessoaService.pesquisarPessoa();
	}
}

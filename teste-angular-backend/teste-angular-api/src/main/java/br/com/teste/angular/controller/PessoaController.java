package br.com.teste.angular.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.teste.angular.model.Pessoa;
import br.com.teste.angular.model.PessoaService;
import br.com.teste.angular.service.impl.BusinessException;

@CrossOrigin
@RestController
@RequestMapping("pessoa")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;
	private static final Logger logger = Logger.getLogger(PessoaController.class.getName());

	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> pesquisarPessoaById(@PathVariable String id) {
		return new ResponseEntity<>(pessoaService.findPessoaById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> inserirPessoa(@RequestBody Pessoa pessoa) {
		try {
			return new ResponseEntity<>(new ResponseJson(pessoaService.inserirPessoa(pessoa)), HttpStatus.OK);
		} catch (BusinessException e) {
			logger.log(Level.WARNING, "Regra de negocio: falha na inclusao de pessoa");
			return new ResponseEntity<>(e.getListaMessagem(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/listagem")
	public List<Pessoa> pesquisarPessoa() {
		return pessoaService.pesquisarPessoa();
	}

	@GetMapping("/{nome}/listagem")
	public ResponseEntity<List<Pessoa>> pesquisarPessoaByNome(@PathVariable String nome) {
		return new ResponseEntity<>(pessoaService.pesquisarPessoaByNome(nome), HttpStatus.OK);
	}
}

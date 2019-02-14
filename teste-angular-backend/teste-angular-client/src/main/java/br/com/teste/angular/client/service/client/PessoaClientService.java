package br.com.teste.angular.client.service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.teste.angular.client.entity.Pessoa;

@FeignClient(url = "http://localhost:8081/pessoa", name = "PessoaClientService")
public interface PessoaClientService {

	@GetMapping(value = "/listagem")
	List<Pessoa> pesquisarPessoa();

}

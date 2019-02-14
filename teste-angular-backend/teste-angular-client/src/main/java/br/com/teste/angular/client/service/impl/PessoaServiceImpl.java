package br.com.teste.angular.client.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.angular.client.entity.Pessoa;
import br.com.teste.angular.client.service.PessoaService;
import br.com.teste.angular.client.service.client.PessoaClientService;

@Service
public class PessoaServiceImpl implements PessoaService {
	@Autowired
	private PessoaClientService clientService;

	@Override
	public List<Pessoa> pesquisarPessoa() {
		return clientService.pesquisarPessoa();
	}
}

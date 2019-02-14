package br.com.teste.angular.model;

import java.util.List;

import br.com.teste.angular.service.impl.BusinessException;

public interface PessoaService {

	Pessoa findPessoaById(String id);

	String inserirPessoa(Pessoa pessoa) throws BusinessException;

	List<Pessoa> pesquisarPessoa();

	List<Pessoa> pesquisarPessoaByNome(String nome);

}

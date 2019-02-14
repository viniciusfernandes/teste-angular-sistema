package br.com.teste.angular.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.angular.controller.PessoaDAO;
import br.com.teste.angular.model.Pessoa;
import br.com.teste.angular.model.PessoaService;

@Service
public class PessoaServiceImpl implements PessoaService {
	@Autowired
	private PessoaDAO pessoaDAO;

	@Override
	public Pessoa findPessoaById(String id) {
		return pessoaDAO.findPessoaById(id);
	}

	@Override
	public List<Pessoa> pesquisarPessoaByNome(String nome) {
		return pessoaDAO.pesquisarPessoaByNome(nome);

	}

	@Override
	public String inserirPessoa(Pessoa pessoa) throws BusinessException {
		BusinessException be = new BusinessException();
		if (pessoa == null) {
			be.addMensagem("A pessoa nao pode ser nula.");
		}
		if (pessoa.getNome() == null || pessoa.getNome().trim().length() <= 0) {
			be.addMensagem("O nome eh um dado obrigatorio.");

		}
		if (pessoa.getNome() != null
				&& (pessoa.getNome().trim().length() <= 1 || pessoa.getNome().trim().length() >= 50)) {
			be.addMensagem("O nome da pessoa deve estar entre 2 e 50 caracteres.");

		}
		if (pessoa.getIdade() < 1 || pessoa.getIdade() > 120) {
			be.addMensagem("A idade da pessoa deve estar no intervalo de 1 a 120.");

		}

		be.verifyThrowing();
		return pessoaDAO.inserirPessoa(pessoa);
	}

	@Override
	public List<Pessoa> pesquisarPessoa() {
		// return pessoaDAO.pesquisarPessoa();
		List<Pessoa> l = new ArrayList<>();
		l.add(new Pessoa("vinicius", 43, "3"));
		l.add(new Pessoa("carlos", 22, "33"));
		l.add(new Pessoa("marcio", 11, "4"));
		l.add(new Pessoa("beto", 66, "43"));
		return l;
	}

}

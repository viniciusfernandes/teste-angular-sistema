package br.com.teste.angular.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import br.com.teste.angular.model.Pessoa;

@Repository
public class PessoaDAO {
	@Autowired
	private MongoTemplate mongoTemplate;

	public Pessoa findPessoaById(String id) {
		return mongoTemplate.findById(id, Pessoa.class);
	}

	public String inserirPessoa(Pessoa pessoa) {
		if (pessoa.getId() == null) {
			mongoTemplate.insert(pessoa);
		} else {

			Query q = new Query(Criteria.where("id").is(pessoa.getId()));
			Update up = new Update();
			up.set("nome", pessoa.getNome());
			up.set("idade", pessoa.getIdade());
			mongoTemplate.updateFirst(q, up, Pessoa.class);
		}
		return "pessoa_id_teste";
	}

	public List<Pessoa> pesquisarPessoa() {
		Query q = new Query();
		q.with(new Sort(Sort.Direction.ASC, "nome"));
		return mongoTemplate.find(q, Pessoa.class);
	}

	public List<Pessoa> pesquisarPessoaByNome(String nome) {
		Query q = new Query();
		if (nome != null && (nome = nome.trim()).length() > 0) {
			q.addCriteria(Criteria.where("nome").regex("^" + nome, "i"));
		}
		q.with(new Sort(Sort.Direction.ASC, "nome"));

		return mongoTemplate.find(q, Pessoa.class);
	}
}

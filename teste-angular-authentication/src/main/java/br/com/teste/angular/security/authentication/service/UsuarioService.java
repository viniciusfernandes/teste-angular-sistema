package br.com.teste.angular.security.authentication.service;

import br.com.teste.angular.security.authentication.entity.Usuario;

public interface UsuarioService {

	Usuario buscarPorEmail(String email);

	Long inserir(Usuario usuario);

	void deleteAll();

}

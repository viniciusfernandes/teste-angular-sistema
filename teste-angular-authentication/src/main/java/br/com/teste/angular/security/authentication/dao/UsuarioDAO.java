package br.com.teste.angular.security.authentication.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.teste.angular.security.authentication.entity.Usuario;

@Transactional(readOnly = true)
public interface UsuarioDAO extends JpaRepository<Usuario, Long> {
	Usuario findByEmail(String email);

}

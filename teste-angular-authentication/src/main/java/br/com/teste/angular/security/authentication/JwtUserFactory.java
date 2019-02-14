package br.com.teste.angular.security.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.teste.angular.security.authentication.entity.Usuario;
import br.com.teste.angular.security.authentication.entity.contant.PerfilEnum;

public class JwtUserFactory {

	private JwtUserFactory() {
	}

	public static JwtUser gerarJwtUser(Usuario usuario) {
		return new JwtUser(usuario.getId(), usuario.getEmail(), usuario.getSenha(),
				getGrantedAuthorities(usuario.getPerfil()));
	}

	private static List<GrantedAuthority> getGrantedAuthorities(PerfilEnum perfilEnum) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(perfilEnum.toString()));
		return authorities;
	}
}

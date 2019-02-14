package br.com.teste.angular.security.authentication;

import java.util.ArrayList;
import java.util.List;

public class JwtToken {

	private String token;
	private List<String> roles;

	public JwtToken() {
	}

	public void addRole(String role) {
		if (role == null || role.isEmpty()) {
			return;
		}
		if (roles == null) {
			roles = new ArrayList<>();
			roles.add(role);
		}
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public JwtToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}

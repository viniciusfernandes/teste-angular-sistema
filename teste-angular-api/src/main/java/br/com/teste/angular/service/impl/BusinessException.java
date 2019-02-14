package br.com.teste.angular.service.impl;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception {
	private List<String> l = null;

	public BusinessException() {
		super("Falha em regra de negocio.");
	}

	public void addMensagem(String mensagem) {
		if (l == null) {
			l = new ArrayList<>();
		}
		l.add(mensagem);
	}

	public void verifyThrowing() throws BusinessException {
		if (this.l != null && !l.isEmpty()) {
			throw this;
		}
	}

	public List<String> getListaMessagem() {
		return l;
	}

	private static final long serialVersionUID = 5753967757527649783L;

}

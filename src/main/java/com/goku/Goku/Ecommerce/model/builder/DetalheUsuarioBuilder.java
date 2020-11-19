package com.goku.goku.ecommerce.model.builder;

import com.goku.goku.ecommerce.model.usuario.DetalheUsuario;

public class DetalheUsuarioBuilder {

	private String login;

	public DetalheUsuarioBuilder login(String login) {
		this.login = login;
		return this;
	}

	public DetalheUsuario build() {
		DetalheUsuario detalheUsuarioDTO = new DetalheUsuario();
		detalheUsuarioDTO.setLogin(login);
		return detalheUsuarioDTO;
	}

}

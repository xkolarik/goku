package com.goku.goku.ecommerce.model.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goku.goku.ecommerce.model.usuario.DetalheUsuario;

public class DetalheUsuarioResponse {

	@JsonProperty("usuario-detalhe")
	private DetalheUsuario detalheUsuario;

	public DetalheUsuarioResponse(DetalheUsuario detalheUsuarioDTO) {
		this.detalheUsuario = detalheUsuario;
	}

	public DetalheUsuarioResponse() {
	}

	public DetalheUsuario getDetalheUsuarioDTO() {
		return detalheUsuario;
	}

	public void setDetalheUsuarioDTO(DetalheUsuario detalheUsuarioDTO) {
		this.detalheUsuario = detalheUsuario;
	}

}

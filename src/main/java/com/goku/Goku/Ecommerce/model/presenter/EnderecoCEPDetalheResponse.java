package com.goku.goku.ecommerce.model.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goku.goku.ecommerce.model.EnderecoCEPDetalhe;

public class EnderecoCEPDetalheResponse {

	@JsonProperty("endereco-detalhe")
	private EnderecoCEPDetalhe enderecoCEPDetalhe;

	public EnderecoCEPDetalheResponse(EnderecoCEPDetalhe enderecoCEPDetalhe) {
		this.enderecoCEPDetalhe = enderecoCEPDetalhe;
	}

	public EnderecoCEPDetalheResponse() {
	}

	public EnderecoCEPDetalhe getEnderecoCEPDetalhe() {
		return enderecoCEPDetalhe;
	}

	public void setEnderecoCEPDetalhe(EnderecoCEPDetalhe enderecoCEPDetalhe) {
		this.enderecoCEPDetalhe = enderecoCEPDetalhe;
	}

}

package com.goku.goku.ecommerce.model.builder;

import com.goku.goku.ecommerce.model.endereco.EnderecoCEPDetalhe;

public class EnderecoCEPDetalheBuilder {

	private Long cep;
	private String logradouro;
	private Long numero;
	private String bairro;
	private String cidade;
	private String estado;
	private String pais;

	public EnderecoCEPDetalheBuilder cep(Long cep) {
		this.cep = cep;
		return this;
	}

	public EnderecoCEPDetalheBuilder logradouro(String logradouro) {
		this.logradouro = logradouro;
		return this;
	}

	public EnderecoCEPDetalheBuilder numero(Long numero) {
		this.numero = numero;
		return this;
	}

	public EnderecoCEPDetalheBuilder bairro(String bairro) {
		this.bairro = bairro;
		return this;
	}

	public EnderecoCEPDetalheBuilder cidade(String cidade) {
		this.cidade = cidade;
		return this;
	}

	public EnderecoCEPDetalheBuilder estado(String estado) {
		this.estado = estado;
		return this;
	}

	public EnderecoCEPDetalheBuilder pais(String pais) {
		this.pais = pais;
		return this;
	}

	public EnderecoCEPDetalhe build() {
		EnderecoCEPDetalhe endereco = new EnderecoCEPDetalhe();
		endereco.setCep(cep);
		endereco.setLogradouro(logradouro);
		endereco.setNumero(numero);
		endereco.setBairro(bairro);
		endereco.setCidade(cidade);
		endereco.setEstado(estado);
		endereco.setPais(pais);

		return endereco;
	}

}

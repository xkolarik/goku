package com.goku.goku.ecommerce.function;

import com.goku.goku.ecommerce.model.builder.EnderecoBuilder;
import com.goku.goku.ecommerce.model.endereco.Endereco;

import java.util.function.Function;

public class Endereco2EnderecoFunction implements Function<Endereco, Endereco> {

	@Override
	public Endereco apply(Endereco endereco) {
		return new EnderecoBuilder().cep(endereco.getCep()).logradouro(endereco.getLogradouro()).build();
	}

}

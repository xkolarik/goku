package com.goku.goku.ecommerce.function;

import com.goku.goku.ecommerce.model.endereco.Endereco;
import com.goku.goku.ecommerce.model.endereco.EnderecoCEPDetalhe;

import java.util.function.Function;

public class Endereco2EnderecoCEPDetalheFunction implements Function<Endereco, EnderecoCEPDetalhe> {

	@Override
	public EnderecoCEPDetalhe apply(Endereco endereco) {
		EnderecoCEPDetalhe enderecoCEPDetalhe = new EnderecoCEPDetalhe();
		enderecoCEPDetalhe.setBairro(endereco.getBairro());
		enderecoCEPDetalhe.setCep(endereco.getCep());
		enderecoCEPDetalhe.setCidade(endereco.getCidade());
		enderecoCEPDetalhe.setEstado(endereco.getEstado());
		enderecoCEPDetalhe.setLogradouro(endereco.getLogradouro());
		enderecoCEPDetalhe.setNumero(endereco.getNumero());
		enderecoCEPDetalhe.setPais(endereco.getPais());
		return enderecoCEPDetalhe;
	}
}

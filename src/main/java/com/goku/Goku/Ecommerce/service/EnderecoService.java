package com.goku.goku.ecommerce.service;

import com.goku.goku.ecommerce.model.endereco.Endereco;
import com.goku.goku.ecommerce.model.endereco.EnderecoCEPDetalhe;

import java.util.List;

public interface EnderecoService {

	public void criarEndereco(Endereco novoEndereco);

	public List<Endereco> listarEnderecos();

	public void editarEndereco(Long cep, Endereco editarEndereco);

	public EnderecoCEPDetalhe buscarPorCEP(Long cep);

	public void deletarEndereco(Long cep);

}

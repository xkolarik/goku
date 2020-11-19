package com.goku.goku.ecommerce.service.impl;

import com.goku.goku.ecommerce.exception.EnderecoDuplicadoException;
import com.goku.goku.ecommerce.exception.EnderecoNotFoundException;
import com.goku.goku.ecommerce.function.Endereco2EnderecoCEPDetalheFunction;
import com.goku.goku.ecommerce.function.Endereco2EnderecoFunction;
import com.goku.goku.ecommerce.model.builder.EnderecoBuilder;
import com.goku.goku.ecommerce.model.endereco.Endereco;
import com.goku.goku.ecommerce.model.endereco.EnderecoCEPDetalhe;
import com.goku.goku.ecommerce.repository.EnderecoRepository;
import com.goku.goku.ecommerce.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoServiceImpl implements EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Override
	@CacheEvict(cacheNames = { "enderecos-cache", "enderecos-cep-cache" }, allEntries = true)
	public void criarEndereco(Endereco novoEndereco) {
		
		if(enderecoRepository.findByCep(novoEndereco.getCep()).isPresent()) {
			throw new EnderecoDuplicadoException();
		}
		
		Endereco endereco = new EnderecoBuilder().cep(novoEndereco.getCep())
				.logradouro(novoEndereco.getLogradouro()).numero(novoEndereco.getNumero())
				.bairro(novoEndereco.getBairro()).cidade(novoEndereco.getCidade())
				.estado(novoEndereco.getEstado()).pais(novoEndereco.getPais()).build();

		enderecoRepository.save(endereco);
	}

	@Override
	public List<Endereco> listarEnderecos() {
		return enderecoRepository.findAll().stream().map(new Endereco2EnderecoFunction())
				.collect(Collectors.toList());
	}

	@Override
	@CachePut(cacheNames = { "enderecos-cep-cache" })
	@CacheEvict(value = "enderecos-cache", allEntries = true)
	public void editarEndereco(Long cep, Endereco editarEnderecoDTO) {

		Endereco endereco = enderecoRepository.findByCep(cep).orElseThrow(EnderecoNotFoundException::new);
		Endereco enderecoChanged = new EnderecoBuilder(endereco).modify(editarEnderecoDTO);

		enderecoRepository.save(enderecoChanged);

	}

	@Override
	public EnderecoCEPDetalhe buscarPorCEP(Long cep) {
		return enderecoRepository.findByCep(cep).map(new Endereco2EnderecoCEPDetalheFunction())
				.orElseThrow(EnderecoNotFoundException::new);
	}

	@Override
	@CachePut(cacheNames = { "enderecos-cep-cache" })
	@CacheEvict(value = "enderecos-cache", allEntries = true)
	public void deletarEndereco(Long cep) {
		Endereco endereco = enderecoRepository.findByCep(cep).orElseThrow(EnderecoNotFoundException::new);
		enderecoRepository.deleteById(endereco.getId());
	}

}

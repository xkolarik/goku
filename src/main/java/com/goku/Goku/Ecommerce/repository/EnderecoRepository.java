package com.goku.goku.ecommerce.repository;


import com.goku.goku.ecommerce.model.endereco.Endereco;
import org.springframework.data.repository.CrudRepository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends CrudRepository<Endereco, Long> {

	@Cacheable("enderecos-cep-cache")
	Optional<Endereco> findByCep(Long cep);

	@Cacheable("enderecos-cache")
	List<Endereco> findAll();

}

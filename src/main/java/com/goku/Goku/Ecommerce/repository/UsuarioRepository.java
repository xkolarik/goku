package com.goku.goku.ecommerce.repository;

import com.goku.goku.ecommerce.model.usuario.Usuario;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, String> {

	@Cacheable(value = "listar-usuarios-cache")
	List<Usuario> findAll();

	@Cacheable(value = "usuario-cache")
	Optional<Usuario> findById(String login);

	boolean findByPermissao(String permissao);

}

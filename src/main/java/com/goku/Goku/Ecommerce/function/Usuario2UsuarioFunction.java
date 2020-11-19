package com.goku.goku.ecommerce.function;

import com.goku.goku.ecommerce.model.builder.UsuarioBuilder;
import com.goku.goku.ecommerce.model.usuario.Usuario;

import java.util.function.Function;

public class Usuario2UsuarioFunction implements Function<Usuario, Usuario> {

	@Override
	public Usuario apply(Usuario usuario) {
		return new UsuarioBuilder().login(usuario.getLogin()).permissao(usuario.getPermissao()).build();
	}

}

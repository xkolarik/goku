package com.goku.goku.ecommerce.function;

import com.goku.goku.ecommerce.model.builder.DetalheUsuarioBuilder;
import com.goku.goku.ecommerce.model.usuario.DetalheUsuario;
import com.goku.goku.ecommerce.model.usuario.Usuario;

import java.util.function.Function;

public class Usuario2DetalheUsuarioFunction implements Function<Usuario, DetalheUsuario> {

	@Override
	public DetalheUsuario apply(Usuario usuario) {
		return new DetalheUsuarioBuilder().login(usuario.getLogin()).build();
	}

}

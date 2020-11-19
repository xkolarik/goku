package com.goku.goku.ecommerce.model.builder;

import com.goku.goku.ecommerce.model.usuario.EditarUsuario;
import com.goku.goku.ecommerce.model.usuario.Usuario;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UsuarioBuilder {

	private String login;
	private String senha;
	private int quantidadeItens;
	private EditarUsuario editarUsuario;
	private Usuario usuario;
	private String permissao;

	public UsuarioBuilder login(String login) {
		this.login = login;
		return this;
	}

	public UsuarioBuilder senha(String senha) {
		this.senha = senha;
		return this;
	}

	public UsuarioBuilder permissao(String permissao) {
		this.permissao = permissao;
		return this;
	}

	public UsuarioBuilder quantidadeItens(int quantidadeItens) {
		this.quantidadeItens = quantidadeItens;
		return this;
	}

	public UsuarioBuilder source(EditarUsuario editarUsuario) {
		this.editarUsuario = editarUsuario;
		return this;
	}

	public UsuarioBuilder target(Usuario usuario) {
		this.usuario = usuario;
		return this;
	}

	public Usuario build() {
		Usuario usuario = new Usuario();
		usuario.setLogin(login);
		usuario.setSenha(senha);
		usuario.setPermissao(permissao);
		return usuario;
	}

	public List<Usuario> buildList() {
		List<Usuario> usuarios = new ArrayList<>();

		for (Integer i = 0; i < quantidadeItens; i++) {
			Usuario usuario = new Usuario();
			usuario.setLogin("usuario ".concat(i.toString()));
			usuario.setSenha("senha ".concat(i.toString()));
			usuarios.add(usuario);
		}

		return usuarios;
	}

	public Usuario modify() {

		if (StringUtils.isNotBlank(editarUsuario.getSenha())) {
			usuario.setSenha(editarUsuario.getSenha());
		}

		return usuario;
	}

}

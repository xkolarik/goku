package com.goku.goku.ecommerce.service;

import com.goku.goku.ecommerce.model.usuario.DetalheUsuario;
import com.goku.goku.ecommerce.model.usuario.EditarUsuario;
import com.goku.goku.ecommerce.model.usuario.NovoUsuario;
import com.goku.goku.ecommerce.model.usuario.Usuario;

import java.util.List;

public interface UsuarioService {

    void criarUsuario(NovoUsuario novoUsuario)      ;

    List<Usuario> listarUsuarios();

    void deletarUsuario(String login);

    void editarUsuario(String login, EditarUsuario editarUsuario);

    DetalheUsuario buscarUsuario(String login);

    void criarUsuarioMaster(NovoUsuario novoUsuarioMaster);
}

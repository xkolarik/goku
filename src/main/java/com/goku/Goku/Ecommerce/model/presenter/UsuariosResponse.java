package com.goku.goku.ecommerce.model.presenter;

import com.goku.goku.ecommerce.model.usuario.Usuario;

import java.util.List;

public class UsuariosResponse {

    private List<Usuario> usuarios;

    public UsuariosResponse(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public UsuariosResponse() {
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}

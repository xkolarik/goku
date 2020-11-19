package com.goku.goku.ecommerce.service.impl;

import com.goku.goku.ecommerce.enums.Permissao;
import com.goku.goku.ecommerce.exception.UsuarioDuplicadoException;
import com.goku.goku.ecommerce.exception.UsuarioMasterExistenteException;
import com.goku.goku.ecommerce.exception.UsuarioNotFoundException;
import com.goku.goku.ecommerce.function.Usuario2DetalheUsuarioFunction;
import com.goku.goku.ecommerce.function.Usuario2UsuarioFunction;
import com.goku.goku.ecommerce.model.builder.UsuarioBuilder;
import com.goku.goku.ecommerce.model.usuario.DetalheUsuario;
import com.goku.goku.ecommerce.model.usuario.EditarUsuario;
import com.goku.goku.ecommerce.model.usuario.NovoUsuario;
import com.goku.goku.ecommerce.model.usuario.Usuario;
import com.goku.goku.ecommerce.repository.UsuarioRepository;
import com.goku.goku.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @CacheEvict(cacheNames = { "listar-usuarios-cache", "usuario-cache" }, allEntries = true)
    public void criarUsuario(NovoUsuario novoUsuario) {

        if (usuarioRepository.findById(novoUsuario.getLogin()).isPresent()) {
            throw new UsuarioDuplicadoException();
        }

        Usuario usuario = new UsuarioBuilder().login(novoUsuario.getLogin()).senha(novoUsuario.getSenha())
                .permissao(Permissao.COMUM.name()).build();
        usuarioRepository.save(usuario);

    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(new Usuario2UsuarioFunction()).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(cacheNames = { "listar-usuarios-cache" }, allEntries = true)
    @CachePut(cacheNames = "usuario-cache")
    public void deletarUsuario(String login) {
        Usuario usuario = usuarioRepository.findById(login).orElseThrow(UsuarioNotFoundException::new);
        usuarioRepository.delete(usuario);
    }

    @Override
    @CacheEvict(cacheNames = { "listar-usuarios-cache" }, allEntries = true)
    @CachePut(cacheNames = "usuario-cache")
    public void editarUsuario(String login, EditarUsuario editarUsuarioDTO) {
        Usuario usuario = usuarioRepository.findById(login).orElseThrow(UsuarioNotFoundException::new);
        Usuario usuarioChanged = new UsuarioBuilder().source(editarUsuarioDTO).target(usuario).modify();

        usuarioRepository.save(usuarioChanged);
    }

    @Override
    public DetalheUsuario buscarUsuario(String login) {
        return usuarioRepository.findById(login).map(new Usuario2DetalheUsuarioFunction())
                .orElseThrow(UsuarioNotFoundException::new);
    }

    @Override
    public void criarUsuarioMaster(NovoUsuario novoUsuarioMasterDTO) {

        if (usuarioRepository.findByPermissao(Permissao.MASTER.name()).isPresent()) {
            throw new UsuarioMasterExistenteException();
        }

        Usuario usuario = new UsuarioBuilder().login(novoUsuarioMasterDTO.getLogin())
                .senha(novoUsuarioMasterDTO.getSenha()).permissao(Permissao.MASTER.name()).build();
        usuarioRepository.save(usuario);

    }
}

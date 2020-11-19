package com.goku.goku.ecommerce.controller;

import com.goku.goku.ecommerce.controller.resource.UsuarioControllerResource;
import com.goku.goku.ecommerce.model.presenter.DetalheUsuarioResponse;
import com.goku.goku.ecommerce.model.presenter.UsuariosResponse;
import com.goku.goku.ecommerce.model.usuario.DetalheUsuario;
import com.goku.goku.ecommerce.model.usuario.EditarUsuario;
import com.goku.goku.ecommerce.model.usuario.NovoUsuario;
import com.goku.goku.ecommerce.model.usuario.Usuario;
import com.goku.goku.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class CadastroUsuarioController implements UsuarioControllerResource {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> criarUsuario(@Valid @RequestBody NovoUsuario novoUsuario) {
        usuarioService.criarUsuario(novoUsuario);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping(path = "/{login}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> editarUsuario(@PathVariable("login") String login,
                                              @Valid @RequestBody EditarUsuario editarUsuario) {
        usuarioService.editarUsuario(login, editarUsuario);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping(path = "/{login}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("login") String login) {
        usuarioService.deletarUsuario(login);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping(path = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetalheUsuarioResponse> buscarUsuario(@PathVariable("login") String login) {
        DetalheUsuario detalheUsuario = usuarioService.buscarUsuario(login);
        return ResponseEntity.ok(new DetalheUsuarioResponse(detalheUsuario));
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuariosResponse> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(new UsuariosResponse(usuarios));
    }

    @Override
    @PostMapping(path = "/master", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> criarUsuarioMaster(@RequestBody @Valid NovoUsuario novoUsuario) {
        usuarioService.criarUsuarioMaster(novoUsuario);
        return ResponseEntity.noContent().build();
    }
}

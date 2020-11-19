package com.goku.goku.ecommerce.controller.resource;

import com.goku.goku.ecommerce.model.presenter.DetalheUsuarioResponse;
import com.goku.goku.ecommerce.model.presenter.ErrorResponse;
import com.goku.goku.ecommerce.model.presenter.UsuariosResponse;
import com.goku.goku.ecommerce.model.usuario.EditarUsuario;
import com.goku.goku.ecommerce.model.usuario.NovoUsuario;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface UsuarioControllerResource {

    @ApiOperation(value = "Cria um novo usuário")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "Conflict", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    public ResponseEntity<Void> criarUsuario(NovoUsuario novoUsuario);

    @ApiOperation(value = "Edita um usuário")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    public ResponseEntity<Void> editarUsuario(String login, EditarUsuario editarUsuario);

    @ApiOperation(value = "Deleta um usuário")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    public ResponseEntity<Void> deletarUsuario(String login);

    @ApiOperation(value = "Busca um usuário pelo Login")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    public ResponseEntity<DetalheUsuarioResponse> buscarUsuario(String login);

    @ApiOperation(value = "Lista os usuários")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    public ResponseEntity<UsuariosResponse> listarUsuarios();

    @ApiOperation(value = "Cria usuário master")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "Conflict", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    public ResponseEntity<Void> criarUsuarioMaster(NovoUsuario novoUsuario);
}

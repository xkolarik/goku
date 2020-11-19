package com.goku.goku.ecommerce.controller.resource;

import com.goku.goku.ecommerce.model.endereco.Endereco;
import com.goku.goku.ecommerce.model.presenter.EnderecoCEPDetalheResponse;
import com.goku.goku.ecommerce.model.presenter.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(value = "Serviço que gerencia os endereços")
public interface EnderecoControllerResource {

    @ApiOperation(value = "Cria um novo endereço")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "Conflict", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    ResponseEntity<Void> criarEndereco(Endereco novoEndereco);

    @ApiOperation(value = "Lista os endereços")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    ResponseEntity<List<Endereco>> listarEnderecos();

    @ApiOperation(value = "Edita um endereço")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    ResponseEntity<Void> editarEndereco(Long cep, Endereco editarEndereco);

    @ApiOperation(value = "Busca um endereço por CEP")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    ResponseEntity<EnderecoCEPDetalheResponse> buscarPorCEP(Long cep);

    @ApiOperation(value = "Deleta um endereço")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class) })
    ResponseEntity<Void> deletarEndereco(Long cep);
}

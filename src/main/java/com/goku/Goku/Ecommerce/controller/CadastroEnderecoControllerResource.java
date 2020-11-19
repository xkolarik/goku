package com.goku.goku.ecommerce.controller;

import com.goku.goku.ecommerce.controller.resource.EnderecoControllerResource;
import com.goku.goku.ecommerce.model.endereco.Endereco;
import com.goku.goku.ecommerce.model.endereco.EnderecoCEPDetalhe;
import com.goku.goku.ecommerce.model.presenter.EnderecoCEPDetalheResponse;
import com.goku.goku.ecommerce.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/enderecos")
public class CadastroEnderecoControllerResource implements EnderecoControllerResource {

    @Autowired
    private EnderecoService enderecoService;

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> criarEndereco(@Valid @RequestBody Endereco novoEndereco) {
        enderecoService.criarEndereco(novoEndereco);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Endereco>> listarEnderecos() {
        List<Endereco> enderecos = enderecoService.listarEnderecos();
        return ResponseEntity.ok(enderecos);
    }

    @Override
    @PutMapping(path = "/{cep}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> editarEndereco(@PathVariable("cep") Long cep,
                                               @Valid @RequestBody Endereco editarEndereco) {
        enderecoService.editarEndereco(cep, editarEndereco);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping(path = "/{cep}")
    public ResponseEntity<EnderecoCEPDetalheResponse> buscarPorCEP(@PathVariable("cep") Long cep) {
        EnderecoCEPDetalhe enderecoCEPDetalhe = enderecoService.buscarPorCEP(cep);
        return ResponseEntity.ok(new EnderecoCEPDetalheResponse(enderecoCEPDetalhe));
    }

    @Override
    @DeleteMapping(path = "/{cep}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable("cep") Long cep) {
        enderecoService.deletarEndereco(cep);
        return ResponseEntity.noContent().build();
    }

}

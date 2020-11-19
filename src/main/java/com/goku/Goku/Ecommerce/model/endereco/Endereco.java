package com.goku.goku.ecommerce.model.endereco;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Endereco {

    @Id
    private Long id;

    private Long cep;
    private String logradouro;
    private Long numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;
    private List<String> enderecos;

    public Endereco(Endereco enderecos) {
    }
}

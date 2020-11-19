package com.goku.goku.ecommerce.model.endereco;

import lombok.*;
import org.springframework.data.annotation.Id;

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

    public Endereco(Endereco enderecos) {
    }
}

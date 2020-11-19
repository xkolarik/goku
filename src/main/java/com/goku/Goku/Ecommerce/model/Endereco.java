package com.goku.goku.ecommerce.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Endereco {

    private Long numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;

    public Endereco(Endereco enderecos) {
    }
}

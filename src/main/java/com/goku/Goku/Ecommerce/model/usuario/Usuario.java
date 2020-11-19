package com.goku.goku.ecommerce.model.usuario;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Usuario {

    @Id
    private String login;
    private String senha;
    private String permissao;
}

package com.goku.goku.ecommerce.model.usuario;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NovoUsuario {

    private String login;
    private String senha;
}

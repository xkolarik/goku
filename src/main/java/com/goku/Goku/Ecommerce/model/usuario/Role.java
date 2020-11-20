package com.goku.goku.ecommerce.model.usuario;

import com.goku.goku.ecommerce.model.jwtf.ERole;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Role {

    private Integer id;
    private ERole name;
}

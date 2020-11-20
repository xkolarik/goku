package com.goku.goku.ecommerce.model.usuario;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Usuario(String jwt, Long id, String username, String password, List<String> roles) {
    }

    public Usuario(String login, String permissao, String encode) {
    }
}

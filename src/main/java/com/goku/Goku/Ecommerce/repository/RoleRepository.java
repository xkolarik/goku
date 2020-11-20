package com.goku.goku.ecommerce.repository;

import com.goku.goku.ecommerce.model.jwtf.ERole;
import com.goku.goku.ecommerce.model.usuario.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}

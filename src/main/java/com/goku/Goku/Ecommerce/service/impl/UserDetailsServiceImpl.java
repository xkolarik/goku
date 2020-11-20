package com.goku.goku.ecommerce.service.impl;

import com.goku.goku.ecommerce.model.usuario.Usuario;
import com.goku.goku.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario user = userRepository.findById(login)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + login));

        return UserDetailsImpl.build(user);
    }
}

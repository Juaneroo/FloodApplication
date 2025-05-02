package com.flood_web.security.service;



import com.flood_web.controller.Administrators;
import com.flood_web.service.crud.AdministratorsCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdministratorsCrudService administratorsCrudService;


    // Aquí ocurre la autenticación: se busca el usuario y se devuelve como UserDetails
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Administrators admin = administratorsCrudService.getByCedula(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new org.springframework.security.core.userdetails.User(
                admin.getCedula(),
                admin.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + admin.getRole()))
        );
    }
}

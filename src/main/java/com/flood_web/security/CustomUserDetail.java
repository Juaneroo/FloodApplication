package com.flood_web.security;

import com.flood_web.controller.Administrators;
import com.flood_web.data.entity.AdministratorsEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class CustomUserDetail extends org.springframework.security.core.userdetails.User {

    private final String cedula;
    private final String name;
    private final String roleName;
    private final String phone;

    public CustomUserDetail(Administrators adminEntity) {
        super(adminEntity.getCedula(), adminEntity.getPassword(), getAuthorities(adminEntity));
        this.cedula = adminEntity.getCedula();
        this.name = adminEntity.getName();
        this.roleName = normalizedRoleName(adminEntity.getRole());
        this.phone = adminEntity.getTelephone();
    }

    // Getters for the custom fields
    public String getCedula() {
        return cedula;
    }

    public String getName() {
        return name;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getPhone() {
        return phone;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(Administrators adminEntity) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + adminEntity.getRole().toUpperCase()));
    }

    // create a funton that retunr a normalized a role name
    //if role is ADMIN return Administrador, if SOCORRO return Socorrista otherwise return desconocido
    private static String normalizedRoleName(String roleName){
        switch (roleName) {
            case "ADMIN":
                return "Administrador";
            case "SOCORRO":
                return "Socorrista";
            default:
                return "Desconocido";
        }
    }
}


package com.flood_web.service;

import com.flood_web.data.entity.AdministratorsEntity; // Ajusta a tu entidad de usuario
import com.flood_web.data.repository.AdministratorsRepository; // Ajusta a tu repositorio de usuario
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdministratorsRepository administratorsRepository; // Ajusta al repositorio de tu entidad de usuario

    public void updateUserRole(String userId, String newRole) {
        Optional<AdministratorsEntity> userOptional = administratorsRepository.findById(userId); // Ajusta a tu repositorio y entidad
        if (userOptional.isPresent()) {
            AdministratorsEntity userEntity = userOptional.get();
            userEntity.setRole(newRole);
            administratorsRepository.save(userEntity); // Guarda el rol actualizado
            logoutUser(userId); // Forzar el logout del usuario
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + userId);
        }
    }

    public void logoutUser(String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails.getUsername().equals(userId)) {
                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                HttpSession session = attr.getRequest().getSession(false);
                if (session != null) {
                    session.invalidate();
                    SecurityContextHolder.clearContext(); // Opcional: limpiar el contexto
                }
            }
        }
    }
}
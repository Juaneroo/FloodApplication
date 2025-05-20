package com.flood_web.controller;

import com.flood_web.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@PreAuthorize("hasRole('ADMIN')")
@Controller
@RequestMapping("/admin/roles")
public class RoleManagementController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/update-user-role")
    public String updateUserRole(@RequestParam String userId, @RequestParam String newRole, RedirectAttributes redirectAttributes) {
        try {
            adminService.updateUserRole(userId, newRole);
            redirectAttributes.addAttribute("roleChanged", true); // Añade el parámetro a la URL de redirección
            return "redirect:/outside/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el rol: " + e.getMessage());
            return "redirect:/inside/admin/users"; // Redirige a donde sea apropiado mostrar el error
        }
    }
}
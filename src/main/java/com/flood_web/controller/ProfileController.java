package com.flood_web.controller;

import com.flood_web.security.service.UpdateProfileService;
import com.flood_web.service.crud.AlertCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/inside")
public class ProfileController {

    private static final String VIEW_PATH = "/model/inside";

    @Autowired
    private UpdateProfileService updateProfileService;

    @GetMapping("/profile")
    public ModelAndView profile() {
        return new ModelAndView(VIEW_PATH + "/profile")
                .addObject("changePasswordRequest", ChangePasswordRequest.builder().build()); //
    }

    @PostMapping("/profile/password")
    public String changePassword(
            @ModelAttribute("changePasswordRequest") ChangePasswordRequest changePasswordRequest,
            RedirectAttributes redirectAttributes
    ) {
        boolean wasUpdated = updateProfileService.changePassword(
                changePasswordRequest.getOldPassword(),
                changePasswordRequest.getNewPassword()
        );

        // Añadir el objeto al flash para mantenerlo solo durante la redirección
        redirectAttributes.addFlashAttribute("changePasswordRequest", new ChangePasswordRequest());

        if (wasUpdated) {
            redirectAttributes.addFlashAttribute("pwdChanged", true);
        } else {
            redirectAttributes.addFlashAttribute("pwdNotChanged", true);
        }

        return "redirect:/inside/profile";
    }
}
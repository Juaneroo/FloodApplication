package com.flood_web.controller;

import com.flood_web.service.crud.FamilyCrudService;
import com.flood_web.service.crud.FamilyMembersCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@PreAuthorize("hasRole('ADMIN')")
@Controller
@RequestMapping("/inside")
public class FamilyMembersController {

    private static final String VIEW_PATH = "/model/inside";

    @Autowired
    FamilyMembersCrudService familyMembersCrudService;

    @Autowired
    FamilyCrudService familyCrudService;

    @GetMapping("/familyMembers")
    public ModelAndView getFamilyMembers(){
        return new ModelAndView(VIEW_PATH  + "/familyMembers")
                .addObject("familyMember", FamilyMembers.builder().build())
                .addObject("familyMembers", familyMembersCrudService.listAll())
                .addObject("families", familyCrudService.listAll());
    }

    @GetMapping("/familyMembers/{id}")
    public ModelAndView getFamilyMembersByIdNumber(@PathVariable String id){
        return new ModelAndView(VIEW_PATH  + "/familyMembers")
                .addObject("familyMember", familyMembersCrudService.findById(id).orElse(FamilyMembers.builder().build()))
                .addObject("familyMembers", familyMembersCrudService.listAll())
                .addObject("families", familyCrudService.listAll());
    }

    @PostMapping("/familyMembers")
    public String saveFamilyMembers(@ModelAttribute("familyMember") FamilyMembers familyMember, RedirectAttributes redirectAttributes) {

        // Asumo que el campo en tu DTO FamilyMembers se llama 'cedula'
        if (familyMembersCrudService.existsByCedula(familyMember.getCedula())) {
            redirectAttributes.addFlashAttribute("showFamilyMembersSavedError", true)
                    .addFlashAttribute("errorMessage", "Ya existe un miembro de familia con la cédula: " + familyMember.getCedula());
        } else {
            try {
                familyMembersCrudService.save(familyMember);
                redirectAttributes.addFlashAttribute("showFamilyMembersSavedOk", true);
            } catch (Exception ex) {
                redirectAttributes.addFlashAttribute("showFamilyMembersSavedError", true)
                        .addFlashAttribute("errorMessage", "Ocurrió un error al guardar el miembro de familia.");
            }
        }

        return "redirect:/inside/familyMembers";
    }
}
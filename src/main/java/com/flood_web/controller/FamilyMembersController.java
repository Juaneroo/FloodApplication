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
                .addObject("familyMember", familyMembersCrudService.findById(id).get())
                .addObject("familyMembers", familyMembersCrudService.listAll())
                .addObject("families", familyCrudService.listAll());

    }



    @PostMapping("/familyMembers")
    public String saveFamilyMembers(@ModelAttribute("familyMembers") FamilyMembers familyMembers, RedirectAttributes redirectAttributes) {

        boolean showFamilyMembersSavedOk = true;
        boolean showFamilyMembersSavedError = false;
        try{
            familyMembersCrudService.save(familyMembers);
        }catch (Exception ex){
            showFamilyMembersSavedOk = false;
            showFamilyMembersSavedError = true;
        }

        // return new ModelAndView(VIEW_PATH  + "/familyMembers")
        redirectAttributes.addFlashAttribute("showFamilyMembersSavedOk", showFamilyMembersSavedOk)
                .addFlashAttribute("showFamilyMembersSavedError", showFamilyMembersSavedError);

        return "redirect:/inside/familyMembers";
    }


}
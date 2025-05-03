package com.flood_web.controller;

import com.flood_web.service.crud.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@PreAuthorize("hasRole('ADMIN')")
@Controller
@RequestMapping("/inside")
public class FamilyController {

    @Autowired
    @Qualifier("familyCrudService")
    private CrudService<Family> familyCrudService;

    @Autowired
    @Qualifier("zoneCrudService")
    private CrudService<Zone> zoneCrudService;

    private static final String VIEW_PATH = "/model/inside";

    @GetMapping("/family")
    public ModelAndView getFamilies(){

        return new ModelAndView(VIEW_PATH  + "/family")
                .addObject("family", Family.builder().build())
                .addObject("families", familyCrudService.listAll())
                .addObject("zones", zoneCrudService.listAll());

    }

    @GetMapping("/family/{id}")
    public ModelAndView getFamilyById(@PathVariable String id){

        return new ModelAndView(VIEW_PATH  + "/family")
                .addObject("family", familyCrudService.findById(id).get())
                .addObject("families", familyCrudService.listAll())
                .addObject("zones", zoneCrudService.listAll());

    }



    @PostMapping("/family")
    public String saveFamily(@ModelAttribute("family") Family family, RedirectAttributes redirectAttributes) {

        boolean showFamilySavedOk = true;
        boolean showFamilySavedError = false;
        try{
            familyCrudService.save(family);
        }catch (Exception ex){
            showFamilySavedOk = false;
            showFamilySavedError = true;
        }

        //return new ModelAndView(VIEW_PATH  + "/family")
        redirectAttributes.addFlashAttribute("showFamilySavedOk", showFamilySavedOk)
                .addFlashAttribute("showFamilySavedError", showFamilySavedError);

        return "redirect:/inside/family";
    }


}
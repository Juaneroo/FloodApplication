package com.flood_web.controller;

import com.flood_web.service.FamilyCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/inside")
public class FamilyController {

    private static final String VIEW_PATH = "/model/inside";

    @Autowired
    FamilyCrudService familyCrudService;

    @GetMapping("/family")
    public ModelAndView getFamilies(){

        return new ModelAndView(VIEW_PATH  + "/family")
                .addObject("family", Family.builder().build())
                .addObject("families", familyCrudService.listAll());

    }

    @GetMapping("/family/{id}")
    public ModelAndView getFamilyById(@PathVariable String id){

        return new ModelAndView(VIEW_PATH  + "/family")
                .addObject("family", familyCrudService.findById(id).get())
                .addObject("families", familyCrudService.listAll());

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
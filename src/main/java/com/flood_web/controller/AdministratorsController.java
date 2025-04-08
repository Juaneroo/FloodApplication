package com.flood_web.controller;

import com.flood_web.service.crud.AdministratorsCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inside")
public class AdministratorsController {

    private static final String VIEW_PATH = "/model/inside";

    @Autowired
    AdministratorsCrudService administratorsCrudService;

    @GetMapping("/administrators")
    public ModelAndView getAdministrators(){

        return new ModelAndView(VIEW_PATH  + "/administrators")
                .addObject("administratorForm", Administrators.builder().build())
                .addObject("administrators", administratorsCrudService.listAll());

    }

    @GetMapping("/administrators/{id}")
    public ModelAndView getAdministratorsByIdNumber(@PathVariable String id){

        return new ModelAndView(VIEW_PATH  + "/administrators")
                .addObject("administratorForm", administratorsCrudService.findById(id).get())
                .addObject("administrators", administratorsCrudService.listAll());

    }



    @PostMapping("/administrators")
    public String saveAdministrators(@ModelAttribute("administrators") Administrators administrators, RedirectAttributes redirectAttributes) {

        boolean showAdministratorsSavedOk = true;
        boolean showAdministratorsSavedError = false;
        try{
            administratorsCrudService.save(administrators);
        }catch (Exception ex){
            showAdministratorsSavedOk = false;
            showAdministratorsSavedError = true;
        }

        // return new ModelAndView(VIEW_PATH  + "/administrators")
        redirectAttributes.addFlashAttribute("showAdministratorsSavedOk", showAdministratorsSavedOk)
                .addFlashAttribute("showAdministratorsSavedError", showAdministratorsSavedError);

        return "redirect:/inside/administrators";
    }


}
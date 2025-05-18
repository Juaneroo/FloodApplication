package com.flood_web.controller;



import com.flood_web.service.crud.AdministratorsCrudService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@PreAuthorize("hasRole('ADMIN')")

@Controller

@RequestMapping("/inside")

public class AdministratorsController {



    private static final String VIEW_PATH = "/model/inside";



    @Autowired

    AdministratorsCrudService administratorsCrudService;



    @GetMapping("/administrators")

    public ModelAndView getAdministrators(){

        return new ModelAndView(VIEW_PATH + "/administrators")

                .addObject("administratorForm", Administrators.builder().build())

                .addObject("administrators", administratorsCrudService.listAll());

    }



    @GetMapping("/administrators/{id}")

    public ModelAndView getAdministratorsByIdNumber(@PathVariable String id){

        return new ModelAndView(VIEW_PATH + "/administrators")

                .addObject("administratorForm", administratorsCrudService.findById(id).orElse(Administrators.builder().build()))

                .addObject("administrators", administratorsCrudService.listAll());

    }



    @PostMapping("/administrators")

    public String saveAdministrators(@ModelAttribute("administratorForm") Administrators administratorForm, RedirectAttributes redirectAttributes) {



        if (administratorForm.getId() == null) {

// Es una nueva creación, verificar si la cédula ya existe

            if (administratorsCrudService.getByCedula(administratorForm.getCedula()).isPresent()) {

                redirectAttributes.addFlashAttribute("showAdministratorsSavedError", true)

                        .addFlashAttribute("errorMessage", "Ya existe un administrador/socorrista con la cédula: " + administratorForm.getCedula());

                return "redirect:/inside/administrators"; // Importante: Regresar a la vista para mostrar el error

            }

        }



        boolean showAdministratorsSavedOk = true;

        boolean showAdministratorsSavedError = false;

        String errorMessage = "Ocurrió un error al guardar el administrador/socorrista.";



        try{

            administratorsCrudService.save(administratorForm);

        } catch (Exception ex){

            showAdministratorsSavedOk = false;

            showAdministratorsSavedError = true;

            errorMessage = "Ocurrió un error, ya existe un administrador/socorrista con ese número de cédula";

        }



        redirectAttributes.addFlashAttribute("showAdministratorsSavedOk", showAdministratorsSavedOk)

                .addFlashAttribute("showAdministratorsSavedError", showAdministratorsSavedError)

                .addFlashAttribute("errorMessage", errorMessage);



        return "redirect:/inside/administrators";

    }

}
package com.flood_web.controller;

import com.flood_web.service.RiverCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/inside")
public class RiverController {

    private static final String VIEW_PATH = "/model/inside";

    @Autowired
    RiverCrudService riverCrudService;

    @GetMapping("/river")
    public ModelAndView getRivers(){

        return new ModelAndView(VIEW_PATH  + "/river")
                .addObject("river", River.builder().build())
                .addObject("rivers", riverCrudService.listAll());

    }

    @GetMapping("/river/{id}")
    public ModelAndView getRiverById(@PathVariable String id){

       return new ModelAndView(VIEW_PATH  + "/river")
                .addObject("river", riverCrudService.findById(id).get())
                .addObject("rivers", riverCrudService.listAll());

    }



    @PostMapping("/river")
    public String saveRiver(@ModelAttribute("river") River river, RedirectAttributes redirectAttributes) {

        boolean showRiverSavedOk = true;
        boolean showRiverSavedError = false;
        try{
            riverCrudService.save(river);
        }catch (Exception ex){
            showRiverSavedOk = false;
            showRiverSavedError = true;
        }

        //return new ModelAndView(VIEW_PATH  + "/river")
                redirectAttributes.addFlashAttribute("showRiverSavedOk", showRiverSavedOk)
                .addFlashAttribute("showRiverSavedError", showRiverSavedError);

        return "redirect:/inside/river";
    }


}

package com.flood_web.controller;

import com.flood_web.service.ZoneCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/inside")
public class ZoneController {

    private static final String VIEW_PATH = "/model/inside";

    @Autowired
    ZoneCrudService zoneCrudService;

    @GetMapping("/zone")
    public ModelAndView getZone(){

        return new ModelAndView(VIEW_PATH  + "/zone")
                .addObject("zone", Zone.builder().build())
                .addObject("zones", zoneCrudService.listAll());

    }

    @GetMapping("/zone/{id}")
    public ModelAndView getZoneById(@PathVariable String id){

        return new ModelAndView(VIEW_PATH  + "/zone")
                .addObject("zone", zoneCrudService.findById(id).get())
                .addObject("zones", zoneCrudService.listAll());

    }



    @PostMapping("/zone")
    public String saveZone(@ModelAttribute("zone") Zone zone, RedirectAttributes redirectAttributes) {

        boolean showZoneSavedOk = true;
        boolean showZoneSavedError = false;
        try{
            zoneCrudService.save(zone);
        }catch (Exception ex){
            showZoneSavedOk = false;
            showZoneSavedError = true;
        }

        //return new ModelAndView(VIEW_PATH  + "/river")
        redirectAttributes.addFlashAttribute("showZoneSavedOk", showZoneSavedOk)
                .addFlashAttribute("showZoneSavedError", showZoneSavedError);

        return "redirect:/inside/zone";
    }


}

package com.flood_web.controller;

import com.flood_web.service.RiverCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
                .addObject("rivers", null);

    }

    @PostMapping("/river")
    public ModelAndView saveRiver(@ModelAttribute("river") River river) {

        boolean showRiverSavedOk = true;
        boolean showRiverSavedError = false;
        try{
            riverCrudService.save(river);
        }catch (Exception ex){
            showRiverSavedOk = false;
            showRiverSavedError = true;
        }

        return new ModelAndView(VIEW_PATH  + "/river")
                .addObject("showRiverSavedOk", showRiverSavedOk)
                .addObject("showRiverSavedError", showRiverSavedError)
                .addObject("river", River.builder().build())
                .addObject("rivers", riverCrudService.listAll());
    }


}

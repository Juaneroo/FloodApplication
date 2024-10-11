package com.flood_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/insite/seate")
public class SensorController {

    StringBuilder pathBuiler;


    private static final String VIEW_PATH = "/insite";


    @GetMapping("/sensor")
    private ModelAndView showModelSignUp() {

        pathBuiler = new StringBuilder();
        return new ModelAndView(pathBuiler.append(VIEW_PATH).append("/sensor").toString());
    }

}

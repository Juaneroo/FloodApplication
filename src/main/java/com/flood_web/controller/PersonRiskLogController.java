package com.flood_web.controller;

import com.flood_web.service.crud.PersonRiskLogCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inside")
public class PersonRiskLogController {

    private static final String VIEW_PATH = "/model/inside";

    @Autowired
    PersonRiskLogCrudService personRiskLogCrudService;

    @GetMapping("/personRiskLog")
    public ModelAndView getPersonRiskLog(){

        return new ModelAndView(VIEW_PATH  + "/personRiskLog")
                .addObject("personRiskLogForm", PersonRiskLog.builder().build())
                .addObject("personRiskLogs", personRiskLogCrudService.listAll());

    }

    @GetMapping("/personRiskLog/{id}")
    public ModelAndView getPersonRiskLogByIdNumber(@PathVariable String id){

        return new ModelAndView(VIEW_PATH  + "/personRiskLog")
                .addObject("personRiskLogForm", personRiskLogCrudService.findById(id).get())
                .addObject("personRiskLogs", personRiskLogCrudService.listAll());

    }

}
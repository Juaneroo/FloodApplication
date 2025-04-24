package com.flood_web.controller;
import com.flood_web.service.crud.AdministratorsCrudService;
import com.flood_web.service.crud.AlertCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/inside")
public class AlertsController {

    private static final String VIEW_PATH = "/model/inside";

    @Autowired
    AlertCrudService alertCrudService;

    @GetMapping("/alerts")
    public ModelAndView getAlerts(){

        List<Alert> alerts = alertCrudService.listAll();
        Collections.reverse(alerts); // invierte la lista
        return new ModelAndView(VIEW_PATH  + "/alerts")
                .addObject("alerts", alertCrudService.listAll());

    }
}
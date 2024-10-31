package com.flood_web.controller;

import com.flood_web.service.CrudService;
import com.flood_web.service.SensorCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/inside")
public class SensorController {

    @Autowired
    @Qualifier("sensorCrudService")
    private CrudService<Sensor> sensorCrudService;

    private static final String VIEW_PATH = "/model/inside";

    @GetMapping("/sensor")
    private ModelAndView getAllSensor() {
        return new ModelAndView(VIEW_PATH  + "/update-2")
                .addObject("showSensorSavedOk", false)
                .addObject("showSensorSavedError", false)
                .addObject("sensor", Sensor.builder().build())
                .addObject("sensors", sensorCrudService.listAll());
    }

    @PostMapping("/sensor")
    private ModelAndView saveSensor(@ModelAttribute("sensor") Sensor sensor) {

        boolean showSensorSavedOk = true;
        boolean showSensorSavedError = false;
        try{
            sensorCrudService.save(sensor);
        }catch (Exception ex){
            showSensorSavedOk = false;
            showSensorSavedError = true;
        }

        return new ModelAndView(VIEW_PATH  + "/update-2")
                .addObject("showSensorSaved", showSensorSavedOk)
                .addObject("showSensorError", showSensorSavedError)
                .addObject("sensor", Sensor.builder().build())
                .addObject("sensors", sensorCrudService.listAll());
    }

}

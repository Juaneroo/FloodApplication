package com.flood_web.controller;

import com.flood_web.service.crud.CrudService;
import com.flood_web.service.risk.RiskLevelEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inside")
public class SensorController {

    @Autowired
    @Qualifier("sensorCrudService")
    private CrudService<Sensor> sensorCrudService;

    @Autowired
    private RiskLevelEvaluator riskLevelEvaluator;

    private static final String VIEW_PATH = "/model/inside";

    @GetMapping("/sensor")
    private ModelAndView getSensor() {
        return new ModelAndView(VIEW_PATH  + "/sensor")
                .addObject("sensor", Sensor.builder().build())
                .addObject("sensors", sensorCrudService.listAll());
    }

    @GetMapping("/sensor/{id}")
    private ModelAndView getSensorById(@PathVariable String id) {
        return new ModelAndView(VIEW_PATH  + "/sensor")
                .addObject("sensor", sensorCrudService.findById(id).get())
                .addObject("sensors", sensorCrudService.listAll());
    }

    @PostMapping("/sensor")
    public String saveSensor(@ModelAttribute("sensor") Sensor sensor, RedirectAttributes redirectAttributes) {

        boolean showSensorSavedOk = true;
        boolean showSensorSavedError = false;

        if(!riskLevelEvaluator.evaluateExpression(sensor.riskExpression)){
            redirectAttributes.addFlashAttribute("showSensorSavedOk", false)
                    .addFlashAttribute("showSensorSavedError", false)
                    .addFlashAttribute("showExpressionError", true)
                    .addFlashAttribute("expressionInfo", riskLevelEvaluator.getInconsistencies(sensor.riskExpression));

            return "redirect:/inside/sensor";
        }

        try{
            sensorCrudService.save(sensor);
        }catch (Exception ex){
            showSensorSavedOk = false;
            showSensorSavedError = true;
        }

        redirectAttributes.addFlashAttribute("showSensorSavedOk", showSensorSavedOk)
                        .addFlashAttribute("showSensorSavedError", showSensorSavedError)
                .addFlashAttribute("showExpressionError", false)
                .addFlashAttribute("expressionInfo", "");


        return "redirect:/inside/sensor";

    }

}

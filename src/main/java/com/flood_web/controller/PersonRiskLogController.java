package com.flood_web.controller;

import com.flood_web.service.crud.PersonRiskLogCrudService;
import com.flood_web.controller.PersonRiskLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/inside")
public class PersonRiskLogController {

    private static final String VIEW_PATH = "/model/inside";

    @Autowired
    PersonRiskLogCrudService personRiskLogCrudService;

    @GetMapping("/personRiskLog")
    public ModelAndView getPersonRiskLog(
            @RequestParam(name="desde", required = false) String desde,
            @RequestParam(name="hasta", required = false) String hasta,
            RedirectAttributes redirectAttributes
    ){

        List<PersonRiskLog> logs;

        if (desde != null && hasta != null && !desde.isEmpty() && !hasta.isEmpty()) {
            LocalDate desdeDate = LocalDate.parse(desde);
            LocalDate hastaDate = LocalDate.parse(hasta);

            if (desdeDate.isAfter(hastaDate)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Error al ingresar las fechas 'desde' no puede ser mayor que 'hasta'.");
                return new ModelAndView("redirect:/inside/personRiskLog");
            }

            logs = personRiskLogCrudService.findBetweenDates(desde, hasta);
        } else {
            logs = personRiskLogCrudService.listAll();
        }

        return new ModelAndView(VIEW_PATH + "/personRiskLog")
                .addObject("personRiskLogForm", PersonRiskLog.builder().build())
                .addObject("personRiskLogs", logs);
    }
}

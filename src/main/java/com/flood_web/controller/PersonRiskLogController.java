package com.flood_web.controller;

import com.flood_web.service.crud.PersonRiskLogCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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


        if(!fromLowerThanTo(desde, hasta)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al ingresar las fechas 'desde' no puede ser mayor que 'hasta'.");
            return new ModelAndView("redirect:/inside/personRiskLog");
        }
        Map<String, String> dates = setDateValues(desde, hasta);
        List<PersonRiskLog> logs = personRiskLogCrudService.findBetweenDates(dates.get("desde"), dates.get("hasta"));
        return new ModelAndView(VIEW_PATH + "/personRiskLog")
                .addObject("personRiskLogForm", PersonRiskLog.builder().build())
                .addObject("personRiskLogs", logs);
    }

    private Map<String, String> setDateValues(String desde, String hasta) {
        LocalDate desdeDate = (desde == null || desde.isEmpty()) ? LocalDate.of(1999, 1, 1) : LocalDate.parse(desde);
        LocalDate hastaDate = (hasta == null || hasta.isEmpty()) ? LocalDate.now().plusDays(1) : LocalDate.parse(hasta);

        return Map.of("desde", desdeDate.toString(), "hasta", hastaDate.toString());
    }

    private boolean fromLowerThanTo(String desde, String hasta) {
        if (desde != null && hasta != null && !desde.isEmpty() && !hasta.isEmpty()) {
            LocalDate desdeDate = LocalDate.parse(desde);
            LocalDate hastaDate = LocalDate.parse(hasta);
            return desdeDate.isAfter(hastaDate);
        }
        return true;
    }
}

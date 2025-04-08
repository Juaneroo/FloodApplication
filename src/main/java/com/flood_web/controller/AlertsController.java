package com.flood_web.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;


@Controller
public class AlertsController {

    @GetMapping("/inside/alerts")
    public String mostrarAlertas() {
        return "alerts"; // Este nombre debe coincidir con el archivo alerts.html
    }
}
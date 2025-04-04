package com.flood_web.controller;

import com.flood_web.data.entity.Alerts;
import com.flood_web.data.repository.AlertsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alerts")
public class AlertsController {

    // Inyectar el repositorio (NO estático)
    private final AlertsRepository alertsRepository;

    @Autowired
    public AlertsController(AlertsRepository alertsRepository) {
        this.alertsRepository = alertsRepository;
    }

    @PostMapping("/crear")
    public String crearAlerta(@RequestParam String mensaje) {
        alertsRepository.save(new Alerts(mensaje)); // ✅ Correcto
        return "Alerta guardada";
    }
}
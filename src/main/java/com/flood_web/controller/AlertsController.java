package com.flood_web.controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.flood_web.data.entity.Alerts;
import com.flood_web.data.repository.AlertsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inside")
public class AlertsController {

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

    @Controller
    public class AlertsViewController {

        @GetMapping("/inside/alerts")
        public String mostrarVista() {
            return "alerts";
        }
    }
}
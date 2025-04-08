package com.flood_web.controller;

import com.flood_web.data.entity.AlertsEntity;
import com.flood_web.data.repository.AlertsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertsRestController {

    private final AlertsRepository alertsRepository;

    public AlertsRestController(AlertsRepository alertsRepository) {
        this.alertsRepository = alertsRepository;
    }

    @GetMapping("/listar")
    public List<AlertsEntity> listarAlertas() {
        return alertsRepository.findAll();
    }
}
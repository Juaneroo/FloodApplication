package com.flood_web.controller;

import com.flood_web.service.risk.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/risk/level")
public class RiskController {

    @Autowired
    private RiskService riskService;

    @PostMapping("/{reporterSensorId}")
    public void levelReport(@RequestBody Integer level, @PathVariable String reporterSensorId){
        riskService.handleRisk(reporterSensorId, level);
    }
}

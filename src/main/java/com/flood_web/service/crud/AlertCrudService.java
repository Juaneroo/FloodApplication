package com.flood_web.service.crud;

import com.flood_web.controller.Alert;
import com.flood_web.data.entity.AlertsEntity;
import com.flood_web.data.repository.AlertsRepository;
import com.flood_web.service.risk.RiskLevel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AlertCrudService implements CrudService<Alert> {

    @Autowired
    private AlertsRepository alertsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void save(Alert obj) {
        AlertsEntity alertsEntity = modelMapper.map(obj, AlertsEntity.class);

        // Convertir la descripción del nivel de riesgo a número antes de guardar
        int level = convertRiskLevelToNumber(obj.getRiskLevel());
        alertsEntity.setRiskLevel(String.valueOf(level));

        alertsRepository.save(alertsEntity);
    }

    @Override
    public List<Alert> listAll() {
        Iterable<AlertsEntity> entities = alertsRepository.findAll();

        return StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> {
                    Alert alert = modelMapper.map(entity, Alert.class);

                    // Convertir número (como String) a descripción
                    try {
                        int level = Integer.parseInt(entity.getRiskLevel());
                        RiskLevel rl = RiskLevel.fromNumber(level);
                        alert.setRiskLevel(rl != null ? rl.getDescription() : "Desconocido");
                    } catch (NumberFormatException e) {
                        alert.setRiskLevel("Desconocido");
                    }

                    return alert;
                }).collect(Collectors.toList());
    }

    @Override
    public Optional<Alert> findById(String id) {
        return Optional.empty();
    }

    private int convertRiskLevelToNumber(String description) {
        if (description == null) return 0;

        for (RiskLevel level : RiskLevel.values()) {
            if (level.getDescription().equalsIgnoreCase(description)) {
                return level.getLevel();
            }
        }
        return 0;
    }
}
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

@Service
public class AlertCrudService implements CrudService<Alert> {

    @Autowired
    private AlertsRepository alertsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void save(Alert obj) {
        AlertsEntity alertsEntity = modelMapper.map(obj, AlertsEntity.class);
        alertsRepository.save(alertsEntity);
    }

    @Override
    public List<Alert> listAll() {
        List<AlertsEntity> entities = alertsRepository.findAllByOrderByDateDesc(); //

        return entities.stream()
                .map(entity -> modelMapper.map(entity, Alert.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Alert> findById(String id) {
        return Optional.empty();
    }
}
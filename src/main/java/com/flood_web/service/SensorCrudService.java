package com.flood_web.service;

import com.flood_web.controller.Sensor;
import com.flood_web.data.entity.SensorEntity;
import com.flood_web.data.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service("sensorCrudService")
public class SensorCrudService implements CrudService<Sensor>{

    @Autowired
    private SensorRepository sensorRepository;

    @Override
    public void save(Sensor sensor) {

        SensorEntity sensorEntity = SensorEntity
                .builder()
                .withId(UUID.randomUUID().toString().substring(0, 8))
                .withLocation(sensor.location())
                .withName(sensor.name())
                .build();

        sensorRepository.save(sensorEntity);
    }

    @Override
    public List<Sensor> listAll() {

        Iterable<SensorEntity> entities = sensorRepository.findAll();

        return StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> Sensor.builder()
                        .withId(entity.getId())
                        .withName(entity.getName())
                        .withLocation(entity.getLocation())
                        .build()
                )
                .collect(Collectors.toList());
    }
}

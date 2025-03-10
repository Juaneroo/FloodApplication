package com.flood_web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flood_web.controller.River;
import com.flood_web.controller.Sensor;
import com.flood_web.data.entity.RiverEntity;
import com.flood_web.data.entity.SensorEntity;
import com.flood_web.data.repository.SensorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service("sensorCrudService")
public class SensorCrudService implements CrudService<Sensor>{

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void save(Sensor sensor) {

        SensorEntity sensorEntity = modelMapper.map(sensor, SensorEntity.class);
        sensorRepository.save(sensorEntity);
    }

    @Override
    public List<Sensor> listAll() {

        Iterable<SensorEntity> entities = sensorRepository.findAll();

        return StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> modelMapper.map(entity, Sensor.class)
                )
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Sensor> findById(String id) {
        Optional<SensorEntity> foundSensor = this.sensorRepository.findById(id);
        if (foundSensor.isEmpty()){
            return Optional.of(Sensor.builder().build());
        }
        SensorEntity sensorEntity = foundSensor.get();
        Sensor sensor = modelMapper.map(sensorEntity, Sensor.class);
        return Optional.of(sensor);
    }


    public Optional<Sensor> findByIdToEvaluate(String id) {
        Optional<SensorEntity> foundSensor = this.sensorRepository.findById(id);
        if (foundSensor.isEmpty()){
            return Optional.ofNullable(null);
        }
        SensorEntity sensorEntity = foundSensor.get();
        Sensor sensor = modelMapper.map(sensorEntity, Sensor.class);
        return Optional.of(sensor);
    }
}

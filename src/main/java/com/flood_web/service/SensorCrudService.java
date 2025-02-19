package com.flood_web.service;

import com.flood_web.controller.River;
import com.flood_web.controller.Sensor;
import com.flood_web.data.entity.RiverEntity;
import com.flood_web.data.entity.SensorEntity;
import com.flood_web.data.repository.SensorRepository;
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
    SensorRepository sensorRepository;

    @Override
    public void save(Sensor sensor) {

        String idSensor = null;
        if (sensor.getId() != null && !sensor.getId().trim().isEmpty()){
            idSensor = sensor.getId();
        }else{
            idSensor = UUID.randomUUID().toString().substring(0, 8);
        }

        SensorEntity sensorEntity = SensorEntity.builder()
                .withId(idSensor)
                .withName(sensor.getName())
                .withActive(sensor.getActive())
                .withRiverEntity(RiverEntity.builder().withId(sensor.getRiver().getId()).build())
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
                        .withActive(entity.isActive())
                        .withRiver(River.builder().withId("ss").withName("fake").build())
                        .build()
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
        Sensor sensor = Sensor.builder()
                .withId(sensorEntity.getId())
                .withName(sensorEntity.getName())
                .withActive(sensorEntity.isActive())
                .build();

        return Optional.of(sensor);
    }
}
